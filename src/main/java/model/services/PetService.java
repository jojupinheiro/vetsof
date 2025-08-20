package model.services;

import java.util.List;
import model.classes.utilitario.Especie;
import model.classes.Pet;
import model.classes.utilitario.Raca;
import model.classes.Usuario;
import model.dao.PetDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class PetService {

    private PetDAO dao;

    public PetService() {
        dao = new PetDAO(DB.getConnection());
    }

    public List<Pet> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }

    public List<Especie> getEspecies() {
        return dao.getEspecies();
    }
    
    public boolean inserirEspecie(Especie especie){
        return dao.inserirEspecie(especie);
    }
    
    public boolean excluirEspecie(Especie especie){
        return dao.excluirEspecie(especie);
    }
    
    public boolean inserirRaca(Raca raca){
        return dao.inserirRaca(raca);
    }
    
    public boolean excluirRaca(Raca raca){
        return dao.excluirRaca(raca);
    }

    public List<Raca> getRacas(Especie especie) {
        return dao.getRacas(especie);
    }

    public boolean salvarOuAtualizar(Pet pet) {
        //Tenho que testar se é uma inclusão ou alteração
        if (pet.getIdPet() <= 0) {
            //É inclusão
            return dao.inserir(pet);
        } else {
            //È alteração
            return dao.editar(pet);
        }
    }

    public boolean excluir(Pet pet) {
        return dao.excluir(pet);
    }

}
