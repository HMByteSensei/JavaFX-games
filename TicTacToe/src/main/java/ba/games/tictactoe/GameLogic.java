package ba.games.tictactoe;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class GameLogic {
    private ObservableList<ObservableList<StringProperty>> board;
    private int moveCounter;
    private boolean xTurn;
    GameLogic(ObservableList<ObservableList<StringProperty>> b) {
        board = b;
        moveCounter = 0;
        xTurn = true;
    }

    public boolean winCheck(int row, int col) {
        String turn = xTurn ? "X" : "O";
        // Check for victory at current row and column
        boolean rowWin = true;
        boolean colWin = true;
        for(int i=0; i<board.size(); i++) {
            if(!board.get(row).get(i).get().equals(turn)) {
                rowWin = false;
            }
            if(!board.get(i).get(col).get().equals(turn)) {
                colWin = false;
            }
        }
        // diagonal victory check
        boolean diagonalLeft = true;
        boolean diagonalRight = true;
        int size = board.size() - 1;
        for(int i=0; i<board.size(); i++) {
            if(!board.get(i).get(i).get().equals(turn)) {
                diagonalLeft = false;
            }
            if(!board.get(i).get(size - i).get().equals(turn)) {
                diagonalRight = false;
            }
        }
        return diagonalLeft || diagonalRight || colWin || rowWin;
    }

    public String makeMove(int row, int col) {
        if(xTurn) {
            board.get(row).get(col).set("X");
        } else {
            board.get(row).get(col).set("O");
        }
        moveCounter = moveCounter + 1;
        if(moveCounter > 4) {
            if(winCheck(row, col)) {
                return xTurn ? "X player wins!" : "O player wins!";
            }
        }
        if(moveCounter == 9) {
            return "Draw!";
        }
        xTurn = !xTurn;
        return "";
    }
}
