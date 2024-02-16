package org.example.seabattle.service.api;

import java.util.Scanner;

public interface BattleshipMultiGameService {

    void configureAndRunGame(Scanner scanner, int gameNumber);

    void endGame();
}
