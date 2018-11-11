package com.fmi.is.h3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueensSolver {

  public static void main(String[] args) throws IOException {
    int numberOfQueens = readNumberOfQueens();
    QueenBoard board = new QueenBoard(numberOfQueens);

    board.solve();
    System.out.println(board);
  }

  private static int readNumberOfQueens() throws IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    return Integer.parseInt(bufferedReader.readLine());
  }
}
