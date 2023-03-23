package managers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static helper.Constants.Direction.*;
import static helper.Constants.Enemies.*;
import static helper.Constants.Tiles.*;


import enemies.*;
import helper.LoadSave;
import scenes.Playing;
/**
 * Klasa odpowiedzialna za zarządzanie przeciwnikami
 */
public class EnemyManager {

	/** Zmienna klasy Playing */
	private Playing playing;
	/** Zmienna przechowująca tekstury przeciwników*/
	private BufferedImage[] enemyImgs;
	/** Lista przeciwników */
	private ArrayList<Enemy> enemies = new ArrayList<>();
	/** Zmienna odpowiadająca za szerokość paska życia*/
	private int HPbarWidth=20;
	/** Zmienna przechowująca efekt graficzny spowolnienia */
	private BufferedImage slowEffect;

	/**
	 * Konstruktor - wczytanie tekstur i efektów
	 */
	public EnemyManager(Playing playing) {
		this.playing = playing;
		enemyImgs = new BufferedImage[4];
		loadEnemyImgs();
		loadEffectImg(); 
	}

	/**
	 * Wczytanie efektu spowolnienia
	 */
	private void loadEffectImg() {
		slowEffect=LoadSave.getSpriteAtlas().getSubimage(32*8,32*1,32,32);
	}

	/**
	 * Wczytanie tekstur przeciwników
	 */
	private void loadEnemyImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		enemyImgs[0] = atlas.getSubimage(0*32, 1*32, 32, 32); //ork
		enemyImgs[1] = atlas.getSubimage(1*32, 1*32, 32, 32); //nietoperz
		enemyImgs[2] = atlas.getSubimage(2*32, 1*32, 32, 32); //rycerz
		enemyImgs[3] = atlas.getSubimage(3*32, 1*32, 32, 32); //wilk

	}

	/**
	 * Jeżeli przeciwnik żyje - porusza się
	 */
	public void update() {
		for (Enemy e : enemies)
			if(e.isAlive())
			upadateEnemyMove(e);
	}

	/**
	 * Jeżeli następny kafelek to droga-porusz się. Jeżeli koniec mapy - usuń przeciwnika i odejmij życie
	 */
	public void upadateEnemyMove(Enemy e) {
		if(e.getLastDir()== -1)
			setNewDirectionAndMove(e);
		int newX = (int) (e.getX() + getSpeedX(e.getLastDir(), e.getEnemyType()));
		int newY = (int) (e.getY() + getSpeedY(e.getLastDir(), e.getEnemyType()));

		if (getTileType(newX, newY) == ROAD_TILE) {
			e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
		} else if (isAtEnd(e)) {
			e.kill();
			playing.removeOneLife();
		} else {
			setNewDirectionAndMove(e);
		}
	}

	/**
	 * Zmiana kierunku i poruszenie się
	 */
	private void setNewDirectionAndMove(Enemy e) {
		int dir = e.getLastDir();

		int xCord = (int) (e.getX() / 32);
		int yCord = (int) (e.getY() / 32);

		fixEnemyOffsetTile(e, dir, xCord, yCord);
		if(isAtEnd(e))
			return;

		if (dir == LEFT || dir == RIGHT) {
			int newY = (int) (e.getY() + getSpeedY(UP,e.getEnemyType()));
			if (getTileType((int) e.getX(), newY) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()),UP);
			else
				e.move(GetSpeed(e.getEnemyType()),DOWN);
		} else {
			int newX = (int)(e.getX() + getSpeedX(RIGHT,e.getEnemyType()));
			if (getTileType(newX, (int) e.getY()) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()),RIGHT);
			else
				e.move(GetSpeed(e.getEnemyType()),LEFT);
		}
	}
	/**
	 * Poprawa pozycji przeciwnika
	 */
	private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
		switch (dir) {
			case RIGHT:
				if (xCord < 19)
					xCord++;
				break;
			case DOWN:
				if (yCord < 19)
					yCord++;
				break;
		}
		e.setPos(xCord * 32, yCord * 32);
	}

	/**
	 * Sprawdzenie czy przeciwnik jest na końcu
	 */
	private boolean isAtEnd(Enemy e) {
		if(e.getX()== 19*32 && e.getY()==7*32){ //współrzędne końca
			return true;
		}
		else return false;
	}
	/**
	 * Dodanie przeciwnika
	 */
	public void addEnemy(int EnemyType) {
		int xStart=0*32; //pozycja startowa
		int yStart=10*32;
				switch (EnemyType){
			case ORC: enemies.add(new Orc(xStart, yStart,0,this));
				break;
				case BAT: enemies.add(new Bat(xStart, yStart,1,this));
				break;
			case KNIGHT: enemies.add(new Knight(xStart, yStart,2,this));
				break;
			case WOLF: enemies.add(new Wolf(xStart, yStart,3,this));
				break;
		}
	}

	/**
	 * Dodanie przeciwnika
	 */
	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
	}

	/**
	 * Usunięcie przeciwników
	 */
	public void reset() {
		enemies.clear();
	}

	/**
	 * Nagrodzenie gracza za pokonanie przeciwnika
	 */
	public void rewardPlayer(int enemyType) {
		playing.rewardPlayer(enemyType);
	}

	/**
	 * Wywołanie metod rysujących przeciwnika, pasek życia i efekty
	 */
	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			if(e.isAlive()) {
				drawEnemy(e, g);
				drawHealthBar(e, g);
				drawEffects(e,g);
			}
		}
	}

	/**
	 * Narysowanie efektu spowolnienia jeżeli przeciwnik jest spowolniony
	 */

	private void drawEffects(Enemy e, Graphics g) {
		if(e.isSlowed()) {
			g.drawImage(slowEffect,(int)e.getX(),(int)e.getY(),null);
			}
		}

	/**
	 * Narysowanie pasku życia, zależnego od HP przeciwnika
	 */
	private void drawHealthBar(Enemy e, Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)e.getX()+16-(getNewBarWidth(e)/2),(int)e.getY()-10,getNewBarWidth(e),3);
	}

	/**
	 * Narysowanie przeciwnika
	 */
	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
	}

	/**
	 * Pobierz NewBarWidth
	 * @return (int)(HPbarWidth*e.getHealthBarFloat() - szerokość paska po zdaniu obrażeń
	 */
	private int getNewBarWidth(Enemy e){
		return (int)(HPbarWidth*e.getHealthBarFloat());
	}

	/**
	 * Pobierz listę Enemies
	 * @return enemies
	 */
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}


	/**
	 * Pobierz liczbę żywych przecwinków
	 * @return  liczbę żywych przecwinków
	 */
    public int getAmountOfAliveEnemies() {
		int size=0;
		for(Enemy e:enemies)
				if(e.isAlive())
					size++;
		return size;
    }

	/**
	 * Pobierz tileType
	 * @return tileType
	 */
	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	/**
	 * Pobierz SpeedY
	 * @return SpeedY+32 lub -SpeedY
	 */
	private float getSpeedY(int dir, int enemyType) {
		if(dir==UP)
			return -GetSpeed(enemyType);
		else if(dir == DOWN)
			return GetSpeed(enemyType)+32;
		return 0;
	}
	/**
	 * Pobierz SpeedX
	 * @return SpeedX+32 lub -SpeedX
	 */
	private float getSpeedX(int dir, int enemyType) {
		if(dir==LEFT)
			return -GetSpeed(enemyType);
		else if(dir == RIGHT)
			return  GetSpeed(enemyType)+32; //jak nie ma 32 to opóxnia
		return 0;
	}


}

