package scct.Window.Types.Scenes.Base;

public class Azione {
    //Struttura generica che rappresenta una scelta dell'utente nel gioco. Ne chiama una nuova scena
    private String nomeBottone;
    private float idProssimaScena;
    private int oggettoNecessario;

    public Azione(String nomeBottone, float idProssimaScena, int oggettoNecessario){
        makeAzione(nomeBottone, idProssimaScena, oggettoNecessario);
    }

    public Azione(String nomeBottone, float idProssimaScena){
        makeAzione(nomeBottone, idProssimaScena, -1);
    }    
     
    private void makeAzione(String nomeBottone, float idProssimaScena, int oggettoNecessario){
        this.nomeBottone = nomeBottone;
        this.idProssimaScena = idProssimaScena;
        this.oggettoNecessario = oggettoNecessario;
    }

    public String getNomeBottone(){
        return nomeBottone;
    }

    public float getIdProssimaScena(){
        return idProssimaScena;
    }

    public int getOggettoNecessario(){
        return oggettoNecessario;
    }
}
