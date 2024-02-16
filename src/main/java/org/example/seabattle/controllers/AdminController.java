package org.example.seabattle.controllers;

import lombok.RequiredArgsConstructor;
import org.example.seabattle.services.api.AdminService;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    public void checkGame(Scanner scanner) {
        adminService.checkGame(scanner);
    }

    public void deleteGame(Scanner scanner) {
        adminService.deleteGame(scanner);
    }

    public void archiveGame(Scanner scanner) {
        adminService.archiveGame(scanner);
    }
}
