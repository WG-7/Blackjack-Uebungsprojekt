package org.gamblingcoltd.blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("game_view.fxml"));
        Parent root = fxmlLoader.load();
        Scene GameScene = new Scene(root);

        FXMLLoader fxmlLoader2 = new FXMLLoader(Main.class.getResource("game_view.fxml"));
        Parent root2 = fxmlLoader2.load();
        Scene bettingScene = new Scene(root2);

        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setTitle("Blackjack");
        stage.setScene(GameScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}