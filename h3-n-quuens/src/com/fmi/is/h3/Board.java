package com.fmi.is.h3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[][] board;
    private int dimensionSize;

    public Board(int dimensionSize) {
        this.dimensionSize = dimensionSize;
        initBoard();
        placeInitialQueens();
    }

    private void placeInitialQueens() {
        for (int col = 0; col < dimensionSize; col++) {
            List<Point> possiblePoints = new ArrayList<>(0);
            int minConflicts = Integer.MAX_VALUE;
            for (int row = 0; row < dimensionSize; row++) {
                if (minConflicts > this.board[row][col] && minConflicts != -1) {
                    minConflicts = this.board[row][col];
                    possiblePoints = new ArrayList<>();
                }

                if (minConflicts == this.board[row][col]){
                    possiblePoints.add(new Point(row, col));
                }
            }

            int randIndexForQueenPoint = (int) (Math.random()*possiblePoints.size());
            Point point = possiblePoints.get(randIndexForQueenPoint);
            this.board[point.row][point.col] = -1;
            precalculateConflicts(point.row, point.col);
        }
    }

    private void precalculateConflicts(int row, int col) {
        //add to row
        for (int i = 0; i < dimensionSize; i++) {
            increaseValueIfNotQueen(row, i);
        }

        //add to col
        for (int i = 0; i < dimensionSize; i++) {
            increaseValueIfNotQueen(i, col);
        }

        //add to up-down diagonal
        for (int tRow = 0, tCol = Math.abs(col - row); tCol < dimensionSize && tRow < dimensionSize; tRow++, tCol++) {
            increaseValueIfNotQueen(tRow, tCol);
        }

        //add to down-up diagonal
        for (int tRow = Math.min(row + col, row), tCol = 0; tRow > 0 && tCol < dimensionSize; tRow--, tCol++) {
            increaseValueIfNotQueen(tRow, tCol);
        }
    }

    private void increaseValueIfNotQueen(int row, int col) {
        if (this.board[row][col] != -1) {
            this.board[row][col] += 1;
        }
    }

    private void initBoard() {
        this.board = new int[dimensionSize][];
        for (int row = 0; row < dimensionSize; row++) {
            this.board[row] = new int[dimensionSize];
            for (int col = 0; col < dimensionSize; col++) {
                this.board[row][col] = 0;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimensionSize; i++) {
            sb.append(Arrays.toString(this.board[i])).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
