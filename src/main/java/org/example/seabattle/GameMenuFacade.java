package org.example.seabattle;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.controllers.MultiGameController;
import org.example.seabattle.controllers.SoloGameController;
import org.example.seabattle.utils.ConsoleUtils;
import org.example.seabattle.utils.ScannerSingleton;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class GameMenuFacade {

    private int gameNumber = 0;

    private final SoloGameController soloGameController;

    private final MultiGameController multiGameController;

    private final AdminMenuFacade adminMenuFacade;

    private final ConsoleUtils consoleUtils;

    public void selectGameMode() {
        gameNumber++;
        Scanner scanner = ScannerSingleton.getInstance();
        if (consoleUtils.isAdmin(scanner)) {
            adminMenuFacade.chooseAdminAction(scanner);
        }
        else {
            String gameMode = consoleUtils.inputGameMode(scanner);
            if ("Solo game".equals(gameMode)) {
                soloGameController.runSoloGame(scanner);
            } else {
                multiGameController.runMultiGame(scanner, gameNumber);
                multiGameController.endMultiGame();
            }
        }
        if (consoleUtils.isContinue(scanner)) {
            selectGameMode();
        }
        ScannerSingleton.closeScanner();
    }
}
