package org.example.seabattle.controllers;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.services.api.BattleshipSoloGameService;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class SoloGameController {

    private final BattleshipSoloGameService battleshipSoloGameService;

    public void runSoloGame(Scanner scanner) {
        battleshipSoloGameService.runGame(scanner);
    }
}
