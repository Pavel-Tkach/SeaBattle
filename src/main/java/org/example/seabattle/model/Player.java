package org.example.seabattle.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player {

    private String nickname;

    private List<String> playerTurns = new ArrayList<>();
}
