package helper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
/**
 * Klasa odpowiedzialna za wczytywanie tekstur
 */
public class LoadSave {
    /**
     * Pobierz obraz z zasobów
     * @return obraz z zasobów
     */
    public static BufferedImage getSpriteAtlas(){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("res/spriteatlas.png");
        try {
            img = ImageIO.read(is);
        }catch(IOException e){ //brak zasobu
            e.printStackTrace();
        }
        return img;
    }
}
