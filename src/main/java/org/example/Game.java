package org.example;
import java.util.ArrayList;


public class Game {

    private Player player;
    private ArrayList<Hand> playerHands;
    private int currentHand;
    private Hand dealerHand;
    private BlackjackManager blackjackManager;

    public Game(Player pPlayer){
        playerHands = new ArrayList<>();
        dealerHand = new Hand();
        Hand hand0 = new Hand();
        playerHands.add(hand0);
        currentHand = 0;
        player = pPlayer;
        blackjackManager = BlackjackManager.getInstance();
    }

    public void startAndExcuteGame(){
        //am ende der methode:
        Game newGame = new Game(player);
        blackjackManager.getGameHistory().add(newGame);
        newGame.startAndExcuteGame();
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
    public void endGame(){

    }
}
