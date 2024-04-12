package Control;

import Control.Model.HighscoreSave;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class HighscoreWindow extends JFrame {
    private JPanel jPanel;
    private MyJList jList;
    public HighscoreWindow(){
        jPanel = new JPanel();
        jList = new MyJList();
        jPanel.add(jList);
        add(jPanel);
        setLocationRelativeTo(null);
        //setSize(new Dimension(250,500));
        pack();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public HighscoreWindow(HighscoreSave highscoreSave){
        jList = new MyJList();
        jList.writeFile(highscoreSave);
        jPanel = new JPanel();
        jPanel.add(jList);
        add(jPanel);
        setLocationRelativeTo(null);
        pack();
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

    }
    private class MyJList extends JList<String>{
        private static String filePath = "src/resources/Highscores/highscore.txt";
        private ArrayList<HighscoreSave> savedHighscores;
        private MyJList(){
            savedHighscores = new ArrayList<>();
            DefaultListModel<String> listModel = new DefaultListModel<>();
            readFile();
            for (HighscoreSave highscore : savedHighscores) {
                listModel.addElement(highscore.getUsername() + ": " + highscore.getHighscore());
            }
            setModel(listModel);
        }
        private void readFile() {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                while (true) {
                    try {
                        HighscoreSave temp = (HighscoreSave) in.readObject();
                        savedHighscores.add(temp);
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        public void writeFile(HighscoreSave highscore) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
                for (HighscoreSave existingHigh : savedHighscores) {
                    out.writeObject(existingHigh);
                }
                out.writeObject(highscore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
