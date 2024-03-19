package org.gamblingcoltd.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

import java.util.Objects;

public class Controller {
    public Label LbPlayerName;
    public Label LbBalance;

    public HBox cardBoxPlayer;
    public HBox cardBoxDealer;
    public Label cardValueDealer;
    public Label handId;
    public Label cardValuePlayer;
    public Label bet;

    private BlackjackManager blackjackManager;
    private Game currentGame;
    public Controller(){
        blackjackManager = BlackjackManager.getInstance();
        blackjackManager.initilizeAndRunBlackjack();
        currentGame = blackjackManager.gameHistory.get(blackjackManager.gameHistory.size()-1);
    }

    @FXML
    public void handleShowInstructions(ActionEvent actionEvent) {
    }
    @FXML
    public void handleResetGame(ActionEvent actionEvent) {

    }

    public void setBet(){
        currentGame.setBet(10);
        loadUI();
    }

    public void handleHit(ActionEvent actionEvent) {
        currentGame.hit();
        loadUI();
    }

    public void handleStand(ActionEvent actionEvent) {
        currentGame.stand();
        loadUI();
    }

    public void handleDouble(ActionEvent actionEvent) {
        currentGame.doubleDown();
        loadUI();
    }

    public void handleSplit(ActionEvent actionEvent) {
        currentGame.split();
        loadUI();
    }

    public void handleInsure(ActionEvent actionEvent) {
        currentGame.insure();
        loadUI();
    }

    private void loadUI(){
        loadCards();

        //Name and Balance
        LbPlayerName.setText(currentGame.getPlayer().getName());
        LbBalance.setText(""+currentGame.getPlayer().getBalance());

        // Label PlayerHand
        handId.setText("Hand "+currentGame.getCurrentHandIndex()+1+" von "+currentGame.getPlayerHandSize());
        cardValuePlayer.setText("Kartenwert: "+currentGame.getCurrenPlayerHand().getHandValue());
        bet.setText("Einsatz: "+currentGame.getCurrenPlayerHand().getBet());

        // Label DealerHand
        cardValueDealer.setText("Kartenwert: "+currentGame.getDealerHand().getHandValue());

        loadCards();
    }

    private void loadCards(){
        // get screen width
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();


        cardBoxDealer.getChildren().clear();

        //Player
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
            cardView.setFitWidth(screenWidth*0.1);
            cardView.setFitHeight(screenHeight*0.2);
            cardBoxDealer.minHeight(screenHeight*0.2);
            cardBoxDealer.maxHeight(screenHeight*0.2);
            cardView.setPreserveRatio(true);
            cardBoxDealer.getChildren().add(cardView);
        }
        adjustCardPositions(cardBoxDealer);

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
            cardView.setFitWidth(screenWidth*0.1);
            cardView.setFitHeight(screenHeight*0.2);
            cardBoxPlayer.minHeight(screenHeight*0.2);
            cardBoxPlayer.maxHeight(screenHeight*0.2);
            cardView.setPreserveRatio(true);

            cardBoxPlayer.getChildren().add(cardView);
        }
        adjustCardPositions(cardBoxPlayer);
    }

    private void adjustCardPositions(HBox pHBox) {
        HBox cardBox = pHBox;
        double overlap = 160;
        //(cardBoxPlayer width in px - px width of hand)/2
        double standartRightShift = (cardBox.getWidth()-((cardBox.getChildren().size()*40)+overlap))/2;

        for (int i = 0; i < cardBox.getChildren().size(); i++) {
            Node child = cardBox.getChildren().get(i);
            double translateX = i * overlap;
            child.setTranslateX(-translateX+standartRightShift);
        }
    }
}