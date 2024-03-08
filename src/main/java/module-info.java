module ba.games.chess.chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ba.games.chess.chess to javafx.fxml;
    exports ba.games.chess.chess;
}