package org.example.seabattle.utils;

import java.util.Scanner;

public class ScannerSingleton {

    private static Scanner scanner;

    public static Scanner getInstance(){
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public static void closeScanner(){
        if (scanner != null) {
            try {
                scanner.close();
            } catch (Exception e) {
                System.out.println("Close scanner error");
            }
        }
    }
}
