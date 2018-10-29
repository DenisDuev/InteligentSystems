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

        if (board.getDistanceSum() == 0) {
            return true;
        }

        TreeMap<Integer, Action> map = new TreeMap<>();
        Board upBoard = new Board(board);
        upBoard.moveUp();
        map.put(upBoard.getDistanceSum(), Action.UP);
        Board downBoard= new Board(board);
        downBoard.moveDown();
        map.put(downBoard.getDistanceSum(), Action.DOWN);
        Board leftBoard= new Board(board);
        leftBoard.moveLeft();
        map.put(leftBoard.getDistanceSum(), Action.LEFT);
        Board rightBoard= new Board(board);
        rightBoard.moveRight();
        map.put(rightBoard.getDistanceSum(), Action.RIGHT);

        List<Map.Entry<Integer, Action>> list = new ArrayList<>(map.entrySet());
        for (Map.Entry<Integer, Action> entry : list) {
            if (entry.getKey() == -1) {
                return false;
            }
            switch (entry.getValue()) {
                case UP:
                    if (search(new Point(starting.getRow() - 1, starting.getCol()), upBoard)) {
                        path.push("up");
                        return true;
                    }
                    break;
                case DOWN:
                    if (search(new Point(starting.getRow() + 1, starting.getCol()), downBoard)) {
                        path.push("down");
                        return true;
                    }
                    break;
                case LEFT:
                    if (search(new Point(starting.getRow(), starting.getCol() + 1), leftBoard)) {
                        path.push("left");
                        return true;
                    }
                    break;
                case RIGHT:
                    if (search(new Point(starting.getRow(), starting.getCol() - 1), rightBoard)) {
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
