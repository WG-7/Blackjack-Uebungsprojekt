package org.gamblingcoltd.blackjack;

public enum CardRank {
    ACE("Ace", "A", 11),
    TWO("Two", "2", 2),
    THREE("Three", "3", 3),
    FOUR("Four", "4", 4),
    FIVE("Five", "5", 5),
    SIX("Six", "6", 6),
    SEVEN("Seven", "7", 7),
    EIGHT("Eight", "8", 8),
    NINE("Nine", "9", 9),
    TEN("Ten", "10", 10),
    JACK("Jack", "J", 10),
    QUEEN("Queen", "Q", 10),
    KING("King", "K", 10);
    String name;
    String shortName;
    int value;

    CardRank(String pName, String pShortName, int pValue) {
        name = pName;
        shortName = pShortName;
        value = pValue;
    }

    public String toString() {
        return name;
    }
}
