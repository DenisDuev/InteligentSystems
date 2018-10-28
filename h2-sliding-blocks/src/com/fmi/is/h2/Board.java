package com.fmi.is.h2;

public class Board {
    private int[][] board;
    private int boardDim;
    private int emptyX;
    private int emptyY;

    public Board(int[][] board, int emptyX, int emptyY) {
        this.board = copy(board);
        this.boardDim = (int) Math.sqrt(board.length);
        this.emptyX = emptyX;
        this.emptyY = emptyY;
    }

    public Board(Board anotherBoard) {
        this.board = copy(anotherBoard.board);
        this.boardDim = anotherBoard.boardDim;
        this.emptyX = anotherBoard.emptyX;
        this.emptyY = anotherBoard.emptyY;
    }

    public Board(int... inputNumbers) {
        boardDim = (int) Math.sqrt(inputNumbers.length);
        int index = 0;
        this.board = new int[boardDim][];
        for (int i = 0; i < boardDim; i++) {
            this.board[i] = new int[boardDim];
            for (int j = 0; j < boardDim; j++) {
                if (inputNumbers[index] == 0) {
                    this.emptyX = i;
                    this.emptyY = j;
                }
                this.board[i][j] = inputNumbers[index++];
            }
        }
    }

    public boolean moveLeft() {
        if (emptyX <= 0) {
            return false;
        }

        int value = this.board[emptyX - 1][emptyY];
        this.board[emptyX][emptyY] = value;
        emptyX--;
        this.board[emptyX][emptyY] = 0;
        return true;
    }

    public boolean moveRight() {
        if (emptyX >= boardDim - 1) {
            return false;
        }

        int value = this.board[emptyX + 1][emptyY];
        this.board[emptyX][emptyY] = value;
        emptyX++;
        this.board[emptyX][emptyY] = 0;
        return true;
    }

    public boolean moveUp() {
        if (emptyY <= 0) {
            return false;
        }

        int value = this.board[emptyX][emptyY - 1];
        this.board[emptyX][emptyY] = value;
        emptyY--;
        this.board[emptyX][emptyY] = 0;
        return true;
    }

    public boolean moveDown() {
        if (emptyY >= boardDim - 1) {
            return false;
        }

        int value = this.board[emptyX][emptyY + 1];
        this.board[emptyX][emptyY] = value;
        emptyY++;
        this.board[emptyX][emptyY] = 0;
        return true;
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

    private static int[][] copy(int[][] board) {
        int boardDim = (int) Math.sqrt(board.length);
        int[][] copyArray = new int[boardDim][];
        for (int i = 0; i < boardDim; i++) {
            copyArray[i] = new int[boardDim];
            System.arraycopy(board[i], 0, copyArray[i], 0, boardDim);
        }

        return copyArray;
    }
}
