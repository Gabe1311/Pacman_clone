package Control.View.Panel.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImageCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof BufferedImage) {
            ImageIcon imageIcon = new ImageIcon((BufferedImage) value);
            setIcon(imageIcon);
            setText("");
        }

        return rendererComponent;
    }
}
