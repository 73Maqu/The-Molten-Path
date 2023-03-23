package enemies;

import managers.EnemyManager;

import static helper.Constants.Enemies.ORC;
/**
 * Klasa przeciwnika Orka dziedzicząca po klasie Enemy
 */
public class Orc extends Enemy{
    public Orc(float x, float y, int ID,EnemyManager enemyManager) {super(x, y, ID, ORC, enemyManager);}
}
