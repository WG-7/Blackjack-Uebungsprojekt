package org.gamblingcoltd.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import java.util.*;

public class Game_Controller implements GameUpdateListener{
    @FXML
    public Label LbBalance, cardValueDealer, cardValuePlayer, bet, LbPlayerName, handId;
    @FXML
    public HBox cardBoxPlayer, cardBoxDealer;
    @FXML
    private Button hitButton, standButton, doubleButton, splitButton, insureButton;

    private BlackjackManager blackjackManager;
    private Game currentGame;
    public Game_Controller(){
        blackjackManager = BlackjackManager.getInstance();
        blackjackManager.login("Malte");
        blackjackManager.initilizeAndRunBlackjack();
        currentGame = blackjackManager.gameHistory.get(blackjackManager.gameHistory.size()-1);
        currentGame.setUpdateListener(this);
    }
    @Override
    public void updateUI() {
        loadUI();
    }
    public void initialize() {
        loadUI();
    }

    @FXML
    public void handleShowInstructions(ActionEvent actionEvent) {
    }
    @FXML
    public void handleResetGame(ActionEvent actionEvent) {
        System.out.println("Rest");
        blackjackManager.reset();
        blackjackManager.initilizeAndRunBlackjack();
        currentGame = blackjackManager.gameHistory.get(blackjackManager.gameHistory.size()-1);
        currentGame.setUpdateListener(this);
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