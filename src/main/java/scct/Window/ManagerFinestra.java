package scct.Window;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import scct.Window.Types.CaricaPartita;
import scct.Window.Types.MainMenu;
import scct.Window.Types.Opzioni;
import scct.Window.Types.Partita;
import scct.Window.Types.TipoFinestra;
import scct.Window.Types.Utils.Suono;

/**
 * Wrapper per Finestra, offrendo una interfaccia più pulita
 */
public class ManagerFinestra {

    private final Finestra window;
    private TipoFinestra desc = null;

    public ManagerFinestra() {
        window = new Finestra();
    }

    public void changeWindowType(int type, Float extData) {
        window.clearWindow();
        if (type >= 0 && type <= 3) {
            switch (type) {
                case 0: {
                    desc = new MainMenu(this);
                    break;
                }
                case 1: {
                    desc = new Partita(this, extData);
                    break;
                }
                case 2: {
                    desc = new CaricaPartita(this, extData);
                    break;
                }
                case 3: {
                    desc = new Opzioni(this);
                    break;
                }
            }
            JPanel components = desc.getComponents();
            GridBagConstraints constrs = desc.getConstraints();
            window.setTitle(desc.getWindowName());
            if (desc.getWallpaper() != null) {
                window.setWallpaper(desc.getWallpaper());
            }
            window.addComponent(components, constrs);
            window.redraw();
        }
    }

    public int getWindowWidth() {
        return window.getWindowWidth();
    }

    public int getWindowHeight() {
        return window.getWindowHeight();
    }

    public void redraw() {
        window.redraw();
    }

    public void toggleShow() {
        window.toggleShow();
    }

    public void killWindow() {
        window.kill();
    }

    public Suono getAudioSystem() {
        return window.getAudio();
    }
}
