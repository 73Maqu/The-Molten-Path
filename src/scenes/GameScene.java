package scenes;

import main.Game;
/**
 * Klasa abstrakcyjna sceny gry
 */
public abstract class GameScene {
    /** Zmienna typu game */
    protected Game game;

    /** Zmienna działająca jako timer */
    protected int tick;

    /** Zmienna służaca do odliczania czasu do kolejnej aktualizacji */
    protected int ANIMATION_SPEED=25;

    /**
     * Konstruktor - zainicjowanie gry
     */
    public GameScene(Game game){
        this.game = game;
    }

    /**
     * Pobierz game
     * @return game
     */
    public Game getGame(){
        return game;
    }

    /**
     * Metoda zwiększająca i resetująca timer
     */
    protected void updateTick(){
        tick++;
        if (tick >= ANIMATION_SPEED){
            tick=0;
        }
    }

    /**
     * Dodaj obsługę zdarzeń - naciśnięcie przycisku myszki
     */
    public abstract void mousePressed(int x, int y);
}
