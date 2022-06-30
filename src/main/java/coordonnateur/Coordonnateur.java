package coordonnateur;

import application.*;

import java.util.TreeMap;

public class Coordonnateur {

    FacadeProjet facadeProjet = new FacadeProjet();
    FacadeBillet facadeBillet = new FacadeBillet();
    FacadeCompteUsager facadeCompteUsager = new FacadeCompteUsager();

    public void creerCompteUsagerTech(CompteUsagerTechDTO compteDto) {
        facadeCompteUsager.creerCompteUsagerTech(compteDto);
    }

    public void creerProjet(ProjetDTO projetDTO) {
        facadeProjet.creerProjet(projetDTO);
    }

    public void assignerCompteUsagerTechAProjet(CompteUsagerTechDTO usagerDTO, ProjetDTO projetDTO) {
        facadeProjet.assignerUsagerTech(usagerDTO, projetDTO);
    }

    public boolean definirCategorieDeBillet(ProjetDTO projetDto, String nouvelleCategorie) {
        projetDto.ajouterCategorieBillet(new Categorie(nouvelleCategorie));
        if(facadeProjet.ajouterCategorie(projetDto)){
            return true;
        }
        return false;
    }

    public int creerBillet(BilletDTO billetDTO) {
        return facadeBillet.creerBillet(billetDTO);
    }

    public boolean assignerBilletAUsagerTech(BilletDTO billetDTO, CompteUsagerTechDTO compteUsagerTechDTO) {
        billetDTO.setIdUsagerTechAssigne(compteUsagerTechDTO.getIdUsager());
        if(facadeBillet.assignerBillet(billetDTO)){
            return true;
        }
        return false;
    }

    public void changerEtatBillet(BilletDTO billetDTO, String etat, String commentaire) {
        facadeBillet.changerEtatBillet(billetDTO, etat, commentaire);
    }

    public TreeMap<Integer, BilletDTO> consulterListeBillets(String champs, Object filtre) {
        return facadeBillet.consulterListeBillet(champs, filtre);
    }

    public BilletDTO consulterDetailBillet(int id) {
        return facadeBillet.consulterBilletParId(id);
    }

    public ProjetDTO consulterUnProjetDTOParId(int id) {
        return facadeProjet.consulterUnProjetDTOParId(id);
    }

    public CompteUsagerTechDTO consulterCompteUsagerTechDTO(int id) {
        return facadeCompteUsager.consulterCompteUsagerTechDTOParId(id);
    }
}
