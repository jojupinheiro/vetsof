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
import model.classes.utilitario.Bairro;
import model.classes.Clinica;
import model.classes.utilitario.Municipio;
import model.classes.Veterinario;
import model.db.DB;
import model.services.VeterinarioService;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class ClinicaDAO {

    private Connection con;

    public ClinicaDAO(Connection con) {
        this.con = con;
    }

    public List<Clinica> getAll(int filtroSelecionado, String txtFiltro) {
        List<Clinica> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT clinica.*, v.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, mc.nome_municipio as nome_municipio_clinica, "
                    + "mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica\n"
                    + "FROM clinica\n"
                    + "        JOIN veterinario v ON (clinica.fk_idveterinario_clinica = v.pk_idveterinario)\n"
                    + "        JOIN municipio mv ON (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio)\n"
                    + "        JOIN bairro bv ON (v.fk_idbairro_veterinario = bv.pk_idbairro)\n"
                    + "        JOIN municipio mc ON (clinica.fk_idmunicipio_clinica = mc.pk_idmunicipio)\n"
                    + "        JOIN bairro bc ON (clinica.fk_idbairro_clinica = bc.pk_idbairro)";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = ";";
                    break;
                case 0:
                    filtroSql = "WHERE clinica.nome_clinica like ?;";
                    break;
                case 1:
                    filtroSql = "WHERE clinica.cnpj_clinica like ?;";
                    break;
                case 2:
                    filtroSql = "WHERE v.nome_veterinario like ?;";
                    break;
                case 3:
                    filtroSql = "WHERE mc.nome_municipio_clinica like ?;";
                    break;
                default:
                    break;
            }

            //preparando a String sql para execução
            sql += filtroSql;
            stmt = con.prepareStatement(sql);
            if (txtFiltro == null) {
                txtFiltro = "";
            }

            if (filtroSelecionado >= 0 && filtroSelecionado <= 3) {
                stmt.setString(1, "%" + txtFiltro + "%");

            }

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Municipio Clinica
                int idMunicipioClinica = res.getInt("fk_idmunicipio_clinica");
                String nomeMunicipioClinica = res.getString("nome_municipio_clinica");
                String estadoClinica = res.getString("estado_clinica");
                Municipio municipioClinica = new Municipio(idMunicipioClinica, nomeMunicipioClinica, estadoClinica);

                //Atributos Bairro Clinica
                int idBairroClinica = res.getInt("fk_idbairro_clinica");
                String nomeBairroClinica = res.getString("nome_bairro_clinica");
                Bairro bairroClinica = new Bairro(idBairroClinica, nomeBairroClinica, municipioClinica);

                //Coloca atributos em variaveis
                //Atributos clinica
                int idClinica = res.getInt("pk_idclinica");
                String nomeClinica = res.getString("nome_clinica");
                String cnpj = res.getString("cnpj_clinica");
                String emailClinica = res.getString("email_clinica");
                String rua = res.getString("rua_clinica");
                String numero = res.getString("numero_clinica");
                String cep = res.getString("cep_clinica");
                String telefone = res.getString("telefone_clinica");
                String telefoneAlternativo = res.getString("telefone_alternativo_clinica");
                String observacao = res.getString("observacao_clinica");
                LocalDate dataCadastro;
                if (res.getDate("dtCadastro_clinica") == null) {
                    dataCadastro = null;
                } else {
                    dataCadastro = res.getDate("dtCadastro_clinica").toLocalDate();
                }
                

                //Atributos Municipio Veterinario
                int idMunicipioVeterinario = res.getInt("fk_idmunicipio_veterinario");
                String nomeMunicipioVeterinario = res.getString("nome_municipio_veterinario");
                String estadoVeterinario = res.getString("estado_veterinario");
                Municipio municipioVeterinario = new Municipio(idMunicipioVeterinario, nomeMunicipioVeterinario, estadoVeterinario);

                //Atributos Bairro Veterinario
                int idBairroVeterinario = res.getInt("fk_idbairro_veterinario");
                String nomeBairroVeterinario = res.getString("nome_bairro_veterinario");
                Bairro bairroVeterinario = new Bairro(idBairroVeterinario, nomeBairroVeterinario, municipioVeterinario);

                //Atributos Veterinario
                int idVeterinario = res.getInt("pk_idveterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String cpfVeterinario = res.getString("cpf_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                String emailVeterinario = res.getString("email_veterinario");
                String telefoneVeterinario = res.getString("telefone_veterinario");
                String ruaVeterinario = res.getString("rua_veterinario");
                String numeroVeterinario = res.getString("numero_veterinario");
                String cepVeterinario = res.getString("cep_veterinario");
                String observacaoVeterinario = res.getString("observacao_veterinario");
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);

                //Criando o objeto Veterinario
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario,
                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario, listaClinicas);


                List listaVeterinarios = getVeterinariosDaClinica(idClinica, idVeterinario);
                //Cria o objeto Clinica
                Clinica clinica = new Clinica(idClinica, nomeClinica, cnpj, emailClinica, veterinario, rua, bairroClinica, numero, cep, municipioClinica, telefone, telefoneAlternativo, observacao, dataCadastro, listaVeterinarios);
