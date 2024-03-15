package org.example;
import java.util.Random;

public class Card {
    private CardSymbol symbol;
    private CardValue value;


    public Card(CardSymbol pSymbol, CardValue pValue){
        symbol = pSymbol;
        value = pValue;
    }

    public CardSymbol getSuit(){
        return symbol;
    }
    public CardValue getRank(){
        return value;
    }
//    public CardValue getValue(){
//        return (int)value;
//    }

    @Override
    public String toString() {
        return "Symbol: "+symbol+"\tValue: "+value;
    }
}
