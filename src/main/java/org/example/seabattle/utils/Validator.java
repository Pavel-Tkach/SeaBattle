package org.example.seabattle.utils;

import org.example.seabattle.models.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validator {

    private List<String> letters = List.of("A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K",  "L", "M",  "N",  "O", "P");
    private List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);

    public boolean isValidCoordinate(String coordinate) {
        coordinate = coordinate.trim().toUpperCase();
        if (coordinate.length() < 2 || coordinate.length() > 3)
            return false;
        String letter = coordinate.substring(0, 1);
        String numberPart = coordinate.substring(1);
        int number;
        try {
            number = Integer.parseInt(numberPart);
        } catch (NumberFormatException e) {
            return false;
        }
        return letters.contains(letter) && numbers.contains(number);
    }

    public boolean isTurnExists(String coordinates, Player player) {
        List<String> playerTurns = player.getPlayerTurns();
        if (!playerTurns.contains(coordinates)) {
            playerTurns.add(coordinates);
            return true;
        } else {
            return false;
        }
    }
}
