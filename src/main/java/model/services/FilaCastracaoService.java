/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import model.classes.Pet;
import model.dao.FilaCastracaoDAO;

/**
 *
 * @author juliano
 */
public class FilaCastracaoService {
    private FilaCastracaoDAO dao;
    
    public boolean incluirNaFila(Pet pet) {
        return dao.incluirNaFila(pet);
    }

    public boolean removerDaFila(Pet pet) {
        return removerDaFila(pet);
    }
}
