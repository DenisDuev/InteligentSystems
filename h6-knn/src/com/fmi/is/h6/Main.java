package com.fmi.is.h6;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
  private static List<Element> elements = new ArrayList<>();
  private static List<Element> tests = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    initializeData();  
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    selectTestAndTrainData();
    int k = Integer.parseInt(reader.readLine());
  }

  private static void selectTestAndTrainData() {
    Random random = new Random(10);
    for (int i = 0; i < 20; i++) {
      int index = random.nextInt(elements.size());
      Element current = elements.get(index);
      tests.add(current);
      elements.remove(index);
    }
  }

  private static void initializeData() throws IOException {
    List<String> stringList = Files.readAllLines(new File("iris.data").toPath());
    for (String aStringList : stringList) {
      String[] splitted = aStringList.split(",");
      Element element = new Element(splitted[splitted.length - 1]);
      for (int j = 0; j < splitted.length - 1; j++) {
        Double point = Double.parseDouble(splitted[j]);
        element.addPoint(point);
      }
      elements.add(element);
    }
  }
}
