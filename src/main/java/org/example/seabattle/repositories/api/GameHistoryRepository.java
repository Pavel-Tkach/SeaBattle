package org.example.seabattle.repositories.api;

public interface GameHistoryRepository {

    void writeBoardHistory(int[][] board, int fileNumber);

    void writeAdditionalInformation(String info, int fileNumber);

    String readGameHistory(String fileName);
}
