/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.classes.Exame;
import model.dao.ExameDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ExameService {
    private ExameDAO dao;

    public ExameService() {
        dao = new ExameDAO(DB.getConnection());
    }
    
    public List<Exame> getAll(){
        return dao.getAll();
    }
    
    public Exame getExame(String nomeExame){
        return dao.getExame(nomeExame);
    }
    
    public List<Exame> getExameDoAtendimento(int idAtendimento){
        return dao.getExameDoAtendimento(idAtendimento);
    }
    
    public boolean salvarOuAtualizar(Exame exame){
        //Tenho que testar se é uma inclusão ou alteração
        if (exame.getIdExame()<= 0){
            //É inclusão
            return dao.inserir(exame);
        }else {
            //È alteração
            return dao.editar(exame);
        }
    }
    
    public boolean excluir(Exame exame){
        return dao.excluir(exame);
    }
}
