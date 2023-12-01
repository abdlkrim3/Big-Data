package org.example.spark_sql;

import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructType;

import java.util.concurrent.TimeoutException;

public class StreamingApp {

    public static void main(String[] args) throws StreamingQueryException, TimeoutException {
        SparkSession spark = SparkSession.builder()
                .appName("Incident Analysis")
                .master("local[2]")
                .getOrCreate();

        // Define the schema for your CSV data
        StructType schema = new StructType()
                .add("Id", DataTypes.IntegerType,false, Metadata.empty())
                .add("titre", DataTypes.StringType,false, Metadata.empty())
                .add("description", DataTypes.StringType,false, Metadata.empty())
                .add("service", DataTypes.StringType,false, Metadata.empty())
                .add("date", DataTypes.DateType,false, Metadata.empty());

        // Lecture des données en streaming à partir du répertoire spécifié
        Dataset<Row> incidents = spark.readStream()
                .option("header",true)
                .schema(schema)
                .csv("incidents.csv");


        System.out.println("******************************************************************");
        System.out.println("1. Afficher le nombre d’incidents par service.");
        StreamingQuery queryService = incidents.groupBy("service")
                .count()
                .writeStream()
                .outputMode("complete")
                .format("console")
                .start();

        System.out.println("*******************************************************************");
        System.out.println("2. Afficher les deux années où il y avait plus d’incidents.");        StreamingQuery queryYears = incidents.groupBy(functions.year(incidents.col("date")).as("year"))
                .count()
                .orderBy(functions.desc("count"))
                .limit(2)
                .writeStream()
                .outputMode("complete")
                .format("console")
                .start();

        // Attendre la fin du streaming
        queryService.awaitTermination();
        queryYears.awaitTermination();
    }
}

