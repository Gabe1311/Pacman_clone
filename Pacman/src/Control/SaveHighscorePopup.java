package Control;

import Control.Model.HighscoreSave;

import javax.swing.*;

public class SaveHighscorePopup {

    private JFrame savingWindow;
    private JPanel saveMenu;
    private HighscoreSave highscoreSave;
    private String username;
    private long highscore;
    private JTextField insertUsername = new JTextField(10);
    public SaveHighscorePopup(long highscore){
        this.highscore = highscore;
        saveMenu = new JPanel();
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        giveALifePurposeToButtons(yes,no);
        saveMenu.add(yes);
        saveMenu.add(no);
        savingWindow = new JFrame();
        savingWindow.setName("Save score?");
        savingWindow.add(saveMenu);
        savingWindow.setSize(200,200);
        savingWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        savingWindow.setLocationRelativeTo(null);
        savingWindow.setVisible(true);

    }

    private void giveALifePurposeToButtons(JButton yes, JButton no) {
        yes.addActionListener(e->{
            saveMenu.add(insertUsername);
            saveMenu.revalidate();

            JButton submit = new JButton("Submit");
            submit.addActionListener(e2->{
                username = insertUsername.getText();
                highscoreSave = new HighscoreSave(highscore,username);
                SwingUtilities.invokeLater(()->new HighscoreWindow(highscoreSave));
                System.out.println(username+ "!!!!!");
                savingWindow.dispose();
            });
            saveMenu.add(submit);
        });
        no.addActionListener(e3-> savingWindow.dispose());
    }
}
