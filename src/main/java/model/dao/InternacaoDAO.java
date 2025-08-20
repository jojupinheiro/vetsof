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
