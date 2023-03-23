package managers;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static helper.Constants.Tiles.*;
import Objects.Tile;
import helper.LoadSave;
/**
 * Klasa odpowiedzialna za zarządzanie kafelkami elementami mapy
 */

public class TileManager {
    /** Rodzaje kafelków*/
    public Tile GRASS,WATER,ROAD;
    /** Atlas tekstur*/
    public BufferedImage atlas;
    /** Tablica przechowujaca tekstury kafelków */
    public ArrayList<Tile> tiles = new ArrayList<>();
    /**
     * Konstruktor - wczytanie tekstur
     */
    public TileManager(){
        loadAtlas();
        createTiles();
    }
    /**
     * Wczytanie do tablicy tesktur kafelków
     */
    private void createTiles() {
        int id = 0;
        tiles.add(GRASS = new Tile(getSprite(1,0),id++,GRASS_TILE));
        tiles.add(WATER = new Tile(getSprite(0,0),id++,WATER_TILE));
        tiles.add(ROAD = new Tile(getSprite(2,0),id++,ROAD_TILE));
    }

    /**
     * Wczytanie Atlasu
     */
    private void loadAtlas() {
        atlas = LoadSave.getSpriteAtlas();
    }

    /**
     * Pobierz tile
     * @return tile
     */
    public Tile getTile(int id) {
        return tiles.get(id);
    }

    /**
     * Pobierz Sprite
     * @return tile
     */
    public BufferedImage getSprite(int id) {
        return tiles.get(id).getSprite();
    }

    /**
     * Pobierz Sprite
     * @return atlas
     */
    private BufferedImage getSprite(int xCord, int yCord){
        return atlas.getSubimage(xCord*32,yCord*32,32,32);
    }
}
