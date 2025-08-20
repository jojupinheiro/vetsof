package model.services;

import java.util.List;
import java.util.Map;
import model.classes.prescricoes.FormatacaoPrescricao;
import model.classes.prescricoes.Prescricao;
import model.dao.PrescricaoDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class PrescricaoService {

    private PrescricaoDAO dao;

    public PrescricaoService() {
        dao = new PrescricaoDAO(DB.getConnection());
    }

    public List<Prescricao> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }

    public Map<String, Map<String, String[]>> getProdutosDaPrescricao(int idPrescricao) {
        return dao.getProdutosDaPrescricao(idPrescricao);
    }

    public boolean salvarOuAtualizar(Prescricao prescricao) {
        //Tenho que testar se é uma inclusão ou alteração
        if (prescricao.getId() <= 0) {
            //É inclusão
            return dao.inserir(prescricao);
        } else {
            //È alteração
            return dao.editar(prescricao);
        }
    }

    public boolean excluir(Prescricao prescricao) {
        return dao.excluir(prescricao);
    }

    public List<FormatacaoPrescricao> getFormatacaoDoModeloDaPrescricao(int modelo) {
        return dao.getFormatacaoDoModeloDaPrescricao(modelo);
    }

    public List<FormatacaoPrescricao> getFormatacaoAtivaDaPrescricao() {
        return dao.getFormatacaoAtivaDaPrescricao();
    }

    public boolean salvarOuAtualizarFormatacao(List<FormatacaoPrescricao> list) {
        boolean result = false;
        for (FormatacaoPrescricao item : list) {
            if (item.getId() <= 0) {
                //É inclusão
                result = dao.inserirFormatacao(item);
            } else {
                //È alteração
                result = dao.editarFormatacao(item);
            }
        }
        return result;
    }
    
    public boolean definirModeloFormatacaoComoAtivo(int modelo){
        return dao.definirModeloFormatacaoComoAtivo(modelo);
    }

}
