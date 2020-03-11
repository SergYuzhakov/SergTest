package Level30_Multithreading.task3513;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private Model model;
    private View view;
    private final int  WINNING_TILE = 2048; // константа будет определять вес плитки при достижении которого игра будет считаться выигранной

    public Controller(Model model) {
        this.model = model;
        view = new View(this);
    }

    public void resetGame(){
        view.isGameWon = false;
        view.isGameLost = false;
        model.resetGameTiles();
        model.score = 0;
    }

    public Tile[][] getGameTiles(){
        return getGameTiles();
    }
    public int getScore(){
        return model.score;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) resetGame();
        if(!model.canMove()) view.isGameLost = true;
        if(!view.isGameLost & !view.isGameWon){
            if(e.getKeyCode() == KeyEvent.VK_LEFT) model.left();
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) model.right();
            if(e.getKeyCode() == KeyEvent.VK_UP) model.up();
            if(e.getKeyCode() == KeyEvent.VK_DOWN) model.down();
        }
        if(model.maxTile == WINNING_TILE) view.isGameWon = true;

        view.repaint();


    }
}
