package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import model.classes.Administrador;
import model.classes.utilitario.Bairro;
import model.classes.Funcionario;
import model.classes.utilitario.Municipio;
import model.classes.Pet;
import model.classes.ProdutoVacina;
import model.classes.Tutor;
import model.classes.Usuario;
import model.classes.Vacina;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class ProdutoVacinaDAO {
    
    private Connection con;
    
    public ProdutoVacinaDAO(Connection con) {
        this.con = con;
    }
    
    public List<ProdutoVacina> getAll(ProdutoVacina vacina) {
        List<ProdutoVacina> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM nome_vac nv "
                    + "LEFT JOIN tipo_vac tv on (nv.fk_idtipo_vac = tv.pk_idtipo_vac) "
                    + "WHERE fk_idtipo_vac = ? "
                    + "ORDER BY nome_vac";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, vacina.getIdTipoVacina());
            res = stmt.executeQuery();
            while (res.next()) {
                int idProdutoVacina = res.getInt("pk_idnome_vac");
                String nomeVacina = res.getString("nome_vac");
                String tipoVacina = res.getString("tipo_vac");
                int idTipoVacina = res.getInt("pk_idtipo_vac");
                String laboratorioVacina = res.getString("laboratorio_nome_vac");
                float valorVacina = res.getFloat("valor_nome_vac");

                ProdutoVacina produtoVacina = new ProdutoVacina(idProdutoVacina, nomeVacina, tipoVacina, idTipoVacina, laboratorioVacina, valorVacina);
                list.add(produtoVacina);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<ProdutoVacina> getTiposVacinas() {
        List<ProdutoVacina> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM tipo_vac tv "
                    + "ORDER BY tipo_vac";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                String tipoVacina = res.getString("tipo_vac");
                int idTipoVacina = res.getInt("pk_idtipo_vac");

                ProdutoVacina produtoVacina = new ProdutoVacina(tipoVacina, idTipoVacina);
                list.add(produtoVacina);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public boolean inserirNomeVacina(ProdutoVacina produtoVacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            //String SQL para INSERIR
            String sql = "INSERT INTO nome_vac (pk_idnome_vac, fk_idtipo_vac, laboratorio_nome_vac, nome_vac) VALUES (?,?,?,?)";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //trocando os ??????
            stmt.setInt(1, produtoVacina.getId());
            stmt.setInt(2, produtoVacina.getIdTipoVacina());
            stmt.setString(3, produtoVacina.getLaboratorioVacina());
            stmt.setString(4, produtoVacina.getNomeVacina());

            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                //deu certo
                //pegando o código gerado no INSERT
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    //getInt 1 pega o código que foi gerado e está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //atualiza o código do Departamento no parâmetro
                    //que foi recebido pelo método
                    produtoVacina.setId(id);
                    result = true;
                    //depois daqui vai para o finally
                }
            } else {
                //falhou e vamos gerar uma exception para que o código 
                //caia automaticamente dentro do catch e depois no finally
                throw new SQLException("Não foi possível inserir");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirTipoVacina(ProdutoVacina produtoVacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO tipo_vac (tipo_vac) VALUE (?)";
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produtoVacina.getTipoVacina());
            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    produtoVacina.setId(id);
                    result = true;
                }
            } else {
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluirVacina(ProdutoVacina produtoVacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM nome_vac WHERE pk_idnome_vac = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, produtoVacina.getId());
            //executa
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir a vacina!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluirTipoVacina(ProdutoVacina produtoVacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM tipo_vac WHERE pk_idtipo_vac = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, produtoVacina.getIdTipoVacina());
            //executa
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir a categoria de vacinas!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editarNomeVacina(ProdutoVacina produtoVacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE nome_vac SET fk_idtipo_vac = ?, laboratorio_nome_vac = ?, nome_vac = ? WHERE pk_idnome_vac = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, produtoVacina.getIdTipoVacina());
            stmt.setString(2, produtoVacina.getLaboratorioVacina());
            stmt.setString(3, produtoVacina.getNomeVacina());
            stmt.setInt(4, produtoVacina.getId());
            //executa
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editarTipoVacina(ProdutoVacina produtoVacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE tipo_vac SET tipo_vac = ? WHERE pk_idtipo_vac = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setString(1, produtoVacina.getTipoVacina());
            stmt.setInt(2, produtoVacina.getIdTipoVacina());
            //executa
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
