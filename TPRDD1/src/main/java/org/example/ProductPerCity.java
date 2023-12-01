package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class ProductPerCity {
    public static void main(String[] args) {
        // Set up Spark configuration
        SparkConf conf = new SparkConf().setAppName("Total Sales").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> salesData = sc.textFile("ventes.txt");
        JavaPairRDD<Tuple2<String, String>, Double> cityProductSalesPairs = salesData.mapToPair(line -> {
            String[] fields = line.split(" ");
            String city = fields[1];
            String product = fields[2];
            double price = Double.parseDouble(fields[3]);
            return new Tuple2<>(new Tuple2<>(city, product), price);
        });

        JavaPairRDD<Tuple2<String, String>, Double> sales = cityProductSalesPairs.filter(e ->
                e._1._1.equals("2022"));

        JavaPairRDD<Tuple2<String, String>, Double> total = sales.reduceByKey((a,b) ->a+b);

        total.foreach(e -> System.out.println(e._1 + ": " + e._2));

    }
}

