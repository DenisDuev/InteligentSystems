package com.fmi.is.h2;

public class ManhattanDistance {
    public static int calculateDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}
