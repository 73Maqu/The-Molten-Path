package managers;

import Objects.Tower;
import enemies.Enemy;
import helper.LoadSave;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Klasa odpowiedzialna za zarządzanie wieżyczkami
 */
public class TowerManager {
    /** Zmienna klasy Playing */
    private Playing playing;
    /** Tablica przechowujaca tekstury kafelków */
    private BufferedImage[] towerImgs;
    /** Tablica przechowujaca wieżyczki */
    private ArrayList<Tower> towers= new ArrayList<>();
    /** Numer porządkowy wieżyczki*/
    private int towerAmount=0;

    /**
     * Konstruktor - wczytanie tekstur
     */
    public TowerManager(Playing playing) {
        this.playing = playing;
        loadTowerImgs();

    }


    /**
     * Importowanie tekstur wieżyczek
     */
    private void loadTowerImgs(){
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs=new BufferedImage[3];
        towerImgs[0] = atlas.getSubimage(4*32, 1*32, 32, 32);
        towerImgs[1] = atlas.getSubimage(5*32, 1*32, 32, 32);
        towerImgs[2] = atlas.getSubimage(6*32, 1*32, 32, 32);
    }

    /**
     * Wyrysowanie wieżyczek
     */
    public void draw(Graphics g){
        for(Tower t:towers)
            g.drawImage(towerImgs[t.getTowerType()],t.getX(),t.getY(),null);
    }

    /**
     * Aktualizowanie ataku wieżyczek - atak jeżeli przeciwnik jest w zasięgu
     */
    public void update() {
        for(Tower t: towers) {
            t.update();
            attackEnemyIfClose(t);
        }
    }

    /**
     * Atakowanie przeciwników jeżeli są w zasięgu
     */
    private void attackEnemyIfClose(Tower t) {

        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive())
                if (isEnemyInRange(t, e)) {
                    if (t.isCdOver()) {
                        playing.shootEnemy(t, e);
                        t.resetCd();
                    }
                } else {//nothing}
                }

        }
    }
    /**
     * Dodanie wieżyczki
     */
    public void addTower(Tower selectedTower,int xPos,int yPos) {
        towers.add(new Tower(xPos,yPos,towerAmount++,selectedTower.getTowerType()));
    }

    /**
     * Zwrócenie dystansu od przeciwnika
     */

    public static int GetHypoDistance(float x1,float y1, float x2, float y2){
        float xDiff=Math.abs(x1-x2);
        float yDiff=Math.abs(y1-y2);
        return (int) Math.hypot(xDiff,yDiff);
    }

    /**
     * Sprawdzenie czy przeciwnik jest w zasięgu
     */
    private boolean isEnemyInRange(Tower t, Enemy e){
        int range = GetHypoDistance(t.getX(),t.getY(),e.getX(),e.getY());
        return range<t.getRange();
    }

    /**
     * Pobierz towerImgs
     * @return towerImgs
     */
    public BufferedImage[] getTowerImgs(){
        return towerImgs;
    }


    /**
     * Funckja służąca do zapobiegania stawianiu wieżyczek na sobie
     */
    public Tower getTowerAt(int x, int y) {
        for(Tower t: towers)
            if(t.getX()==x && t.getY()==y){
                return t;
            }
        return null;
    }

    /**
     * Funckja służąca do usuwania wieżyczek
     */
    public void removeTower(Tower displayedTower) {
        for(int i=0;i<towers.size();i++)
            if(towers.get(i).getId()==displayedTower.getId())
                towers.remove(i);

    }

    /**
     * Funckja służąca do ulepszania wieżyczek
     */
    public void upgradeTower(Tower displayedTower) {
        for (Tower t : towers)
            if (t.getId() == displayedTower.getId())
                t.upgradeTower();
    }

    /**
     * Usunięcie wieżyczek
     */
    public void reset() {
        towers.clear();
        towerAmount = 0;
    }
}

