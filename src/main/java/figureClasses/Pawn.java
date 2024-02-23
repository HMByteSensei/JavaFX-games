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
            int columnIndex = GridPane.getColumnIndex((Node) event.getSource()) + 1;
            VBox cell = (VBox) tabla.getChildren().get(8 * rowIndex + columnIndex);
            setFigureToMove(cell);
            canEat(cell);
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

    @Override
    public void canEat(VBox cell) {
        int nextRowIndex = this.color == "White" ? GridPane.getRowIndex(cell) - 1 : GridPane.getRowIndex(cell) + 1;
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
//            System.out.println("Current col: " + currentColumnIndex);
//            System.out.println("Cell col: " + (GridPane.getColumnIndex(cell)) + " Cell row: " + GridPane.getRowIndex(cell));
//            System.out.println("NEXT col: " + plusColIndex + " next row: " + nextRowIndex);
            VBox eat = (VBox) tabla.getChildren().get(nextRowIndex * 8 + plusColIndex + 1);
            if(!isFree(eat) && ChessController.getColor(eat) != this.getColor()) {
                drawEatPlaceholder(eat);
            }
        }
        if(minusColIndex != -1) {
            VBox eat = (VBox) tabla.getChildren().get(nextRowIndex * 8 + plusColIndex - 1);
            if(!isFree(eat) && ChessController.getColor(eat) != this.getColor()) {
                drawEatPlaceholder(eat);
            }
        }
//        int brojac = 0;
//        VBox start = (VBox) tabla.getChildren().get(brojac + brojac);
//        for(int i=0; i<8; i++) {
//            for(int j=0; j<8; j++) {
//                System.out.print("(" + GridPane.getRowIndex(start) + ", " + GridPane.getColumnIndex(start) + ") ");
//                brojac++;
//                start = (VBox) tabla.getChildren().get(brojac);
//            }
//            System.out.println();
//        }
//        System.out.println("Po pozicijama:");
//        for (Node start : tabla.getChildren()) {
//            int rowIndex = GridPane.getRowIndex(start);
//            int colIndex = GridPane.getColumnIndex(start);
//            System.out.println("Cell: " + start + " at (" + rowIndex + "," + colIndex + ")");
//        }

    }
}
