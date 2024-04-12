package Control;

import Control.Model.Map;
import Control.Model.Pellet.PelletManager;
import Control.View.GameWindow;
import Control.View.Panel.GamePanel;
import Control.View.Panel.Map.MapTable;
import Entities.Ghost;
import Entities.Player;
import Entities.PowerUp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Map map;
    private Player player;
    private Ghost[] ghosts;
    private final int FPS = 60;
    private Thread gameLoopThread;
    private MapTable mapTable;
    private PelletManager pelletManager;
    private GameMenu gameMenu;
    private ArrayList<PowerUp> powerUps;
    public Game( GameMenu gameMenu){
        this.gameMenu = gameMenu;
        this.map = new Map();
        powerUps = new ArrayList<>();
        player = new Player(13,map.getCells().length*12/2, map,this);
        pelletManager = new PelletManager(map.getCells(), this);
        initGhosts();

        mapTable = new MapTable(map);

        gamePanel = new GamePanel(this,mapTable);
        gamePanel.setMap(map);
        gameWindow = new GameWindow(gamePanel);



        gamePanel.requestFocus();


        startGameLoop();
    }

    private void initGhosts() {
        ghosts = new Ghost[4];
        ghosts[0] = new Ghost(14,14,0,map,this);
        ghosts[1] = new Ghost(14,map.getCells().length*12 -14,1,map,this);
        ghosts[2] = new Ghost(map.getCells()[0].length*12 - 24,map.getCells().length*12 -24,2,map,this);
        ghosts[3] = new Ghost(map.getCells()[0].length*12 - 14,14,3,map,this);
    }

    private void startGameLoop(){
        gameLoopThread = new Thread(this);
        gameLoopThread.start();
        for(int i =0; i < ghosts.length; i++) {
            ghosts[i].startThread();
        }
    }
    public PelletManager getPelletManager(){
        return pelletManager;
    }

    public void update(){
        player.update();
        player.updatePlayerPosition();
//        if(pelletManager.getTotalPellets() ==0){
//        }
//        if(player.getLifeCounter() == 1){
//        }
    }

    public void render(Graphics g) throws Exception {
        pelletManager.renderPellets(g);
        renderPowerUps(g);
        renderGhosts(g);

        player.render(g);
    }

    private void renderPowerUps(Graphics g) throws Exception {
        for(PowerUp powerUp: powerUps){
            powerUp.render(g);
        }
    }

    private void renderGhosts(Graphics g){
        for(int i = 0; i< ghosts.length;i++) {
            ghosts[i].render(g);
        }
    }

    //Game loop
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        long lastFrame = System.nanoTime();
        long now;
        int frames = 0;
        Thread thread = new Thread();
        thread.start();
        //this is the only occasion I didn't use the thread to time everything,
        // its just to stabilize the frame rate so the game will be stable
        long lastCheck= System.currentTimeMillis();
        int counter =0;
        int time = 0;
        int numberOfTotalPellets = pelletManager.getTotalPellets();
        int numberOfPelletsNow = 0;
        while(true) {
            try {
                counter++;
                if(counter >=60)
                    time++;
                numberOfPelletsNow = pelletManager.getTotalPellets();
                update();
                gamePanel.repaint();
                if(player.getLifeCounter()<=0||pelletManager.getTotalPellets()<=0) {
                    long score = getScore(numberOfTotalPellets- numberOfPelletsNow, time);
                    int pelletsEaten = numberOfTotalPellets- numberOfPelletsNow;
                    gameWindow.closeWindow();
                    SwingUtilities.invokeLater(()->new SaveHighscorePopup(score));
                    break;
                }
                thread.sleep(1000/60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private long getScore(int i, int time) {
        long score = i*100 + time + player.getLifeCounter()*300;
        return score;
    }

    public Player getPlayer(){
        return player;
    }
    public void calledResetToSpawn(){
        for(int i = 0; i< ghosts.length;i++){
            ghosts[i].resetToSpawn();
        }
        player.resetPosition();
    }
    public void resetPosition() {
        for(int i = 0; i< ghosts.length;i++){
            ghosts[i].resetToSpawn();
        }
        player.resetToSpawn();
    }

    public void createNewFruit(float posX, float posY) {
        int n =(int)(Math.random()*5+0);
        if(n >4)
            n--;
        PowerUp temp = new PowerUp(posX,posY,n,this);

        temp.start();
        powerUps.add(temp);
    }

    public void removePowerUp(PowerUp powerUp) {
        powerUps.remove(powerUp);
    }
}
