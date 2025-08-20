package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.classes.Atendimento;
import model.classes.Servico;
import model.classes.ServicoRealizado;
import model.classes.Pet;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class ServicoRealizadoDAO {

    private Connection con;

    public ServicoRealizadoDAO(Connection con) {
        this.con = con;
    }

    public List<ServicoRealizado> getServicosDoPet(Pet pet) {
        List<ServicoRealizado> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT er.*, e.*, a.* FROM servico_realizado er "
                    + "JOIN servico e ON (er.fk_idservico_servico_realizado = e.pk_idservico) "
                    + "JOIN atendimento a ON (er.idatendimento_servico_realizado = a.pk_idatendimento) "
                    + "WHERE er.fk_idpet_servico_realizado = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, pet.getIdPet());

            res = stmt.executeQuery();
            while (res.next()) {
                
                //Atributo id do atendimento
                int idAtendimento = res.getInt("pk_idatendimento");
                Atendimento atendimento = new Atendimento(idAtendimento);
                
                //Atributos servico
                int idServico = res.getInt("pk_idservico");
                String nomeServico = res.getString("nome_servico");
                String descricaoServico = res.getString("descricao_servico");
                float ValorServico = res.getFloat("valor_servico");
                Servico servico = new Servico(idServico, nomeServico, ValorServico, descricaoServico);
                
                //Atributos ServicoRealizado
                int idServicoRealizado = res.getInt("pk_idservico_realizado");
                float valorServicoRealizado = res.getFloat("valor_servico_realizado");
                String observacaoServicoRealizado = res.getString("observacao_servico_realizado");
                int qtdServicoRealizado = res.getInt("quantidade_servico_realizado");
                ServicoRealizado servicoRealizado = new ServicoRealizado(idServicoRealizado, servico, valorServicoRealizado, observacaoServicoRealizado, 
                        atendimento, qtdServicoRealizado);

                list.add(servicoRealizado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<ServicoRealizado> getServicosDoAtendimento(int idAtendimento) {
        List<ServicoRealizado> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT er.*, e.*, a.* FROM servico_realizado er "
                    + "JOIN servico e ON (er.fk_idservico_servico_realizado = e.pk_idservico) "
                    + "JOIN atendimento a ON (er.idatendimento_servico_realizado = a.pk_idatendimento) "
                    + "WHERE er.idatendimento_servico_realizado = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idAtendimento);

            res = stmt.executeQuery();
            while (res.next()) {
                
                //Criando o atendimento somente com o id
                Atendimento atendimento = new Atendimento(idAtendimento);
                
                //Atributos servico
                int idServico = res.getInt("pk_idservico");
                String nomeServico = res.getString("nome_servico");
                String descricaoServico = res.getString("descricao_servico");
                float ValorServico = res.getFloat("valor_servico");
                Servico servico = new Servico(idServico, nomeServico, ValorServico, descricaoServico);
                
                //Atributos ServicoRealizado
                int idServicoRealizado = res.getInt("pk_idservico_realizado");
                float valorServicoRealizado = res.getFloat("valor_servico_realizado");
                String observacaoServicoRealizado = res.getString("observacao_servico_realizado");
                int qtdServicoRealizado = res.getInt("quantidade_servico_realizado");
                ServicoRealizado servicoRealizado = new ServicoRealizado(idServicoRealizado, servico, valorServicoRealizado, observacaoServicoRealizado, 
                        atendimento, qtdServicoRealizado);

                list.add(servicoRealizado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<ServicoRealizado> getServicosDaDiariaDaInternacao(int idDiariaInternacao) {
        List<ServicoRealizado> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        
        try {
            String sql = "SELECT er.*, e.*, de.*, di.* FROM servico_realizado er "
                    + "JOIN servico e ON (er.fk_idservico_servico_realizado = e.pk_idservico) "
                    + "JOIN diaria_servico de ON (de.fk_idservico_realizado_diaria_servico = er.pk_idservico_realizado) "
                    + "JOIN diaria_internacao di ON (di.pk_iddiaria_internacao = de.fk_iddiaria_internacao_diaria_servico) "
                    + "WHERE di.pk_iddiaria_internacao = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idDiariaInternacao);

            res = stmt.executeQuery();
            while (res.next()) {
                
                //Atributos servico
                int idServico = res.getInt("pk_idservico");
                String nomeServico = res.getString("nome_servico");
                String descricaoServico = res.getString("descricao_servico");
                float ValorServico = res.getFloat("valor_servico");
                Servico servico = new Servico(idServico, nomeServico, ValorServico, descricaoServico);
                
                //Atributos ServicoRealizado
                int idServicoRealizado = res.getInt("pk_idservico_realizado");
                float valorServicoRealizado = res.getFloat("valor_servico_realizado");
                String observacaoServicoRealizado = res.getString("observacao_servico_realizado");
                int quantidadeServicoRealizado = res.getInt("quantidade_servico_realizado");
                ServicoRealizado servicoRealizado = new ServicoRealizado(idServicoRealizado, servico, valorServicoRealizado, observacaoServicoRealizado, 
                        null, quantidadeServicoRealizado);

                list.add(servicoRealizado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }


    public boolean inserir(ServicoRealizado servicoRealizado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO servico_realizado (fk_idservico_servico_realizado,"
                    + "idatendimento_servico_realizado, fk_idpet_servico_realizado, valor_servico_realizado,"
                    + "observacao_servico_realizado, quantidade_servico_realizado) "
                    + "VALUES (?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setInt(1, servicoRealizado.getServico().getIdServico());
//            stmt.setInt(3, servicoRealizado.getAtendimento().getIdAtendimento());
            if(servicoRealizado.getAtendimento() == null){
                stmt.setNull(2, 0);
            }else{
                stmt.setInt(2, servicoRealizado.getAtendimento().getIdAtendimento());
            }
            stmt.setInt(3, servicoRealizado.getPet().getIdPet());
            stmt.setFloat(4, servicoRealizado.getValor());
            stmt.setString(5, servicoRealizado.getObservacao());
            stmt.setInt(6, servicoRealizado.getQuantidade());

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
                    servicoRealizado.setId(id);
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
    
    public boolean editar(ServicoRealizado servicoRealizado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE servico_realizado SET "
                    + "fk_idservico_servico_realizado = ?, idatendimento_servico_realizado = ?, fk_idpet_servico_realizado = ?, "
                    + "valor_servico_realizado = ?, observacao_servico_realizado = ?, quantidade_servico_realizado = ? "
                    + "WHERE pk_idservico_realizado = ?;";
            
            int idServico = servicoRealizado.getServico().getIdServico();
            int idAtendimento = servicoRealizado.getAtendimento() != null ? servicoRealizado.getAtendimento().getIdAtendimento() : 0;
            int idPet = servicoRealizado.getPet().getIdPet();
            float valorServicoRealizado = servicoRealizado.getValor();
            String observacaoServicoRealizado = servicoRealizado.getObservacao();
            int idServicoRealizado = servicoRealizado.getId();
            int qtdServicoRealizado = servicoRealizado.getQuantidade();

            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idServico);
            stmt.setInt(2, (int) idAtendimento == 0 ? null : idAtendimento);
            stmt.setInt(3, idPet);
            stmt.setFloat(4, valorServicoRealizado);
            stmt.setString(5, observacaoServicoRealizado);
            stmt.setInt(6, qtdServicoRealizado);
            stmt.setInt(7, idServicoRealizado);

            stmt.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluir(ServicoRealizado servicoRealizado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from servico_realizado where pk_idservico_realizado = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, servicoRealizado.getId());
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
