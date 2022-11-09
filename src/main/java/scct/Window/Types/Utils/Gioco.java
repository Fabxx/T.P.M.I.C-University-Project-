package scct.Window.Types.Utils;

import scct.Window.Types.Scenes.Scene;
import scct.Window.Types.Scenes.Base.Scena;

public class Gioco {
    /*
     * Contiene le informazioni riguardo la partita tra cui inventario e stato della storia
     */

    private final Inventario inv;
    private Scena scenaAttuale;
    protected Scene tutteLeScene = new Scene();
    private boolean gameLoadRequest = false;

    //Nuova partita
    public Gioco(){
        inv = new Inventario(true);
        scenaAttuale = tutteLeScene.costruisciScena(1f);
    }

    //Carica partita
    public Gioco(float startingPoint){
        inv = new Inventario(true);
        gameLoadRequest = true;
        scenaAttuale = tutteLeScene.costruisciScena(startingPoint);
    }

    public Inventario getInventory(){
        return inv;
    }

    public Scena getScena(){
        return scenaAttuale;
    }

    public void setScena(Scena scena){
        scenaAttuale = scena;
    }

    public Scena getScenaDaId(float idScena){
        return tutteLeScene.costruisciScena(idScena);
    }

    public boolean getGameLoadRequest(){
        return gameLoadRequest;
    }

    public void restoreGameLoadRequestState(){
        gameLoadRequest = false;
    }
}
