package org.example.seabattle.repository.impl;

import org.example.seabattle.repository.api.GameHistoryRepository;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class GameHistoryRepositoryImpl implements GameHistoryRepository {
    @Override
    public void writeBoardHistory(int[][] board, int fileNumber) {
        String path = "./fight/fight" + fileNumber + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write( "\n   A B C D E F G H I J K L M N O P");
            writer.newLine();
            for (int i = 1; i < board.length - 1; i++) {
                if (i < 10) {
                    writer.write(i + "  ");
                } else {
                    writer.write(i + " ");
                }
                for (int j = 1; j < board[i].length - 1; j++) {
                    if (board[j][i] == 0) {
                        writer.write("- ");
                    } else if (board[j][i] == 1) {
                        writer.write("1 ");
                    } else if (board[j][i] == 2) {
                        writer.write("X ");
                    } else {
                        writer.write(". ");
                    }
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    @Override
    public void writeAdditionalInformation(String info, int fileNumber) {
        String path = "./fight/fight" + fileNumber + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write("\n" + info);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    @Override
    public String readGameHistory(String fileName) {
        String path = "./fight/" + fileName;
        String fightInfo = "";
        try{
            fightInfo = Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return fightInfo;
    }
}
