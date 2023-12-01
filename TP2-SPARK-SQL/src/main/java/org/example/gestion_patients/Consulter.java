package org.example.gestion_patients;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;
import static scala.reflect.internal.util.NoPosition.show;

public class Consulter {
    public static void main(String[] args) {
        SparkSession ss= SparkSession.builder().appName("TP SPARK SQL").master("local[*]").getOrCreate();
        Dataset<Row> df1=ss.read().format("jdbc")
                .option("driver","com.mysql.jdbc.Driver")
                .option("url","jdbc:mysql://localhost:3306/DB_HOPITAL")
                .option("dbtable","CONSULTATIONS")
                //.option("query","SELECT count(*) FROM consultations GROUP BY DATE_CONSULTATION ")
                .option("user","root")
                .option("password","")
                .load();
        System.out.println("Afficher le nombre de consultations par jour");
        df1 = df1.withColumn("DATE_CONSULTATION", to_date(col("DATE_CONSULTATION")));
        df1.groupBy("DATE_CONSULTATION").count().show();

        System.out.println("Chargement des données des médecins depuis MySQL");
        Dataset<Row> df2 = ss.read().format("jdbc")
                .option("driver", "com.mysql.jdbc.Driver")
                .option("url", "jdbc:mysql://localhost:3306/DB_HOPITAL")
                .option("dbtable", "MEDECINS")
                .option("user", "root")
                .option("password", "")
                .load();

        Dataset<Row> df3 = df1.join(df2, df1.col("ID_MEDCINE").equalTo(df2.col("ID")));

        System.out.println("Afficher le nombre de consultations par médecin");
        df3.groupBy("NOM", "PRENOM")
                .agg(count("*").alias("NOMBRE DE CONSULTATION"))
                .show();
        System.out.println("Afficher le nombre de patients par médecin");
        df3.groupBy("NOM", "PRENOM").agg(countDistinct("ID_PATIENT").alias("NOMBRE_PATIENTS")).show();
        System.out.println("Afficher les deux tables consultations et médecins");
        df3.show();
    }
}
