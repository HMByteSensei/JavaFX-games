package figureClasses;

import ba.games.chess.chess.ChessController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Queen extends Figure {
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;

    public Queen(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.color = color;
        this.tabla = tabla;
    }
    @Override
    public void canMoveOnClick(MouseEvent event) {
        clicked = !clicked;
        removePlaceholder();
        if (clicked) {
            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
            int colIndex = GridPane.getColumnIndex((Node) event.getSource());
            VBox currentCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
            setFigureToMove(currentCell);
            // Upwords check so -1
            VBox checkCell;
            rowIndex--;
            while (rowIndex >= 0) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex--;
            }
            // Downwords check so +1
            rowIndex = GridPane.getRowIndex(currentCell);
            rowIndex++;
            while (rowIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex++;
            }
            // Right check +1; reset rowIndex that we have changed
            rowIndex = GridPane.getRowIndex(currentCell);
            colIndex++;
            while (colIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                colIndex++;
            }
            // Left check -1; reset both variables
            rowIndex = GridPane.getRowIndex(currentCell);
            colIndex = GridPane.getColumnIndex(currentCell);
            colIndex--;
            while (colIndex >= 0) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                colIndex--;
            }
            // Move as hunter
            // up right +1;
            rowIndex = GridPane.getRowIndex((Node) event.getSource()) - 1;
            colIndex = GridPane.getColumnIndex((Node) event.getSource()) + 1;
            while (rowIndex >= 0 && colIndex <= 7) {// && colIndex >= 0 && colIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex--;
                colIndex++;
            }
            // down right
            rowIndex = GridPane.getRowIndex(currentCell) + 1;
            colIndex = GridPane.getColumnIndex(currentCell) + 1;
            while (rowIndex <= 7 && colIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex++;
                colIndex++;
            }
            // Left down
            rowIndex = GridPane.getRowIndex(currentCell) + 1;
            colIndex = GridPane.getColumnIndex(currentCell) - 1;
            while (rowIndex <= 7 && colIndex >= 0) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex++;
                colIndex--;
            }
            // Up left
            rowIndex = GridPane.getRowIndex(currentCell) - 1;
            colIndex = GridPane.getColumnIndex(currentCell) - 1;
            while (colIndex >= 0 && rowIndex >= 0) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if (!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                colIndex--;
                rowIndex--;
            }
        }
    }

    @Override
    public void canEat(VBox cell) {
        if(!this.getColor().equals(ChessController.getColor(cell))) {
            drawEatPlaceholder(cell);
        }
    }

    @Override
    public String getColor() {
        return color;
    }
}
