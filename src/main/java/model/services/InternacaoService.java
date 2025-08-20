package model.services;

import java.util.List;
import model.classes.DiariaInternacao;
import model.classes.Internado;
import model.classes.Pet;
import model.classes.controleEstoque.Estoque;
import model.dao.InternacaoDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class InternacaoService {
    private InternacaoDAO dao;

    public InternacaoService() {
        dao = new InternacaoDAO(DB.getConnection());
    }
    
    public List<Internado> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }
    
    public boolean salvarOuAtualizarInternado(Internado internado) {
        //Tenho que testar se é uma inclusão ou alteração
        if (internado.getId() <= 0) {
            //É inclusão
            return dao.inserirInternado(internado);
        } else {
            //È alteração
            return dao.editarInternado(internado);
        }
    }
    
    public boolean excluir(Internado internado) {
        return dao.excluir(internado);
    }
    
    public List<DiariaInternacao> getDiariasDaInternacao(int idInternacao) {
        return dao.getDiariasDaInternacao(idInternacao);
    }
    
    public boolean salvarOuAtualizarDiaria(Internado internado, DiariaInternacao diaria) {
        //Tenho que testar se é uma inclusão ou alteração
        if (diaria.getId() <= 0) {
            //É inclusão
            return dao.inserirDiaria(internado, diaria);
        } else {
            //È alteração
            return dao.editarDiaria(internado, diaria);
        }
    }
    
}
