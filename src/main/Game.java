package main;

import managers.TileManager;
import scenes.*;
import inputs.KeyboardListener;
import inputs.MyMouseListener;

import javax.swing.JFrame;
import java.awt.image.BufferedImage;

/**
 * Okno główne gry
 */

public class Game extends JFrame implements Runnable{

    /** Zmienna ekranu gry */
    private GameScreen gameScreen;
    /** Zmienna Game thread - podzielenie gry na parę "wątków" */
    private Thread gameThread;
    /** Zmienna do renderowania */
    private Render render;
    /** Zmienna klasy Menu */
    private Menu menu;
    /** Zmienna klasy Playing - gra*/
    private Playing playing;
    /** Zmienna klasy Settings - ustawienia*/
    private Settings settings;
    /** Zmienna klasy GameOver - koniec gry*/
    private GameOver gameOver;
    /** Zmienna klasy GameWon - koniec gry*/
    private GameWon gameWon;
    /** Zmienna klasy TileManager - zarządzanie kafelkami*/
    private TileManager tileManager;

    /**
     * Konstruktor - zainicjowanie klas, dodanie ekranu i ustawienie jego własności
     */
    public Game(){
        initClasses();
        setDefaultCloseOperation(EXIT_ON_CLOSE); //zamknięcie aplikacji
        setResizable(false); //stały rozmiar
        setTitle("The Molten Path"); //tytuł okna
        //setLocationRelativeTo(null);
        add(gameScreen);
        pack();
        setVisible(true);

    }

    /**
     * Start gry
     */
    public static void main(String[] args){
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    /**
     * Inicjowanie klas
     */
    private void initClasses() {
        tileManager = new TileManager();
        render = new Render(this);
        gameScreen = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        gameOver=new GameOver(this);
        gameWon=new GameWon(this);
    }

    /**
     * Wystartowanie nowego gameThreadu
     */
    private void start(){
        gameThread=new Thread(this){};
        gameThread.start();
    }

    /**
     * Zmiana między "oknami" gry
     */
    private void updateGame(){
        switch (GameStates.gameState){
            case MENU:
                break;
            case PLAYING:
                playing.update();
                break;
            case SETTINGS:
                break;
            default:
                break;
        }
    }


    /**
     * Aktualizowanie FPS i UPS
     */
    public void run() {

        double timePerFrame = 1000000000.0/120.0;
        double timePerUpdate = 1000000000.0/60.0;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates =0;

        long now;
        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                lastFrame = now;
                repaint();
                frames++;
            }
            if(now-lastUpdate>=timePerUpdate){
                updateGame();
                lastUpdate=now;
                updates++;
            }
            if(System.currentTimeMillis() - lastTimeCheck>=1000){
                frames =0;
                updates=0;
                lastTimeCheck=System.currentTimeMillis();
            }
        }
    }
    /**
     * Pobierz gameOver
     * @return gameOver
     */
    public GameOver getGameOver(){
        return gameOver;
    }

    /**
     * Pobierz gameWon
     * @return gameWon
     */
    public GameWon getGameWon(){return gameWon;}
    /**
     * Pobierz render
     * @return render
     */
    public Render getRender() {
        return render;
    }
    /**
     * Pobierz menu
     * @return menu
     */
    public Menu getMenu() {
        return menu;
    }
    /**
     * Pobierz playing
     * @return playing
     */
    public Playing getPlaying() {
        return playing;
    }
    /**
     * Pobierz settings
     * @return settings
     */
    public Settings getSettings() {
        return settings;
    }
    /**
     * Pobierz tileManager
     * @return tileManager
     */
    public TileManager getTileManager() {
        return tileManager;
    }
}

