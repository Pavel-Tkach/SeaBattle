package org.example.seabattle.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.models.Player;
import org.example.seabattle.services.api.ShipPlacementService;
import org.example.seabattle.utils.BoardPrinter;
import org.example.seabattle.utils.ConsoleUtils;
import org.example.seabattle.utils.CoordinateConverter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ShipPlacementServiceImpl implements ShipPlacementService {

    private final CoordinateConverter coordinateConverter;

    private final BoardPrinter boardPrinter;

    private final ConsoleUtils consoleUtils;

    @Override
    public void placeShipsManually(Player player, int[][] battlefield, Scanner scanner, Map<String, Integer> ships) {
        int placementAttempts = 1;
        int deck = 6;
        int direction = 1;
        int x = 0;
        int y = 0;
        while (deck * placementAttempts > 0) {
            for (placementAttempts = 1; placementAttempts <= 7 - deck; placementAttempts++) {
                String coordinates = consoleUtils.inputCoordinatesToPlaceShip(player, deck, battlefield, scanner);
                String coordinateOfX = String.valueOf(coordinates.charAt(0));
                String coordinateOfY;
                if (coordinates.trim().length() == 3) {
                    coordinateOfY = coordinates.substring(1);
                } else {
                    coordinateOfY = String.valueOf(coordinates.charAt(1));
                }
                x = coordinateConverter.convertCoordinate(coordinateOfX);
                y = coordinateConverter.convertCoordinate(coordinateOfY);
                if (deck > 1) {
                    direction = consoleUtils.inputShipDirection(scanner);
                }
                if (isPlacementInvalid(x, y, deck, direction, battlefield)) {
                    placementAttempts--;
                } else {
                    placeShip(x, y, deck, direction, battlefield, ships);
                }
            }
            deck--;
        }
        consoleUtils.showPlayerShipPlacement(battlefield);
    }

    @Override
    public void placeShipsAuto(Player player, int[][] battlefield, Scanner scanner, Map<String, Integer> ships) {
        Random random = new Random();
        int placementAttempts = 1;
        int deck = 6;
        int direction = 1;
        int x;
        int y;
        while (deck * placementAttempts > 0) {
            for (placementAttempts = 1; placementAttempts <= 7 - deck; placementAttempts++) {
                x = random.nextInt(16) + 1;
                y = random.nextInt(16) + 1;
                if (deck > 1) {
                    direction = (random.nextInt(2) + 1);
                }
                if (isPlacementInvalid(x, y, deck, direction, battlefield)) {
                    placementAttempts--;
                } else {
                    placeShip(x, y, deck, direction, battlefield, ships);
                }
                boardPrinter.printBoard(battlefield);
            }
            deck--;
            if (deck == 0 && !player.getNickname().equals("Bot")) {
                if (!consoleUtils.isShipsCorrectPlaced(player, battlefield, scanner)) {
                    deck = 6;
                    placementAttempts = 1;
                    boardPrinter.printClearBoard(battlefield);
                }
            }
        }
    }

    public boolean isPlacementInvalid(int x, int y, int deck, int direction, int[][] battlefield) {
        if (direction == 1) {
            boolean flag = false;
            if ((y + deck < battlefield.length) && battlefield[x][y - 1] == 0 && battlefield[x - 1][y - 1] == 0 && battlefield[x + 1][y - 1] == 0 && battlefield[x][y + deck] == 0 && battlefield[x - 1][y + deck] == 0 && battlefield[x + 1][y + deck] == 0) {
                flag = true;
                for (int step = 0; step < deck; step++) {
                    if (battlefield[x - 1][y + step] == 1 || battlefield[x + 1][y + step] == 1) {
                        flag = false;
                        break;
                    }
                }
            }
            return !flag;
        }
        else if (direction == 2) {
            boolean flag = false;
            if ((x + deck < battlefield.length) && battlefield[x - 1][y - 1] == 0 && battlefield[x - 1][y + 1] == 0 && battlefield[x - 1][y] == 0 && battlefield[x + deck][y] == 0 && battlefield[x + deck][y - 1] == 0 && battlefield[x + deck][y + 1] == 0) {
                flag = true;
                for (int step = 0; step < deck; step++) {
                    if (battlefield[x + step][y - 1] == 1 || battlefield[x + step][y + 1] == 1) {
                        flag = false;
                        break;
                    }
                }
            }
            return !flag;
        }
        return false;
    }

    public void placeShip(int x, int y, int deck, int direction, int[][] battlefield, Map<String, Integer> ships) {
        StringBuilder coordinatesShip = new StringBuilder();
        if (direction == 1) {
            for (int step = 0; step < deck; step++) {
                int coordinateOfY = y + step;
                coordinatesShip.append(coordinateConverter.convertCoordinateToString(x)).append(coordinateOfY);
                battlefield[x][y + step] = 1;
            }
        } else {
            for (int step = 0; step < deck; step++) {
                coordinatesShip.append(coordinateConverter.convertCoordinateToString(x + step)).append(y);
                battlefield[x + step][y] = 1;
            }
        }
        ships.put(coordinatesShip.toString(), deck);
    }
}
