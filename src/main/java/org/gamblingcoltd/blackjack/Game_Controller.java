package org.gamblingcoltd.blackjack;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.*;

public class Game_Controller implements GameUpdateListener{
    @FXML
    public Label LbBalance, cardValueDealer, cardValuePlayer, bet, LbPlayerName, handId;
    @FXML
    public HBox cardBoxPlayer, cardBoxDealer;
    @FXML
    private Button hitButton, standButton, doubleButton, splitButton, insureButton, nextButton;

    private BlackjackManager blackjackManager;
    private Game currentGame;
    public Game_Controller(){
        blackjackManager = BlackjackManager.getInstance();
        currentGame = blackjackManager.gameHistory.get(blackjackManager.gameHistory.size()-1);
        currentGame.setUpdateListener(this);
    }

    @Override
    public void updateUI() {
        loadUI();
    }
    @Override
    public void printWinMessage(double pAmount, int pHandIndex, String pMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Payout Info");
        alert.setHeaderText(pMessage);
        if(pMessage.equals("Insurance Lost")) {
            alert.setContentText("You lost your Insurance");
        } else if (pMessage.equals("Insurance Won")) {
            alert.setContentText("You won your Insurance and got "+pAmount+"€");
        } else if (pAmount != 0){
            alert.setContentText("Hand NR "+(pHandIndex+1)+" : "+pAmount+"€");
        } else{
            alert.setContentText("Hand NR "+(pHandIndex+1)+" : You got your Bet back");
        }

        Scene currentScene = hitButton.getScene();
        alert.initOwner(currentScene.getWindow());
        alert.showAndWait();
    }

    public void initialize() {
        currentGame.dealStartingCards();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(event -> {
            loadUI();
        });
        pause.play();

    }

    @FXML
    public void handleShowInstructions(ActionEvent actionEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("tutorial_view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(newRoot);
    }
    @FXML
    public void handleResetGame(ActionEvent actionEvent) throws IOException {
        //set back bet if Hand was not finalised
        Hand currentHand = blackjackManager.getGameHistory().get(blackjackManager.getGameHistory().size()-1).getCurrenPlayerHand();
        if(!currentHand.getHandFinalised()) {
            int amount = currentHand.getBet();
            blackjackManager.getGameHistory().get(blackjackManager.getGameHistory().size()-1).increaseBet(-amount);
        }
        //load new game
        blackjackManager.beginNewGame();

        Parent newRoot = FXMLLoader.load(getClass().getResource("betting_view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(newRoot);
    }

    @FXML
    public void handleHit(ActionEvent actionEvent) {
        currentGame.hit();
    }
    @FXML
    public void handleStand(ActionEvent actionEvent) {
        currentGame.stand();
    }
    @FXML
    public void handleDouble(ActionEvent actionEvent) {
        currentGame.doubleDown();
    }
    @FXML
    public void handleSplit(ActionEvent actionEvent) {
        currentGame.split();
    }
    @FXML
    public void handleInsure(ActionEvent actionEvent) {
        currentGame.insure();
    }

    public void handleNextHand(ActionEvent actionEvent) {
        currentGame.endCurrentHand();
    }

    private void loadUI(){
        updateButtonStates();
        loadCards();

        //Name and Balance
        LbPlayerName.setText(currentGame.getPlayer().getName());
        LbBalance.setText(""+currentGame.getPlayer().getBalance());

        // Label PlayerHand
        handId.setText("Hand "+(currentGame.getCurrentHandIndex()+1)+" von "+currentGame.getPlayerHandSize());
        cardValuePlayer.setText("Kartenwert: "+currentGame.getCurrenPlayerHand().getHandValue());
        bet.setText("Einsatz: "+currentGame.getCurrenPlayerHand().getBet());

        System.out.println(currentGame.getCurrenPlayerHand());
        System.out.println(currentGame.getCurrenPlayerHand().getBet());
        System.out.println(currentGame.getCurrentHandIndex());
        System.out.println(currentGame.getDealerHand().getBet());

        // Label DealerHand
        cardValueDealer.setText("Kartenwert: "+currentGame.getDealerHand().getHandValue());
    }

    private void updateButtonStates() {
        hitButton.setDisable(currentGame.getCurrenPlayerHand().getHandFinalised());
        standButton.setDisable(currentGame.getCurrenPlayerHand().getHandFinalised());
        doubleButton.setDisable(!currentGame.getCurrenPlayerHand().isDoubleAvailable());
        splitButton.setDisable(!currentGame.getCurrenPlayerHand().isSplitAvailable());
        insureButton.setDisable(!currentGame.isInsureAvailable());
        nextButton.setDisable(!currentGame.getCurrenPlayerHand().getHandFinalised());
    }

    private void loadCards(){
        // get screen width
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
//        double screenWidth = 1080;
//        double screenHeight = 1920;

        cardBoxDealer.getChildren().clear();

        //modify according to screenHeight
        cardBoxDealer.minHeight(screenHeight*0.3);
        cardBoxDealer.maxHeight(screenHeight*0.3);
        cardBoxPlayer.minHeight(screenHeight*0.3);
        cardBoxPlayer.maxHeight(screenHeight*0.3);
        double cardHeight = screenHeight*0.3;
        double cardWidth = screenWidth*0.15;

        //Dealer
        Hand dealerHand = currentGame.getDealerHand();
        for(int i = 0; i < dealerHand.getSize();i++)
        {
            String cardUrl ="";
            Image card;
            if(dealerHand.getBet()==-1 && i==0){
                card = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/Backside.png")));
            } else {
                cardUrl = dealerHand.getCardAtIndex(i).getUrl();
                card = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/"+cardUrl)));
            }
            ImageView cardView = new ImageView(card);
            cardView.setFitWidth(cardWidth);
            cardView.setFitHeight(cardHeight);
            cardView.setPreserveRatio(true);
            cardBoxDealer.getChildren().add(cardView);
        }
        adjustCardPositions(cardBoxDealer,cardWidth);

        //Player
        cardBoxPlayer.getChildren().clear();
        Hand currentPlayerHand = currentGame.getCurrenPlayerHand();
        for(int i = 0; i < currentPlayerHand.getSize();i++)
        {
            String cardUrl = currentPlayerHand.getCardAtIndex(i).getUrl();
            Image card = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/"+cardUrl)));
            ImageView cardView = new ImageView(card);
//            cardView.setFitWidth(200);
//            cardView.setFitHeight(290.4);
            cardView.setFitWidth(cardWidth);
            cardView.setFitHeight(cardHeight);
            cardView.setPreserveRatio(true);

            cardBoxPlayer.getChildren().add(cardView);
        }
        adjustCardPositions(cardBoxPlayer, cardWidth);
    }

    private void adjustCardPositions(HBox pHBox,double cardWidth) {
        HBox cardBox = pHBox;

        double noOverlap = cardWidth*0.2;
        double overlap = cardWidth*0.8;

        //(cardBoxPlayer width in px - px width of hand)/2
        double standartRightShift = (cardBox.getWidth()-((cardBox.getChildren().size()*noOverlap)+overlap))/2;

        for (int i = 0; i < cardBox.getChildren().size(); i++) {
            Node child = cardBox.getChildren().get(i);
            double translateX = i * 100;
            child.setTranslateX(-translateX+standartRightShift);
        }
    }



}