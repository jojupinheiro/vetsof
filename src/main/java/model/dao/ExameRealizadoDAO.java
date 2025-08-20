
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.classes.Atendimento;
import model.classes.Exame;
import model.classes.ExameRealizado;
import model.classes.Pet;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class ExameRealizadoDAO {

    private Connection con;

    public ExameRealizadoDAO(Connection con) {
        this.con = con;
    }

    public List<ExameRealizado> getExamesDoPet(Pet pet) {
        List<ExameRealizado> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT er.*, e.*, a.* FROM exame_realizado er "
                    + "JOIN exame e ON (er.fk_idexame_exame_realizado = e.pk_idexame) "
                    + "JOIN atendimento a ON (er.idatendimento_exame_realizado = a.pk_idatendimento) "
                    + "JOIN atendimento a ON (er.idatendimento_exame_realizado = a.pk_idatendimento) "
                    + "WHERE er.fk_idpet_exame_realizado = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, pet.getIdPet());

            res = stmt.executeQuery();
            while (res.next()) {
                
                //Atributo id do atendimento
                int idAtendimento = res.getInt("pk_idatendimento");
                Atendimento atendimento = new Atendimento(idAtendimento);
                
                //Atributos exame
                int idExame = res.getInt("pk_idexame");
                String nomeExame = res.getString("nome_exame");
                String descricaoExame = res.getString("descricao_exame");
                float ValorExame = res.getFloat("valor_exame");
                Exame exame = new Exame(idExame, nomeExame, ValorExame, descricaoExame);
                
                //Atributos ExameRealizado
                int idExameRealizado = res.getInt("pk_idexame_realizado");
                float valorExameRealizado = res.getFloat("valor_exame_realizado");
                String observacaoExameRealizado = res.getString("observacao_exame_realizado");
                String resultadoExameRealizado = res.getString("resultado_exame_realizado");
                ExameRealizado exameRealizado = new ExameRealizado(idExameRealizado, exame, valorExameRealizado, observacaoExameRealizado, 
                        atendimento, resultadoExameRealizado);

                list.add(exameRealizado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<ExameRealizado> getExamesDoAtendimento(int idAtendimento) {
        List<ExameRealizado> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT er.*, e.*, a.* FROM exame_realizado er "
                    + "JOIN exame e ON (er.fk_idexame_exame_realizado = e.pk_idexame) "
                    + "JOIN atendimento a ON (er.idatendimento_exame_realizado = a.pk_idatendimento) "
                    + "WHERE er.idatendimento_exame_realizado = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idAtendimento);

            res = stmt.executeQuery();
            while (res.next()) {
                
                //Criando o atendimento somente com o id
                Atendimento atendimento = new Atendimento(idAtendimento);
                
                //Atributos exame
                int idExame = res.getInt("pk_idexame");
                String nomeExame = res.getString("nome_exame");
                String descricaoExame = res.getString("descricao_exame");
                float ValorExame = res.getFloat("valor_exame");
                Exame exame = new Exame(idExame, nomeExame, ValorExame, descricaoExame);
                
                //Atributos ExameRealizado
                int idExameRealizado = res.getInt("pk_idexame_realizado");
                float valorExameRealizado = res.getFloat("valor_exame_realizado");
                String observacaoExameRealizado = res.getString("observacao_exame_realizado");
                String resultadoExameRealizado = res.getString("resultado_exame_realizado");
                ExameRealizado exameRealizado = new ExameRealizado(idExameRealizado, exame, valorExameRealizado, observacaoExameRealizado, 
                        atendimento, resultadoExameRealizado);

                list.add(exameRealizado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<ExameRealizado> getExamesDaDiariaDaInternacao(int idDiariaInternacao) {
        List<ExameRealizado> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT er.*, e.*, de.*, di.* FROM exame_realizado er "
                    + "JOIN exame e ON (er.fk_idexame_exame_realizado = e.pk_idexame) "
                    + "JOIN diaria_exame de ON (de.fk_idexame_realizado_diaria_exame = er.pk_idexame_realizado) "
                    + "JOIN diaria_internacao di ON (di.pk_iddiaria_internacao = de.fk_iddiaria_internacao_diaria_exame) "
                    + "WHERE di.pk_iddiaria_internacao = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idDiariaInternacao);

            res = stmt.executeQuery();
            while (res.next()) {
                
                //Atributos exame
                int idExame = res.getInt("pk_idexame");
                String nomeExame = res.getString("nome_exame");
                String descricaoExame = res.getString("descricao_exame");
                float ValorExame = res.getFloat("valor_exame");
                Exame exame = new Exame(idExame, nomeExame, ValorExame, descricaoExame);
                
                //Atributos ExameRealizado
                int idExameRealizado = res.getInt("pk_idexame_realizado");
                float valorExameRealizado = res.getFloat("valor_exame_realizado");
                String observacaoExameRealizado = res.getString("observacao_exame_realizado");
                String resultadoExameRealizado = res.getString("resultado_exame_realizado");
                ExameRealizado exameRealizado = new ExameRealizado(idExameRealizado, exame, valorExameRealizado, observacaoExameRealizado, 
                        null, resultadoExameRealizado);

                list.add(exameRealizado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(ExameRealizado exameRealizado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO exame_realizado (pk_idexame_realizado, fk_idexame_exame_realizado,"
                    + "idatendimento_exame_realizado, fk_idpet_exame_realizado, valor_exame_realizado,"
                    + "observacao_exame_realizado, resultado_exame_realizado) "
                    + "VALUES (?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???
            
            stmt.setInt(1, exameRealizado.getId());
            stmt.setInt(2, exameRealizado.getExame().getIdExame());
//            stmt.setInt(3, exameRealizado.getAtendimento() == null ? null : exameRealizado.getAtendimento().getIdAtendimento());
            if(exameRealizado.getAtendimento() == null){
                stmt.setNull(3, 0);
            }else{
                stmt.setInt(3, exameRealizado.getAtendimento().getIdAtendimento());
            }
            stmt.setInt(4, exameRealizado.getPet().getIdPet());
            stmt.setFloat(5, exameRealizado.getValor());
            stmt.setString(6, exameRealizado.getObservacao());
            stmt.setString(7, exameRealizado.getResultado());

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID do vacina no parâmetro que foi recebido pelo método
                    exameRealizado.setId(id);
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
    
    public boolean editar(ExameRealizado exameRealizado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE exame_realizado SET "
                    + "fk_idexame_exame_realizado = ?, idatendimento_exame_realizado = ?, fk_idpet_exame_realizado = ?, "
                    + "valor_exame_realizado = ?, observacao_exame_realizado = ?, resultado_exame_realizado = ? "
                    + "WHERE pk_idexame_realizado = ?;";
            
            int idExame = exameRealizado.getExame().getIdExame();
            int idAtendimento = exameRealizado.getAtendimento() != null ? exameRealizado.getAtendimento().getIdAtendimento() : 0;
            int idPet = exameRealizado.getPet().getIdPet();
            float valorExameRealizado = exameRealizado.getValor();
            String observacaoExameRealizado = exameRealizado.getObservacao();
            String resultadoExame = exameRealizado.getResultado();
            int idExameRealizado = exameRealizado.getId();

            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idExame);
            stmt.setInt(2, (int) idAtendimento == 0 ? null : idAtendimento);
            stmt.setInt(3, idPet);
            stmt.setFloat(4, valorExameRealizado);
            stmt.setString(5, observacaoExameRealizado);
            stmt.setString(6, resultadoExame);
            stmt.setInt(7, idExameRealizado);

            stmt.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluir(ExameRealizado exameRealizado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from exame_realizado where pk_idexame_realizado = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, exameRealizado.getId());
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
