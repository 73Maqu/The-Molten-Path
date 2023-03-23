package Objects;

import helper.Constants;

import static helper.Constants.Towers.*;
/**
 * Klasa dotycząca wieżyczek
 */
public class Tower {
    /** Współrzędna x wieżyczki */
    private int x;
    /** Współrzędna y wieżyczki */
    private int y;
    /** Numer porządkowy wieżyczki */
    private int id;
    /** Typ wieżyczki*/
    private int towerType;
    /** Czas odnowienia wiezyczki*/
    private int cdTick;
    /** Timer czasu odnowienia wieżyczki*/
    private int dmg;
    /** Poziom ulepszeń wieżyczki*/
    private int tier;
    /** Zasięg ulepszeń wieżyczki*/
    private float range;
    /** Czas odnowienia wieżyczki*/
    private float cooldown;

    /**
     * Konstruktor - ustawienie parametrów obiektu, ustalenie jego typu oraz ustawienie wartości domyślnych
     * @param x Współrzędna x wieżyczki
     * @param y Współrzędna y wieżyczki
     * @param id Numer porządkowy wieżyczki
     * @param towerType wieżyczki
     */
    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        setDefaultDmg();
        setDefaultRange();
        setDefaultCD();
    }

    /**
     * Metoda odpowiadająca za ulepszenie wieżyczki (dodanie odpowiednich wartości)
     */
    public void upgradeTower() {
        this.tier++;

        switch (towerType) {
            case ARCHER:
                dmg += 2;
                range += 20;
                cooldown -= 5;
                break;
            case CANNON:
                dmg += 5;
                range += 20;
                cooldown -= 15;
                break;
            case WIZARD:
                range += 20;
                cooldown -= 10;
                break;
        }
    }

    /**
     * Metoda sprawdzająca czy czas odnowienia się zakończył
     */
    public boolean isCdOver() {

        return cdTick >= cooldown;
    }

    /**
     * Metoda zwiększająca timer czasu odnowienia
     */
    public void update(){
         cdTick++;
    }

    /**
     * Metoda resetująca timer czasu odnowienia
     */
    public void resetCd() {
        cdTick=0;
    }

    /**
     * Pobierz współrzędną X
     * @return współrzędną X
     */
    public int getX() {
        return x;
    }


    /**
     * Pobierz współrzędną Y
     * @return współrzędną Y
     */
    public int getY() {
        return y;
    }

    /**
     * Pobierz obrażenia
     * @return obrażenia
     */
    public int getDmg() {
        return dmg;
    }

    /**
     * Pobierz id
     * @return id
     */

    public int getId() {
        return id;
    }
    /**
     * Pobierz zasięg
     * @return zasięg
     */
    public float getRange() {return range;}

    /**
     * Pobierz typ wieżyczki
     * @return towerType wieżyczki
     */
    public int getTowerType() {
        return towerType;
    }
    /**
     * Pobierz poziom ulepszeń wieżyczki
     * @return poziom ulepszeń wieżyczki
     */
    public int getTier() {
        return tier;
    }

    /**
     * Ustaw id wieżyczki
     * @param id id wieżyczki
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Ustaw współrzędna x wieżyczki
     * @param x współrzędna x wieżyczki
     */
    public void setX(int x) {this.x = x;}

    /**
     * Ustaw współrzędna y wieżyczki
     * @param y współrzędna y wieżyczki
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Ustaw domyślny czas odnowienia wieżyczki
     */
    private void setDefaultCD() {
        cooldown=Constants.Towers.GetDefaultCD(towerType);
    }

    /**
     * Ustaw domyślny zasięg wieżyczki
     */
    private void setDefaultRange() {
        range=Constants.Towers.GetDefaultRange(towerType);
    }
    /**
     * Ustaw domyślne obrażenia wieżyczki
     */
    private void setDefaultDmg() {
        dmg=Constants.Towers.GetDefaultDMG(towerType);
    }
}
