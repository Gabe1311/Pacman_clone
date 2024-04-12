package Entities;

import Control.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class PowerUp extends Entity{
    private String[] filePaths;
    private int id;
    private BufferedImage fruitImage;
    private Game game;
    private Rectangle hitBox;
    private Thread powerUpThread;
    private Effect effect;

    public PowerUp(float posX, float posY, int id, Game game) {
        super(posX, posY);
        this.id=id;
        this.game = game;
        initFilepaths();
        hitBox = new Rectangle((int) posX-4,(int)posY-4,10,10);
        loadAnimations();
        powerUpThread = new Thread(new PowerUpChecks());
    }

    private void initFilepaths() {
        filePaths = new String[5];
        filePaths[0] ="/resources/Fruits/Apple.png";
        filePaths[1] = "/resources/Fruits/Cherries.png";
        filePaths[2] ="/resources/Fruits/Grapes.png";
        filePaths[3] = "/resources/Fruits/Pear.png";
        filePaths[4] = "/resources/Fruits/Watermelon.png";
    }
    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream(filePaths[id]);
        try {
            fruitImage = ImageIO.read(is);
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(Graphics g) {
        g.drawImage(fruitImage, (int) posX -1 , (int) posY -1,12,12, null);
    }
    private boolean checkIfEaten(){
        if(game.getPlayer().getHitBox().intersects(hitBox)||game.getPlayer().getHitBox().contains(hitBox))
           return true;
        return false;
    }


    public void start() {
        try {
            powerUpThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void wasEaten()throws Exception{
        effect = new Effect(game, id);
        powerUpThread.interrupt();
        game.removePowerUp(this);
    }
    private class PowerUpChecks implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    if (checkIfEaten()) {
                        System.out.println("was eaten");
                        wasEaten();
                        break;
                    }
                    powerUpThread.sleep(50);
                } catch (Exception e){
                    e.getSuppressed();
                }

            }
        }
    }
}
