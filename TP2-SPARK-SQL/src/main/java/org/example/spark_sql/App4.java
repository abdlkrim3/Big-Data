package org.example.spark_sql;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import scala.Function1;

import java.util.ArrayList;
import java.util.List;

public class App4 {
    public static void main(String[] args) {
        SparkSession ss= SparkSession.builder().appName("TP SPARK SQL").master("local[*]").getOrCreate();
        List<Product> products=new ArrayList<>();
        products.add(new Product("Dell",1500,7));
        products.add(new Product("HP",9700,4));
        products.add(new Product("ASUS",64576,6));

        Encoder<Product> productEncoder= Encoders.bean(Product.class);
        Dataset<Product> ds1=ss.createDataset(products,productEncoder);
        ds1.filter((Function1<Product, Object>) product -> product.getPrice()>1300).show();


    }
}
