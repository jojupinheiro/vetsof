/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.classes.SalaEspera;
import model.classes.Veterinario;
import model.dao.SalaEsperaDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class SalaEsperaService {
    private SalaEsperaDAO dao;

    public SalaEsperaService() {
        dao = new SalaEsperaDAO(DB.getConnection());
    }
    
    public List<SalaEspera> getAll(){
        return dao.getAll();
    }
    
    public boolean salvarOuAtualizar(SalaEspera salaEspera){
        //Tenho que testar se é uma inclusão ou alteração
        if (salaEspera.getId()<= 0){
            //É inclusão
            return dao.inserir(salaEspera);
        }else {
            //È alteração
            return dao.editar(salaEspera);
        }
    }
    
    public boolean excluir(SalaEspera salaEspera){
        return dao.excluir(salaEspera);
    }
}
