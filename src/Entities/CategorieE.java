/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author House
 */
public class CategorieE {
    private int idC;
    private String nomC;


    public CategorieE() { 
    }

    public CategorieE(int idC, String nomC) {
        this.idC = idC;
        this.nomC = nomC;

        
    }

    public CategorieE(String nomC) {
        this.nomC = nomC;

        
    }
    
    public int getId() {
        return idC;
    }

    public String getNom() {
        return nomC;
    }

  
    public void setId(int idC) {
        this.idC = idC;
    }

    public void setNom(String nomC) {
        this.nomC = nomC;
    }


    

    @Override
    public String toString() {
        return "Categorie{" + "idC = " + idC + ", nomC = " + nomC + '}';
    }
    
}
