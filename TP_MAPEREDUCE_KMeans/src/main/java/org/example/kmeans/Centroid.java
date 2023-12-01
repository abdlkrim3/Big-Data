package org.example.kmeans;

import java.io.Serializable;

public class Centroid implements Serializable {
    private double x;
    private double y;

    public Centroid(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void update(double newX, double newY) {
        x = newX;
        y = newY;
    }

    public double distanceTo(double x, double y) {
        double deltaX = x - this.x;
        double deltaY = y - this.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
