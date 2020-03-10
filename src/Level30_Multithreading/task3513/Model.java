package Level30_Multithreading.task3513;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Model {

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


    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    protected int score, maxTile;

    public Model() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        resetGameTiles();
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
        for (int i = 0; i < FIELD_WIDTH ; i++) {
            if(compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) isChange = true;
        }
         if(isChange) addTile();
    }

    public void right(){
        boolean isRightChange = false;
        rotationClockWise();
        rotationClockWise();
        for (int i = 0; i < FIELD_WIDTH ; i++) {
            if(compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) isRightChange = true;
        }
        rotationClockWise();
        rotationClockWise();
        if(isRightChange) addTile();

    }

    public void up(){
        boolean isUpChange = false;
        rotationClockWise();
        rotationClockWise();
        rotationClockWise();
        for (int i = 0; i < FIELD_WIDTH ; i++) {
            if(compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) isUpChange = true;
        }
        rotationClockWise();
         if (isUpChange) addTile();
    }

    public void down(){
        boolean isDownChange = false;
        rotationClockWise();
        for (int i = 0; i < FIELD_WIDTH ; i++) {
            if(compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) isDownChange = true;
        }
        rotationClockWise();
        rotationClockWise();
        rotationClockWise();
         if (isDownChange) addTile();
    }
}
