/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes.utilitario;

/**
 *
 * @author joaoj
 */
public class Raca {
    private int id;
    private String nome;
    private Especie especie;

    public Raca(int id, String nome, Especie especie) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
    }

    public Raca(String nome, Especie especie) {
        this.nome = nome;
        this.especie = especie;
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

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
