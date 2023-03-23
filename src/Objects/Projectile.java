package Objects;

import java.awt.geom.Point2D;
/**
 * Klasa dotycząca pocisków
 */
public class Projectile {
    /** Zmienna przechowująca pozycję */
    private Point2D.Float pos;
    /** Numer porządkowy pocisku*/
    private int id;
    /** Zmienna przechowująca typ pocisku*/
    private int projectileType;
    /** Zmienna przechowująca obrażenia pocisku*/
    private int dmg;
    /** Zmienna przechowująca prędkość X pocisku*/
    private float xSpeed;
    /** Zmienna przechowująca prędkość Y pocisku*/
    private float ySpeed;
    /** Zmienna przechowująca rotację pocisku*/
    private float rotation;
    /** Zmienna przechowująca stan aktywności pocisku */
    private boolean active = true;


    /**
     * Konstruktor - ustawienie parametrów obiektu, ustalenie jego typu
     * @param xSpeed początkowa współrzędna x
     * @param ySpeed początkowa współrzędna y
     * @param dmg początkowe obrażenia
     * @param rotation początkowa rotacja
     * @param id początkowe id
     * @param projectileType typ Pocisku
     */
    public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg,float rotation,int id, int projectileType) {
        pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
    }

    /**
     * Metoda odpowiadająca za poruszanie pocisku
     */
    public void move() {
        pos.x += xSpeed;
        pos.y += ySpeed;
    }

    /**
     * Metoda zwaracająca czy pocisk jest aktywny
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Metoda ustwiajaca że pocisk jest aktywny
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Pobierz obrażenia
     * @return obrazenia
     */
    public int getDmg() {
        return dmg;
    }

    /**
     * Pobierz rotacje
     * @return rotacje
     */

    public float getRotation() {
        return rotation;
    }

    /**
     * Pobierz pozycja
     * @return pozycja
     */
    public Point2D.Float getPos() {
        return pos;
    }


    /**
     * Pobierz id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Pobierz typ Pocisku
     * @return typ Pocisku
     */
    public int getProjectileType() {
        return projectileType;
    }

}