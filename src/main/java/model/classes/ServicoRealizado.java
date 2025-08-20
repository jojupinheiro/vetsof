/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

/**
 *
 * @author juliano
 */
public class ServicoRealizado {
    int id;
    int quantidade;
    Servico servico;
    float valor;
    String observacao;
    Atendimento atendimento;
    Pet pet;

    public ServicoRealizado(int id, Servico servico, float valor, String observacao, Atendimento atendimento, int quantidade) {
        this.id = id;
        this.servico = servico;
        this.valor = valor;
        this.observacao = observacao;
        this.atendimento = atendimento;
        this.quantidade = quantidade;
    }

    public ServicoRealizado(Servico servico, float valor, String observacao, Atendimento atendimento, int quantidade) {
        this.servico = servico;
        this.valor = valor;
        this.observacao = observacao;
        this.atendimento = atendimento;
        this.quantidade = quantidade;
    }

    public ServicoRealizado(Servico servico, float valor, String observacao, int quantidade) {
        this.servico = servico;
        this.valor = valor;
        this.observacao = observacao;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    @Override
    public String toString() {
        return servico.getNomeServico();
    }
    
}
