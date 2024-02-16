package org.example.seabattle.util;

import org.example.seabattle.model.Player;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PlayerBuilder {

    public Player createPlayer(Scanner scanner) {
        Player player = new Player();
        inputPlayerName(scanner, player);
        return player;
    }

    public void inputPlayerName(Scanner scanner, Player player) {
        System.out.print("Input your nickname: ");
        String nickname = scanner.nextLine();
        player.setNickname(nickname);
    }
}
