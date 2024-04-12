package Control.Model.Pellet;

import Control.Game;
import Control.Model.Cell;

import java.awt.*;
import java.util.ArrayList;

import static Utility.Constants.CellsConst.FREE;

public class PelletManager {
    private ArrayList<Pellet> pellets;
    private Cell[][] cells;
    private Game game;
    public PelletManager(Cell[][] cells, Game game){
        pellets = new ArrayList<>();
        this.cells = cells;
        this.game = game;
        generatePellets();
    }
    public void renderPellets(Graphics g){
        try {
            for (Pellet pellet : pellets) {
                pellet.render(g);
            }
        }catch (Exception e){
            e.getSuppressed();
        }
    }
    private void generatePellets() {
        for(int y = 0; y < cells.length;y++){
            for(int x = 0; x < cells[0].length;x++){
                if(cells[y][x].getType() == FREE){
                    int rnd = (int)(Math.random()*6+1);
                    if(rnd == 1) {
                        Pellet pellet = new Pellet(x, y, game.getPlayer(), this);
                        cells[y][x].setPellet(pellet);
                        cells[y][x].setContainsSomething(true);
                        pellets.add(pellet);
                    }
                }
            }
        }
    }

    public ArrayList<Pellet> getPellets() {
        return pellets;
    }

    public void removePellet(Pellet pellet) {
        pellets.remove(pellet);
    }

    public int getTotalPellets() {
        return pellets.size();
    }
}
