package helper;
/**
 * Klasa zawierające stałe wartości wieżyczek, kierunków, pocisków i przeciwników
 */
public class Constants {
    /**
     * Klasa przypisująca stałe dla kierunków
     */
    public static class Direction{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    /**
     * Klasa przypisująca stałe dla każdego typu pocisku
     */
    public static class Projectiles{
        public static final int ARROW = 0;
        public static final int CHAINS = 1;
        public static final int BOMB = 2;

        /**
         * Pobierz prędkość pocisku
         * @return prędkość pocisku
         */
        public static float GetSpeed(int type) {
            switch (type) {
                case ARROW:
                    return 8f;
                case BOMB:
                    return 4f;
                case CHAINS:
                    return 6f;
            }
            return 0f;
        }
    }

    /**
     * Klasa przypisująca stałe dla każdego typu przeciwnika
     */
    public static class Enemies{
        public static final int ORC=0;
        public static final int BAT=1;
        public static final int KNIGHT=2;
        public static final int WOLF=3;

        /**
         * Pobierz prędkość przeciwnika
         * @return prędkość przeciwnika
         */
        public static  float GetSpeed(int enemyType){
            switch (enemyType){
                case ORC:
                    return 0.5f;
                case BAT:
                    return 0.7f;
                case KNIGHT:
                    return 0.3f;
                case WOLF:
                    return 0.6f;
            }
            return 0;
        }
        /**
         * Pobierz liczbę punktów życia przeciwnika
         * @return liczbę punktów życia przeciwnika
         */
        public static int GetStartHealth(int enemyType) {
            switch (enemyType){
                case ORC:
                    return 50;
                case BAT:
                    return 60;
                case KNIGHT:
                    return 100;
                case WOLF:
                    return 50;
            }
            return 0;
        }
        /**
         * Pobierz liczbę złota za zabicie przeciwnika
         * @return liczbę złota za zabicie przeciwnika
         */
        public static int GetReward(int enemyType) {
            switch (enemyType) {
                case ORC:
                    return 5;
                case BAT:
                    return 5;
                case KNIGHT:
                    return 25;
                case WOLF:
                    return 10;
            }
            return 0;
        }
        }

    /**
     * Klasa przypisująca stałe dla każdego typu "kafelka"
     */
    public static class Tiles{
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }
    /**
     * Klasa przypisująca stałe dla każdego typu wieżyczki
     */

    public static class Towers{
        public static final int CANNON=0;
        public static final int ARCHER=1;
        public static final int WIZARD=2;

        /**
         * Pobierz nazwę wieżyczki
         * @return nazwa wieżyczki
         */
        public static String GetName(int towerType){
            switch (towerType){
                case CANNON:
                    return "Cannon";
                case WIZARD:
                    return "Wizard";
                case ARCHER:
                    return "Archer";
            }
            return "";
        }
        /**
         * Pobierz koszt wieżyczki
         * @return koszt wieżyczki
         */
        public static int GetTowerCost(int towerType) {
            switch (towerType) {
                case CANNON:
                    return 65;
                case ARCHER:
                    return 35;
                case WIZARD:
                    return 50;
            }
            return 0;
        }
        /**
         * Pobierz obrażenia wieżyczki
         * @return obrażenia wieżyczki
         */
        public static int GetDefaultDMG(int towerType){
            switch (towerType){
                case CANNON:
                    return 15;
                case WIZARD:
                    return 0;
                case ARCHER:
                    return 5;
            }
            return 0;
        }
        /**
         * Pobierz zasięg wieżyczki
         * @return zasięg wieżyczki
         */
        public static float GetDefaultRange(int towerType){
            switch (towerType){
                case CANNON:
                    return 100;
                case WIZARD:
                    return 100;
                case ARCHER:
                    return 100;
            }
            return 0;
        }
        /**
         * Pobierz czas odnowienia wieżyczki
         * @return czas odnowienia wieżyczki
         */
        public static float GetDefaultCD(int towerType){
            switch (towerType){
                case CANNON:
                    return 120;
                case WIZARD:
                    return 25;
                case ARCHER:
                    return 40;
            }
            return 0;
        }
    }

}