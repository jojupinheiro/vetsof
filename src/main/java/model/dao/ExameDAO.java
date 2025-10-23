package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.classes.Exame;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ExameDAO {

    private Connection con;

    public ExameDAO(Connection con) {
        this.con = con;
    }

    public List<Exame> getAll() {
        List<Exame> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * from exame order by nome_exame";
            stmt = con.prepareStatement(sql);

            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos atendimento
                int IdExame = res.getInt("pk_idexame");
                String nomeExame = res.getString("nome_exame");
                String descricao = res.getString("descricao_exame");
                float valorExame = res.getFloat("valor_exame");

                //Atributos Usuario
//                int idUsuario = res.getInt("pk_idusuario");
//                String nome = res.getString("nomeusuario");
//                String senha = res.getString("senha");
//                String emailUsuario = res.getString("emailUsuario");
//                String cargo = res.getString("cargo");
//                int idFuncional = res.getInt("idfuncional");
//                boolean tipoUsuario = res.getBoolean("tipousuario");
//                Usuario usuario;
//                if(tipoUsuario == true){
//                    usuario = new Administrador(idFuncional, cargo, idUsuario, nome, senha, emailUsuario, tipoUsuario);
//                }else{
//                    usuario = new Funcionario(idUsuario, nome, senha, emailUsuario, tipoUsuario);
//                }
                //Cria o objeto Exame
                Exame exame = new Exame(IdExame, nomeExame, valorExame, descricao);

                //Adiciona o objeto Serviço na lista de Serviços
                list.add(exame);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public List<Exame> getExameDoAtendimento(int idAtendimento) {
        List<Exame> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select * from exame\n"
                    + "LEFT join atendimento_exame on (exame.pk_idexame = atendimento_exame.fk_idexame)\n"
                    + "LEFT join atendimento on (atendimento_exame.fk_idatendimento = atendimento.pk_idatendimento)\n"
                    + "where atendimento.pk_idatendimento = ?";
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idAtendimento);
            
            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos atendimento
                int IdExame = res.getInt("pk_idexame");
                String nomeExame = res.getString("nome_exame");
                String descricao = res.getString("descricao_exame");
                float valorExame = res.getFloat("valor_exame");

                //Cria o objeto Exame
                Exame exame = new Exame(IdExame, nomeExame, valorExame, descricao);

                //Adiciona o objeto Serviço na lista de Serviços
                list.add(exame);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(Exame exame) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO exame (nome_exame, valor_exame, descricao_exame)\n"
                    + "VALUES (?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setString(1, exame.getNomeExame());
            stmt.setFloat(2, exame.getValorExame());
            stmt.setString(3, exame.getDescricaoExame());

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID da clínica no parâmetro que foi recebido pelo método
                    exame.setIdExame(id);
                    result = true;
                    //Depois daqui vai para o finally
                }
            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean excluir(Exame exame) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from exame where pk_idexame = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, exame.getIdExame());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public Exame getExame(String nomeExame) {
        Exame exame = null;
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * from exame WHERE nome_exame = ? order by nome_exame";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, nomeExame);

            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos atendimento
                int idExame = res.getInt("pk_idexame");
                String descricao = res.getString("descricao_exame");
                float valorExame = res.getFloat("valor_exame");
                exame = new Exame(idExame, nomeExame, valorExame, descricao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return exame;
        }
    }

    public boolean editar(Exame exame) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE exame SET valor_exame = ?, descricao_exame = ?, nome_exame = ? WHERE pk_idexame = ?;";
            String descricao = exame.getDescricaoExame();
            String nomeExame = exame.getNomeExame();
            float valorExame = exame.getValorExame();
            int idExame = exame.getIdExame();

            stmt = con.prepareStatement(sql);
            stmt.setFloat(1, valorExame);
            stmt.setString(2, descricao);
            stmt.setString(3, nomeExame);
            stmt.setInt(4, idExame);

            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
}
