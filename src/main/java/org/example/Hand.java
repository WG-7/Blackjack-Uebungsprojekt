package org.example;
import java.util.ArrayList;
import java.util.Random;

public class Hand {
    private Random rand = new Random();
    private ArrayList<Card> cards;
    private int bet;

    public Hand() {
        cards = new ArrayList<>();
    }

    private int getBet(){
        return bet;
    }
    private void setBet(int pAmount){
        bet = pAmount;
    }

    private int getHandValue(){
        return 10;
    }

    private void addCard(){

    }
    private Card getRandomCard(){
        CardSuit rSuit = CardSuit.values()[rand.nextInt(4)];
        CardRank rRank = CardRank.values()[rand.nextInt(13)];
        return new Card(rSuit,rRank);
    }

    private boolean isBust(){
        return true;
    }
    private boolean isSplitAvailable(){
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
}
