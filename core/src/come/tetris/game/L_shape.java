package come.tetris.game;

import com.badlogic.gdx.graphics.Color;


public class L_shape extends Shape {

    public L_shape() {
        boolean[][] t_piece= {{true,false},{true,false},{true,true}};
        piece = new boolean[3][2];
        piece=t_piece;
        color = 2;
    }

    @Override
    public void rotate() {
        super.rotate();
    }
}
