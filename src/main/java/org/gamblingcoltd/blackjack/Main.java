package org.gamblingcoltd.blackjack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private Stage stage;
    Scene gameScene;
    Scene bettingScene;
    Scene loginScene;
    @Override
    public void start(Stage pStage) throws IOException {
        stage = pStage;

//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("game_view.fxml"));
//        Parent root = fxmlLoader.load();
//        Scene gameScene = new Scene(root);

        FXMLLoader fxmlLoader2 = new FXMLLoader(Main.class.getResource("login_view.fxml"));
        Parent root2 = fxmlLoader2.load();
        Scene loginScene = new Scene(root2);

        stage.setFullScreen(false);
        stage.setFullScreenExitHint("");
        stage.setTitle("Blackjack");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Scene getBettingScene() {
        return bettingScene;
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public Scene getLoginScene() {
        return loginScene;
    }

    public Stage getStage() {
        return stage;
    }
}