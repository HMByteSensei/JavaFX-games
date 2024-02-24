package figureClasses;

import ba.games.chess.chess.ChessController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Rook extends Figure {
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;

    public Rook(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.tabla = tabla;
        this.color = color;
    }
    @Override
    public void canMoveOnClick(MouseEvent event) {
        clicked  = !clicked;
        removePlaceholder();
        if(clicked) {
            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
            int colIndex = GridPane.getColumnIndex((Node) event.getSource());
            VBox currentCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
            setFigureToMove(currentCell);
//            canEat(currentCell);
            // Upwords check so -1
            VBox checkCell;
            rowIndex--;
            while(rowIndex >= 0) {// && colIndex >= 0 && colIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if(!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex--;
            }
            // Downwords check so +1
            rowIndex = GridPane.getRowIndex(currentCell);
            rowIndex++;
            while(rowIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if(!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                rowIndex++;
            }
            // Right check +1; reset rowIndex that we have changed
            rowIndex = GridPane.getRowIndex(currentCell);
            colIndex++;
            while(colIndex <= 7) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if(!isFree(checkCell)) {
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
            while(colIndex >= 0) {
                checkCell = (VBox) tabla.getChildren().get(rowIndex * 8 + colIndex);
                if(!isFree(checkCell)) {
                    canEat(checkCell);
                    break;
                }
                drawPlaceholder(checkCell);
                colIndex--;
            }
        }
    }

    @Override
    public void canEat(VBox cell) {
        // we call this method only if we figured out that a cell was occupied
        if(!this.getColor().equals(ChessController.getColor(cell))) {
            drawEatPlaceholder(cell);
        }
    }

    @Override
    public String getColor() {
        return color;
    }
}
