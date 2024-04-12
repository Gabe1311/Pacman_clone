import Control.Game;
import Control.GameMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new GameMenu());
    }
}
