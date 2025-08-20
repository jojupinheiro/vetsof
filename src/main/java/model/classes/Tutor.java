package model.classes;

import model.classes.utilitario.Municipio;
import model.classes.utilitario.Bairro;
import java.time.LocalDate;
import view.utils.Utils;

/**
 *
 * @author juliano
 */
public class Tutor {
    private int idTutor;
    private String cpf;
    private String nome;
    private String rua;
    private Bairro bairro;
    private String numero;
    private String cep;
    private Municipio municipio;
    private String tipoTutor; //Atributo que indica se é pessoa fisica, ONG, etc.
    private String telefoneTutor;
    private String telefoneAlternativoTutor;
    private String emailTutor;    
    private String observacaoTutor;    
    private int faixaRenda;
    private boolean sexo;
    private LocalDate dtNasc;

    public Tutor() {
    }

    public Tutor(int idTutor, String cpf, String nome, String rua, Bairro bairro, String numero, String cep, Municipio municipio, String tipoTutor, 
            String telefoneTutor, String telefoneAlternativoTutor, String emailTutor, String observacaoTutor, int faixaRenda, boolean sexo, LocalDate dtNasc) {
        this.idTutor = idTutor;
        this.cpf = cpf;
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
        this.municipio = municipio;
        this.tipoTutor = tipoTutor;
        this.telefoneTutor = telefoneTutor;
        this.telefoneAlternativoTutor = telefoneAlternativoTutor;
        this.emailTutor = emailTutor;
        this.observacaoTutor = observacaoTutor;
        this.faixaRenda = faixaRenda;
        this.sexo = sexo;
        this.dtNasc = dtNasc;
    }

    public Tutor(String cpf, String nome, String rua, Bairro bairro, String numero, String cep, Municipio municipio, String tipoTutor, String telefoneTutor, 
            String telefoneAlternativoTutor, String emailTutor, String observacaoTutor, int faixaRenda, boolean sexo, LocalDate dtNasc) {
        this.cpf = cpf;
        this.nome = nome;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
        this.municipio = municipio;
        this.tipoTutor = tipoTutor;
        this.telefoneTutor = telefoneTutor;
        this.telefoneAlternativoTutor = telefoneAlternativoTutor;
        this.emailTutor = emailTutor;
        this.observacaoTutor = observacaoTutor;
        this.faixaRenda = faixaRenda;
        this.sexo = sexo;
        this.dtNasc = dtNasc;
    }
    
    

    public LocalDate getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(LocalDate dtNasc) {
        this.dtNasc = dtNasc;
    }

    public int getFaixaRenda() {
        return faixaRenda;
    }

    public void setFaixaRenda(int faixaRenda) {
        this.faixaRenda = faixaRenda;
    }

    public String getSexo(){
        if (sexo == true){
            return "Masculino";
        }else{
            return "Feminino";
        }
    }
    
    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getLogradouro() {
        return rua+", nº "+numero;
    }
    
    public String getEmailTutor() {
        return emailTutor;
    }

    public void setEmailTutor(String emailTutor) {
        this.emailTutor = emailTutor;
    }

    public String getObservacaoTutor() {
        return observacaoTutor;
    }

    public void setObservacaoTutor(String observacaoTutor) {
        this.observacaoTutor = observacaoTutor;
    }

    public String getTipoTutor() {
        return tipoTutor;
    }

    public void setTipoTutor(String tipoTutor) {
        this.tipoTutor = tipoTutor;
    }

    public int getIdTutor() {
        return idTutor;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getRua() {
        return rua;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public String getNumero() {
        return numero;
    }

    public String getCep() {
        return cep;
    }

    public String getTelefoneTutor() {
        return telefoneTutor;
    }

    public String getTelefoneAlternativoTutor() {
        return telefoneAlternativoTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setTelefoneTutor(String telefoneTutor) {
        this.telefoneTutor = telefoneTutor;
    }

    public void setTelefoneAlternativoTutor(String telefoneAlternativoTutor) {
        this.telefoneAlternativoTutor = telefoneAlternativoTutor;
    }

    @Override
    public String toString() {
        return  cpf.length() == 11 ? nome + " - " + Utils.imprimeCPF(cpf) : nome + " - " + Utils.imprimeCNPJ(cpf);
    }

   
}
