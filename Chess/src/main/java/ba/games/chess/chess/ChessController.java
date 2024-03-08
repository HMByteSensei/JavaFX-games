package ba.games.chess.chess;

import figureClasses.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
public class ChessController {
    @FXML
    private GridPane tabla;

    @FXML
    public void initialize() {
        Figure.setTabla(tabla);
        ChessLogic cL = new ChessLogic(tabla);
//        initTable();
//        for (int i = 0; i < tabla.getRowCount(); i++) {
//            for (int j = 0; j < tabla.getColumnCount(); j++) {
//                Node start = tabla.getChildren().get(i * tabla.getColumnCount() + j);
//                Integer rowIndex = GridPane.getRowIndex(start);
//                Integer colIndex = GridPane.getColumnIndex(start);
//
//                if (rowIndex != null && colIndex != null) {
//                    System.out.print("(" + rowIndex + "," + colIndex + ") ");
//                } else {
//                    System.out.print("(idk) ");
//                }
//            }
//            System.out.println();
//        }
//        for (int i = 0; i < tabla.getRowCount(); i++) {
//            for (int j = 0; j < tabla.getColumnCount(); j++) {
//                System.out.print(i*8+j + " ");
//            }
//            System.out.println();
//        }
    }

    public static String getColor(VBox cell) {
        ImageView iV = (ImageView) cell.getChildren().get(0);
        String word = iV.getImage().getUrl();
        for(int i=word.length() - 1; i>=0; i--) {
            if(word.charAt(i) == '_') {
                return word.charAt(i-1) == 'e' ? "White" : "Black";
            }
        }
        return "None";
    }
}