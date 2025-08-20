package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.classes.Administrador;
import model.classes.Clinica;
import model.classes.Funcionario;
import model.classes.Servico;
import model.classes.Usuario;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ServicoDAO {

    private Connection con;

    public ServicoDAO(Connection con) {
        this.con = con;
    }

    public List<Servico> getAll() {
        List<Servico> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * from servico order by nome_servico";
            stmt = con.prepareStatement(sql);

            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos atendimento
                int IdServico = res.getInt("pk_idservico");
                String nomeServico = res.getString("nome_servico");
                String descricao = res.getString("descricao_servico");
                float valorServico = res.getFloat("valor_servico");

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
                //Cria o objeto Atendimento
                Servico servico = new Servico(IdServico, nomeServico, valorServico, descricao);

                //Adiciona o objeto Serviço na lista de Serviços
                list.add(servico);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public List<Servico> getServicoDoAtendimento(int idAtendimento) {
        List<Servico> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "select * from servico\n"
                    + "join atendimento_servico on (servico.pk_idservico = atendimento_servico.fk_idservico)\n"
                    + "join atendimento on (atendimento_servico.fk_idatendimento = atendimento.pk_idatendimento)\n"
                    + "where atendimento.pk_idatendimento = ?";
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idAtendimento);

            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos atendimento
                int IdServico = res.getInt("pk_idservico");
                String nomeServico = res.getString("nome_servico");
                String descricao = res.getString("descricao_servico");
                float valor = res.getFloat("valor_servico");

                //Cria o objeto Exame
                Servico servico = new Servico(IdServico, nomeServico, valor, descricao);

                //Adiciona o objeto Serviço na lista de Serviços
                list.add(servico);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(Servico servico) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO servico (nome_servico, valor_servico, descricao_servico)\n"
                    + "VALUES (?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setString(1, servico.getNomeServico());
            stmt.setFloat(2, servico.getValorServico());
            stmt.setString(3, servico.getDescricaoServico());

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
                    servico.setIdServico(id);
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
    
//    public boolean inserirServicosNoAtendimento(List listServico) {
//        PreparedStatement stmt = null;
//        boolean result = false;
//        try {
//            // String SQL para INSERIR
//            String sql = "INSERT INTO servico (nomeservico, valor, descricao)\n"
//                    + "VALUES (?,?,?)";
//            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
//            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            //Trocando os ???
//
//            stmt.setString(1, servico.getNomeServico());
//            stmt.setFloat(2, servico.getValorServico());
//            stmt.setString(3, servico.getDescricaoServico());
//
//            // Executar o scipt
//            int rowsAffected = stmt.executeUpdate();
//            if (rowsAffected > 0) {
//                // Deu certo
//                // Pegando o código gerado no insert
//                ResultSet rs = stmt.getGeneratedKeys();
//                if (rs.next()) {
//                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
//                    int id = rs.getInt(1);
//                    //Atualiza o ID da clínica no parâmetro que foi recebido pelo método
//                    servico.setIdServico(id);
//                    result = true;
//                    //Depois daqui vai para o finally
//                }
//            } else {
//                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
//                throw new SQLException("Não foi possível inserir");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            DB.closeStatement(stmt);
//            return result;
//        }
//    }

    public boolean excluir(Servico servico) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from servico where pk_idservico = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, servico.getIdServico());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public Servico getServico(String nomeServico) {
        Servico servico = null;
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * from servico WHERE nome_servico = ? order by nome_servico";
            stmt = con.prepareStatement(sql);

            stmt.setString(1, nomeServico);

            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos atendimento
                int idServico = res.getInt("pk_idservico");
                String descricao = res.getString("descricao_servico");
                float valorServico = res.getFloat("valor_servico");
                servico = new Servico(idServico, nomeServico, valorServico, descricao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return servico;
        }
    }

    public boolean editar(Servico servico) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE servico SET valor_servico = ?, descricao_servico = ?, nome_servico = ? WHERE pk_idservico = ?;";
            String descricao = servico.getDescricaoServico();
            String nomeServico = servico.getNomeServico();
            float valorServico = servico.getValorServico();
            int idServico = servico.getIdServico();

            stmt = con.prepareStatement(sql);
            stmt.setFloat(1, valorServico);
            stmt.setString(2, descricao);
            stmt.setString(3, nomeServico);
            stmt.setInt(4, idServico);

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
