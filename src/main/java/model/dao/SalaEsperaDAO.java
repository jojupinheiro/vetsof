/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

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
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Especie;
import model.classes.SalaEspera;
import model.classes.utilitario.Municipio;
import model.classes.Pet;
import model.classes.utilitario.Raca;
import model.classes.Tutor;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class SalaEsperaDAO {
    
    private Connection con;

    public SalaEsperaDAO(Connection con) {
        this.con = con;
    }

    public List<SalaEspera> getAll() {
        List<SalaEspera> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT se.*, p.*, t.*, r.*, e.*, mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, bt.nome_bairro as nome_bairro_tutor " +
                    "FROM sala_espera se " +
                    "JOIN pet p ON (se.fk_idpet_sala_espera = p.pk_idpet) " +
                    "JOIN raca r ON (p.fk_idraca_pet = r.pk_idraca) " +
                    "JOIN especie e ON (r.fk_idespecie = e.pk_idespecie) " +
                    "JOIN tutor t ON (p.fk_idtutor_pet = t.pk_idtutor) " +
                    "JOIN municipio mt ON (t.fk_idmunicipio_tutor = mt.pk_idmunicipio) " +
                    "JOIN bairro bt ON (t.fk_idbairro_tutor = bt.pk_idbairro) ";


            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);

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

                //Atributos Paciente na Sala de Espera
                int idSalaEspera = res.getInt("pk_idsala_espera");
                LocalTime horarioChegadaSalaEspera = res.getTime("chegada_sala_espera").toLocalTime();
                boolean agendadoSalaEspera = res.getBoolean("agendado_sala_espera");
                LocalTime horarioAgendadoSalaEspera;
                if (res.getTime("horario_agendado_sala_espera") != null){
                    horarioAgendadoSalaEspera = res.getTime("horario_agendado_sala_espera").toLocalTime();
                }else{
                    horarioAgendadoSalaEspera = null;
                }
                 
                boolean urgenciaSalaEspera = res.getBoolean("urgencia_sala_espera");
                
                //Cria o objeto SalaEspera
                SalaEspera paciente = new SalaEspera(idSalaEspera, pet, horarioChegadaSalaEspera, agendadoSalaEspera, horarioAgendadoSalaEspera, urgenciaSalaEspera);

                //Adiciona o objeto SalaEspera na lista de salaEsperas
                list.add(paciente);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }


    public boolean inserir(SalaEspera salaEspera) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO sala_espera " +
                    "(fk_idpet_sala_espera, chegada_sala_espera, agendado_sala_espera, horario_agendado_sala_espera, urgencia_sala_espera) " +
                    "VALUES (?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, salaEspera.getPet().getIdPet());
            stmt.setTime(2, Time.valueOf(salaEspera.getHorarioChegada()));
            stmt.setBoolean(3, salaEspera.isAgendado());
            if (salaEspera.getHorarioAgendado() != null){
                stmt.setTime(4, Time.valueOf(salaEspera.getHorarioAgendado()));
            }else{
                stmt.setNull(4, 0);
            }
            stmt.setBoolean(5, salaEspera.isUrgencia());

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
                    salaEspera.setId(id);
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

    public boolean excluir(SalaEspera salaEspera) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from sala_espera where pk_idsala_espera = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, salaEspera.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(SalaEspera salaEspera) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE sala_espera SET "
                    + "fk_idpet_sala_espera = ?, chegada_sala_espera = ?, agendado_sala_espera = ?, horario_agendado_sala_espera = ?, urgencia_sala_espera = ? "
                    + "WHERE pk_idsala_espera = ?;";
            int idPet = salaEspera.getPet().getIdPet();
            LocalTime horarioChegada = salaEspera.getHorarioChegada();
            boolean agendado = salaEspera.isAgendado();
            LocalTime horarioAgendado = salaEspera.getHorarioAgendado();
            boolean urgencia = salaEspera.isUrgencia();
            int idSalaEspera = salaEspera.getId();

            stmt = con.prepareStatement(sql);
            stmt.setInt(1, idPet);
            stmt.setTime(2, Time.valueOf(horarioChegada));
            stmt.setBoolean(3, agendado);
            if (horarioAgendado != null){
                stmt.setTime(4, Time.valueOf(horarioAgendado));
            }else{
                stmt.setNull(4, 0);
            }            
            stmt.setBoolean(5, urgencia);
            stmt.setInt(6, idSalaEspera);

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
