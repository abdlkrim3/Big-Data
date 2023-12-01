package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;


public class App1 {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf=new SparkConf().setAppName("TP Streaming").setMaster("local[*]");
        JavaStreamingContext sc =new JavaStreamingContext(conf, Duration.apply(8000));

        JavaReceiverInputDStream<String> dStream=sc.socketTextStream("localhost",8080);
        JavaDStream<String>dStreamWords=dStream.flatMap(line-> Arrays.asList(line.split(" ")).iterator());
        JavaPairDStream<String,Integer> pairDstreamWords =dStreamWords.mapToPair(m -> new Tuple2<>(m,1));
        JavaPairDStream<String,Integer> wordCount=pairDstreamWords.reduceByKey((a,b) ->a+b);
        wordCount.print();
        sc.start();
        sc.awaitTermination();
    }
}