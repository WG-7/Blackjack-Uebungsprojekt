package org.example;

public class Player {
    private String name;
    private int balance;
    public Player(String pName, int pBalance) {
        name = pName;
        balance = pBalance;
    }

    private String getName(){
        return name;
    }
    private int getBalance(){
        return balance;
    }

    private void setName(String pName){
        name = pName;
    }
    private void setBalance(int pBalance) {
        balance = pBalance;
    }
}
