package model.classes;

import model.classes.utilitario.Municipio;
import model.classes.utilitario.Bairro;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author juliano
 */
public class Clinica {

    private int idClinica;
    private String nomeClinica;
    private String cnpj;
    private String emailClinica;
    private Veterinario veterinarioClinica;
    private String ruaClinica;
    private Bairro bairroClinica;
    private String numeroClinica;
    private String cepClinica;
    private Municipio municipioClinica;
    private String telefoneClinica;
    private String telefoneAlternativoClinica;
    private String observacaoClinica;
    private LocalDate dataCadastro;
    private List<Veterinario> listaVeterinarios;
    String razaoSocial;

    public Clinica(int idClinica, String nomeClinica, String cnpj, String emailClinica, Veterinario veterinarioClinica, String ruaClinica, Bairro bairroClinica, String numeroClinica, 
            String cepClinica, Municipio municipioClinica, String telefoneClinica, String telefoneAlternativoClinica, String observacaoClinica, LocalDate dataCadastro, List<Veterinario> listaVeterinarios) {
        this.idClinica = idClinica;
        this.nomeClinica = nomeClinica;
        this.cnpj = cnpj;
        this.emailClinica = emailClinica;
        this.veterinarioClinica = veterinarioClinica;
        this.ruaClinica = ruaClinica;
        this.bairroClinica = bairroClinica;
        this.numeroClinica = numeroClinica;
        this.cepClinica = cepClinica;
        this.municipioClinica = municipioClinica;
        this.telefoneClinica = telefoneClinica;
        this.telefoneAlternativoClinica = telefoneAlternativoClinica;
        this.observacaoClinica = observacaoClinica;
        this.dataCadastro = dataCadastro;
        this.listaVeterinarios = listaVeterinarios;
    }

    public Clinica(String nomeClinica, String cnpj, String emailClinica, Veterinario veterinarioClinica, String ruaClinica, Bairro bairroClinica, String numeroClinica, String cepClinica, 
            Municipio municipioClinica, String telefoneClinica, String telefoneAlternativoClinica, String observacaoClinica, LocalDate dataCadastro, List<Veterinario> listaVeterinarios) {
        this.nomeClinica = nomeClinica;
        this.cnpj = cnpj;
        this.emailClinica = emailClinica;
        this.veterinarioClinica = veterinarioClinica;
        this.ruaClinica = ruaClinica;
        this.bairroClinica = bairroClinica;
        this.numeroClinica = numeroClinica;
        this.cepClinica = cepClinica;
        this.municipioClinica = municipioClinica;
        this.telefoneClinica = telefoneClinica;
        this.telefoneAlternativoClinica = telefoneAlternativoClinica;
        this.observacaoClinica = observacaoClinica;
        this.dataCadastro = dataCadastro;
        this.listaVeterinarios = listaVeterinarios;
    }

    public Clinica(int idClinica, String nomeClinica, String cnpj, String emailClinica, String ruaClinica, Bairro bairroClinica, String numeroClinica, String cepClinica, 
            Municipio municipioClinica, String telefoneClinica, String telefoneAlternativoClinica, String observacaoClinica, LocalDate dataCadastro, List<Veterinario> listaVeterinarios) {
        this.idClinica = idClinica;
        this.nomeClinica = nomeClinica;
        this.cnpj = cnpj;
        this.emailClinica = emailClinica;
        this.ruaClinica = ruaClinica;
        this.bairroClinica = bairroClinica;
        this.numeroClinica = numeroClinica;
        this.cepClinica = cepClinica;
        this.municipioClinica = municipioClinica;
        this.telefoneClinica = telefoneClinica;
        this.telefoneAlternativoClinica = telefoneAlternativoClinica;
        this.observacaoClinica = observacaoClinica;
        this.dataCadastro = dataCadastro;
        this.listaVeterinarios = listaVeterinarios;
    }

    public Clinica(int idClinica, String nomeClinica, String cnpj, String emailClinica, String ruaClinica, Bairro bairroClinica, String numeroClinica, String cepClinica, 
            Municipio municipioClinica, String telefoneClinica, String telefoneAlternativoClinica, String observacaoClinica, LocalDate dataCadastro) {
        this.idClinica = idClinica;
        this.nomeClinica = nomeClinica;
        this.cnpj = cnpj;
        this.emailClinica = emailClinica;
        this.ruaClinica = ruaClinica;
        this.bairroClinica = bairroClinica;
        this.numeroClinica = numeroClinica;
        this.cepClinica = cepClinica;
        this.municipioClinica = municipioClinica;
        this.telefoneClinica = telefoneClinica;
        this.telefoneAlternativoClinica = telefoneAlternativoClinica;
        this.observacaoClinica = observacaoClinica;
        this.dataCadastro = dataCadastro;
    }

    public Clinica() {
    }

    public List<Veterinario> getListaVeterinarios() {
        return listaVeterinarios;
    }

    public void setListaVeterinarios(List<Veterinario> listaVeterinarios) {
        this.listaVeterinarios = listaVeterinarios;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getObservacaoClinica() {
        return observacaoClinica;
    }

    public void setObservacaoClinica(String observacaoClinica) {
        this.observacaoClinica = observacaoClinica;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Municipio getMunicipioClinica() {
        return municipioClinica;
    }

    public void setMunicipioClinica(Municipio municipioClinica) {
        this.municipioClinica = municipioClinica;
    }

    public int getIdClinica() {
        return idClinica;
    }

    public String getNomeClinica() {
        return nomeClinica;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getEmailClinica() {
        return emailClinica;
    }

    public Veterinario getVeterinarioClinica() {
        return veterinarioClinica;
    }

    public String getRuaClinica() {
        return ruaClinica;
    }

    public Bairro getBairroClinica() {
        return bairroClinica;
    }

    public String getNumeroClinica() {
        return numeroClinica;
    }

    public String getCepClinica() {
        return cepClinica;
    }

    public String getTelefoneClinica() {
        return telefoneClinica;
    }

    public String getTelefoneAlternativoClinica() {
        return telefoneAlternativoClinica;
    }

    public String getLogradouro() {
        return ruaClinica + ", " + numeroClinica;
    }

    public void setIdClinica(int idClinica) {
        this.idClinica = idClinica;
    }

    public void setNomeClinica(String nomeClinica) {
        this.nomeClinica = nomeClinica;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setEmailClinica(String emailClinica) {
        this.emailClinica = emailClinica;
    }

    public void setVeterinarioClinica(Veterinario veterinarioClinica) {
        this.veterinarioClinica = veterinarioClinica;
    }

    public void setRuaClinica(String ruaClinica) {
        this.ruaClinica = ruaClinica;
    }

    public void setBairroClinica(Bairro bairroClinica) {
        this.bairroClinica = bairroClinica;
    }

    public void setNumeroClinica(String numeroClinica) {
        this.numeroClinica = numeroClinica;
    }

    public void setCepClinica(String cepClinica) {
        this.cepClinica = cepClinica;
    }

    public void setTelefoneClinica(String telefoneClinica) {
        this.telefoneClinica = telefoneClinica;
    }

    public void setTelefoneAlternativoClinica(String telefoneAlternativoClinica) {
        this.telefoneAlternativoClinica = telefoneAlternativoClinica;
    }

    @Override
    public String toString() {
        return nomeClinica + " - " + municipioClinica;
    }

}
