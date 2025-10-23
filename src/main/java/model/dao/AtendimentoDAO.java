package model.dao;

import application.Principal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.classes.Administrador;
import model.classes.Atendimento;
import model.classes.utilitario.Bairro;
import model.classes.Clinica;
import model.classes.utilitario.Especie;
import model.classes.Exame;
import model.classes.ExameRealizado;
import model.classes.Funcionario;
import model.classes.utilitario.Municipio;
import model.classes.Pet;
import model.classes.utilitario.Raca;
import model.classes.Servico;
import model.classes.ServicoRealizado;
import model.classes.Tutor;
import model.classes.Usuario;
import model.classes.Veterinario;
import model.db.DB;
import model.services.ClinicaService;
import model.services.ExameRealizadoService;
import model.services.ExameService;
import model.services.ServicoRealizadoService;
import model.services.ServicoService;
import model.services.VeterinarioService;
import view.utils.MascarasFX;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class AtendimentoDAO {

    private Connection con;

    public AtendimentoDAO(Connection con) {
        this.con = con;
    }

    public List<Atendimento> getAll(int filtroSelecionado, String txtFiltro) {        
        List<Atendimento> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT a.*, p.*, c.*, t.*, v.*, e.*, r.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, mc.nome_municipio as nome_municipio_clinica, \n" +
"                    mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica, mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, bt.nome_bairro as nome_bairro_tutor\n" +
"                    FROM atendimento a \n" +
"                    LEFT JOIN pet p ON (p.pk_idpet = a.fk_idpet_atendimento) \n" +
"                    LEFT JOIN raca r ON (p.fk_idraca_pet = r.pk_idraca) \n" +
"                    LEFT JOIN especie e ON (r.fk_idespecie = e.pk_idespecie) \n" +
"                    LEFT JOIN clinica c ON (c.pk_idclinica = a.fk_idclinica_atendimento) \n" +
"                    LEFT JOIN municipio mc ON (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) \n" +
"                    LEFT JOIN bairro bc ON (c.fk_idbairro_clinica = bc.pk_idbairro) \n" +
"                    LEFT JOIN tutor t on (t.pk_idtutor = p.fk_idtutor_pet) \n" +
"                    LEFT JOIN municipio mt on (t.fk_idmunicipio_tutor = mt.pk_idmunicipio) \n" +
"                    LEFT JOIN bairro bt on (t.fk_idbairro_tutor = bt.pk_idbairro) \n" +
"                    LEFT JOIN veterinario v on (a.fk_idveterinario_atendimento = v.pk_idveterinario) \n" +
"                    LEFT JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) \n" +
"                    LEFT JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) ";
            
            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = " ";
                    break;
                case 0:
                    filtroSql = "WHERE a.pk_idatendimento = ? ";
                    break;
                case 1:
                    filtroSql = "WHERE t.nome_tutor like ? ";
                    break;
                case 2:
                    filtroSql = "WHERE p.nome_pet like ? ";
                    break;
                case 3:
                    filtroSql = "WHERE c.nome_clinica like ? ";
                    break;
                case 4:
                    filtroSql = "WHERE a.dt_atendimento BETWEEN ? AND ? ";
                    break;
                case 5:
                    filtroSql = "WHERE t.cpf_tutor like ? ";
                    break;
                case 6:
                    filtroSql = "WHERE p.rfid_pet like ?";
                    break;
                case 7:
                    filtroSql = "";
                    break;
                case 8:
                    filtroSql = "WHERE p.pk_idpet = ? ";
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
            
            switch (filtroSelecionado) {
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                    stmt.setString(1, "%" + txtFiltro + "%");
                    break;
                case 0:
                 
                case 8:
                    stmt.setString(1, txtFiltro);
                    break;
                case 4:
                    String[] datas = txtFiltro.split(" ");
                    stmt.setString(1, datas[0]);
                    stmt.setString(2, datas[1]);
                default:
                    break;
            }

            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos atendimento
                int idAtendimento = res.getInt("pk_idatendimento");
                float valorTotal = res.getFloat("valortotal_atendimento");
                String descricao = res.getString("descricao_atendimento");
                String anamneseAtendimento = res.getString("anamnese_atendimento");
                String diagnostico_atendimento = res.getString("diagnostico_atendimento");
                LocalDate dataAtendimento;
                if (res.getDate("dt_atendimento") == null) {
                    dataAtendimento = null;
                } else {
                    dataAtendimento = res.getDate("dt_atendimento").toLocalDate();
                }
                LocalTime horarioAtendimento;
                if (res.getTime("horario_atendimento") == null) {
                    horarioAtendimento = null;
                } else {
                    horarioAtendimento = res.getTime("horario_atendimento").toLocalTime();
                }
                List<ServicoRealizado> servicos = new ServicoRealizadoService().getServicosDoAtendimento(idAtendimento);
                List<ExameRealizado> exames = new ExameRealizadoService().getExamesDoAtendimento(idAtendimento);


                //Atributos Municipio Tutor
                int idMunicipioTutor = res.getInt("fk_idmunicipio_tutor");
                String nomeMunicipioTutor = res.getString("nome_municipio_tutor");
                String estadoTutor = res.getString("estado_tutor");
                Municipio municipioTutor= new Municipio(idMunicipioTutor, nomeMunicipioTutor, estadoTutor);
                
                //Atributos Bairro Tutor
                int idBairroTutor = res.getInt("fk_idbairro_tutor");
                String nomeBairroTutor = res.getString("nome_bairro_tutor");
                Bairro bairroTutor = new Bairro(idBairroTutor, nomeBairroTutor, municipioTutor);
                
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

                //Criando o objeto Tutor
                Tutor tutor = new Tutor(idTutor, cpfTutor, nomeTutor, ruaTutor, bairroTutor, numeroTutor, cepTutor, municipioTutor, tipoTutor, telefoneTutor,
                        telefoneSecundarioTutor, emailTutor, observacaoTutor, faixaRenda, sexoTutor, dtNascTutor);

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
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
                String observacaoVeterinario = res.getString("observacao_veterinario");
                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);
                        
                //Criando o objeto Veterinario
                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario, 
                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario, listaClinicas);
                
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

                //Cria a clinica
                Clinica clinica = new Clinica(idClinica, nomeClinica, cnpjClinica, emailClinica, veterinario, ruaClinica, bairroClinica, numeroClinica, cepClinica, 
                        municipioClinica, telefoneClinica, telefoneAlternativoClinica, observacaoClinica, dataCadastroClinica, listaVeterinarios);

                //Atributos Pet
                int idPet = res.getInt("pk_idpet");
                String nomePet = res.getString("nome_pet");
                double pesoPet = res.getDouble("peso_pet");
                boolean sexo = res.getBoolean("sexo_pet");
                String rfid = res.getString("rfid_pet");
                String observacaoPet = res.getString("observacao_pet");
                Boolean castrado = res.getBoolean("castrado_pet");
                Boolean adotado = res.getBoolean("adotado_pet");
                Boolean vivo = res.getBoolean("vivo_pet");
                LocalDate dataNascimentoPet;
                if (res.getDate("dtnascimento_pet") == null) {
                    dataNascimentoPet = null;
                } else {
                    dataNascimentoPet = res.getDate("dtnascimento_pet").toLocalDate();
                }
                
                String nomeRaca = res.getString("nome_raca");
                String nomeEspecie = res.getString("nome_especie");
                int idRaca = res.getInt("fk_idraca_pet");
                int idEspecie = res.getInt("fk_idespecie_pet");
                
                Especie especiePet = new Especie(idEspecie, nomeEspecie);
                Raca racaPet = new Raca(idRaca, nomeRaca, especiePet);
                
                String stringTemperamento = res.getString("temperamento_pet");
                List<String> listTemperamento = null;
                if (stringTemperamento != null){
                    String[] vetorTemperamento = stringTemperamento.split(" ");         //Separa os temperamentos, que estão salvos no banco como uma string separada por espaços, em um vetor
                    listTemperamento = Arrays.asList(vetorTemperamento);   //Adiciona os temperamentos a uma list, para poder ser incluída no objeto
                }
                
                //Cria o pet
                Pet pet = new Pet(idPet, nomePet, racaPet, pesoPet, sexo, rfid, observacaoPet, castrado, adotado, dataNascimentoPet, tutor, vivo, listTemperamento);

                //Cria o objeto Atendimento
                Atendimento atendimento = new Atendimento(idAtendimento, pet, clinica, veterinario, dataAtendimento, horarioAtendimento, valorTotal, descricao, servicos, exames);
                atendimento.setDiagnostico(diagnostico_atendimento);
                atendimento.setAnamnese(anamneseAtendimento);
                list.add(atendimento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(Atendimento atendimento) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO atendimento (fk_idpet_atendimento, fk_idclinica_atendimento, valortotal_atendimento, descricao_atendimento, dt_atendimento, horario_atendimento, fk_idveterinario_atendimento,"
                    + "anamnese_atendimento, diagnostico_atendimento)"
                    + "VALUES (?,?,?,?,?,?,?,?,?);";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setInt(1, atendimento.getPet().getIdPet());
            stmt.setInt(2, atendimento.getClinica().getIdClinica());
            stmt.setFloat(3, atendimento.getValorTotal());
            stmt.setString(4, atendimento.getDescricao());
            stmt.setDate(5, Date.valueOf(atendimento.getDataAtendimento()));
            stmt.setTime(6, Time.valueOf(atendimento.getHorarioAtendimento()));
            stmt.setInt(7, atendimento.getVeterinario().getId());
            stmt.setString(8, atendimento.getAnamnese());
            stmt.setString(9, atendimento.getDiagnostico());

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
                    atendimento.setIdAtendimento(id);
                    result = true;
                    //Depois daqui vai para o finally
                    inserirListas(atendimento);
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

    public boolean excluir(Atendimento atendimento) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from atendimento where pk_idatendimento = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, atendimento.getIdAtendimento());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(Atendimento atendimento) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE atendimento SET \n"
                    + "    fk_idpet_atendimento = ?, fk_idclinica_atendimento = ?, valortotal_atendimento = ?, "
                    + "descricao_atendimento = ?, dt_atendimento = ?, horario_atendimento = ?, fk_idveterinario_atendimento = ?, anamnese_atendimento = ?, diagnostico_atendimento = ? "
                    + "WHERE pk_idatendimento = ?;";

            int idPet = atendimento.getPet().getIdPet();
            int idClinica = atendimento.getClinica().getIdClinica();
            float valorTotal = atendimento.getValorTotal();
            String descricao = atendimento.getDescricao();
            int idAtendimento = atendimento.getIdAtendimento();
            int idVeterinario = atendimento.getVeterinario().getId();
            String anamneseAtendimento = atendimento.getAnamnese();
            String diagnosticoAtendimento = atendimento.getDiagnostico();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idPet);
            stmt.setInt(2, idClinica);
            stmt.setFloat(3, valorTotal);
            stmt.setString(4, descricao);
            if (atendimento.getDataAtendimento() == null) {
                stmt.setDate(5, null);
            } else {
                stmt.setDate(5, Date.valueOf(atendimento.getDataAtendimento()));
            }
            if (atendimento.getHorarioAtendimento() == null) {
                stmt.setTime(6, null);
            } else {
                stmt.setTime(6, Time.valueOf(atendimento.getHorarioAtendimento()));
            }
            stmt.setInt(7, idVeterinario);
            stmt.setString(8, anamneseAtendimento);
            stmt.setString(9, diagnosticoAtendimento);
            stmt.setInt(10, idAtendimento);

            stmt.executeUpdate();

            sql = "delete from servico_realizado where idatendimento_servico_realizado = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, atendimento.getIdAtendimento());
            stmt.executeUpdate();
            
            sql = "delete from exame_realizado where idatendimento_exame_realizado = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, atendimento.getIdAtendimento());
            stmt.executeUpdate();
            
            inserirListas(atendimento);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public List<Servico> getServicosRealizados(Atendimento atendimento) {
        List<Servico> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT s.*, a_s.* a.* FROM servico s"
                    + "LEFT JOIN atendimento_servico as ON (a_s.fk_idservico = s.pk_idservico)"
                    + "LEFT JOIN atendimento a ON (a_s.fk_idatendimento = a.pk_idatendimento)"
                    + "WHERE pk_idatendimento = ?";

            int idAtendimento = atendimento.getIdAtendimento();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idAtendimento);

            res = stmt.executeQuery();
            while (res.next()) {

                //Coloca atributos em variaveis
                //Atributos Servico
                int IdServico = res.getInt("pk_idservico");
                String nomeServico = res.getString("nome_servico");
                String descricao = res.getString("descricao_servico");
                float valorServico = res.getFloat("valor_servico");

                //Cria o objeto Servico
                Servico servico = new Servico(IdServico, nomeServico, valorServico, descricao);

                //Adiciona o objeto Servico na lista de servicos
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

    public boolean inserirListas(Atendimento atendimento) {
        PreparedStatement stmt = null;
        boolean result = false;
        boolean resultServico = false;
        boolean resultExame = false;
        try {
            for (ServicoRealizado serv : atendimento.getListaServico()) {
                // String SQL para INSERIR
                String sql = "INSERT INTO servico_realizado (fk_idservico_servico_realizado, idatendimento_servico_realizado, fk_idpet_servico_realizado, "
                        + "valor_servico_realizado, observacao_servico_realizado) "
                        + "VALUES (?,?,?,?,?);";
                // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
                stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                //Trocando os ???
                stmt.setInt(1, serv.getServico().getIdServico());
                stmt.setInt(2, atendimento.getIdAtendimento());
                stmt.setInt(3, serv.getPet().getIdPet());
                stmt.setFloat(4, serv.getValor());
                stmt.setString(5, serv.getObservacao());
                // Executar o scipt
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Deu certo
                    resultServico = true;
                } else {
                    //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                    throw new SQLException("Não foi possível inserir");
                }
            }

            for (ExameRealizado exam : atendimento.getListaExames()) {
                // String SQL para INSERIR
                String sql = "INSERT INTO exame_realizado (fk_idexame_exame_realizado, idatendimento_exame_realizado, fk_idpet_exame_realizado, "
                        + "valor_exame_realizado, observacao_exame_realizado, resultado_exame_realizado)"
                        + "VALUES (?,?,?,?,?,?);";
                // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
                stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                //Trocando os ???
                stmt.setInt(1, exam.getExame().getIdExame());
                stmt.setInt(2, atendimento.getIdAtendimento());
                stmt.setInt(3, exam.getPet().getIdPet());
                stmt.setFloat(4, exam.getValor());
                stmt.setString(5, exam.getObservacao());
                stmt.setString(6, exam.getResultado());
                // Executar o scipt
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Deu certo
                    resultExame = true;
                } else {
                    //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                    throw new SQLException("Não foi possível inserir");
                }
            }
            result = resultServico && resultExame;
//            System.out.println("Método inserirListas do AtendimentoDAO");
//            System.out.println("var resultServico: " + resultServico);
//            System.out.println("var resultExame: " + resultExame);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
}
