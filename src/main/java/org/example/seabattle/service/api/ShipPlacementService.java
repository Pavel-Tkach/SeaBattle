package org.example.seabattle.service.api;

import org.example.seabattle.model.Player;

import java.util.Map;
import java.util.Scanner;

public interface ShipPlacementService {

    void placeShipsManually(Player player, int[][] battlefield, Scanner scanner, Map<String, Integer> ships);
    void placeShipsAuto(Player player, int[][] battlefield, Scanner scanner, Map<String, Integer> ships);
}
