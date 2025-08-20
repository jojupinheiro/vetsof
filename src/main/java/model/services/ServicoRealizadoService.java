/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.classes.Pet;
import model.classes.ServicoRealizado;
import model.dao.ServicoRealizadoDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ServicoRealizadoService {
    private ServicoRealizadoDAO dao;

    public ServicoRealizadoService() {
        dao = new ServicoRealizadoDAO(DB.getConnection());
    }

//    public List<Vacina> getAll(int filtroSelecionado, String txtFiltro) {
//        return dao.getAll(filtroSelecionado, txtFiltro);
//    }
    public List<ServicoRealizado> getServicosDoPet(Pet pet) {
        return dao.getServicosDoPet(pet);
    }

    public List<ServicoRealizado> getServicosDoAtendimento(int idAtendimento) {
        return dao.getServicosDoAtendimento(idAtendimento);
    }
    
    public List<ServicoRealizado> getServicosDaDiariaDaInternacao(int idDiariaInternacao) {
        return dao.getServicosDaDiariaDaInternacao(idDiariaInternacao);
    }

    public boolean salvarOuAtualizar(ServicoRealizado servicoRealizado) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (servicoRealizado.getId() <= 0) {
            //é inclusão
            return dao.inserir(servicoRealizado);

        } else {
            // é uma alteração
            return dao.editar(servicoRealizado);
        }
    }

    public boolean excluir(ServicoRealizado servicoRealizado) {
        return dao.excluir(servicoRealizado);
    }
}
