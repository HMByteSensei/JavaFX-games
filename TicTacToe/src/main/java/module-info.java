module ba.games.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens ba.games.tictactoe to javafx.fxml;
    exports ba.games.tictactoe;
}