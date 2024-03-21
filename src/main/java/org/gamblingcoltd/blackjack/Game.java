package org.gamblingcoltd.blackjack;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player player;
    private ArrayList<Hand> playerHands;
    private int currentHandIndex;
    private int insuranceAmount;
    private Hand dealerHand;
    private BlackjackManager blackjackManager;
    private GameUpdateListener updateListener;

    public Game(Player pPlayer){
        playerHands = new ArrayList<>();
        Hand hand0 = new Hand();
        playerHands.add(hand0);
        currentHandIndex = 0;

        dealerHand = new Hand();
        dealerHand.setBet(-1);  //dealer: player turn

        player = pPlayer;
        blackjackManager = BlackjackManager.getInstance();
    }

    public void setUpdateListener(GameUpdateListener listener) {
        this.updateListener = listener;
    }

    public Player getPlayer(){
        return player;
    }
    public int getPlayerHandSize(){
        return playerHands.size();
    }
    public int getCurrentHandIndex(){
        return currentHandIndex;
    }
    public Hand getCurrenPlayerHand(){
        return playerHands.get(currentHandIndex);
    }
    public Hand getDealerHand(){
        return dealerHand;
    }
    public void setBet(int pAmount){
        player.changeBalance(-pAmount);
        playerHands.get(currentHandIndex).setBet(pAmount);
    }
    public void increaseBet(int pAmount) {
        player.changeBalance(-pAmount);
        playerHands.get(currentHandIndex).setBet(playerHands.get(currentHandIndex).getBet() + pAmount);
        System.out.println(playerHands.get(currentHandIndex).getBet());
        System.out.println(currentHandIndex);

    }
    public boolean isInsureAvailable(){
        return dealerHand.getCardAtIndex(1).getRank()==CardRank.ACE && getCurrenPlayerHand().getSize() == 2 && !getCurrenPlayerHand().getHandFinalised();
    }

    public void dealStartingCards(){
        playerHands.get(0).newCard();
        playerHands.get(0).newCard();

        dealerHand.newCard();
        dealerHand.newCard();

        //BlackjackCheck
        if(playerHands.get(currentHandIndex).getHandValue() == 21){
            getCurrenPlayerHand().setHandFinalised();
        }
        if (updateListener != null) {
            updateListener.updateUI();
        }
    }

    public void hit(){
        playerHands.get(currentHandIndex).newCard();
        if (updateListener != null) {
            updateListener.updateUI();
        }
        if(playerHands.get(currentHandIndex).getHandValue()>=21){
            getCurrenPlayerHand().setHandFinalised();
        }
        if (updateListener != null) {
            updateListener.updateUI();
        }
    }
    public void stand(){
        getCurrenPlayerHand().setHandFinalised();
        if (updateListener != null) {
            updateListener.updateUI();
        }
    }
    public void doubleDown(){
        int currentHandBet = playerHands.get(currentHandIndex).getBet();

        player.changeBalance(-currentHandBet);
        playerHands.get(currentHandIndex).setBet(currentHandBet*2);
        playerHands.get(currentHandIndex).newCard();

        if (updateListener != null) {
            updateListener.updateUI();
        }
        getCurrenPlayerHand().setHandFinalised();

        if (updateListener != null) {
            updateListener.updateUI();
        }
    }
    public void split(){
        //get second card
        Card splitCard = playerHands.get(currentHandIndex).getCardAtIndex(1);
        playerHands.get(currentHandIndex).removeCardAtIndex(1);

        //add the card to a new Hand
        Hand hand1 = new Hand();
        playerHands.add(hand1);
        player.changeBalance(-playerHands.get(currentHandIndex).getBet());
        playerHands.get(playerHands.size()-1).setBet(playerHands.get(currentHandIndex).getBet());
        playerHands.get(playerHands.size()-1).addCardToHand(splitCard);

        //draw for both hands
        playerHands.get(currentHandIndex).newCard();
        playerHands.get(playerHands.size()-1).newCard();

        //Check for Blackjack of the current hand // secound hand will be checked on switch
        if(playerHands.get(currentHandIndex).getHandValue() == 21){
            getCurrenPlayerHand().setHandFinalised();
        }

        if (updateListener != null) {
            updateListener.updateUI();
        }
    }
    public void insure(){
        player.changeBalance(-playerHands.get(currentHandIndex).getBet());

        int amount = 0;
        String message = "";

        //Check for Dealer Blackjack
        if(dealerHand.getCardAtIndex(0).getValue()==10) {
            dealerHand.setBet(0);  // dealer: his turn
            if (updateListener != null) {
                updateListener.updateUI();
            }
            player.changeBalance(playerHands.get(currentHandIndex).getBet()*2);         // 2/1 Ratio
            message = "Insurance Won";
            amount = playerHands.get(currentHandIndex).getBet();
        }else{
            message = "Insurance Lost";
        }
        if (updateListener != null) {
            updateListener.printWinMessage(amount, currentHandIndex, message);
        }

        if (updateListener != null) {
            updateListener.updateUI();
        }
    }

    public void endCurrentHand(){
        getCurrenPlayerHand().setHandFinalised();

        if(playerHands.size() >= (currentHandIndex+1)+1) {
            currentHandIndex++;
            if(playerHands.get(currentHandIndex).getHandValue() == 21){
                getCurrenPlayerHand().setHandFinalised();
            }
        }
        else {
            startDealersTurn();
        }
    }
    private void startDealersTurn(){
        dealerHand.setBet(0);  // dealer: his turn
        showHands();
        if (updateListener != null) {
            updateListener.updateUI();
        }
        while (dealerHand.getHandValue()<17) {
            dealerHand.newCard();
            if (updateListener != null) {
                updateListener.updateUI();
            }
        }
        payWinningHands();
    }

    private void payWinningHands(){
        showHands();
        for (int i = 0; i < playerHands.size();i++)
        {
            double amount = -playerHands.get(i).getBet();
            String message = "You Lost";

            Hand currentHand = playerHands.get(i);
            System.out.println("Hand "+i);
            if(currentHand.isBust()){
                System.out.println("LOST");
            } else if (dealerHand.isBust() || currentHand.getHandValue() > dealerHand.getHandValue()) {
                System.out.println("(Innit Bet) Payed back "+playerHands.get(i).getBet());
                player.changeBalance(playerHands.get(i).getBet());                      // payback initial Bet

                //player blackjack boni (only before splitting + first turn)
                if(playerHands.size()==1 && currentHand.getHandValue()==21 && currentHand.getSize()==2) {
                    System.out.println("(Blackjack) Won "+playerHands.get(i).getBet()*1.5);
                    message = "You Won";
                    amount = playerHands.get(i).getBet()*1.5;
                    player.changeBalance(playerHands.get(i).getBet()*1.5);      //Blackjack boni 3/2
                }
                else{
                    System.out.println("(Normal) Won "+playerHands.get(i).getBet());
                    message = "You Won";
                    amount = playerHands.get(i).getBet();
                    player.changeBalance(playerHands.get(i).getBet());                   //Normal Payback 1/1
                }
            } else if (currentHand.getHandValue() == dealerHand.getHandValue()) {
                System.out.println("(Push) Payed back "+playerHands.get(i).getBet());
                message = "Push Back";
                amount = 0;
                player.changeBalance(playerHands.get(i).getBet());                      //push (pay bet back)
            } else{
                System.out.println("LOST");
            }

            if (updateListener != null) {
                updateListener.printWinMessage(amount, i, message);
            }
        }

        if (updateListener != null) {
            updateListener.updateUI();
        }
    }

    private void showHands(){
        System.out.println("--------------------------------------------------------------------");
        System.out.println(player.getName()+"("+ player.getBalance()+"):");
        for (int i = 0; i < playerHands.size();i++) {
            System.out.println("Hand "+(i+1)+": "+playerHands.get(i)+"\t Value:"+playerHands.get(i).getHandValue()+" BetAmount:"+playerHands.get(i).getBet());
        }
        System.out.println();
        System.out.println("Dealer: "+dealerHand+"\t Value:"+dealerHand.getHandValue());
        System.out.println("--------------------------------------------------------------------");
    }
}

