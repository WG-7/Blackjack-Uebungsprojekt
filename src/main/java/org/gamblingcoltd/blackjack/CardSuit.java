package org.gamblingcoltd.blackjack;

public enum CardSuit {
    SPADES("\u2660"),
    CLUBS("\u2663"),
    HEARTS("\u2661"),
    DIAMONDS("\u2662");

    private final String suit;

    CardSuit(String pSuit) {
        suit = pSuit;
    }

    @Override
    public String toString() {
        return suit;
    }
}