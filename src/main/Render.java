package main;

import java.awt.*;
/**
 * Klasa odpowiedzialna za renderowanie gry
 */
public class Render {
    /** Zmienna typu Game */
    private Game game;

    /**
     * Konstruktor
     */
    public Render(Game game){
        this.game=game;
    }
    /**
     * Renderowanie odpowiedniego stanu gry
     */
    public void render(Graphics g){
        switch(GameStates.gameState){
            case MENU:
                game.getMenu().render(g);
                break;
            case PLAYING:
                game.getPlaying().render(g);
                break;
            case SETTINGS:
                game.getSettings().render(g);
                break;
            case GAME_OVER:
                game.getGameOver().render(g);
                break;
            case GAME_WON:
                game.getGameWon().render(g);
                break;
        }
    }
}

