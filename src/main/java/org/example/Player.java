package org.example;

public class Player {
    private String name;
    private int balance;
    public Player(String pName, int pBalance) {
        name = pName;
        balance = pBalance;
    }

    public String getName(){
        return name;
    }
    public int getBalance(){
        return balance;
    }

    public void setName(String pName){
        name = pName;
    }
    public void setBalance(int pBalance) {
        balance = pBalance;
    }
}
