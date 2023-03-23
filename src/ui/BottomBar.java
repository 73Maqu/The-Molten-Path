package ui;

import Objects.Tower;
import helper.Constants;
import scenes.Playing;
import java.awt.*;
import java.text.DecimalFormat;

import static main.GameStates.*;
/**
 * Klasa odpowiedzialna za boczny panel
 */
public class BottomBar {
    /** Współrzędna x panelu*/
    private int x;
    /** Współrzędna y panelu */
    private int y;
    /** Szerokość panelu*/
    private int width;
    /** Wysokość panelu*/
    private int height;
    /** Zmienna typu Playing */
    private Playing playing;
    /** Przycisk Menu*/
    private MyButton bMenu;
    /** Przycisk sellTower*/
    private MyButton sellTower;
    /** Przycisk upgradeTower*/
    private MyButton upgradeTower;
    /** Przycisk Pause*/
    private MyButton bPause;
    /** Przyciski od wieżyczek*/
    private MyButton[] towerButtons;
    /** Zmienna typu Twoer przechowująca wybraną wieżyczkę*/
    private Tower selectedTower;
    /** Zmienna typu Tower przechowująca wyświetlaną wieżyczkę*/
    private Tower displayedTower;
    /** Zmienna typu DecimalFormat odpowiedzialna za format wyświetlania liczb zmiennoprzecinkowych */
    private DecimalFormat formatter;
    /** Zmienna przechowująca złoto gracza*/
    private int gold=100;
    /** Zmienna odpowiedzialna za pokazanie kosztu wieżyczki */
    private boolean showTowerCost;
    /** Zmienna przechowująca koszt wieżyczki */
    private int towerCostType;

    /** Zmienna przechowująca liczbę żyć gracza*/
    private int lives=10;

