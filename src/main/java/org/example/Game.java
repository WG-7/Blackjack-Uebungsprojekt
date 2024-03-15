package org.example;

import java.util.Random;

public class Game {
    Random rand = new Random();
    private Player player;


    public Card getRandomCard(){
        CardSymbol r_symbol = CardSymbol.values()[rand.nextInt(4)];
        CardValue r_value = CardValue.values()[rand.nextInt(13)];
        return new Card(r_symbol,r_value);
    }
}
