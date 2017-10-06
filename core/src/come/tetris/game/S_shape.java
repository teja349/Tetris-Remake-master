package come.tetris.game;

import com.badlogic.gdx.graphics.Color;


public class S_shape extends Shape {
    public S_shape() {
        boolean[][] t_piece= {{false,true,true},{true,true,false}};
        piece = new boolean[2][3];
        piece=t_piece;
        color =5;
    }
    public void rotate() {
        super.rotate();
    }
}
