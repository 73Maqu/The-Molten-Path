package Objects;

import java.awt.image.BufferedImage;
/**
 * Klasa dotycząca kafelków
 */
public class Tile {
    /** Zmienna przechowująca teksturę kafelka */
    private BufferedImage[] sprite;
    /** Numer porządkowy kafelka*/
    private int id;
    /** Typ kafelka */
    private int tileType;

    /**
     * Konstruktor - ustawienie parametrów obiektu, ustalenie jego typu
     * @param sprite tekstura kafelka
     * @param id Numer porządkowy kafelka
     * @param tileType Typ kafelka
     */
    public Tile(BufferedImage sprite,int id,int tileType){
        this.sprite = new BufferedImage[1];
        this.sprite[0]=sprite;
        this.id = id;
        this.tileType=tileType;
    }

    /**
     * Pobierz typ Kafelka
     * @return typ Kafelka
     */
    public int getTileType() {
        return tileType;
    }

    /**
     * Pobierz Sprite
     * @return Sprite
     */
    public BufferedImage getSprite() {
        return sprite[0];
    }
}
