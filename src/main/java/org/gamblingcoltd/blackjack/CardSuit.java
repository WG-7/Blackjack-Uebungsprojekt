package org.gamblingcoltd.blackjack;

public enum CardSuit {
    SPADES("\u2660","spades"),
    CLUBS("\u2663","clubs"),
    HEARTS("\u2661","hearts"),
    DIAMONDS("\u2662","diamonds");

    public final String suit;
    public final String name;

    CardSuit(String pSuit, String pName) {
        suit = pSuit;
        name = pName;
    }

    @Override
    public String toString() {
        return suit;
    }
}