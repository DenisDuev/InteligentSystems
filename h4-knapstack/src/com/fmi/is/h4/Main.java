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

  private static List<Chromosome> population = new ArrayList<>();
  private static final int SIZE_OF_POPULATION = 50;
  private static final int NUMBER_OF_GENERATIONS = 500;
  private static final int NUMBER_OF_MUTATIONS = 20;
  private static final int NUMBER_OF_CROSSOVERS = 10;
  private static final int NUMBER_OF_BEST_CROSSOVERS = 10;
  private static int maxKilos;
  private static int numberOfEntries;
  private static Random rand = new Random();

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String[] inputData = reader.readLine().split(" ");
    maxKilos = Integer.parseInt(inputData[0]);
    numberOfEntries = Integer.parseInt(inputData[1]);
    fillInitialItems(reader);
    createInitialPopulation(numberOfEntries);

    for (int i = 0; i < NUMBER_OF_GENERATIONS; i++) {
      crossover();
      mutate();
      fitness();
      Chromosome max = Collections.max(population, Comparator.comparingInt(Chromosome::getPrice));
      System.out.println("size " + population.size()+ " " + max);
    }
  }

  private static void fillInitialItems(BufferedReader reader)
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

      Chromosome chromosome = new Chromosome(numberOfEntries, weight, price);
      List<Integer> range =
          IntStream.rangeClosed(0, numberOfEntries - 1).boxed().collect(Collectors.toList());

      while (!range.isEmpty()) {
        int randomRangeIndex = rand.nextInt(range.size());
        int randomIndex = range.get(randomRangeIndex);
        range.remove(randomRangeIndex);
        if (chromosome.getWeight() + weight[randomIndex] > maxKilos){
          continue;
        }
        chromosome.addItem(randomIndex);
      }
      population.add(chromosome);
    }
  }

  private static void crossover() {
    population.sort(Comparator.comparingInt(Chromosome::getPrice));
    List<Chromosome> children = new ArrayList<>(NUMBER_OF_CROSSOVERS);
    for (int i = 0; i < NUMBER_OF_CROSSOVERS; i++) {
      int indexOfGoodChromosome = rand.nextInt(population.size()) % NUMBER_OF_BEST_CROSSOVERS;
      int randomIndex = rand.nextInt(population.size());
      if (indexOfGoodChromosome == randomIndex) {
        continue;
      }
      Chromosome goodChromosome = population.get(indexOfGoodChromosome);
      Chromosome otherChromosome = population.get(randomIndex);
      children.add(Chromosome.onePointCrossover(goodChromosome, otherChromosome));
    }
    population.addAll(children);
  }

  private static void fitness(){
    population.sort(Comparator.comparingInt(Chromosome::getPrice).reversed());
    List<Chromosome> newList = new ArrayList<>(NUMBER_OF_GENERATIONS);
    for (int i = 0, fittest = 0; fittest < SIZE_OF_POPULATION; i++) {
      if (population.get(i).getWeight() <= maxKilos){
        newList.add(population.get(i));
        fittest++;
      }
    }
    population = newList;
  }

  private static void mutate() {
    population.sort(Comparator.comparingInt(Chromosome::getWeight).reversed());
    List<Chromosome> mutatedChromosomes = new ArrayList<>(NUMBER_OF_MUTATIONS);
    for (int i = 0; i < NUMBER_OF_MUTATIONS; i++) {
      int randomIndexChromosome = rand.nextInt(population.size());
      int randomIndexItem = rand.nextInt(numberOfEntries);
      Chromosome mutationChromosome = new Chromosome(population.get(randomIndexChromosome));
      mutationChromosome.mutateItem(randomIndexItem);
      mutatedChromosomes.add(mutationChromosome);
    }
    population.addAll(mutatedChromosomes);
  }
}