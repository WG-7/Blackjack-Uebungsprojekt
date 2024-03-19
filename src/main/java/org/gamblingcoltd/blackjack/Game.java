package org.gamblingcoltd.blackjack;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player player;
    private ArrayList<Hand> playerHands;
    private int currentHandIndex;
    private Hand dealerHand;
    private BlackjackManager blackjackManager;

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
    public void startAndExcuteGame(){
        dealStartingCards();
        //am ende der methode:
//        Game newGame = new Game(player);
//        blackjackManager.getGameHistory().add(newGame);
//        newGame.startAndExcuteGame();
    }

    private void setBet(int pAmount){
        player.changeBalance(-pAmount);
        playerHands.get(currentHandIndex).setBet(pAmount);
    }

    private void dealStartingCards(){
        playerHands.get(0).newCard();
        playerHands.get(0).newCard();

        dealerHand.newCard();
        dealerHand.newCard();
    }

    public void hit(){
        playerHands.get(currentHandIndex).newCard();
    }
    public void stand(){
        endCurrentHand();
    }
    public void doubleDown(){
        int currentHandBet = playerHands.get(currentHandIndex).getBet();

        player.changeBalance(-currentHandBet);
        playerHands.get(currentHandIndex).setBet(currentHandBet*2);
        playerHands.get(currentHandIndex).newCard();

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
        playerHands.get(currentHandIndex+1).setBet(playerHands.get(currentHandIndex).getBet());
        playerHands.get(currentHandIndex+1).addCardToHand(splitCard);

        //draw for both hands
        playerHands.get(currentHandIndex).newCard();
        playerHands.get(currentHandIndex+1).newCard();
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
        if(playerHands.size() >= (currentHandIndex+1)+1) {
            currentHandIndex++;
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
        }
        payWinningHands();
    }

    private void payWinningHands(){
        showHands();

        for (int i = 0; i < playerHands.size();i++)
        {
            Hand currentHand = playerHands.get(i);

            if(currentHand.isBust()){
                break;
            } else if (dealerHand.isBust() || currentHand.getHandValue() > dealerHand.getHandValue()) {
                player.changeBalance(playerHands.get(i).getBet());                      // payback initial Bet

                //player blackjack boni (only before splitting + first turn)
                if(playerHands.size()==1 && currentHand.getHandValue()==21 && currentHand.getSize()==2) {
                    player.changeBalance(playerHands.get(i).getBet()*1.5);      //Blackjack boni 3/2
                }
                else{
                    player.changeBalance(playerHands.get(i).getBet());                   //Normal Payback 1/1
                }
            } else if (currentHand.getHandValue() == dealerHand.getHandValue()) {
                player.changeBalance(playerHands.get(i).getBet());                      //push (pay bet back)
            }
        }
    }

    public Hand getCurrenPlayerHand(){
        return playerHands.get(currentHandIndex);
    }
    public Hand getDealerHand(){
        return dealerHand;
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

