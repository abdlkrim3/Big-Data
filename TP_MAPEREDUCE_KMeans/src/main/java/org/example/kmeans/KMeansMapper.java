package org.example.kmeans;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class KMeansMapper extends Mapper<LongWritable, Text, Text, Text> {
    private List<Centroid> centroids = new ArrayList<>();

    protected void setup(Context context) throws IOException, InterruptedException {
        // Charger les centroïdes initiaux à partir d'un fichier CSV spécifié dans le cache distribué
        Path[] localCacheFiles = context.getLocalCacheFiles();

        if (localCacheFiles != null && localCacheFiles.length > 0) {
            String centroidsCSVPath = localCacheFiles[0].toString();
            centroids = loadCentroidsFromCSV(centroidsCSVPath);
        }
    }

    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split(",");
        if (tokens.length >= 2) {
            double x = Double.parseDouble(tokens[0]);
            double y = Double.parseDouble(tokens[1]);

            double minDistance = Double.MAX_VALUE;
            int nearestCentroidIndex = -1;

            // Trouver le centroïde le plus proche
            for (int i = 0; i < centroids.size(); i++) {
                double distance = centroids.get(i).distanceTo(x, y);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestCentroidIndex = i;
                }
            }

            // Émettre le centroïde le plus proche comme clé et le point de données comme valeur
            context.write(new Text(centroids.get(nearestCentroidIndex).toString()), value);
        }
    }

    private List<Centroid> loadCentroidsFromCSV(String csvPath) throws IOException {
        List<Centroid> centroids = new ArrayList();
        BufferedReader reader = new BufferedReader(new FileReader(csvPath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                double x = Double.parseDouble(parts[0]);
                double y = Double.parseDouble(parts[1]);
                centroids.add(new Centroid(x, y));
            }
        }

        return centroids;
    }
}
