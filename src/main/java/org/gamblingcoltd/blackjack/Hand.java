package org.gamblingcoltd.blackjack;
import java.util.ArrayList;
import java.util.Random;

public class Hand {
    private Random rand = new Random();
    private ArrayList<Card> cards;
    private int bet;

    public Hand() {
        cards = new ArrayList<>();
    }

    public int getBet(){
        return bet;
    }
    public void setBet(int pAmount){
        bet = pAmount;
    }

    public int getHandValue(){
        int totalHandValue = 0;
        int amountOfAces = 0;

        for(int i=0; i < cards.size();i++)
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

    public void newCard(){
        cards.add(getRandomCard());
    }
    private Card getRandomCard(){
        CardSuit rSuit = CardSuit.values()[rand.nextInt(4)];
        CardRank rRank = CardRank.values()[rand.nextInt(13)];
        return new Card(rSuit,rRank);
    }

    public boolean isBust(){
        return getHandValue()>21;
    }
    public boolean isSplitAvailable(){
        //returns if 2 cards and equalValue
        return cards.size() == 2 && cards.get(0).getValue() == cards.get(1).getValue();
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
        return "";
    }
}
