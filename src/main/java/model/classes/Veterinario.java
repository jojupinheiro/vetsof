package model.classes;

import model.classes.utilitario.Municipio;
import model.classes.utilitario.Bairro;
import java.util.List;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class Veterinario {
    int id;
    String nome;
    String cpf;
    String crmv;
    String email;
    String telefone;
    Municipio municipio;
    Bairro bairro;
    String rua;
    String numero;
    String cep;
    boolean sexo;
    String observacao;
    private List<Clinica> listaClinicas;

    public Veterinario() {
    }

    public Veterinario(int id, String nome, String cpf, String crmv, String email, String telefone, Municipio municipio, Bairro bairro, String rua, String numero, String cep, boolean sexo, String observacao, List<Clinica> listaClinicas) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.crmv = crmv;
        this.email = email;
        this.telefone = telefone;
        this.municipio = municipio;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.sexo = sexo;
        this.observacao = observacao;
        this.listaClinicas = listaClinicas;
    }

    public Veterinario(String nome, String cpf, String crmv, String email, String telefone, Municipio municipio, Bairro bairro, String rua, String numero, String cep, boolean sexo, String observacao, List<Clinica> listaClinicas) {
        this.nome = nome;
        this.cpf = cpf;
        this.crmv = crmv;
        this.email = email;
        this.telefone = telefone;
        this.municipio = municipio;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.sexo = sexo;
        this.observacao = observacao;
        this.listaClinicas = listaClinicas;
    }

    public Veterinario(int id, String nome, String cpf, String crmv, String email, String telefone, Municipio municipio, Bairro bairro, String rua, String numero, String cep, boolean sexo, String observacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.crmv = crmv;
        this.email = email;
        this.telefone = telefone;
        this.municipio = municipio;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cep = cep;
        this.sexo = sexo;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Clinica> getListaClinicas() {
        return listaClinicas;
    }

    public void setListaClinicas(List<Clinica> listaClinicas) {
        this.listaClinicas = listaClinicas;
    }

    @Override
    public String toString() {
        return nome + " - CRMV: " + crmv ;
    }
    
}
