package org.gamblingcoltd.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Game_Controller {
    private Stage mainStage;
    private Scene bettingScene, loginScene, gameScene;
    private Map<Button, Integer> betButtons;
    public Label LbBalance;
    public Label LbPlayerName;
    public HBox cardBoxPlayer;
    public HBox cardBoxDealer;
    public Label cardValueDealer;
    public Label handId;
    public Label cardValuePlayer;
    public Label bet;
    public TextField TfUsername;

    @FXML
    private Button betButton;
    @FXML
    private Button hitButton;
    @FXML
    private Button standButton;
    @FXML
    private Button doubleButton;
    @FXML
    private Button splitButton;
    @FXML
    private Button insureButton;

    private BlackjackManager blackjackManager;
    private Game currentGame;
    public Game_Controller(){
        blackjackManager = BlackjackManager.getInstance();
        currentGame = blackjackManager.gameHistory.get(blackjackManager.gameHistory.size()-1);
    }
    private void initialize() {
        loadUI();
    }
    @FXML
    public void handleShowInstructions(ActionEvent actionEvent) {
    }
    @FXML
    public void handleResetGame(ActionEvent actionEvent) {
    }
    @FXML
    public void handleBet(){
        currentGame.setBet(10);
        currentGame.dealStartingCards();
        loadUI();
    }
    @FXML
    public void handleHit(ActionEvent actionEvent) {
        currentGame.hit();
        loadUI();
    }
    @FXML
    public void handleStand(ActionEvent actionEvent) {
        currentGame.stand();
        loadUI();
    }
    @FXML
    public void handleDouble(ActionEvent actionEvent) {
        currentGame.doubleDown();
        loadUI();
    }
    @FXML
    public void handleSplit(ActionEvent actionEvent) {
        currentGame.split();
        loadUI();
    }
    @FXML
    public void handleInsure(ActionEvent actionEvent) {
        currentGame.insure();
        loadUI();
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

        // Label DealerHand
        cardValueDealer.setText("Kartenwert: "+currentGame.getDealerHand().getHandValue());
    }

    private void updateButtonStates() {
        hitButton.setDisable(currentGame.getCurrenPlayerHand().getHandFinalised());
        standButton.setDisable(currentGame.getCurrenPlayerHand().getHandFinalised());
        doubleButton.setDisable(!currentGame.getCurrenPlayerHand().isDoubleAvailable());
        splitButton.setDisable(!currentGame.getCurrenPlayerHand().isSplitAvailable());
        insureButton.setDisable(!currentGame.isInsureAvailable());
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
                card = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/red_joker.png")));
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