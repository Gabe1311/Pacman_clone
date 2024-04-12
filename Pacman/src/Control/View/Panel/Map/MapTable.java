package Control.View.Panel.Map;

import Control.Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static Utility.Constants.CellsConst.*;

public class MapTable extends AbstractTableModel {
    private Cell[][] cells;
    public MapTable (Map map){

        cells = map.getCells();

    }
    @Override
    public int getRowCount() {
        return cells.length;
    }

    @Override
    public int getColumnCount() {
        return cells[0].length;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cells[rowIndex][columnIndex].getCellImage();
    }
    @Override
    public Class<?> getColumnClass (int columnIndex){
        return BufferedImage.class;
    }
}
