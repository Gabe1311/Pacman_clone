package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class GameMenu {
    private JFrame gameMenuFrame;
    private JPanel gameMenuPanel;
    public GameMenu(){
        gameMenuFrame = new JFrame();
        gameMenuFrame.setLocationRelativeTo(null);
        gameMenuPanel = new JPanel();//new BoxLayout(gameMenuPanel,BoxLayout.Y_AXIS)

        JButton newGame = new JButton("NEW GAME");
        JButton highScores = new JButton("HIGHSCORES");
        JButton exit = new JButton("EXIT");
        setUpButton(newGame,exit,highScores);
        gameMenuFrame.setSize(new Dimension(500,250));
        gameMenuPanel.add(newGame);
        gameMenuPanel.add(highScores);
        gameMenuPanel.add(exit);

        gameMenuFrame.add(gameMenuPanel);
        gameMenuFrame.setResizable(false);
        gameMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameMenuFrame.setVisible(true);
    }
    private void setUpButton(JButton newGame,JButton exit ,JButton highScore) {
        newGame.addActionListener(e -> SwingUtilities.invokeLater(()->{
            newGame.setText("Loading...");
            new Game(this);
        }));
        exit.addActionListener(e ->gameMenuFrame.dispose());
        highScore.addActionListener(e-> new HighscoreWindow());
    }
}
