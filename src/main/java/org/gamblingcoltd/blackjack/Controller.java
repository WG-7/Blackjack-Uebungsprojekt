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
    public HBox cardBox;
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
        cardBox.getChildren().clear();

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

            cardBox.getChildren().add(cardView);
        }
        adjustCardPositions();
    }

    private void adjustCardPositions() {
        double overlap = 160; // Ihr gewünschter Überlappungswert

        double standartRightShift = (cardBox.getChildren().size()/2.0)*100;

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

    public void handleDouble(ActionEvent actionEvent) {
    }

    public void handleSplit(ActionEvent actionEvent) {
    }

    public void handleInsure(ActionEvent actionEvent) {
    }
}