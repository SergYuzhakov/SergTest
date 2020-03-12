package Level30_Multithreading.task3513;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Model {
/*
    public static void main(String[] args) {

        Model m = new Model();
        m.gameTiles = new Tile[][]{{new Tile(4), new Tile(4), new Tile(2), new Tile(0)},
                {new Tile(4), new Tile(2), new Tile(0), new Tile(4)},
                {new Tile(4), new Tile(4), new Tile(4), new Tile(0)},
                {new Tile(4), new Tile(4), new Tile(4), new Tile(4)}};

       // m.rotationClockWise();
        m.down();
        for (int i = 0; i < m.gameTiles.length ; i++) {
            for (int j = 0; j < m.gameTiles.length ; j++) {
                System.out.print(m.gameTiles[i][j].value + " ");
            }
            System.out.println();
        }
    }
*/

    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    protected int score, maxTile;
    private Stack<Tile[][]> previousStates;
    private Stack<Integer> previousScores;
    private boolean isSaveNeeded = true;

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove(){
        if(!getEmptyTiles().isEmpty()) return true;

        for (int i = 0; i < FIELD_WIDTH - 1 ; i++) {
            for (int j = 0; j < FIELD_WIDTH ; j++) {
                if(j != 3) {
                    if(gameTiles[i][j].value == gameTiles[i][j+1].value | gameTiles[i][j].value == gameTiles[i+1][j].value) return true;
                }
                else {
                    if(gameTiles[i][j].value == gameTiles[i+1][j].value) return true;
                }
            }
        }
        return false;
    }

    public Model() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        previousStates = new Stack<>();
        previousScores = new Stack<>();
        resetGameTiles();
    }

    public void randomMove(){
        switch ((int) (Math.random() * 100) % 4) {
            case (0) : left();
            break;
            case (1) : right();
            break;
            case (2) : up();
            break;
            case (3) : down();
            break;
        }

    }

    private void saveState(Tile[][] tiles){
        Tile[][] tempGameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH ; i++) {
            for (int j = 0; j < FIELD_WIDTH ; j++) {
                tempGameTiles[i][j] = new Tile(tiles[i][j].value);
            }
        }
         previousStates.push(tempGameTiles);
         previousScores.push(score);
         isSaveNeeded = false;

    }

    public void rollback(){ // Метод rollback не должен модифицировать текущее игровое состояние в случае, если хотя бы один из стеков пуст.
        if(!previousStates.empty() & !previousScores.empty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }

    }

    private List<Tile> getEmptyTiles(){
        List<Tile> listEmptyTiles = Arrays.stream(gameTiles).flatMap(Arrays::stream).filter(Tile::isEmpty).collect(Collectors.toList());
        return listEmptyTiles;
    }

    private void addTile(){
        if(!getEmptyTiles().isEmpty()){
            int a = (int)(Math.random()*getEmptyTiles().size());
            int addValue = (Math.random() < 0.9 ? 2 : 4);
            getEmptyTiles().get(a).value = addValue;
        }

    }

    protected void resetGameTiles(){
        for (int i = 0; i < FIELD_WIDTH ; i++) {
            for (int j = 0; j < FIELD_WIDTH ; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        score = 0;
        maxTile = 0;
        addTile();
        addTile();

    }

    private void rotationClockWise(){ // метод для осуществления поворота на 90 гр.нашего массива по часовой стрелке
        Tile tempTiles;
        for (int i = 0; i < FIELD_WIDTH / 2 ; i++) {
            for (int j = i; j < FIELD_WIDTH  - 1 - i ; j++) {
                tempTiles = gameTiles[i][j];
                gameTiles[i][j] = gameTiles[FIELD_WIDTH - 1 - j][i];
                gameTiles[FIELD_WIDTH - 1 - j][i] = gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j];
                gameTiles[FIELD_WIDTH - 1 - i][FIELD_WIDTH - 1 - j] = gameTiles[j][FIELD_WIDTH - 1 - i];
                gameTiles[j][FIELD_WIDTH - 1 - i] = tempTiles;
            }
        }


    }

    private boolean compressTiles(Tile[] tiles) {
        boolean isCompress = false;
        for (int i = 0; i < tiles.length - 1 ; i++) {
            for (int j = 0; j < tiles.length - 1 ; j++) {
                if(tiles[j].value == 0 && tiles[j+1].value != 0) {
                    tiles[j].value = tiles[j + 1].value;
                    tiles[j +1].value = 0;
                    isCompress = true;
                }
            }
        }
        return isCompress;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isMerge = false;
        for (int j = 0; j < tiles.length - 1 ; j++) {
            if(tiles[j].value == tiles[j + 1].value && tiles[j].value != 0) {
                tiles[j].value = 2 * tiles[j].value;
                score = score + tiles[j].value;
                tiles[j +1].value = 0;
                isMerge = true;
            }
            if(maxTile < tiles[j].value) maxTile = tiles[j].value;
        }
        compressTiles(tiles);
        return isMerge;
    }
    public void left(){
        boolean isChange = false;
        if(isSaveNeeded) saveState(gameTiles);
        for (int i = 0; i < FIELD_WIDTH ; i++) {
            if(compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) isChange = true;
        }
         if(isChange) addTile();
         isSaveNeeded = true;
    }

    public void right(){
        saveState(gameTiles);
        rotationClockWise();
        rotationClockWise();
        left();
        rotationClockWise();
        rotationClockWise();
    }

    public void up(){
        saveState(gameTiles);
        rotationClockWise();
        rotationClockWise();
        rotationClockWise();
        left();
        rotationClockWise();

    }

    public void down(){
        saveState(gameTiles);
        rotationClockWise();
        left();
        rotationClockWise();
        rotationClockWise();
        rotationClockWise();
    }
}
