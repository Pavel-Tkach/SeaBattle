package org.example.seabattle.repository.api;

public interface PlayerBoardRepository {

    void writePlayerBoard(int[][] board, String fileName);
    void writeMonitor(int[][] monitor, String fileName);

    int[][] readPlayerBoard(String fileName);
    int[][] readMonitor(String fileName);

    void clearBoardFile(String fileName);
}
