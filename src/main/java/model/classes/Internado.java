package model.classes;

import java.time.LocalDate;
import java.util.List;
import view.utils.Utils;

/**
 * Classe para adicionar os pacientes internados
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class Internado {
    private int id;
    private Pet pet;
    private Veterinario veterinario;
    private LocalDate dtInternacao;
    private LocalDate dtAlta;
    private float valorDiaria;
    private float valorTotal;
    private String observacoes;
    private List<DiariaInternacao> diaria;
    private boolean internacaoAtiva;

    public Internado(int id, Pet pet, Veterinario veterinario, LocalDate dtInternacao, LocalDate dtAlta, float valorDiaria, 
            float valorTotal, String observacoes, List<DiariaInternacao> diaria, boolean internacaoAtiva) {
        this.id = id;
        this.pet = pet;
        this.veterinario = veterinario;
        this.dtInternacao = dtInternacao;
        this.dtAlta = dtAlta;
        this.valorDiaria = valorDiaria;
        this.valorTotal = valorTotal;
        this.observacoes = observacoes;
        this.diaria = diaria;
        this.internacaoAtiva = internacaoAtiva;
    }

    public Internado(Pet pet, Veterinario veterinario, LocalDate dtInternacao, LocalDate dtAlta, float valorDiaria, 
            float valorTotal, String observacoes, List<DiariaInternacao> diaria, boolean internacaoAtiva) {
        this.pet = pet;
        this.veterinario = veterinario;
        this.dtInternacao = dtInternacao;
        this.dtAlta = dtAlta;
        this.valorDiaria = valorDiaria;
        this.valorTotal = valorTotal;
        this.observacoes = observacoes;
        this.diaria = diaria;
        this.internacaoAtiva = internacaoAtiva;
    }

    public Internado(Pet pet, Veterinario veterinario, LocalDate dtInternacao, LocalDate dtAlta, float valorDiaria, float valorTotal, String observacoes, boolean internacaoAtiva) {
        this.pet = pet;
        this.veterinario = veterinario;
        this.dtInternacao = dtInternacao;
        this.dtAlta = dtAlta;
        this.valorDiaria = valorDiaria;
        this.valorTotal = valorTotal;
        this.observacoes = observacoes;
        this.internacaoAtiva = internacaoAtiva;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalDate getDtInternacao() {
        return dtInternacao;
    }

    public void setDtInternacao(LocalDate dtInternacao) {
        this.dtInternacao = dtInternacao;
    }

    public LocalDate getDtAlta() {
        return dtAlta;
    }

    public void setDtAlta(LocalDate dtAlta) {
        this.dtAlta = dtAlta;
    }

    public float getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(float valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<DiariaInternacao> getDiaria() {
        return diaria;
    }

    public void setDiaria(List<DiariaInternacao> diaria) {
        this.diaria = diaria;
    }

    public boolean isInternacaoAtiva() {
        return internacaoAtiva;
    }
    
    public String getInternacaoAtiva(){
        return internacaoAtiva ? "Sim" : "Não";
    }

    public void setInternacaoAtiva(boolean internacaoAtiva) {
        this.internacaoAtiva = internacaoAtiva;
    }

    public int getNumeroDiarias() {
        return diaria.size();
    }
    
    public Tutor getTutor(){
        return pet.getTutorPet();
//        return pet.getTutorPet() + " - " + Utils.imprimeCPFouCNPJ(pet.getTutorPet().getCpf());
    }
    
    public String getNomePet(){
        return pet.getNomePet();
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
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
        final Internado other = (Internado) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Internado{" + "id=" + id + ", pet=" + pet + ", dtInternacao=" + dtInternacao + ", dtAlta=" + dtAlta + ", valorDiaria=" + valorDiaria + ", valorTotal=" + valorTotal + ", observacoes=" + observacoes + ", diaria=" + diaria + ", internacaoAtiva=" + internacaoAtiva + '}';
    }
    
}
