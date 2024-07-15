package figureClasses;

import ba.games.chess.chess.ChessController;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class King extends Figure {
    private String color;
    private GridPane tabla;
    // to know if user clicks on same figure to remove placeholders
    private static boolean clicked = false;
    // for castle
    private boolean moved = false;
    private VBox curentlyAtCell;
    // to make figures independent of other classes I will make two rooks here
    Rook LeftRook;
    Rook RightRook;

    public King(String path, VBox cell, GridPane tabla, String color) {
        super(path, cell);
        this.color = color;
        this.tabla = tabla;
        curentlyAtCell = cell;
        int rowIndex = GridPane.getRowIndex(cell);
        VBox leftCell = (VBox) tabla.getChildren().get(rowIndex * 8);
        LeftRook = new Rook(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\" + color.toLowerCase() + "_rook.png", leftCell, tabla, color);
        VBox rightCell = (VBox) tabla.getChildren().get(rowIndex * 8 + 7);
        RightRook = new Rook(System.getProperty("user.dir") + "\\Chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\" + color.toLowerCase() + "_rook.png", rightCell, tabla, color);
    }
    private void testMove(int toMoveRowIndex, int toMoveColIndex){
        VBox cellToMove = (VBox) tabla.getChildren().get(8 * toMoveRowIndex + toMoveColIndex);
        if(!isFree(cellToMove)) {
            canEat(cellToMove);
            return;
        }
        drawPlaceholder(cellToMove);
    }
    private boolean castleTest(int currentRow, int startFrom, int goTill) {
        for(int i=startFrom; i<goTill; i++) {
            VBox testCell = (VBox) tabla.getChildren().get(currentRow * 8 + i);
            if(!isFree(testCell)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void canMoveOnClick(MouseEvent event) {
        clicked = !clicked;
        removePlaceholder();
        if (clicked) {
            int rowIndex = GridPane.getRowIndex((Node) event.getSource());
            int columnIndex = GridPane.getColumnIndex((Node) event.getSource());
            VBox cell = (VBox) tabla.getChildren().get(8 * rowIndex + columnIndex);
            if(!moved) {
                castle(event);
            }
            setFigureToMove(cell);
            // UP
            int rowToTest = rowIndex -1;
            int colToTest;
            if(rowToTest >= 0) {
                // left
                colToTest = columnIndex - 1;
                if(colToTest >= 0) {
                    testMove(rowToTest, colToTest);
                }
                // up
                colToTest = columnIndex;
                testMove(rowToTest, colToTest);
                // right
                colToTest = colToTest + 1;
                if(colToTest <= 7) {
                    testMove(rowToTest, colToTest);
                }
            }
            // SAME ROW
            rowToTest = rowIndex;
            // left
            colToTest = columnIndex - 1;
            if(colToTest >= 0) {
                testMove(rowToTest, colToTest);
            }
            // right
            colToTest = columnIndex + 1;
            if(colToTest <= 7) {
                testMove(rowToTest, colToTest);
            }
            // DOWN ROW
            rowToTest = rowIndex + 1;
            if(rowToTest <= 7) {
                // right
                if(colToTest <= 7) {
                    testMove(rowToTest, colToTest);
                }
                // left
                colToTest = columnIndex - 1;
                if(colToTest >= 0) {
                    testMove(rowToTest, colToTest);
                }
                // down
                colToTest = columnIndex;
                testMove(rowToTest, colToTest);
            }
        }
    }

    @Override
    public void canEat(VBox cell) {
        if(!this.getColor().equals(ChessController.getColor(cell))) {
            drawEatPlaceholder(cell);
        }
    }
    public void moveOnClick(MouseEvent event) {
        Node node = (Node) event.getSource();
        int rowIndex = GridPane.getRowIndex(node.getParent());
        int columnIndex = GridPane.getColumnIndex((node.getParent()));
        VBox moveToCell = (VBox) tabla.getChildren().get(rowIndex * 8 + columnIndex);
        ImageView figura = (ImageView) curentlyAtCell.getChildren().get(0);

        curentlyAtCell.getChildren().remove(figura);
        curentlyAtCell.setOnMouseClicked(null);
        removePlaceholder();
        moveToCell.getChildren().add(figura);
        curentlyAtCell = moveToCell;
        moveToCell.setOnMouseClicked(ev -> canMoveOnClick(ev));
        // leftCastle = "rowIndex * 2 + 2" and rightCastle = "rowIndex * 8 + 6
        if(!moved) {
            // moveToCell.equals(leftCell)
            if(moveToCell.equals((VBox) tabla.getChildren().get(rowIndex * 8 + 2))) {
                VBox rookPos = (VBox) tabla.getChildren().get(rowIndex * 8);
                VBox goTo = (VBox) tabla.getChildren().get(rowIndex * 8 + 3);
                ImageView rook = (ImageView) rookPos.getChildren().get(0);

                rookPos.getChildren().remove(rook);
                rookPos.setOnMouseClicked(null);
                goTo.getChildren().add(rook);
                LeftRook.setEventAfterCastle(goTo);
            }
            // moveToCell.equals(rightCell)
            if(moveToCell.equals((VBox) tabla.getChildren().get(rowIndex * 8 + 6))) {
                VBox rookPos = (VBox) tabla.getChildren().get(rowIndex * 8 + 7);
                VBox goTo = (VBox) tabla.getChildren().get(rowIndex * 8 + 5);
                ImageView rook = (ImageView) rookPos.getChildren().get(0);

                rookPos.getChildren().remove(rook);
                rookPos.setOnMouseClicked(null);
                goTo.getChildren().add(rook);
                RightRook.setEventAfterCastle(goTo);
            }
        }
        moved = true;
    }
    public void eatOnClick(VBox cell) {
        cell.getChildren().remove(0);
        curentlyAtCell.setOnMouseClicked(null);
        removePlaceholder();
        cell.getChildren().add((ImageView) curentlyAtCell.getChildren().get(0));
        curentlyAtCell.getChildren().removeAll();
        curentlyAtCell = cell;
        cell.setOnMouseClicked(ev -> canMoveOnClick(ev));
        moved = true;
    }

    @Override
    public String getColor() {
        return color;
    }

    public void castle(MouseEvent event) {
        int rowIndex = GridPane.getRowIndex((Node) event.getSource());
        // we start at 1 and go till 4 to test for cells between rook and king
        if(castleTest(rowIndex, 1, 4) && !LeftRook.getMoved()) {
            // Column index for left castle placeholder is 2 (that is why -> "+ 2"
            VBox leftCastleCell = (VBox) tabla.getChildren().get(rowIndex * 8 + 2);
            drawPlaceholder(leftCastleCell);
        }
        // start at 5(cell next to king) go till 7 cell where rook is
        if(castleTest(rowIndex, 5, 7) && !RightRook.getMoved()) {
            VBox rightCastleCell = (VBox) tabla.getChildren().get(rowIndex * 8 + 6);
            drawPlaceholder(rightCastleCell);
        }
    }
}
