package org.gamblingcoltd.blackjack;
import java.util.ArrayList;
import java.util.Scanner;

public class BlackjackManager {
    private Player player;
    private ArrayList<Player> allPlayers;
    private ArrayList<Game> gameHistory;
    private static BlackjackManager instance;
    private BlackjackManager(){
        gameHistory = new ArrayList<>();
        allPlayers = new ArrayList<>();
        initilizeAndRunBlackjack();
    }

    public void initilizeAndRunBlackjack(){
        login();
        Game game = new Game(player);
        game.startAndExcuteGame();
        gameHistory.add(game);
    }
    public void login(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bitte geben Sie ihren Nutzernamen ein:");
        String namenEingabe = scanner.nextLine();
        player = new Player(namenEingabe, 5000);
        allPlayers.add(player);

        scanner.close();
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

    public static BlackjackManager getInstance(){
        if (instance == null) {
            instance = new BlackjackManager();
        }
        return instance;
    }
}