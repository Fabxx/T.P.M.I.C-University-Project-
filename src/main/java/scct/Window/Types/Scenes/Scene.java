package scct.Window.Types.Scenes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import scct.Window.Types.Scenes.Base.Azione;
import scct.Window.Types.Scenes.Base.Scena;

public final class Scene {

    private final Map<Float, Dati> datiScene = new HashMap<>();

    //Oggetti temporanei di storage per costruire gli oggetti Dati
    private List<String> immagini = new LinkedList<>();
    private String descrizione = "";
    private List<Integer> startSoundAtFrame = new LinkedList<>();
    private List<String> soundPath = new LinkedList<>();
    private List<Azione> azioni = new LinkedList<>();

    public class Dati {

        private List<String> immagini = new LinkedList<>();
        private String descrizione = "";
        private List<Integer> startSoundAtFrame = new LinkedList<>();
        private List<String> soundPath = new LinkedList<>();
        private List<Azione> azioni = new LinkedList<>();
        private boolean ultima = false;

        Dati(final List<String> immagini, final String descrizione, final List<Integer> startSoundAtFrame, final List<String> soundPath, final List<Azione> azioni) {
            this.immagini = immagini;
            this.descrizione = descrizione;
            this.startSoundAtFrame = startSoundAtFrame;
            this.soundPath = soundPath;
            this.azioni = azioni;
        }
        
        void setUltima(){
            ultima = true;
        }
        
        public boolean isUltimiDati(){
            return ultima;
        }

        public List<String> getImmagini() {
            return immagini;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public List<Integer> getSoundFrames() {
            return startSoundAtFrame;
        }

        public List<String> getSoundPaths() {
            return soundPath;
        }

        public List<Azione> getAzioni() {
            return azioni;
        }
    }

