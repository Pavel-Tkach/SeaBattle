package org.example.seabattle.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.models.BoardContext;
import org.example.seabattle.models.Player;
import org.example.seabattle.repositories.api.GameHistoryRepository;
import org.example.seabattle.repositories.api.PlayerBoardRepository;
import org.example.seabattle.services.api.BattleshipMultiGameService;
import org.example.seabattle.services.api.ShipPlacementService;
import org.example.seabattle.utils.ConsoleUtils;
import org.example.seabattle.utils.CoordinateConverter;
import org.example.seabattle.utils.PlayerBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class BattleshipMultiGameServiceImpl implements BattleshipMultiGameService {

    private final ShipPlacementService shipPlacementService;

    private final PlayerBuilder playerBuilder;

    private final CoordinateConverter coordinateConverter;

    private final PlayerBoardRepository playerBoardRepository;

    private final GameHistoryRepository gameHistoryRepository;

    private final ConsoleUtils consoleUtils;

    @Override
    public void configureAndRunGame(Scanner scanner, int gameNumber) {
        Map<String, Integer> shipsOfFirstPlayer = new HashMap<>();
        Map<String, Integer> shipsOfSecondPlayer = new HashMap<>();
        LocalTime startTime = LocalTime.now();
        BoardContext boardContext = new BoardContext();
        int[][] battlefield1 = boardContext.getBattlefield1();
        int[][] battlefield2 = boardContext.getBattlefield2();
        Player firstPlayer = playerBuilder.createPlayer(scanner);
        if(consoleUtils.shouldAutoFillBoard(scanner)) {
            shipPlacementService.placeShipsAuto(firstPlayer, battlefield1, scanner, shipsOfFirstPlayer);
        } else {
            shipPlacementService.placeShipsManually(firstPlayer, battlefield1, scanner, shipsOfFirstPlayer);
        }
        playerBoardRepository.writePlayerBoard(battlefield1, "battlefield1.txt");
        Player secondPlayer = playerBuilder.createPlayer(scanner);
        if(consoleUtils.shouldAutoFillBoard(scanner)) {
            shipPlacementService.placeShipsAuto(secondPlayer, battlefield2, scanner, shipsOfSecondPlayer);
        } else {
            shipPlacementService.placeShipsManually(secondPlayer, battlefield2, scanner, shipsOfSecondPlayer);
        }
        playerBoardRepository.writePlayerBoard(battlefield2, "battlefield2.txt");
        runGame(firstPlayer, secondPlayer, scanner, gameNumber, shipsOfFirstPlayer, shipsOfSecondPlayer);
        gameHistoryRepository.writeAdditionalInformation("-".repeat(35), gameNumber);
        saveStartAndEndTimeOfBattle(startTime, gameNumber);
    }

    public void runGame(Player firstPlayer,
                        Player secondPlayer,
                        Scanner scanner,
                        int gameNumber,
                        Map<String, Integer> shipsOfFirstPlayer,
                        Map<String, Integer> shipsOfSecondPlayer
    ) {
        while (true) {
            int[][] playerBoard1 = playerBoardRepository.readPlayerBoard("battlefield1.txt");
            int[][] playerBoard2 = playerBoardRepository.readPlayerBoard("battlefield2.txt");
            int[][] playerMonitor1 = playerBoardRepository.readMonitor("monitor1.txt");
            executePlayerTurn(firstPlayer, playerBoard1, playerMonitor1, playerBoard2, scanner, gameNumber, shipsOfSecondPlayer);
            playerBoardRepository.writePlayerBoard(playerBoard2, "battlefield2.txt");
            playerBoardRepository.writeMonitor(playerMonitor1, "monitor1.txt");
            int[][] monitor1 = playerBoardRepository.readMonitor("monitor1.txt");
            int[][] monitor2 = playerBoardRepository.readMonitor("monitor2.txt");
            if (isWinCondition(firstPlayer, monitor1, monitor2, gameNumber)) {
                break;
            }
            int[][] board1 = playerBoardRepository.readPlayerBoard("battlefield1.txt");
            int[][] board2 = playerBoardRepository.readPlayerBoard("battlefield2.txt");
            int[][] playerMonitor2 = playerBoardRepository.readMonitor("monitor2.txt");
            executePlayerTurn(secondPlayer, board2, playerMonitor2, board1, scanner, gameNumber, shipsOfFirstPlayer);
            playerBoardRepository.writePlayerBoard(board1, "battlefield1.txt");
            playerBoardRepository.writeMonitor(playerMonitor2, "monitor2.txt");
            int[][] monitor11 = playerBoardRepository.readMonitor("monitor1.txt");
            int[][] monitor22 = playerBoardRepository.readMonitor("monitor2.txt");
            if (isWinCondition(secondPlayer, monitor11, monitor22, gameNumber)) {
                break;
            }
        }
    }


    @Override
    public void endGame() {
        playerBoardRepository.clearBoardFile("battlefield1.txt");
        playerBoardRepository.clearBoardFile("battlefield2.txt");
        playerBoardRepository.clearBoardFile("monitor1.txt");
        playerBoardRepository.clearBoardFile("monitor2.txt");
    }

    public void executePlayerTurn(Player player,
                                  int[][] playerBoard,
                                  int[][] monitor,
                                  int[][] enemyBoard, Scanner scanner,
                                  int gameNumber,
                                  Map<String, Integer> ships
    ) {
        int x = 0;
        int y = 0;
        while (true) {
            String coordinates = consoleUtils.inputCoordinateToMakeTurn(playerBoard, player, monitor, scanner);
            gameHistoryRepository.writeAdditionalInformation("-".repeat(35), gameNumber);
            String playerTurnInfo = player.getNickname() + " turn and coordinate: " + coordinates + ", Time: " + LocalTime.now();
            gameHistoryRepository.writeAdditionalInformation(playerTurnInfo, gameNumber);
            String coordinateOfX = String.valueOf(coordinates.toUpperCase().charAt(0));
            String coordinateOfY = "";
            if (coordinates.trim().length() == 3) {
                coordinateOfY = coordinates.substring(1);
            } else {
                coordinateOfY = String.valueOf(coordinates.charAt(1));
            }
            x = coordinateConverter.convertCoordinate(coordinateOfX);
            y = coordinateConverter.convertCoordinate(coordinateOfY);
            if (enemyBoard[x][y] == 1) {
                consoleUtils.hitMessage();
                if (isShipDefeated(coordinates, ships)) {
                    consoleUtils.shipDefeatedMessage();
                }
                monitor[x - 1][y - 1] = 2;
                enemyBoard[x][y] = 2;
                gameHistoryRepository.writeBoardHistory(enemyBoard, gameNumber);
            } else {
                consoleUtils.missMessage();
                monitor[x - 1][y - 1] = 1;
                enemyBoard[x][y] = 3;
                gameHistoryRepository.writeBoardHistory(enemyBoard, gameNumber);
                break;
            }
        }
    }

    public boolean isWinCondition(Player player, int[][] monitor1, int[][] monitor2, int gameNumber) {
        int numberHitsFirstPlayer = 0;
        for (int[] row : monitor1) {
            for (int cell : row) {
                if (cell == 2) {
                    numberHitsFirstPlayer++;
                }
            }
        }
        int numberHitsSecondPlayer = 0;
        for (int[] row : monitor2) {
            for (int cell : row) {
                if (cell == 2) {
                    numberHitsSecondPlayer++;
                }
            }
        }
        if (numberHitsFirstPlayer == 56) {
            saveMultiGameWinnerInfo(player, gameNumber);
            consoleUtils.showWinnerName(player);
            return true;
        }
        if (numberHitsSecondPlayer == 56) {
            saveMultiGameWinnerInfo(player, gameNumber);
            consoleUtils.showWinnerName(player);
            return true;
        }
        return false;
    }

    public boolean isShipDefeated(String coordinates, Map<String, Integer> ships) {
        for (String key : ships.keySet()) {
            if (key.contains(coordinates)) {
                Integer amountShipDeck = ships.get(key);
                int decrementedMountainside = amountShipDeck - 1;
                ships.put(key, decrementedMountainside);
                if (decrementedMountainside == 0) {
                    ships.remove(key);
                    return true;
                }
            }
        }
        return false;
    }

    public void saveMultiGameWinnerInfo(Player player, int gameNumber) {
        String winnerName = player.getNickname();
        String winnerMessage = "\nWinner is " + winnerName;
        gameHistoryRepository.writeAdditionalInformation(winnerMessage, gameNumber);
        int amountPlayerMoves = player.getPlayerTurns().size() - 1;
        String amountPlayerMovesMessage = winnerName + "'s turns = " + amountPlayerMoves;
        gameHistoryRepository.writeAdditionalInformation(amountPlayerMovesMessage, gameNumber);
    }
    
    public void saveStartAndEndTimeOfBattle(LocalTime startTime, int gameNumber) {
        LocalTime endTime = LocalTime.now();
        String formattedStartTime = String.format("%02d:%02d:%02d",
                startTime.getHour(), startTime.getMinute(), startTime.getSecond());
        String formattedEndTime = String.format("%02d:%02d:%02d",
                endTime.getHour(), endTime.getMinute(), endTime.getSecond());
        String battleStartMessage = "The battle began at " + formattedStartTime;
        String battleEndMessage = "The battle ended at " + formattedEndTime;
        gameHistoryRepository.writeAdditionalInformation(battleStartMessage, gameNumber);
        gameHistoryRepository.writeAdditionalInformation(battleEndMessage, gameNumber);
    }
}