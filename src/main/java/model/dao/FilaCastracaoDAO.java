package model.dao;

import application.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import model.classes.Pet;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class FilaCastracaoDAO {
    private Connection con;

    public FilaCastracaoDAO(Connection con) {
        this.con = con;
    }
    
    public boolean removerDaFila(Pet pet) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM pet WHERE pk_idpet = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, pet.getIdPet());
            //executa
            stmt.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir o departamento!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean incluirNaFila(Pet pet) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            //String SQL para INSERIR
            String sql = "INSERT INTO filaesperacastracao (fk_idusuario, fk_idpet, pontuacao, posicao, datainclusao) VALUES (?,?,?,?,DATE_FORMAT(now(), ?))";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //trocando os ??????
            stmt.setInt(1, Principal.usuarioLogado.getIdUsuario());
            stmt.setInt(2, pet.getIdPet());
//            stmt.setInt(3, );
//            stmt.setString(4, );
            stmt.setString(5, "%Y-%c-%d");

            if (pet.getDataNascimentoPet() == null) {
                stmt.setDate(11, null);
            } else {
                stmt.setDate(11, Date.valueOf(pet.getDataNascimentoPet()));
            }
            stmt.setString(12, pet.getObservacao());
            stmt.setBoolean(13, pet.isAdotado());

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
                    pet.setIdPet(id);
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
    
}
