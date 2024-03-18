package org.example;
import java.util.ArrayList;


public class Game {

    private Player player;
    private ArrayList<Hand> playerHands;
    private int currentHand;
    private Hand dealerHand;

    public Game(){
        playerHands = new ArrayList<>();
        dealerHand = new Hand();
        Hand hand0 = new Hand();
        playerHands.add(hand0);
        currentHand = 0;
    }

    public void startAndExcuteGame(){

    }

    private void setBet(){

    }
    private void dealStartingCards(){

    }

    private void hit(){

    }
    private void stand(){

    }
    private void doubleDown(){

    }
    private void split(){

    }
    private void insure(){

    }

    private void switchCurrentHand(){

    }
    private void startDealersTurn(){

    }

    private void payWinningHands(){

    }

    private void showHands(){

    }
}
