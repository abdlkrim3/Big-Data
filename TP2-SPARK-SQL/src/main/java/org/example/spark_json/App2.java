package org.example.spark_json;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class App2 {
    public static void main(String[] args) {
        SparkSession ss= SparkSession.builder().appName("TP SPARK SQL").master("local[*]").getOrCreate();
        Dataset<Row> df1=ss.read().option("header",true).csv("products.csv");
        df1.show();
        df1.printSchema();

    }
}
