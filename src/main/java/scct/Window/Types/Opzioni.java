package scct.Window.Types;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import scct.App;
import scct.App.OPTIONS;
import scct.Window.ManagerFinestra;
import scct.Window.Types.Utils.ConstraintsBuilder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Opzioni extends TipoFinestra{
    private final JButton exit;
    private final JCheckBox checkboxe;
    private final JSlider audioSlider;
    private final JLabel testo1;
    protected GridBagConstraints buttonConstraints;
    protected GridBagConstraints checkboxConstraints;
    protected GridBagConstraints sliderConstraints;
    protected GridBagConstraints testo1Constraints;
    private final ManagerFinestra manager;
    private final Insets ins = new Insets(10, 10, 10, 10);

    public Opzioni(ManagerFinestra manager){
        panels = null;
        JPanel panel1 = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        this.manager = manager;
        
        windowName = "Opzioni";

        buttonConstraints = new GridBagConstraints();
        checkboxConstraints = new GridBagConstraints();

        exit = new JButton("Indietro");
        exit.addActionListener(a -> button0Action());
        buttonConstraints = ConstraintsBuilder.make(0, 3, 1, 1, 0, ins);

        audioSlider = new JSlider(JSlider.HORIZONTAL);
        audioSlider.setMaximum(0);
        audioSlider.setMinimum(-40);
        audioSlider.setValue(App.OPTIONS.getMusicGainSetting());
        audioSlider.setPaintTicks(true);  
        audioSlider.setPaintLabels(true);  
        audioSlider.setMinorTickSpacing(2);  
        audioSlider.setMajorTickSpacing(10);  
        audioSlider.addChangeListener(a -> audioSliderAction());
        sliderConstraints = ConstraintsBuilder.make(0, 2, 1, 1, 0, ins);

        testo1 = new JLabel();
        testo1.setText("Riduzione volume ("+App.OPTIONS.getMusicGainSetting()+" dB)");
        testo1Constraints = ConstraintsBuilder.make(0, 1, 1, 1, 0, ins);

        checkboxe = new JCheckBox("Suoni abilitati", App.OPTIONS.getMusicEnabledSetting());
        checkboxe.addActionListener(a -> checkbox0Action());
        checkboxConstraints = ConstraintsBuilder.make(0, 0, 1, 1, 0, ins);

        panel1.add(exit, buttonConstraints);
        panel1.add(audioSlider, sliderConstraints);
        panel1.add(testo1, testo1Constraints);
        panel1.add(checkboxe, checkboxConstraints);
        
        constraints = ConstraintsBuilder.make(0, 0, 1, 1, 0);
        panels = panel1;
        
        music = manager.getAudioSystem();
        music.setNewAudioFile("game/selection.wav");
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

    private void button0Action(){
        music.stop();
        App.OPTIONS.setMusicGainSetting(audioSlider.getValue());
        File opt = new File("game/options.txt");
        try {
            if(!opt.exists())
                opt.createNewFile();
            try (FileWriter fw = new FileWriter(opt, false)){
                fw.write(OPTIONS.getMusicEnabledSetting() + ";" + OPTIONS.getMusicGainSetting());
                fw.close();
            }
        } catch (IOException e) {}
        manager.changeWindowType(0, null);
    }

    private void audioSliderAction(){
        testo1.setText("Riduzione volume ("+audioSlider.getValue()+" dB)");
        manager.redraw();
    }

    private void checkbox0Action(){
        App.OPTIONS.toggleMusic();
    }

    @Override
    public String getWindowName(){
        return windowName;
    } 

    @Override
    public String getWallpaper(){
        return null;
    }
}
