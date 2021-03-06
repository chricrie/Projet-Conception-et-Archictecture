package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;

class FacadeBilletTest {

    /**
     * Les méthodes de test de cette page doivent être testées une à la fois
     */
    private final FacadeBillet facadeBillet = new FacadeBillet();
    private final FacadeCompteUsager facadeCompteUsager = new FacadeCompteUsager();
    private final FacadeProjet facadeProjet = new FacadeProjet();
    private final RegistreBillet registreBillet = RegistreBillet.getInstance();
    private final Proxy proxy = new Proxy();

    private final BilletDTO billetDTO1 = new BilletDTO(1, "Ouvert", "Urgent",
            "utilisateur1@gmail.com", "Etat du projet ne s'update pas.",
            "J'ai redémarré et ça ne fonctionne pas.", LocalDate.of(2022, 6, 20));
    private final BilletDTO billetDTO2 = new BilletDTO(1, "Ouvert", "Bénin",
            "utilisateur1@gmail.com", "Etat du projet ne s'update pas.",
            "J'ai redémarré et ça ne fonctionne pas.", LocalDate.of(2022, 5, 18));
    private final BilletDTO billetDTO3 = new BilletDTO(1, "Fermé", "Bénin",
            "utilisateur1@gmail.com", "Etat du projet ne s'update pas.",
            "J'ai redémarré et ça ne fonctionne pas.", LocalDate.of(2022, 5, 18));
    private final BilletDTO billetDTO4 = new BilletDTO(1, "Fermé", "Urgent",
            "utilisateur6@gmail.com", "Etat du projet ne s'update pas.",
            "J'ai redémarré et ça ne fonctionne pas.", LocalDate.of(2022, 6, 20));
    private final ProjetDTO projetDTO1 =
            new ProjetDTO(1, "ProjetTopSecret", LocalDate.of(2022, 5, 28),
                    LocalDate.of(2022, 5, 29));

    private final Categorie categorieAnomalie = new Categorie("Anomalie");
    private final Categorie categorieEchec = new Categorie("Echec");
    private final Categorie categorieRidicule = new Categorie("Ridicule");
    private final CompteUsagerTechDTO usagerDTO1 =
            new CompteUsagerTechDTO(1, "Roger", "mdp", "email");

    @BeforeEach
    void setUp() {

        //Créer un projetDTO
        facadeProjet.creerProjet(projetDTO1);

        //Créer un CompteUsagerTechniqueDTO
        facadeCompteUsager.creerCompteUsagerTech(usagerDTO1);
        //Assigner UsagerTechnique au projet
        facadeProjet.assignerUsagerTech(usagerDTO1, projetDTO1);

        //Ajouter une catégorie au billet
        projetDTO1.ajouterCategorieBillet(categorieAnomalie);
        projetDTO1.ajouterCategorieBillet(categorieEchec);
        projetDTO1.ajouterCategorieBillet(categorieRidicule);
        //Attibuer une catégorie au billet
        billetDTO1.setCategorie(categorieAnomalie);
        billetDTO2.setCategorie(categorieEchec);
        billetDTO3.setCategorie(categorieAnomalie);
        billetDTO4.setCategorie(categorieRidicule);
    }

    @Test
    void assignerBillet() {
        facadeBillet.creerBillet(billetDTO1);
        BilletDTO billetTrouve = facadeBillet.consulterBilletParId(1);
        billetTrouve.setIdUsagerTechAssigne(1);
        facadeBillet.assignerBillet(billetTrouve);
        assertEquals(1,facadeBillet.consulterBilletParId(1).getIdUsagerTechAssigne());
    }

    @Test
    void testCreerBilletEnPassantUnBilletDTO() {
        int indice = facadeBillet.creerBillet(billetDTO1);
        assertTrue(indice > 0);

        BilletDTO billetTrouve = facadeBillet.consulterBilletParId(indice);

        RegistreHistorique registreHistoriqueTrouve = billetTrouve.getRegistreHistorique();

        int id = RegistreHistorique.getInstance().lastKey();

        Historique historiqueTrouve = registreHistoriqueTrouve.chercherParNumero(id);

        assertEquals(20, historiqueTrouve.getIdHistorique());
    }

    @Test
    void testerSiChangerEtatSeRetrouveDansLeRegistreAvecSonHistoriqueAjoute() {
        //Debut
        facadeBillet.creerBillet(billetDTO1); //etat = ouvert
        BilletDTO billetDTO = facadeBillet.consulterBilletParId(1);

        facadeBillet.changerEtatBillet(billetDTO, "Fermé", "Travail terminé. Fermeture du billet");
        assertEquals("Fermé", facadeBillet.consulterBilletParId(1).getEtat());

        //Registre historique doit contenir original + trace du changement
        RegistreHistorique registreHistorique = facadeBillet.consulterBilletParId(1).getRegistreHistorique();
        assertEquals(11, RegistreHistorique.getInstance().size());
    }

    @Test
    void consulterDetailBillet() {
        facadeBillet.creerBillet(billetDTO1);
        BilletDTO billetVerification= facadeBillet.consulterBilletParId(1);
        assertEquals(1,billetVerification.getIdBillet());
    }

