
package model.services;

import java.util.List;
import model.classes.Venda;
import model.dao.VendaDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class VendaService {
    private VendaDAO dao;

    public VendaService() {
        dao = new VendaDAO(DB.getConnection());
    }
    
    public List<Venda> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }

    public boolean salvarOuAtualizar(Venda venda) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (venda.getId()<= 0) {
            //é inclusão
            return dao.inserir(venda);

        } else {
            // é uma alteração
            return dao.editar(venda);
        }
    }
    
    public boolean excluir(Venda venda){
        return dao.excluir(venda);
    }
}
