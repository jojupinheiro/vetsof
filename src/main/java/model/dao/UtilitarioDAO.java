/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import application.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.control.Alert;
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Municipio;
import model.classes.utilitario.ValorPadrao;
import model.db.DB;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class UtilitarioDAO {
    
    private Connection con;

    public UtilitarioDAO(Connection con) {
        this.con = con;
    }
    
    public List<Municipio> getMunicipios() {
        List<Municipio> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM municipio order by nome_municipio";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                int idMunicipio = res.getInt("pk_idmunicipio");
                String nomeMunicipio = res.getString("nome_municipio"); //Passa para a String criada o valor obtido no DB
                String estadoMunicipio = res.getString("estado_municipio");
                Municipio municipio = new Municipio(idMunicipio, nomeMunicipio, estadoMunicipio);
                
                list.add(municipio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return list;
        }
    }
    
    public List<Bairro> getBairros(Municipio municipio) {
        List<Bairro> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM bairro b "
                    + "JOIN municipio m ON (b.fk_idmunicipio_bairro = m.pk_idmunicipio) "
                    + "where m.pk_idmunicipio = ? order by nome_bairro";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, municipio.getId());
            res = stmt.executeQuery();
            while (res.next()) {
                int idBairro = res.getInt("pk_idbairro");
                String nomeBairro = res.getString("nome_bairro");
                Bairro bairro = new Bairro(idBairro, nomeBairro, municipio);
                
                list.add(bairro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return list;
        }
    }
    
    public boolean inserirMunicipio(Municipio municipio) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO municipio (nome_municipio, estado_municipio) VALUES (?, ?)";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, municipio.getNome());
            stmt.setString(2, municipio.getEstado());

            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                //deu certo
                //pegando o código gerado no INSERT
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    //getInt 1 pega o código que foi gerado e está no primeiro campo do resultSet
                    int id = rs.getInt(1);
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
    
    public boolean inserirBairro(Bairro bairro) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        boolean result = false;
 
        stmt = null;
        
        try {
            String sql = "INSERT INTO bairro (fk_idmunicipio_bairro, nome_bairro) VALUE (?, ?)";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, bairro.getMunicipio().getId());
            stmt.setString(2, bairro.getNome());

            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                //deu certo
                //pegando o código gerado no INSERT
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    //getInt 1 pega o código que foi gerado e está no primeiro campo do resultSet
                    int id = rs.getInt(1);
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
    
    public boolean editarBairro(Bairro bairro) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE bairro SET  fk_idmunicipio_bairro = ?, nome_bairro = ? WHERE pk_idbairro = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, bairro.getMunicipio().getId());
            stmt.setString(2, bairro.getNome());
            stmt.setInt(3, bairro.getId());

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
    
    public boolean editarMunicipio(Municipio municipio) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE municipio SET estado_municipio = ?, nome_municipio = ? WHERE pk_idmunicipio = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setString(1, municipio.getEstado());
            stmt.setString(2, municipio.getNome());
            stmt.setInt(3, municipio.getId());

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

    public boolean excluirMunicipio(Municipio municipio) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM municipio WHERE pk_idmunicipio = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, municipio.getId());
            //executa
            stmt.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir o municipio!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluirBairro(Bairro bairro) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        boolean result = false;
        
        try {
            String sql = "DELETE FROM bairro WHERE pk_idbairro = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, bairro.getId());
            //executa
            stmt.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir o bairro!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public String getEstiloAtivo(){
        String estilo = "";
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT * FROM estilo WHERE ativo_estilo = 1";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                estilo = res.getString("nome_estilo"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return estilo;
        }
    }
    
    public boolean trocaEstiloAtivo(String estilo){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE estilo SET ativo_estilo = 0 WHERE ativo_estilo = 1";
            stmt = con.prepareStatement(sql);
            //executa
            stmt.executeUpdate();
            
            sql = "UPDATE estilo SET ativo_estilo = 1 WHERE nome_estilo = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            estilo = estilo.substring(7);   //Remove o caminho 'styles/' da string
            stmt.setString(1, estilo);
            
            stmt.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean atualizarPreferencias(Map<Integer, Integer> preferencias){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE preferencia SET escolha_preferencia = ? WHERE nome_preferencia = ?";
            
            for (int chave : preferencias.keySet()){
                int definicao = preferencias.get(chave);
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, definicao);
                stmt.setInt(2, chave);
                stmt.executeUpdate();
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public Map<Integer, Integer> getPreferencias(){
        Map<Integer, Integer> preferencias = new TreeMap<>();
        PreparedStatement stmt = null;
        ResultSet res = null;
        
        try {
            String sql = "SELECT * FROM preferencia ORDER BY nome_preferencia";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                int idPreferencia = res.getInt("pk_idpreferencia");
                int nomePreferencia = res.getInt("nome_preferencia");
                int escolhaPreferencia = res.getInt("escolha_preferencia");
                
                preferencias.put(nomePreferencia, escolhaPreferencia);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return preferencias;
        }
    }
    
    public boolean atualizarValoresPadrao(List<ValorPadrao> valoresPadrao){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE valores_padrao SET valor_valores_padrao = ?, string_valores_padrao = ? WHERE codigo_valores_padrao = ?";
            
            for (ValorPadrao item : valoresPadrao){
                int codigo = item.getCodigoValorPadrao();
                stmt = con.prepareStatement(sql);
                stmt.setFloat(1, item.getValorPadraoNumeral());
                stmt.setString(2, item.getValorPadraoString());
                stmt.setInt(3, codigo);
                stmt.executeUpdate();
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public List<ValorPadrao> getValoresPadrao(){
        List<ValorPadrao> valoresPadrao = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet res = null;
        
        try {
            String sql = "SELECT * FROM valores_padrao";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("pk_idvalores_padrao");
                int codigoValorPadrao = res.getInt("codigo_valores_padrao");
                float valorPadraoNumeral = res.getFloat("valor_valores_padrao");
                String valorPadraoString = res.getString("string_valores_padrao");
                
                ValorPadrao valorPadrao = new ValorPadrao(id, codigoValorPadrao, valorPadraoNumeral, valorPadraoString);
                valoresPadrao.add(valorPadrao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return valoresPadrao;
        }
    }
    
}
