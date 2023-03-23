package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Klasa odpowiedzialna za ekran gry
 */
public class GameScreen extends JPanel {
    /** Zmienna klasy Game*/
    private Game game;
    /** Zmienna odpowiedzialna za rozmiar ekranu*/
    private  Dimension size;
    /** Zmienna odpowiedzialna za obsługę myszki */
    public MyMouseListener myMouseListener;
    /** Zmienna odpowiedzialna za obsługę klawiatury */
    public KeyboardListener keyboardListener;

    /**
     * Konstruktor - zainicjowanie ekranu gry, ustawienie jego wielkości
     */
    public GameScreen(Game game){
        this.game = game;

        setPanelSize();
    }

    /**
     * Zainicijowanie obsługi myszki i klawiatury
     */
    public void initInputs(){
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener(game);
        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);
        requestFocus();
    }
    /**
     * Ustawienie i zablokowanie wymiaru okna
     */
    private void setPanelSize() {
        size = new Dimension(880,640);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    /**
     * Wyrysowanie komponentów
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.getRender().render(g);
        }
    }




