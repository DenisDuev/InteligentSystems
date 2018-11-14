package com.fmi.is.h4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  private static int[] weight;
  private static int[] price;

  private static List<Hromosome> population = new ArrayList<>();
  private static final int SIZE_OF_POPULATION = 50;
  private static final int NUMBER_OF_GENERATIONS = 1;
  private static int maxKilos;
  private static Random rand = new Random();

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] inputData = reader.readLine().split(" ");
    maxKilos = Integer.parseInt(inputData[0]);
    int numberOfEntries = Integer.parseInt(inputData[1]);
    fillInitialItems(reader, numberOfEntries);
    createInitialPopulation(numberOfEntries);

    for (int i = 0; i < NUMBER_OF_GENERATIONS; i++) {
      population.sort(Comparator.comparingInt(Hromosome::getPrice).reversed());
    }
    System.out.println("Solution: " + population.get(0));
  }

  private static void fillInitialItems(BufferedReader reader, int numberOfEntries)
      throws IOException {
    weight = new int[numberOfEntries];
    price = new int[numberOfEntries];
    for (int i = 0; i < numberOfEntries; i++) {
      int[] entries =
          Arrays.stream(reader.readLine().split(" "))
              .map(Integer::parseInt)
              .mapToInt(Integer::intValue)
              .toArray();
      weight[i] = entries[0];
      price[i] = entries[1];
    }
  }

  private static void createInitialPopulation(int numberOfEntries) {
    for (int i = 0; i < Main.SIZE_OF_POPULATION; i++) {

      Hromosome hromosome = new Hromosome(numberOfEntries, weight, price);
      List<Integer> range =
          IntStream.rangeClosed(0, numberOfEntries - 1).boxed().collect(Collectors.toList());

      while (!range.isEmpty()) {
        int randomRangeIndex = rand.nextInt(range.size());
        int randomIndex = range.get(randomRangeIndex);
        range.remove(randomRangeIndex);
        if (hromosome.getWeight() + weight[randomIndex] > maxKilos){
          continue;
        }
        hromosome.addItem(randomIndex);
      }
      population.add(hromosome);
    }
  }
}
