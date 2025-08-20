/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.classes.Servico;
import model.classes.Usuario;
import model.dao.ServicoDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ServicoService {
    private ServicoDAO dao;

    public ServicoService() {
        dao = new ServicoDAO(DB.getConnection());
    }
    
    public List<Servico> getAll(){
        return dao.getAll();
    }
    
    public Servico getServico(String nomeServico){
        return dao.getServico(nomeServico);
    }
    
    public List<Servico> getServicoDoAtendimento(int idAtendimento) {
        return dao.getServicoDoAtendimento(idAtendimento);
    }
    
    public boolean salvarOuAtualizar(Servico servico){
        //Tenho que testar se é uma inclusão ou alteração
        if (servico.getIdServico()<= 0){
            //É inclusão
            return dao.inserir(servico);
        }else {
            //È alteração
            return dao.editar(servico);
        }
    }
    
    public boolean excluir(Servico servico){
        return dao.excluir(servico);
    }
}
