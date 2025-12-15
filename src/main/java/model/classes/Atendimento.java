package model.classes;

import model.classes.prescricoes.Prescricao;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.services.VacinaService;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */

public class Atendimento {
    private int idAtendimento;
    private Pet pet;
    private Clinica clinica;
    private Veterinario veterinario;
    private Prescricao prescricao;
    private String diagnostico;
    private String anamnese;
    private String tratamento;
    private String exameFisico;
    private LocalDate dataAtendimento;
    private LocalTime horarioAtendimento; 
    private float valorTotal;
    private String descricao;
    private List<ServicoRealizado> listaServico;
    private List<ExameRealizado> listaExames;

    public Atendimento() {
    }

    public Atendimento(int idAtendimento, Pet pet, Clinica clinica,Veterinario veterinario, LocalDate dataAtendimento, LocalTime horarioAtendimento, float valorTotal, 
            String descricao, List<ServicoRealizado> listaServico, List<ExameRealizado> listaExames) {
        this.idAtendimento = idAtendimento;
        this.pet = pet;
        this.clinica = clinica;
        this.veterinario = veterinario;
        this.dataAtendimento = dataAtendimento;
        this.horarioAtendimento = horarioAtendimento;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
        this.listaServico = listaServico;
        this.listaExames = listaExames;
    }

    public Atendimento(int idAtendimento, Pet pet, Clinica clinica, Veterinario veterinario, Prescricao prescricao, String diagnostico, String anamnese, String tratamento, String exameFisico, LocalDate dataAtendimento, LocalTime horarioAtendimento, float valorTotal, String descricao, List<ServicoRealizado> listaServico, List<ExameRealizado> listaExames) {
        this.idAtendimento = idAtendimento;
        this.pet = pet;
        this.clinica = clinica;
        this.veterinario = veterinario;
        this.prescricao = prescricao;
        this.diagnostico = diagnostico;
        this.anamnese = anamnese;
        this.tratamento = tratamento;
        this.exameFisico = exameFisico;
        this.dataAtendimento = dataAtendimento;
        this.horarioAtendimento = horarioAtendimento;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
        this.listaServico = listaServico;
        this.listaExames = listaExames;
    }

    public Atendimento(Pet pet, Clinica clinica, Veterinario veterinario, Prescricao prescricao, String diagnostico, String anamnese, String tratamento, String exameFisico, LocalDate dataAtendimento, LocalTime horarioAtendimento, float valorTotal, String descricao, List<ServicoRealizado> listaServico, List<ExameRealizado> listaExames) {
        this.pet = pet;
        this.clinica = clinica;
        this.veterinario = veterinario;
        this.prescricao = prescricao;
        this.diagnostico = diagnostico;
        this.anamnese = anamnese;
        this.tratamento = tratamento;
        this.exameFisico = exameFisico;
        this.dataAtendimento = dataAtendimento;
        this.horarioAtendimento = horarioAtendimento;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
        this.listaServico = listaServico;
        this.listaExames = listaExames;
    }
    
    public Atendimento(Pet pet, Clinica clinica, LocalDate dataAtendimento, LocalTime horarioAtendimento, float valorTotal, String descricao, List<ServicoRealizado> listaServico, List<ExameRealizado> listaExames) {
        this.pet = pet;
        this.clinica = clinica;
        this.dataAtendimento = dataAtendimento;
        this.horarioAtendimento = horarioAtendimento;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
        this.listaServico = listaServico;
        this.listaExames = listaExames;
    }

    public Atendimento(int idAtendimento, Pet pet, Clinica clinica, LocalDate dataAtendimento, LocalTime horarioAtendimento, float valorTotal, String descricao) {
        this.idAtendimento = idAtendimento;
        this.pet = pet;
        this.clinica = clinica;
        this.dataAtendimento = dataAtendimento;
        this.horarioAtendimento = horarioAtendimento;
        this.valorTotal = valorTotal;
        this.descricao = descricao;
    }

    public Atendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getExameFisico() {
        return exameFisico;
    }

    public void setExameFisico(String exameFisico) {
        this.exameFisico = exameFisico;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
    
    public int getIdAtendimento() {
        return idAtendimento;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDate dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public LocalTime getHorarioAtendimento() {
        return horarioAtendimento;
    }

    public void setHorarioAtendimento(LocalTime horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }

    public List<ServicoRealizado> getListaServico() {
        return listaServico;
    }

    public void setListaServico(List<ServicoRealizado> listaServico) {
        this.listaServico = listaServico;
    }   

    public List<ExameRealizado> getListaExames() {
        return listaExames;
    }

    public void setListaExames(List<ExameRealizado> listaExames) {
        this.listaExames = listaExames;
    }
    
    public Tutor getTutor(){
        return pet.getTutorPet();
    }
    
    public List<Vacina> getListaVacinas(){
        Atendimento atendimento = new Atendimento(idAtendimento);
        return new VacinaService().getVacinasDoAtendimento(atendimento);
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Atendimento{" + "idAtendimento=" + idAtendimento + ", pet=" + pet + ", clinica=" + 
                clinica  + ", dataAtendimento=" + dataAtendimento + ", horarioAtendimento=" + 
                horarioAtendimento + ", valorTotal=" + valorTotal + ", descricao=" + 
                descricao + ", listaServico=" + listaServico + ", listaExames=" + listaExames + '}';
    }
    
    
}