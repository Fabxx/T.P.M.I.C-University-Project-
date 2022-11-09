package scct.Window.Types.Utils;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;

import java.awt.Component;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JList;

//Parziale reimplementazione del renderer JList per permettere immagini oltre a testo per ogni componente della lista
public class SalvataggiRenderer extends DefaultListCellRenderer {

    private final String[] thumbs;
    private final String[] friendlyName;

    public SalvataggiRenderer(String[] thumbs, String[] friendlyName){
        super();
        this.thumbs = thumbs;
        this.friendlyName = friendlyName;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> lista, Object valore, int index, boolean selected, boolean focus){
        JLabel jlabel = (JLabel) super.getListCellRendererComponent(lista, valore, index, selected, focus);
        int id = -1;
        for(int i = 0; i < friendlyName.length; i++){
            if(friendlyName[i].equals((String) valore)){
                id = i;
                i = friendlyName.length; //break
            }
        }
        if(id != -1)
            jlabel.setIcon(new ImageIcon(new ImageIcon(thumbs[id]).getImage().getScaledInstance(426, 240, Image.SCALE_DEFAULT)));
        jlabel.setHorizontalTextPosition(JLabel.RIGHT);
        return jlabel;
    }
}
