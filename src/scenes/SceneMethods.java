package scenes;

import java.awt.*;
/**
 * Interfejs odpowiedzialny za wspólne metody
 */
public interface SceneMethods {
    /** Metoda wyrysowująca*/
    public void render(Graphics g);
    /** Obsługa zdarzeń - kliknięcie przycisku myszki */
    public void mouseClicked(int x, int y);
    /** Obsługa zdarzeń - poruszenie myszki */
    public void mouseMoved(int x, int y);
    /** Obsługa zdarzeń - puszczenie przycisku myszki */
    public void mouseReleased(int x, int y);
}
