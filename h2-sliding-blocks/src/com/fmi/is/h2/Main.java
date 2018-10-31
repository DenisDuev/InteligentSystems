package com.fmi.is.h2;

import java.util.*;

public class Main {

    static Board board;
    static Set<String> visited = new HashSet<>();
    static Stack<String> path = new Stack<>();
    static TreeMap<Integer, Action> map = new TreeMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfEntries = scanner.nextInt();
        int[] entries = new int[numberOfEntries + 1];
        for (int i = 0; i <= numberOfEntries; i++) {
            entries[i] = scanner.nextInt();

        }
        board = new Board(entries);
        search(board.getEmptyPoint(), board, 0);
        System.out.println(path.size());
        while (!path.empty()) {
            System.out.println(path.pop());
        }
    }

    public static boolean search(Point starting, Board board, int distance) {
        if (visited.contains(board.toString())) {
            return false;
        }
        visited.add(board.toString());

        if (board.getDistanceSum() == 0) {
            return true;
        }

        Board upBoard = new Board(board);
        upBoard.moveUp();
        map.put(upBoard.getDistanceSum() + distance, Action.UP);
        Board downBoard = new Board(board);
        downBoard.moveDown();
        map.put(downBoard.getDistanceSum() + distance, Action.DOWN);
        Board leftBoard = new Board(board);
        leftBoard.moveLeft();
        map.put(leftBoard.getDistanceSum() + distance, Action.LEFT);
        Board rightBoard = new Board(board);
        rightBoard.moveRight();
        map.put(rightBoard.getDistanceSum() + distance, Action.RIGHT);

        while (map.size() > 0){
            Map.Entry<Integer, Action> entry = map.pollFirstEntry();
            if (entry.getKey() == -1) {
                return false;
            }
            switch (entry.getValue()) {
                case UP:
                    if (search(new Point(starting.getRow() - 1, starting.getCol()), upBoard, distance + 1)) {
                        path.push("up");
                        return true;
                    }
                    break;
                case DOWN:
                    if (search(new Point(starting.getRow() + 1, starting.getCol()), downBoard, distance + 1)) {
                        path.push("down");
                        return true;
                    }
                    break;
                case LEFT:
                    if (search(new Point(starting.getRow(), starting.getCol() + 1), leftBoard, distance + 1)) {
                        path.push("right");
                        return true;
                    }
                    break;
                case RIGHT:
                    if (search(new Point(starting.getRow(), starting.getCol() - 1), rightBoard, distance + 1)) {
                        path.push("left");
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
