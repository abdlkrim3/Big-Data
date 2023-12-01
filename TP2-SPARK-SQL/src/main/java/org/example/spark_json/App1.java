package org.example.spark_json;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;

public class App1 {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "winutils.exe");
        SparkSession ss= SparkSession.builder().appName("TP SPARK SQL").master("local[*]")
                .getOrCreate();

        Dataset<Row> df1=ss.read().option("multiline",true).json("products.json");
        df1.show();
        //df1.printSchema();
        //df1.select("name").show();
        //df1.select(col("name").alias("Nom of product")).show();
        //df1.orderBy(col("name").asc()).show();
        //df1.groupBy(col("name")).count().show();
        //df1.limit(2).show();
        //df1.filter("name like 'Dell' and price>1700").show();


    }
}