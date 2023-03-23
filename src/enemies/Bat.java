package enemies;

import managers.EnemyManager;

import static helper.Constants.Enemies.BAT;

/**
 * Klasa przeciwnika nietoperza dziedzicząca po klasie Enemy
 */
public class Bat extends Enemy{
    public Bat(float x, float y, int ID, EnemyManager enemyManager) {super(x, y, ID, BAT,enemyManager);}
}