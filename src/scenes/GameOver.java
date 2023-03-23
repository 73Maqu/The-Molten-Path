package scenes;

import java.awt.*;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;
/**
 * Klasa odpowiedzialna za ekran po przegranej
 */
public class GameOver extends GameScene implements SceneMethods {

    /** Przycisk Replay*/
    private MyButton bReplay;
    /** Przycisk Menu*/
    private MyButton bMenu;

    /**
     * Konstruktor - inicjalizacja przycisków
     */
    public GameOver(Game game) {
        super(game);
        initButtons();
    }


    /**
     * Utworzenie przycisków
     */
    private void initButtons() {

        int w = 150;
        int h = w / 3;
        int x = 880 / 2 - w / 2;
        int y = 300;
        int yOffset = 100;

        bMenu = new MyButton("Menu", x, y, w, h);
        bReplay = new MyButton("Replay", x, y + yOffset, w, h);

    }

    /**
     * Metoda rysująca przyciski
     */
    @Override
    public void render(Graphics g) {
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.red);
        g.drawString("Game Over!", 300, 80);

        g.setFont(new Font("LucidaSans", Font.BOLD, 20));
        bMenu.draw(g);
        bReplay.draw(g);
    }

    /**
     * Metoda pozwalajca zagrać jeszcze raz
     */
    private void replayGame() {
        resetAll();
        SetGameState(PLAYING);
    }

    /**
     * Metoda pozwalajca zrestetować grę
     */
    private void resetAll() {
        game.getPlaying().resetEverything();
    }

    /**
     * Dodaj obsługę zdarzeń - kliknięcie przycisku myszki
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            SetGameState(MENU);
            resetAll();
        } else if (bReplay.getBounds().contains(x, y))
            replayGame();
    }

    /**
     * Dodaj obsługę zdarzeń - poruszenie myszki
     */
    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bReplay.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if (bReplay.getBounds().contains(x, y))
            bReplay.setMouseOver(true);
    }

    /**
     * Dodaj obsługę zdarzeń - naciśnięcie przycisku myszki
     */
    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if (bReplay.getBounds().contains(x, y))
            bReplay.setMousePressed(true);
    }

    /**
     * Dodaj obsługę zdarzeń - puszczenie przycisku myszki
     */
    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bReplay.resetBooleans();
    }
}