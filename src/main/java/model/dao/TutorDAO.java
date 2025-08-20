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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.classes.Administrador;
import model.classes.utilitario.Bairro;
import model.classes.Funcionario;
import model.classes.utilitario.Municipio;
import model.classes.Tutor;
import model.classes.Usuario;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class TutorDAO {

    private Connection con;

    public TutorDAO(Connection con) {
        this.con = con;
    }

    public List<Tutor> getAll(int filtroSelecionado, String txtFiltro) {
        List<Tutor> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT t.*, mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, bt.nome_bairro as nome_bairro_tutor "
                    + "FROM tutor t "
                    + "JOIN municipio mt on (t.fk_idmunicipio_tutor = mt.pk_idmunicipio) "
                    + "JOIN bairro bt on (t.fk_idbairro_tutor = bt.pk_idbairro) ";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = " ";
                    break;
                case 0:
                    filtroSql = "WHERE t.cpf_tutor like ? ";
                    break;
                case 1:
                    filtroSql = "WHERE t.nome_tutor like ? ";
                    break;
                case 2:
                    filtroSql = "WHERE t.sexo_tutor = 1 ";
                    break;
                case 3:
                    filtroSql = "WHERE t.sexo_tutor = 0 ";
                    break;
                case 4:
                    filtroSql = "WHERE t.dtnascimento_tutor like ? ";
                    break;
                case 5:
                    filtroSql = "WHERE bt.nome_bairro_tutor like ? ";
                    break;
                case 6:
                    filtroSql = "WHERE mt.nome_municipio_tutor like ? ";
                    break;
                case 7:
                    filtroSql = "WHERE t.tipo_tutor like ? ";
                    break;
                case 8:
                    filtroSql = "WHERE t.faixarenda_tutor like ? ";
                    break;
                case 9:
                    filtroSql = "WHERE t.faixarenda_tutor like ? ";
                    break;
                case 10:
                    filtroSql = "WHERE t.faixarenda_tutor like ? ";
                    break;
                case 11:
                    filtroSql = "WHERE t.observacao_tutor like ? ";
                    break;
                case 12:
                    filtroSql = "WHERE t.telefone_tutor like ? ";
                    break;

                default:
                    break;
            }

            //preparando a String sql para execução
            sql += filtroSql;
            sql += "ORDER BY t.nome_tutor";
            stmt = con.prepareStatement(sql);
            if (txtFiltro == null) {
                txtFiltro = "";
            }

            if (filtroSelecionado != 2 && filtroSelecionado != 3 && filtroSelecionado != -1) {
                stmt.setString(1, "%" + txtFiltro + "%");

            }

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Municipio Tutor
                int idMunicipioTutor = res.getInt("fk_idmunicipio_tutor");
                String nomeMunicipioTutor = res.getString("nome_municipio_tutor");
                String estadoTutor = res.getString("estado_tutor");
                Municipio municipioTutor= new Municipio(idMunicipioTutor, nomeMunicipioTutor, estadoTutor);
                
                //Atributos Bairro Tutor
                int idBairroTutor = res.getInt("fk_idbairro_tutor");
                String nomeBairroTutor = res.getString("nome_bairro_tutor");
                Bairro bairroTutor = new Bairro(idBairroTutor, nomeBairroTutor, municipioTutor);
                
                //Coloca atributos em variaveis
                //Atributos Tutor
                //Atributos tutor
                int idTutor = res.getInt("pk_idtutor");
                String cpfTutor = res.getString("cpf_tutor");
                String nomeTutor = res.getString("nome_tutor");
                String ruaTutor = res.getString("rua_tutor");
                String numeroTutor = res.getString("numero_tutor");
                String cepTutor = res.getString("cep_tutor");
                String tipoTutor = res.getString("tipo_tutor");
                String telefoneTutor = res.getString("telefone_tutor");
                String telefoneSecundarioTutor = res.getString("telefone_alternativo_tutor");
                String emailTutor = res.getString("email_tutor");
                String observacaoTutor = res.getString("observacao_tutor");
                int faixaRenda = res.getInt("faixarenda_tutor");
                boolean sexoTutor = res.getBoolean("sexo_tutor");
                LocalDate dtNascTutor;
                if (res.getDate("dtnascimento_tutor") == null) {
                    dtNascTutor = null;
                } else {
                    dtNascTutor = res.getDate("dtnascimento_tutor").toLocalDate();
                }


                Tutor tutor = new Tutor(idTutor, cpfTutor, nomeTutor, ruaTutor, bairroTutor, numeroTutor, cepTutor, municipioTutor, tipoTutor, telefoneTutor,
                        telefoneSecundarioTutor, emailTutor, observacaoTutor, faixaRenda, sexoTutor, dtNascTutor);
                list.add(tutor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(Tutor tutor) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO tutor (cpf_tutor, nome_tutor, rua_tutor, fk_idbairro_tutor, numero_tutor, cep_tutor, telefone_tutor, "
                    + "telefone_alternativo_tutor, tipo_tutor, email_tutor, observacao_tutor, fk_idmunicipio_tutor, faixarenda_tutor, sexo_tutor, dtnascimento_tutor)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setString(1, tutor.getCpf());
            stmt.setString(2, tutor.getNome());
            stmt.setString(3, tutor.getRua());
            stmt.setInt(4, tutor.getBairro().getId());
            stmt.setString(5, tutor.getNumero());
            stmt.setString(6, tutor.getCep());
            stmt.setString(7, tutor.getTelefoneTutor());
            stmt.setString(8, tutor.getTelefoneAlternativoTutor());
            stmt.setString(9, tutor.getTipoTutor());
            stmt.setString(10, tutor.getEmailTutor());
            stmt.setString(11, tutor.getObservacaoTutor());
            stmt.setInt(12, tutor.getMunicipio().getId());
            stmt.setInt(13, tutor.getFaixaRenda());
            stmt.setBoolean(14, tutor.isSexo());
            if (tutor.getDtNasc() == null) {
                stmt.setDate(15, null);
            } else {
                stmt.setDate(15, Date.valueOf(tutor.getDtNasc()));
            }

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID do tutor no parâmetro que foi recebido pelo método
                    tutor.setIdTutor(id);
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

    public boolean excluir(Tutor tutor) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from tutor where pk_idtutor = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, tutor.getIdTutor());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(Tutor tutor) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE tutor SET \n"
                    + "cpf_tutor = ?, nome_tutor = ?, rua_tutor = ?, fk_idbairro_tutor = ?, numero_tutor = ?, cep_tutor = ?, "
                    + "telefone_tutor = ?, telefone_alternativo_tutor = ?, tipo_tutor = ?,"
                    + "email_tutor = ?, observacao_tutor = ?, sexo_tutor = ?, dtnascimento_tutor = ?, fk_idmunicipio_tutor = ?, faixarenda_tutor = ? WHERE pk_idtutor = ?;";
            int idUsuarioAlteracao = Principal.usuarioLogado.getIdUsuario();
            String cpfTutor = tutor.getCpf();
            String nometutor = tutor.getNome();
            String rua = tutor.getRua();
            int idBairroTutor = tutor.getBairro().getId();
            String numero = tutor.getNumero();
            String cep = tutor.getCep();
            String tipoTutor = tutor.getTipoTutor();
            String telefoneTutor = tutor.getTelefoneTutor();
            String telefoneAlternativo = tutor.getTelefoneAlternativoTutor();
            String emailTutor = tutor.getEmailTutor();
            String observacaoTutor = tutor.getObservacaoTutor();
            boolean sexoTutor = tutor.isSexo();
            LocalDate dtNascimentoTutor = tutor.getDtNasc();
            int idMunicipioTutor = tutor.getMunicipio().getId();
            int faixaRenda = tutor.getFaixaRenda();

            int idTutor = tutor.getIdTutor();

            stmt = con.prepareStatement(sql);

            stmt.setString(1, cpfTutor);
            stmt.setString(2, nometutor);
            stmt.setString(3, rua);
            stmt.setInt(4, idBairroTutor);
            stmt.setString(5, numero);
            stmt.setString(6, cep);
            stmt.setString(7, telefoneTutor);
            stmt.setString(8, telefoneAlternativo);
            stmt.setString(9, tipoTutor);
            stmt.setString(10, emailTutor);
            stmt.setString(11, observacaoTutor);
            stmt.setBoolean(12, sexoTutor);
            if (tutor.getDtNasc() == null) {
                stmt.setDate(13, null);
            } else {
                stmt.setDate(13, Date.valueOf(dtNascimentoTutor));
            }
            stmt.setInt(14, idMunicipioTutor);
            stmt.setInt(15, faixaRenda);
            stmt.setInt(16, idTutor);

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
