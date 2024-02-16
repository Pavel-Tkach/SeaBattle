package org.example.seabattle.repository.api;

public interface GameHistoryRepository {

    void writeBoardHistory(int[][] board, int fileNumber);

    void writeAdditionalInformation(String info, int fileNumber);

    String readGameHistory(String fileName);
}
