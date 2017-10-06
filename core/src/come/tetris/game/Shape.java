package come.tetris.game;

import com.badlogic.gdx.graphics.Color;
import java.util.Random;

public class Shape {
    public boolean[][] piece;
    public int color;


    public Shape() {

        color = 0;
    }

    public void rotate(){
        boolean[][] temp_piece = new boolean[piece[0].length][piece.length];
        for ( int i = 0 ; i< piece.length ; i++){
            for ( int j = 0 ; j< piece[i].length ; j++){
                temp_piece[temp_piece.length-1-j][i] = piece[i][j];
            }
        }
        piece=temp_piece;
    }

}
