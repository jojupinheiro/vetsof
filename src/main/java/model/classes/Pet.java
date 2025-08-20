package model.classes;

import model.classes.utilitario.Raca;
import model.classes.utilitario.Especie;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class Pet {

    private int idPet;
    private String nomePet;
    private Raca raca;
    private double pesoPet;
    private Boolean sexoPet;
    private String rfid;
    private String observacao;
    private boolean castrado; //Indica se está castrado ou não
    private boolean adotado;
    private LocalDate dataNascimentoPet;
    private Tutor tutorPet;
    private boolean vivo; //Indica se está vivo ou morto
    private List<Vacina> listaVacinas;
    private List<String> listaTemperamento;

    public Pet(int idPet, String nomePet, Raca raca, double pesoPet, boolean sexoPet, String rfid, String observacao, boolean castrado, boolean adotado, LocalDate dataNascimentoPet, 
            Tutor tutorPet, boolean vivo, List<String> listaTemperamento) {
        this.idPet = idPet;
        this.nomePet = nomePet;
        this.raca = raca;
        this.pesoPet = pesoPet;
        this.sexoPet = sexoPet;
        this.rfid = rfid;
        this.observacao = observacao;
        this.castrado = castrado;
        this.adotado = adotado;
        this.dataNascimentoPet = dataNascimentoPet;
        this.tutorPet = tutorPet;
        this.vivo = vivo;
        this.listaTemperamento = listaTemperamento;
    }

    public Pet(String nomePet, Raca raca, double pesoPet, boolean sexoPet, String rfid, String observacao, boolean castrado, boolean adotado, LocalDate dataNascimentoPet, Tutor tutorPet, boolean vivo, List<String> listaTemperamento) {
        this.nomePet = nomePet;
        this.raca = raca;
        this.pesoPet = pesoPet;
        this.sexoPet = sexoPet;
        this.rfid = rfid;
        this.observacao = observacao;
        this.castrado = castrado;
        this.adotado = adotado;
        this.dataNascimentoPet = dataNascimentoPet;
        this.tutorPet = tutorPet;
        this.vivo = vivo;
        this.listaTemperamento = listaTemperamento;
    }

    public boolean isAdotado() {
        return adotado;
    }

    public String getAdotado() {
        if (adotado) {
            return "Sim";
        } else {
            return "Não";
        }
    }

    public void setAdotado(boolean adotado) {
        this.adotado = adotado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getVivo() {
        if (vivo) {
            return "Vivo";
        } else {
            return "Morto";
        }
    }

    public void setStatus(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isSexoPet() {
        return sexoPet;
    }

    public void setSexoPet(boolean sexoPet) {
        this.sexoPet = sexoPet;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public Pet() {
    }

    public String getCastrado() {
        return castrado ? "Sim" : "Não";
    }

    public String getSexoPet() {
        return sexoPet ? "Macho" : "Fêmea";
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }

    public LocalDate getDataNascimentoPet() {
        return dataNascimentoPet;
    }

    public void setDataNascimentoPet(LocalDate dataNascimentoPet) {
        this.dataNascimentoPet = dataNascimentoPet;
    }

    public boolean isCastrado() {
        return castrado;
    }

    public int getIdPet() {
        return idPet;
    }

    public String getNomePet() {
        return nomePet;
    }

    public Raca getRaca() {
        return raca;
    }
    
    public Especie getEspecie() {
        return raca.getEspecie();
    }

    public double getPesoPet() {
        return pesoPet;
    }

    public Tutor getTutorPet() {
        return tutorPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public void setNomePet(String nomePet) {
        this.nomePet = nomePet;
    }

    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public void setPesoPet(double pesoPet) {
        this.pesoPet = pesoPet;
    }

    public void setTutorPet(Tutor tutorPet) {
        this.tutorPet = tutorPet;
    }

    @Override
    public String toString() {
        return  nomePet;
    }

    public String getNomeTutor() {
        return tutorPet.getNome();
    }

    public List<Vacina> getListaVacinas() {
        return listaVacinas;
    }

    public void setListaVacinas(List<Vacina> listaVacinas) {
        this.listaVacinas = listaVacinas;
    }

    public List<String> getListaTemperamento() {
        return listaTemperamento;
    }

    public void setListaTemperamento(List<String> listaTemperamento) {
        this.listaTemperamento = listaTemperamento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.idPet;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pet other = (Pet) obj;
        return this.idPet == other.idPet;
    }

    
    
}
