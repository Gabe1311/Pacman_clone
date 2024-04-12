package Control.Model.Pellet;

import Entities.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Pellet{
    private PelletManager pelletManager;
    private float posX,posY;
    private Player player;
    private BufferedImage pelletImage;
    private Rectangle hitBox;
    public Pellet(int cellX,int cellY,Player player,PelletManager pelletManager){
        this.player = player;
        this.posX = cellX*12;
        this.posY = cellY*12;
        this.pelletManager = pelletManager;
        loadAnimations();
        hitBox = new Rectangle((int) posX,(int)posY,10,10);
    }
    public void render(Graphics g) {
        g.drawImage(pelletImage, (int) posX -1 , (int) posY -1,13,13, null);
    }
    public boolean checkIfEaten(){
        if(player.getHitBox().intersects(hitBox)||player.getHitBox().contains(hitBox))
            return true;
        return false;
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/resources/Map/Pellets/Pill.png");
        try {
            pelletImage = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
