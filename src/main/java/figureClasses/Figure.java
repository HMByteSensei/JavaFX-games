package figureClasses;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

// Crni
// Bijeli

public abstract class Figure extends ImageView {
    private static ArrayList<VBox> listOfPlaceholders;
    private VBox figureToMove = null;
    private static GridPane tabla;
    public Figure(String path, VBox cell) {
        cell.getChildren().add(new ImageView(path));
        cell.setOnMouseClicked(event -> canMoveOnClick(event));
        listOfPlaceholders = new ArrayList<>();
    }
    // before program starts set tabla
    public static void setTabla(GridPane t) { tabla = t; }
    public void drawPlaceholder(VBox cell) {
        Circle placeholder = new Circle(10);
        placeholder.setFill(Color.GRAY);
        placeholder.setOnMouseClicked(event -> moveOnClick(event));
        cell.getChildren().add(placeholder);
        listOfPlaceholders.add(cell);
    }
    public void removePlaceholder() {
        System.out.println(listOfPlaceholders.size());
        for(VBox cell : listOfPlaceholders) {
            cell.getChildren().removeIf(node -> node instanceof Circle);
        }
        listOfPlaceholders.clear();
        figureToMove = null;
    }
    public boolean isFree(VBox cell) {
        return !cell.getChildren().stream().anyMatch(child -> child instanceof Figure);
    }
    public void setFigureToMove(VBox cell) {
        figureToMove = cell;
    }
    public void moveOnClick(MouseEvent event) {
        Node node = (Node) event.getSource();
        int rowIndex = GridPane.getRowIndex(node.getParent());
        int columnIndex = GridPane.getColumnIndex((node.getParent())) + 1;
        VBox moveToCell = (VBox) tabla.getChildren().get(rowIndex * 8 + columnIndex);
        ImageView figura = (ImageView) figureToMove.getChildren().get(0);

        figureToMove.getChildren().remove(figura);
        removePlaceholder();
        moveToCell.getChildren().add(figura);
        figureToMove = moveToCell;
    }
    public abstract void canMoveOnClick(MouseEvent event);
    // public abstract canEat(VBox cell); i onda ako moze pomjeris ga tamo
}
