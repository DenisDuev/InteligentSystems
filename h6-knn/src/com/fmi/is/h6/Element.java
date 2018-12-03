package com.fmi.is.h6;

import java.util.ArrayList;
import java.util.List;

public class Element {
  public List<Double> points;
  public String name;

  public Element(String name) {
    this.name = name;
    this.points = new ArrayList<>();
  }

  public void addPoint(double point) {
    points.add(point);
  }

  public double distance(Element other) {
    double sum = 0;
    List<Double> otherPoints = other.points;
    for (int i = 0; i < points.size(); i++) {
      sum += Math.pow(points.get(i) - otherPoints.get(i), 2);
    }
    return Math.sqrt(sum);
  }
}
