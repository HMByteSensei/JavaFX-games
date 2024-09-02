package ba.games.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Controller {
    @FXML
    private Label cell00, cell01, cell02;
    @FXML
    private Label cell10, cell11, cell12;
    @FXML
    private Label cell20, cell21, cell22;

    public void mouseClick(MouseEvent mouseEvent) {
        cell00.setText("neki txt");
    }
}