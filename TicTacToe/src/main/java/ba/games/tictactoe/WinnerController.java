package ba.games.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WinnerController {
    public VBox winnerWindow;
    @FXML
    private Button exitButton;
    @FXML
    private Button playAgainButton;
    @FXML
    private Label winnerLabel;
    private Stage primStage;
    private Controller controller;
    public void playAgainClick(MouseEvent mouseEvent) {
        controller.cleanup();
        Stage root = (Stage) winnerWindow.getScene().getWindow();
        root.close();
    }

    public void exitClick(MouseEvent mouseEvent) {
        Stage thisWindow = (Stage) winnerWindow.getScene().getWindow();
        thisWindow.close();
        if(primStage != null) {
            primStage.close();
        }
    }
    public void setPrimaryStage(Stage st) {
        primStage = st;
    }
    public void setLabelText(String text) {
        winnerLabel.setText(text);
    }
    public void setController(Controller c) {
        controller = c;
    }
}
