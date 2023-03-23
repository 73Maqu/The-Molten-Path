package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.GameStates;
/**
 * Klasa odpowiedzialna za obsługę myszki
 */
public class MyMouseListener implements MouseListener, MouseMotionListener {
    /** Zmienna typu Game */
    private Game game;

    /**
     * Konstruktor - ustawienie parametrów obiektu, ustalenie jego typu
     * @param game zmienna typu Game
     */
    public MyMouseListener(Game game) {
        this.game = game;
    }

    /**
     * Dodaj obsługę zdarzeń - poruszenie myszki
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseMoved(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseMoved(e.getX(), e.getY());
                break;
            case SETTINGS:
                game.getSettings().mouseMoved(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mouseMoved(e.getX(), e.getY());
                break;
            case GAME_WON:
                game.getGameWon().mouseMoved(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }
    /**
     * Dodaj obsługę zdarzeń - kliknięcie przycisku myszki
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (GameStates.gameState) {
                case MENU:
                    game.getMenu().mouseClicked(e.getX(), e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mouseClicked(e.getX(), e.getY());
                    break;
                case SETTINGS:
                    game.getSettings().mouseClicked(e.getX(), e.getY());
                    break;
                case GAME_OVER:
                    game.getGameOver().mouseClicked(e.getX(), e.getY());
                    break;
                case GAME_WON:
                    game.getGameWon().mouseClicked(e.getX(), e.getY());
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * Dodaj obsługę zdarzeń - naciśnięcie przycisku myszki
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mousePressed(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mousePressed(e.getX(), e.getY());
                break;
            case SETTINGS:
                game.getSettings().mousePressed(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mousePressed(e.getX(), e.getY());
                break;
            case GAME_WON:
                game.getGameWon().mousePressed(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }
    /**
     * Dodaj obsługę zdarzeń - puszczenie przycisku myszki
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().mouseReleased(e.getX(), e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseReleased(e.getX(), e.getY());
                break;
            case SETTINGS:
                game.getSettings().mouseReleased(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mouseReleased(e.getX(), e.getY());
                break;
            case GAME_WON:
                game.getGameWon().mouseReleased(e.getX(), e.getY());
                break;
            default:
                break;
        }
    }

    /**
     * Nieużywana obsługa zdarzeń
     */
    @Override
    public void mouseEntered(MouseEvent e) {}
    /**
     * Nieużywana obsługa zdarzeń
     */
    @Override
    public void mouseExited(MouseEvent e) {}
    /**
     * Nieużywana obsługa zdarzeń
     */
    @Override
    public void mouseDragged(MouseEvent e) {}

}
