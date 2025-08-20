/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.services;

import java.util.List;
import model.classes.ProdutoVacina;
import model.dao.ProdutoVacinaDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ProdutoVacinaService {
    private ProdutoVacinaDAO dao;

    public ProdutoVacinaService() {
        dao = new ProdutoVacinaDAO(DB.getConnection());
    }
    
    public List<ProdutoVacina> getAll(ProdutoVacina vacina) {
        return dao.getAll(vacina);
    }
    
    public List<ProdutoVacina> getTiposVacinas() {
        return dao.getTiposVacinas();
    }

    public boolean salvarOuAtualizarNomeVacina(ProdutoVacina produtoVacina) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (produtoVacina.getId()<= 0) {
            //é inclusão
            return dao.inserirNomeVacina(produtoVacina);

        } else {
            // é uma alteração
            return dao.editarNomeVacina(produtoVacina);
        }
    }
    
    public boolean salvarOuAtualizarTipoVacina(ProdutoVacina produtoVacina) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (produtoVacina.getId()<= 0) {
            //é inclusão
            return dao.inserirTipoVacina(produtoVacina);

        } else {
            // é uma alteração
            return dao.editarTipoVacina(produtoVacina);
        }
    }
    
    public boolean excluirNomeVacina(ProdutoVacina produtoVacina){
        return dao.excluirVacina(produtoVacina);
    }
    
    public boolean excluirTipoVacina(ProdutoVacina produtoVacina){
        return dao.excluirTipoVacina(produtoVacina);
    }
}
