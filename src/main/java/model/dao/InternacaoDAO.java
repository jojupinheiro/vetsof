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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;
import model.classes.Clinica;
import model.classes.DiariaInternacao;
import model.classes.ExameRealizado;
import model.classes.Internado;
import model.classes.Pet;
import model.classes.ServicoRealizado;
import model.classes.Tutor;
import model.classes.Vacina;
import model.classes.Veterinario;
import model.classes.controleEstoque.Estoque;
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Especie;
import model.classes.utilitario.Municipio;
import model.classes.utilitario.Raca;
import model.db.DB;
import model.services.EstoqueService;
import model.services.ExameRealizadoService;
import model.services.ServicoRealizadoService;
import model.services.VacinaService;
import model.services.VeterinarioService;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class InternacaoDAO {
    private Connection con;

    public InternacaoDAO(Connection con) {
        this.con = con;
    }
    
    /*
    public List<Internado> getAll(int filtroSelecionado, String txtFiltro) {
        List<Internado> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT i.*, pet.*, esp.*, raca.*, t.*, v.*, mv.nome_municipio as nome_municipio_veterinario, mv.estado_municipio as estado_veterinario, bv.nome_bairro as nome_bairro_veterinario, "
                    + "mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, bt.nome_bairro as nome_bairro_tutor "
                    + "FROM internado i " +
                         "JOIN pet ON (i.fk_idpet_internado = pet.pk_idpet) " +
                         "JOIN especie esp ON (esp.pk_idespecie = pet.fk_idespecie_pet) " +
                         "JOIN raca ON (raca.pk_idraca = pet.fk_idraca_pet) " +
                         "JOIN tutor t on (t.pk_idtutor = pet.fk_idtutor_pet) " +
                         "JOIN municipio mt on (t.fk_idmunicipio_tutor = mt.pk_idmunicipio) " +
                         "JOIN bairro bt on (t.fk_idbairro_tutor = bt.pk_idbairro) " +
                         "JOIN veterinario v ON (v.pk_idveterinario = i.fk_idveterinario_internado) " +
                         "JOIN municipio mv on (v.fk_idmunicipio_veterinario = mv.pk_idmunicipio) " +
                         "JOIN bairro bv on (v.fk_idbairro_veterinario = bv.pk_idbairro) ";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case -1:
                    filtroSql = "";
                    break;
                case 0:
                    filtroSql = "WHERE cp.nome_categoria_prod like ? ";
                    break;
                case 1:
                    filtroSql = "WHERE e.aquisicao_estoque BETWEEN ? AND ? ";
                    break;
                case 2:
                    filtroSql = "WHERE e.fabricacao_estoque BETWEEN ? AND ? ";
                    break;
                case 3:
                    filtroSql = "WHERE e.validade_estoque BETWEEN ? AND ? ";
                    break;
                case 4:
                    filtroSql = "WHERE p.fabricante_produto like ? ";
                    break;
                case 5:
                    filtroSql = "WHERE p.nome_produto like ? ";
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

            switch (filtroSelecionado){
                case 0:
                case 4:
                case 5:
                    stmt.setString(1, "%" + txtFiltro + "%");
                    break;
                case 1:
                case 2:
                case 3:
                    String[] datas = txtFiltro.split(" ");
                    stmt.setString(1, datas[0]);
                    stmt.setString(2, datas[1]);
                    break;
                default:
                    break;
                
            }
            
            
            if (filtroSelecionado >= 0 && filtroSelecionado <= 3) {
                stmt.setString(1, "%" + txtFiltro + "%");
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
                    listTemperamento = Arrays.asList(vetorTemperamento);                //Adiciona os temperamentos a uma list, para poder ser incluída no objeto
                }
                
                //Cria o pet
                Pet pet = new Pet(idPet, nomePet, racaPet, pesoPet, sexo, rfid, observacaoPet, castrado, adotado, dataNascimentoPet, tutor, vivo, listTemperamento);
                
                //Atributos internado
                int idInternado = res.getInt("pk_idinternado");
                LocalDate dtInternacao = res.getDate("dtinternacao_internado").toLocalDate();
                LocalDate dtAlta = res.getDate("dtalta_internado") == null ? null : res.getDate("dtalta_internado").toLocalDate();
                float valorDiariaInternado = res.getFloat("valor_diaria_internado");
                float valorTotalInternado = res.getFloat("valor_total_internado");
                String observacoesInternado = res.getString("observacoes_internado");
                boolean internacaoAtiva = res.getBoolean("ativo_internado");
                
                List<DiariaInternacao> listaDiarias = getDiariasDaInternacao(idInternado);
                
                //Insere o Pet nos objetos Vacina, que não havia sido inserido antes
                for(DiariaInternacao item : listaDiarias){
                    for(Vacina vacina : item.getListaVacinas()){
                        vacina.setPet(pet);
                    }
                }
                
                Internado internado = new Internado(idInternado, pet, veterinario, dtInternacao, dtAlta, valorDiariaInternado, valorTotalInternado, observacoesInternado, listaDiarias, internacaoAtiva);

                list.add(internado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    */
    
    public List<Internado> getAll(int filtroSelecionado, String txtFiltro) {
        // LinkedHashMap mantém a ordem de inserção, o que pode ser útil.
        Map<Integer, Internado> internadosMap = new LinkedHashMap<>();

        ResultSet res = null;
        PreparedStatement stmt = null;

        String sql = "SELECT " +
            "i.pk_idinternado, i.dtinternacao_internado, i.dtalta_internado, i.valor_diaria_internado, i.valor_total_internado, i.observacoes_internado, i.ativo_internado, " +
            "pet.pk_idpet, pet.nome_pet, pet.peso_pet, pet.sexo_pet, pet.rfid_pet, pet.observacao_pet, pet.castrado_pet, pet.adotado_pet, pet.vivo_pet, pet.dtnascimento_pet, pet.temperamento_pet, " +
            "raca.pk_idraca, raca.nome_raca, " +
            "esp.pk_idespecie, esp.nome_especie, " +
            "t.pk_idtutor, t.cpf_tutor, t.nome_tutor, t.rua_tutor, t.numero_tutor, t.cep_tutor, t.tipo_tutor, t.telefone_tutor, t.telefone_alternativo_tutor, t.email_tutor, t.observacao_tutor, t.faixarenda_tutor, t.sexo_tutor, t.dtnascimento_tutor, " +
            "mt.pk_idmunicipio AS id_municipio_tutor, mt.nome_municipio AS nome_municipio_tutor, mt.estado_municipio AS estado_tutor, " +
            "bt.pk_idbairro AS id_bairro_tutor, bt.nome_bairro AS nome_bairro_tutor, " +
            "v.pk_idveterinario, v.nome_veterinario, v.cpf_veterinario, v.crmv_veterinario, v.email_veterinario, v.telefone_veterinario, v.rua_veterinario, v.numero_veterinario, v.cep_veterinario, v.sexo_veterinario, v.observacao_veterinario, " +
            "mv.pk_idmunicipio AS id_municipio_veterinario, mv.nome_municipio AS nome_municipio_veterinario, mv.estado_municipio AS estado_veterinario, " +
            "bv.pk_idbairro AS id_bairro_veterinario, bv.nome_bairro AS nome_bairro_veterinario, " +
            "di.pk_iddiaria_internacao, di.numero_diaria_internacao, di.sinais_clinicos_diaria_internacao, di.notas_diaria_internacao, di.tratamento_diaria_internacao, di.data_diaria_internacao " +
            "FROM internado i " +
            "LEFT JOIN pet ON i.fk_idpet_internado = pet.pk_idpet " +
            "LEFT JOIN especie esp ON esp.pk_idespecie = pet.fk_idespecie_pet " +
            "LEFT JOIN raca ON raca.pk_idraca = pet.fk_idraca_pet " +
            "LEFT JOIN tutor t ON t.pk_idtutor = pet.fk_idtutor_pet " +
            "LEFT JOIN municipio mt ON t.fk_idmunicipio_tutor = mt.pk_idmunicipio " +
            "LEFT JOIN bairro bt ON t.fk_idbairro_tutor = bt.pk_idbairro " +
            "LEFT JOIN veterinario v ON v.pk_idveterinario = i.fk_idveterinario_internado " +
            "LEFT JOIN municipio mv ON v.fk_idmunicipio_veterinario = mv.pk_idmunicipio " +
            "LEFT JOIN bairro bv ON v.fk_idbairro_veterinario = bv.pk_idbairro " +
            "LEFT JOIN diaria_internacao di ON i.pk_idinternado = di.fk_idinternado_diaria_internacao " +
            // Você precisa RECONSTRUIR a lógica do filtro aqui.
            // Exemplo: " WHERE pet.nome_pet LIKE ? "
            "ORDER BY i.pk_idinternado, di.pk_iddiaria_internacao";

        try {
            stmt = con.prepareStatement(sql);
            // Coloque aqui a lógica para aplicar o filtro (stmt.setString, etc.)
            // if (filtroSelecionado == ... ) { stmt.setString(1, "%" + txtFiltro + "%"); }

            res = stmt.executeQuery();

            while (res.next()) {
                int idInternado = res.getInt("pk_idinternado");
                Internado internado = internadosMap.get(idInternado);

                // Se o 'internado' ainda não está no map, cria o objeto completo.
                if (internado == null) {
                    // Endereço e dados do Veterinário
                    Municipio municipioVeterinario = new Municipio(res.getInt("id_municipio_veterinario"), res.getString("nome_municipio_veterinario"), res.getString("estado_veterinario"));
                    Bairro bairroVeterinario = new Bairro(res.getInt("id_bairro_veterinario"), res.getString("nome_bairro_veterinario"), municipioVeterinario);
                    // A lista de clínicas foi removida pois causava outro N+1. Carregue-a separadamente quando precisar.
                    Veterinario veterinario = new Veterinario(res.getInt("pk_idveterinario"), res.getString("nome_veterinario"), res.getString("cpf_veterinario"), res.getString("crmv_veterinario"), res.getString("email_veterinario"), res.getString("telefone_veterinario"), municipioVeterinario, bairroVeterinario, res.getString("rua_veterinario"), res.getString("numero_veterinario"), res.getString("cep_veterinario"), res.getBoolean("sexo_veterinario"), res.getString("observacao_veterinario"), new ArrayList<>());

                    // Endereço e dados do Tutor
                    Municipio municipioTutor = new Municipio(res.getInt("id_municipio_tutor"), res.getString("nome_municipio_tutor"), res.getString("estado_tutor"));
                    Bairro bairroTutor = new Bairro(res.getInt("id_bairro_tutor"), res.getString("nome_bairro_tutor"), municipioTutor);
                    LocalDate dtNascTutor = (res.getDate("dtnascimento_tutor") == null) ? null : res.getDate("dtnascimento_tutor").toLocalDate();
                    Tutor tutor = new Tutor(res.getInt("pk_idtutor"), res.getString("cpf_tutor"), res.getString("nome_tutor"), res.getString("rua_tutor"), bairroTutor, res.getString("numero_tutor"), res.getString("cep_tutor"), municipioTutor, res.getString("tipo_tutor"), res.getString("telefone_tutor"), res.getString("telefone_alternativo_tutor"), res.getString("email_tutor"), res.getString("observacao_tutor"), res.getInt("faixarenda_tutor"), res.getBoolean("sexo_tutor"), dtNascTutor);

                    // Especie, Raça e Pet
                    Especie especiePet = new Especie(res.getInt("pk_idespecie"), res.getString("nome_especie"));
                    Raca racaPet = new Raca(res.getInt("pk_idraca"), res.getString("nome_raca"), especiePet);
                    LocalDate dataNascimentoPet = (res.getDate("dtnascimento_pet") == null) ? null : res.getDate("dtnascimento_pet").toLocalDate();
                    String stringTemperamento = res.getString("temperamento_pet");
                    List<String> listTemperamento = (stringTemperamento != null) ? Arrays.asList(stringTemperamento.split(" ")) : null;
                    Pet pet = new Pet(res.getInt("pk_idpet"), res.getString("nome_pet"), racaPet, res.getDouble("peso_pet"), res.getBoolean("sexo_pet"), res.getString("rfid_pet"), res.getString("observacao_pet"), res.getBoolean("castrado_pet"), res.getBoolean("adotado_pet"), dataNascimentoPet, tutor, res.getBoolean("vivo_pet"), listTemperamento);

                    // Finalmente, o Internado
                    LocalDate dtInternacao = res.getDate("dtinternacao_internado").toLocalDate();
                    LocalDate dtAlta = (res.getDate("dtalta_internado") == null) ? null : res.getDate("dtalta_internado").toLocalDate();
                    internado = new Internado(idInternado, pet, veterinario, dtInternacao, dtAlta, res.getFloat("valor_diaria_internado"), res.getFloat("valor_total_internado"), res.getString("observacoes_internado"), new ArrayList<>(), res.getBoolean("ativo_internado"));

                    internadosMap.put(idInternado, internado);
                }

                // Agora, processa a diária (se existir nesta linha do resultado)
                int idDiaria = res.getInt("pk_iddiaria_internacao");
                if (idDiaria > 0) {
                    // Evita adicionar a mesma diária múltiplas vezes
                    boolean diariaJaExiste = internado.getDiaria().stream().anyMatch(d -> d.getId() == idDiaria);
                    if (!diariaJaExiste) {
                        DiariaInternacao diaria = new DiariaInternacao(
                            idDiaria,
                            res.getString("notas_diaria_internacao"),
                            res.getString("tratamento_diaria_internacao"),
                            new ArrayList<>(), // listaServicos - carregar sob demanda
                            new ArrayList<>(), // listaExames - carregar sob demanda
                            new ArrayList<>()  // listaConsumo - carregar sob demanda
                        );
                        diaria.setNumeroDiaria(res.getInt("numero_diaria_internacao"));
                        diaria.setSinaisClinicos(res.getString("sinais_clinicos_diaria_internacao"));
                        diaria.setData(res.getDate("data_diaria_internacao").toLocalDate());
                        diaria.setListaVacinas(new ArrayList<>()); // carregar sob demanda

                        internado.getDiaria().add(diaria);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Lidar com a exceção de forma apropriada
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
        }

        return new ArrayList<>(internadosMap.values());
    }
    
     public boolean inserirInternado(Internado internado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            //String SQL para INSERIR
            String sql = "INSERT INTO internado (fk_idpet_internado, fk_idveterinario_internado, dtinternacao_internado, "
                    + "dtalta_internado, valor_diaria_internado, valor_total_internado, observacoes_internado, ativo_internado) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            //o RETURN_GENERATE_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int idPet = internado.getPet().getIdPet();
            int idVeterinario = internado.getVeterinario().getId();
            LocalDate dtInternacao = internado.getDtInternacao();
            LocalDate dtAlta = internado.getDtAlta();
            float valorDiaria = internado.getValorDiaria();
            float valorTotal = internado.getValorTotal();
            String observacoes = internado.getObservacoes();
            boolean internacaoAtiva = internado.isInternacaoAtiva();
            
            //trocando os ??????
            stmt.setInt(1, idPet);
            stmt.setInt(2, idVeterinario);
            stmt.setDate(3, Date.valueOf(dtInternacao));
            stmt.setDate(4, dtAlta == null ? null : Date.valueOf(dtAlta));
            stmt.setFloat(5, valorDiaria);
            stmt.setFloat(6, valorTotal);
            stmt.setString(7, observacoes);
            stmt.setBoolean(8, internacaoAtiva);

            //executar o script
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                //deu certo
                //pegando o código gerado no INSERT
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    //getInt 1 pega o código que foi gerado e está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //atualiza o código do internado no parâmetro
                    //que foi recebido pelo método
                    internado.setId(id);
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
     
     public boolean editarInternado(Internado internado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE internado SET fk_idpet_internado = ?, fk_idveterinario_internado = ?, dtinternacao_internado = ?, "
                    + "dtalta_internado = ?, valor_diaria_internado = ?, valor_total_internado = ?, observacoes_internado = ?, ativo_internado = ? "
                    + "WHERE pk_idinternado = ?";
            stmt = con.prepareStatement(sql);

            int idPet = internado.getPet().getIdPet();
            int idVeterinario = internado.getVeterinario().getId();
            LocalDate dtInternacao = internado.getDtInternacao();
            LocalDate dtAlta = internado.getDtAlta();
            float valorDiaria = internado.getValorDiaria();
            float valorTotal = internado.getValorTotal();
            String observacoes = internado.getObservacoes();
            boolean internacaoAtiva = internado.isInternacaoAtiva();
            int idInternado = internado.getId();
            
            //trocando os ??????
            stmt.setInt(1, idPet);
            stmt.setInt(2, idVeterinario);
            stmt.setDate(3, Date.valueOf(dtInternacao));
            stmt.setDate(4, dtAlta == null ? null : Date.valueOf(dtAlta));
            stmt.setFloat(5, valorDiaria);
            stmt.setFloat(6, valorTotal);
            stmt.setString(7, observacoes);
            stmt.setBoolean(8, internacaoAtiva);
            stmt.setInt(9, idInternado);
            
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
    
    public List<DiariaInternacao> getDiariasDaInternacao(int idInternado) {
        List<DiariaInternacao> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM diaria_internacao " +
                         "WHERE fk_idinternado_diaria_internacao = ? ";

            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idInternado);

            res = stmt.executeQuery();
            while (res.next()) {

                //Atributos DiariaInternacao
                int idDiariaInternacao = res.getInt("pk_iddiaria_internacao");
                int numeroDiariaInternacao = res.getInt("numero_diaria_internacao");
                String sinaisClinicos = res.getString("sinais_clinicos_diaria_internacao");
                String notasDiariaInternacao = res.getString("notas_diaria_internacao");
                String tratamentoDiariaInternacao = res.getString("tratamento_diaria_internacao");
                LocalDate dtDiaria = res.getDate("data_diaria_internacao").toLocalDate();
                
                List<ExameRealizado> listaExames = new ExameRealizadoService().getExamesDaDiariaDaInternacao(idDiariaInternacao);
                List<ServicoRealizado> listaServicos = new ServicoRealizadoService().getServicosDaDiariaDaInternacao(idDiariaInternacao);
                List<Estoque> listaConsumo = new EstoqueService().getConsumoNaInternacao(idDiariaInternacao);
                List<Vacina> listaVacinas = new VacinaService().getVacinasDaDiariaDaInternacao(idDiariaInternacao);
                
                DiariaInternacao diaria = new DiariaInternacao(idDiariaInternacao, notasDiariaInternacao, tratamentoDiariaInternacao, listaServicos, listaExames, listaConsumo);
                diaria.setNumeroDiaria(numeroDiariaInternacao);
                diaria.setSinaisClinicos(sinaisClinicos);
                diaria.setData(dtDiaria);
                diaria.setListaVacinas(listaVacinas);
                //Adiciona o objeto Diaria na lista de diárias do internado
                list.add(diaria);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public boolean inserirDiaria(Internado internado, DiariaInternacao diaria) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO diaria_internacao (fk_idinternado_diaria_internacao, notas_diaria_internacao, "
                    + "tratamento_diaria_internacao, data_diaria_internacao, sinais_clinicos_diaria_internacao, numero_diaria_internacao) "
                    + "VALUES (?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //Trocando os ???
            int idInternado = internado.getId();
            String notasDiaria = diaria.getNotas();
            String tratamento = diaria.getTratamento();
            Date dataDiaria = Date.valueOf(diaria.getData());
            String sinaisClinicos = diaria.getSinaisClinicos();
            int numeroDiaria = diaria.getNumeroDiaria();
            
            stmt.setInt(1, idInternado);
            stmt.setString(2, notasDiaria);
            stmt.setString(3, tratamento);
            stmt.setDate(4, dataDiaria);
            stmt.setString(5, sinaisClinicos);
            stmt.setInt(6, numeroDiaria);

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID da diaria no parâmetro que foi recebido pelo método
                    diaria.setId(id);
                    //Depois daqui vai para o finally
                }
                
                inserirListasDaDiaria(diaria);
                
            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                result = false;
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean editarDiaria(Internado internado, DiariaInternacao diaria) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "UPDATE diaria_internacao SET notas_diaria_internacao = ?, "
                    + "tratamento_diaria_internacao = ?, data_diaria_internacao = ?, sinais_clinicos_diaria_internacao = ?, numero_diaria_internacao = ? "
                    + "WHERE pk_iddiaria_internacao = ?";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //Trocando os ???
            String notasDiaria = diaria.getNotas();
            String tratamento = diaria.getTratamento();
            Date dataDiaria = Date.valueOf(diaria.getData());
            String sinaisClinicos = diaria.getSinaisClinicos();
            int numeroDiaria = diaria.getNumeroDiaria();
            int idDiaria = diaria.getId();
            
            
            stmt.setString(1, notasDiaria);
            stmt.setString(2, tratamento);
            stmt.setDate(3, dataDiaria);
            stmt.setString(4, sinaisClinicos);
            stmt.setInt(5, numeroDiaria);
            stmt.setInt(6, idDiaria);

            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID da diaria no parâmetro que foi recebido pelo método
                    diaria.setId(id);
                    //Depois daqui vai para o finally
                }
                
                inserirListasDaDiaria(diaria);
                
            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                result = false;
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public void inserirListasDaDiaria(DiariaInternacao diaria) {
        excluirListasDaDiaria(diaria);

        //Inserir as listas
        if (diaria.getListaConsumo() != null) {
            for (Estoque item : diaria.getListaConsumo()) {
                inserirProdutoDaDiaria(diaria, item);
            }
        }
        if (diaria.getListaExames() != null) {
            for (ExameRealizado item : diaria.getListaExames()) {
                new ExameRealizadoService().salvarOuAtualizar(item);
                inserirExameDaDiaria(diaria, item);
            }
        }
        if (diaria.getListaServico() != null) {
            for (ServicoRealizado item : diaria.getListaServico()) {
                new ServicoRealizadoService().salvarOuAtualizar(item);
                inserirServicoDaDiaria(diaria, item);
            }
        }
        if (diaria.getListaVacinas() != null) {
            for (Vacina item : diaria.getListaVacinas()) {
                new VacinaService().salvarOuAtualizar(item);
                inserirVacinaDaDiaria(diaria, item);
            }
        }
    }
    
    public boolean excluirListasDaDiaria(DiariaInternacao diaria) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM consumo_internacao WHERE fk_iddiaria_internacao_consumo_internacao = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, diaria.getId());
            //executa
            stmt.executeUpdate();

            sql = "DELETE FROM diaria_exame WHERE fk_iddiaria_internacao_diaria_exame = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, diaria.getId());
            //executa
            stmt.executeUpdate();
            
            sql = "DELETE FROM diaria_servico WHERE fk_iddiaria_internacao_diaria_servico = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, diaria.getId());
            //executa
            stmt.executeUpdate();
            
            sql = "DELETE FROM diaria_vacina WHERE fk_iddiaria_internacao_diaria_vacina = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, diaria.getId());
            //executa
            stmt.executeUpdate();
            
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir as listas!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirProdutoDaDiaria(DiariaInternacao diaria, Estoque estoque){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO consumo_internacao (fk_iddiaria_internacao_consumo_internacao, "
                    + "fk_idestoque_consumo_internacao, "
                    + "quantidade_consumo_internacao) "
                    + "VALUES (?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        
            //Trocando os ???
            int idDiaria = diaria.getId();
            int idEstoque = estoque.getId();
            int quantidadeConsumo = estoque.getQuantidadeConsumida();
            
            stmt.setInt(1, idDiaria);
            stmt.setInt(2, idEstoque);
            stmt.setInt(3, quantidadeConsumo);
            
            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {

                }
            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                result = false;
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirExameDaDiaria(DiariaInternacao diaria, ExameRealizado exame){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO diaria_exame (fk_iddiaria_internacao_diaria_exame, fk_idexame_realizado_diaria_exame) "
                    + "VALUES (?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //Trocando os ???
            int idDiaria = diaria.getId();
            int idExame = exame.getId();
            
            stmt.setInt(1, idDiaria);
            stmt.setInt(2, idExame);
            
            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();

            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                result = false;
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirServicoDaDiaria(DiariaInternacao diaria, ServicoRealizado servico){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO diaria_servico (fk_iddiaria_internacao_diaria_servico, fk_idservico_realizado_diaria_servico) "
                    + "VALUES (?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //Trocando os ???
            int idDiaria = diaria.getId();
            int idServico = servico.getId();
            
            stmt.setInt(1, idDiaria);
            stmt.setInt(2, idServico);
            
            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();

            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                result = false;
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirVacinaDaDiaria(DiariaInternacao diaria, Vacina vacina){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO diaria_vacina (fk_iddiaria_internacao_diaria_vacina, fk_idvacina_diaria_vacina) "
                    + "VALUES (?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            //Trocando os ???
            int idDiaria = diaria.getId();
            int idVacina = vacina.getId();
            
            stmt.setInt(1, idDiaria);
            stmt.setInt(2, idVacina);
            
            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();

            } else {
                //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                result = false;
                throw new SQLException("Não foi possível inserir");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluir(Internado internado) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "DELETE FROM internado WHERE pk_idinternado = ?";
            stmt = con.prepareStatement(sql);
            //troca os parâmetros
            stmt.setInt(1, internado.getId());
            //executa
            stmt.executeUpdate();

            result = true;

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Exclusão");
            alert.setContentText("Não foi possível excluir o internado!");
            alert.showAndWait();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
}
