package Control.View;

import Control.View.Panel.GamePanel;

import javax.swing.*;

public class GameWindow{
    private JFrame jFrame;
    private GamePanel gamePanel;
    public GameWindow(GamePanel gamePanel){
        jFrame = new JFrame();
        this.gamePanel = gamePanel;

        //Starts JFrame
        jFrame.add(gamePanel);
        jFrame.pack();

        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public void closeWindow() {
        jFrame.dispose();
    }
}
