package ui;

import java.awt.*;
/**
 * Klasa odpowiedzialna za przyciski
 */
public class MyButton {
    /** Współrzędna x przycisku*/
    public int x;
    /** Współrzędna y przycisku */
    public int y;
    /** Szerokość przycisku */
    public int width;
    /** Wysokość przycisku */
    public int height;
    /** Liczba porządkowa przycisku */
    public int id;
    /** Tekst na przycisku */
    private String text;
    /** Granice(obwód) przycisku */
    private Rectangle bounds;
    /** Zmienna przechowująca informację czy myszka jest na przycisku */
    private boolean mouseOver;
    /** Zmienna przechowująca informację czy przycisk został wciśnięty*/
    private boolean mousePressed;

    /**
     * Konstruktor - ustawienie parametrów obiektu, inicjalizacja granic
     * @param text tekst przycisku
     * @param x współrzędna x przycisku
     * @param y współrzędna y przycisku
     * @param width długość przycisku
     * @param height szerokość przycisku
     */
    public MyButton(String text, int x, int y, int width, int height) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;
        initBounds();
    }

    /**
     * Konstruktor - ustawienie parametrów obiektu, inicjalizacja granic
     * @param text tekst przycisku
     * @param x współrzędna x przycisku
     * @param y współrzędna y przycisku
     * @param width długość przycisku
     * @param height szerokość przycisku
     * @param id numer przycisku
     */
    public MyButton(String text, int x, int y, int width, int height, int id) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        initBounds();

    }

    /**
     * Metoda rysująca prostokąt
     */
    private void initBounds() {
        this.bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Metoda rysująca przyciski
     */
    public void draw(Graphics g) {
        drawBody(g);
        drawBorder(g);
        drawText(g);
    }

    /**
     * Metoda rysująca "obwód" przycisku oraz reagująca na naciśniecie przycisku
     */
    private void drawBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        if (mousePressed) {
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
        }
    }

    /**
     * Metoda rysująca body przycisku
     */
    private void drawBody(Graphics g) {
        if (mouseOver)
            g.setColor(Color.gray);
        else
            g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    /**
     * Metoda rysująca tekst przycisku
     */
    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);
    }

    /**
     * Metoda sprawdzająca najechanie myszką
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Metoda sprawdzająca naciśniecie myszki
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Pobierz obwód przycisku
     * @return obwód przycisku
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Metoda resetująca przyciski
     */
    public void resetBooleans() {
        this.mouseOver = false;
        this.mousePressed = false;
    }

    /**
     * Pobierz id przycisku
     * @return id przycisku
     */
    public int getId() {
        return id;
    }

    /**
     * Ustaw text przycisku
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Ustaw mousePressed przycisku
     * @param mousePressed
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Ustaw mouseOver przycisku
     * @param mouseOver
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
}
