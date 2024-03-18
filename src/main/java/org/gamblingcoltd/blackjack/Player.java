package org.gamblingcoltd.blackjack;

public class Player {
    private String name;
    private double balance;
    public Player(String pName, double pBalance) {
        name = pName;
        balance = pBalance;
    }

    public String getName(){
        return name;
    }
    public double getBalance(){
        return balance;
    }

    public void setName(String pName){
        name = pName;
    }
    public void setBalance(double pBalance) {
        balance = pBalance;
    }
    public void changeBalance(double pBalance) {
        balance = balance + pBalance;
    }
}
