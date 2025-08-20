/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

/**
 *
 * @author juliano
 */
public class Funcionario extends Usuario{

    public Funcionario(int idUsuario, String nomeUsuario, String senha, String email, boolean tipoUsuario, String login) {
        super(idUsuario, nomeUsuario, senha, email, tipoUsuario, login);
    }

    public Funcionario(String nomeUsuario, String senha, String email, boolean tipoUsuario, String login) {
        super(nomeUsuario, senha, email, tipoUsuario, login);
    }
    
}
