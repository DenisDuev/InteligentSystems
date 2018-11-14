package com.fmi.is.h4;

public class Item {
    private int weight;
    private int price;

    public Item(int weight, int price) {
        this.weight = weight;
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (weight != item.weight) return false;
        return price == item.price;
    }

    @Override
    public int hashCode() {
        int result = weight;
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "weight=" + weight +
                ", price=" + price +
                '}';
    }
}
