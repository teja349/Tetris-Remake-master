package come.tetris.game;

import com.badlogic.gdx.graphics.Color;


public class T_shape extends Shape {
    public T_shape() {
        boolean[][] t_piece= {{true,true,true},{false,true,false}};
        piece = new boolean[2][3];
        piece=t_piece;
        color = 4;
    }
    public void rotate() {
        super.rotate();
    }
}