    /**Konstruktor - ustawienie parametrów obiektu, inicjalizacja przycisków oraz formatu wyświetlania liczb
     *
     * @param x współrzędna x panelu
     * @param y współrzędna y panelu
     * @param width szerokość panelu
     * @param height wysokość panelu
     * @param playing zmienna typu Playing
     */
    public BottomBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;
        formatter = new DecimalFormat("0.0");
        initButtons();
    }

    /**
     * Metoda odejmująca życie gracza oraz kończąca grę
     */
    public void removeOneLife() {
        lives--;
        if (lives <= 0) //jeżeli życie spadnie do 0 ->game over
            SetGameState(GAME_OVER);
    }

    /**
     * Metoda wyświetlająca wieżyczkę
     */
    public void displayTower(Tower t) {
        displayedTower = t;
    }

    /**
     * Metoda wyrysowująca panel oraz informacje na nim
     */
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        drawButtons(g);
        drawDisplayedTower(g);
        drawWaveInfo(g);
        drawGoldAmount(g);
        if (showTowerCost)
            drawTowerCost(g);
        if(playing.isGamePaused()) { //gra zapauzowana
            g.setColor(Color.red);
            g.drawString("Game is paused", 700, 75);
        }

        g.setColor(Color.red); //ilość żyć
        g.drawString("Lives: " + lives,650,350);
    }

    /**
     * Metoda wyrysowująca ilość złota
     */
    private void drawGoldAmount(Graphics g) { //ilość złota
        g.drawString("Gold: " +gold,650,385);
    }

    /**
     * Metoda wyrysowująca informację o fali
     */
    private void drawWaveInfo(Graphics g) {
        g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        g.setColor(Color.BLACK);
        drawWaveTimerInfo(g);
        drawEnemiesLeftInfo(g);
        drawWavesLeftInfo(g);
    }

    /**
     * Metoda wyrysowująca informację o koszcie wieżyczki
     */
    private void drawTowerCost(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(640,400,240,50);
          g.setColor(Color.black);
            g.drawRect(640,400,240,50);

        g.drawString("" + getTowerCostName(), 645,420);
        g.drawString("Cost: " + getTowerCostCost() + "g", 645,445);

        if (!isGoldEnoughForTower(towerCostType)) { //jeżeli gracza nie stać na wieżyczkę -> wypisz na czerwono
            g.setColor(Color.RED);
            g.drawString("Cost: " + getTowerCostCost() + "g", 645,445);

        }

    }

    /**
     * Metoda wyrysowująca informację o czasie pozostałym do następnej fali
     */
    private void drawWaveTimerInfo(Graphics g) {
        if(playing.getWaveManager().isWaveTimerStarted()){
            float timeLeft=playing.getWaveManager().getTimeLeft();
        String formatedText = formatter.format(timeLeft);
        g.drawString("Time left: " + formatedText, 695, 620);
        }
    }

    /**
     * Metoda wyrysowująca informację o pozostałych przeciwnikach
     */
    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
        g.drawString("Enemies Left: " + remaining, 740, 580);
    }

    /**
     * Metoda wyrysowująca informację o pozostałych falach
     */
    private void drawWavesLeftInfo(Graphics g) {
        int current = playing.getWaveManager().getWaveIndex();
        int size = playing.getWaveManager().getWaves().size();
        g.drawString("Wave: " + (current + 1) + " / " + size, 650, 580);

    }

    /**
     * Metoda wyrysowująca informację o wybranej wieżyczce
     */
    private void drawDisplayedTower(Graphics g) {
        if(displayedTower!=null){
            g.setColor(Color.gray);
            g.fillRect(640,400,240,50);
            g.setColor(Color.BLACK);
            g.drawRect(640,400,240,50);
            g.drawRect(640,400,50,50);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],640,400,50,50,null);
            g.setFont(new Font("LucidaSans",Font.BOLD,15));
            g.drawString(""+ Constants.Towers.GetName(displayedTower.getTowerType()),700,420);
            g.drawString("Tier "+ displayedTower.getTier(),760,420);
            drawDisplayedTowerBorder(g);
            drawDisplayedTowerRange(g);


            sellTower.draw(g);
            drawButtonFeedback(g, sellTower);
            if(displayedTower.getTier()<3 && gold >= getUpgradeAmount(displayedTower)){ //maksymalnie 3 poziomy ulepszenia
                upgradeTower.draw(g);
                drawButtonFeedback(g, upgradeTower);
            }


            if(sellTower.isMouseOver()){
                g.setColor(Color.red);
                g.drawString("Sell for: "+getSellAmount(displayedTower)+" g",650,475); //cena sprzedaży wieżyczki
            }else if(upgradeTower.isMouseOver() && gold >= getUpgradeAmount(displayedTower)){
                g.setColor(Color.blue);
                g.drawString("Upgrade for: "+getUpgradeAmount(displayedTower)+" g",650,475); //cena ulepszenia wieżyczki

            }
        }
    }

    /**
     * Pobierz cenę ulepszenia wieżyczki
     * @return cenę ulepszenia wieżyczki
     */
    private int getUpgradeAmount(Tower displayedTower) {
        return (int)(helper.Constants.Towers.GetTowerCost(displayedTower.getTowerType())*0.3f);
    }


    /**
     * Pobierz cenę sprzedaży wieżyczki
     * @return cenę sprzedaży  wieżyczki
     */

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (displayedTower.getTier()-1)*getUpgradeAmount(displayedTower);
        upgradeCost*=0.5f;
        return helper.Constants.Towers.GetTowerCost(displayedTower.getTowerType())/2+upgradeCost;
    }

    /**
     * Metoda pozwalająca sprzedać wieżyzkę po naciśnięciu przycisku "Sell"
     */
    private void sellTowerClicked() {
        playing.removeTower(displayedTower);
        gold+=helper.Constants.Towers.GetTowerCost(displayedTower.getTowerType())/2;
        int upgradeCost = (displayedTower.getTier()-1)*getUpgradeAmount(displayedTower);
        upgradeCost*=0.5f;
        gold+=upgradeCost;
        displayedTower = null;
    }

    /**
     * Metoda pozwalająca ulepszyć wieżyzkę po naciśnięciu przycisku "Wieżyczkę"
     */
    private void upgradeTowerClicked() {
        playing.upgradeTower(displayedTower);
        gold -= getUpgradeAmount(displayedTower);
    }


    /**
     * Metoda rysująca zasięg wieżyczki
     */
    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(displayedTower.getX()+16-(int)(displayedTower.getRange()*2)/2,
                displayedTower.getY()+16-(int)(displayedTower.getRange()*2)/2,(int)displayedTower.getRange()*2,(int)displayedTower.getRange()*2);
    }

    /**
     * Metoda rysująca "granice" wieżyczki
     */
    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(displayedTower.getX(),displayedTower.getY(),32,32);
    }

    /**
     * Metoda rysująca tworząca przyciski
     */
    private void initButtons() {
        bMenu = new MyButton("Menu", 650, 2, 70, 30);
        bPause = new MyButton("Pause", 800, 2, 70, 30);
        towerButtons= new MyButton[3];
        sellTower = new MyButton("Sell",700,425,70,20);
        upgradeTower = new MyButton("Upgrade",780,425,70,20);
        int w = 50;
        int h = 50;
        int xStart =675;
        int yStart=100;
        int xOffset= (int)(w*1.1f);

        for(int i=0;i<towerButtons.length;i++){
            towerButtons[i]=new MyButton("",xStart+xOffset*i,yStart,w,h,i);
        }
    }

    /**
     * Metoda odpowiedzialna za responsywność przycisków
     */
    private void drawButtonFeedback(Graphics g, MyButton b) {
        if (b.isMouseOver())
            g.setColor(Color.white);
        else
            g.setColor(Color.BLACK);
        g.drawRect(b.x, b.y, b.width, b.height);
        if (b.isMousePressed()) {
            g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
            g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
        }
    }

    /**
     * Metoda rysująca przyciski
     */
    private void drawButtons(Graphics g){
        for(MyButton b:towerButtons) {
            g.setColor(Color.GRAY);
            g.fillRect(b.x,b.y,b.width,b.height);
            g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            drawButtonFeedback(g, b);

        }
        bMenu.draw(g);
        bPause.draw(g);

    }

    /**
     * Dodaj obsługę zdarzeń - naciśnięcie przycisku myszki
     */
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if (bPause.getBounds().contains(x, y))
            bPause.setMousePressed(true);
        else {
            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMousePressed(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgradeTower.setMousePressed(true);
                    return;
                }
            }
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMousePressed(true);
                    return;
                }
        }

    }
    /**
     * Dodaj obsługę zdarzeń - poruszenie myszki
     */
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bPause.setMouseOver(false);
        showTowerCost = false;
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);

        for (MyButton b : towerButtons)
            b.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if (bPause.getBounds().contains(x, y))
            bPause.setMouseOver(true);
        else {

            if (displayedTower != null) {
                if (sellTower.getBounds().contains(x, y)) {
                    sellTower.setMouseOver(true);
                    return;
                } else if (upgradeTower.getBounds().contains(x, y) && displayedTower.getTier() < 3) {
                    upgradeTower.setMouseOver(true);
                    return;
                }
            }
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    showTowerCost = true;
                    towerCostType = b.getId();
                    return;
                }
        }
    }

    /**
     * Dodaj obsługę zdarzeń - kliknięcie przycisku myszki
     */
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            SetGameState(MENU);
        else if (bPause.getBounds().contains(x, y))
            togglePause();
        else {
            if(displayedTower!=null){
                if(sellTower.getBounds().contains(x,y)){
                    sellTowerClicked();
                }else if(upgradeTower.getBounds().contains(x,y)&& displayedTower.getTier() < 3 && gold >= getUpgradeAmount(displayedTower)){
                    upgradeTowerClicked();
                    return;
                }
            }

            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if (!isGoldEnoughForTower(b.getId()))
                        return;
                    selectedTower = new Tower(0, 0, -1, b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    /**
     * Dodaj obsługę zdarzeń - puszczenie przycisku myszki
     */
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bPause.resetBooleans();
        for (MyButton b : towerButtons)
            b.resetBooleans();
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();

    }

    /**
     * Metoda włączająca pauzę
     */
    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if (playing.isGamePaused())
            bPause.setText("Unpause");
        else
            bPause.setText("Pause");
    }

    /**
     * Metoda sprawdzająca czy gracz ma wystarczająco złota nie wieżyczkę
     */

    private boolean isGoldEnoughForTower(int towerType) {
            return gold >= helper.Constants.Towers.GetTowerCost(towerType);
        }

    /**
     * Metoda odejmująca złoto po zakupie wieży
     */
    public void payForTower(int towerType) {
        this.gold -= helper.Constants.Towers.GetTowerCost(towerType);
    }

    /**
     * Metoda dodające złoto
     */
    public void addGold(int getReward) {
        this.gold+=getReward;
    }

    /**
     * Pobierz nazwę wieżyczki
     * @return nazwę wieżyczki
     */
    private String getTowerCostName() {
        return helper.Constants.Towers.GetName(towerCostType);
    }

    /**
     * Pobierz koszt wieżyczki
     * @return koszt wieżyczki
     */
    private int getTowerCostCost() {
        return helper.Constants.Towers.GetTowerCost(towerCostType);
    }


    /**
     * Metoda resetująca zmienne
     */
    public void resetEverything() {
        lives = 10;
        towerCostType = 0;
        showTowerCost = false;
        gold = 100;
        selectedTower = null;
        displayedTower = null;
    }
}
