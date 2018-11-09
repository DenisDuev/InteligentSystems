package com.fmi.is.h3;

import java.util.*;

public class Board {
    private int[][] board;
    private List<Point> queens = new ArrayList<>();
    private int dimensionSize;

    public Board(int dimensionSize) {
        this.dimensionSize = dimensionSize;
        initBoard();
        placeInitialQueens();
    }

    private void placeInitialQueens() {
        HashSet<Integer> placedRows = new HashSet<>();
        for (int col = 0; col < dimensionSize; col++) {
            List<Point> possiblePoints = new ArrayList<>(1);
            int minConflicts = Integer.MAX_VALUE;
            for (int row = 0; row < dimensionSize; row++) {
                if (placedRows.contains(row)) {
                    continue;
                }
                if (minConflicts > this.board[row][col] && minConflicts != -1) {
                    minConflicts = this.board[row][col];
                    possiblePoints = new ArrayList<>();
                }

                if (minConflicts == this.board[row][col]) {
                    possiblePoints.add(new Point(row, col));
                }
            }

            int randIndexForQueenPoint = (int) (Math.random() * possiblePoints.size());
            Point point = possiblePoints.get(randIndexForQueenPoint);
            this.board[point.row][point.col] = -1;
            queens.add(point);
            placedRows.add(point.row);
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
        for (int tRow = 0, tCol = col - row; tCol < dimensionSize && tRow < dimensionSize; tRow++, tCol++) {
            if (tCol < 0) {
                continue;
            }
            increaseValueIfNotQueen(tRow, tCol);
        }

        //add to down-up diagonal
        for (int tRow = row + col, tCol = 0; tRow >= 0 && tCol < dimensionSize; tRow--, tCol++) {
            if (tRow >= dimensionSize) {
                continue;
            }
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

    public int isValid() {
        int countOfConflicts = 0;
        for (int col = 0; col < dimensionSize; col++) {
            final int a = col;
            int row = queens.stream().filter(e -> e.col == a).findFirst().get().row;

            countOfConflicts += conflictsForQueen(row, col);
        }
        return countOfConflicts;
    }

    private int conflictsForQueen(int row, int col) {
        int countOfConflicts = -4;
        for (int i = 0; i < dimensionSize; i++) {
            if (this.board[row][i] == -1) {
                countOfConflicts++;
                break;
            }
        }

        //add to col
        for (int i = 0; i < dimensionSize; i++) {
            if (this.board[i][col] == -1) {
                countOfConflicts++;
                break;
            }
        }

        //add to up-down diagonal
        for (int tRow = 0, tCol = col - row; tCol < dimensionSize && tRow < dimensionSize; tRow++, tCol++) {
            if (tCol < 0) {
                continue;
            }
            if (this.board[tRow][tCol] == -1) {
                countOfConflicts++;
                break;
            }
        }

        //add to down-up diagonal
        for (int tRow = row + col, tCol = 0; tRow >= 0 && tCol < dimensionSize; tRow--, tCol++) {
            if (tRow >= dimensionSize) {
                continue;
            }
            if (this.board[tRow][tCol] == -1) {
                countOfConflicts++;
                break;
            }
        }
        return countOfConflicts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimensionSize; i++) {
            sb.append(Arrays.toString(this.board[i])).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public String toStringPrettyPrint() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < dimensionSize; row++) {
            for (int col = 0; col < dimensionSize; col++) {
                sb.append(this.board[row][col] == -1 ? "*" : "_").append(" ");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public void moveToSolve() {
        for (int i = 0; i < queens.size(); i++) {
            Point queen = queens.get(i);
            queen.conflicts = conflictsForQueen(queen.row, queen.col);
        }

        //find worst queens
        queens.sort(Comparator.comparingInt(e -> -e.conflicts));
        List<Point> possible = new ArrayList<>();
        int maxConflict = queens.get(0).conflicts;
        for (Point queen : queens) {
            if (maxConflict != queen.conflicts) {
                break;
            }
            possible.add(queen);
        }

        //get one random queen
        int randomIndex = (int) (Math.random() * possible.size());
        Point changeQueen = possible.get(randomIndex);

        //get best row
        List<Point> bestPoints = new ArrayList<>();
        Point bestPoint = new Point(changeQueen.row, changeQueen.col, Integer.MAX_VALUE);
        for (int row = 0; row < dimensionSize; row++) {
            if (bestPoint.conflicts > this.board[row][changeQueen.col] && this.board[row][changeQueen.col] != -1) {
                bestPoint = new Point(row, changeQueen.col, this.board[row][changeQueen.col]);
                bestPoints = new ArrayList<>();
            }

            if (bestPoint.conflicts == this.board[row][changeQueen.col]) {
                bestPoints.add(bestPoint);
            }
        }

        int randPlaceIndex = (int) (Math.random() * bestPoints.size());
        Point bestChangePoint = bestPoints.get(randPlaceIndex);

       //make queen a point
        this.board[changeQueen.row][changeQueen.col] = changeQueen.conflicts + 1;

        //move queen
        this.board[bestChangePoint.row][bestChangePoint.col] = -1;
        changeQueen.row = bestChangePoint.row;
        changeQueen.col = bestChangePoint.col;
        changeQueen.conflicts = conflictsForQueen(changeQueen.row, changeQueen.col);
    }
}
