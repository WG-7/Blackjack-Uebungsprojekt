package org.gamblingcoltd.blackjack;
import java.util.ArrayList;
import java.util.Random;

public class Hand {
    private Random rand = new Random();
    private ArrayList<Card> cards;
    private int bet;
    private boolean isHandFinalised;

    public Hand() {
        cards = new ArrayList<>();
    }

    public int getBet(){
        return bet;
    }
    public int getSize(){
        return cards.size();
    }
    public boolean getHandFinalised(){
        return isHandFinalised;
    }
    public void setBet(int pAmount){
        bet = pAmount;
    }
    public void setHandFinalised(){
        isHandFinalised = true;
    }

    public boolean isBust(){
        return getHandValue()>21;
    }
    public boolean isSplitAvailable(){
        //returns if 2 cards and equalValue
        return getSize() == 2 && cards.get(0).getValue() == cards.get(1).getValue() && !isHandFinalised;
    }
    public boolean isDoubleAvailable(){
        //returns if 2 cards and equalValue
        return getSize() == 2 && !isHandFinalised;
    }


    public void newCard(){
        cards.add(getRandomCard());
    }
    private Card getRandomCard(){
        CardSuit rSuit = CardSuit.values()[rand.nextInt(4)];
        CardRank rRank = CardRank.values()[rand.nextInt(13)];
        return new Card(rSuit,rRank);
    }

    public int getHandValue(){
        int totalHandValue = 0;
        int amountOfAces = 0;
        int arrayStartValue = 0;
        if(bet == -1) { // dealersHand during Players turn
            arrayStartValue = 1;
        }

        for(int i = arrayStartValue; i < getSize(); i++)
        {
            totalHandValue += cards.get(i).getValue();
            if(cards.get(i).getRank() == CardRank.ACE)
            {
                amountOfAces++;
            }
        }
        while(totalHandValue > 21 && amountOfAces > 0)
        {
            totalHandValue -= 10;
            amountOfAces--;
        }

        return totalHandValue;
    }
    public Card getCardAtIndex(int pIndex){
        return cards.get(pIndex);
    }
    public void addCardToHand(Card pCard){
        cards.add(pCard);
    }
    public void removeCardAtIndex(int pIndex){
        cards.remove(pIndex);
    }

    @Override
    public String toString() {
        String cardsString = "";
        int arrayStartValue = 0;
        if(bet == -1) { // dealersHand during Players turn
            arrayStartValue = 1;
            cardsString = cardsString.concat("??, ");
        }

        for(int i = arrayStartValue ; i < getSize(); i++) {
            cardsString = cardsString.concat(getCardAtIndex(i).toString());
            if(i+1 < getSize()){
                cardsString = cardsString.concat(", ");
            }
        }
        return cardsString;
    }
}
