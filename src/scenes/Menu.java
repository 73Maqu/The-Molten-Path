package scenes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import main.Game;
import ui.MyButton;

import static main.GameStates.*;
/**
 * Klasa odpowiedzialna za Menu
 */
public class Menu extends GameScene implements SceneMethods{
    /** Zmienna przechowująca grafikę tła */
    Image background = Toolkit.getDefaultToolkit().getImage("res/background.png");
    /** Zmienna przechowująca grafikę przycisku Play */
    Image buttonPlay = Toolkit.getDefaultToolkit().getImage("res/play.png");
    /** Zmienna przechowująca grafikę przycisku Settings */
    Image buttonSettings = Toolkit.getDefaultToolkit().getImage("res/settings.png");
    /** Zmienna przechowująca grafikę przycisku Quit */
    Image buttonQuit= Toolkit.getDefaultToolkit().getImage("res/quit.png");

    /** Przycisk Play*/
    private MyButton bPlay;
    /** Przycisk Settings*/
    private MyButton bSettings;
    /** Przycisk Quit*/
    private MyButton bQuit;

    /**
     * Konstruktor - zainicjowanie przycisków
     */
    public Menu(Game game) {
        super(game);
        initButtons();
    }

    /**
     * Utworzenie przycisków
     */
    private void initButtons() {

        int w=200;
        int h=50;
        int x=360;
        int y=430;
        int yOffset = 75;

        bPlay=new MyButton("Play",x,y,w,h);
        bSettings=new MyButton("Settings",x,y+yOffset,w,h);
        bQuit=new MyButton("Quit",x,y+yOffset*2,w,h);
    }

    /**
     * Metoda wywołująca rysowanie przyciski
     */
    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }


    /**
     * Metoda rysująca przyciski razem z ich teksturami + tło
     */
    private void drawButtons(Graphics g) {
        g.drawImage(background, 0, 0, null);
        bPlay.draw(g);
        g.drawImage(buttonPlay, 360, 430, null);
        bSettings.draw(g);
        g.drawImage(buttonSettings, 360, 505, null);
        bQuit.draw(g);
        g.drawImage(buttonQuit, 360, 580, null);


    }

    /**
     * Metoda resetujaca przyciski
     */
    private void resetButtons() {
        bPlay.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();

    }

    /**
     * Dodaj obsługę zdarzeń - kliknięcie przycisku myszki
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (bPlay.getBounds().contains(x, y))
            SetGameState(PLAYING);
        else if (bSettings.getBounds().contains(x, y))
            SetGameState(SETTINGS);
        else if (bQuit.getBounds().contains(x, y))
            System.exit(0);
    }

    /**
     * Dodaj obsługę zdarzeń - naciśnięcie przycisku myszki
     */
    @Override
    public void mousePressed(int x, int y) {

        if (bPlay.getBounds().contains(x, y))
            bPlay.setMousePressed(true);
        else if (bSettings.getBounds().contains(x, y))
            bSettings.setMousePressed(true);
        else if (bQuit.getBounds().contains(x, y))
            bQuit.setMousePressed(true);

    }

    /**
     * Dodaj obsługę zdarzeń - poruszenie myszki
     */
    @Override
    public void mouseMoved(int x, int y) {
        bPlay.setMouseOver(false);
        bSettings.setMouseOver(false);
        if(bPlay.getBounds().contains(x,y)){
            bPlay.setMouseOver(true);
        }
        else if(bSettings.getBounds().contains(x,y)){
            bSettings.setMouseOver(true);
        }
    }

    /**
     * Dodaj obsługę zdarzeń - puszczenie przycisku myszki
     */
    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }
}
