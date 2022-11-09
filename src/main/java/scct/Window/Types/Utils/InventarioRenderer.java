package scct.Window.Types.Utils;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;

//Parziale reimplementazione del renderer JList per permettere immagini oltre a testo per ogni componente della lista
public class InventarioRenderer extends DefaultListCellRenderer {

    private final Inventario inv;

    public InventarioRenderer(Inventario inv){
        super();
        this.inv = inv;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> lista, Object valore, int index, boolean selected, boolean focus){
        JLabel jlabel = (JLabel) super.getListCellRendererComponent(lista, valore, index, selected, focus);
        int id = inv.getIdByItemName((String) valore);
        if(id != -1)
            jlabel.setIcon(new ImageIcon(inv.getItemIconPathById(id)));
        jlabel.setHorizontalTextPosition(JLabel.RIGHT);
        jlabel.setToolTipText(inv.getLoreByItemId(id)); //Tooltip degli oggetti in HTML
        return jlabel;
    }
}
