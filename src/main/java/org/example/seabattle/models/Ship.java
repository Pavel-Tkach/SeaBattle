package org.example.seabattle.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Ship {

    private String startPosition;

    private String endPosition;

    private int direction;

    private int deck;

    public boolean isVerticalDirection(int direction) {
        return direction == 1;
    }
}
