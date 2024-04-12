package Control.Input;

import Control.View.Panel.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static Utility.Constants.PlayerConsts.*;
public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setPlayerAction(UP);
                gamePanel.getGame().getPlayer().setUp(true);
//                gamePanel.getGame().getPlayer().updatePlayerPosition();
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setPlayerAction(LEFT);
                gamePanel.getGame().getPlayer().setLeft(true);
//                gamePanel.getGame().getPlayer().updatePlayerPosition();
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setPlayerAction(DOWN);
                gamePanel.getGame().getPlayer().setDown(true);
//                gamePanel.getGame().getPlayer().updatePlayerPosition();
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setPlayerAction(RIGHT);
                gamePanel.getGame().getPlayer().setRight(true);
//                gamePanel.getGame().getPlayer().updatePlayerPosition();
                break;
            case KeyEvent.VK_0:
                gamePanel.getGame().calledResetToSpawn();
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGame().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGame().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGame().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGame().getPlayer().setRight(false);
                break;
        }
    }
}
