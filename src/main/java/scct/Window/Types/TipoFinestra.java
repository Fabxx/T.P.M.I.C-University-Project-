package scct.Window.Types;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import scct.Window.Types.Utils.Suono;

/**
 * Classe generica con elementi comuni per tutte le finestre di gioco
 */
public abstract class TipoFinestra {
    protected JPanel panels;
    protected GridBagConstraints constraints;
    protected Suono music;
    protected String windowName;
    protected String wallpaper;

    abstract public JPanel getComponents();
    abstract public GridBagConstraints getConstraints();
    abstract public String getWindowName();
    abstract public String getWallpaper();
}
