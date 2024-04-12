package Control.View.Panel;

import Control.Game;
import Control.Input.KeyboardInputs;
import Control.Model.Map;
import Control.View.Panel.Map.BufferedImageCellRenderer;
import Control.View.Panel.Map.MapTable;

import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import static Utility.Constants.PlayerConsts.*;

public class GamePanel extends JPanel {

//    private BufferedImage playerImage;
//    private BufferedImage[][] playerAnimation;
//    private int animationTick =0,animationIndex,animationSpeed = 7;
//    private int playerAction = LEFT;
    private int tableWidth,tableHeight;
    private Game game;
    private Map map;
    private int windowHeight = 600;

    private BufferedImage tableImage;
    public GamePanel(Game game, MapTable mapTable){
        this.game = game;
        tableWidth = mapTable.getColumnCount()*12;
        tableHeight = mapTable.getRowCount()*12;
        JTable jTableMap = new JTable(mapTable);
        jTableMap.setDefaultRenderer(BufferedImage.class, new BufferedImageCellRenderer());
        jTableMap.getCellRect(12,12,true);
        tableImage = createMapTableImage(jTableMap);

        setAutoscrolls(true);

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
    }
    private BufferedImage createMapTableImage(JTable mapTable) {
        int rowCount = mapTable.getRowCount();
        int colCount = mapTable.getColumnCount();
        int cellWidth = 12;
        int cellHeight = 12;

        BufferedImage image = new BufferedImage(colCount * cellWidth, rowCount * cellHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                Object cellValue = mapTable.getValueAt(row, col);
                if (cellValue instanceof BufferedImage) {
                    BufferedImage cellImage = (BufferedImage) cellValue;
                    g.drawImage(cellImage, col * cellWidth, row * cellHeight, null);
                }
            }
        }
        return image;
    }
    private void setPanelSize() {
        Dimension maxSize = new Dimension(tableWidth,600);//tableHeight
        //Dimension size = new Dimension(1200,980);
        setMinimumSize(new Dimension(12*10,12*10));
        setPreferredSize(new Dimension(tableWidth,tableHeight));
        setMaximumSize(maxSize);
    }
    public String getPlayerState(){
        return game.getPlayer().getPowerUp();
    }
    public void setMap(Map map){
        this.map = map;
    }
    private void renderMap(Graphics g){
            g.drawImage(tableImage,0,0,tableImage.getWidth(),tableImage.getHeight(),null);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        renderMap(g);
        g.setColor(Color.white);
        g.drawString("Life X " + game.getPlayer().getLifeCounter(),10,10);
        g.drawString("State: "+ getPlayerState(),tableImage.getWidth() - 100,10);
        try {
            game.render(g);
        } catch (Exception e) {
            e.getSuppressed();
        }
    }
    public Game getGame(){
        return game;
    }
}
