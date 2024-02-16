package org.example.seabattle.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.models.Player;
import org.example.seabattle.services.api.BattleshipSoloGameService;
import org.example.seabattle.services.api.ShipPlacementService;
import org.example.seabattle.utils.ConsoleUtils;
import org.example.seabattle.utils.CoordinateConverter;
import org.example.seabattle.utils.PlayerBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class BattleshipSoloGameServiceImpl implements BattleshipSoloGameService {

    private final ShipPlacementService shipPlacementService;

    private final PlayerBuilder playerBuilder;

    private final CoordinateConverter coordinateConverter;

    private final ConsoleUtils consoleUtils;

    private int[][] battlefield1 = new int[18][18];

    private int[][] battlefield2 = new int[18][18];

    private int[][] monitor1 = new int[18][18];

    private int[][] monitor2 = new int[18][18];

    @Override
    public void runGame(Scanner scanner) {
        Map<String, Integer> shipsOfFirstPlayer = new HashMap<>();
        Map<String, Integer> shipsOfBot = new HashMap<>();
        Player firstPlayer = playerBuilder.createPlayer(scanner);
        if(consoleUtils.shouldAutoFillBoard(scanner)) {
            shipPlacementService.placeShipsAuto(firstPlayer, battlefield1, scanner, shipsOfFirstPlayer);
        } else {
            shipPlacementService.placeShipsManually(firstPlayer, battlefield1, scanner, shipsOfFirstPlayer);
        }
        Player bot = new Player();
        bot.setNickname("Bot");
        shipPlacementService.placeShipsAuto(bot, battlefield2, scanner, shipsOfBot);
        while (true) {
            executePlayerTurn(firstPlayer, battlefield1, monitor1, battlefield2, scanner, shipsOfBot);
            if (isWinCondition(firstPlayer)) {
                break;
            }
            executeBotTurn(monitor2, battlefield1);
            if (isWinCondition(bot)) {
                break;
            }
        }
    }

    public void executePlayerTurn(Player player,
                                  int[][] playerBoard,
                                  int[][] monitor,
                                  int[][] enemyBoard,
                                  Scanner scanner,
                                  Map<String, Integer> ships
    ) {
        int x = 0;
        int y = 0;
        while (true) {
            String coordinates = consoleUtils.inputCoordinateToMakeTurn(playerBoard, player, monitor, scanner);
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
            } else {
                consoleUtils.missMessage();
                monitor[x - 1][y - 1] = 1;
                enemyBoard[x][y] = 3;
                break;
            }
        }
    }

    public void executeBotTurn(int[][] monitor, int[][] enemyBoard) {
        while (true) {
            Random random = new Random();
            int x = random.nextInt(16) + 1;
            int y = random.nextInt(16) + 1;
            if (enemyBoard[x][y] == 1) {
                monitor[x - 1][y - 1] = 2;
                enemyBoard[x][y] = 2;
            } else {
                monitor[x - 1][y - 1] = 1;
                enemyBoard[x][y] = 3;
                break;
            }
        }
    }

    public boolean isWinCondition(Player player) {
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
            consoleUtils.showWinnerName(player);
            return true;
        }
        if (numberHitsSecondPlayer == 56) {
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
}
