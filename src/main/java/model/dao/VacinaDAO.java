package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.classes.Atendimento;
import model.classes.utilitario.Bairro;
import model.classes.Clinica;
import model.classes.utilitario.Especie;
import model.classes.utilitario.Municipio;
import model.classes.Pet;
import model.classes.ProdutoVacina;
import model.classes.utilitario.Raca;
import model.classes.Tutor;
import model.classes.Vacina;
import model.classes.Veterinario;
import model.db.DB;
import model.services.ClinicaService;
import model.services.VeterinarioService;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class VacinaDAO {

    private Connection con;

    public VacinaDAO(Connection con) {
        this.con = con;
    }

    public List<Vacina> getVacinasDoPet(Pet pet) {
        List<Vacina> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT v.*, c.*, vac.*, p.*, nv.*, tv.*, t.*, mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, "
                    + "bt.nome_bairro as nome_bairro_tutor, v.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, "
                    + "mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica "
                    + "FROM vacina vac "
                    + "LEFT JOIN pet p on (vac.fk_idpet_vacina = p.pk_idpet) "
                    + "LEFT JOIN nome_vac nv on (vac.fk_idnomevac_vacina = nv.pk_idnome_vac) "
                    + "LEFT JOIN tipo_vac tv ON (vac.fk_idtipo_vac_vacina = tv.pk_idtipo_vac) "
                    + "LEFT JOIN tutor t on (p.fk_idtutor_pet = t.pk_idtutor) "
                    + "LEFT JOIN municipio mt on (t.fk_idmunicipio_tutor = mt.pk_idmunicipio) "
                    + "LEFT JOIN bairro bt on (t.fk_idbairro_tutor = bt.pk_idbairro) "
                    + "LEFT JOIN clinica c on (a.fk_idclinica_atendimento = c.pk_idclinica) "
                    + "LEFT JOIN municipio mc ON (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) "
                    + "LEFT JOIN bairro bc ON (c.fk_idbairro_clinica = bc.pk_idbairro) "
                    + "LEFT JOIN veterinario v on (a.fk_idveterinario_atendimento = v.pk_idveterinario) "
                    + "LEFT JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) "
                    + "LEFT JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) "
                    + "WHERE p.pk_idpet = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, pet.getIdPet());

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

                // Atributos ProdutoVacina
                int idProdutoVacina = res.getInt("pk_idnome_vac");
                String nomeVacina = res.getString("nome_vac");
                String tipoVacina = res.getString("tipo_vac");
                int idTipoVacina = res.getInt("pk_idtipo_vac");
                String laboratorioVacina = res.getString("laboratorio_nome_vac");
                float valorVacina = res.getFloat("valor_nome_vac");

                // Atributos VACINA
                int idVacina = res.getInt("pk_idvacina");
                LocalDate dtVacina = res.getDate("dtvacina").toLocalDate();
                boolean vacinaAplicada = res.getBoolean("status_vacina");
                String observacaoVacina = res.getString("observacao_vacina");
                int doseAtualVacina = res.getInt("dose_atual_vacina");
                int dosesTotaisVacina = res.getInt("doses_totais_vacina");
                boolean temProximaDoseVacina = res.getBoolean("tem_proxima_dose_vacina");
                float valorVacinaCadastrada = res.getFloat("valor_vacina");
                LocalDate dtProximaDoseVacina;
                if (res.getDate("dtproxima_dose_vacina") == null) {
                    dtProximaDoseVacina = null;
                } else {
                    dtProximaDoseVacina = res.getDate("dtproxima_dose_vacina").toLocalDate();
                }

                ProdutoVacina produtoVacina = new ProdutoVacina(idProdutoVacina, nomeVacina, tipoVacina, idTipoVacina, laboratorioVacina, valorVacina);

                Vacina vacina = new Vacina(idVacina, produtoVacina, pet, dtVacina, vacinaAplicada, observacaoVacina,
                        doseAtualVacina, dosesTotaisVacina, temProximaDoseVacina, dtProximaDoseVacina, valorVacinaCadastrada);

                list.add(vacina);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public List<Vacina> getVacinasDoAtendimento(Atendimento atendimento) {
        List<Vacina> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT e.*, r.*, vac.*, p.*, nv.*, tv.*, t.*, mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, "
                    + "bt.nome_bairro as nome_bairro_tutor  "
//                    + "mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica "
                    + "FROM vacina vac "
                    + "LEFT JOIN pet p on (vac.fk_idpet_vacina = p.pk_idpet) "
                    + "LEFT JOIN nome_vac nv on (vac.fk_idnome_vac_vacina = nv.pk_idnome_vac) "
                    + "LEFT JOIN tipo_vac tv ON (vac.fk_idtipo_vac_vacina = tv.pk_idtipo_vac) "
                    + "LEFT JOIN tutor t on (p.fk_idtutor_pet = t.pk_idtutor) "
                    + "LEFT JOIN municipio mt on (t.fk_idmunicipio_tutor = mt.pk_idmunicipio) "
                    + "LEFT JOIN bairro bt on (t.fk_idbairro_tutor = bt.pk_idbairro) "
                    + "LEFT JOIN raca r on (r.pk_idraca = p.fk_idraca_pet) "
                    + "LEFT JOIN especie e on (e.pk_idespecie = fk_idespecie_pet) "
                    + "WHERE vac.idatendimento_vacina = ?";
//mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario,
            
            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, atendimento.getIdAtendimento());

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos Municipio Tutor
                int idMunicipioTutor = res.getInt("fk_idmunicipio_tutor");
                String nomeMunicipioTutor = res.getString("nome_municipio_tutor");
                String estadoTutor = res.getString("estado_tutor");
                Municipio municipioTutor = new Municipio(idMunicipioTutor, nomeMunicipioTutor, estadoTutor);

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

//                //Atributos atendimento
//                int idAtendimento = res.getInt("pk_idatendimento");
//                float valorTotal = res.getFloat("valortotal_atendimento");
//                String descricao = res.getString("descricao_atendimento");
//                LocalDate dataAtendimento;
//                if (res.getDate("dt_atendimento") == null) {
//                    dataAtendimento = null;
//                } else {
//                    dataAtendimento = res.getDate("dt_atendimento").toLocalDate();
//                }
//                LocalTime horarioAtendimento;
//                if (res.getTime("horario_atendimento") == null) {
//                    horarioAtendimento = null;
//                } else {
//                    horarioAtendimento = res.getTime("horario_atendimento").toLocalTime();
//                }
//                List<Servico> servicos = new ServicoService().getServicoDoAtendimento(idAtendimento);
//                List<Exame> exames = new ExameService().getExameDoAtendimento(idAtendimento);
//
//                //Atributos Municipio Veterinario
//                int idMunicipioVeterinario = res.getInt("fk_idmunicipio_veterinario");
//                String nomeMunicipioVeterinario = res.getString("nome_municipio_veterinario");
//                String estadoVeterinario = res.getString("estado_veterinario");
//                Municipio municipioVeterinario = new Municipio(idMunicipioVeterinario, nomeMunicipioVeterinario, estadoVeterinario);
//
//                //Atributos Bairro Veterinario
//                int idBairroVeterinario = res.getInt("fk_idbairro_veterinario");
//                String nomeBairroVeterinario = res.getString("nome_bairro_veterinario");
//                Bairro bairroVeterinario = new Bairro(idBairroVeterinario, nomeBairroVeterinario, municipioVeterinario);
//
//                //Atributos Veterinario
//                int idVeterinario = res.getInt("pk_idveterinario");
//                String nomeVeterinario = res.getString("nome_veterinario");
//                String cpfVeterinario = res.getString("cpf_veterinario");
//                String crmvVeterinario = res.getString("crmv_veterinario");
//                String emailVeterinario = res.getString("email_veterinario");
//                String telefoneVeterinario = res.getString("telefone_veterinario");
//                String ruaVeterinario = res.getString("rua_veterinario");
//                String numeroVeterinario = res.getString("numero_veterinario");
//                String cepVeterinario = res.getString("cep_veterinario");
//                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");
//                String observacaoVeterinario = res.getString("observacao_veterinario");
//                List<Clinica> listaClinicas = new VeterinarioService().getClinicasDoVeterinario(idVeterinario);
//
//                //Criando o objeto Veterinario
//                Veterinario veterinario = new Veterinario(idVeterinario, nomeVeterinario, cpfVeterinario, crmvVeterinario, emailVeterinario, telefoneVeterinario,
//                        municipioVeterinario, bairroVeterinario, ruaVeterinario, numeroVeterinario, cepVeterinario, sexoVeterinario, observacaoVeterinario, listaClinicas);

//                //Atributos Municipio Clinica
//                int idMunicipioClinica = res.getInt("fk_idmunicipio_clinica");
//                String nomeMunicipioClinica = res.getString("nome_municipio_clinica");
//                String estadoClinica = res.getString("estado_clinica");
//                Municipio municipioClinica = new Municipio(idMunicipioClinica, nomeMunicipioClinica, estadoClinica);
//
//                //Atributos Bairro Clinica
//                int idBairroClinica = res.getInt("fk_idbairro_clinica");
//                String nomeBairroClinica = res.getString("nome_bairro_clinica");
//                Bairro bairroClinica = new Bairro(idBairroClinica, nomeBairroClinica, municipioClinica);
//
//                //Atributos Clinica
//                int idClinica = res.getInt("pk_idclinica");
//                String nomeClinica = res.getString("nome_clinica");
//                String cnpjClinica = res.getString("cnpj_clinica");
//                String emailClinica = res.getString("email_clinica");
//                String ruaClinica = res.getString("rua_clinica");
//                String numeroClinica = res.getString("numero_clinica");
//                String cepClinica = res.getString("cep_clinica");
//                String telefoneClinica = res.getString("telefone_clinica");
//                String telefoneAlternativoClinica = res.getString("telefone_alternativo_clinica");
//                String observacaoClinica = res.getString("observacao_clinica");
//                LocalDate dataCadastroClinica;
//                if (res.getDate("dtCadastro_clinica") == null) {
//                    dataCadastroClinica = null;
//                } else {
//                    dataCadastroClinica = res.getDate("dtCadastro_clinica").toLocalDate();
//                }
//                List listaVeterinarios = new ClinicaService().getVeterinariosDaClinica(idClinica, atendimento.getVeterinario().getId());
//
//                //Cria a clinica
//                Clinica clinica = new Clinica(idClinica, nomeClinica, cnpjClinica, emailClinica, atendimento.getVeterinario(), ruaClinica, bairroClinica, numeroClinica, cepClinica,
//                        municipioClinica, telefoneClinica, telefoneAlternativoClinica, observacaoClinica, dataCadastroClinica, listaVeterinarios);

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

                // Atributos ProdutoVacina
                int idProdutoVacina = res.getInt("pk_idnome_vac");
                String nomeVacina = res.getString("nome_vac");
                String tipoVacina = res.getString("tipo_vac");
                int idTipoVacina = res.getInt("pk_idtipo_vac");
                String laboratorioVacina = res.getString("laboratorio_nome_vac");
                float valorVacina = res.getFloat("valor_nome_vac");

                // Atributos VACINA
                int idVacina = res.getInt("pk_idvacina");
                LocalDate dtVacina = res.getDate("dtvacina").toLocalDate();
                boolean vacinaAplicada = res.getBoolean("status_vacina");
                String observacaoVacina = res.getString("observacao_vacina");
                int doseAtualVacina = res.getInt("dose_atual_vacina");
                int dosesTotaisVacina = res.getInt("doses_totais_vacina");
                boolean temProximaDoseVacina = res.getBoolean("tem_proxima_dose_vacina");
                float valorVacinaCadastrada = res.getFloat("valor_vacina");
                LocalDate dtProximaDoseVacina;
                if (res.getDate("dtproxima_dose_vacina") == null) {
                    dtProximaDoseVacina = null;
                } else {
                    dtProximaDoseVacina = res.getDate("dtproxima_dose_vacina").toLocalDate();
                }

                ProdutoVacina produtoVacina = new ProdutoVacina(idProdutoVacina, nomeVacina, tipoVacina, idTipoVacina, laboratorioVacina, valorVacina);

                Vacina vacina = new Vacina(idVacina, produtoVacina, pet, atendimento, dtVacina, vacinaAplicada, observacaoVacina,
                        doseAtualVacina, dosesTotaisVacina, temProximaDoseVacina, dtProximaDoseVacina, valorVacinaCadastrada);

                list.add(vacina);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<Vacina> getVacinasDaDiariaDaInternacao(int idDiaria) {
        List<Vacina> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT vac.*, nv.*, tv.*, dv.* FROM vacina vac "
                    + "LEFT JOIN nome_vac nv ON (vac.fk_idnome_vac_vacina = nv.pk_idnome_vac) "
                    + "LEFT JOIN tipo_vac tv ON (vac.fk_idtipo_vac_vacina = tv.pk_idtipo_vac) "
                    + "LEFT JOIN diaria_vacina dv ON (vac.pk_idvacina = dv.fk_idvacina_diaria_vacina) "
                    + "WHERE dv.fk_iddiaria_internacao_diaria_vacina = ?";
            
            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idDiaria);

            res = stmt.executeQuery();
            while (res.next()) {

 
                // Atributos ProdutoVacina
                int idProdutoVacina = res.getInt("pk_idnome_vac");
                String nomeVacina = res.getString("nome_vac");
                String tipoVacina = res.getString("tipo_vac");
                int idTipoVacina = res.getInt("pk_idtipo_vac");
                String laboratorioVacina = res.getString("laboratorio_nome_vac");
                float valorVacina = res.getFloat("valor_nome_vac");

                // Atributos VACINA
                int idVacina = res.getInt("pk_idvacina");
                LocalDate dtVacina = res.getDate("dtvacina").toLocalDate();
                boolean vacinaAplicada = res.getBoolean("status_vacina");
                String observacaoVacina = res.getString("observacao_vacina");
                int doseAtualVacina = res.getInt("dose_atual_vacina");
                int dosesTotaisVacina = res.getInt("doses_totais_vacina");
                boolean temProximaDoseVacina = res.getBoolean("tem_proxima_dose_vacina");
                float valorVacinaCadastrada = res.getFloat("valor_vacina");
                LocalDate dtProximaDoseVacina;
                if (res.getDate("dtproxima_dose_vacina") == null) {
                    dtProximaDoseVacina = null;
                } else {
                    dtProximaDoseVacina = res.getDate("dtproxima_dose_vacina").toLocalDate();
                }

                ProdutoVacina produtoVacina = new ProdutoVacina(idProdutoVacina, nomeVacina, tipoVacina, idTipoVacina, laboratorioVacina, valorVacina);

                Vacina vacina = new Vacina(idVacina, produtoVacina, dtVacina, vacinaAplicada, observacaoVacina,
                        doseAtualVacina, dosesTotaisVacina, temProximaDoseVacina, dtProximaDoseVacina, valorVacinaCadastrada);

                list.add(vacina);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public boolean inserir(Vacina vacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO vacina (fk_idpet_vacina, fk_idnome_vac_vacina, fk_idtipo_vac_vacina, "
                    + "idatendimento_vacina, dtvacina, status_vacina, observacao_vacina, dose_atual_vacina, "
                    + "doses_totais_vacina, tem_proxima_dose_vacina, dtproxima_dose_vacina, valor_vacina) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, vacina.getPet().getIdPet());
            stmt.setInt(2, vacina.getProdutoVacina().getId());
            stmt.setInt(3, vacina.getProdutoVacina().getIdTipoVacina());
//            stmt.setInt(4, vacina.getAtendimento().getIdAtendimento());
            if(vacina.getAtendimento() == null){
                stmt.setNull(4, 0);
            }else{
                stmt.setInt(4, vacina.getAtendimento().getIdAtendimento());
            }
            stmt.setDate(5, Date.valueOf(vacina.getDtVacina()));
            stmt.setBoolean(6, vacina.isAplicada());
            stmt.setString(7, vacina.getObservacao());
            stmt.setInt(8, vacina.getDoseAtual());
            stmt.setInt(9, vacina.getDosesTotais());
            stmt.setBoolean(10, vacina.isTemProximaDose());
            if (vacina.getDtProximaDose() == null) {
                stmt.setDate(11, null);
            } else {
                stmt.setDate(11, Date.valueOf(vacina.getDtProximaDose()));
            }
            stmt.setFloat(12, vacina.getValor());

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
                    vacina.setId(id);
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

    public boolean excluir(Vacina vacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from vacina where pk_idvacina = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, vacina.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluirVacinaDoAtendimento(Atendimento atendimento) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from vacina where idatendimento_vacina = ?";
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

    public boolean editar(Vacina vacina) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE vacina SET "
                    + "fk_idpet_vacina = ?, fk_idnome_vac_vacina = ?, fk_idtipo_vac_vacina = ?, idatendimento_vacina = ?, dtvacina = ?, "
                    + "status_vacina = ?, observacao_vacina = ?, dose_atual_vacina = ?, doses_totais_vacina = ?, tem_proxima_dose_vacina = ?, dtproxima_dose_vacina = ?, valor_vacina = ? "
                    + " WHERE pk_idvacina = ?;";
            int idPet = vacina.getPet().getIdPet();
            int idNomeVacina = vacina.getProdutoVacina().getId();
            int idTipoVacina = vacina.getProdutoVacina().getIdTipoVacina();
            int idAtendimento = vacina.getAtendimento().getIdAtendimento();
            LocalDate dtVacina = vacina.getDtVacina();
            boolean vacinaAplicada = vacina.isAplicada();
            String observacaoVacina = vacina.getObservacao();
            int doseAtualVacina = vacina.getDoseAtual();
            int dosesTotaisVacina = vacina.getDosesTotais();
            boolean temProximaDose = vacina.isTemProximaDose();
            LocalDate dtProximaDose = vacina.getDtProximaDose();
            float valorVacinaCadastrada = vacina.getValor();
            int idVacina = vacina.getId();

            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idPet);
            stmt.setInt(2, idNomeVacina);
            stmt.setInt(3, idTipoVacina);
            stmt.setInt(4, idAtendimento);
            stmt.setDate(5, Date.valueOf(dtVacina));
            stmt.setBoolean(6, vacinaAplicada);
            stmt.setString(7, observacaoVacina);
            stmt.setInt(8, doseAtualVacina);
            stmt.setInt(9, dosesTotaisVacina);
            stmt.setBoolean(10, temProximaDose);
            if (dtProximaDose == null) {
                stmt.setDate(11, null);
            } else {
                stmt.setDate(11, Date.valueOf(dtProximaDose));
            }
            stmt.setFloat(12, valorVacinaCadastrada);
            stmt.setInt(13, idVacina);

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
