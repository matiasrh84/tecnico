/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.UIManager;

/**
 *
 * @author MATIAS
 */
public class TableHeder extends  DefaultTableHeaderCellRenderer {
    Color background;
    Color foreground;

  /**
   * Constructs a <code>VerticalTableHeaderCellRenderer</code>.
   * <P>
   * The horizontal and vertical alignments and text positions are set as
   * appropriate to a vertical table header cell.
   */
  public TableHeder(Color background, Color foreground) {
    super();
    this.background = background;
    this.foreground = foreground;
    setHorizontalAlignment(CENTER);
    setHorizontalTextPosition(CENTER);
    setVerticalAlignment(CENTER);
    setVerticalTextPosition(CENTER);
    
  }

    public TableHeder() {
        
    setHorizontalAlignment(CENTER);
    setHorizontalTextPosition(CENTER);
    setVerticalAlignment(CENTER);
    setVerticalTextPosition(CENTER);
    
    }
  

  /**
   * Overridden to return a rotated version of the sort icon.
   *
   * @param table the <code>JTable</code>.
   * @param column the colummn index.
   * @return the sort icon, or null if the column is unsorted.
   */
  @Override
  protected Icon getIcon(JTable table, int column) {
    RowSorter.SortKey sortKey = getSortKey(table, column);
    if (sortKey != null && table.convertColumnIndexToView(sortKey.getColumn()) == column) {
      SortOrder sortOrder = sortKey.getSortOrder();
      switch (sortOrder) {
        case ASCENDING:
          return VerticalSortIcon.ASCENDING;
        case DESCENDING:
          return VerticalSortIcon.DESCENDING;
      }
    }
    return null;
  }

  /**
   * An icon implementation to paint the contained icon rotated 90Â° clockwise.
   * <P>
   * This implementation assumes that the L&F provides ascending and
   * descending sort icons of identical size.
   */
  private enum VerticalSortIcon implements Icon {

    ASCENDING(UIManager.getIcon("Table.ascendingSortIcon")),
    DESCENDING(UIManager.getIcon("Table.descendingSortIcon"));
    private final Icon icon;// = ;

    private VerticalSortIcon(Icon icon) {
      this.icon = icon;
    }

    /**
     * Paints an icon suitable for the header of a sorted table column,
     * rotated by 90Â° clockwise.  This rotation is applied to compensate
     * the rotation already applied to the passed in Graphics reference
     * by the VerticalLabelUI.
     * <P>
     * The icon is retrieved from the UIManager to obtain an icon
     * appropriate to the L&F.
     *
     * @param c the component to which the icon is to be rendered
     * @param g the graphics context
     * @param x the X coordinate of the icon's top-left corner
     * @param y the Y coordinate of the icon's top-left corner
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
      int maxSide = Math.max(getIconWidth(), getIconHeight());
      Graphics2D g2 = (Graphics2D) g.create(x, y, maxSide, maxSide);
      g2.rotate((Math.PI / 2));
      g2.translate(0, -maxSide);
      icon.paintIcon(c, g2, 0, 0);
      g2.dispose();
    }

    /**
     * Returns the width of the rotated icon.
     *
     * @return the <B>height</B> of the contained icon
     */
    @Override
    public int getIconWidth() {
      return icon.getIconHeight();
    }

    /**
     * Returns the height of the rotated icon.
     *
     * @return the <B>width</B> of the contained icon
     */
    @Override
    public int getIconHeight() {
      return icon.getIconWidth();
    }
  }
  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
comp.setBackground(background);
comp.setForeground(foreground);
      
comp.setFont(new java.awt.Font("Arial", 1, 12));

return comp;
}
}