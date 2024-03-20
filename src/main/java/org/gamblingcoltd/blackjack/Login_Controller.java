package org.gamblingcoltd.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login_Controller {
    BlackjackManager blackjackManager;
    @FXML
    TextField TfUsername;
    public Login_Controller(){
        blackjackManager = BlackjackManager.getInstance();
    }
    public void handleLogin(ActionEvent actionEvent) throws IOException {
        blackjackManager.login(TfUsername.getText());
        blackjackManager.initilizeAndRunBlackjack();

        Parent newRoot = FXMLLoader.load(getClass().getResource("betting_view.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene currentScene = stage.getScene();
        currentScene.setRoot(newRoot);
    }
}
