package org.example;

public enum CardSuit {
    Spades("\u2660"),
    Clubs("\u2663"),
    Hearts("\u2661"),
    Diamonds("\u2662");

    private final String suit;

    CardSuit(String pSuit) {
        suit = pSuit;
    }

    @Override
    public String toString() {
        return suit;
    }
}
