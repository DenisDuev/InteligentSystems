package com.fmi.is.h2;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point anotherPoint) {
        this.x = anotherPoint.x;
        this.y = anotherPoint.y;
    }

    public int getRow() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getCol() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void addToRow(int i){
        this.x+=i;
    }

    public void addToCol(int i){
        this.y+=i;
    }
}
