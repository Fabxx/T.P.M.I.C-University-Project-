package scct.Window.Types.Scenes.Base;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Scena {
    /**
     * Elementi ed azioni comuni in tutte le scene nel gioco.
     * Alla creazione della classe i suoi dati vengono preparati ma le immagini non saranno ancora caricate.
     * Per caricare le immagini, quindi prima dell'utilizzo della scena nel gioco, è necessario chiamare il metodo load();
     */
    
    private final List<String> immagini = new LinkedList<>(); //Lista di nomi delle cartelle dei file per fotogrammi
    private final List<String> immaginiPaths = new LinkedList<>(); //lista di tute le immagini da mostrare
    private long frames = 0; //Quantità di fotogrammi presenti
    private final String descrizione; //Descrizione della scena, lore della scena (Appare nella zona bassa della finestra di gioco)
    private final Map<Integer,String> soundData = new HashMap<>(); //Effetti sonori da attivare all'arrivo di un fotogramma
    private final List<Azione> azioni; //Lista di azioni possibili nella scena
    private final List<String> pathsFrames; //Lista di tutte le cartelle contenenti i fotogrammi della scena animata
    private final float idScena; //Identificatore della scena per permettere il salvataggio
    private boolean ultima = false; //Identificha se la seguente è l'ultima scena o no

    private Thread sceneLoadThread; 

    public Scena(String lore, float id ,String... paths){
        descrizione = lore;
        azioni = new ArrayList<>();
        pathsFrames = new ArrayList<>();
        pathsFrames.addAll(Arrays.asList(paths));
        idScena = id;
    }

    public Scena(String lore, float id, List<String> paths){
        descrizione = lore;
        azioni = new ArrayList<>();
        pathsFrames = new ArrayList<>();
        for(String imgf : paths){
            pathsFrames.add(imgf);
        }
        idScena = id;
    }

    public void load(boolean lastFrameOnly){
        for(String imgf : pathsFrames){
            immagini.add(imgf);
            try{
                //Aggiorna la quantità di fotogrammi di questa scena
                frames += new File(imgf).list().length;
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }
        //Prova a caricare tutti i fotogrammi
        sceneLoadThread = new Thread(() -> {
            if(!lastFrameOnly){
                for(String imgf : pathsFrames){
                    //Per tutti i file nella cartella
                    String videoid="";
                    //Raccolta dell'id video della scena
                    StringTokenizer st = new StringTokenizer(imgf, "/");
                    while(st.hasMoreTokens()){
                        videoid = st.nextToken();
                    }
                    for(int i = 1; i < new File(imgf).list().length+1; i++){
                        //Genera l'id del fotogramma aspettato
                        String formatted = String.format("%05d", i);
                        immaginiPaths.add(imgf+"/"+videoid+"-"+formatted+".jpg");
                    }
                }
            }else{
                frames = 1;
                String onlyName = "";
                for(String imgf : pathsFrames){
                    //Per tutti i file nella cartella
                    String videoid="";
                    //Raccolta dell'id video della scena
                    StringTokenizer st = new StringTokenizer(imgf, "/");
                    while(st.hasMoreTokens()){
                        videoid = st.nextToken();
                    }
                    int i = new File(imgf).list().length;
                    String formatted = String.format("%05d", i);
                    onlyName = imgf+"/"+videoid+"-"+formatted+".jpg";
                }
                immaginiPaths.add(onlyName);
            }
            
        });
        sceneLoadThread.start();
    }

    public void addAction(String nomeBottone, Scena prossimaScena, int idProssimaScena){
        azioni.add(new Azione(nomeBottone, idProssimaScena));
    }

    public void addAction(Azione azione){
        azioni.add(azione);
    }

    public void addSoundFX(int frame, String filename){
        soundData.put(frame, filename);
    }

    public List<String> getImages(){
        return immagini;
    }

    public String getLore(){
        return descrizione;
    }

    public long getFramesAmount(){
       return frames; 
    }

    public List<Azione> getAzioni(){
        return azioni;
    }

    public Map<Integer,String> getAudio(){
        return soundData;
    }

    public List<String> getImmaginiPaths(){
        return immaginiPaths;
    }

    public float getIdScena(){
        return idScena;
    }
    
    public void setQuestaUltimaScena(){
        ultima = true;
    }
    
    public boolean isUltimaScena(){
        return ultima;
    }
}
