package org.example;
import java.util.Random;

public class Card {
    private CardSymbol symbol;
    private CardValue value;


    public Card(CardSymbol symbol, CardValue value){
        this.symbol = symbol;
        this.value = value;
    }

    public CardSymbol getSymbol(){
        return this.symbol;
    }
    public CardValue getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return "Symbol: "+symbol+"\t Value: "+value;
    }
}
