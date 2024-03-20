package org.gamblingcoltd.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Betting_Controller {
    @FXML
    public Label LbBetScreenPlayerName, LbBetSum, LbBettingBalance;
    @FXML
    Button ChipValue10, ChipValue25, ChipValue50, ChipValue100, ChipValue500, handlePlayGame;
    private BlackjackManager blackjackManager;
    private Map<Button, Integer> betButtons;
    public Betting_Controller(){
        betButtons = new HashMap<>();
        betButtons.put(ChipValue10, 10);
        betButtons.put(ChipValue25, 25);
        betButtons.put(ChipValue50, 50);
        betButtons.put(ChipValue100, 100);
        betButtons.put(ChipValue500, 500);
        blackjackManager = BlackjackManager.getInstance();
    }
    public void initialize() {
        updateBettingUI();
    }
    @FXML
    public void handleChipValue10(ActionEvent actionEvent) {
        addBetAndUpdateUI(10);
    }
    @FXML
    public void handleChipValue25(ActionEvent actionEvent) {
        addBetAndUpdateUI(25);
    }
    @FXML
    public void handleChipValue50(ActionEvent actionEvent) {
        addBetAndUpdateUI(50);
    }
    @FXML
    public void handleChipValue100(ActionEvent actionEvent) {
        addBetAndUpdateUI(100);
    }
    @FXML
    public void handleChipValue500(ActionEvent actionEvent) {
        addBetAndUpdateUI(500);
    }
    @FXML
    public void handlePlayGame(ActionEvent actionEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("game_view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(newRoot);
    }
    public void addBetAndUpdateUI(int amount){
        blackjackManager.getGameHistory().get(blackjackManager.getGameHistory().size()-1).increaseBet(amount);
        updateBettingUI();
    }
    public void updateBettingUI(){
        deactivateButtonsWithTooHighValue();
        LbBetScreenPlayerName.setText("Name: " + blackjackManager.getPlayer().getName());
        LbBettingBalance.setText("Guthaben: " + blackjackManager.getPlayer().getBalance());
        LbBetSum.setText(blackjackManager.getGameHistory().get(blackjackManager.getGameHistory().size()-1).getCurrenPlayerHand().getBet() + "$");
    }
    public void deactivateButtonsWithTooHighValue(){
        double playerBalance = blackjackManager.getPlayer().getBalance();
        Set<Button> keys = betButtons.keySet();
        boolean disable = false;
        for (Button key : keys) {
            if(key == null) continue;
            if(playerBalance < betButtons.get(key)){
                disable = true;
            }
            key.setDisable(disable);
        }
    }

    public void handleAllIn(ActionEvent actionEvent) {
        addBetAndUpdateUI((int) blackjackManager.getPlayer().getBalance());
    }

}
