package scenes;

import Objects.Tower;
import enemies.Enemy;
import helper.LevelBuilder;
import main.Game;
import managers.*;
import ui.BottomBar;
import static helper.Constants.Tiles.GRASS_TILE;
import static main.GameStates.*;

import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * Klasa odpowiedzialna za granie
 */
public class Playing extends GameScene implements SceneMethods {
    /** Tablica przechowująca poziom*/
    private int[][] lvl;
    /** Zmienna typu TileManager */
    private TileManager tileManager;
    /** Zmienna typu EnemyManager */
    private EnemyManager enemyManager;
    /** Zmienna typu TowerManager */
    private TowerManager towerManager;
    /** Zmienna typu ProjectileManager */
    private ProjectileManager projectileManager;
    /** Zmienna typu WaveManager */
    private WaveManager waveManager;
    /** Zmienna typu BottomBar */
    private BottomBar bottomBar;
    /** Zmienna przechowująca współrzędną X myszki */
    private int mouseX;
    /** Zmienna przechowująca współrzędną Y myszki */
    private int mouseY;
    /** Zmienna typu Tower przechowująca wybraną wieżyczkę */
    private Tower selectedTower;
    /** Timer odpowiadający pasywnemy dochodowi  złota u gracza*/
    private int goldTick;
    /** Zmienna odpowiadająca za stan zapauzowania gra*/
    private boolean gamePaused=false;

    /**
     * Konstruktor - zainicjowanie wszystkich managerów, poziomu oraz bocznego panelu
     */
    public Playing(Game game) {
        super(game);
        lvl = LevelBuilder.getLevelData();
        tileManager = new TileManager();
        enemyManager = new EnemyManager(this);
        towerManager = new TowerManager(this);
        waveManager = new WaveManager(this);
        projectileManager = new ProjectileManager(this);
        bottomBar = new BottomBar(640, 0, 240, 640, this);

        //LVL
    }


