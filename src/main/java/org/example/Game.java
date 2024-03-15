package org.example;
import java.util.ArrayList;
import java.util.Arrays;
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

    public Card getRandomCard(){
        CardSymbol r_symbol = CardSymbol.values()[rand.nextInt(4)];
        CardValue r_value = CardValue.values()[rand.nextInt(13)];
        return new Card(r_symbol,r_value);
    }

    public void showHand(){
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
        }
    }
}
