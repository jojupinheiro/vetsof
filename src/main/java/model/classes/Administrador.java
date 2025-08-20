/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

/**
 *
 * @author juliano
 */
public class Administrador extends Usuario{
    
    private String cargo;

    public Administrador(String cargo, int idUsuario, String nomeUsuario, String senha, String email, boolean tipoUsuario, String login) {
        super(idUsuario, nomeUsuario, senha, email, tipoUsuario, login);
        this.cargo = cargo;
    }

    public Administrador(String cargo, String nomeUsuario, String senha, String email, boolean tipoUsuario, String login) {
        super(nomeUsuario, senha, email, tipoUsuario, login);
        this.cargo = cargo;
    }


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Administrador{" + "idFuncional=" + ", cargo=" + cargo + '}';
    }
    
    
    
}