    @Test
    void faireSortirLaListedeBilletsFiltreeParDateDebut() {
        facadeBillet.creerBillet(billetDTO1); //20 juin
        facadeBillet.creerBillet(billetDTO2); //18 mai
        facadeBillet.creerBillet(billetDTO3); //18 mai
        facadeBillet.creerBillet(billetDTO4); //20 juin

        TreeMap<Integer,BilletDTO> liste =facadeBillet.consulterListeBillet("date", LocalDate.of(2022, 5, 18));
        assertEquals(10,liste.size());
    }

    @Test
    void faireSortirLaListedeBilletsFiltreeParDemandeur() {
        facadeBillet.creerBillet(billetDTO1); //utilisateur1@gmail.com
        facadeBillet.creerBillet(billetDTO2); //utilisateur1@gmail.com
        facadeBillet.creerBillet(billetDTO3); //utilisateur1@gmail.com
        facadeBillet.creerBillet(billetDTO4); //utilisateur6@gmail.com
        TreeMap<Integer,BilletDTO> liste = facadeBillet.consulterListeBillet("demandeur", "utilisateur6@gmail.com");
        assertEquals(8,liste.size());
    }

    @Test
    void faireSortirLaListedeBilletsFiltreeParEtat() {
        facadeBillet.creerBillet(billetDTO1); //ouvert
        facadeBillet.creerBillet(billetDTO2); //ouvert
        facadeBillet.creerBillet(billetDTO3); //fermé
        facadeBillet.creerBillet(billetDTO4); //fermé
        TreeMap<Integer,BilletDTO> liste = facadeBillet.consulterListeBillet("etat", "Ouvert");
        assertEquals(16,liste.size());
    }

    @Test
    void faireSortirLaListedeBilletsFiltreeParTech() {
        facadeBillet.creerBillet(billetDTO1); //0
        facadeBillet.creerBillet(billetDTO2); //0
        facadeBillet.creerBillet(billetDTO3); //0
        facadeBillet.creerBillet(billetDTO4); //0
        TreeMap<Integer,BilletDTO> liste = facadeBillet.consulterListeBillet("tech", "0");
        assertEquals(31,liste.size());
    }

    @Test
    void faireSortirLaListedeBilletsFiltreeParProjet() {
        facadeBillet.creerBillet(billetDTO1); //1
        facadeBillet.creerBillet(billetDTO2); //1
        facadeBillet.creerBillet(billetDTO3); //1
        facadeBillet.creerBillet(billetDTO4); //1
        TreeMap<Integer,BilletDTO> liste = facadeBillet.consulterListeBillet("projet", "1");
        assertEquals(18, liste.size());

    }

    @Test
    void faireSortirLaListedeBilletsFiltreeParCategorie() {
        facadeBillet.creerBillet(billetDTO1); //anomalie
        facadeBillet.creerBillet(billetDTO2); //echec
        facadeBillet.creerBillet(billetDTO3); //anomalie
        facadeBillet.creerBillet(billetDTO4); //ridicule
        TreeMap<Integer,BilletDTO> liste = facadeBillet.consulterListeBillet("categorie", "Ridicule");
        assertEquals(1,liste.size());
    }

    @Test
    void faireSortirLaListedeBilletsFiltreeParGravite() {
        facadeBillet.creerBillet(billetDTO1); //urgent
        facadeBillet.creerBillet(billetDTO2); //benin
        facadeBillet.creerBillet(billetDTO3); //benin
        facadeBillet.creerBillet(billetDTO4); //urgent
        TreeMap<Integer,BilletDTO> liste = facadeBillet.consulterListeBillet("gravite", "Urgent");
        assertEquals(4,liste.size());
    }

    @Test
    void faireSortirUneListeVide(){
            facadeBillet.creerBillet(billetDTO1); //urgent
            facadeBillet.creerBillet(billetDTO2); //benin
            facadeBillet.creerBillet(billetDTO3); //benin
            facadeBillet.creerBillet(billetDTO4); //urgent
            TreeMap<Integer,BilletDTO> liste = facadeBillet.consulterListeBillet("gravite", "hjhdfkjhjds");
            assertEquals(0,liste.size());
        }
    @Test
    void modifierUnBilletBDAvecUsageTechBD() {
        BilletDTO billetDTO = proxy.chercherBilletDTOParId(1);
        assertEquals(0, billetDTO.getIdUsagerTechAssigne());

        CompteUsagerTechDTO compteUsagerTechDTO = proxy.chercherCompteUsagerTechDTOParId(1);
        billetDTO.setIdUsagerTechAssigne(compteUsagerTechDTO.getIdUsager());
        facadeBillet.assignerBillet(billetDTO);
        assertEquals(1, proxy.chercherBilletDTOParId(1).getIdUsagerTechAssigne());
    }

    @Test
    void modifierUnBilletBDAvecUsageTechBDEtVerifierChangementRegistre() {
        BilletDTO billetDTO = proxy.chercherBilletDTOParId(1);
        assertEquals(0, billetDTO.getIdUsagerTechAssigne());

        CompteUsagerTechDTO compteUsagerTechDTO = proxy.chercherCompteUsagerTechDTOParId(1);
        billetDTO.setIdUsagerTechAssigne(compteUsagerTechDTO.getIdUsager());
        facadeBillet.assignerBillet(billetDTO);
        assertEquals(1, registreBillet.chercherBilletParId(0).getIdUsagerTechAssigne());
    }
}


