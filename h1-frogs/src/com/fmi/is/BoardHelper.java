package com.fmi.is;

public class BoardHelper {
    public static final char LEFT_FROG = '>';
    public static final char EMPTY_LEAF = '_';
    public static final char RIGHT_FROG = '<';

    public static String createInitialBoard(int size) {
        StringBuilder board = new StringBuilder(2 * size + 1);
        for (int i = 0; i < size; i++) {
            board.append(LEFT_FROG);
        }

        board.append(EMPTY_LEAF);
        for (int i = size + 1; i < size * 2 + 1; i++) {
            board.append(RIGHT_FROG);
        }

        return board.toString();
    }

    public static boolean isBoardFinished(String board) {
        int middleIndex = board.length() / 2;
        for (int i = 0; i < middleIndex; i++) {
            if (board.charAt(i) == LEFT_FROG) {
                return false;
            }
        }

        return board.charAt(middleIndex) == EMPTY_LEAF;
    }
}
