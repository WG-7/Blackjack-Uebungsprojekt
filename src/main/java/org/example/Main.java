package org.example;

public class Main {
    public static void main(String[] args) {
        Game g1 = new Game();
        g1.showHands();

        BlackjackManager b1 = new BlackjackManager();
        b1.initilizeGame();
//        for(int i=0;i<20;i++)
//        {
//            Card newCard = g1.getRandomCard();
//            System.out.println(newCard);
//        }

    }
}