    public Scene() {

        //Scena 1 - Inizio
        immagini.add("game/scenes/1");
        immagini.add("game/scenes/2/1");
        descrizione = "Sei Sam Fisher, una splinter cell reclutata da Third Echelon dal programma sperimentale dell'NSA.\nTi trovi all'esterno di un'isola di un faro, devi proseguire all'interno della caverna per cercare L'ingegnere.";
        azioni.add(new Azione("Entra nella caverna", 2));
	startSoundAtFrame.add(0);
	soundPath.add("game/scenes/1.wav");
        datiScene.put(1f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 2 - Entra nella caverna
        immagini.add("game/scenes/2/2");
        descrizione = "Prosegui all'interno di un percorso stretto, che ti porta all'interno di una grotta";
        azioni.add(new Azione("Entra strisciando", 3));
        datiScene.put(2f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 3 - Striscia
        immagini.add("game/scenes/2/3");
        immagini.add("game/scenes/2/4");
        descrizione = "Arrivano due guardie, cerchi di ricavare delle informazioni da una di esse";
        azioni.add(new Azione("Ingaggia il nemico di soppiatto", 4));
        startSoundAtFrame.add(135);
        soundPath.add("game/scenes/2/2-3.wav");
        datiScene.put(3f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 4 - Attacca soldato
        immagini.add("game/scenes/3/1");
        descrizione = "Ascolti le informazioni dal soldato. Per evitare di lasciare tracce del tuo passaggio,\ndevi uccidere o stordire la guardia";
        azioni.add(new Azione("Stordisci", 5.1f));
        azioni.add(new Azione("Uccidi", 5.2f));
        startSoundAtFrame.add(408);
        soundPath.add("game/scenes/3/1.wav");
        datiScene.put(4f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 5.1 - Stordisci soldato e prosegui
        immagini.add("game/scenes/3/2/1");
        immagini.add("game/scenes/4/0");
        descrizione = "Dopo aver ascoltato le informazioni dal soldato, lo stordisci per poi continuare nella caverna.";
        azioni.add(new Azione("Assicurati sia morto", 6.1f));
        azioni.add(new Azione("Slegalo", 6.2f));
        startSoundAtFrame.add(228);
        soundPath.add("game/scenes/4/0.wav");
        datiScene.put(5.1f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 5.2 - Uccidi soldato e prosegui
        immagini.add("game/scenes/3/2/0");
        immagini.add("game/scenes/4/0");
        descrizione = "Dopo aver ascoltato le informazioni dal soldato, lo uccidi per poi continuare nella caverna.";
        azioni.add(new Azione("Assicurati sia morto", 6.1f));
        azioni.add(new Azione("Slegalo", 6.2f));
        startSoundAtFrame.add(188);
        soundPath.add("game/scenes/4/0.wav");
        datiScene.put(5.2f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 6.1 - Spara Morghenholt e vai avanti
        immagini.add("game/scenes/4/1/1");
        immagini.add("game/scenes/5/0/");
        descrizione = "Scegli di sparare Morghenholt e prosegui. Nel buio del corridoio trovi una porta chiusa";
        azioni.add(new Azione("Rompi serratura", 7.2f));
        azioni.add(new Azione("Scassina serratura", 7.1f));
        startSoundAtFrame.add(17);
        soundPath.add("game/scenes/4/1/1.wav");
        startSoundAtFrame.add(496);
        soundPath.add("game/scenes/5/0.wav");
        datiScene.put(6.1f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 6.2 - Libera a Morghenholt e vai avanti
        immagini.add("game/scenes/4/1/0");
        immagini.add("game/scenes/5/0/");
        descrizione = "Scegli di liberare Morghenholt e prosegui. Nel buio del corridoio trovi una porta chiusa";
        azioni.add(new Azione("Rompi serratura", 7.2f));
        azioni.add(new Azione("Scassina serratura", 7.1f));
        startSoundAtFrame.add(22);
        soundPath.add("game/scenes/4/1/0.wav");
        startSoundAtFrame.add(473);
        soundPath.add("game/scenes/5/0.wav");
        
        datiScene.put(6.2f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 7.1 Scassina la serratura e prosegui avanti
        immagini.add("game/scenes/5/1/0");
        immagini.add("game/scenes/5/3");
        descrizione = "La strada è bloccata da una porta con serratura. Decidi di scassinarla e di proseguire, ma c'è una guardia da superare";
        azioni.add(new Azione("Estrai Pistola", 8f, 0));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/5/1/0.wav");
        startSoundAtFrame.add(214);
        soundPath.add("game/scenes/5/1.wav");
        datiScene.put(7.1f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 7.2 Rompi la serratura e prosegui avanti
        immagini.add("game/scenes/5/1/1");
        immagini.add("game/scenes/5/3");
        descrizione = "La strada è bloccata da una porta con serratura. Decidi di romperla e proseguire, ma c'è una guardia da superare";
        azioni.add(new Azione("Estrai Pistola", 8f, 0));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/5/1/1.wav");
        startSoundAtFrame.add(214);
        soundPath.add("game/scenes/5/1.wav");
        datiScene.put(7.2f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 8 Estrai Pistola
        immagini.add("game/scenes/5/4");
        descrizione = "Per evitare di essere scoperto, usi l'OCP della pistola per disturbare la luce";
        azioni.add(new Azione("Usa OCP", 9, 0));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/5/4.wav");
        datiScene.put(8f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 9 Usa Pistola e prosegui
        immagini.add("game/scenes/5/5");
        descrizione = "Nel proseguire, ti imbatti in un'altra luce, riutilizza la pistola per proseguire";
        azioni.add(new Azione("Estrai Pistola", 10, 0));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/5/5.wav");
        datiScene.put(9f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 10 Riutilizza pistola
        immagini.add("game/scenes/5/6");
        descrizione = "Nel proseguire, ti imbatti in un'altra luce, riutilizza la pistola per proseguire";
        azioni.add(new Azione("Usa OCP", 11, 0));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/5/6.wav");
        datiScene.put(10f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 11 Prosegui finché non trovi altre guardie
        immagini.add("game/scenes/5/7");
        descrizione = "Ti imbatti in altre guardie, ascoltando la loro conversazione scopri dettagli importanti.";
        azioni.add(new Azione("Salta su sporgenza", 12));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/5/7.wav");
        datiScene.put(11f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 12 Sali su un piccolo tetto per non farti scoprire
        immagini.add("game/scenes/5/8");
        descrizione = "Trovi una sporgenza utile per non farti individuare. Devi rimanere nascosto";
        azioni.add(new Azione("Usa OCP", 13, 0));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/5/8.wav");
        datiScene.put(12f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //TODO Scene 6, audio non partono, probabile numero errato, controllare anche scene 5 in caso
        //Scena 13 DIsattivi un'altra luce
        immagini.add("game/scenes/6/0");
        descrizione = "Trovi una sporgenza utile per non farti individuare.";
        azioni.add(new Azione("Salta Sporgenza", 14));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/6/0.wav");
        datiScene.put(13f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 14 Ti aggrappi su un'altra sporgenza per proseguire, più avanti incontri un NPC
        immagini.add("game/scenes/6/1");
        descrizione = "Più avanti trovi il computer con il tecnico, recupera le informazioni dal server.";
        azioni.add(new Azione("Ingaggia NPC", 15));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/6/1.wav");
        datiScene.put(14f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 15 Interrogazione NPC 
        immagini.add("game/scenes/6/2");
        descrizione = "Più avanti trovi il computer con il tecnico, recupera le informazioni dal server.";
        azioni.add(new Azione("Uccidi NPC", 16.1f));
        azioni.add(new Azione("Stordisci NPC", 16.2f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/6/2.wav");
        datiScene.put(15f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 16.1 Uccidi NPC
        immagini.add("game/scenes/6/3/0");
        immagini.add("game/scenes/6/4");
        descrizione = "PIù avanti trovi il computer con il tecnico, recupera le informazioni dal server.";
        azioni.add(new Azione("Usa Computer", 17f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/6/3-0.wav");
        soundPath.add("game/scenes/6/4.wav");
        startSoundAtFrame.add(306);
        datiScene.put(16.1f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 16.2 Stordisci NPC
        immagini.add("game/scenes/6/3/1");
        immagini.add("game/scenes/6/4");
        descrizione = "Più avanti trovi il computer con il tecnico, recupera le informazioni dal server.";
        azioni.add(new Azione("Usa Computer", 17f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/6/3-1.wav");
        soundPath.add("game/scenes/6/4.wav");
        startSoundAtFrame.add(286);
        datiScene.put(16.2f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 17 Rimuovi i dati dal computer e prosegui verso il faro
        immagini.add("game/scenes/6/5");
        descrizione = "Rimuovi i dati dal computer e prosegui verso il faro";
        azioni.add(new Azione("Usa la porta", 18f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/6/5.wav");
        datiScene.put(17f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 18 Rimuovi i dati dal computer e prosegui verso il faro
        immagini.add("game/scenes/6/6");
        descrizione = "Origli la conversazione via Radio con la Maria Narcissa, per poi incamminarti verso la cima";
        azioni.add(new Azione("Sali l'ultima scala", 19f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/6/6.wav");
        datiScene.put(18f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 19 Rimuovi i dati dal computer e prosegui verso il faro
        immagini.add("game/scenes/7/0");
        descrizione = "Prosegui verso la cima del faro per l'estrazione";
        azioni.add(new Azione("Apri inventario", 20f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/7/0.wav");
        datiScene.put(19f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 20 Apri l'inventario e stordisci la guardia con un proiettile elettrico
        immagini.add("game/scenes/7/1");
        descrizione = "Per poter effettuare l'estrazione è necessario liberare l'area";
        azioni.add(new Azione("Usa SC-20K", 21f, 2));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/7/1.wav");
        datiScene.put(20f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 20 Miri con il fucile
        immagini.add("game/scenes/7/2");
        descrizione = "Per poter effettuare l'estrazione è necessario liberare l'area";
        azioni.add(new Azione("Usa gadget", 22f, 1));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/7/2.wav");
        datiScene.put(21f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 21 Stordisci l'NPC
        immagini.add("game/scenes/7/3");
        descrizione = "Ora che l'area è libera, spegni il faro e chiama per l'estrazione";
        azioni.add(new Azione("Spegni il faro", 23f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/7/3.wav");
        datiScene.put(22f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 21 Procedi a spegnere il faro
        immagini.add("game/scenes/7/4");
        descrizione = "Ora che l'area è libera, spegni il faro e chiama per l'estrazione";
        azioni.add(new Azione("Chiama estrazione", 24f));
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/7/4.wav");
        datiScene.put(23f, new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni));
        reset();

        //Scena 22 Estrazione
        immagini.add("game/scenes/7/5");
        descrizione = "Ora che l'area è libera, spegni il faro e chiama per l'estrazione";
        startSoundAtFrame.add(0);
        soundPath.add("game/scenes/7/5.wav");
        Dati ultimi = new Dati(immagini, descrizione, startSoundAtFrame, soundPath, azioni);
        ultimi.setUltima();
        datiScene.put(24f, ultimi);
        reset();

        clean(); //Dereferenzia tutti gli elementi, va usato solo alla fine.
    }

    private void reset() {
        immagini = new LinkedList<>();
        descrizione = "";
        startSoundAtFrame = new LinkedList<>();
        soundPath = new LinkedList<>();
        azioni = new LinkedList<>();
    }

    /**
     * Clean viene chiamata alla FINE della impostazione di tutte le scene,
     * dereferenziando le strutture temporanee. Permette quindi al GC (Garbage
     * Collector) di raccogliere i dati dereferenziati.
     */
    private void clean() {
        immagini = null;
        descrizione = null;
        startSoundAtFrame = null;
        soundPath = null;
        azioni = null;
    }

    public Scena costruisciScena(final float idScena) {
        final Dati dati = datiScene.get(idScena);
        final Scena scena = new Scena(dati.getDescrizione(), idScena, dati.getImmagini());
        if(dati.isUltimiDati())
            scena.setQuestaUltimaScena();
        for (final Azione a : dati.getAzioni()) {
            scena.addAction(a);
        }
        for (int x = 0; x < dati.getSoundFrames().size(); x++) {
            scena.addSoundFX(dati.getSoundFrames().get(x), dati.getSoundPaths().get(x));
        }
        return scena;
    }
}
