package figureClasses;

import ba.games.chess.chess.ChessApplication;
import ba.games.chess.chess.ChessController;
import ba.games.chess.chess.ChessLogic;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Pawn extends Figure {
    // attribute color to know where is the end of table(if player can upgrade to other figures)
    // and to know if pawn is at initial position, and also to know if you can eat enemy
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;
    private VBox figureToMove;
    public Pawn(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.color = color;
        this.tabla = tabla;
        figureToMove = cell;
    }
    // Modal window
    private void evolve(VBox cell) {
        ChessLogic.evolvePawn(cell, this);
    }
    @Override
    public String getColor() { return color; }
    @Override
    public void canMoveOnClick(MouseEvent event) {
        clicked = !clicked;
        removePlaceholder();
        if(clicked) {
            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
            int columnIndex = GridPane.getColumnIndex((Node) event.getSource());
            VBox cell = (VBox) tabla.getChildren().get(8 * rowIndex + columnIndex);
            setFigureToMove(cell);
            canEat(cell);
            if (color.equals("White")) {
                rowIndex--;
            } else {
                rowIndex++;
            }
            // for out of bound exception
            if(rowIndex < 0 || rowIndex > 7) { return; }
            cell = (VBox) tabla.getChildren().get(8 * rowIndex + columnIndex);
            if (isFree(cell)) {
                drawPlaceholder(cell);
                if (color.equals("White") && rowIndex + 1 == 6) {
                    cell = (VBox) tabla.getChildren().get(4 * 8 + columnIndex);
                    if (isFree(cell)) {
                        drawPlaceholder(cell);
                    }
                }
                if (color.equals("Black") && rowIndex - 1 == 1) {
                    cell = (VBox) tabla.getChildren().get(8 * 3 + columnIndex);
                    if (isFree(cell)) {
                        drawPlaceholder(cell);
                    }
                }
            }
        }
    }

    @Override
    public void canEat(VBox cell) {
        int nextRowIndex = this.color.equals("White") ? GridPane.getRowIndex(cell) - 1 : GridPane.getRowIndex(cell) + 1;
        int currentColumnIndex = GridPane.getColumnIndex(cell);
        // to not get out of bounds exception
        if(nextRowIndex >= 0 && nextRowIndex <= 7) {
            // -1 is placeholder for imposible move
            int plusColIndex = -1;
            int minusColIndex = -1;
            if (currentColumnIndex != 0) {
                minusColIndex = currentColumnIndex - 1;
            }
            if (currentColumnIndex != 7) {
                plusColIndex = currentColumnIndex + 1;
            }
            if (plusColIndex != -1) {
                VBox eat = (VBox) tabla.getChildren().get(nextRowIndex * 8 + plusColIndex);
                if (!isFree(eat) && !ChessController.getColor(eat).equals(this.getColor())) {
                    drawEatPlaceholder(eat);
                }
            }
            if (minusColIndex != -1) {
                VBox eat = (VBox) tabla.getChildren().get(nextRowIndex * 8 + minusColIndex);
                if (!isFree(eat) && !ChessController.getColor(eat).equals(this.getColor())) {
                    drawEatPlaceholder(eat);
                }
            }
        }
    }

    public void moveOnClick(MouseEvent event) {
        Node node = (Node) event.getSource(); // circle
        int rowIndex = GridPane.getRowIndex(node.getParent());
        int columnIndex = GridPane.getColumnIndex((node.getParent()));
        VBox moveToCell = (VBox) tabla.getChildren().get(rowIndex * 8 + columnIndex);
        ImageView figura = (ImageView) figureToMove.getChildren().get(0);

        figureToMove.getChildren().remove(figura);
        figureToMove.setOnMouseClicked(null);
        removePlaceholder();
        moveToCell.getChildren().add(figura);
        figureToMove = moveToCell;
        moveToCell.setOnMouseClicked(ev -> canMoveOnClick(ev));
        if(rowIndex == 0 || rowIndex == 7) {
            evolve(moveToCell);
        }
    }
    public void eatOnClick(VBox cell) {
        cell.getChildren().remove(0);
        figureToMove.setOnMouseClicked(null);
        removePlaceholder();
        cell.getChildren().add((ImageView) figureToMove.getChildren().get(0));
        figureToMove.getChildren().removeAll();
        figureToMove = cell;
        cell.setOnMouseClicked(ev -> canMoveOnClick(ev));
        if(GridPane.getRowIndex(cell) == 0 || GridPane.getRowIndex(cell) == 7) {
            evolve(cell);
        }
    }
}
