package model.services;

import java.util.List;
import java.util.Map;
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Municipio;
import model.classes.utilitario.ValorPadrao;
import model.dao.UtilitarioDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class UtilitarioService {
    private UtilitarioDAO dao;

    public UtilitarioService() {
        dao = new UtilitarioDAO(DB.getConnection());
    }

    public List<Municipio> getMunicipios() {
        return dao.getMunicipios();
    }

    public List<Bairro> getBairros(Municipio municipio) {
        return dao.getBairros(municipio);
    }

    public boolean excluirMunicipio(Municipio municipio) {
        return dao.excluirMunicipio(municipio);
    }
    
    public boolean excluirBairro(Bairro bairro) {
        return dao.excluirBairro(bairro);
    }
    
    public boolean inserirOuAtualizarMunicipio(Municipio municipio) {
        if (municipio.getId() > 0){
            return dao.editarMunicipio(municipio);
        }else{
            return dao.inserirMunicipio(municipio);
        }
    }
    
    public boolean inserirOuAtualizarBairro(Bairro bairro) {
        if (bairro.getId() > 0){
            return dao.editarBairro(bairro);
        }else{
            return dao.inserirBairro(bairro);
        }
    }
    
    public String getEstiloAtivo(){
        return dao.getEstiloAtivo();
    }
    
    public boolean trocaEstiloAtivo(String estilo){
        return dao.trocaEstiloAtivo(estilo);
    }
    
    public Map<Integer, Integer> getPreferencias(){
        return dao.getPreferencias();
    }
    
    public boolean atualizarPreferencias(Map<Integer, Integer> preferencias){
        return dao.atualizarPreferencias(preferencias);
    }
    
    public List<ValorPadrao> getValoresPadrao(){
        return dao.getValoresPadrao();
    }
    
    public boolean atualizarValoresPadrao(List<ValorPadrao> valoresPadrao){
        return dao.atualizarValoresPadrao(valoresPadrao);
    }
}
