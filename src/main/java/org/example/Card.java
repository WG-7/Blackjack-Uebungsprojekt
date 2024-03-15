package org.example;

public class Card {
    private CardSuit suit;
    private CardRank rank;

    public Card(CardSuit pSuit, CardRank pRank){
        suit = pSuit;
        rank = pRank;
    }

    private CardSuit getSuit(){
        return suit;
    }
    private CardRank getRank(){
        return rank;
    }
    private int getValue(){
        return rank.value;
    }

    @Override
    public String toString() {
        return suit+""+rank.shortName;
    }
}
