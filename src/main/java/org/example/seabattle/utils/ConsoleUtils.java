package org.example.seabattle.utils;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.models.Player;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleUtils {

    private final Validator validator;

    private final BoardPrinter boardPrinter;

    public String inputGameMode(Scanner scanner) {
        System.out.print("""
        Select number of game mode:
        1. Solo game
        2. With a partner
        """);
        return switch (scanner.nextLine()) {
            case "1" -> "Solo game";
            case "2" -> "With a partner";
            default -> inputGameMode(scanner);
        };
    }

    public boolean isAdmin(Scanner scanner) {
        System.out.print("Input secret word: ");
        String secretWord = scanner.nextLine();
        return "ADMIN".equalsIgnoreCase(secretWord);
    }

    public String inputAdminAction(Scanner scanner) {
        System.out.print("""
        Select number of admin action:
        1. Get game
        2. Delete game
        3. Archive game
        """);
        return switch (scanner.nextLine()) {
            case "1" -> "Get game";
            case "2" -> "Delete game";
            case "3" -> "Archive game";
            default -> inputAdminAction(scanner);
        };
    }

    public boolean isContinue(Scanner scanner) {
        System.out.println("""
                Do you want continue?
                1.Yes
                2.No
                """);
        return switch (scanner.nextLine()) {
            case "1" -> true;
            case "2" -> false;
            default -> isContinue(scanner);
        };
    }

    public String inputFileName(Scanner scanner) {
        System.out.println("Input file name: ");
        return scanner.nextLine();
    }

    public String inputCoordinatesToPlaceShip(Player player, int deck, int[][] battlefield, Scanner scanner) {
        System.out.println("\n" + player.getNickname() + ", please place your " + deck + "-deck ship on the battlefield:\n");
        boardPrinter.printBoard(battlefield);
        System.out.println("Please enter coordinates (for example:A12): ");
        String coordinates = scanner.next();
        while (!validator.isValidCoordinate(coordinates)) {
            System.out.print("Incorrect coordinate, please input again: ");
            coordinates = scanner.next();
        }
        return coordinates;
    }

    public void showPlayerShipPlacement(int[][] battlefield) {
        System.out.println("Your choose is:");
        boardPrinter.printBoard(battlefield);
        System.out.println("\n".repeat(20));
    }

    public int inputShipDirection(Scanner scanner) {
        System.out.println("Choose direction:\n1. Vertical.\n2. Horizontal.");
        return scanner.nextInt();
    }

    public boolean isShipsCorrectPlaced(Player player, int[][] battlefield, Scanner scanner) {
        System.out.println(player.getNickname() + ", your choose is:");
        boardPrinter.printBoard(battlefield);
        System.out.print("Yes or No: ");
        String answer = scanner.nextLine();
        if (answer.toLowerCase().contains("yes")) {
            System.out.println("\n".repeat(20));
        } else {
            return false;
        }
        return true;
    }

    public boolean shouldAutoFillBoard(Scanner scanner) {
        System.out.print("""
        Select number of way to fill board:
        1. Auto
        2. Manually
        """);
        return switch (scanner.nextLine()) {
            case "1" -> true;
            case "2" -> false;
            default -> shouldAutoFillBoard(scanner);
        };
    }

    public String inputCoordinateToMakeTurn(int[][] playerBoard, Player player, int[][] monitor, Scanner scanner) {
        System.out.println("Your board: ");
        boardPrinter.printBoard(playerBoard);
        System.out.println(player.getNickname() + ", please, make your turn.");
        boardPrinter.printMonitor(monitor);
        System.out.println("Please enter coordinates (for example:A1): ");
        String coordinates = scanner.next();
        while (!validator.isValidCoordinate(coordinates) || !validator.isTurnExists(coordinates, player)) {
            System.out.print("Incorrect coordinate, please input again: ");
            coordinates = scanner.next();
        }
        return coordinates;
    }

    public void showWinnerName(Player player) {
        System.out.println(player.getNickname() + " WIN!!!");
    }

    public void hitMessage() {
        System.out.println("Hit! Make your turn again!");
    }

    public void missMessage() {
        System.out.println("Miss! Your opponents turn!");
        System.out.println("\n".repeat(20));
    }

    public void shipDefeatedMessage() {
        System.out.println("Ship defeated!");
    }
}
