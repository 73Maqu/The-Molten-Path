package managers;

import Objects.Projectile;
import Objects.Tower;
import enemies.Enemy;
import helper.Constants;
import helper.LoadSave;
import scenes.Playing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helper.Constants.Projectiles.*;
import static helper.Constants.Towers.*;
/**
 * Klasa odpowiedzialna za zarządzanie pociskami
 */
public class ProjectileManager {
    /** Zmienna klasy Playing */
    private Playing playing;
    /** Tablica pocisków */
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    /** Tablica aktualnych wybuchów */
    private ArrayList<Explosion> explosions = new ArrayList<>();
    /** Tablica przechowująca tekstury pocisków */
    private BufferedImage[] proj_imgs;
    /** Tablica przechowująca tekstury wybuchów */
    private BufferedImage[] expo_imgs;
    /** Numer porządkowy pocisku*/
    private int proj_id = 0;

    /**
     * Konstruktor - wczytanie tekstur
     */
    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();

    }
    /**
     * Tworzenie nowych pocisków oraz obracanie ich
     */
    public void newProjectile(Tower t, Enemy e){
        int type = getProjType(t);
        int xDist= (int)(t.getX()-e.getX());
        int yDist= (int)(t.getY()-e.getY());
        int totDist=Math.abs(xDist)+ Math.abs(yDist);

        float xPer=(float)Math.abs(xDist)/totDist;
        float xSpeed=xPer* Constants.Projectiles.GetSpeed(type);
        float ySpeed=Constants.Projectiles.GetSpeed(type)-xSpeed;

        if (t.getX() > e.getX())
            xSpeed *= -1;
        if (t.getY() > e.getY())
            ySpeed *= -1;
        float rotate=0;

        if(type==ARROW) {
            float arcValue = (float) Math.atan(yDist / (float) xDist);
            rotate = (float) Math.toDegrees(arcValue);
            if (xDist < 0)
                rotate += 180;
        }
            projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed,t.getDmg(), rotate, proj_id++, type));
    }

    /**
     * Importowanie tekstur pocisków
     */
    private void importImgs() {
        BufferedImage atlas= LoadSave.getSpriteAtlas();
        proj_imgs=new BufferedImage[3];
        for (int i = 0; i < 3; i++)
            proj_imgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
        importExplosion(atlas);
    }

    /**
     * Importowanie tekstur wybuchów
     */
    private void importExplosion(BufferedImage atlas) {
        expo_imgs=new BufferedImage[7];
        for(int i=0;i<7;i++){
            expo_imgs[i]=atlas.getSubimage(i*32,2*32,32,32);
        }
    }

    /**
     * Obracanie posisku tylko jeżeli to strzała
     */
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for (Projectile p : projectiles)
            if(p.isActive()) {
                if (p.getProjectileType() == ARROW) {
                    g2d.translate(p.getPos().x, p.getPos().y);
                    g2d.rotate(Math.toRadians(p.getRotation()));
                    g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null);
                    g2d.rotate(-Math.toRadians(p.getRotation()));
                    g2d.translate(-p.getPos().x, -p.getPos().y);
                } else {
                    g2d.drawImage(proj_imgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
                }
            }
        drawExplosions(g2d);
    }
    /**
     * Animacja wybuchów
     */
    private void drawExplosions(Graphics2D g2d) {
        for(Explosion e:explosions) {
            if(e.getIndex()<7) {
                g2d.drawImage(expo_imgs[e.getIndex()],(int)e.getPos().x-16,(int)e.getPos().y-16,null);
            }
        }
        }


    /**
     * Pobierz typ pocisku w zależności od wieżyczki
     * @return ARROW,BOMB,CHAINS
     */
    private int getProjType(Tower t) {
        switch (t.getTowerType()) {
            case ARCHER:
                return ARROW;
            case CANNON:
                return BOMB;
            case WIZARD:
                return CHAINS;
        }
        return 0;
    }

    /**
     * Aktualizowania pocisków i wybuchów
     */
    public void update() {
        for (Projectile p : projectiles)
            if(p.isActive()) {
                p.move();
                if(isProjectileHitting(p)){
                    p.setActive(false);
                    if(p.getProjectileType()==BOMB){
                        explosions.add(new Explosion(p.getPos()));
                        explodeOnEnemies(p);
                    }
                }else if(isProjectileOutsideBounds(p)) {//lag
                        p.setActive(false);
                    }
            }
        for(Explosion e:explosions)
            e.update();
    }
    /**
     * Usuwanie pocisku który wyleci poza granice ekranu
     */
    private boolean isProjectileOutsideBounds(Projectile p) {
        if (p.getPos().x >= 0)
            if (p.getPos().x <= 880)
                if (p.getPos().y >= 0)
                    if (p.getPos().y <= 640)
                        return false;
        return true;
    }

    /**
     * Obrażenia obszarowe od wybuchu
     */
    private void explodeOnEnemies(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if (e.isAlive()) {
                float radius = 40.0f;
                float xDist = Math.abs(p.getPos().x - e.getX());
                float yDist = Math.abs(p.getPos().y - e.getY());
                float realDist = (float) Math.hypot(xDist, yDist);
                if (realDist <= radius)
                    e.hurt(p.getDmg());
            }

        }
    }

    /**
     * Sprawdzenie czy pocisk trafia przeciwnika, jeżeli tak wywołanie odpowiedniego efektu
     */
    private boolean isProjectileHitting(Projectile p) {
        for (Enemy e : playing.getEnemyManager().getEnemies()) {
            if(e.isAlive())
                 if(e.getBounds().contains(p.getPos())){
                 e.hurt(p.getDmg());
                 if(p.getProjectileType()==CHAINS){
                     e.slow();
                 }
                 return true;
            }
        }
        return false;
    }

    /**
     * Klasa odpowiedzialna za wybuchami
     */
    public class Explosion {
        /** Zmienna przechowująca pozycję wybuchy*/
        private Point2D.Float pos;
        /** Zmienna przechowująca "klatki" wybuchy*/
        private int exploTick;
        /** Zmienna odpowiedzialna za zmianę klatek wybuchy*/
        private int exploIndex;

        /**
         * Konstruktor
         */
        public Explosion(Point2D.Float pos) {
            this.pos = pos;
        }

        /**
         * Aktualizowanie klatek wybuchu
         */
        public void update() {
            exploTick++;
            if (exploTick >= 6) {
                exploTick = 0;
                exploIndex++;
            }
        }

        /**
         * Pobierz Index
         * @return exploIndex
         */
        public int getIndex() {
            return exploIndex;
        }

        /**
         * Pobierz pozycję
         * @return pos
         */
        public Point2D.Float getPos() {
            return pos;
        }
    }

    /**
     * Zresetowanie pocisków i wybuchów
     */
    public void reset() {
        projectiles.clear();
        explosions.clear();
        proj_id = 0;
    }
}
