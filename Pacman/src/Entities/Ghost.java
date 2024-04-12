package Entities;

import Control.Game;
import Control.Model.Cell;
import Control.Model.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utility.Constants.CellsConst.*;
import static Utility.Constants.GhostConsts.*;

public class Ghost extends Entity{
    private int id;
    private BufferedImage[][] ghostAnimation;
    private String[] animationPath;
    private static int speed = 12;
    private int animationTick = 0, animationIndex, animationSpeed = 7;
    private int ghostAction = RIGHT;
    private Rectangle hitBox;
    private Cell[][] cells;
    private Thread thread;
    private Thread spawnFruitThread;
    private Game game;
    private int spawnX,spawnY;
    public Ghost(float posX, float posY, int id, Map map, Game game) {
        super(posX, posY);
        spawnX = (int)posX;
        spawnY = (int)posY;
        this.id = id;
        initAnimationPath();
        this.game = game;
        hitBox = new Rectangle((int) (posX+2), (int)(posY+2), 8, 8);
        cells = map.getCells();
        loadAnimations();
        thread = new Thread(new GhostMovement());
        spawnFruitThread = new Thread(new FruitDrops());
    }
    public void startThread(){
        thread.start();
        spawnFruitThread.start();
    }

    private void initAnimationPath() {
        animationPath = new String[4];
        animationPath[0] = "/resources/Ghosts/CyanGhostMovement.png";
        animationPath[1]= "/resources/Ghosts/GreenGhostMovement.png";
        animationPath[2] = "/resources/Ghosts/OrangeGhostMovement.png";
        animationPath[3]= "/resources/Ghosts/RedGhostMovement.png";
    }

    public void update() {
        updateAnimationTick();
    }

    public void render(Graphics g) {
        g.drawImage(ghostAnimation[ghostAction][animationIndex], (int) posX, (int) posY,9,9, null);
        //showHitBox(g);
    }

    public void showHitBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }

    public void setGhostAction(int ghostAction) {
        this.ghostAction = ghostAction;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= 4) {
                animationIndex = 0;
            }
        }
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream(animationPath[id]);
        try {
            BufferedImage ghostImage = ImageIO.read(is);
            is.close();
            ghostAnimation = new BufferedImage[5][5];

            for (int i = 0; i < ghostAnimation.length; i++) {
                for (int j = 0; j < ghostAnimation[i].length; j++) {
                    ghostAnimation[i][j] = ghostImage.getSubimage(j * 32, i * 32, 32, 32);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateHitBox() {
        hitBox.x = (int) posX + 2;
        hitBox.y = (int) posY + 2;
    }

    private boolean canMove(float posX, float posY) {
        if(!isItSolid(posX,posY)){
            if(!isItSolid(posX+ hitBox.width, posY + hitBox.height)){
                if(!isItSolid(posX+ hitBox.width,posY)){
                    if (!isItSolid(posX, posY+ hitBox.height)){
                        return true;
                    }

                }
            }
        }
        return false;
    }
    private boolean isItSolid(float posX, float posY) {
        float xCellIndex = posX/12;
        float yCellIndex = posY/12;
        int cellType = cells[(int)yCellIndex][(int)xCellIndex].getType();
        if(cellType == WALL)
            return true;
        return false;
    }

    public void resetToSpawn() {
        posX = spawnX;
        posY = spawnY;
        updateHitBox();
    }
    private class FruitDrops implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    int rnd = (int)(Math.random()*3);
                    if(rnd == 0)
                        game.createNewFruit(posX,posY);
                    spawnFruitThread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private class GhostMovement implements Runnable{

        @Override
        public void run() {
            while(true) {
                try {
                    int playerX = game.getPlayer().getHitBox().x/12;
                    int playerY = game.getPlayer().getHitBox().y/12;
                    int ghostX = (int)posX/12;
                    int ghostY = (int)posY/12;
                    unaliveThePlayer();
                    if (playerY < ghostY) {
                        if (canMove(posX, posY - speed)) {
                            ghostAction = UP;
                            for (int i = 0; i < speed; i++) {
                                posY--;
                                updateHitBox();
                                update();
                                thread.sleep(17);
                            }
                        }
                    }
                    if (playerY > ghostY) {
                        if (canMove(posX, posY + speed)) {
                            ghostAction = DOWN;
                            for (int i = 0; i < speed; i++) {
                                posY++;
                                updateHitBox();
                                update();
                                thread.sleep(17);
                            }
                        }
                    }
                   if (playerX < ghostX) {
                            if (canMove(posX - speed, posY)) {
                                ghostAction = LEFT;
                                for (int i = 0; i < speed; i++) {
                                    posX--;
                                    updateHitBox();
                                    update();
                                    thread.sleep(17);
                                }
                            }
                        }
                   if (playerX > ghostX) {
                            if (canMove(posX + speed, posY)) {
                                ghostAction = RIGHT;
                                for (int i = 0; i < speed; i++) {
                                    posX++;
                                    updateHitBox();
                                    update();
                                    thread.sleep(17);
                                }
                            }
                        }

                        thread.sleep(30);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        private void unaliveThePlayer() throws InterruptedException {
            if (!game.getPlayer().getIsImmortal()){
                if (hitBox.intersects(game.getPlayer().getHitBox()) || hitBox.intersects(game.getPlayer().getHitBox())) {
                    game.getPlayer().setPlayerIsAlive(false);
                    game.getPlayer().setPlayerAction(DEATH);
                    thread.sleep(1000);
                    if (game.getPlayer().getLifeCounter() > 0)
                        game.resetPosition();
                }
          }
        }
    }
}
