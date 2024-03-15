package org.example;

public class Card {
    private CardSuit suit;
    private CardRank rank;

    public Card(CardSuit pSuit, CardRank pRank){
        suit = pSuit;
        rank = pRank;
    }

    public CardSuit getSuit(){
        return suit;
    }
    public CardRank getRank(){
        return rank;
    }

    @Override
    public String toString() {
        return suit+""+rank;
    }
}
