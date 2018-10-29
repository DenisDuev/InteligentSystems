package com.fmi.is.h2;

import java.util.*;

public class Main {

    static Board board;
    static Set<Board> visited = new HashSet<>();
    static Stack<String> path = new Stack<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfEntries = scanner.nextInt();
        int[] entries = new int[numberOfEntries + 1];
        for (int i = 0; i <= numberOfEntries; i++) {
            entries[i] = scanner.nextInt();

        }
        board = new Board(entries);
        search(board.getEmptyPoint(), board);
        System.out.println(path.size());
        while (!path.empty()) {
            System.out.println(path.pop());
        }
    }

    public static boolean search(Point starting, Board board) {
        if (visited.contains(board)) {
            return false;
        }
        visited.add(board);

        if (board.getDistance(starting.getRow(), starting.getCol()) == 0) {
            return true;
        }

        TreeMap<Integer, Action> map = new TreeMap<>();
        map.put(board.getDistance(starting.getRow() - 1, starting.getCol()), Action.UP);
        map.put(board.getDistance(starting.getRow() + 1, starting.getCol()), Action.DOWN);
        map.put(board.getDistance(starting.getRow(), starting.getCol() + 1), Action.LEFT);
        map.put(board.getDistance(starting.getRow(), starting.getCol() - 1), Action.RIGHT);

        List<Map.Entry<Integer, Action>> list = new ArrayList<>(map.entrySet());
        Collections.reverse(list);
        Board newBoard = null;
        for (Map.Entry<Integer, Action> entry : list) {
            if (entry.getKey() == -1) {
                return false;
            }
            switch (entry.getValue()) {
                case UP:
                    newBoard = new Board(board);
                    if (search(new Point(starting.getRow() - 1, starting.getCol()), newBoard.moveUp())) {
                        path.push("up");
                        return true;
                    }
                    break;
                case DOWN:
                    newBoard = new Board(board);
                    if (search(new Point(starting.getRow() + 1, starting.getCol()), newBoard.moveDown())) {
                        path.push("down");
                        return true;
                    }
                    break;
                case LEFT:
                    newBoard = new Board(board);
                    if (search(new Point(starting.getRow(), starting.getCol() + 1), newBoard.moveLeft())) {
                        path.push("left");
                        return true;
                    }
                    break;
                case RIGHT:
                    newBoard = new Board(board);
                    if (search(new Point(starting.getRow(), starting.getCol() - 1), newBoard.moveRight())) {
                        path.push("right");
                        return true;
                    }
                    break;
            }
        }

        return false;
    }

    enum Action {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
