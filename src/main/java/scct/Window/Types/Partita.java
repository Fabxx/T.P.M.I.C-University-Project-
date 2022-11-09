package scct.Window.Types;

import scct.App;
import scct.Window.ManagerFinestra;
import scct.Window.Types.Utils.ConstraintsBuilder;
import scct.Window.Types.Utils.Gioco;
import scct.Window.Types.Utils.InventarioRenderer;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Partita extends TipoFinestra {

    private final ManagerFinestra manager;

    private final Gioco gioco;

    private final JPanel mainPanel = new JPanel(new GridBagLayout());
    private final Insets ins = new Insets(5, 10, 5, 10);

    private final JLabel schermata;
    private GridBagConstraints schermataConstr= new GridBagConstraints();

    private final JList<String> inventario;
    private GridBagConstraints inventarioConstr= new GridBagConstraints();

    private final JPanel azioni;
    private GridBagConstraints azioniConstr= new GridBagConstraints();

    private JTextArea storia;
    private GridBagConstraints storiaConstr= new GridBagConstraints();

    private JButton salvaButton;
    private JButton caricaButton;
    private String pathIconaSalvataggio;
    private float idScenaSalvataggio;

    private Thread sceneDrawerThread;

    /*
     * In questa classe si effettuano i seguenti controlli sul nome del file durante il salvataggio:
     * -Verifica se il nome non è vuoto.
     * -Verifica in base al filesystem del sistema operativo quali sono i caratteri accettati nel nome del file.
     */
    class SalvaPartitaPane{
        JFrame box;
        SalvaPartitaPane(){
            box = new JFrame();
            boolean end = false;
            String name = "";
            String errorMessage;
            while(!end){
                errorMessage = "Il nome inserito non è valido. Assicurati che sia scritto correttamente.\n";
                name = JOptionPane.showInputDialog(box, "Nome del salvataggio");
                if (name.length() != 0 && !(name.matches("[ ]+")))
                {
                    String system = System.getProperty("os.name");
                    system = new StringTokenizer(system, " ").nextToken();
                    switch(system){
                        case "Linux":{
                            if(name.matches(".*[/].*"))
                                errorMessage+="Il nome non può contenere il carattere '/'";
                            else
                                end = true;
                            break;
                        }
                        case "Windows":{
                            if(name.matches(".*[<>:\"/\\|?*].*"))
                                errorMessage+="Il nome non può contenere caratteri da questa lista (<, >, :, \", /, \\, |, ?, *)";
                            else
                                if(name.matches("^(?:PRN|AUX|CLOCK\\$|NUL|CON|COM\\d|LPT\\d)$"))
                                    errorMessage+="Il nome non può essere un nome riservato dal sistema (PRN, AUX, CLOCK, NUL, CON, COM, LPT)";
                                else
                                    end = true;
                            break;
                        }
                        case "Mac":{
                            if(name.matches(".*[/:].*"))
                                errorMessage+="Il nome non può contenere caratterida questa lista (/, :)";
                            else 
                                end = true;
                            break;
                        }
                        default:{
                            end = true; //Provo comunque a salvarlo
                        }
                    }
                }else{
                    errorMessage+="Il nome non deve ssere vuoto";
                }
                if(!end)
                    JOptionPane.showMessageDialog(box, errorMessage);
            }
            try {
                File sav = new File("game/saves/" + name + ".txt");
                sav.createNewFile();
                try (FileWriter fWriter = new FileWriter(sav)) {
                    fWriter.write(pathIconaSalvataggio+";"+idScenaSalvataggio+";"+LocalDateTime.now());
                }
                JOptionPane.showMessageDialog(box, "Creato file di salvataggio in "+sav.getAbsolutePath());             
            } catch (IOException e) {}
        }
    }

    private class Animation extends TimerTask{

        //Classe per thread di gestione animazione

        ImageReader reader = ImageIO.getImageReadersBySuffix("JPG").next(); //Imposta decoder
        private final JLabel video;
        private final List<String> framesbins;
        long frames;
        int currentFrame = 0;
        Map<Integer,String> soundData;

        private boolean animationEnded = false;

        Animation(JLabel video, List<String> framesbins, long frames, Map<Integer,String> soundData){
            this.video = video;
            this.framesbins = framesbins;
            this.frames = frames;
            this.soundData = soundData;
        }

        @Override
        public void run() {
            if(currentFrame < frames){
                //Carica in memoria il file .jpg con l'id generato dalla cartella attualmente in uso
                try {
                    reader.setInput(new FileImageInputStream(new File(framesbins.get(currentFrame))));
                } catch (IOException e) {}
                //Genera un elemento ImageIcon per il rendering da Java Swing
                video.setIcon(new ImageIcon(framesbins.get(currentFrame)));
                if(App.OPTIONS.getMusicEnabledSetting())
                    if(soundData.containsKey(currentFrame) && !gioco.getGameLoadRequest()){
                        music.setNewAudioFile(soundData.get(currentFrame));
                        music.play();
                    }
                currentFrame++;
            }else{
                animationEnded = true;
            }
            
        }

        boolean getAnimationEnded(){
            return animationEnded;
        }
        
    }

    public Partita(ManagerFinestra manager, Float lgioco) {
        this.manager = manager;
        panels = null;
        constraints = new GridBagConstraints();

        windowName = "T.P.M.I.C.";

        if(lgioco == null){
            /*
             * Richiesta una nuova partita
             */
            gioco = new Gioco();
        }else{
            /*
             * Richiesto il caricamento di una partita
             */
            gioco = new Gioco(lgioco);
        }
        
        /*
         * Schermata del videogioco con immagini ed animazioni
         */
        schermata = new JLabel("", SwingConstants.CENTER);
        gioco.getScena().load(gioco.getGameLoadRequest());
        playScene(schermata, gioco.getScena().getImmaginiPaths(), gioco.getScena().getFramesAmount(), gioco.getScena().getAudio());
        schermataConstr = ConstraintsBuilder.make(0, 0, 3, 2, 1, ins, 1, 1);

        /*
         * Lista oggetti nell'inventario
         */
        String invlist[]= new String[gioco.getInventory().getSize()];
        for(int i = 0; i < gioco.getInventory().getSize(); i++)
            invlist[i] = gioco.getInventory().getItemNameById(i);
        inventario = new JList<>(invlist);
        inventario.setCellRenderer(new InventarioRenderer(gioco.getInventory()));
        inventarioConstr = ConstraintsBuilder.make(3, 0, 1, 2, 1, ins, 1, 1);

        /*
         * Pannello azioni come Salva, Esci o movimenti
         */
        azioni = new JPanel();
        preparaBottoniDiSistema();
        azioniConstr = ConstraintsBuilder.make(0, 2, 1, 1, 3, ins);

        /*
         * La TextArea dove viene raccontata la storia
         */
        storia = new JTextArea(gioco.getScena().getLore());
        storia.setLineWrap(true);
        storia.setWrapStyleWord(true);
        storia.setEditable(false);
        Font f = storia.getFont();
        /*
        * Ridimensione il testo nella JTextArea al ridimensionamento della finestra,
        * così da non apparirre troppo grande in una finestra ridimensionata
        */
        storia.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e){
                storia.setFont(f.deriveFont(manager.getWindowWidth() / 75.0f));
            }
        });
        storiaConstr = ConstraintsBuilder.make(1, 2, 3, 1, 1, ins, 1, 1);

        /*
         * Finalizza finestra
         */
        mainPanel.add(schermata, schermataConstr);
        mainPanel.add(inventario, inventarioConstr);
        mainPanel.add(azioni, azioniConstr);
        mainPanel.add(new JScrollPane(storia), storiaConstr); //La JTextArea è aggiunta in un JScrollPane per far contenere l'intero testo nel suo spazio
        mainPanel.setOpaque(false);

        constraints = ConstraintsBuilder.make(0, 0, 1, 1, 1, 1, 1);
        panels = mainPanel;
        
        music = manager.getAudioSystem();

    }

    private void preparaBottoniDiSistema(){
        azioni.setLayout(new GridBagLayout());

        salvaButton = new JButton("SALVA PARTITA");
        salvaButton.addActionListener(a -> new SalvaPartitaPane());
        salvaButton.setEnabled(false);
        azioni.add(salvaButton, ConstraintsBuilder.make(0, 97, 1, 1, 0, ins));

        caricaButton = new JButton("CARICA PARTITA");
        caricaButton.addActionListener(a -> buttonCaricaPartita());
        caricaButton.setEnabled(false);
        azioni.add(caricaButton, ConstraintsBuilder.make(0, 98, 1, 1, 0, ins));
        
        JButton esciButton = new JButton("FINE PARTITA");
        esciButton.addActionListener(a -> buttonEsciAction());
        azioni.add(esciButton, ConstraintsBuilder.make(0, 99, 1, 1, 0, ins));
    }

    /*
    * Avvia l'intera scena, facendo da "centralina" per il caricamento e rendering dei fotogrammi ed azioni.
    */
    private void playScene(JLabel schermata, List<String> framesbins, long frames, Map<Integer,String> soundData){

        sceneDrawerThread = new Thread(new Runnable() {
            final JLabel s = schermata; //Schermo da animare
            final List<String> fb = framesbins; //Oggetti precostruiti dei fotogrammi da mostrare
            long f = frames; //Quantità di fotogrammi dell'animazione
            final Map<Integer,String> sd = soundData; //Dati audio (frame, path a file audio)
            @Override
            public void run() {
                if(gioco.getGameLoadRequest()){
                    f = 1;
                    while(fb.size() != f){
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {}
                    } //Attendo che il singolo frame finisca di caricare
                }else{
                    while(f*15/100 > fb.size()){
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {}
                    } //Attendo che il 15% dei frame venga caricato
                }
                
                s.setIcon(new ImageIcon(fb.get(0)));
                Timer t = new Timer();
                Animation a = new Animation(s, fb, f, sd);
                t.scheduleAtFixedRate(a, 0, 42); //per renderizzare a ~24FPS (ottieni valore arrotondando 1000/targetFPS)
                while(!a.getAnimationEnded() && !Thread.currentThread().isInterrupted()){
                    
                }
                t.cancel();
                gioco.restoreGameLoadRequestState();
                
                //Costruzione bottoni della scena 
                if(!Thread.currentThread().isInterrupted()){
                    int actionsN = gioco.getScena().getAzioni().size();
                    for(int i = 0; i < actionsN; i++){
                        JButton newButton = new JButton(gioco.getScena().getAzioni().get(i).getNomeBottone());
                        final int idBottone = i;
                        newButton.addActionListener(b -> buttonCambiaScena(idBottone));
                        azioni.add(newButton, ConstraintsBuilder.make(0, i, 1, 1, 0, ins));
                    }
                }
                salvaButton.setEnabled(true);
                caricaButton.setEnabled(true);
                manager.redraw();
                
                pathIconaSalvataggio = fb.get(fb.size()-1);
                idScenaSalvataggio = gioco.getScena().getIdScena();
                if(gioco.getScena().isUltimaScena())
                    JOptionPane.showMessageDialog(new JFrame(), "Il gioco termina qui. Puoi ora finire la partita o caricare un salvataggio precedente.");
            }
        });
        sceneDrawerThread.start();
    }

    @Override
    public GridBagConstraints getConstraints(){
        return constraints;
    }

    @Override
    public String getWindowName(){
        return windowName;
    } 

    @Override
    public String getWallpaper(){
        return wallpaper;
    }

    @Override
    public JPanel getComponents() {
        return panels;
    }

    private void buttonCaricaPartita(){
        if(music != null && music.isValid())
            if(music.getMicrosecondPosition() < music.getMicrosecondLenght())
                music.stop();
        manager.changeWindowType(2, gioco.getScena().getIdScena());
    }

    private void buttonEsciAction(){

        if(music != null && music.isValid())
            if(music.getMicrosecondPosition() < music.getMicrosecondLenght())
                music.stop();
        sceneDrawerThread.interrupt();
        manager.changeWindowType(0, null);
    }

    private void buttonCambiaScena(int idBottone){
        int oggettoNecessario = gioco.getScena().getAzioni().get(idBottone).getOggettoNecessario();
        if(oggettoNecessario == -1 || oggettoNecessario == inventario.getSelectedIndex()){
            sceneDrawerThread.interrupt();
            if(gioco.getScena() != null){
                gioco.setScena(gioco.getScenaDaId(gioco.getScena().getAzioni().get(idBottone).getIdProssimaScena()));
                
                storia.setText(gioco.getScena().getLore());
                azioni.removeAll();
    
                preparaBottoniDiSistema();
                manager.redraw();
        
                gioco.getScena().load(false);
                playScene(schermata, gioco.getScena().getImmaginiPaths(), gioco.getScena().getFramesAmount(), gioco.getScena().getAudio());
                System.gc(); //Chiamata a garbage collector per pulire i residui della animazione della scena
            }
        }else{
            JOptionPane.showMessageDialog(new JFrame(), "Devi selezionare "+gioco.getInventory().getItemNameById(gioco.getScena().getAzioni().get(idBottone).getOggettoNecessario())+" per questa azione"); 
        }
    }
}
