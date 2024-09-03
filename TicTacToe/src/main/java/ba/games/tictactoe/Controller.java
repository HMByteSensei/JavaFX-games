package ba.games.tictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Field;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller {
    public GridPane gridPane;
    @FXML
    private Label cell00, cell01, cell02;
    @FXML
    private Label cell10, cell11, cell12;
    @FXML
    private Label cell20, cell21, cell22;
    private ObservableList<ObservableList<StringProperty>> board;
    private GameLogic gameLogic;
    private Stage primaryStage;
    public void initialize() {
        initializeGame();
    }
    public void initializeGame() {
        board = FXCollections.observableArrayList();
        for(int i=0; i<3; i++) {
            ObservableList<StringProperty> row = FXCollections.observableArrayList();
            for(int j=0; j<3; j++) {
                row.add(new SimpleStringProperty(""));
            }
            board.add(row);
        }

        cell00.textProperty().bind(board.get(0).get(0));
        cell01.textProperty().bind(board.get(0).get(1));
        cell02.textProperty().bind(board.get(0).get(2));

        cell10.textProperty().bind(board.get(1).get(0));
        cell11.textProperty().bind(board.get(1).get(1));
        cell12.textProperty().bind(board.get(1).get(2));

        cell20.textProperty().bind(board.get(2).get(0));
        cell21.textProperty().bind(board.get(2).get(1));
        cell22.textProperty().bind(board.get(2).get(2));
        gameLogic = new GameLogic(board);
        setHandler();
    }

    public void setHandler() {
        cell00.setOnMouseClicked(event -> mouseClick(0, 0));
        cell01.setOnMouseClicked(event -> mouseClick(0, 1));
        cell02.setOnMouseClicked(event-> mouseClick(0, 2));

        cell10.setOnMouseClicked(event -> mouseClick(1, 0));
        cell11.setOnMouseClicked(event -> mouseClick(1, 1));
        cell12.setOnMouseClicked(event -> mouseClick(1, 2));

        cell20.setOnMouseClicked(event -> mouseClick(2, 0));
        cell21.setOnMouseClicked(event -> mouseClick(2, 1));
        cell22.setOnMouseClicked(event -> mouseClick(2, 2));
    }
    public void cleanup() {
        board = null;
        gameLogic = null;
        initializeGame();
    }
    public void removeHandler(int row, int col) {
        String fieldName = "cell" + row + col;
        try {
            Field field = getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Label label = (Label) field.get(this);
            label.setOnMouseClicked(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void mouseClick(int row, int col) {
        String win = gameLogic.makeMove(row, col);
        removeHandler(row, col);
        if(!win.isBlank()) {
            winnerWindow(win);
        }
    }
    public void winnerWindow(String winner) {
        Stage st = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TicTacToe.class.getResource("winner-view.fxml"));
            // Set as MODAL WINDOW
            st.initModality(Modality.APPLICATION_MODAL);
            st.initOwner(primaryStage);
            //--------
            Scene scene = new Scene(fxmlLoader.load());
            st.setTitle("Winner");
            st.setScene(scene);
            st.setResizable(false);

            WinnerController winnerController = fxmlLoader.getController();
            // set up all parameters needed for winner controller
            winnerController.setPrimaryStage(primaryStage);
            winnerController.setLabelText(winner);
            winnerController.setController(this);
            st.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
                winnerController.exitClick(null);
            });
            st.showAndWait();
        } catch (IOException e) {
            e.getMessage();
        }
    }
    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
}