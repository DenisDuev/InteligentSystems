package com.fmi.is.h6;

import java.util.ArrayList;
import java.util.List;

public class Element implements Comparable<Element> {
  public List<Double> points;
  public String name;
  double distance;

  public Element(String name) {
    this.name = name;
    this.points = new ArrayList<>();
  }

  public void addPoint(double point) {
    points.add(point);
  }

  public void resetDistance(){
    this.distance = 0;
  }

  public void calcDistance(Element other) {
    double sum = 0;
    List<Double> otherPoints = other.points;
    for (int i = 0; i < points.size(); i++) {
      sum += Math.pow(points.get(i) - otherPoints.get(i), 2);
    }
    this.distance = Math.sqrt(sum);
  }

  @Override
  public int compareTo(Element o) {
    if (this.distance < o.distance) {
      return -1;
    } else if (this.distance > o.distance) {
      return 1;
    }
    return 0;
  }
}
