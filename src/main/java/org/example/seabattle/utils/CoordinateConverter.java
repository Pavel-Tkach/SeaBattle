package org.example.seabattle.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CoordinateConverter {

    private List<String> letters = List.of("A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K",  "L", "M",  "N",  "O", "P");

    public int convertCoordinate(String coordinate) {
        if (letters.contains(coordinate)){
            return switch (coordinate) {
                case "A" ->  1;
                case "B" ->  2;
                case "C" ->  3;
                case "D" ->  4;
                case "E" ->  5;
                case "F" ->  6;
                case "G" ->  7;
                case "H" ->  8;
                case "I" ->  9;
                case "J" ->  10;
                case "K" ->  11;
                case "L" ->  12;
                case "M" ->  13;
                case "N" ->  14;
                case "O" ->  15;
                case "P" ->  16;
                default -> 0;
            };
        }
        else {
            return switch (coordinate) {
                case "1" -> 1;
                case "2" -> 2;
                case "3" -> 3;
                case "4" -> 4;
                case "5" -> 5;
                case "6" -> 6;
                case "7" -> 7;
                case "8" -> 8;
                case "9" -> 9;
                case "10" -> 10;
                case "11" -> 11;
                case "12" -> 12;
                case "13" -> 13;
                case "14" -> 14;
                case "15" -> 15;
                case "16" -> 16;
                default -> 0;
            };
        }
    }

    public String convertCoordinateToString(int coordinate) {
        return switch (coordinate) {
            case 1 -> "A";
            case 2 -> "B";
            case 3 -> "C";
            case 4 -> "D";
            case 5 -> "E";
            case 6 -> "F";
            case 7 -> "G";
            case 8 -> "H";
            case 9 -> "I";
            case 10 -> "J";
            case 11 -> "K";
            case 12 -> "L";
            case 13 -> "M";
            case 14 -> "N";
            case 15 -> "O";
            case 16 -> "P";
            default -> "";
        };
    }
}
