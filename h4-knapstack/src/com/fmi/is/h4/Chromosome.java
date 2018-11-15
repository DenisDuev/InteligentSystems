package com.fmi.is.h4;

import java.util.Arrays;
import java.util.Random;

public class Chromosome {
  private static Random random = new Random();
  private boolean[] items;
  private int weight;
  private int price;
  int[] allWeights;
  int[] allPrices;

  public Chromosome(int numberOfItems, int[] weights, int[] prices) {
    this.items = new boolean[numberOfItems];
    this.allWeights = weights;
    this.allPrices = prices;
  }

  public Chromosome(Chromosome chromosomeToBeCopied){
    this.items = Arrays.copyOf(chromosomeToBeCopied.items, chromosomeToBeCopied.getNumberOfItems());
    this.weight = chromosomeToBeCopied.weight;
    this.price = chromosomeToBeCopied.price;
    this.allWeights = chromosomeToBeCopied.allWeights;
    this.allPrices = chromosomeToBeCopied.allPrices;
  }

  public int getPrice() {
    return price;
  }

  public int getNumberOfItems(){
    return items.length;
  }

  public void addItem(int index) {
    this.items[index] = true;
    this.weight += allWeights[index];
    this.price += allPrices[index];
  }

  public int getWeight() {
    return weight;
  }

  public void mutateItem(int index){
    boolean isPresented = items[index];
    if (isPresented){
      items[index] = false;
      price -= allPrices[index];
      weight -= allWeights[index];
    } else {
      items[index] = true;
      price += allPrices[index];
      weight += allWeights[index];
    }
  }

  @Override
  public String toString() {
    return "Chromosome{" + "weight=" + weight + ", price=" + price + ", items=" + Arrays.toString(items) + '}';
  }

  public static Chromosome onePointCrossover(Chromosome ch1, Chromosome ch2) {
    int numberOfGenes = ch1.getNumberOfItems();
    Chromosome child = new Chromosome(numberOfGenes, ch1.allWeights, ch1.allPrices);
    int randomCrossoverIndex = random.nextInt(numberOfGenes);
    for (int i = 0; i < randomCrossoverIndex; i++) {
      if (ch1.items[i]) {
        child.addItem(i);
      }
    }
    for (int i = randomCrossoverIndex; i < numberOfGenes; i++) {
      if (ch2.items[i]) {
        child.addItem(i);
      }
    }
    return child;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Chromosome that = (Chromosome) o;

    return Arrays.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(items);
  }
}
