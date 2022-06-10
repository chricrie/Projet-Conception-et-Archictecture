package application;

import accesPersistence.UsagerTechDAO;

public class FacadeProjet {

    private RegistreUsagerTech registreUsagerTech = RegistreUsagerTech.getInstance();
    private RegistreProjet registreProjet = RegistreProjet.getInstance();

    public FacadeProjet() {
    }

    public void creerProjet(ProjetDTO projetDTO){
        Projet nouveauProjet = new Projet(projetDTO);
        registreProjet.ajouterProjet(nouveauProjet);
    }


    public void ajouterCategorie(ProjetDTO projet) {
        Projet projetExistant = new Projet(projet);
        registreProjet.modifierProjet(projetExistant);
    }


    public void assignerUsagerTech(CompteUsagerTechDTO UsagerDTO, ProjetDTO projetDTO) {
        CompteUsagerTech usagerAAssigne = registreUsagerTech.trouverUsagerTech(UsagerDTO.getIdUsager());
        Projet projetAAssigne = registreProjet.trouverProjet(projetDTO.getIdProjet());
        projetAAssigne.ajouterUsagerAuRegistre(usagerAAssigne);
    }
}
