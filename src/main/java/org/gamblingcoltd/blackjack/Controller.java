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

    private int testCounter = 1;

    public void drawCard(MouseEvent actionEvent) {
        testCounter++;
        Image card = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/"+testCounter+"_of_hearts.png")));
        ImageView cardView = new ImageView(card);
        cardView.setFitWidth(200);
        cardView.setFitHeight(290.4);
        cardView.setPreserveRatio(true);

        cardBox.getChildren().add(cardView);
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
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    public void handleShowInstructions(ActionEvent actionEvent) {
    }
    @FXML
    public void handleResetGame(ActionEvent actionEvent) {
    }
}