//                System.out.println(clinica);

                //Adiciona o objeto Clinica na lista de clinicas
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
    
    public Clinica getClinicaPrincipal() {
        Clinica clinica = null;
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT c.*, v.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, "
                    + "bv.nome_bairro as nome_bairro_veterinario, mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, "
                    + "bc.nome_bairro as nome_bairro_clinica "
                    + "FROM clinica c "
                    + "JOIN veterinario v ON (c.fk_idveterinario_clinica = v.pk_idveterinario) "
                    + "JOIN municipio mv ON (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) "
                    + "JOIN bairro bv ON (v.fk_idbairro_veterinario = bv.pk_idbairro) "
                    + "JOIN municipio mc ON (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) "
                    + "JOIN bairro bc ON (c.fk_idbairro_clinica = bc.pk_idbairro) "
                    + "WHERE c.pk_idclinica = 1 ";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Municipio Clinica
                int idMunicipioClinica = res.getInt("fk_idmunicipio_clinica");
                String nomeMunicipioClinica = res.getString("nome_municipio_clinica");
                String estadoClinica = res.getString("estado_clinica");
                Municipio municipioClinica = new Municipio(idMunicipioClinica, nomeMunicipioClinica, estadoClinica);

                //Atributos Bairro Clinica
                int idBairroClinica = res.getInt("fk_idbairro_clinica");
                String nomeBairroClinica = res.getString("nome_bairro_clinica");
                Bairro bairroClinica = new Bairro(idBairroClinica, nomeBairroClinica, municipioClinica);

                //Coloca atributos em variaveis
                //Atributos clinica
                int idClinica = res.getInt("pk_idclinica");
                String nomeClinica = res.getString("nome_clinica");
                String razaoSocialClinica = res.getString("razao_social_clinica");
                String cnpj = res.getString("cnpj_clinica");
                String emailClinica = res.getString("email_clinica");
                String rua = res.getString("rua_clinica");
                String numero = res.getString("numero_clinica");
                String cep = res.getString("cep_clinica");
                String telefone = res.getString("telefone_clinica");
                String telefoneAlternativo = res.getString("telefone_alternativo_clinica");
                String observacao = res.getString("observacao_clinica");
                LocalDate dtCadastro = null;

                //Atributos Municipio Veterinario
                int idMunicipioVeterinario = res.getInt("fk_idmunicipio_veterinario");
                String nomeMunicipioVeterinario = res.getString("nome_municipio_veterinario");
                String estadoVeterinario = res.getString("estado_veterinario");
                Municipio municipioVeterinario = new Municipio(idMunicipioVeterinario, nomeMunicipioVeterinario, estadoVeterinario);

                //Atributos Bairro Veterinario
                int idBairroVeterinario = res.getInt("fk_idbairro_veterinario");
                String nomeBairroVeterinario = res.getString("nome_bairro_veterinario");
                Bairro bairroVeterinario = new Bairro(idBairroVeterinario, nomeBairroVeterinario, municipioVeterinario);

                //Atributos Veterinario
                int idVeterinario = res.getInt("pk_idveterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String cpfVeterinario = res.getString("cpf_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                String emailVeterinario = res.getString("email_veterinario");
                String telefoneVeterinario = res.getString("telefone_veterinario");
                String ruaVeterinario = res.getString("rua_veterinario");
                String numeroVeterinario = res.getString("numero_veterinario");
                String cepVeterinario = res.getString("cep_veterinario");
                String observacaoVeterinario = res.getString("observacao_veterinario");
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);

                //Criando o objeto Veterinario
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario,
                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario, listaClinicas);

                List listaVeterinarios = getVeterinariosDaClinica(idClinica, idVeterinario);
                //Cria o objeto Clinica
                clinica = new Clinica(idClinica, nomeClinica, cnpj, emailClinica, veterinario, rua, bairroClinica, numero, cep, municipioClinica, 
                        telefone, telefoneAlternativo, observacao, dtCadastro, listaVeterinarios);
                clinica.setRazaoSocial(razaoSocialClinica);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return clinica;
        }
    }

    public Veterinario getVetResponsavelDaClinica(int idClinica){
        Veterinario veterinario = null;
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT c.*, cv.*, v.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, "
                    + "mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica "
                    + "FROM veterinario v "
                    + "JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) "
                    + "JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) "
                    + "JOIN clinica c ON (c.fk_idveterinario_clinica = v.pk_idveterinario) "
                    + "JOIN municipio mc on (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) "
                    + "JOIN bairro bc on (c.fk_idbairro_clinica = bc.pk_idbairro) "
                    + "JOIN clinica_veterinario cv on (cv.fk_idveterinario_cv = v.pk_idveterinario) "
                    + "WHERE c.pk_idclinica = ?";

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idClinica);

            res = stmt.executeQuery();
            while (res.next()) {

//                //Atributos Municipio Clinica
//                int idMunicipioClinica = res.getInt("fk_idmunicipio_clinica");
//                String nomeMunicipioClinica = res.getString("nome_municipio_clinica");
//                String estadoClinica = res.getString("estado_clinica");
//                Municipio municipioClinica= new Municipio(idMunicipioClinica, nomeMunicipioClinica, estadoClinica);
//                
//                //Atributos Bairro Clinica
//                int idBairroClinica = res.getInt("fk_idbairro_clinica");
//                String nomeBairroClinica = res.getString("nome_bairro_clinica");
//                Bairro bairroClinica = new Bairro(idBairroClinica, nomeBairroClinica, municipioClinica);
//                
//                //Coloca atributos em variaveis
//                //Atributos clinica
//                String nomeClinica = res.getString("nome_clinica");
//                String cnpj = res.getString("cnpj_clinica");
//                String emailClinica = res.getString("email_clinica");
//                String rua = res.getString("rua_clinica");
//                String numero = res.getString("numero_clinica");
//                String cep = res.getString("cep_clinica");
//                String telefone = res.getString("telefone_clinica");
//                String telefoneAlternativo = res.getString("telefone_alternativo_clinica");
//                String observacao = res.getString("observacao_clinica");
//                LocalDate dataCadastro;
//                if (res.getDate("dtCadastro_clinica") == null) {
//                    dataCadastro = null;
//                } else {
//                    dataCadastro = res.getDate("dtCadastro_clinica").toLocalDate();
//                }
                //Atributos Municipio Veterinario
                int idMunicipioVeterinario = res.getInt("fk_idmunicipio_veterinario");
                String nomeMunicipioVeterinario = res.getString("nome_municipio_veterinario");
                String estadoVeterinario = res.getString("estado_veterinario");
                Municipio municipioVeterinario = new Municipio(idMunicipioVeterinario, nomeMunicipioVeterinario, estadoVeterinario);

                //Atributos Bairro Veterinario
                int idBairroVeterinario = res.getInt("fk_idbairro_veterinario");
                String nomeBairroVeterinario = res.getString("nome_bairro_veterinario");
                Bairro bairroVeterinario = new Bairro(idBairroVeterinario, nomeBairroVeterinario, municipioVeterinario);

                //Atributos Veterinario
                int idVeterinario = res.getInt("pk_idveterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String cpfVeterinario = res.getString("cpf_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                String emailVeterinario = res.getString("email_veterinario");
                String telefoneVeterinario = res.getString("telefone_veterinario");
                String ruaVeterinario = res.getString("rua_veterinario");
                String numeroVeterinario = res.getString("numero_veterinario");
                String cepVeterinario = res.getString("cep_veterinario");
                String observacaoVeterinario = res.getString("observacao_veterinario");
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
//                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);

                //Criando o objeto Veterinario
                veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario,
                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return veterinario;
        }
    }
    
    public List<Veterinario> getVeterinariosDaClinica(int idClinica, int idVetResponsavel) {
        List<Veterinario> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql =  "SELECT v.*, c.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, \n" +
"                    mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica \n" +
"                    FROM veterinario v \n" +
"                    JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) \n" +
"                    JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) \n" +
"                    JOIN clinica_veterinario cv ON (cv.fk_idveterinario_cv = v.pk_idveterinario) \n" +
"                    JOIN clinica c ON (c.pk_idclinica = cv.fk_idclinica_cv) \n" +
"                    JOIN municipio mc on (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) \n" +
"                    JOIN bairro bc on (c.fk_idbairro_clinica = bc.pk_idbairro) \n" +
"                    WHERE cv.fk_idclinica_cv = ? UNION\n" +
"                    SELECT v.*, c.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, \n" +
"                    mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica \n" +
"                    FROM veterinario v \n" +
"                    JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) \n" +
"                    JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) \n" +
"                    JOIN clinica c ON (c.fk_idveterinario_clinica = v.pk_idveterinario) \n" +
"                    JOIN municipio mc on (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) \n" +
"                    JOIN bairro bc on (c.fk_idbairro_clinica = bc.pk_idbairro) \n" +
"                    WHERE c.fk_idveterinario_clinica = ? AND c.pk_idclinica = ? ";

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idClinica);
            stmt.setInt(2, idVetResponsavel);
            stmt.setInt(3, idClinica);

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

                //Atributos Veterinario
                int idVeterinario = res.getInt("pk_idveterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String cpfVeterinario = res.getString("cpf_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                String emailVeterinario = res.getString("email_veterinario");
                String telefoneVeterinario = res.getString("telefone_veterinario");
                String ruaVeterinario = res.getString("rua_veterinario");
                String numeroVeterinario = res.getString("numero_veterinario");
                String cepVeterinario = res.getString("cep_veterinario");
                String observacaoVeterinario = res.getString("observacao_veterinario");
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
//                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);

                //Criando o objeto Veterinario
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario,
                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario);

                //Adiciona o objeto Clinica na lista de clinicas
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
    
    public List<Veterinario> getVeterinariosDaClinica(int idClinica) {
        List<Veterinario> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT v.*, c.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, \n" +
"                    mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica \n" +
"                    FROM veterinario v \n" +
"                    JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) \n" +
"                    JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) \n" +
"                    JOIN clinica_veterinario cv ON (cv.fk_idveterinario_cv = v.pk_idveterinario) \n" +
"                    JOIN clinica c ON (c.pk_idclinica = cv.fk_idclinica_cv) \n" +
"                    JOIN municipio mc on (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) \n" +
"                    JOIN bairro bc on (c.fk_idbairro_clinica = bc.pk_idbairro) \n" +
"                    WHERE cv.fk_idclinica_cv = ? ";


            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idClinica);

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

                //Atributos Veterinario
                int idVeterinario = res.getInt("pk_idveterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String cpfVeterinario = res.getString("cpf_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                String emailVeterinario = res.getString("email_veterinario");
                String telefoneVeterinario = res.getString("telefone_veterinario");
                String ruaVeterinario = res.getString("rua_veterinario");
                String numeroVeterinario = res.getString("numero_veterinario");
                String cepVeterinario = res.getString("cep_veterinario");
                String observacaoVeterinario = res.getString("observacao_veterinario");
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
//                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);

                //Criando o objeto Veterinario
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario,
                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario);

                //Adiciona o objeto Clinica na lista de clinicas
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

    public boolean inserir(Clinica clinica) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO clinica (nome_clinica, cnpj_clinica, email_clinica, fk_idveterinario_clinica, rua_clinica, fk_idbairro_clinica, "
                    + "numero_clinica, cep_clinica, telefone_clinica, telefone_alternativo_clinica, fk_idmunicipio_clinica, dtcadastro_clinica)\n"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setString(1, clinica.getNomeClinica());
            stmt.setString(2, clinica.getCnpj());
            stmt.setString(3, clinica.getEmailClinica());
            stmt.setInt(4, clinica.getVeterinarioClinica().getId());
            stmt.setString(5, clinica.getRuaClinica());
            stmt.setInt(6, clinica.getBairroClinica().getId());
            stmt.setString(7, clinica.getNumeroClinica());
            stmt.setString(8, clinica.getCepClinica());
            stmt.setString(9, clinica.getTelefoneClinica());
            stmt.setString(10, clinica.getTelefoneAlternativoClinica());
            stmt.setInt(11, clinica.getMunicipioClinica().getId());
            if (clinica.getDataCadastro() == null) {
                stmt.setDate(12, null);
            } else {
                stmt.setDate(12, Date.valueOf(clinica.getDataCadastro()));
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
                    //Atualiza o ID da clínica no parâmetro que foi recebido pelo método
                    clinica.setIdClinica(id);
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

    public boolean excluir(Clinica clinica) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from clinica where pk_idclinica = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, clinica.getIdClinica());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluirVeterinariosDaClinica(Clinica clinica) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from clinica_veterinario where fk_idclinica_cv = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, clinica.getIdClinica());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirVeterinariosNaClinica(List<Veterinario> list, int idClinica) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO clinica_veterinario (fk_idveterinario_cv, fk_idclinica_cv) "
                    + "VALUES (?,?)";
            
            for(Veterinario vet : list){
               stmt = con.prepareStatement(sql);

                stmt.setInt(1, vet.getId());
                stmt.setInt(2, idClinica); 
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

    public boolean editar(Clinica clinica) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE clinica SET \n"
                    + "nome_clinica = ?, cnpj_clinica = ?, email_clinica = ?, fk_idveterinario_clinica = ?, rua_clinica = ?, fk_idbairro_clinica = ?, "
                    + "numero_clinica = ?, cep_clinica = ?, telefone_clinica = ?, telefone_alternativo_clinica = ?, fk_idmunicipio_clinica = ?, dtcadastro_clinica = ?\n"
                    + "WHERE pk_idclinica = ?;";
            String nomeClinica = clinica.getNomeClinica();
            String cnpj = clinica.getCnpj();
            String emailClinica = clinica.getEmailClinica();
            int veterinario = clinica.getVeterinarioClinica().getId();
            String rua = clinica.getRuaClinica();
            Bairro bairro = clinica.getBairroClinica();
            String numero = clinica.getNumeroClinica();
            String cep = clinica.getCepClinica();
            String telefonePrincipal = clinica.getTelefoneClinica();
            String telefoneAlternativo = clinica.getTelefoneAlternativoClinica();
            Municipio municipioClinica = clinica.getMunicipioClinica();
            LocalDate dataCadastro = clinica.getDataCadastro();
//            int idUsuarioOriginal = clinica.getUsuario().getIdUsuario();
            int idClinica = clinica.getIdClinica();

            stmt = con.prepareStatement(sql);
            stmt.setString(1, nomeClinica);
            stmt.setString(2, cnpj);
            stmt.setString(3, emailClinica);
            stmt.setInt(4, veterinario);
            stmt.setString(5, rua);
            stmt.setInt(6, bairro.getId());
            stmt.setString(7, numero);
            stmt.setString(8, cep);
            stmt.setString(9, telefonePrincipal);
            stmt.setString(10, telefoneAlternativo);
            stmt.setInt(11, municipioClinica.getId());
            if (dataCadastro == null) {
                stmt.setDate(12, null);
            } else {
                stmt.setDate(12, Date.valueOf(dataCadastro));
            }
            stmt.setInt(13, idClinica);
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
