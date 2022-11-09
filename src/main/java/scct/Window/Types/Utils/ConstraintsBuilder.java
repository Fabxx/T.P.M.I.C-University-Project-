package scct.Window.Types.Utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Classe di utilità. Costruisce constratints per i renderer Swing
 */
public final class ConstraintsBuilder {
    /**
     * Costruzione dei constraints principale con tutti i dati necessari al progetto.
     * 
     * <pre>
     * <b>FillTypes</b>
     * 0 (NONE) -> Non riepire i spazi vuoti
     * 1 (BOTH) -> Riempi tutti gli spazi vuoti
     * 2 (HORIZONTAL) -> Riempi gli spazi vuoti in orizzontale
     * 3 (VERTICAL) -> Riempi gli spazi vuoti in verticale
     * </pre>
     * 
     * @param x Posizione x in griglia
     * @param y Posizione y in griglia
     * @param width Largezza in celle
     * @param height Altezza in celle
     * @param fillType Modalità di espansione dello spazio vuoto da un oggetto più piccolo
     * @param ins Insets (bordi)
     * @param wx Peso sulla riga dell'oggetto (per gestione del resizing)
     * @param wy Peso sulla colonna dell'oggetto (per gestione del resizing)
     * @return I constraints raggruppati
     */
    public static GridBagConstraints make(int x, int y, int width, int height, int fillType, Insets ins, int wx, int wy){
        // --> https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/GridBagLayout.html
        GridBagConstraints current = new GridBagConstraints();
        current.gridx = x;
        current.gridy = y;
        current.gridwidth = width;
        current.gridheight = height;
        current.weightx = wx;
        current.weighty = wy;
        switch(fillType){
            case GridBagConstraints.BOTH:
            case GridBagConstraints.HORIZONTAL: 
            case GridBagConstraints.VERTICAL:
                current.fill = fillType;
                break;
            default:
                current.fill = GridBagConstraints.NONE;
        }
        if(ins != null)
            current.insets = ins;
        return current;
    }

    /**
     * Costruzione dei constraints con omissione degli <b>Insets</b>.
     * @see #make(int, int, int, int, int, Insets, int, int)
     * @param x Posizione x in griglia
     * @param y Posizione y in griglia
     * @param width Largezza in celle
     * @param height Altezza in celle
     * @param fillType Modalità di espansione dello spazio vuoto da un oggetto più piccolo
     * @param wx Peso sulla riga dell'oggetto (per gestione del resizing)
     * @param wy Peso sulla colonna dell'oggetto (per gestione del resizing)
     * @return I constraints raggruppati
     */
    public static GridBagConstraints make(int x, int y, int width, int height, int fillType, int wx, int wy){
        return make(x ,y ,width ,height ,fillType ,null, wx, wy);
    }

    /**
     * Costruzione dei constraints con omissione del peso.
     * @see #make(int, int, int, int, int, Insets, int, int)
     * @param x Posizione x in griglia
     * @param y Posizione y in griglia
     * @param width Largezza in celle
     * @param height Altezza in celle
     * @param fillType Modalità di espansione dello spazio vuoto da un oggetto più piccolo
     * @param ins Insets (bordi)
     * @return I constraints raggruppati
     */
    public static GridBagConstraints make(int x, int y, int width, int height, int fillType, Insets ins){
        return make(x ,y ,width ,height ,fillType ,ins , 0, 0);
    }

    /**
     * Costruzione dei constraints con omissione degli Insets e del peso.
     * @see #make(int, int, int, int, int, Insets, int, int)
     * @param x Posizione x in griglia
     * @param y Posizione y in griglia
     * @param width Largezza in celle
     * @param height Altezza in celle
     * @param fillType Modalità di espansione dello spazio vuoto da un oggetto più piccolo
     * @return I constraints raggruppati
     */
    public static GridBagConstraints make(int x, int y, int width, int height, int fillType){
        return make(x ,y ,width ,height ,fillType ,null, 0, 0);
    }
}
