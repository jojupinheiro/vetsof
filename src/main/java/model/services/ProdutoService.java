package model.services;

import java.util.List;
import model.classes.controleEstoque.Estoque;
import model.classes.controleEstoque.Produto;
import model.dao.ProdutoDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ProdutoService {
    private ProdutoDAO dao;
    
    public ProdutoService() {
        dao = new ProdutoDAO(DB.getConnection());
    }
    
    public List<Produto> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }
    
    public List<Produto> getCategoriasDeProduto() {
        return dao.getCategoriasDeProduto();
    }
    
    public List<Produto> getProdutosDaCategoria(int idCategoria) {
        return dao.getProdutosDaCategoria(idCategoria);
    }
    
    public boolean salvarOuAtualizarProduto(Produto produto){
        //Tenho que testar se é uma inclusão ou alteração
        if (produto.getId()<= 0){
            //É inclusão
            return dao.inserirProduto(produto);
        }else {
            //È alteração
            return dao.editarProduto(produto);
        }
    }
    
    public boolean salvarOuAtualizarCategoriaDeProduto(Produto produto){
        //Tenho que testar se é uma inclusão ou alteração
        if (produto.getIdCategoriaProduto()<= 0){
            //É inclusão
            return dao.inserirCategoriaDeProduto(produto);
        }else {
            //È alteração
            return dao.editarCategoriaDeProduto(produto);
        }
    }
    
    public boolean excluir(Produto produto){
        return dao.excluirProduto(produto);
    }
    
    public boolean excluirCategoriaDeProduto(Produto produto){
        return dao.excluirCategoriaDeProduto(produto);
    }
}
