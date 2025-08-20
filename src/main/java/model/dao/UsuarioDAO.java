package model.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import model.classes.Administrador;
import model.classes.Funcionario;
import model.classes.Usuario;
import model.db.DB;

//Classe que transforma os registros do banco em objetos, e vice-versa
//DAO - Data Access Object
public class UsuarioDAO {
    private Connection con;

    public UsuarioDAO (Connection con) {
        this.con = con;
    }
    
    public List<Usuario> getAll(int filtroSelecionado, String txtFiltro){
        //lista temporária dos usuários
        List<Usuario> list = new ArrayList<>();
        //listagem dos registros que virão do banco
        ResultSet res = null;
        //um statement é um objeto que executa o script SQL
        PreparedStatement stmt = null;
        
        try{
            String sql = "select * from usuario";
            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            
            //executa o script sql
            //e guarda o resultado dentro do res
            res = stmt.executeQuery();
            //percorrer o res e ir criando objetos
            while(res.next()){
                //Atributos Usuario
                int idUsuario = res.getInt("pk_idusuario");
                String nome = res.getString("nome_usuario");
                String senha = res.getString("senha_usuario");
                String emailUsuario = res.getString("email_usuario");
                String cargo = res.getString("cargo_usuario");
                boolean tipoUsuario = res.getBoolean("tipo_usuario");
                String login = res.getString("login_usuario");
                
                Usuario usuario;
                if(tipoUsuario == true){
                    usuario = new Administrador(cargo, idUsuario, nome, senha, emailUsuario, tipoUsuario, login);
                }else{
                    usuario = new Funcionario(idUsuario, nome, senha, emailUsuario, tipoUsuario, login);
                }
                
                //adicionando esse usuário na lista temporária
                list.add(usuario);
            }
            
        }catch (Exception e){
            //aqui entra quando dá erro
            e.printStackTrace();
        }finally{
            //entra sempre, dando erro ou não
            //fechar conexões e retornar resultados
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return list;
        }         
    }
    
    //método que faz inserções no banco de dados
    public boolean inserir(Usuario usuario){
        PreparedStatement stmt = null;
        boolean result = false;
        try{
            //String SQL para INSERIR
            String sql = "INSERT INTO usuario (nome_usuario, senha_usuario, email_usuario, cargo_usuario, tipo_usuario, login_usuario)  VALUES (?,?,?,?,?,?)";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //trocando os ??????
            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getEmail());
                        
            if(usuario instanceof Administrador){
                Administrador adm = (Administrador) usuario;
                stmt.setString(4, adm.getCargo());
            }else {
                stmt.setString(4, "");
            }
            stmt.setBoolean(5, usuario.isTipoUsuario());
            stmt.setString(6, usuario.getLogin());
               
            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0){
                //deu certo
                //pegando o código gerado no INSERT
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    //getInt 1 pega o código que foi gerado e está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //atualiza o código do Usuário no parâmetro
                    //que foi recebido pelo método
                    usuario.setIdUsuario(id);
                    result = true;
                    //depois daqui vai para o finally
                }
            }else {
                //falhou e vamos gerar uma exception para que o código 
                //caia automaticamente dentro do catch e depois no finally
                throw new SQLException("Não foi possível inserir");
            }
            
        }catch(Exception e){
            e.printStackTrace();                    
        }finally{
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluir(Usuario usuario){
       PreparedStatement stmt = null;
       boolean result = false;
       try{
           String sql = "DELETE FROM usuario WHERE pk_idusuario = ?";
           stmt = con.prepareStatement(sql);
           //troca os parâmetros
           stmt.setInt(1, usuario.getIdUsuario());
           //executa
           stmt.executeUpdate();
           
           result = true;
           
       }catch(Exception e){
           e.printStackTrace();
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Erro de Exclusão");
           alert.setContentText("Não foi possível excluir o usuário!");
           alert.showAndWait();
       } finally{
           DB.closeStatement(stmt);
           return result;
       }
    }
    
    public boolean editar(Usuario usuario){
       PreparedStatement stmt = null;
       boolean result = false;
       try{
           String sql = "UPDATE usuario SET nome_usuario = ?, senha_usuario = ?, email_usuario = ?, cargo_usuario = ?, tipo_usuario = ?, login_usuario = ?  WHERE pk_idusuario = ?";
           stmt = con.prepareStatement(sql);
           //troca os parâmetros
           stmt.setString(1, usuario.getNomeUsuario());
           stmt.setString(2, usuario.getSenha());
           stmt.setString(3, usuario.getEmail());
           stmt.setBoolean(5,usuario.isTipoUsuario());
           
           int tipoUsuario;
           
           if(usuario instanceof Administrador){
               Administrador adm = (Administrador) usuario;
               stmt.setString(4, adm.getCargo());
           }
           stmt.setString(6, usuario.getLogin());
           stmt.setInt(7, usuario.getIdUsuario());
           //executa
           stmt.executeUpdate();
           
           result = true;
           
       }catch(Exception e){
           e.printStackTrace();
       } finally{
           DB.closeStatement(stmt);
           return result;
       }
    }
    
    public Usuario efetuarLogin(Usuario usuario) {
        PreparedStatement stmt = null; // utilizado para executar o script SQL
        Usuario usuarioLogado = null;
        try {
            // preparando a string SQL para a consulta
            String sql = "select * from usuario where BINARY login_usuario = ? and BINARY senha_usuario = ? ";
            stmt = con.prepareStatement(sql);
            // substituindo os ? do script sql pelos parâmetros
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            // executando o select e obtendo o retorno
            ResultSet res = stmt.executeQuery();
            // percorrendo o resultado (retorno)
            while(res.next()) {
                // obtendo as informações (parâmetros) da consulta
                int idUsuario = res.getInt("pk_idusuario");
                String nomeUsuario = res.getString("nome_usuario");
                String senha = res.getString("senha_usuario");
                String emailUsuario = res.getString("email_usuario");
                boolean tipoUsuario = res.getBoolean("tipo_usuario");
                String login = res.getString("login_usuario");
                
                // verificando o tipo de usuario
                if (tipoUsuario) { // Administrador
                    String cargo = res.getString("cargo_usuario");
                    usuarioLogado = new Administrador(cargo, idUsuario, nomeUsuario, senha, emailUsuario, tipoUsuario, login);
                } else {
                    usuarioLogado = new Funcionario(idUsuario, nomeUsuario, senha, emailUsuario, tipoUsuario, login);
                }
            }
            res.close(); // fechando o resultado
            stmt.close(); // fechando o statement
            con.close(); // fechando a conexão com o banco
        }catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            usuarioLogado = null;
        }
        return usuarioLogado;
    }
}


