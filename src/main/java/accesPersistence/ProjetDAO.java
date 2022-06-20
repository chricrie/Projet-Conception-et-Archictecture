package accesPersistence;

import application.ProjetDTO;

import java.time.LocalDate;
import java.util.TreeMap;

 class ProjetDAO {

    private static ProjetDTO projet1 = new ProjetDTO(1, "ProjetTopSecret1", LocalDate.of(2022,05,28), LocalDate.of(2022,05,29));
    private static ProjetDTO projet2 = new ProjetDTO(2, "ProjetTopSecret2", LocalDate.of(2022,05,28), LocalDate.of(2022,05,29));
    private static ProjetDTO projet3 = new ProjetDTO(3, "ProjetTopSecret3", LocalDate.of(2022,05,28), LocalDate.of(2022,05,29));
    private TreeMap<Integer, ProjetDTO> collectionProjets;
     public ProjetDAO() {
         this.collectionProjets = new TreeMap<>();
         this.collectionProjets.put(projet1.getIdProjet(), projet1);
         this.collectionProjets.put(projet2.getIdProjet(), projet2);
         this.collectionProjets.put(projet3.getIdProjet(), projet3);
     }

     public void persisterProjet(ProjetDTO projetDTO){
         this.collectionProjets.put(projetDTO.getIdProjet(), projetDTO);
     }

     public ProjetDTO chercherProjetDTOParId(int id){
         ProjetDTO projetDTO = this.collectionProjets.get(id);
         if(projetDTO == null) {
             System.out.println("Aucun projet avec cette id");
         }
         return projetDTO;
     }

     public ProjetDTO modifierUnProjet(ProjetDTO projetDTO){
         try {
             return this.collectionProjets.put(projetDTO.getIdProjet(), projetDTO);
         } catch (Exception e) {
             System.out.println("Modification interromptu");
//           persistanceProjets.transaction.rollback();  <-- rollback de la transaction dans un contexte transactionnel
             return null;
         }
     }
 }

