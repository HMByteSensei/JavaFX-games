package figureClasses;

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
            //VBox cell = (VBox)
        }
    }

    @Override
    public void canEat(VBox cell) {

    }

    @Override
    public String getColor() {
        return color;
    }
}
