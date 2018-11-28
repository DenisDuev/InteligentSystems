package com.fmi.is.h5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static final boolean START_WITH_PLAYER = true;
    private static BufferedReader reader;
    private static Board board;

    public static void main(String[] args) throws IOException {
        board = new Board();
        reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            if (START_WITH_PLAYER) {
                movePlayer();
                System.out.println(board);
                if (board.isPlayerWin("X")) {
                    System.out.println("You win");
                    break;
                }

                board.moveAI();
                System.out.println(board);
                if (board.isPlayerWin("O")){
                    System.out.println("You loose");
                    break;
                }
            } else {
                board.moveAI();
                System.out.println(board);
                if (board.isPlayerWin("O")){
                    System.out.println("You loose");
                    break;
                }
                movePlayer();
                System.out.println(board);
                if (board.isPlayerWin("X")) {
                    System.out.println("You win");
                    break;
                }
            }
        }
    }

    private static void movePlayer() throws IOException {
        while (true) {
            int[] moves = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            if (board.placePlayer(moves[0],moves[1], "X")) {
                break;
            } else {
                System.out.println("enter valid coordinates");
            }
        }
    }
}