    /**
     * Metoda wywołująca rysowanie poziomu, przeciwników, pocisków, panelu bocznego,
     * wybranej wieżyczki oraz informacji o falach
     */
    @Override
    public void render(Graphics g) {
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(tileManager.getSprite(id), x * 32, y * 32, null);
            }
        }
        enemyManager.draw(g);
        towerManager.draw(g);
        projectileManager.draw(g);
        bottomBar.draw(g);
        drawSelectedTower(g);
        drawHighlight(g);
        drawWaveInfos(g);
    }

    /**
     * Metoda wyrysowująca informację o fali
     */
    private void drawWaveInfos(Graphics g) {

    }

    /**
     * Metoda usawtiająca pauzę
     */
    public void setGamePaused(boolean gamePaused){
        this.gamePaused=gamePaused;
    }

    /**
     * Metoda zwracająca czy gra jest zapauzowana
     */
    public boolean isGamePaused(){
        return gamePaused;
    }


    /**
     * Metoda podświetlająca najechany myszką kafelek
     */
    private void drawHighlight(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.drawRect(mouseX,mouseY,32,32);
    }

    /**
     * Metoda wyrysowująca wybraną wieżę
     */
    private void drawSelectedTower(Graphics g) {
        if (selectedTower != null)
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
    }

    /**
     * Pobierz współrzędną x, współrzędną y
     * @return typ kafelka;
     */
    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if (xCord < 0 || xCord > 19)
            return 0;
        if (yCord < 0 || yCord > 19)
            return 0;

        int id = lvl[y / 32][x / 32];
        return game.getTileManager().getTile(id).getTileType();
    }

    /**
     * Dodaj obsługę zdarzeń - kliknięcie przycisku myszki
     */
    @Override
    public void mouseClicked(int x, int y) {
        if (x >= 640)
            bottomBar.mouseClicked(x, y);
        else {
            if (selectedTower != null) {
                if (isTileGrass(mouseX, mouseY)) { //tylko trawa
                    if (getTowerAt(mouseX, mouseY) == null) {//brak stakowania
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        removeGold(selectedTower.getTowerType());
                        selectedTower = null;

                    }
                }
            } else {
                Tower t = getTowerAt(mouseX, mouseY);
                    bottomBar.displayTower(t);
            }
        }
    }

    /**
     * Dodaj obsługę zdarzeń - naciśnięcie przycisku myszki
     */
    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640)
            bottomBar.mousePressed(x, y);

    }

    /**
     * Dodaj obsługę zdarzeń - puszczenie przycisku myszki
     */
    @Override
    public void mouseReleased(int x, int y) {
        bottomBar.mouseReleased(x, y);
    }

    /**
     * Dodaj obsługę zdarzeń - poruszenie myszki
     */
    @Override
    public void mouseMoved(int x, int y) {
        if (x >= 640)
            bottomBar.mouseMoved(x, y);
        else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    /**
     * Metoda usuwająca złoto przy zakupie wieży
     */
    private void removeGold(int towerType) {
        bottomBar.payForTower(towerType);
    }

    /**
     * Metoda stawiająca wieżyczkę
     */
    private Tower getTowerAt(int x, int y) {
        return  towerManager.getTowerAt(x,y);
    }

    /**
     * Metoda sprawdzająca czy kafelek jest trawą
     */
    private boolean isTileGrass(int x, int y) {
        int id = lvl[y/32][x/32];
        int tileType = game.getTileManager().getTile(id).getTileType();
        return tileType==GRASS_TILE;
    }


    /**
     * Metoda aktualizująca wszystkie timery i managerów
     */
    public void update() {
        if (!gamePaused) {
            updateTick();
            waveManager.update();
            goldTick++;
            if (goldTick % (60 * 3) == 0)
                bottomBar.addGold(1);
            if (isAllEnemiesDead()) {
                if (isThereMoreWaves()) {
                    waveManager.startWaveTimer();

                    if (isWaveTimerOver()) {
                        waveManager.increaseWaveIndex();
                        enemyManager.getEnemies().clear();
                        waveManager.resetEnemyIndex();
                    }
                }else{ SetGameState(GAME_WON);} //nie ma fal - wygrana
            }
            if (isTimeForNewEnemy()) {
                spawnEnemy();
            }
            enemyManager.update();
            towerManager.update();
            projectileManager.update();

        }
    }

    /**
     * Metoda sprawdzająca czy skończył się timer fali
     */
    private boolean isWaveTimerOver() {
        return waveManager.isWaveTimerOver();
    }

    /**
     * Metoda sprawdzająca czy jest więcej fali
     */
    private boolean isThereMoreWaves() {
        return waveManager.isThereMoreWaves();
    }

    /**
     * Metoda sprawdzająca czy wszyscy przeciwnicy są martwi
     */
    private boolean isAllEnemiesDead() { //trupy
        if(waveManager.isThereMoreEnemiesInWave()){
            return false;
        }
        for(Enemy e: enemyManager.getEnemies())
            if(e.isAlive())
                return false;
        return true;
    }

    /**
     *
     * @return towerManager
     */
    public TowerManager getTowerManager(){
        return towerManager;
    }


    /**
     * Metoda nagradzająca gracza za pokonanie przeciwnika
     */
    public void rewardPlayer(int enemyType) {
        bottomBar.addGold(helper.Constants.Enemies.GetReward(enemyType));
    }

    /**
     *
     * @return enemyManager
     */
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }

    /**
     *
     * @return waveManager
     */
    public WaveManager getWaveManager(){
        return waveManager;
    }

    /**
     * Metoda sprawdzająca czy gra powinna wysłać kolejnego przeciwnika
     */
    private boolean isTimeForNewEnemy(){
        if(waveManager.isTimeForNewEnemy()){
            if(waveManager.isThereMoreEnemiesInWave())
                return true;
        }
        return false;
    }

    /**
     * Metoda dodająca przeciwnika
     */
    private void spawnEnemy() {
        enemyManager.spawnEnemy(waveManager.getNextEnemy());
    }

    /**
     * Ustaw wieżyczkę
     * @param selectedTower
     */
    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower=selectedTower;
    }

    /**
     * Dodaj obsługę zdarzeń - klawisz naciśnięty
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            selectedTower = null;
        }
    }

    /**
     * Metoda strzelająca do przeciwnika
     */
    public void shootEnemy(Tower t, Enemy e) {
        projectileManager.newProjectile(t,e);
    }

    /**
     * Metoda usuwająca wieżyczkę
     */
    public void removeTower(Tower displayedTower) {
        towerManager.removeTower(displayedTower);
    }

    /**
     * Metoda ulepszająca wieżyczkę
     */
    public void upgradeTower(Tower displayedTower) {
        towerManager.upgradeTower(displayedTower);
    }

    /**
     * Metoda zabierająca życie
     */
    public void removeOneLife() {
        bottomBar.removeOneLife();
    }

    /**
     * Metoda resetująca wszystko
     */
    public void resetEverything() {
        bottomBar.resetEverything();
        enemyManager.reset();
        towerManager.reset();
        projectileManager.reset();
        waveManager.reset();

        mouseX = 0;
        mouseY = 0;

        selectedTower = null;
        goldTick = 0;
        gamePaused = false;
    }
}

