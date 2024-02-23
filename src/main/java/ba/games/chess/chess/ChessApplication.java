package ba.games.chess.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class ChessApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 500 pixels is set as default in fxml so we will use them as min width and height
        final int minWindowDimension = 500;
        // 815 will be our full window
        final int maxWindowDimensions = 815;

        FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("fxml/mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
        stage.setTitle("Chess");
        stage.setScene(scene);
        stage.setMinHeight(minWindowDimension);
        stage.setMinWidth(minWindowDimension);
        stage.setMaxWidth(maxWindowDimensions);
        stage.setMaxHeight(maxWindowDimensions);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}