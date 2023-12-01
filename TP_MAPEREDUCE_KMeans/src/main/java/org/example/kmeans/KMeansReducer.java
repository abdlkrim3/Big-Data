package org.example.kmeans;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KMeansReducer extends Reducer<Text, Text, Text, Text> {
    private List<Centroid> centroids = new ArrayList<>();

    protected void setup(Context context) throws IOException, InterruptedException {
        // Charger les centroïdes initiaux à partir d'un fichier CSV spécifié dans le cache distribué
        Path[] localCacheFiles = context.getLocalCacheFiles();

        if (localCacheFiles != null && localCacheFiles.length > 0) {
            String centroidsCSVPath = localCacheFiles[0].toString();
            centroids = loadCentroidsFromCSV(centroidsCSVPath);
        }
    }

    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<DataPoint> dataPoints = new ArrayList<>();

        for (Text value : values) {
            String[] tokens = value.toString().split(",");
            if (tokens.length >= 2) {
                double x = Double.parseDouble(tokens[0]);
                double y = Double.parseDouble(tokens[1]);
                dataPoints.add(new DataPoint(x, y));
            }
        }

        Centroid newCentroid = calculateNewCentroid(dataPoints);

        // Émettre le nouveau centroïde comme clé et les points de données comme valeur
        context.write(new Text(newCentroid.toString()), new Text(dataPoints.toString()));
    }

    private Centroid calculateNewCentroid(List<DataPoint> dataPoints) {
        // Calculer le nouveau centroïde basé sur la moyenne des points de données du cluster
        double sumX = 0.0;
        double sumY = 0.0;

        for (DataPoint dataPoint : dataPoints) {
            sumX += dataPoint.getX();
            sumY += dataPoint.getY();
        }

        double newX = sumX / dataPoints.size();
        double newY = sumY / dataPoints.size();

        return new Centroid(newX, newY);
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
