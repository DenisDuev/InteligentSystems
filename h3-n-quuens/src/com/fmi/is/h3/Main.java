package com.fmi.is.h3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Main {

    public static final int MAX_NUMBER_OF_TRIES = 500000;

    public static void main(String[] args) throws IOException {
        int numberOfQueens = readNumberOfQueens();
        Board board = new Board(numberOfQueens);
        for (int i = 0; i < MAX_NUMBER_OF_TRIES; i++) {

            int conflicts = board.isValid();
            if (conflicts == 0) {

                System.out.println("solved");
                try (PrintWriter out = new PrintWriter("results.txt")) {
                    out.println(board.toStringPrettyPrint());
                }
                //System.out.println(board.toStringPrettyPrint());
                return;
            }
            board.moveToSolve();

        }
        System.out.println("cannot be solved");
    }

    private static int readNumberOfQueens() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(bufferedReader.readLine());
    }
}
