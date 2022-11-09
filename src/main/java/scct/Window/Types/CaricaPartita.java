package scct.Window.Types;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import scct.App;
import scct.Window.ManagerFinestra;
import scct.Window.Types.Utils.ConstraintsBuilder;
import scct.Window.Types.Utils.SalvataggiRenderer;

public class CaricaPartita extends TipoFinestra{

    private final JButton[] buttons = new JButton[3];
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> listaSalvataggi = new JList<>(model);
    private String savs[];
    private GridBagConstraints savConstraints = new GridBagConstraints();
    protected GridBagConstraints[] buttonConstraints;
    private final Float recData;
    
    private final JPanel panel1 = new JPanel(new GridBagLayout());
    private final ManagerFinestra manager;
    private final Insets ins = new Insets(10, 10, 10, 10);

    public CaricaPartita(ManagerFinestra manager, Float recData){
        panels = null;
        panel1.setOpaque(false);
        constraints = new GridBagConstraints();
        this.manager = manager;
        this.recData = recData;

        windowName = "Carica Partita";

        buttonConstraints = new GridBagConstraints[buttons.length];

        buttons[0] = new JButton("Annulla Carica");
        buttons[0].addActionListener(a -> buttonAnnullaAction());
        buttonConstraints[0] = ConstraintsBuilder.make(1, 5, 1, 1, 0, ins, 1, 0);

        buttons[1] = new JButton("Elimina Partita");
        buttons[1].addActionListener(a -> buttonEliminaAction());
        buttonConstraints[1] = ConstraintsBuilder.make(2, 5, 1, 1, 0, ins, 1, 0);


        buttons[2] = new JButton("Carica Partita");
        buttons[2].addActionListener(a -> buttonCaricaAction());
        buttonConstraints[2] = ConstraintsBuilder.make(3, 5, 1, 1, 0, ins, 1, 0);

        preparaLista();
        savConstraints = ConstraintsBuilder.make(0, 0, 5, 5,1, ins, 1, 1);

        panel1.add(new JScrollPane(listaSalvataggi), savConstraints);
        for(int i = 0; i < buttons.length; i++){
            panel1.add(buttons[i], buttonConstraints[i]);
        }
        constraints = ConstraintsBuilder.make(0, 0, 1, 1, 1, 1, 1);
        panels = panel1;
        
        music = manager.getAudioSystem();
        music.setNewAudioFile("game/selection.wav");
        if(App.OPTIONS.getMusicEnabledSetting()){
            music.enableLoop();
            music.play();
        }

        if(model.size() == 0){
            JOptionPane.showMessageDialog(new JFrame(), "Non è disponibile alcun salvataggio da caricare"); 
        }
    }

    private void preparaLista(){
        savs = new File("game/saves").list();
        List<String> validSaveThumbs = new ArrayList<>();
        List<Float> validSaveScene = new ArrayList<>();
        List<String> validSaveDate = new ArrayList<>();
        for(int i = 0; i < savs.length; i++){
            String sav = savs[i];
            File file = new File("game/saves/"+sav);
            try (FileReader fr = new FileReader(file); BufferedReader buf = new BufferedReader(fr)){
                StringTokenizer tok = new StringTokenizer(buf.readLine(), ";");
                buf.close();
                fr.close();
                String thumb = "";
                float scena = -1;
                String date = "";
                boolean valid = true;
                if(tok.hasMoreTokens())
                    thumb = tok.nextToken();
                else
                    valid = false;
                if(tok.hasMoreTokens())
                    scena = Float.parseFloat(tok.nextToken());
                else
                    valid = false;
                if(tok.hasMoreTokens())
                    date = tok.nextToken();
                else
                    valid = false;
                if(valid){
                    validSaveScene.add(scena);
                    validSaveThumbs.add(thumb);
                    LocalDateTime datetimeobj = LocalDateTime.parse(date);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    validSaveDate.add(datetimeobj.format(formatter));
                }else{
                    savs[i] = savs[savs.length-1];
                    savs = Arrays.copyOf(savs, savs.length-1);
                    i--;
                }
            } catch (IOException e) {}
        }
        String[] friendlyName = new String[savs.length];
        for(int i = 0; i < savs.length; i++){
            friendlyName[i] = savs[i] + " - Scena " + validSaveScene.get(i) + " - " + validSaveDate.get(i);
        }
        String[] thumbs = validSaveThumbs.toArray(String[]::new);
        model.removeAllElements();
        for(int i = 0; i < friendlyName.length; i++){
            model.add(i, friendlyName[i]);
        }
        listaSalvataggi.setCellRenderer(new SalvataggiRenderer(thumbs, friendlyName));
    }

    private void buttonAnnullaAction(){

        if(music != null && music.isValid())
            if(music.getMicrosecondPosition() < music.getMicrosecondLenght())
                music.stop();
        if(recData == null)
            manager.changeWindowType(0, null);
        else
            manager.changeWindowType(1, recData);
    }

    private void buttonEliminaAction(){
        int index = listaSalvataggi.getSelectedIndex();
        if(index != -1){
            File file = new File("game/saves/"+savs[index]);
            if(!file.delete()){
                JOptionPane.showMessageDialog(new JPanel(), "Un errore di sistema non sta permettendo la cancellazione di "+file.getName());
            }
            preparaLista();
            manager.redraw();
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "Devi selezionare un salvataggio da cancellare"); 
        }
    }

    private void buttonCaricaAction(){
        int index = listaSalvataggi.getSelectedIndex();
        if(index != -1) {
            File file = new File("game/saves/"+savs[index]);
            try (FileReader fr = new FileReader(file); BufferedReader buf = new BufferedReader(fr)){
                StringTokenizer tok = new StringTokenizer(buf.readLine(), ";");
                String scene = "";
                buf.close();
                for(int i=0;i<2;i++) scene = tok.nextToken();
                float sceneNumber = Float.parseFloat(scene);
                manager.changeWindowType(1, sceneNumber);
            } catch (IOException e) {}
            manager.getAudioSystem().stop();
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "Devi selezionare un salvataggio da caricare"); 
        }
    }

    @Override
    public JPanel getComponents() {
        return panels;
    }

    @Override
    public GridBagConstraints getConstraints() {
        return constraints;
    }

    @Override
    public String getWindowName() {
        return windowName;
    }

    @Override
    public String getWallpaper() {
        return wallpaper;
    }
    
}
