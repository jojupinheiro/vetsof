/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes.utilitario;

/**
 *
 * @author joaoj
 */
public class Municipio {
    private int id;
    private String nome;
    private String estado;

    public Municipio(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public Municipio(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Municipio(int id, String nome, String estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
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
        final Municipio other = (Municipio) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return nome + " - " + estado;
    }
    
}
