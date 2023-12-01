package org.example.kmeans;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class KMeansDriver {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: KMeansDriver <inputPath> <outputPath> <centroidsCSVPath>");
            System.exit(1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "K-Means Clustering");

        job.setJarByClass(KMeansDriver.class);

        job.setMapperClass(KMeansMapper.class);
        job.setReducerClass(KMeansReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Charger les centroïdes initiaux à partir du fichier CSV spécifié (args[2])
        job.addCacheFile(new Path(args[2]).toUri());

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

