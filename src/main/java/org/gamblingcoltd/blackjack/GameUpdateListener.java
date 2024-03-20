package org.gamblingcoltd.blackjack;

public interface GameUpdateListener {
    void updateUI();
    void printWinMessage(double pAmount, int pHandIndex, String pMessage);
}
