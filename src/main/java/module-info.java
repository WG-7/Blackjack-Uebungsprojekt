module org.gamblingcoltd.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.gamblingcoltd.blackjack to javafx.fxml;
    exports org.gamblingcoltd.blackjack;
}