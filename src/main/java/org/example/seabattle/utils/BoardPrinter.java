package org.example.seabattle.utils;

import org.springframework.stereotype.Component;

@Component
public class BoardPrinter {

    public void printBoard(int[][] board) {
        System.out.println("   A B C D E F G H I J K L M N O P");
        for (int i = 1; i < board.length - 1; i++) {
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            for (int j = 1; j < board[1].length - 1; j++) {
                if (board[j][i] == 0) {
                    System.out.print("- ");
                } else if(board[j][i] == 1) {
                    System.out.print("1 ");
                } else if (board[j][i] == 2){
                    System.out.print("X ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public void printClearBoard(int[][] board) {
        for (int i = 1; i < board.length + 1; i++) {
            for (int j = 1; j < board[1].length + 1; j++) {
                board[j - 1][i - 1] = 0;
            }
        }
    }

    public void printMonitor(int[][] board) {
        System.out.println("   A B C D E F G H I J K L M N O P");
        for (int i = 1; i < board.length - 1; i++) {
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            for (int j = 1; j < board[1].length - 1; j++) {
                if (board[j - 1][i - 1] == 0) {
                    System.out.print("- ");
                } else if (board[j - 1][i - 1] == 1) {
                    System.out.print(". ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
}
