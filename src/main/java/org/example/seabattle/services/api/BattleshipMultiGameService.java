package org.example.seabattle.services.api;

import java.util.Scanner;

public interface BattleshipMultiGameService {

    void configureAndRunGame(Scanner scanner, int gameNumber);

    void endGame();
}
