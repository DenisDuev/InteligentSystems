package com.fmi.is;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

import static com.fmi.is.BoardHelper.*;

public class Main {

    private static Stack<String> output = new Stack<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numberOfFrogs = Integer.parseInt(reader.readLine());
        String initialBoard = createInitialBoard(numberOfFrogs);
        dfs(initialBoard, numberOfFrogs);
        printOutput();
    }

    public static boolean dfs(String board, int indexOfEmptyLeaf) {
        if (BoardHelper.isBoardFinished(board)) {
            output.push(board);
            return true;
        }

        if (canMoveLeftFrog(board, indexOfEmptyLeaf, 1)) {
            if (dfs(moveLeft(board, indexOfEmptyLeaf, indexOfEmptyLeaf - 1), indexOfEmptyLeaf - 1)) {
                output.push(board);
                return true;
            }
        }

        if (canMoveRightFrog(board, indexOfEmptyLeaf, 1)) {
            if (dfs(moveRight(board,indexOfEmptyLeaf, indexOfEmptyLeaf + 1), indexOfEmptyLeaf + 1)) {
                output.push(board);
                return true;
            }
        }

        if (canMoveLeftFrog(board, indexOfEmptyLeaf, 2)) {
            if (dfs(moveLeft(board, indexOfEmptyLeaf, indexOfEmptyLeaf - 2), indexOfEmptyLeaf - 2)) {
                output.push(board);
                return true;
            }
        }

        if (canMoveRightFrog(board, indexOfEmptyLeaf, 2)) {
            if (dfs(moveRight(board,indexOfEmptyLeaf, indexOfEmptyLeaf + 2), indexOfEmptyLeaf + 2)) {
                output.push(board);
                return true;
            }
        }

        return false;
    }


    private static boolean canMoveRightFrog(String board, int indexOfEmptyLeaf, int steps) {
        int rightFrogIndex = indexOfEmptyLeaf + steps;
        return rightFrogIndex < board.length() && board.charAt(rightFrogIndex) != LEFT_FROG;
    }

    private static boolean canMoveLeftFrog(String board, int indexOfEmptyLeaf, int steps) {
        int leftFrogIndex = indexOfEmptyLeaf - steps;
        return leftFrogIndex >= 0 && board.charAt(leftFrogIndex) != RIGHT_FROG;
    }

    private static String moveLeft(String board, int indexOfEmptyLeaf, int leftFrogIndex) {
        StringBuilder stringBuilder = new StringBuilder(board);
        stringBuilder.setCharAt(indexOfEmptyLeaf, LEFT_FROG);
        stringBuilder.setCharAt(leftFrogIndex, EMPTY_LEAF);
        return stringBuilder.toString();
    }

    private static String moveRight(String board, int indexOfEmptyLeaf, int rightFrogIndex) {
        StringBuilder stringBuilder = new StringBuilder(board);
        stringBuilder.setCharAt(indexOfEmptyLeaf, RIGHT_FROG);
        stringBuilder.setCharAt(rightFrogIndex, EMPTY_LEAF);
        return stringBuilder.toString();
    }


    private static void printOutput() {
        while (!output.empty()){
            System.out.println(output.pop());
        }
    }
}
