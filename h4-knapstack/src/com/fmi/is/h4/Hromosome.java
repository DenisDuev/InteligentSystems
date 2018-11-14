package com.fmi.is.h4;

import java.util.Arrays;

public class Hromosome {
  private boolean[] items;
  private int weight;
  private int price;
  int[] allWeights;
  int[] allPrices;

  public Hromosome(int numberOfItems, int[] weights, int[] prices) {
    this.items = new boolean[numberOfItems];
    this.allWeights = weights;
    this.allPrices = prices;
  }

  public int getPrice() {
    return price;
  }

  public void addItem(int index) {
    this.items[index] = true;
    this.weight += allWeights[index];
    this.price += allPrices[index];
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return "Hromosome{" + "weight=" + weight + ", price=" + price + ", items=" + Arrays.toString(items) + '}';
  }
}
