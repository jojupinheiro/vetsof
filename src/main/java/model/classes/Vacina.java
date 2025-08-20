package model.classes;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe destinada a cadastrar os episódios de aplicação de vacinas nos animais
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class Vacina {
    private int id;
    private ProdutoVacina produtoVacina;
    private Pet pet;
    private Atendimento atendimento;
    private LocalDate dtVacina;
    private boolean aplicada;
    private String observacao;
    private int doseAtual;
    private int dosesTotais;
    private boolean temProximaDose;
    private LocalDate dtProximaDose;
    private float valor;

    public Vacina(int id, ProdutoVacina produtoVacina, Pet pet, Atendimento atendimento, LocalDate dtVacina, boolean aplicada, String observacao, int doseAtual, int dosesTotais, boolean temProximaDose, LocalDate dtProximaDose, float valor) {
        this.id = id;
        this.produtoVacina = produtoVacina;
        this.pet = pet;
        this.atendimento = atendimento;
        this.dtVacina = dtVacina;
        this.aplicada = aplicada;
        this.observacao = observacao;
        this.doseAtual = doseAtual;
        this.dosesTotais = dosesTotais;
        this.temProximaDose = temProximaDose;
        this.dtProximaDose = dtProximaDose;
        this.valor = valor;
    }

    public Vacina(ProdutoVacina produtoVacina, Pet pet, Atendimento atendimento, LocalDate dtVacina, boolean aplicada, String observacao, int doseAtual, int dosesTotais, boolean temProximaDose, LocalDate dtProximaDose, float valor) {
        this.produtoVacina = produtoVacina;
        this.pet = pet;
        this.atendimento = atendimento;
        this.dtVacina = dtVacina;
        this.aplicada = aplicada;
        this.observacao = observacao;
        this.doseAtual = doseAtual;
        this.dosesTotais = dosesTotais;
        this.temProximaDose = temProximaDose;
        this.dtProximaDose = dtProximaDose;
        this.valor = valor;
    }

    public Vacina(ProdutoVacina produtoVacina, LocalDate dtVacina, boolean aplicada, String observacao, int doseAtual, int dosesTotais, boolean temProximaDose, LocalDate dtProximaDose, float valor) {
        this.produtoVacina = produtoVacina;
        this.dtVacina = dtVacina;
        this.aplicada = aplicada;
        this.observacao = observacao;
        this.doseAtual = doseAtual;
        this.dosesTotais = dosesTotais;
        this.temProximaDose = temProximaDose;
        this.dtProximaDose = dtProximaDose;
        this.valor = valor;
    }

    public Vacina(ProdutoVacina produtoVacina, boolean aplicada, String observacao, int doseAtual, int dosesTotais, boolean temProximaDose, LocalDate dtProximaDose, float valor) {
        this.produtoVacina = produtoVacina;
        this.aplicada = aplicada;
        this.observacao = observacao;
        this.doseAtual = doseAtual;
        this.dosesTotais = dosesTotais;
        this.temProximaDose = temProximaDose;
        this.dtProximaDose = dtProximaDose;
        this.valor = valor;
    }

    public Vacina(int id, ProdutoVacina produtoVacina, Pet pet, LocalDate dtVacina, boolean aplicada, String observacao, int doseAtual, int dosesTotais, boolean temProximaDose, LocalDate dtProximaDose, float valor) {
        this.id = id;
        this.produtoVacina = produtoVacina;
        this.pet = pet;
        this.dtVacina = dtVacina;
        this.aplicada = aplicada;
        this.observacao = observacao;
        this.doseAtual = doseAtual;
        this.dosesTotais = dosesTotais;
        this.temProximaDose = temProximaDose;
        this.dtProximaDose = dtProximaDose;
        this.valor = valor;
    }

    public Vacina(int id, ProdutoVacina produtoVacina, LocalDate dtVacina, boolean aplicada, String observacao, int doseAtual, int dosesTotais, boolean temProximaDose, LocalDate dtProximaDose, float valor) {
        this.id = id;
        this.produtoVacina = produtoVacina;
        this.dtVacina = dtVacina;
        this.aplicada = aplicada;
        this.observacao = observacao;
        this.doseAtual = doseAtual;
        this.dosesTotais = dosesTotais;
        this.temProximaDose = temProximaDose;
        this.dtProximaDose = dtProximaDose;
        this.valor = valor;
    }
    
    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public ProdutoVacina getProdutoVacina() {
        return produtoVacina;
    }

    public void setProdutoVacina(ProdutoVacina produtoVacina) {
        this.produtoVacina = produtoVacina;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDtVacina() {
        return dtVacina;
    }

    public void setDtVacina(LocalDate dtVacina) {
        this.dtVacina = dtVacina;
    }

    public boolean isAplicada() {
        return aplicada;
    }

    public void setAplicada(boolean aplicada) {
        this.aplicada = aplicada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getDoseAtual() {
        return doseAtual;
    }

    public void setDoseAtual(int doseAtual) {
        this.doseAtual = doseAtual;
    }

    public int getDosesTotais() {
        return dosesTotais;
    }

    public void setDosesTotais(int dosesTotais) {
        this.dosesTotais = dosesTotais;
    }

    public boolean isTemProximaDose() {
        return temProximaDose;
    }

    public void setTemProximaDose(boolean temProximaDose) {
        this.temProximaDose = temProximaDose;
    }

    public LocalDate getDtProximaDose() {
        return dtProximaDose;
    }

    public void setDtProximaDose(LocalDate dtProximaDose) {
        this.dtProximaDose = dtProximaDose;
    }

    @Override
    public String toString() {
        return produtoVacina.getNomeVacina();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.produtoVacina);
        hash = 13 * hash + Objects.hashCode(this.pet);
        hash = 13 * hash + Objects.hashCode(this.atendimento);
        hash = 13 * hash + Objects.hashCode(this.dtVacina);
        hash = 13 * hash + (this.aplicada ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.observacao);
        hash = 13 * hash + this.doseAtual;
        hash = 13 * hash + this.dosesTotais;
        hash = 13 * hash + (this.temProximaDose ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.dtProximaDose);
        hash = 13 * hash + Float.floatToIntBits(this.valor);
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
        final Vacina other = (Vacina) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.aplicada != other.aplicada) {
            return false;
        }
        if (this.doseAtual != other.doseAtual) {
            return false;
        }
        if (this.dosesTotais != other.dosesTotais) {
            return false;
        }
        if (this.temProximaDose != other.temProximaDose) {
            return false;
        }
        if (Float.floatToIntBits(this.valor) != Float.floatToIntBits(other.valor)) {
            return false;
        }
        if (!Objects.equals(this.observacao, other.observacao)) {
            return false;
        }
        if (!Objects.equals(this.produtoVacina, other.produtoVacina)) {
            return false;
        }
        if (!Objects.equals(this.pet, other.pet)) {
            return false;
        }
        if (!Objects.equals(this.atendimento, other.atendimento)) {
            return false;
        }
        if (!Objects.equals(this.dtVacina, other.dtVacina)) {
            return false;
        }
        return Objects.equals(this.dtProximaDose, other.dtProximaDose);
    }

}
