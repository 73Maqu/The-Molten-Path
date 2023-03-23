package main;
/**
 * Klasa odpowiedzialna za różne stany gry
 */
public enum GameStates {
    /** Wyliczenie stanów gry*/
    PLAYING, MENU, SETTINGS,GAME_OVER,GAME_WON;
    /**
     * Ustawienie podstawowego stanu gry
     */
    public static GameStates gameState = MENU;
    /**
     * Ustaw stan gry
     * @param state
     */
    public static void SetGameState(GameStates state){
        gameState = state;
    }
}
