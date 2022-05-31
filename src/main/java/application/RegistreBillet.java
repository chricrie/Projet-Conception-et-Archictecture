package application;

import java.util.TreeMap;

public class RegistreBillet {


    /**
     * Instance unique.
     */
    private static RegistreBillet registreBilletInstance = new RegistreBillet();


    /**
     * Registre des clients indexé par numéro.
     */
    private TreeMap<Integer, Billet> registreBillet = new TreeMap<>();

    /**
     * Dernier numéro attribué à un billet.
     */
    private static int dernierNumeroBilletAttribue = 0;


    /**
     * Constructeur par défaut
     */
    public RegistreBillet() {
    }

    /**
     * Getter de l'instance
     */

    public static RegistreBillet getRegistreBilletInstance() {
        if (registreBilletInstance == null) {
            registreBilletInstance = new RegistreBillet();
        }
        return registreBilletInstance;
    }

    /**
     * Ajouter billet au registre
     */
    public int ajouterBilletAuRegistre(Billet nouveauBillet) {
        nouveauBillet.setIdBillet(this.prochainIdBillet());
        this.registreBillet.put(nouveauBillet.getIdBillet(), nouveauBillet);

        return nouveauBillet.getIdBillet();
    }

    /**
     * Récupère un Billet par son Id
     */
    public Billet chercherBilletParId(int idBillet) {
        return this.registreBillet.get(idBillet);
    }

    /**
     * incrémentation automatique du IDBillet
     */
    private int prochainIdBillet() {
        return ++dernierNumeroBilletAttribue;
    }


    public TreeMap afficherRegistreBillet() {

        return registreBillet;
    }

    boolean billetExists(int idBillet){
        try {
            this.registreBillet.get(idBillet);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }


    BilletDTO consulterDetailBillet(int idBillet) {
        Billet billetTemporaire = chercherBilletParId(idBillet);

        return billetTemporaire.asBilletDTO();

    }

    public void modifierBillet(int id, Billet billetModifiable) {
        this.registreBillet.put(id, billetModifiable);
    }
}
