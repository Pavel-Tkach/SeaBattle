package org.example.seabattle.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.repositories.api.AdminRepository;
import org.example.seabattle.repositories.api.GameHistoryRepository;
import org.example.seabattle.services.api.AdminService;
import org.example.seabattle.utils.ConsoleUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final GameHistoryRepository gameHistoryRepository;

    private final ConsoleUtils consoleUtils;

    @Override
    public void checkGame(Scanner scanner) {
        List<String> files = getAllFiles();
        printFiles(files);
        String fileName = consoleUtils.inputFileName(scanner);
        if (files.contains(fileName)) {
            String fightHistory = gameHistoryRepository.readGameHistory(fileName);
            String[] fightBoards = fightHistory.split("-".repeat(35));
            for (int i = 1; i < fightBoards.length - 1; i++) {
                System.out.println(fightBoards[i]);
                scanner.nextLine();
            }
        }
    }

    @Override
    public void deleteGame(Scanner scanner) {
        List<String> files = getAllFiles();
        printFiles(files);
        String fileName = consoleUtils.inputFileName(scanner);
        if (files.contains(fileName)) {
            adminRepository.deleteGame(fileName);
        }
    }

    @Override
    public void archiveGame(Scanner scanner) {
        List<String> files = getAllFiles();
        printFiles(files);
        String fileName = consoleUtils.inputFileName(scanner);
        if (files.contains(fileName)) {
            adminRepository.archiveGame(fileName);
        }
    }

    public List<String> getAllFiles() {
        List<String> fileNames = new ArrayList<>();
        File directory = new File("./fight");
        File[] files = directory.listFiles();
        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            fileNames.add(files[i].getName());
        }
        return fileNames;
    }

    public void printFiles(List<String> files) {
        for (String file : files) {
            System.out.println(file);
        }
    }
}
