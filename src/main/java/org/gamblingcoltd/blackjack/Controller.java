package org.gamblingcoltd.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private Label welcomeText;
    @FXML
    private ImageView imagedealer1;
    @FXML
    private ImageView imagedealer2;

    @FXML
    private void initialize() {
        Image image = new Image(getClass().getResourceAsStream("/org/gamblingcoltd/blackjack/CardPNG/2_of_clubs.png"));
        imagedealer1.setImage(image);
        imagedealer2.setImage(image);
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