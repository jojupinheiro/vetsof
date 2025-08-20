package model.services;

import java.util.List;
import model.classes.Clinica;
import model.classes.Veterinario;
import model.dao.ClinicaDAO;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class ClinicaService {
    private ClinicaDAO dao;

    public ClinicaService() {
        dao = new ClinicaDAO(DB.getConnection());
    }
    
    public List<Clinica> getAll(int filtroSelecionado, String txtFiltro){
        return dao.getAll(filtroSelecionado, txtFiltro);
    }
    
    public Clinica getClinicaPrincipal() {
        return dao.getClinicaPrincipal();
    }
    
    public List<Veterinario> getVeterinariosDaClinica(int idClinica) {
        return dao.getVeterinariosDaClinica(idClinica);
    }
    
    public List<Veterinario> getVeterinariosDaClinica(int idClinica, int idVeterinario) {
        return dao.getVeterinariosDaClinica(idClinica, idVeterinario);
    }
    
    public Veterinario getVetResponsavelDaClinica(int idClinica){
        return dao.getVetResponsavelDaClinica(idClinica);
    }
    
    public boolean salvarOuAtualizar(Clinica clinica){
        //Tenho que testar se é uma inclusão ou alteração
        if (clinica.getIdClinica()<= 0){
            //É inclusão
            return dao.inserir(clinica);
        }else {
            //È alteração
            return dao.editar(clinica);
        }
    }
    
    public boolean excluir(Clinica clinica){
        return dao.excluir(clinica);
    }
    
    public boolean excluirVeterinariosDaClinica(Clinica clinica) {
        return dao.excluirVeterinariosDaClinica(clinica);
    }
    
    public boolean inserirVeterinariosNaClinica(List<Veterinario> list, int idClinica) {
        return dao.inserirVeterinariosNaClinica(list, idClinica);
    }
    
}