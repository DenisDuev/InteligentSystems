package com.fmi.is.h2;

public class Board {
    private int[][] board;
    private int boardDim;

    public Board(int... inputNumbers) {
        boardDim = (int) Math.sqrt(inputNumbers.length);
        int index = 0;
        this.board = new int[boardDim][];
        for (int i = 0; i < boardDim; i++) {
            this.board[i] = new int[boardDim];
            for (int j = 0; j < boardDim; j++) {
                this.board[i][j] = inputNumbers[index++];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                stringBuilder.append(this.board[i][j]).append(" ");
            }
            stringBuilder.append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}
