package figureClasses;

import ba.games.chess.chess.ChessController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pawn extends Figure {
    // attribute color to know where is the end of table(if player can upgrade to other figures)
    // and to know if pawn is at initial position, and also to know if you can eat enemy
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;

    public Pawn(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.color = color;
        this.tabla = tabla;
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
        // -1 is placeholder for imposible move
        int plusColIndex = -1;
        int minusColIndex = -1;
        if(currentColumnIndex != 0) {
            minusColIndex = GridPane.getColumnIndex(cell) - 1;
            System.out.println("minusColIndx" + minusColIndex);
        }
        if(currentColumnIndex != 7) {
            plusColIndex = GridPane.getColumnIndex(cell) + 1;
            System.out.println("plusColIndex: " + plusColIndex);
        }
        if(plusColIndex != -1) {
            VBox eat = (VBox) tabla.getChildren().get(nextRowIndex * 8 + plusColIndex);
            if(!isFree(eat) && ChessController.getColor(eat).equals(this.getColor())) {
                drawEatPlaceholder(eat);
            }
        }
        if(minusColIndex != -1) {
            VBox eat = (VBox) tabla.getChildren().get(nextRowIndex * 8 + minusColIndex);
            if(!isFree(eat) && ChessController.getColor(eat).equals(this.getColor())) {
                drawEatPlaceholder(eat);
            }
        }
    }
}
