package ba.games.chess.chess;

import figureClasses.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

import static javafx.geometry.Pos.CENTER;

// GridPane first row: 1 2 3 4 5 6 7 8 it starts with 1 not 0
public class ChessController {
    @FXML
    public GridPane tabla;

    public List<BufferedImage> loadImage() {
        // loading the images
        List<BufferedImage> image = new ArrayList<>();
        File folder = new File("src/main/resources/ba/games/chess/chess/png");
        File[] list_of_files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().endsWith("png");
            }
        });
        try {
            for(var file : list_of_files) {
                BufferedImage i = ImageIO.read(file);
                image.add(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private Node getCell(final int row, final int coloumn, GridPane grid) {
        // if we find cell we will put it in a rez
        Node rez = null;
        ObservableList<Node> cells = grid.getChildren();

        for(Node cell : cells) {
            // we need to test if rowIndex and coloumnIndex != null so we will use Integer
            Integer rowIndex = GridPane.getRowIndex(cell);
            Integer coloumnIndex = GridPane.getColumnIndex(cell);
            // System.out.println("RowIndex: " + rowIndex + "    columnIndex: " + coloumnIndex);
            if(rowIndex != null && coloumnIndex != null && rowIndex == row && coloumnIndex == coloumn) {
                rez = cell;
                break;
            }
        }
        return rez;
    }

    public void initTable() {
        for(int i=0; i<tabla.getRowCount(); i++) {
            for(int j=0; j<tabla.getColumnCount(); j++) {
                VBox cell = new VBox();
               // Image image = new Image("C:\\Users\\nezzr\\IdeaProjects\\chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\black_pawn.png");
                if((i % 2 != 0 || j % 2 != 0) && !(i % 2 != 0 && j % 2 != 0)) {
                    cell.getStyleClass().add("pozadina");
                }
                GridPane.setRowIndex(cell, i);
                GridPane.setColumnIndex(cell, j);
                cell.setAlignment(CENTER);
                if(i == 6) {
                    Figure pawn = new Pawn("C:\\Users\\nezzr\\IdeaProjects\\chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\white_pawn.png", cell, tabla,"White");
                }
                if(i == 1) {
                    Figure pawn = new Pawn("C:\\Users\\nezzr\\IdeaProjects\\chess\\src\\main\\resources\\ba\\games\\chess\\chess\\png\\black_pawn.png", cell, tabla,"Black");
                }

                //cell.getChildren().add(chess_figure);
                tabla.getChildren().add(cell);
            }
        }
    }
    @FXML
    public void initialize() {
        Figure.setTabla(tabla);
        initTable();
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