package ba.games.chess.chess;

import figureClasses.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

import static javafx.geometry.Pos.CENTER;

public class ChessLogic {
    private static GridPane tabla;
    private static Figure[][] figure;
    private static boolean clicked = false;
    public ChessLogic(GridPane tabla) {
        this.tabla = tabla;
        // 4 rows which represents the rows and columns of figures; 1. and 2. rows are pawns
        // first 2 rows black figures, second 2 for white figures
        figure = new Figure[4][8];
        drawTable();
        initFigures();

    }

    public void drawTable() {
        for(int i=0; i<tabla.getRowCount(); i++) {
            for(int j=0; j<tabla.getColumnCount(); j++) {
                VBox cell = new VBox();
                if((i % 2 != 0 || j % 2 != 0) && !(i % 2 != 0 && j % 2 != 0)) {
                    cell.getStyleClass().add("pozadina");
                }
                cell.setAlignment(CENTER);
                GridPane.setRowIndex(cell, i);
                GridPane.setColumnIndex(cell, j);
                tabla.getChildren().add(cell);
            }
        }
    }

    public void initFigures() {
        // I will initialize rook in king class, although it would be cleaner if I initialize it here
        for(int i=0; i<8; i++) {
            figure[1][i] = new Pawn(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\black_pawn.png", (VBox) tabla.getChildren().get(8 + i), tabla,"Black");
            // 48 + i because that is the cell where white figure starts
            figure[2][i] = new Pawn(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\white_pawn.png", (VBox) tabla.getChildren().get(48 + i), tabla,"White");
        }
        for(int i=1; i<=6; i+=5) {
            figure[0][i] = new Horse(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\black_horse.png", (VBox) tabla.getChildren().get(i), tabla, "Black");
            // 56 + i (because of formula "8*i+j"
            figure[3][i] = new Horse(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\white_horse.png", (VBox) tabla.getChildren().get(56 + i), tabla, "White");
        }
        for(int i=2; i<=5; i+=3) {
            figure[0][i] = new Hunter(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\black_hunter.png", (VBox) tabla.getChildren().get(i), tabla, "Black");
            figure[3][i] = new Hunter(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\white_hunter.png", (VBox) tabla.getChildren().get(56 + i), tabla, "White");
        }
        figure[0][3] = new Queen(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\black_queen.png", (VBox) tabla.getChildren().get(3), tabla,"Black");
        figure[3][3] = new Queen(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\white_queen.png", (VBox) tabla.getChildren().get(59), tabla,"White");

//        VBox kingCell = (VBox) tabla.getChildren().get(4);
        figure[0][4] = new King(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\black_king.png", (VBox) tabla.getChildren().get(4), tabla,"Black");
//        kingCell = (VBox) tabla.getChildren().get(60);
        figure[3][4] = new King(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\white_king.png", (VBox) tabla.getChildren().get(60), tabla,"White");
    }
    public static void evolvePawn(VBox cell, Figure pawn) {
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChessApplication.class.getResource("fxml/evolveView.fxml"));
            // Set as MODAL WINDOW
            GridPane evolve = fxmlLoader.load();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(cell.getScene().getWindow());
            //--------
            Scene scene = new Scene(evolve);
            stage.setTitle("Chose to evolve");
            stage.setScene(scene);
            stage.setResizable(false);
            cell.getChildren().remove(0);

            addImageToEvolveView("first", evolve, pawn, "horse", cell);
            addImageToEvolveView("second", evolve, pawn, "hunter", cell);
            addImageToEvolveView("third", evolve, pawn, "rook", cell);
            addImageToEvolveView("fourth", evolve, pawn, "queen", cell);

            stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
                event.consume();
            });
            stage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> closeWindow(event));
            stage.showAndWait();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void addImageToEvolveView(String rowField, GridPane evolve, Figure pawn, String figura, VBox cell) {
        VBox first = (VBox) evolve.lookup("#" + rowField);
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\" + pawn.getColor() + "_" + figura + ".png";
        ImageView i1 = new ImageView(path);
        first.getChildren().add(i1);
        first.setOnMouseClicked(event -> choseFigure(event,path, cell, pawn, figura));
    }

    public static void choseFigure(MouseEvent event, String path, VBox cell, Figure pawn, String figura) {
        Figure fig;
        switch(figura) {
            case "horse":
                fig = new Horse(path, cell, (GridPane) cell.getParent(), pawn.getColor());
                break;
            case "hunter":
                fig = new Hunter(path, cell, tabla, pawn.getColor());
                break;
            case "rook":
                fig = new Rook(path, cell, tabla, pawn.getColor());
                break;
            default:
                fig = new Queen(path, cell, tabla, pawn.getColor());
                break;
        }
        int figureRowIndex = (pawn.getColor() == "White") ? 2 : 1;
        for(int i=0; i<8; i++) {
            if (pawn.equals(figure[figureRowIndex][i])) {
                figure[figureRowIndex][i] = fig;
            }
        }
    }
    public static void closeWindow(MouseEvent event) {
        Stage st = (Stage) event.getSource();
        st.close();
    }
}
