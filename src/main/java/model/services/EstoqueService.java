package model.services;

import java.util.List;
import model.classes.controleEstoque.Estoque;
import model.dao.EstoqueDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class EstoqueService {
    private EstoqueDAO dao;

    public EstoqueService() {
        dao = new EstoqueDAO(DB.getConnection());
    }
    
    public List<Estoque> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }
    
    public Estoque getProdutoEmEstoque(int idEstoque){
        return dao.getProdutoEmEstoque(idEstoque);
    }

    public List<Estoque> getConsumoNaInternacao(int idDiariaInternacao){
        return dao.getConsumoNaInternacao(idDiariaInternacao);
    }
    
    public boolean salvarOuAtualizar(Estoque estoque){
        //Tenho que testar se é uma inclusão ou alteração
        if (estoque.getId()<= 0){
            //É inclusão
            return dao.inserirProdutoNoEstoque(estoque);
        }else {
            //È alteração
            return dao.editar(estoque);
        }
    }
    
    public boolean excluir(Estoque estoque){
        return dao.excluir(estoque);
    }
    
}
