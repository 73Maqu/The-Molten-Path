package events;

import java.util.ArrayList;
/**
 * Klasa odpowiedzialna za fale przeciwników
 */
public class Wave {
    /** Lista przeciwników w danej fali*/
    private ArrayList<Integer> enemyList;

    /**
     * Konstruktor - ustawienie parametru obiektu
     * @param enemyList lista przeciwników*/
    public Wave(ArrayList<Integer> enemyList) {
        this.enemyList = enemyList;
    }

    /**
     * Pobierz listę przeciwników
     * @return listę przeciwników
     */
    public ArrayList<Integer> getEnemyList() {
        return enemyList;
    }

}