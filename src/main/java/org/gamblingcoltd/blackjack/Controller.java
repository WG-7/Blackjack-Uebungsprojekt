package org.gamblingcoltd.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class Controller {
    public HBox cardBoxPlayer;
    public HBox cardBoxDealer;
    @FXML
    private Label welcomeText;
    private BlackjackManager blackjackManager;
    private Game currentGame;

    public Controller(){
        blackjackManager = BlackjackManager.getInstance();
        blackjackManager.initilizeAndRunBlackjack();
        currentGame = blackjackManager.gameHistory.get(blackjackManager.gameHistory.size()-1);
    }

    public void setBet(){
        currentGame.getCurrenPlayerHand().setBet(10);
        loadCards();
    }
    public void drawCard(MouseEvent actionEvent) {
        currentGame.hit();
        loadCards();
    }

    private void loadCards(){
        cardBoxDealer.getChildren().clear();

        //Player
        Hand dealerHand = currentGame.getDealerHand();
        for(int i = 0; i < dealerHand.getSize();i++)
        {
            String cardUrl = dealerHand.getCardAtIndex(i).getUrl();
            System.out.println(cardUrl);
            Image card = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/"+cardUrl)));
            ImageView cardView = new ImageView(card);
            cardView.setFitWidth(200);
            cardView.setFitHeight(290.4);
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
            System.out.println(cardUrl);
            Image card = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/"+cardUrl)));
            ImageView cardView = new ImageView(card);
            cardView.setFitWidth(200);
            cardView.setFitHeight(290.4);
            cardView.setPreserveRatio(true);

            cardBoxPlayer.getChildren().add(cardView);
        }
        adjustCardPositions(cardBoxPlayer);
    }

    private void adjustCardPositions(HBox pHBox) {
        HBox cardBox = pHBox;

        double overlap = 160; // Ihr gewünschter Überlappungswert
        //(cardBoxPlayer width in px - px width of hand)/2
        double standartRightShift = (cardBox.getWidth()-((cardBox.getChildren().size()*40)+overlap))/2;

        for (int i = 0; i < cardBox.getChildren().size(); i++) {
            Node child = cardBox.getChildren().get(i);
            double translateX = i * overlap;
            child.setTranslateX(-translateX+standartRightShift);
        }
    }
    @FXML
    public void handleShowInstructions(ActionEvent actionEvent) {
    }
    @FXML
    public void handleResetGame(ActionEvent actionEvent) {
    }

    public void handleHit(ActionEvent actionEvent) {
    }

    public void handleStand(ActionEvent actionEvent) {
    }

    public void handleDoublee(ActionEvent actionEvent) {
    }

    public void handleSplit(ActionEvent actionEvent) {
    }
}