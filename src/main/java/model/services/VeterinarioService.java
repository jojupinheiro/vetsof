package model.services;

import java.util.List;
import model.classes.Clinica;
import model.classes.Veterinario;
import model.dao.VeterinarioDAO;
import model.db.DB;

/**
 *
 * @author joaoj
 */
public class VeterinarioService {
    private VeterinarioDAO dao;

    public VeterinarioService() {
        dao = new VeterinarioDAO(DB.getConnection());
    }
    
    public List<Veterinario> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }

    public List<Clinica> getClinicasDoVeterinario(int idVeterinario) {
        return dao.getClinicasDoVeterinario(idVeterinario);
    }
    
    public boolean salvarOuAtualizar(Veterinario veterinario) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (veterinario.getId()<= 0) {
            //é inclusão
            return dao.inserir(veterinario);

        } else {
            // é uma alteração
            return dao.editar(veterinario);
        }
    }
    
    public boolean inserirListaClinicas(Veterinario veterinario){
        return dao.inserirListaClinicas(veterinario);
    }
    
    public boolean excluir(Veterinario veterinario){
        return dao.excluir(veterinario);
    }
}
