package scct.Window;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import scct.Window.Types.Utils.Suono;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class Finestra {
    private final JFrame window;
    private GridBagLayout layout = new GridBagLayout();
    private final JLabel background;
    private final Suono audio;
    
    //Inizializzazione finestra base
    Finestra(){
        audio = new Suono();
        background = new JLabel();
        background.setVerticalAlignment(SwingConstants.CENTER);
        background.setHorizontalAlignment(SwingConstants.CENTER);
        window = new JFrame();
        window.setContentPane(background);
        window.setSize(1280, 768);
        window.setLayout(layout);
        window.setVisible(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(window.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }

    /**
     * Imposta il titolo della finestra
     * @param name Il titolo da impostare
     */
    protected void setTitle(String name){
        window.setTitle(name);
    }

    /**
     * Aggiungi un componente alla finestra utilizzando dei parametri precostruiti.
     * 
     * @param component Il JComponent da inserire
     * @param constraints I suoi parametri di posizione
     */
    protected void addComponent(JComponent component, GridBagConstraints constraints){
        window.add(component, constraints);
    }

    /**
     * Rimuovi tutti i componenti che fanno parte della finestra
     */
    protected void clearWindow(){
        window.getContentPane().removeAll();
        this.rebuildLayout();
        window.validate();
    }

    /**
     * Ridisegna i componenti che fanno parte della finestra
     */
    protected void redraw(){
        window.validate();
        window.repaint();
    }

    /**
     * Cambia tra il nascondere la finestra o mostrarla.
     */
    protected void toggleShow(){
        window.setVisible(!window.isVisible());
    }

    /**
     * Ricostruisce il layout attuale azzerandolo in uno stato vuoto
     */
    private void rebuildLayout(){
        layout = new GridBagLayout();
        window.setLayout(layout);
    }

    /**
     * Chiudi la finestra Swing ed esce dal programma
     */
    void kill(){
        window.dispose();
        System.exit(0);
    }

    /**
     * Imposta uno sfondo alla finestra Swing
     * @param name Path al file immagine
     */
    protected void setWallpaper(String name){
        background.setIcon(new ImageIcon(name));
    }    

    /**
     * Ottieni il JLabel contenete lo sfondo
     * @return Il JLabel di sfondo
     */
    protected JLabel getWallpaper(){
        return background;
    }

    /**
     * Ottieni la attuale larghezza della finestra Swing
     * @return Come intero la larghezza della finestra
     */
    protected int getWindowWidth(){
        return window.getWidth();
    }

    /**
     * Ottieni la attuale altezza della finestra Swing
     * @return Come intero la altezza della finestra
     */
    protected int getWindowHeight(){
        return window.getHeight();
    }

    protected Suono getAudio(){
        return audio;
    }
}
