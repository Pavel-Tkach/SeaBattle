package org.example.seabattle;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.controllers.AdminController;
import org.example.seabattle.utils.ConsoleUtils;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class AdminMenuFacade {

    private final AdminController adminController;

    private final ConsoleUtils consoleUtils;

    public void chooseAdminAction(Scanner scanner) {
        String adminChoice = consoleUtils.inputAdminAction(scanner);
        switch (adminChoice)  {
            case "Get game" -> adminController.checkGame(scanner);
            case "Delete game" -> adminController.deleteGame(scanner);
            case "Archive game" -> adminController.archiveGame(scanner);
        }
        if (consoleUtils.isContinue(scanner)) {
            chooseAdminAction(scanner);
        }
    }
}
