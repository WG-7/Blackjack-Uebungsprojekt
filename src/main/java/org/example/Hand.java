package org.example;
import java.util.ArrayList;
import java.util.Random;

public class Hand {
    private Random rand = new Random();
    private ArrayList<Card> cards;
    private int bet;

    public Hand() {
        cards = new ArrayList<Card>();
    }

    private int getBet(){
        return bet;
    }
    private void setBet(int pAmount){
        bet = pAmount;
    }

    private int getHandValue(){

    }

    private void addCard(){

    }
    private Card getRandomCard(){
        CardSuit r_suit = CardSuit.values()[rand.nextInt(4)];
        CardRank r_rank = CardRank.values()[rand.nextInt(13)];
        return new Card(r_suit,r_rank);
    }

    private bool isBust(){

    }
    private bool isSplitAvailable(){

    }

    @Override
    public String toString() {
        return "";
    }
}
