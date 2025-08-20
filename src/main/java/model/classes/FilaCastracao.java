/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

import java.time.LocalDate;

/**
 *
 * @author juliano
 */
public class FilaCastracao {
    private Pet pet;
    private LocalDate dataInclusao;
    private int id;
    private int pontuacao;
    private int posicao;

    public FilaCastracao(Pet pet, LocalDate dataInclusao, int id, int pontuacao, int posicao) {
        this.pet = pet;
        this.dataInclusao = dataInclusao;
        this.id = id;
        this.pontuacao = pontuacao;
        this.posicao = posicao;
    }

    public FilaCastracao(Pet pet, LocalDate dataInclusao, int pontuacao, int posicao) {
        this.pet = pet;
        this.dataInclusao = dataInclusao;
        this.pontuacao = pontuacao;
        this.posicao = posicao;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalDate getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDate dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    @Override
    public String toString() {
        return "FilaCastracao{" + "pet=" + pet + ", dataInclusao=" + dataInclusao + ", id=" + id + ", pontuacao=" + pontuacao + ", posicao=" + posicao + '}';
    }
    
    
}
