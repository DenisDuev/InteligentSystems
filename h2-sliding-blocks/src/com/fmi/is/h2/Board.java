package com.fmi.is.h2;

import java.util.Arrays;

public class Board {
    private int[][] board;
    private int[][] distance;
    private int boardDim;
    private Point emptyPoint;

    public Board(int[][] board, Point emptyPoint) {
        this.board = copy(board);
        this.boardDim = (int) Math.sqrt(board.length);
        this.emptyPoint = new Point(emptyPoint.getRow(), emptyPoint.getCol());
        calculateDistance();
    }

    public Board(Board anotherBoard) {
        this.board = copy(anotherBoard.board);
        this.boardDim = anotherBoard.boardDim;
        this.emptyPoint = new Point(anotherBoard.emptyPoint);
        this.distance = copy(anotherBoard.distance);
    }

    public Board(int... inputNumbers) {
        boardDim = (int) Math.sqrt(inputNumbers.length);
        int index = 0;
        this.board = new int[boardDim][];
        for (int i = 0; i < boardDim; i++) {
            this.board[i] = new int[boardDim];
            for (int j = 0; j < boardDim; j++) {
                if (inputNumbers[index] == 0) {
                    this.emptyPoint = new Point(i,j);
                }
                this.board[i][j] = inputNumbers[index++];
            }
        }
        calculateDistance();
    }

    public Board moveUp() {
        if (this.emptyPoint.getRow() <= 0) {
            return null;
        }

        int value = this.board[this.emptyPoint.getRow() - 1][this.emptyPoint.getCol()];
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = value;
        this.emptyPoint.addToRow(-1);
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = 0;
        calculateDistance();
        return this;
    }

    public Board moveDown() {
        if (this.emptyPoint.getRow() >= boardDim - 1) {
            return null;
        }

        int value = this.board[this.emptyPoint.getRow() + 1][this.emptyPoint.getCol()];
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = value;
        this.emptyPoint.addToRow(1);
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = 0;
        calculateDistance();
        return this;
    }

    public Board moveRight() {
        if (this.emptyPoint.getCol() <= 0) {
            return null;
        }

        int value = this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol() - 1];
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = value;
        this.emptyPoint.addToCol(-1);
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = 0;
        calculateDistance();
        return this;
    }

    public Board moveLeft() {
        if (this.emptyPoint.getCol() >= boardDim - 1) {
            return null;
        }

        int value = this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol() + 1];
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = value;
        this.emptyPoint.addToCol(1);
        this.board[this.emptyPoint.getRow()][this.emptyPoint.getCol()] = 0;
        calculateDistance();
        return this;
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
        int boardDim = board.length;
        int[][] copyArray = new int[boardDim][];
        for (int i = 0; i < boardDim; i++) {
            copyArray[i] = new int[boardDim];
            System.arraycopy(board[i], 0, copyArray[i], 0, boardDim);
        }

        return copyArray;
    }

    public void calculateDistance() {
        this.distance = new int[boardDim][];
        for (int row = 0; row < boardDim; row++) {
            this.distance[row] = new int[boardDim];
            for (int col = 0; col < boardDim; col++) {
                int value = this.board[row][col];
                if (value == 0){
                    this.distance[row][col] = ManhattanDistance.calculateDistance(row, col, boardDim - 1, boardDim - 1);
                    continue;
                }
                int wantedValueCol = (value % boardDim) == 0 ? boardDim - 1 : (value % boardDim) - 1 ;
                int wantedValueRow = (value / boardDim) == row ? (value / boardDim) : (value / boardDim) - 1;

                this.distance[row][col] = ManhattanDistance.calculateDistance(row, col, wantedValueRow, wantedValueCol);
            }
        }
    }

    public Point getEmptyPoint() {
        return emptyPoint;
    }

    public int getDistanceSum() {
        int sum = 0;
        for (int i = 0; i < boardDim; i++) {
            for (int j = 0; j < boardDim; j++) {
                sum += this.distance[i][j];
            }
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        if (boardDim != board1.boardDim) return false;
        if (!Arrays.deepEquals(board, board1.board)) return false;
        if (!Arrays.deepEquals(distance, board1.distance)) return false;
        return emptyPoint.equals(board1.emptyPoint);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(board);
        result = 31 * result + Arrays.deepHashCode(distance);
        result = 31 * result + boardDim;
        result = 31 * result + emptyPoint.hashCode();
        return result;
    }
}
