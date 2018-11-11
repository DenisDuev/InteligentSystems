package com.fmi.is.h3;

import java.util.ArrayList;
import java.util.Random;

public class QueenBoard {
  private Random random = new Random();

  private int[] rowsArray;
  private int numberOfQueens;

  public QueenBoard(int numberOfQueens) {
    rowsArray = new int[numberOfQueens];
    this.numberOfQueens = numberOfQueens;
    initBoardWithQueens();
  }

  private void initBoardWithQueens() {
    for (int row = 0, n = numberOfQueens; row < n; row++) {
      rowsArray[row] = row;
    }
    for (int row = 0, n = numberOfQueens; row < n; row++) {
      int randomIndex = random.nextInt(n);
      int rowToSwap = rowsArray[row];
      rowsArray[row] = rowsArray[randomIndex];
      rowsArray[randomIndex] = rowToSwap;
    }
  }

  private int conflictsWithQueens(int row, int col) {
    int conflicts = 0;
    for (int tCol = 0; tCol < numberOfQueens; tCol++) {
      if (tCol == col) {
        continue;
      }
      int tRow = rowsArray[tCol];
      if (tRow == row || Math.abs(tRow - row) == Math.abs(tCol - col)) {
        conflicts++;
      }
    }
    return conflicts;
  }

  void solve() {

    ArrayList<Integer> candidates = new ArrayList<>();

    for (int numberOfMoves = 0; ; numberOfMoves++) {

      int maxConflicts = findWorstQueens(candidates);
      if (maxConflicts == 0) {
        return;
      }

      int worstQueenColumn = candidates.get(random.nextInt(candidates.size()));
      findBestPlacesToSwapQueen(candidates, worstQueenColumn);

      if (!candidates.isEmpty()) {
        rowsArray[worstQueenColumn] = candidates.get(random.nextInt(candidates.size()));
      }

      if (numberOfMoves == numberOfQueens * 2) {
        initBoardWithQueens();
        numberOfMoves = 0;
      }
    }
  }

    private void findBestPlacesToSwapQueen(ArrayList<Integer> candidates, int worstQueenColumn) {
        int minConflicts = numberOfQueens;
        candidates.clear();
        for (int tRow = 0; tRow < numberOfQueens; tRow++) {
          int conflicts = conflictsWithQueens(tRow, worstQueenColumn);
          if (conflicts < minConflicts) {
            minConflicts = conflicts;
            candidates.clear();
          }

          if (conflicts == minConflicts) {
            candidates.add(tRow);
          }
        }
    }

    private int findWorstQueens(ArrayList<Integer> candidates) {
    int maxConflicts = 0;
    candidates.clear();
    for (int tCol = 0; tCol < numberOfQueens; tCol++) {
      int conflicts = conflictsWithQueens(rowsArray[tCol], tCol);
      if (conflicts == maxConflicts) {
        candidates.add(tCol);
      } else if (conflicts > maxConflicts) {
        maxConflicts = conflicts;
        candidates.clear();
        candidates.add(tCol);
      }
    }
    return maxConflicts;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < numberOfQueens; row++) {
      for (int col = 0; col < numberOfQueens; col++) {
        sb.append(rowsArray[col] == row ? "* " : "_ ");
      }
      sb.append(System.lineSeparator());
    }

    return sb.toString();
  }
}
