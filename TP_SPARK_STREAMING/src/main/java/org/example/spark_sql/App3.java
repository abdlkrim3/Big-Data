package org.example.spark_sql;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;


public class App3 {
    public static void main(String[] args) throws TimeoutException, StreamingQueryException {
        SparkSession ss= SparkSession.builder().appName("Structured Streaming").master("local[*]").getOrCreate();
        Dataset<Row> inputTable=ss.readStream().format("socket")
                .option("host","localhost")
                .option("port",8080)
                .load();
        Dataset<String> wordsTable = inputTable.as(Encoders.STRING()).flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(line.split(" ")).iterator(), Encoders.STRING());
        Dataset<Row> resultTable=wordsTable.groupBy("value").count();
        StreamingQuery query=resultTable.writeStream().format("console").outputMode("complete").start();

        query.awaitTermination();

    }
}