package org.example;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    Random rand = new Random();
    private Player player;
    private ArrayList<ArrayList<Card>> playerHands;
    private ArrayList<Card> dealerHand;
    private int currentHand;

    public Game(){
        playerHands = new ArrayList<>();
        dealerHand = new ArrayList<>();
        ArrayList<Card> hand0 = new ArrayList<>();
        playerHands.add(hand0);
        currentHand = 0;
    }

    private void dealCard(){
        playerHands.get(currentHand).add(getRandomCard());
    }

    private Card getRandomCard(){
        CardSuit r_suit = CardSuit.values()[rand.nextInt(4)];
        CardRank r_rank = CardRank.values()[rand.nextInt(13)];
        return new Card(r_suit,r_rank);
    }

    public void showHands(){
        dealCard();
        dealCard();
        dealCard();
        dealCard();
        dealCard();
        //second Hand
        ArrayList<Card> hand1 = new ArrayList<>();
        playerHands.add(hand1);
        currentHand = 1;
        dealCard();
        dealCard();
        dealCard();

        for(int i=0;i<playerHands.size();i++)
        {
            System.out.print("Hand "+(i+1)+": \n");
            for(int j=0;j<playerHands.get(i).size();j++)
            {
                System.out.print(playerHands.get(i).get(j)+", \n");
            }
            System.out.println();
        }
    }
}
