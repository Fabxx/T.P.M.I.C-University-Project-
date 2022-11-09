package scct.Window.Types.Utils;

import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Un inventario è una lista generica di oggetti. Ogni oggetto ha un
 * identificatore che permettere ad ogni oggetti di essere trovato nella lista.
 */
public class Inventario {

    /**
     * Rappresenta un singolo semplice oggetti in un inventario. Composto da un
     * {@code int} identificatore, un {@code String name} nome e una {@code int}
     * quantità dello stesso oggetto.
     */
    class Item {

        private int id;
        private String name;
        private String icon;
        private String lore;

        Item(int newId, String newName, String newIcon, String loreFile) {
            this.id = newId;
            this.name = newName;
            this.icon = newIcon;
            this.lore = "";

            try {
                File loreTextFile = new File(loreFile);
                try ( Scanner sc = new Scanner(loreTextFile)) {
                    while (sc.hasNextLine()) {
                        lore += sc.nextLine();
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println(loreFile + " non e' presente nella cartella del gioco!");
            }
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public String getIcon() {
            return icon;
        }

        public String getLore() {
            return lore;
        }

    }

    private final List<Inventario.Item> inventory;

    public void safeAddToInventory(int newId, String newName, String newIcon, String loreFile) {
        safeAddToInventory(new Item(newId, newName, newIcon, loreFile));
    }

    private void safeAddToInventory(Item item) {
        boolean safeIdFound = false;
        boolean isNameSafe = true;
        //prima controlla se il nome è stato già inserito
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(item.getName())) {
                isNameSafe = false;
            }
        }

        //iniziando dall'id richiesto, trova il primo sicuro da usare (non già in uso)
        while (!safeIdFound && isNameSafe) {
            if (getItemNameById(item.getId()) == null) {
                inventory.add(item);
                safeIdFound = true;
            } else {
                item = new Item(item.getId() + 1, item.getName(), item.getIcon(), item.getLore());
            }
        }

    }

    public Inventario() {
        inventory = new ArrayList<>();
    }

    /*
     * Precostruiamo l'inventario con gli oggetti che saranno usati in gioco.
     */
    public Inventario(boolean prebuild) {
        inventory = new ArrayList<>();
        if (prebuild) {
            safeAddToInventory(new Item(0, "Pistola SC", "game/inv/pistola.jpg", "game/inv/pistola.htm"));
            safeAddToInventory(new Item(1, "Munizioni Elettriche", "game/inv/proiettile.jpg", "game/inv/proiettile.htm"));
            safeAddToInventory(new Item(2, "SC-20K", "game/inv/SC-20K.jpg", "game/inv/SC-20K.htm"));
        }
    }

    public String getItemNameById(int id) {
        String itemName = null;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                itemName = inventory.get(i).getName();
                i = inventory.size();
            }
        }
        return itemName;
    }
    
    public boolean deleteItemById(int id){
        boolean status = false;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                inventory.remove(i);
                status = true;
                i = inventory.size();
            }
        }
        return status;
    }

    public int getIdByItemName(String name) {
        int id = -1;

        for (int i = 0; i < inventory.size() && id == -1; i++) {
            if (inventory.get(i).getName().equals(name)) {
                id = inventory.get(i).getId();
            }
        }

        return id;
    }

    public String getItemIconPathById(int id) {
        String path = "";

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                path = inventory.get(i).getIcon();
            }
        }

        return path;
    }

    public String getLoreByItemId(int id) {
        String lore = null;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId() == id) {
                lore = inventory.get(i).getLore();
                i = inventory.size();
            }
        }
        return lore;
    }

    public int getSize() {
        return inventory.size();
    }
}
