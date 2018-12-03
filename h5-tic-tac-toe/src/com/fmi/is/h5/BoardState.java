package com.fmi.is.h5;

public class BoardState implements Comparable<BoardState>{
    public int row;
    public int col;
    public int value;

    public BoardState(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    @Override
    public int compareTo(BoardState o) {
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "BoardState{" +
                "row=" + row +
                ", col=" + col +
                ", value=" + value +
                '}';
    }
}
