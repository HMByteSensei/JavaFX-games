package figureClasses;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class King extends Figure {
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;

    public King(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.color = color;
        this.tabla = tabla;
    }
    @Override
    public void canMoveOnClick(MouseEvent event) {

    }

    @Override
    public void canEat(VBox cell) {

    }

    @Override
    public String getColor() {
        return color;
    }
}
