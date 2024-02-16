package org.example.seabattle;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SeaBattleApplication implements CommandLineRunner {

    private final GameMenuFacade gameMenuFacade;

    public static void main(String[] args) {
        SpringApplication.run(SeaBattleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        gameMenuFacade.selectGameMode();
    }
}
