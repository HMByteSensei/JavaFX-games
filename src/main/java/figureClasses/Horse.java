package figureClasses;

import ba.games.chess.chess.ChessController;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class Horse extends Figure {
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;

    public Horse(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.color = color;
        this.tabla = tabla;
    }
    private void testMove(int toMoveRowIndex, int toMoveColIndex){
        VBox cellToMove = (VBox) tabla.getChildren().get(8 * toMoveRowIndex + toMoveColIndex);
        if(!isFree(cellToMove)) {
            canEat(cellToMove);
            return;
        }
        drawPlaceholder(cellToMove);
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
            // UP left/right
            int toMoveRowIndex = rowIndex - 2;
            int toMoveColIndex = columnIndex - 1;
            if(toMoveRowIndex >= 0) {
                if (toMoveColIndex >= 0) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
                toMoveColIndex = columnIndex + 1;
                if(toMoveColIndex <= 7) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
            }
            // One row up, then two rows right/left
            toMoveRowIndex = rowIndex - 1;
            if(toMoveRowIndex >= 0) {
                toMoveColIndex = columnIndex + 2;
                if (toMoveColIndex <= 7) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
                toMoveColIndex = columnIndex - 2;
                if(toMoveColIndex >= 0) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
            }
            // Down left/right
            toMoveRowIndex = rowIndex + 2;
            if(toMoveRowIndex <= 7) {
                toMoveColIndex = columnIndex - 1;
                if (toMoveColIndex >= 0) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
                toMoveColIndex = columnIndex + 1;
                if(toMoveColIndex <= 7) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
            }
            // One row down, left/right
            toMoveRowIndex = rowIndex + 1;
            if(toMoveRowIndex <= 7) {
                toMoveColIndex = columnIndex - 2;
                if(toMoveColIndex >= 0) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
                toMoveColIndex = columnIndex + 2;
                if(toMoveColIndex <= 7) {
                    testMove(toMoveRowIndex, toMoveColIndex);
                }
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
