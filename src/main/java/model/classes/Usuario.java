/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

/**
 *
 * @author juliano
 */
public class Usuario {

    private int idUsuario;
    private String nomeUsuario, senha, email, login;
    private boolean tipoUsuario;

    public Usuario(int idUsuario, String nomeUsuario, String senha, String email, boolean tipoUsuario, String login) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.login = login;
    }

    public Usuario(String nomeUsuario, String senha, String email, boolean tipoUsuario, String login) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.login = login;

    }

    public Usuario(String login, String senha) {
//        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.login = login;

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoUsuario() {
        if (tipoUsuario) {
            return "Administrador";
        } else {
            return "Comum";
        }
    }

    public boolean isTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(boolean tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nomeUsuario=" + nomeUsuario + ", senha=" + senha + ", email=" + email + ", tipoUsuario=" + tipoUsuario + '}';
    }

}
