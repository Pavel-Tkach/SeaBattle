package org.example.seabattle.services.api;

import org.example.seabattle.models.Player;

import java.util.Map;
import java.util.Scanner;

public interface ShipPlacementService {

    void placeShipsManually(Player player, int[][] battlefield, Scanner scanner, Map<String, Integer> ships);
    void placeShipsAuto(Player player, int[][] battlefield, Scanner scanner, Map<String, Integer> ships);
}
