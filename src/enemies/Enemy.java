package enemies;

import helper.Constants;
import managers.EnemyManager;

import java.awt.Rectangle;
import static helper.Constants.Direction.*;

/**
 * Klasa abstrakcyjna przeciwnika
 */
public abstract class Enemy {

	/** Współrzędna x przeciwnika */
	private float x;
	/** Współrzędna y przeciwnika */
	private float y;
	/** Hitbox przeciwnika */
	private Rectangle bounds;
	/** Liczba punktów życia przeciwnika*/
	private int health;
	/** Maksymalna liczba punktów życia przeciwnika*/
	private int maxHealth;
	/** Numer porządkowy przeciwnika*/
	private int ID;
	/** Typ przeciwnika (Orc,bat...)*/
	private int enemyType;
	/** Ostatni kierunek w którym poruszył się przeciwnik*/
	private int lastDir;
	/** Zmienna określająca czy przeciwnik żyje*/
	protected boolean alive=true;
	/** Zmienna określająca czas trwania spowolnienia*/
	protected int slowTick=120;
	/** Zmienna określająca maksymalny czas trwania spowolnienia*/
	protected int slowTickLimit=120;
	/** Zmienna typu EnemyManager */
	protected  EnemyManager enemyManager;

	/**
	 * Konstruktor - ustawienie parametrów obiektu, ustalenie jego typu
	 * @param x początkowa współrzędna x
	 * @param y początkowa współrzędna y
	 * @param ID liczba porządkowa przeciwnika
	 * @param enemyType typ przeciwnika
	 * @param enemyManager Zmienna typu EnemyManager
	 */
	public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.enemyType = enemyType;
		this.enemyManager= enemyManager;
		bounds = new Rectangle((int) x, (int) y, 32, 32);
		lastDir = -1;
		setStartHealth();
	}
	/**
	 * Poruszanie przeciwnikiem w odpowiednim kierunku, jeżeli jest spowolniony - obniż jego prędkość
	 */
	public void move(float speed, int dir) {
		lastDir=dir;
		if(slowTick<slowTickLimit){
			slowTick++;
			speed*=0.5f;
		}
		switch (dir){
			case LEFT:
				this.x-=speed;
				break;
			case UP:
				this.y-=speed;
				break;
			case RIGHT:
				this.x+=speed;
				break;
			case DOWN:
				this.y+=speed;
				break;
		}
		updateHitbox();
	}

	/**
	 * Aktualizacja hitboxu przeciwnika (używane po przesunięciu)
	 */
	private void updateHitbox() {
		bounds.x=(int)x;
		bounds.y=(int)y;
	}

	/**
	 * Ustawienie nowych współrzędnych przeciwnika
	 */
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Ustawienie początkowego zdrowia w zależności od typu przeciwnika
	 */
	protected void setStartHealth(){
		health = Constants.Enemies.GetStartHealth(enemyType);
		maxHealth=health;
	}

	/**
	 * Zadanie obrażeń przeciwnikowi, sprawdzenie czy żyje, jeżeli nie -nagrodzenie gracza
	 */
	public void hurt(int dmg) {
		this.health-=dmg;
		if(health<=0) {
			alive = false;
			enemyManager.rewardPlayer(enemyType);
		}
	}

	/**
	 * Sprawdzenie czy przeciwnik żyje
	 */
	public boolean isAlive(){
		return alive;
	}
	/**
	 * Spowolnienie przeciwnika
	 */
	public void slow() {
		slowTick=0;
	}

	/**
	 * Sprawdzenie czy przeciwnik jest spowolniony, jeżeli jest zwaraca prawdę
	 */
	public boolean isSlowed(){
		return slowTick<slowTickLimit; //jeżeli czas trwania spowolnienia jest krótszy od maksymalnego czasu trwania
	}

	/**
	 * Usynięcie przeciwnika, używane gdy przeciwnik dojdzie do końca mapy
	 */
	public void kill() {
		alive=false;
		health=0;
	}

	/**
	 * Pobierz hitbox przeciwnika
	 * @return hitbox przeciwnika
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Pobierz pasek życia przeciwnika
	 * @return pasek życia przeciwnika
	 */
	public float getHealthBarFloat(){
		return health/(float)maxHealth;
	}

	/**
	 * Pobierz współrzędną x przeciwnika
	 * @return współrzędną x przeciwnika
	 */
	public float getX() {
		return x;
	}

	/**
	 * Pobierz współrzędną y przeciwnika
	 * @return współrzędną y przeciwnika
	 */
	public float getY() {
		return y;
	}

	/**
	 * Pobierz typ przeciwnika
	 * @return typ przeciwnika
	 */
	public int getEnemyType() {
		return enemyType;
	}

	/**
	 * Pobierz ostatni kierunek przeciwnika
	 * @return ostatni kierunek  przeciwnika
	 */
	public int getLastDir() {
		return lastDir;}
}
