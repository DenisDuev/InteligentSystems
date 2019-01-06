package com.fmi.is.h6;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.*;

public class Main {
  private static List<Element> elements = new ArrayList<>();
  private static List<Element> tests = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    initializeData();
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    selectTestAndTrainData();
    int k = Integer.parseInt(reader.readLine());
    List<Boolean>results = new ArrayList<>();
    for (Element testElement : tests) {
      for (int i = 0; i < elements.size(); i++) {
        Element element = elements.get(i);
        element.calcDistance(testElement);
      }
      Collections.sort(elements);

      String className = knn(k);
      System.out.printf("Real class: %s; Calculated class: %s%n", testElement.name, className);
      results.add(testElement.name.equals(className));
    }
    Double numberOfCorrect = results.stream().filter(e -> e).count()*1.0;
    System.out.printf("Accuracy: %.2f", numberOfCorrect/results.size());
  }

  private static String knn(int k) {
    Map<String, Integer> classCoutMap = new HashMap<>();
    for (int i = 0; i < k; i++) {
      String name = elements.get(i).name;
      if (!classCoutMap.containsKey(name)) {
        classCoutMap.put(name, 0);
      }

      int prevValue = classCoutMap.get(name);
      classCoutMap.put(name, prevValue + 1);
    }

    List<Map.Entry<String, Integer>> maxList = new ArrayList<>();
    maxList.add(
        new Map.Entry<String, Integer>() {
          @Override
          public String getKey() {
            return "";
          }

          @Override
          public Integer getValue() {
            return 0;
          }

          @Override
          public Integer setValue(Integer value) {
            return null;
          }
        });
    for (Map.Entry<String, Integer> stringIntegerEntry : classCoutMap.entrySet()) {
      int value = stringIntegerEntry.getValue();
      if (maxList.get(0).getValue() == value) {
        maxList.add(stringIntegerEntry);
      } else if (maxList.get(0).getValue() < value) {
        maxList = new ArrayList<>();
        maxList.add(stringIntegerEntry);
      }
    }

    if (maxList.size() == 1) {
      return maxList.get(0).getKey();
    } else {
      for (Element element : elements) {
        for (Map.Entry<String, Integer> stringIntegerEntry : maxList) {
          if (element.name.equals(stringIntegerEntry.getKey())) {
            return element.name;
          }
        }
      }
    }
    return "";
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
