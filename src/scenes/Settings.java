package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;
/**
 * Klasa odpowiedzialna za Ustawienia
 */
public class Settings extends GameScene implements SceneMethods{

    /** Przycisk Menu*/
    private MyButton bMenu;
    /**
     * Konstruktor - zainicjowanie przycisków
     */
    public Settings(Game game) {
        super(game);
        initButtons();
    }

    /**
     * Metoda wywołująca rysowanie przyciski oraz wypełniająca tło
     */
    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 1280,960);
        drawButtons(g);
    }

    /**
     * Metoda resetujaca przyciski
     */
    private void resetButtons() {
        bMenu.resetBooleans();

    }

    /**
     * Utworzenie przycisków
     */
    private void initButtons() {
        bMenu = new MyButton("Menu", 320, 320, 100, 30);
    }

    /**
     * Metoda rysująca przyciski
     */
    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }

    /**
     * Dodaj obsługę zdarzeń - kliknięcie przycisku myszki
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            SetGameState(MENU);

    }

    /**
     * Dodaj obsługę zdarzeń - poruszenie myszki
     */
    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);

    }

    /**
     * Dodaj obsługę zdarzeń - naciśnięcie przycisku myszki
     */
    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
    }

    /**
     * Dodaj obsługę zdarzeń - puszczenie przycisku myszki
     */
    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }
}
