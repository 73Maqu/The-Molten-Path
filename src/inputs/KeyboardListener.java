package inputs;

import main.Game;
import main.GameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.PLAYING;
/**
 * Klasa odpowiedzialna za obsługę klawiatury
 */
public class KeyboardListener implements KeyListener {
    /** Zmienna typu Game */
    private Game game;

    /**
     * Konstruktor - ustawienie parametrów obiektu, ustalenie jego typu
     * @param game zmienna typu Game
     */
    public KeyboardListener(Game game){
        this.game=game;
    }
    /**
     * Dodaj obsługę zdarzeń - klawisz naciśnięty
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(GameStates.gameState==PLAYING);
        game.getPlaying().keyPressed(e);
    }

    /**
     * Nieużywana obsługa zdarzeń
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    /**
     * Nieużywana obsługa zdarzeń
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
