package model.dao;

import application.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import model.classes.utilitario.Bairro;
import model.classes.Clinica;
import model.classes.utilitario.Municipio;
import model.classes.Veterinario;
import model.db.DB;
import model.services.ClinicaService;
import model.services.VeterinarioService;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class VeterinarioDAO {

    private Connection con;

    public VeterinarioDAO(Connection con) {
        this.con = con;
    }

    public List<Veterinario> getAll(int filtroSelecionado, String txtFiltro) {
        List<Veterinario> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT v.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario "
                    + "FROM veterinario v "
                    + "LEFT JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) "
                    + "LEFT JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) ";
//                    + "JOIN clinica_veterinario cv ON (cv.fk_idveterinario_cv = v.pk_idveterinario) "
//                    + "JOIN clinica c ON (c.pk_idclinica = cv.fk_idclinica_cv)";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = " ";
                    break;
                case 0:
                    filtroSql = "WHERE v.cpf_veterinario like ? ";
                    break;
                case 1:
                    filtroSql = "WHERE v.nome_veterinario like ? ";
                    break;
                case 2:
                    filtroSql = "WHERE v.sexo_veterinario = 1 ";
                    break;
                case 3:
                    filtroSql = "WHERE v.sexo_veterinario = 0 ";
                    break;
                case 4:
                    filtroSql = "WHERE v.crmv_veterinario like ? ";
                    break;
                case 5:
                    filtroSql = "WHERE bv.nome_bairro_veterinario like ? ";
                    break;
                case 6:
                    filtroSql = "WHERE mv.nome_municipio_veterinario like ? ";
                    break;
                case 7:
                    filtroSql = "WHERE  like ? ";
                    break;
                case 8:
                    filtroSql = "WHERE  like ? ";
                    break;
                case 9:
                    filtroSql = "WHERE  like ? ";
                    break;
                case 10:
                    filtroSql = "WHERE  like ? ";
                    break;
                case 11:
                    filtroSql = "WHERE v.observacao_veterinario like ? ";
                    break;
                case 12:
                    filtroSql = "WHERE v.telefone_veterinario like ? ";
                    break;
                case 13:
                    filtroSql = "WHERE c.pk_idclinica = ? ";
                    break;

                default:
                    break;
            }

            //preparando a String sql para execução
            sql += filtroSql;
            sql += "ORDER BY v.nome_veterinario";
            stmt = con.prepareStatement(sql);
            if (txtFiltro == null) {
                txtFiltro = "";
            }

            if (filtroSelecionado != 2 && filtroSelecionado != 3 && filtroSelecionado != -1 && filtroSelecionado != 13) {
                stmt.setString(1, "%" + txtFiltro + "%");
            } else if (filtroSelecionado == 13) {
                stmt.setString(1, txtFiltro);
            }

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Municipio Veterinario
                int idMunicipioVeterinario = res.getInt("fk_idmunicipio_veterinario");
                String nomeMunicipioVeterinario = res.getString("nome_municipio_veterinario");
                String estadoVeterinario = res.getString("estado_veterinario");
                Municipio municipioVeterinario = new Municipio(idMunicipioVeterinario, nomeMunicipioVeterinario, estadoVeterinario);

                //Atributos Bairro Veterinario
                int idBairroVeterinario = res.getInt("fk_idbairro_veterinario");
                String nomeBairroVeterinario = res.getString("nome_bairro_veterinario");
                Bairro bairroVeterinario = new Bairro(idBairroVeterinario, nomeBairroVeterinario, municipioVeterinario);

                //Coloca atributos em variaveis
                //Atributos Veterinario
                int idVeterinario = res.getInt("pk_idveterinario");
                String cpfVeterinario = res.getString("cpf_veterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String ruaVeterinario = res.getString("rua_veterinario");
                String numeroVeterinario = res.getString("numero_veterinario");
                String cepVeterinario = res.getString("cep_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                String telefoneVeterinario = res.getString("telefone_veterinario");
                String emailVeterinario = res.getString("email_veterinario");
                String observacaoVeterinario = res.getString("observacao_veterinario");
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);

                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario,
                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario, listaClinicas);
                list.add(veterinario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public List<Clinica> getClinicasDoVeterinario(int idVeterinario) {
        List<Clinica> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT v.*, cv.*, c.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, "
                    + "mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica "
                    + "FROM veterinario v "
                    + "LEFT JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) "
                    + "LEFT JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) "
                    + "LEFT JOIN clinica_veterinario cv ON (cv.fk_idveterinario_cv = v.pk_idveterinario) "
                    + "LEFT JOIN clinica c ON (c.pk_idclinica = cv.fk_idclinica_cv) "
                    + "LEFT JOIN municipio mc on (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) "
                    + "LEFT JOIN bairro bc on (c.fk_idbairro_clinica = bc.pk_idbairro) "
                    + "WHERE cv.fk_idveterinario_cv = ? "
                    + "ORDER BY v.nome_veterinario";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idVeterinario);

            res = stmt.executeQuery();
            while (res.next()) {

//                //Atributos Municipio Veterinario
//                int idMunicipioVeterinario = res.getInt("fk_idmunicipio_veterinario");
//                String nomeMunicipioVeterinario = res.getString("nome_municipio_veterinario");
//                String estadoVeterinario = res.getString("estado_veterinario");
//                Municipio municipioVeterinario= new Municipio(idMunicipioVeterinario, nomeMunicipioVeterinario, estadoVeterinario);
//                
//                //Atributos Bairro Veterinario
//                int idBairroVeterinario = res.getInt("fk_idbairro_veterinario");
//                String nomeBairroVeterinario = res.getString("nome_bairro_veterinario");
//                Bairro bairroVeterinario = new Bairro(idBairroVeterinario, nomeBairroVeterinario, municipioVeterinario);
//                
//                //Coloca atributos em variaveis
//                //Atributos Veterinario
//                String cpfVeterinario = res.getString("cpf_veterinario");
//                String nomeVeterinario = res.getString("nome_veterinario");
//                String ruaVeterinario = res.getString("rua_veterinario");
//                String numeroVeterinario = res.getString("numero_veterinario");
//                String cepVeterinario = res.getString("cep_veterinario");
//                String crmvVeterinario = res.getString("crmv_veterinario");
//                String telefoneVeterinario = res.getString("telefone_veterinario");
//                String emailVeterinario = res.getString("email_veterinario");
//                String observacaoVeterinario = res.getString("observacao_veterinario");
//                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
 

                //Atributos Municipio Clinica
                int idMunicipioClinica = res.getInt("fk_idmunicipio_clinica");
                String nomeMunicipioClinica = res.getString("nome_municipio_clinica");
                String estadoClinica = res.getString("estado_clinica");
                Municipio municipioClinica = new Municipio(idMunicipioClinica, nomeMunicipioClinica, estadoClinica);

                //Atributos Bairro Clinica
                int idBairroClinica = res.getInt("fk_idbairro_clinica");
                String nomeBairroClinica = res.getString("nome_bairro_clinica");
                Bairro bairroClinica = new Bairro(idBairroClinica, nomeBairroClinica, municipioClinica);

                //Atributos Clinica
                int idClinica = res.getInt("pk_idclinica");
                String nomeClinica = res.getString("nome_clinica");
                String cnpjClinica = res.getString("cnpj_clinica");
                String emailClinica = res.getString("email_clinica");
                String ruaClinica = res.getString("rua_clinica");
                String numeroClinica = res.getString("numero_clinica");
                String cepClinica = res.getString("cep_clinica");
                String telefoneClinica = res.getString("telefone_clinica");
                String telefoneAlternativoClinica = res.getString("telefone_alternativo_clinica");
                String observacaoClinica = res.getString("observacao_clinica");
                LocalDate dataCadastroClinica;
                if (res.getDate("dtCadastro_clinica") == null) {
                    dataCadastroClinica = null;
                } else {
                    dataCadastroClinica = res.getDate("dtCadastro_clinica").toLocalDate();
                }
                List listaVeterinarios = new ClinicaService().getVeterinariosDaClinica(idClinica);
//                List listaClinicas = null;
//                //Cria o Veterinario
//                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario, 
//                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario, listaClinicas);
//                
                //Cria a clinica
                Clinica clinica = new Clinica(idClinica, nomeClinica, cnpjClinica, emailClinica, ruaClinica, bairroClinica, numeroClinica, cepClinica,
                        municipioClinica, telefoneClinica, telefoneAlternativoClinica, observacaoClinica, dataCadastroClinica);

                list.add(clinica);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(Veterinario veterinario) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "INSERT INTO veterinario (pk_idveterinario, fk_idbairro_veterinario, "
                    + "fk_idmunicipio_veterinario, nome_veterinario, cpf_veterinario, crmv_veterinario, "
                    + "email_veterinario, telefone_veterinario, cep_veterinario, rua_veterinario, "
                    + "numero_veterinario, observacao_veterinario, sexo_veterinario)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, veterinario.getId());
            stmt.setInt(2, veterinario.getBairro().getId());
            stmt.setInt(3, veterinario.getMunicipio().getId());
            stmt.setString(4, veterinario.getNome());
            stmt.setString(5, veterinario.getCpf());
            stmt.setString(6, veterinario.getCrmv());
            stmt.setString(7, veterinario.getEmail());
            stmt.setString(8, veterinario.getTelefone());
            stmt.setString(9, veterinario.getCep());
            stmt.setString(10, veterinario.getRua());
            stmt.setString(11, veterinario.getNumero());
            stmt.setString(12, veterinario.getObservacao());
            stmt.setBoolean(13, veterinario.isSexo());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    veterinario.setId(id);
                    result = true;
                    inserirListaClinicas(veterinario);
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

    public boolean inserirListaClinicas(Veterinario veterinario) {
        PreparedStatement stmt = null;
        boolean result = false;

        try {
            for (Clinica clin : veterinario.getListaClinicas()) {
                // String SQL para INSERIR
                String sql = "INSERT INTO clinica_veterinario (fk_idveterinario_cv, fk_idclinica_cv)\n"
                        + "VALUES (?,?);";
                // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
                stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                //Trocando os ???
                stmt.setInt(1, veterinario.getId());
                stmt.setInt(2, clin.getIdClinica());
                // Executar o scipt
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Deu certo
                    result = true;
                } else {
                    //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                    throw new SQLException("Não foi possível inserir");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean excluir(Veterinario veterinario) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from veterinario where pk_idveterinario = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, veterinario.getId());
            stmt.executeUpdate();
            result = true;
        } catch (SQLIntegrityConstraintViolationException e) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Erro!");
            al.setContentText(veterinario.getNome() + " não pode ser excluído porque está vinculado a uma clínica!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(Veterinario veterinario) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE veterinario SET "
                    + "fk_idbairro_veterinario = ?, fk_idmunicipio_veterinario = ?, nome_veterinario = ?, "
                    + "cpf_veterinario = ?, crmv_veterinario = ?, email_veterinario = ?, telefone_veterinario = ?, cep_veterinario = ?, rua_veterinario = ?, numero_veterinario = ?, "
                    + "observacao_veterinario = ?, sexo_veterinario = ?"
                    + "    WHERE pk_idveterinario = ?";

            int idBairroVeterinario = veterinario.getBairro().getId();
            int idMunicipioVeterinario = veterinario.getMunicipio().getId();
            String nomeveterinario = veterinario.getNome();
            String cpfVeterinario = veterinario.getCpf();
            String crmvVeterinario = veterinario.getCrmv();
            String emailVeterinario = veterinario.getEmail();
            String telefoneVeterinario = veterinario.getTelefone();
            String cepVeterinario = veterinario.getCep();
            String rua = veterinario.getRua();
            String numero = veterinario.getNumero();
            String observacaoVeterinario = veterinario.getObservacao();
            boolean sexoVeterinario = veterinario.isSexo();
            int idVeterinario = veterinario.getId();

            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idBairroVeterinario);
            stmt.setInt(2, idMunicipioVeterinario);
            stmt.setString(3, nomeveterinario);
            stmt.setString(4, cpfVeterinario);
            stmt.setString(5, crmvVeterinario);
            stmt.setString(6, emailVeterinario);
            stmt.setString(7, telefoneVeterinario);
            stmt.setString(8, cepVeterinario);
            stmt.setString(9, rua);
            stmt.setString(10, numero);
            stmt.setString(11, observacaoVeterinario);
            stmt.setBoolean(12, sexoVeterinario);
            stmt.setInt(13, idVeterinario);

            stmt.executeUpdate();

            sql = "delete from clinica_veterinario where fk_idveterinario_cv = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, veterinario.getId());
            stmt.executeUpdate();

            inserirListaClinicas(veterinario);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

}
