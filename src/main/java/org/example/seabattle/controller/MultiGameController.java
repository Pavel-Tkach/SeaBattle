package org.example.seabattle.controller;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.service.api.BattleshipMultiGameService;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class MultiGameController {

    private final BattleshipMultiGameService battleshipGameService;

    public void runMultiGame(Scanner scanner, int gameNumber) {
        battleshipGameService.configureAndRunGame(scanner, gameNumber);
    }

    public void endMultiGame() {
        battleshipGameService.endGame();
    }
}
