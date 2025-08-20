package model.services;

import java.util.List;
import model.classes.Atendimento;
import model.classes.DiariaInternacao;
import model.classes.Pet;
import model.classes.Vacina;
import model.dao.VacinaDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class VacinaService {

    private VacinaDAO dao;

    public VacinaService() {
        dao = new VacinaDAO(DB.getConnection());
    }

//    public List<Vacina> getAll(int filtroSelecionado, String txtFiltro) {
//        return dao.getAll(filtroSelecionado, txtFiltro);
//    }
    public List<Vacina> getVacinasDoPet(Pet pet) {
        return dao.getVacinasDoPet(pet);
    }

    public List<Vacina> getVacinasDoAtendimento(Atendimento atendimento) {
        return dao.getVacinasDoAtendimento(atendimento);
    }
    
    public List<Vacina> getVacinasDaDiariaDaInternacao(int idDiaria) {
        return dao.getVacinasDaDiariaDaInternacao(idDiaria);
    }

    public boolean salvarOuAtualizar(Vacina vacina) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (vacina.getId() <= 0) {
            //é inclusão
            return dao.inserir(vacina);

        } else {
            // é uma alteração
            return dao.editar(vacina);
        }
    }
    
    public boolean inserir(Vacina vacina) {
        return dao.inserir(vacina);
    }

    public boolean excluir(Vacina vacina) {
        return dao.excluir(vacina);
    }
    
    public boolean excluirVacinaDoAtendimento(Atendimento atendimento) {
        return dao.excluirVacinaDoAtendimento(atendimento);
    }

}
