package model.dao;

import application.Principal;
import application.TelaListaPetController;
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
import javafx.scene.control.Alert;
import model.classes.Administrador;
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Especie;
import model.classes.Funcionario;
import model.classes.utilitario.Municipio;
import model.classes.Pet;
import model.classes.utilitario.Raca;
import model.classes.Tutor;
import model.classes.Usuario;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class PetDAO {

    private Connection con;

    public PetDAO(Connection con) {
        this.con = con;
    }

    public List<Pet> getAll(int filtroSelecionado, String txtFiltro) {
        //lista temporária dos departamentos
        List<Pet> list = new ArrayList<>();
        //listagem dos registros que virão do banco
        ResultSet res = null;
        //um statement é um objeto que executa o script SQL
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT pet.*, t.*, mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, bt.nome_bairro as nome_bairro_tutor, e.*, r.* FROM pet\n"
                    + " JOIN tutor t on (pet.fk_idtutor_pet = t.pk_idtutor)"
                    + " JOIN municipio mt on (t.fk_idmunicipio_tutor = mt.pk_idmunicipio)"
                    + " JOIN bairro bt on (t.fk_idbairro_tutor = bt.pk_idbairro)"
                    + " JOIN especie e on (pet.fk_idespecie_pet = e.pk_idespecie)"
                    + " JOIN raca r on (pet.fk_idraca_pet = r.pk_idraca)";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = " ";
                    break;
                case 0:
                    filtroSql = "WHERE pet.nome_pet like ? ";
                    break;
                case 1:
                    filtroSql = "WHERE e.nome_especie like ? ";
                    break;
                case 2:
                    filtroSql = "WHERE r.nomr_raca like ? ";
                    break;
                case 3:
                    filtroSql = "WHERE pet.sexo_pet = 1 ";
                    break;
                case 4:
                    filtroSql = "WHERE pet.sexo_pet = 0 ";
                    break;
                case 5:
                    filtroSql = "WHERE t.nome_tutor like ? ";
                    break;
                case 6:
                    filtroSql = "WHERE pet.castrado_pet = 1 ";
                    break;
                case 7:
                    filtroSql = "WHERE pet.castrado_pet = 0 ";
                    break;
                case 8:
                    filtroSql = "WHERE pet.adotado_pet = 1 ";
                    break;
                case 9:
                    filtroSql = "WHERE pet.adotado_pet = 0 ";
                    break;
                case 10:
                    filtroSql = "WHERE pet.rfid_pet like ? ";
                    break;
                case 11:
                    filtroSql = "WHERE pet.vivo_pet = 1 ";
                    break;
                case 12:
                    filtroSql = "WHERE pet.vivo_pet = 0 ";
                    break;
                case 13:
                    filtroSql = "WHERE t.pk_idtutor = ? ";
                    break;
                default:
                    break;
            }

            //preparando a String sql para execução
            sql += filtroSql;
            sql += "ORDER BY pet.nome_pet";
            stmt = con.prepareStatement(sql);
            if (txtFiltro == null) {
                txtFiltro = "";
            }

            if (filtroSelecionado == 0 || filtroSelecionado == 1 || filtroSelecionado == 2
                    || filtroSelecionado == 5 || filtroSelecionado == 10) {
                stmt.setString(1, "%" + txtFiltro + "%");

            } else if(filtroSelecionado == 13){
                stmt.setString(1, txtFiltro);
            }
//            

            //executa o script sql
            //e guarda o resultado dentro do res
            res = stmt.executeQuery();
            //percorrer o res e ir criando objetos
            while (res.next()) {
                //Coloca atributos em variaveis

                //Atributos Municipio Tutor
                int idMunicipioTutor = res.getInt("fk_idmunicipio_tutor");
                String nomeMunicipioTutor = res.getString("nome_municipio_tutor");
                String estadoTutor = res.getString("estado_tutor");
                Municipio municipioTutor= new Municipio(idMunicipioTutor, nomeMunicipioTutor, estadoTutor);
                
                //Atributos Bairro Tutor
                int idBairroTutor = res.getInt("fk_idbairro_tutor");
                String nomeBairroTutor = res.getString("nome_bairro_tutor");
                Bairro bairroTutor = new Bairro(idBairroTutor, nomeBairroTutor, municipioTutor);
                
                //Atributos Tutor
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

                //Atributos pet
                int idPet = res.getInt("pet.pk_idpet");
                String nomePet = res.getString("pet.nome_pet");
                double pesoPet = res.getDouble("pet.peso_pet");
                boolean sexo = res.getBoolean("sexo_pet");
                String rfid = res.getString("rfid_pet");
                String observacao = res.getString("observacao_pet");
                boolean castrado = res.getBoolean("castrado_pet");
                boolean adotado = res.getBoolean("adotado_pet");
                boolean vivo = res.getBoolean("vivo_pet");

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
                
                //Criando o objeto Pet 
                Pet pet = new Pet(idPet, nomePet, racaPet, pesoPet, sexo, rfid, observacao, castrado, adotado, dataNascimentoPet, tutor, vivo, listTemperamento);

//                System.out.println(pet);
                list.add(pet);

            }
        } catch (Exception e) {
            //aqui entra quando dá erro
            e.printStackTrace();
        } finally {
            //entra sempre, dando erro ou não
            //fechar conexões e retornar resultados
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return list;
        }
    }

    public List<Especie> getEspecies() {
        List<Especie> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT concat(Upper(substr(nome_especie, 1,1)), lower(substr(nome_especie, 2,length(nome_especie)))) as especie, pk_idespecie FROM especie order by especie";
            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();
            while (res.next()) {
                String nomeEspecie = res.getString("especie");
                int idEspecie = res.getInt("pk_idespecie");
                Especie especie = new Especie(idEspecie, nomeEspecie);
                list.add(especie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return list;
        }
    }

    public boolean inserirEspecie(Especie especie) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO especie (nome_especie) VALUE (?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setString(1, especie.getNome());

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
//                    int id = rs.getInt(1);
                    //Atualiza o ID da clínica no parâmetro que foi recebido pelo método
//                    especie.setIdExame(id);
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

    public boolean excluirEspecie(Especie especie) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM especie WHERE pk_idespecie = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, especie.getId());
            //executa
            stmt.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir a espécie!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean inserirRaca(Raca raca) {
        PreparedStatement stmt = null;
//        ResultSet res = null;
        boolean result = false;

        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO raca (nome_raca, fk_idespecie) VALUE (?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???
            stmt.setString(1, raca.getNome());
            stmt.setInt(2, raca.getEspecie().getId());

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
//                    int id = rs.getInt(1);
                    //Atualiza o ID da clínica no parâmetro que foi recebido pelo método
//                    especie.setIdExame(id);
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

    public List<Raca> getRacas(Especie especie) {
        List<Raca> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;

        try {
            String sql = "SELECT concat(Upper(substr(nome_raca, 1,1)), lower(substr(nome_raca, 2,length(nome_raca)))) as raca, pk_idraca FROM raca "
                    + "JOIN especie ON (especie.pk_idespecie = raca.fk_idespecie) "
                    + "where especie.pk_idespecie = ? "
                    + "order by raca";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, especie.getId());
            res = stmt.executeQuery();
            while (res.next()) {
                String nomeRaca = res.getString("raca");
                int idRaca = res.getInt("pk_idRaca");
                Raca raca = new Raca(idRaca, nomeRaca, especie);
                list.add(raca);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            DB.closeResultSet(res);
            return list;
        }
    }

    public boolean excluirRaca(Raca raca) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        boolean result = false;
   
        try {
            String sql = "DELETE FROM raca WHERE pk_idraca = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, raca.getId());
            //executa
            stmt.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir a raça!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    //método que faz inserções no banco de dados
    public boolean inserir(Pet pet) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            //String SQL para INSERIR
            String sql = "INSERT INTO pet (fk_idtutor_pet, nome_pet, fk_idraca_pet, peso_pet, fk_idespecie_pet, sexo_pet, rfid_pet, "
                    + "castrado_pet, vivo_pet, dtnascimento_pet, observacao_pet, adotado_pet, temperamento_pet) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String temperamento = "";
            for (String item : pet.getListaTemperamento()){
                temperamento += item + " ";
            }
            
            //trocando os ??????
            stmt.setInt(1, pet.getTutorPet().getIdTutor());
            stmt.setString(2, pet.getNomePet());
            stmt.setInt(3, pet.getRaca().getId());
            stmt.setDouble(4, pet.getPesoPet());
            stmt.setInt(5, pet.getRaca().getEspecie().getId());
            stmt.setBoolean(6, pet.isSexoPet());
            stmt.setString(7, pet.getRfid());
            stmt.setBoolean(8, pet.isCastrado());
            stmt.setBoolean(9, pet.isVivo());

            if (pet.getDataNascimentoPet() == null) {
                stmt.setDate(10, null);
            } else {
                stmt.setDate(10, Date.valueOf(pet.getDataNascimentoPet()));
            }
            stmt.setString(11, pet.getObservacao());
            stmt.setBoolean(12, pet.isAdotado());
            stmt.setString(13, temperamento);

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

    public boolean excluir(Pet pet) {
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
            alert.setContentText("Não foi possível excluir o pet!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(Pet pet) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE pet SET  fk_idtutor_pet = ?, nome_pet = ?, fk_idraca_pet = ?, peso_pet = ?, fk_idespecie_pet = ?, "
                    + "castrado_pet = ?, vivo_pet = ?, dtnascimento_pet = ?, rfid_pet = ?, sexo_pet = ?, observacao_pet = ?, adotado_pet = ?, temperamento_pet = ? WHERE pk_idpet = ?";
            stmt = con.prepareStatement(sql);
            
            String temperamento = "";
            for (String item : pet.getListaTemperamento())   temperamento += item + " ";
                
            //troca os parâmetros
            stmt.setInt(1, pet.getTutorPet().getIdTutor());
            stmt.setString(2, pet.getNomePet());
            stmt.setInt(3, pet.getRaca().getId());
            stmt.setDouble(4, pet.getPesoPet());
            stmt.setInt(5, pet.getRaca().getEspecie().getId());
            stmt.setBoolean(6, pet.isCastrado());
            stmt.setBoolean(7, pet.isVivo());
            if (pet.getDataNascimentoPet() == null) {
                stmt.setDate(8, null);
            } else {
                stmt.setDate(8, Date.valueOf(pet.getDataNascimentoPet()));
            }
            stmt.setString(9, pet.getRfid());
            stmt.setBoolean(10, pet.isSexoPet());
            stmt.setString(11, pet.getObservacao());
            stmt.setBoolean(12, pet.isAdotado());
            stmt.setString(13, temperamento);
            stmt.setInt(14, pet.getIdPet());
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
