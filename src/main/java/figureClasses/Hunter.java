package figureClasses;

import ba.games.chess.chess.ChessController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Hunter extends Figure {
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;
    public Hunter(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.color = color;
        this.tabla = tabla;
    }
    @Override
    public void canMoveOnClick(MouseEvent event) {
        clicked = !clicked;
        removePlaceholder();
        if(clicked) {
            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
            int columnIndex = GridPane.getColumnIndex((Node) event.getSource());
            VBox currentCell = (VBox) tabla.getChildren().get(8 * rowIndex + columnIndex);
            setFigureToMove(currentCell);
            // up right +1;
            VBox checkCell;
            rowIndex--;
            columnIndex++;
            while(rowIndex >= 0 && columnIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + columnIndex);
                if(!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex--;
                columnIndex++;
            }
            // down right
            rowIndex = GridPane.getRowIndex(currentCell) + 1;
            columnIndex = GridPane.getColumnIndex(currentCell) + 1;
            while(rowIndex <= 7 && columnIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + columnIndex);
                if(!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex++;
                columnIndex++;
            }
            // Left down
            rowIndex = GridPane.getRowIndex(currentCell) + 1;
            columnIndex = GridPane.getColumnIndex(currentCell) - 1;
            while(rowIndex <= 7 && columnIndex >= 0) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + columnIndex);
                if(!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex++;
                columnIndex--;
            }
            // Up left
            rowIndex = GridPane.getRowIndex(currentCell) - 1;
            columnIndex = GridPane.getColumnIndex(currentCell) - 1;
            while(columnIndex >= 0 && rowIndex >= 0) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + columnIndex);
                if(!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                columnIndex--;
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
