package scct.Window.Types;

import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import scct.App;
import scct.Window.ManagerFinestra;
import scct.Window.Types.Utils.ConstraintsBuilder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainMenu extends TipoFinestra{
    private final JButton[] buttons = new JButton[4];
    protected GridBagConstraints[] buttonConstraints;

    private final ManagerFinestra manager;
    private final Insets ins = new Insets(10, 10, 10, 10);

    public MainMenu(ManagerFinestra manager){
        panels = null;
        JPanel panel1 = new JPanel(new GridBagLayout());
        panel1.setOpaque(false);
        constraints = new GridBagConstraints();
        this.manager = manager;
        
        windowName = "Menu Principale";

        buttonConstraints = new GridBagConstraints[buttons.length];

        buttons[0] = new JButton("Nuova Partita");
        buttons[0].addActionListener(a -> button0Action());
        buttonConstraints[0] = ConstraintsBuilder.make(0, 0, 1, 1, 0, ins);
        
        buttons[1] = new JButton("Carica Partita");
        buttons[1].addActionListener(a -> button1Action());
        buttonConstraints[1] = ConstraintsBuilder.make(0, 1, 1, 1, 0, ins);

        buttons[2] = new JButton("Opzioni");
        buttons[2].addActionListener(a -> button2Action());
        buttonConstraints[2] = ConstraintsBuilder.make(0, 2, 1, 1, 0, ins);

        buttons[3] = new JButton("Esci");
        buttons[3].addActionListener(a -> button3Action());
        buttonConstraints[3] = ConstraintsBuilder.make(0, 3, 1, 1, 0, ins);

        for(int i = 0; i < buttons.length; i++){
            panel1.add(buttons[i], buttonConstraints[i]);
        }

        constraints = ConstraintsBuilder.make(0, 0, 1, 1, 0);
        wallpaper = "game/menu.jpg";
        JPanel wallj = new JPanel();
        wallj.setOpaque(false);
        wallj.add(panel1);
        panels = wallj;
        
        //Seleziono una delle 3 possibili file audio del menù iniziale
        int rand = new Random().nextInt(3); 
        music = manager.getAudioSystem();
        music.setNewAudioFile("game/menu" + rand + ".wav");
        if(App.OPTIONS.getMusicEnabledSetting()){
            music.enableLoop();
            music.play();
        }
    }

    @Override
    public JPanel getComponents(){
        return panels;
    }

    @Override
    public GridBagConstraints getConstraints(){
        return constraints;
    }

    private void button0Action(){ //Nuova Partita
        music.disableLoop();
        music.stop();
        music = null;
        System.gc();
        manager.changeWindowType(1, null);
    }

    private void button1Action(){ //Carica Partita
        music.disableLoop();
        music.stop();
        music = null;
        System.gc();
        manager.changeWindowType(2, null);
    }

    private void button2Action(){ //Opzioni
        music.disableLoop();
        music.stop();
        music = null;
        System.gc(); 
        manager.changeWindowType(3, null);
    }
    
    private void button3Action(){ //Esci
        music.stop();
        manager.killWindow();
    }

    @Override
    public String getWindowName(){
        return windowName;
    } 

    @Override
    public String getWallpaper(){
        return wallpaper;
    }
}
