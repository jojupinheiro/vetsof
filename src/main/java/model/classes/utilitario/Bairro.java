/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes.utilitario;

/**
 *
 * @author joaoj
 */
public class Bairro {
    private int id;
    private String nome;
    private Municipio municipio;

    public Bairro(String nome, Municipio municipio) {
        this.nome = nome;
        this.municipio = municipio;
    }

    public Bairro(int id, String nome, Municipio municipio) {
        this.id = id;
        this.nome = nome;
        this.municipio = municipio;
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

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}
