/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.classes.Atendimento;
import model.classes.Pet;
import model.classes.ExameRealizado;
import model.classes.Vacina;
import model.dao.ExameRealizadoDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ExameRealizadoService {
    
    private ExameRealizadoDAO dao;

    public ExameRealizadoService() {
        dao = new ExameRealizadoDAO(DB.getConnection());
    }

//    public List<Vacina> getAll(int filtroSelecionado, String txtFiltro) {
//        return dao.getAll(filtroSelecionado, txtFiltro);
//    }
    public List<ExameRealizado> getExamesDoPet(Pet pet) {
        return dao.getExamesDoPet(pet);
    }

    public List<ExameRealizado> getExamesDoAtendimento(int idAtendimento) {
        return dao.getExamesDoAtendimento(idAtendimento);
    }
    
    public List<ExameRealizado> getExamesDaDiariaDaInternacao(int idDiariaInternacao) {
        return dao.getExamesDaDiariaDaInternacao(idDiariaInternacao);
    }

    public boolean salvarOuAtualizar(ExameRealizado exameRealizado) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (exameRealizado.getId() <= 0) {
            //é inclusão
            return dao.inserir(exameRealizado);

        } else {
            // é uma alteração
            return dao.editar(exameRealizado);
        }
    }

    public boolean excluir(ExameRealizado exameRealizado) {
        return dao.excluir(exameRealizado);
    }
}
