package com.fmi.is.h3;

public class Point {
    int row;
    int col;
    int conflicts;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
        this.conflicts = 0;
    }

    public Point(int row, int col, int conflicts) {
        this.row = row;
        this.col = col;
        this.conflicts = conflicts;
    }
}
