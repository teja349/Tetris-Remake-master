package come.tetris.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;


import java.util.Random;

import com.badlogic.gdx.utils.Logger;

public class Tetrisgame extends ApplicationAdapter implements GestureDetector.GestureListener {

    public static Grid_cell[][] gameGrid;
    public boolean PIM = false;
    public boolean fr = true;

    public int DELAY = 30;
    public int C_Delay = 0;
    public int gameover = 0;
    public float piece_x = 0;
    public float piece_y = 0;
    public float bracket_Height;
    public float screen_Width;
    public float screen_Height;
    public float comm_Width;
    public float grid_Width;
    public float grid_block_ratio_width;
    public float grid_block_ratio_height;
    public static float def_x;

    public Shape c_piece = new Shape();
    public Shape n_piece = new Shape();
    public SpriteBatch batch;
    public Texture[] blocks;
    public Texture background;
    public Texture whitebackground;
    public Texture grid;
    public Texture bracket;
    public Texture gamedone;
    Random rand = new Random(System.currentTimeMillis());


    @Override
    public void create() {

        Gdx.input.setInputProcessor(new GestureDetector(this));
        gameGrid = new Grid_cell[32][16];
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                gameGrid[i][j] = new Grid_cell();

            }
        }

        batch = new SpriteBatch();
        blocks = new Texture[]{new Texture("brown.png"), new Texture("darker_green.png"), new Texture("gold.png"), new Texture("light_green.png"), new Texture("pure_green.png"), new Texture("purple.png")};
        background = new Texture("backeground.png");

        grid = new Texture("grid_Back.png");
        bracket = new Texture("next_piece_bracket.png");
        gamedone = new Texture("wedding-game-over-ball-and-chain.png");
        whitebackground = new Texture("Solid_white_bordered.png");
        screen_Height = Gdx.graphics.getHeight();
        screen_Width = Gdx.graphics.getWidth();
        bracket_Height = (float) (screen_Height * 0.30);
        comm_Width = (float) (screen_Width * 0.20);
        grid_Width = (float) (screen_Width * 0.60);
        grid_block_ratio_width = (float) (grid_Width / gameGrid[0].length);
        grid_block_ratio_height = (float) (screen_Height / gameGrid.length);
        def_x = (float) (comm_Width + (int) (gameGrid[0].length / 2) * grid_block_ratio_width);

    }

    @Override
    public void render() {
        batch.begin();

        if(gameover==1){
            int i=0;
            //while(i<1000000)i++;
            batch.draw(whitebackground, 0, 0, screen_Width, screen_Height);
            batch.draw(gamedone, 0, 0, screen_Width, screen_Height);
            batch.end();
            return ;
        }
        boardCheck();
        update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        batch.draw(background, 0, 0, screen_Width, screen_Height);
        batch.draw(grid, comm_Width, 0, grid_Width, screen_Height);
        batch.draw(bracket, 0, screen_Height - bracket_Height, comm_Width, bracket_Height);
        drawGrid();
        drawPiece();
        batch.end();
    }

    public void drawPiece() {
        for (int i = 0; i < c_piece.piece.length; i++) {
            for (int j = 0; j < c_piece.piece[0].length; j++) {
                if (c_piece.piece[i][j]) {
                    batch.draw(blocks[c_piece.color], piece_x + (j * grid_block_ratio_width), piece_y + (i * grid_block_ratio_height), grid_block_ratio_width, grid_block_ratio_height);
                }
            }

        }
        for (int i = 0; i < n_piece.piece.length; i++) {
            for (int j = 0; j < n_piece.piece[0].length; j++) {
                if (n_piece.piece[i][j]) {
                    batch.draw(blocks[n_piece.color], (float) (comm_Width * 0.30) + (j * grid_block_ratio_width), screen_Height - (float) (bracket_Height * 0.37) + (i * grid_block_ratio_height), grid_block_ratio_width, grid_block_ratio_height);
                }
            }

        }
    }

    public void drawGrid() {
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (gameGrid[i][j].color != 7) {
                    batch.draw(blocks[gameGrid[i][j].color], comm_Width + (j * grid_block_ratio_width), i * grid_block_ratio_height, grid_block_ratio_width, grid_block_ratio_height);
                }
            }
        }
    }

    public void update() {
        if (!PIM) {
            randomSelector();
            piece_x = def_x;
            piece_y = screen_Height - (c_piece.piece.length * grid_block_ratio_height);
            PIM = true;
        } else if (PIM && DELAY <= C_Delay) {
            PIM = hitbox();
            if (!PIM) {
                return;
            }
            C_Delay = 0;
            piece_y -= grid_block_ratio_height;

        } else {
            C_Delay++;
        }


    }

    public void randomSelector() {
        int index;
        int next_piece;
        if (!fr) {
            c_piece = n_piece;
            next_piece = rand.nextInt(6);
        } else {
            index = rand.nextInt(6);
            next_piece = rand.nextInt(6);
            fr = false;
            switch (index) {
                case 0:
                    c_piece = new S_shape();
                    break;
                case 1:
                    c_piece = new L_shape();
                    break;
                case 2:
                    c_piece = new I_shape();
                    break;
                case 3:
                    c_piece = new Box_shape();
                    break;
                case 4:
                    c_piece = new Z_shape();
                    break;
                case 5:
                    c_piece = new T_shape();
                    break;
            }
        }
        switch (next_piece) {
            case 0:
                n_piece = new S_shape();
                break;
            case 1:
                n_piece = new L_shape();
                break;
            case 2:
                n_piece = new I_shape();
                break;
            case 3:
                n_piece = new Box_shape();
                break;
            case 4:
                n_piece = new Z_shape();
                break;
            case 5:
                n_piece = new T_shape();
                break;
        }


    }

    public boolean hitbox() {
        for (int i = 0; i < c_piece.piece.length; i++) {
            for (int j = 0; j < c_piece.piece[0].length; j++) {
                if (c_piece.piece[i][j]) {
                    int x = (int) ((piece_x - comm_Width) / grid_block_ratio_width) + j;
                    int y = (int) (piece_y / grid_block_ratio_height) + i;
                    if (y - 1 < 0 || gameGrid[y - 1][x].taken) {
                        placePiece();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placePiece() {
        for (int i = 0; i < c_piece.piece.length; i++) {
            for (int j = 0; j < c_piece.piece[0].length; j++) {
                if (c_piece.piece[i][j]) {
                    int x = (int) ((piece_x - comm_Width) / grid_block_ratio_width) + j;
                    int y = (int) (piece_y / grid_block_ratio_height) + i;
                    gameGrid[y][x].taken = true;
                    gameGrid[y][x].color = c_piece.color;
                }
            }
        }
        if((int)(piece_y+grid_block_ratio_height*c_piece.piece.length-screen_Height)==0)
            gameover=1;
        DELAY = 30;
    }

    public void boardCheck() {
        int counter = 0;
        for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                if (gameGrid[i][j].taken) {
                    counter++;
                }
            }

            if (counter == gameGrid[0].length && i != gameGrid.length - 1) {

                for (int z = i + 1; z <= gameGrid.length - 1; z++) {
                    //Gdx.app.log("TAG", " " + counter + " " + z + " ");
                    System.arraycopy(gameGrid[z], 0, gameGrid[z - 1], 0, gameGrid[z].length);
                    //gameGrid[z] = new Grid_cell[gameGrid[z - 1].length];
                }

                for (int l = 0; l < gameGrid[0].length; l++) {
                    gameGrid[gameGrid.length - 1][l] = new Grid_cell();

                }
                counter = 0;
            } else if (counter == gameGrid[0].length && i == gameGrid.length - 1) {
                for (int l = 0; l < gameGrid[0].length; l++) {
                    gameGrid[gameGrid.length - 1][l] = new Grid_cell();

                }
                counter = 0;
            } else {
                counter = 0;
            }

        }

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {



        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        c_piece.rotate();
        if (piece_x + c_piece.piece[0].length * grid_block_ratio_width > screen_Width - comm_Width) {
            piece_x -= (piece_x + c_piece.piece[0].length * grid_block_ratio_width) - (screen_Width - comm_Width);
        }
        if (piece_y + c_piece.piece.length * grid_block_ratio_height > screen_Height) {
            piece_y -= (piece_y + c_piece.piece.length * grid_block_ratio_height) - screen_Height;
        }


        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityX < 500 && velocityX > -500 && velocityY > 0) {
            DELAY = 1;
            return true;
        }
        int d = 0;
        boolean e_break = false;
        for (int i = 0; i < c_piece.piece.length; i++) {
            for (int j = 0; j < c_piece.piece[0].length; j++) {
                if (c_piece.piece[i][j]) {
                    int x = (int) ((piece_x - comm_Width) / grid_block_ratio_width) + j;
                    int y = (int) (piece_y / grid_block_ratio_height) + i;
                    if (velocityX > 0 && x < gameGrid[0].length - 1 && !gameGrid[y][x + 1].taken) {
                        d = 1;
                    } else if (velocityX < 0 && x > 0 && !gameGrid[y][x - 1].taken) {
                        d = 2;
                    } else {
                        d = 0;
                        e_break=true;
                        break;
                    }

                }
            }
            if(e_break){
                break;
            }

        }
        switch (d) {
            case 1:
                if (piece_x + (c_piece.piece[0].length * grid_block_ratio_width) < screen_Width - comm_Width) {
                    piece_x += grid_block_ratio_width;
                }
                break;
            case 2:
                if (piece_x > comm_Width) {
                    piece_x -= grid_block_ratio_width;
                }
                break;
            default:
                break;

        }


        return true;

    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //Gdx.app.log("TAG", " " + x + " "+ y);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2
            pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].dispose();
        }
        background.dispose();
        grid.dispose();
        bracket.dispose();

    }
}
