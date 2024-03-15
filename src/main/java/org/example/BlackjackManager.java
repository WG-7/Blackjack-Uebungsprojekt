package org.example;
import java.util.Scanner;

public class BlackjackManager {
    public BlackjackManager(){

    }

    public void initilizeGame(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte geben Sie ihren Namen ein:");
        String namenEingabe = scanner.nextLine();

        System.out.println("Sie haben eingegeben: " + namenEingabe);

        System.out.println("Bitte geben Sie ihren Namen ein:");
        namenEingabe = scanner.nextLine();

        System.out.println("Sie haben eingegeben: " + namenEingabe);

        scanner.close(); // Schließt den Scanner
    }
}
