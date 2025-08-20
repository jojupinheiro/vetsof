/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.classes.Tutor;
import model.classes.Usuario;
import model.dao.TutorDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class TutorService {
    private TutorDAO dao;

    public TutorService() {
        dao = new TutorDAO(DB.getConnection());
    }
    
    public List<Tutor> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }

    public boolean salvarOuAtualizar(Tutor tutor) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (tutor.getIdTutor()<= 0) {
            //é inclusão
            return dao.inserir(tutor);

        } else {
            // é uma alteração
            return dao.editar(tutor);
        }
    }
    
    public boolean excluir(Tutor tutor){
        return dao.excluir(tutor);
    }
    
}


