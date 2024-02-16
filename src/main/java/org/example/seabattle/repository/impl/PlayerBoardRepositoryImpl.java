package org.example.seabattle.repository.impl;

import org.example.seabattle.repository.api.PlayerBoardRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerBoardRepositoryImpl implements PlayerBoardRepository {


    @Override
    public void writePlayerBoard(int[][] board, String fileName) {
        String path = "./board/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 1; i < board.length - 1; i++) {
                for (int j = 1; j < board[1].length - 1; j++) {
                    writer.write(String.valueOf(board[j][i]));
                }
                writer.write("\r\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    @Override
    public void writeMonitor(int[][] monitor, String fileName) {
        String path = "./board/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (int i = 1; i < monitor.length - 1; i++) {
                for (int j = 1; j < monitor[1].length - 1; j++) {
                    writer.write(String.valueOf(monitor[j - 1][i - 1]));
                }
                writer.write("\r\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    @Override
    public int[][] readPlayerBoard(String fileName) {
        String path = "./board/" + fileName;
        int[][] board = new int[18][18];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); i++) {
                String row = lines.get(i);
                for (int j = 0; j < row.length(); j++) {
                    board[j + 1][i + 1] = Character.getNumericValue(row.charAt(j));
                }
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return board;
    }

    @Override
    public int[][] readMonitor(String fileName) {
        String path = "./board/" + fileName;
        int[][] board = new int[18][18];
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            for (int i = 0; i < lines.size(); i++) {
                String row = lines.get(i);
                for (int j = 0; j < row.length(); j++) {
                    board[j][i] = Character.getNumericValue(row.charAt(j));
                }
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return board;
    }

    @Override
    public void clearBoardFile(String fileName) {
        String path = "./board/" + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
