package Entities;

import Control.Game;
import Control.Model.Cell;
import Control.Model.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utility.Constants.CellsConst.*;
import static Utility.Constants.PlayerConsts.*;

public class Player extends Entity {
    private BufferedImage[][] playerAnimation;
    private static int speed = 1;
    private float speedMultiplier = 1.0f;
    private int animationTick = 0, animationIndex, animationSpeed = 7;
    private int playerAction = RIGHT;
    private boolean left,right,up,down;
    private Rectangle hitBox;
    private Cell[][] cells;
    private boolean playerIsAlive;
    private int lifeCounter = 3;
    private int spawnX,spawnY;
    private boolean isImmortal = false;
    private Game game;


    public Player(float posX, float posY, Map map,Game game) {
        super(posX, posY);
        this.game = game;
        spawnX = (int)posX;
        spawnY = (int)posY;
        left=right=up=down = false;
        hitBox = new Rectangle((int) (posX), (int)(posY), 5, 5 );
        cells = map.getCells();
        playerIsAlive = true;
        loadAnimations();
    }
    public String getPowerUp(){
        if(isImmortal)
            return "Immortality";
        if(speedMultiplier >1)
            return "Superspeed";
        return "Normal";
    }
    public void resetToSpawn(){
        posX = spawnX;
        posY = spawnY;
        updateHitBox();
        playerIsAlive = true;
        lifeCounter--;
    }
    public void resetPosition(){
        posX = spawnX;
        posY = spawnY;
        updateHitBox();
    }
    public void update() {
        updateAnimationTick();
    }
    public void render(Graphics g) {
        g.drawImage(playerAnimation[playerAction][animationIndex], (int) posX, (int) posY,10,10, null);
       // showHitBox(g);
    }
    public void showHitBox(Graphics g) {
        g.setColor(Color.red);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }
    public void setPlayerAction(int playerAction) {
        this.playerAction = playerAction;
    }

    public void setPlayerIsAlive(boolean playerIsAlive) {
        this.playerIsAlive = playerIsAlive;
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
        InputStream is = getClass().getResourceAsStream("/resources/PlayerSprites/Pacman/PacmanMovement.png");
        try {
            BufferedImage playerImage = ImageIO.read(is);
            is.close();
            playerAnimation = new BufferedImage[5][4];

            for (int i = 0; i < playerAnimation.length; i++) {
                for (int j = 0; j < playerAnimation[i].length; j++) {
                    playerAnimation[i][j] = playerImage.getSubimage(j * 32, i * 32, 32, 32);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateHitBox() {
        hitBox.x = (int) posX +3;
        hitBox.y = (int) posY+3;
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
    public void updatePlayerPosition(){
        if(!playerIsAlive)
            return;

        float xSpeed =0, ySpeed =0;
        if(left && !right){
            xSpeed-=speed*speedMultiplier;
        } else if (right && !left) {
            xSpeed+=speed*speedMultiplier;
        }
        if (up && !down) {
            ySpeed-=speed*speedMultiplier;
        } else if (down && !up) {
            ySpeed+=speed*speedMultiplier;
        }
        if(canMove(posX+xSpeed,posY+xSpeed)){
            posX+=xSpeed;
            posY+=ySpeed;
            updateHitBox();
            if(cells[(int)(posY/12)][(int)(posX/12)].getContainsSomething()){
                game.getPelletManager().removePellet(cells[(int)(posY/12)][(int)(posX/12)].getPellet());
                cells[(int)(posY/12)][(int)(posX/12)].setPellet(null);
                cells[(int)(posY/12)][(int)(posX/12)].setContainsSomething(false);
            }
        }
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public int getLifeCounter() {
        return lifeCounter;
    }

    public void addOneLifeCounter() {
        lifeCounter++;
        System.out.println("Green mushroom!!!");
    }

    public void setSpeedMultiplier(float i) {
        this.speedMultiplier = i;
        if(speedMultiplier >1){
            System.out.println("Extra Speeeeeeed");
        } else{
            System.out.println("Normal speeed..");
        }
    }
    public void setImmortal(boolean immortal){
        isImmortal = immortal;
        if(isImmortal){
            System.out.println("POWER PILL");
        }else{
            System.out.println("???");
        }
    }
    public boolean getIsImmortal(){
        return isImmortal;
    }
}
