package org.example.kmeans;

import java.util.List;

public class DataPoint {
    private double x;
    private double y;

    public DataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }


}

