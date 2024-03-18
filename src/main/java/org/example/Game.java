package org.example;
import java.util.ArrayList;
import java.util.Scanner;


public class Game {

    private Player player;
    private ArrayList<Hand> playerHands;
    private int currentHandIndex;
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
        playerHands.get(currentHandIndex).newCard();
    }
    private void stand(){
        endCurrentHand();
    }
    private void doubleDown(){
        int currentHandBet = playerHands.get(currentHandIndex).getBet();
        playerHands.get(currentHandIndex).setBet(currentHandBet*2);
        playerHands.get(currentHandIndex).newCard();
        endCurrentHand();
    }
    private void split(){
        //get second card
        Card splitCard = playerHands.get(currentHandIndex).getCardAtIndex(1);
        playerHands.get(currentHandIndex).removeCardAtIndex(1);

        //add the card to a new Hand
        Hand hand1 = new Hand();
        playerHands.add(hand1);
        playerHands.get(currentHandIndex+1).addCardToHand(splitCard);

        //draw for both hands
        playerHands.get(currentHandIndex).newCard();
        playerHands.get(currentHandIndex+1).newCard();
    }
    private void insure(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Möchten Sie eine Versicherung abschließen?(y/n):");
        String wantsInsure = scanner.nextLine();
        if(wantsInsure.compareToIgnoreCase("y") == 0)
        {
            
        }
        scanner.close();
    }

    private void endCurrentHand(){
        if(playerHands.size() >= (currentHandIndex+1)+1) {
            currentHandIndex++;
        }
        else {
            startDealersTurn();
        }
    }
    private void startDealersTurn(){

    }

    private void payWinningHands(){

    }

    private void showHands(){

    }
}
