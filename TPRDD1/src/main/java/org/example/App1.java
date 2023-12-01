package org.example;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class App1 {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("tp 1 RDD").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<Integer> rdd1=sc.parallelize(Arrays.asList(9,8,12,4,17,3));
        JavaRDD<Integer>rdd2=rdd1.map(a -> a+1);
        JavaRDD<Integer>rdd3=rdd2.filter(a->a>=10);
        List<Integer> vals=rdd3.collect();
        for (Integer v :vals) {
            System.out.println(v);
        }
    }
}