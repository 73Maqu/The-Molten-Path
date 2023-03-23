package managers;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Playing;
/**
 * Klasa odpowiedzialna za zarządzanie wieżyczkami
 */
public class WaveManager {
    /** Zmienna klasy Playing */
    private Playing playing;
    /** Tablica przechowujaca fale*/
    private ArrayList<Wave> waves = new ArrayList<>();
    /** Zmienna przechowująca limit pojawiania się przeciwników na sekundę*/
    private int enemySpawnTickLimit = 60 * 1;
    /** Zmienna służaca do odliczania czasu do pojawienia się kolejnego przeciwnika*/
    private int enemySpawnTick = enemySpawnTickLimit;
    /** Numer porządkowy przeciwnika*/
    private int enemyIndex;
    /** Numer porządkowy fali*/
    private int waveIndex;
    /** Zmienna przechowująca informację co ile pojawi się nowa fala*/
    private int waveTickLimit = 60 * 5;
    /** Zmienna służaca do odliczania czasu do pojawienia się kolejnej fali */
    private int waveTick = 0;
    /** Zmienna służąca do sprawdzenia czy timer fali się zaczął*/
    private boolean waveStartTimer;
    /** Zmienna służaca do sprawdzenia czy czas odliczania się zakończył */
    private boolean waveTickTimeOver;

    /**
     * Konstruktor - utworzenie fal
     */
    public WaveManager(Playing playing) {
        this.playing = playing;
        createWaves();
    }

    /**
     * Aktualizowanie kolejnych fal
     */
    public void update() {
        if (enemySpawnTick < enemySpawnTickLimit)
            enemySpawnTick++;
        if(waveStartTimer){
            waveTick++;
            if(waveTick>=waveTickLimit){

                waveTickTimeOver=true;
            }

        }
    }

    /**
     * Zwiększenie numeru fali oraz zatrzymanie timera
     */
    public void increaseWaveIndex(){
        waveIndex++;
        waveTick=0;
        waveTickTimeOver=false;
        waveStartTimer=false;
    }


    /**
     * Pobranie kolejnego przeciwnika z listy
     */
    public int getNextEnemy() {
        enemySpawnTick = 0;
        return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
    }

    /**
     * Utworzenie fali i dodanie jej do listy
     */
    private void createWaves() {
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,3,3,3))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,1,1,1,0,0,1,2,2,2,2,2,2,2,2))));
        waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0,1,3,1,2,2,0,0,0,3,3,0,1,1,1,0,2,2,2))));
    }

    /**
     * Pobierz waves
     * @return waves
     */
    public ArrayList<Wave> getWaves() {
        return waves;
    }

    /**
     * Sprawdzenie czy powinien się pojawić nowy przeciwnik
     */
    public boolean isTimeForNewEnemy() {
        return enemySpawnTick >= enemySpawnTickLimit;
    }

    /**
     * Sprawdzenie czy w fali jest więcej przeciwników
     */
    public boolean isThereMoreEnemiesInWave() {
        return enemyIndex < waves.get(waveIndex).getEnemyList().size();
    }

    /**
     * Sprawdzenie czy jest więcej fal
     */
    public boolean isThereMoreWaves() { return waveIndex+1<waves.size();}

    /**
     * Rozpoczęcie timera
     */
    public void startWaveTimer() {
        waveStartTimer=true;
    }

    /**
     * Sprawdzenie czy timer się zakończył
     */
    public boolean isWaveTimerOver() {
        return waveTickTimeOver;
    }
    /**
     * Zresetowanie indeksów przeciwników
     */
    public void resetEnemyIndex() {
        enemyIndex=0;
    }

    /**
     * Pobierz waveIndex
     * @return waveIndex
     */
    public int getWaveIndex(){
        return waveIndex;
    }

    /**
     * Zwraca czas pozostały do nastepnej fali
     */
    public float getTimeLeft(){
        float tickLeft= waveTickLimit-waveTick;
        return tickLeft/60.0f;
    }

    /**
     * Sprawdzenie czy timer się zaczął
     */
    public boolean isWaveTimerStarted() {
        return waveStartTimer;
    }

    /**
     * Usunięcie fal,indeksów oraz timerów
     */
    public void reset() {
        waves.clear();
        createWaves();
        enemyIndex = 0;
        waveIndex = 0;
        waveStartTimer = false;
        waveTickTimeOver = false;
        waveTick = 0;
        enemySpawnTick = enemySpawnTickLimit;
    }
}
