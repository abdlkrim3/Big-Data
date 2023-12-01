package org.example.spark_sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class App3 {
    public static void main(String[] args) {
        SparkSession ss= SparkSession.builder().appName("TP SPARK SQL").master("local[*]").getOrCreate();
        Dataset<Row> df1=ss.read().format("jdbc")
                .option("driver","com.mysql.jdbc.Driver")
                .option("url","jdbc:mysql://localhost:3306/DB_ECOMMERCE")
                .option("dbtable","PRODUCTS")
                //.option("query","select * from PRODUCTS where PRIX>1800")
                .option("user","root")
                .option("password","")
                .load();
        df1.show();
        df1.printSchema();

    }
}
