package model.services;

import java.util.List;
import model.classes.Atendimento;
import model.classes.Servico;
import model.classes.Usuario;
import model.dao.AtendimentoDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class AtendimentoService {
       private AtendimentoDAO dao;

    public AtendimentoService() {
        dao = new AtendimentoDAO(DB.getConnection());
    }
    
    public List<Atendimento> getAll(int filtroSelecionado, String txtFiltro){
        return dao.getAll(filtroSelecionado, txtFiltro);
    }
    
    public boolean salvarOuAtualizar(Atendimento atendimento){
        //Tenho que testar se é uma inclusão ou alteração
        if (atendimento.getIdAtendimento()<= 0){
            //É inclusão
            return dao.inserir(atendimento);
        }else {
            //È alteração
            return dao.editar(atendimento);
        }
    }
    
    public boolean excluir(Atendimento atendimento){
        return dao.excluir(atendimento);
    }
    
    public List<Servico> getServicosRealizados (Atendimento atendimento){
        return dao.getServicosRealizados(atendimento);
    }
}