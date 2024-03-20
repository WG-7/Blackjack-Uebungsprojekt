package org.gamblingcoltd.blackjack;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackManager {
    private Player player;
    private ArrayList<Player> allPlayers;
    public ArrayList<Game> gameHistory;
    private final static BlackjackManager instance = new BlackjackManager();
    private BlackjackManager(){
        gameHistory = new ArrayList<>();
        allPlayers = new ArrayList<>();
    }

    public void initilizeAndRunBlackjack(){
        Game game = new Game(player);
        game.startAndExcuteGame();
        gameHistory.add(game);
    }
    public void login(String pUsername){
        for(Player searchedPlayer : allPlayers){
            if (this.player == null) {
                continue;
            }
            if(player.getName().equals(pUsername)){
                player = searchedPlayer;
                return;
            }
        }
        Player newPlayer = new Player(pUsername, 5000);
        allPlayers.add(newPlayer);
        player = newPlayer;
    }
    public void reset(){
        gameHistory.get(gameHistory.size()-1).endGame();
        player.setBalance(5000);
        Game game = new Game(player);
        game.startAndExcuteGame();
        gameHistory.add(game);
    }
    public void showTutorial(){
        System.out.println("Platzhalter Tutorial");
    }

    public ArrayList<Game> getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(ArrayList<Game> gameHistory) {
        this.gameHistory = gameHistory;
    }
    public void setPlayer(Player pPlayer){
        player = pPlayer;
    }
    public Player getPlayer(){
        return player;
    }
    public ArrayList<Player> getAllPlayers(){
        return allPlayers;
    }

    public static BlackjackManager getInstance(){
        return instance;
    }
}