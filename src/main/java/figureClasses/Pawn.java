package figureClasses;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    public void canMoveOnClick(MouseEvent event) {
        clicked = !clicked;
        removePlaceholder();
        if(clicked) {
            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
            int columnIndex = GridPane.getColumnIndex((Node) event.getSource()) + 1;
            VBox cell = (VBox) tabla.getChildren().get(8 * rowIndex + columnIndex);
            setFigureToMove(cell);
            if (color == "White") {
                rowIndex--;
            } else {
                rowIndex++;
            }
            cell = (VBox) tabla.getChildren().get(8 * rowIndex + columnIndex);
            if (isFree(cell)) {
                drawPlaceholder(cell);
                if (color == "White" && rowIndex + 1 == 6) {
                    cell = (VBox) tabla.getChildren().get(4 * 8 + columnIndex);
                    if (isFree(cell)) {
                        drawPlaceholder(cell);
                    }
                }
                if (color == "Black" && rowIndex - 1 == 1) {
                    cell = (VBox) tabla.getChildren().get(8 * 3 + columnIndex);
                    if (isFree(cell)) {
                        drawPlaceholder(cell);
                    }
                }
            }
        }
    }
}
