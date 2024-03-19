package org.gamblingcoltd.blackjack;

public class Card {
    private final CardSuit suit;
    private final CardRank rank;

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
    public int getValue(){
        return rank.value;
    }

    public String getUrl(){
        return rank.shortName+"_of_"+suit.name+".png";
    }

    @Override
    public String toString() {
        return suit+rank.shortName;
    }
}
