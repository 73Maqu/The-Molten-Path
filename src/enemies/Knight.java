package enemies;

import managers.EnemyManager;

import static helper.Constants.Enemies.KNIGHT;
/**
 * Klasa przeciwnika rycerza dziedzicząca po klasie Enemy
 */
public class Knight extends Enemy{
    public Knight(float x, float y, int ID, EnemyManager enemyManager) {super(x, y, ID, KNIGHT,enemyManager);}
}