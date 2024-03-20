package org.gamblingcoltd.blackjack;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player player;
    private ArrayList<Hand> playerHands;
    private int currentHandIndex;
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

    public void startAndExcuteGame(){
        //am ende der methode:
//        Game newGame = new Game(player);
//        blackjackManager.getGameHistory().add(newGame);
//        newGame.startAndExcuteGame();
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
    public void increaseBet(int pAmount)
    {
        player.changeBalance(-pAmount);
        playerHands.get(currentHandIndex).setBet(playerHands.get(currentHandIndex).getBet() + pAmount);
    }
    public boolean isInsureAvailable(){
        return dealerHand.getCardAtIndex(1).getRank()==CardRank.ACE && getCurrenPlayerHand().getSize() == 2;
    }

    public void dealStartingCards(){
        playerHands.get(0).newCard();
        playerHands.get(0).newCard();

        dealerHand.newCard();
        dealerHand.newCard();
    }

    public void hit(){
        playerHands.get(currentHandIndex).newCard();
        if (updateListener != null) {
            updateListener.updateUI();
        }
        if(playerHands.get(currentHandIndex).getHandValue()>=21){
            playerHands.get(currentHandIndex).setHandFinalised();
            endCurrentHand();
        }
    }
    public void stand(){
        endCurrentHand();
    }
    public void doubleDown(){
        int currentHandBet = playerHands.get(currentHandIndex).getBet();

        player.changeBalance(-currentHandBet);
        playerHands.get(currentHandIndex).setBet(currentHandBet*2);
        playerHands.get(currentHandIndex).newCard();

        if (updateListener != null) {
            updateListener.updateUI();
        }

        if(playerHands.get(currentHandIndex).getHandValue()>=21){
            playerHands.get(currentHandIndex).setHandFinalised();
        }

        endCurrentHand();
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

        if (updateListener != null) {
            updateListener.updateUI();
        }
    }
    public void insure(){
        // Ask for InsureanceAmount
        Scanner scanner = new Scanner(System.in);
        Boolean isValidAmount = false;
        int insuranceAmountInt = 0;

        do {
            System.out.println("Wie hoch soll die Versicherung sein?(maximal " + playerHands.get(currentHandIndex).getBet() + ") :");
            String insuranceAmount = scanner.nextLine();
            insuranceAmountInt = Integer.parseInt(insuranceAmount);
            if (insuranceAmountInt > 0 && insuranceAmountInt <= playerHands.get(currentHandIndex).getBet()) {
                isValidAmount = true;
                player.changeBalance(-insuranceAmountInt);
            }
        } while (!isValidAmount);
        scanner.close();

        //Check for Dealer Blackjack
        if(dealerHand.getCardAtIndex(1).getValue()==10) {
            player.changeBalance(insuranceAmountInt*2);         // 2/1 Ratio
        }
        endCurrentHand();
    }

    private void endCurrentHand(){
        getCurrenPlayerHand().setHandFinalised();
        if(playerHands.size() >= (currentHandIndex+1)+1) {
            currentHandIndex++;
            if (updateListener != null) {
                updateListener.updateUI();
            }
        }
        else {
            startDealersTurn();
        }
    }
    private void startDealersTurn(){
        dealerHand.setBet(0);  // dealer: his turn
        showHands();
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
            Hand currentHand = playerHands.get(i);
            System.out.println("Hand "+i);
            if(currentHand.isBust()){
                System.out.println("LOST");
                break;
            } else if (dealerHand.isBust() || currentHand.getHandValue() > dealerHand.getHandValue()) {
                System.out.println("(Innit Bet) Payed back "+playerHands.get(i).getBet());
                player.changeBalance(playerHands.get(i).getBet());                      // payback initial Bet

                //player blackjack boni (only before splitting + first turn)
                if(playerHands.size()==1 && currentHand.getHandValue()==21 && currentHand.getSize()==2) {
                    System.out.println("(Blackjack) Won "+playerHands.get(i).getBet()*1.5);
                    player.changeBalance(playerHands.get(i).getBet()*1.5);      //Blackjack boni 3/2
                }
                else{
                    System.out.println("(Normal) Won "+playerHands.get(i).getBet());
                    player.changeBalance(playerHands.get(i).getBet());                   //Normal Payback 1/1
                }
            } else if (currentHand.getHandValue() == dealerHand.getHandValue()) {
                System.out.println("(Push) Payed back "+playerHands.get(i).getBet()*1.5);
                player.changeBalance(playerHands.get(i).getBet());                      //push (pay bet back)
            } else {
                System.out.println("LOST");
            }
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
    public void endGame(){

    }
}

