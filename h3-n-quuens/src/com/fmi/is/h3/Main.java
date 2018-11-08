package com.fmi.is.h3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        int numberOfQueens = readNumberOfQueens();
        Board board = new Board(numberOfQueens);
        System.out.println(board);
        for (int i = 0; i < 50000; i++) {

            int conflicts = board.isValid();
            if (conflicts == 0) {

                System.out.println("solved");
                System.out.println(board);
                return;
            }
            board.moveToSolve();
            System.out.println("Number of conflicts: " + conflicts);
            System.out.println(board);
        }
        System.out.println("cannot be solved");
    }

    private static int readNumberOfQueens() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(bufferedReader.readLine());
    }
}
