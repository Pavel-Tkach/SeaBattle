package org.example.seabattle.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardContext {

    private int[][] battlefield1 = new int[18][18];

    private int[][] battlefield2 = new int[18][18];

    private int[][] monitor1 = new int[18][18];

    private int[][] monitor2 = new int[18][18];
}
