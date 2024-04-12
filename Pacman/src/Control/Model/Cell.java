package Control.Model;

import Control.Model.Pellet.Pellet;
import Control.Model.Pellet.PelletManager;
import Entities.PowerUp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utility.Constants.CellsConst.FREE;
import static Utility.Constants.CellsConst.WALL;

public class Cell {
    private int Type;
    private int x,y;
    private BufferedImage cellImage;
    private Pellet pellet;
    private PowerUp powerUp;
    private boolean containsSomething = false;
    public Cell(int Type,int x,int y){
        this.Type = Type;
        this.x=x;
        this.y = y;
        setCellImage();
    }
   public boolean getContainsSomething(){
        return containsSomething;
   }
   public Pellet getPellet(){
        return pellet;
   }
    public void setPellet(Pellet pellet){
        this.pellet = pellet;
    }
    public void setContainsSomething(boolean contains){
        containsSomething = contains;
    }
    public BufferedImage getCellImage() {
        return cellImage;
    }

    private void setCellImage() {
        InputStream is = getClass().getResourceAsStream("/resources/Map/Empty.png");
        InputStream is2 = getClass().getResourceAsStream("/resources/Map/Wall.png");
        try {
            if(Type == FREE) {
                cellImage = ImageIO.read(is);
            }else{
                cellImage = ImageIO.read(is2);
            }

            is.close();
        } catch (IOException e) {
            e.getSuppressed();
        }
    }

    public int getType(){
        return Type;
    }
    public void setType(int type){
        this.Type = type;
        setCellImage();
    }
}
