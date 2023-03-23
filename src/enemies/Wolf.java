package enemies;

import managers.EnemyManager;

import static helper.Constants.Enemies.WOLF;
/**
 * Klasa przeciwnika wilka dziedzicząca po klasie Enemy
 */
public class Wolf extends Enemy{
    public Wolf(float x, float y, int ID,EnemyManager enemyManager) {super(x, y, ID, WOLF,enemyManager);}
}