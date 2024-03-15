package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        Game g1 = new Game();
        for(int i=0;i<20;i++)
        {
            Card newCard = g1.getRandomCard();
            System.out.println(newCard);
        }

    }
}