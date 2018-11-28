package com.fmi.is.h5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    public static final int BOARD_SIZE = 3;
    public static final String EMPTY_PLACE = "-";
    public static final String REAL_PLAYER = "X";
    public static final String COMPUTER_PLAYER = "O";
    String[][] board;

    List<BoardState> stateList = new ArrayList<>();

    public Board() {
        this.board = initializeNewBoard();
    }

    public Board(Board board) {
        this.board = copyArray(board.board);
    }

    private String[][] copyArray(String[][] board) {
        String[][] copy = new String[BOARD_SIZE][];
        for (int i = 0; i < BOARD_SIZE; i++) {
            copy[i] = Arrays.copyOf(board[i], BOARD_SIZE);
        }

        return copy;
    }

    private static String[][] initializeNewBoard() {
        return new String[][]{
                new String[]{"-", "-", "-"},
                new String[]{"-", "-", "-"},
                new String[]{"-", "-", "-"},
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            sb.append("[ ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(this.board[i][j]).append(" ");
            }
            sb.append("]").append(System.lineSeparator());
        }
        return sb.toString();
    }

    public boolean placePlayer(int row, int col, String player) {
        if (!this.board[row][col].equals("-")) {
            return false;
        }

        this.board[row][col] = player;
        return true;
    }

    public boolean isEmpty(int row, int col) {
        return this.board[row][col].equals(EMPTY_PLACE);
    }

    public int getScore() {
        if (isPlayerWin(REAL_PLAYER)) {
            return 1;
        } else if (isPlayerWin(COMPUTER_PLAYER)) {
            return -1;
        }

        return 0;
    }

    public boolean canMakeMove() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.board[i][j].equals(EMPTY_PLACE)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isPlayerWin(String player) {
        boolean isWinning = false;
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (this.board[row][0].equals(player) && this.board[row][1].equals(player) && this.board[row][2].equals(player)) {
                isWinning = true;
            }
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            if (this.board[0][col].equals(player) && this.board[1][col].equals(player) && this.board[2][col].equals(player)) {
                isWinning = true;
            }
        }

        if ((this.board[0][0].equals(player) && this.board[1][1].equals(player) && this.board[2][2].equals(player))
                || (this.board[2][0].equals(player) && this.board[1][1].equals(player) && this.board[0][2].equals(player))) {
            isWinning = true;
        }
        return isWinning;
    }

    public void moveAI() {
        stateList.clear();
        minimax(this, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (stateList.isEmpty()) return;
        BoardState boardState = Collections.min(stateList);
        this.board[boardState.row][boardState.col] = COMPUTER_PLAYER;

    }

    private int minimax(Board boardNode, int depth, boolean isMaximizingPlayer, int alpha, int beta) {
        String player = "O";
        if (isMaximizingPlayer) {
            player = "X";
        }
        if (boardNode.isPlayerWin("X") || boardNode.isPlayerWin("O") || !boardNode.canMakeMove()) {

            return boardNode.getScore() + depth;
        }

        if (isMaximizingPlayer) {
            int bestValue = Integer.MIN_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (!boardNode.isEmpty(i, col)){
                        continue;
                    }

                    Board newBoard = new Board(boardNode);
                    newBoard.placePlayer(i, col, player);
                    int value = minimax(newBoard, depth+1, false, alpha, beta);
                    bestValue = Integer.max(value, bestValue);
                    alpha = Integer.max(alpha, bestValue);
                    if (depth == 0) {
                        stateList.add(new BoardState(i, col, value));
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            return bestValue;
        } else {
            int bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (!boardNode.isEmpty(i, col)){
                        continue;
                    }

                    Board newBoard = new Board(boardNode);
                    newBoard.placePlayer(i, col, player);
                    int value = minimax(newBoard, depth+1, true, alpha, beta);
                    bestValue = Integer.min(value, bestValue);
                    alpha = Integer.min(alpha, bestValue);
                    if (depth == 0) {
                        stateList.add(new BoardState(i, col, value));
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return bestValue;
        }

    }
}
