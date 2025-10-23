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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import model.classes.utilitario.Bairro;
import model.classes.Clinica;
import model.classes.utilitario.Especie;
import model.classes.prescricoes.FormatacaoPrescricao;
import model.classes.utilitario.Municipio;
import model.classes.Pet;
import model.classes.prescricoes.Prescricao;
import model.classes.utilitario.Raca;
import model.classes.Tutor;
import model.classes.Veterinario;
import model.db.DB;
import model.services.ClinicaService;
import model.services.VeterinarioService;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class PrescricaoDAO {

    private Connection con;

    public PrescricaoDAO(Connection con) {
        this.con = con;
    }

    public List<Prescricao> getAll(int filtroSelecionado, String txtFiltro) {
        List<Prescricao> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT p.*, pet.*, e.*, r.*, t.*, v.*, c.*, mc.*, bc.*, mt.nome_municipio as nome_municipio_tutor, mt.estado_municipio as estado_tutor, bt.nome_bairro as nome_bairro_tutor, "
                    + "mc.nome_municipio as nome_municipio_clinica, mc.estado_municipio as estado_clinica, bc.nome_bairro as nome_bairro_clinica "
                    + "FROM prescricao p "
                    + "LEFT JOIN pet ON (p.fk_idpet_prescricao = pet.pk_idpet) "
                    + "LEFT JOIN especie e ON (pet.fk_idespecie_pet = e.pk_idespecie) "
                    + "LEFT JOIN raca r ON (pet.fk_idraca_pet = r.pk_idraca) "
                    + "LEFT JOIN tutor t ON (pet.fk_idtutor_pet = t.pk_idtutor) "
                    + "LEFT JOIN municipio mt on (t.fk_idmunicipio_tutor = mt.pk_idmunicipio) "
                    + "LEFT JOIN bairro bt on (t.fk_idbairro_tutor = bt.pk_idbairro) "
                    + "LEFT JOIN veterinario v on (p.fk_idveterinario_prescricao = v.pk_idveterinario) "
                    + "LEFT JOIN clinica c on (c.pk_idclinica = p.fk_idclinica_prescricao) "
                    + "LEFT JOIN municipio mc ON (c.fk_idmunicipio_clinica = mc.pk_idmunicipio) "
                    + "LEFT JOIN bairro bc ON (c.fk_idbairro_clinica = bc.pk_idbairro)";

            String filtroSql = "";

            switch (filtroSelecionado) {
                case 0:
                    filtroSql = "WHERE t.cpf_tutor like ? ";
                    break;
                case 1:
                    filtroSql = "WHERE p.data_prescricao BETWEEN ? AND ? ";
                    break;
                case 2:
                    filtroSql = "WHERE e.nome_especie like ? ";
                    break;
                case 3:
                    filtroSql = "WHERE pet.nome_pet like ? ";
                    break;
                case 4:
                    filtroSql = "WHERE p.pk_idprescricao = ? ";
                    break;
                case 5:
                    filtroSql = "WHERE t.nome_tutor like ? ";
                    break;
                case 6:
                    filtroSql = "WHERE v.nome_veterinario like ? ";
                    break;

                default:
                    break;
            }

            //preparando a String sql para execução
            sql += filtroSql;
            sql += "ORDER BY pk_idprescricao;";
            stmt = con.prepareStatement(sql);

            switch (filtroSelecionado){
                case 0:
                case 2:
                case 3:
                case 5:
                case 6:
                    stmt.setString(1, "%" + txtFiltro + "%");
                    break;
                case 1:
                    String[] datas = txtFiltro.split(" ");
                    stmt.setString(1, datas[0]);
                    stmt.setString(2, datas[1]);
                    break;
                case 4:
                    stmt.setString(1, txtFiltro);
                    break;
                default:
                    break;
            }
            

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

                //Atributos Veterinario
                int idVeterinario = res.getInt("pk_idveterinario");
                String nomeVeterinario = res.getString("nome_veterinario");
                String cpfVeterinario = res.getString("cpf_veterinario");
                String crmvVeterinario = res.getString("crmv_veterinario");
                String emailVeterinario = res.getString("email_veterinario");
                boolean sexoVeterinario = res.getBoolean("sexo_veterinario");

                //Criando o objeto Veterinario
                Veterinario veterinario = new Veterinario();
                veterinario.setNome(nomeVeterinario);
                veterinario.setId(idVeterinario);
                veterinario.setCpf(cpfVeterinario);
                veterinario.setCrmv(crmvVeterinario);
                veterinario.setEmail(emailVeterinario);
                veterinario.setSexo(sexoVeterinario);

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

                // Atributos Prescrição
                int idPrescricao = res.getInt("pk_idprescricao");
                String observacaoPrescricao = res.getString("observacao_prescricao");
                LocalDate dataPrescricao = res.getDate("data_prescricao").toLocalDate();

                Map<String, Map<String, String[]>> listaProdutos;
                listaProdutos = getProdutosDaPrescricao(idPrescricao);

                // Cria a Prescricao
                Prescricao prescricao = new Prescricao(idPrescricao, listaProdutos, observacaoPrescricao, dataPrescricao, clinica, veterinario, pet);

                list.add(prescricao);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }

    public Map<String, Map<String, String[]>> getProdutosDaPrescricao(int idPrescricao) {
        Map<String, Map<String, String[]>> listaProdutos = new TreeMap<>();
        Map<String, String[]> produtoReceitado;
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT p.pk_idprescricao, pp.* "
                    + "FROM prescricao p "
                    + "LEFT JOIN produto_prescrito pp ON (p.pk_idprescricao = pp.fk_idprescricao_produto_prescrito)"
                    + "WHERE p.pk_idprescricao = ?";

            //preparando a String sql para execução
            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idPrescricao);

            res = stmt.executeQuery();
            while (res.next()) {
                String formaDeUso = res.getString("forma_uso_produto_prescrito");
                String nomeProduto = res.getString("nome_produto_prescrito");
                String quantidade = res.getString("quantidade_produto_prescrito");
                String posologia = res.getString("posologia_produto_prescrito");

                String[] qtdEPosologia = new String[2];
                qtdEPosologia[0] = quantidade;
                qtdEPosologia[1] = posologia;

                if (listaProdutos.containsKey(formaDeUso)) {
                    listaProdutos.get(formaDeUso).put(nomeProduto, qtdEPosologia);
                } else {
                    produtoReceitado = new TreeMap<>();
                    produtoReceitado.put(nomeProduto, qtdEPosologia);
                    listaProdutos.put(formaDeUso, produtoReceitado);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return listaProdutos;
        }
    }

    public boolean inserir(Prescricao prescricao) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO prescricao (fk_idpet_prescricao, fk_idveterinario_prescricao, fk_idclinica_prescricao, "
                    + "idatendimento_prescricao, observacao_prescricao, data_prescricao) "
                    + "VALUES (?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setInt(1, prescricao.getPet().getIdPet());
            stmt.setInt(2, prescricao.getVeterinario().getId());
            stmt.setInt(3, prescricao.getClinica().getIdClinica());
            try {
                stmt.setInt(4, prescricao.getAtendimento().getIdAtendimento());
            } catch (NullPointerException e) {
                stmt.setNull(4, 0);
            }
            stmt.setString(5, prescricao.getObservacoes());
            if (prescricao.getData() == null) {
                stmt.setDate(6, null);
            } else {
                stmt.setDate(6, Date.valueOf(prescricao.getData()));
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
                    //Atualiza o ID do prescricao no parâmetro que foi recebido pelo método
                    prescricao.setId(id);
                    inserirProdutosPrescritos(prescricao);
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
    
    public boolean inserirProdutosPrescritos(Prescricao prescricao) {
        PreparedStatement stmt = null;
        boolean result = false;
        Map<String, Map<String, String[]>> listaProdutos = prescricao.getListaProdutos();
        Set<String> formasDeUso = listaProdutos.keySet();
        Set<String> listaProdutosDaFormaDeUso;
        String sql = "";
        
        try {
            for(String forma : formasDeUso){
                listaProdutosDaFormaDeUso = listaProdutos.get(forma).keySet();
                
                for (String produto : listaProdutosDaFormaDeUso) {
                    String[] dados = listaProdutos.get(forma).get(produto);
                    String quantidade = dados[0];
                    String posologia = dados[1];
                    
                    // String SQL para INSERIR
                    sql = "INSERT INTO produto_prescrito (fk_idprescricao_produto_prescrito, forma_uso_produto_prescrito, "
                            + "nome_produto_prescrito, quantidade_produto_prescrito, posologia_produto_prescrito) "
                            + "VALUES (?,?,?,?,?)";
                    // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
                    stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    
                    //Trocando os ???
                    stmt.setInt(1,prescricao.getId());
                    stmt.setString(2, forma);
                    stmt.setString(3, produto);
                    stmt.setString(4, quantidade);
                    stmt.setString(5, posologia);
                    int rowsAffected = stmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        // Deu certo
                        // Pegando o código gerado no insert
                        ResultSet rs = stmt.getGeneratedKeys();
                        result = true;
                    } else {
                        //falhou e vamos gerar uma exception para que o código caia automaticamente dentro do catch e depois no finally
                        throw new SQLException("Não foi possível inserir");
                    }
                }
            }
            // Executar o scipt
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean excluir(Prescricao prescricao) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from prescricao where pk_idprescricao = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, prescricao.getId());
            stmt.executeUpdate();
            excluirProdutosPrescritos(prescricao);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean excluirProdutosPrescritos(Prescricao prescricao) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "delete from produto_prescrito where fk_idprescricao_produto_prescrito = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, prescricao.getId());
            stmt.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }

    public boolean editar(Prescricao prescricao) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE prescricao SET "
                    + "fk_idpet_prescricao = ?, fk_idveterinario_prescricao = ?, fk_idclinica_prescricao = ?,  "
                    + "idatendimento_prescricao = ?, observacao_prescricao = ?, data_prescricao = ? "
                    + "WHERE pk_idprescricao = ?;";
            int idPet = prescricao.getPet().getIdPet();
            int idVeterinario = prescricao.getVeterinario().getId();
            int idClinica = prescricao.getClinica().getIdClinica();
            int idAtendimento;
            
            String observacoes = prescricao.getObservacoes();
            LocalDate dataPrescricao = prescricao.getData();
            int idPrescricao = prescricao.getId();

            stmt = con.prepareStatement(sql);

            stmt.setInt(1, idPet);
            stmt.setInt(2, idVeterinario);
            stmt.setInt(3, idClinica);
            try {
                idAtendimento = prescricao.getAtendimento().getIdAtendimento();
                stmt.setInt(4, idAtendimento);
            } catch (NullPointerException e) {
                stmt.setNull(4, 0);
            }
            
            stmt.setString(5, observacoes);
            if (prescricao.getData() == null){
                stmt.setDate(6, null);
            }else{
                stmt.setDate(6, Date.valueOf(dataPrescricao));
            }
            stmt.setInt(7, idPrescricao);

            excluirProdutosPrescritos(prescricao);
            inserirProdutosPrescritos(prescricao);
            
            stmt.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public List<FormatacaoPrescricao> getFormatacaoDoModeloDaPrescricao(int modelo){
        List<FormatacaoPrescricao> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM formatacao_prescricao WHERE modelo = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, modelo);

            res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("pk_idformatacao_prescricao");
                int item = res.getInt("item");
                int recuo = res.getInt("recuo");
                int tamanho = res.getInt("tamanho");
                int espacamento = res.getInt("espacamento");
                boolean presente = res.getBoolean("presente");
                boolean modeloAtivo = res.getBoolean("modelo_ativo");
                boolean negrito = res.getBoolean("negrito");
                boolean italico = res.getBoolean("italico");
                String texto = res.getString("texto");
                String cor = res.getString("cor");
                int alinhamento = res.getInt("alinhamento");
                String fonte = res.getString("fonte");

                // Cria a formatacaoPrescricao
                FormatacaoPrescricao formatacaoPrescricao = new FormatacaoPrescricao(id, item, recuo, modelo, 
                        presente, modeloAtivo, negrito, italico, texto, cor, alinhamento, fonte, tamanho, espacamento);

                list.add(formatacaoPrescricao);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public List<FormatacaoPrescricao> getFormatacaoAtivaDaPrescricao(){
        List<FormatacaoPrescricao> list = new ArrayList<>();
        ResultSet res = null;
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM formatacao_prescricao WHERE modelo_ativo = 1 ORDER BY pk_idformatacao_prescricao";
            stmt = con.prepareStatement(sql);

            res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("pk_idformatacao_prescricao");
                int item = res.getInt("item");
                int recuo = res.getInt("recuo");
                int modelo = res.getInt("modelo");
                int tamanho = res.getInt("tamanho");
                int espacamento = res.getInt("espacamento");
                boolean presente = res.getBoolean("presente");
                boolean modeloAtivo = res.getBoolean("modelo_ativo");
                boolean negrito = res.getBoolean("negrito");
                boolean italico = res.getBoolean("italico");
                String texto = res.getString("texto");
                String cor = res.getString("cor");
                int alinhamento = res.getInt("alinhamento");
                String fonte = res.getString("fonte");

                // Cria a formatacaoPrescricao
                FormatacaoPrescricao formatacaoPrescricao = new FormatacaoPrescricao(id, item, recuo, modelo, presente,
                        modeloAtivo, negrito, italico, texto, cor, alinhamento, fonte, tamanho, espacamento);

                list.add(formatacaoPrescricao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeResultSet(res);
            DB.closeStatement(stmt);
            return list;
        }
    }
    
    public boolean editarFormatacao(FormatacaoPrescricao form) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE formatacao_prescricao SET "
                    + "item = ?, tamanho = ?, recuo = ?, modelo = ?, texto = ?, cor = ?, fonte = ?, alinhamento = ?, "
                    + "negrito = ?, italico = ?, presente = ?, modelo_ativo = ?, espacamento = ? "
                    + "WHERE pk_idformatacao_prescricao = ?;";
            int item = form.getItem();
            int tamanho = form.getTamanho();
            int recuo = form.getRecuo();
            int modelo = form.getModelo();
            String texto = form.getTexto();
            String cor = form.getCor();
            String fonte = form.getFonte();
            int alinhamento = form.getAlinhamento();
            int espacamento = form.getEspacamento();
            Boolean negrito = form.isNegrito();
            Boolean italico = form.isItalico();
            Boolean presente = form.isPresente();
            Boolean modeloAtivo = form.isModeloAtivo();
            int id = form.getId();


            stmt = con.prepareStatement(sql);

            stmt.setInt(1, item);
            stmt.setInt(2, tamanho);
            stmt.setInt(3, recuo);
            stmt.setInt(4, modelo);
            stmt.setString(5, texto);
            stmt.setString(6, cor);
            stmt.setString(7, fonte);
            stmt.setInt(8, alinhamento);
            stmt.setBoolean(9, negrito);
            stmt.setBoolean(10, italico);
            stmt.setBoolean(11, presente);
            stmt.setBoolean(12, modeloAtivo);
            stmt.setInt(13, espacamento);
            stmt.setInt(14, id);

            stmt.executeUpdate();
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(stmt);
            return result;
        }
    }
    
    public boolean inserirFormatacao(FormatacaoPrescricao form) {
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            // String SQL para INSERIR
            String sql = "INSERT INTO formatacao_prescricao (item, texto, cor, negrito, italico, recuo, "
                    + "modelo, alinhamento, fonte, presente, modelo_ativo, tamanho, espacamento) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            // O RETURN_GENERATED_KEYS retorna a chave primária gerada no momento do INSERT
            stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Trocando os ???

            stmt.setInt(1, form.getItem());
            try {stmt.setString(2, form.getTexto());} catch (NullPointerException e)  {stmt.setNull(2, 0);}
            try {stmt.setString(3, form.getCor());} catch (NullPointerException e)  {stmt.setNull(3, 0);}
            try {stmt.setBoolean(4, form.isNegrito());} catch (NullPointerException e) {stmt.setNull(4, 0);}
            try {stmt.setBoolean(5, form.isItalico());} catch (NullPointerException e)  {stmt.setNull(5, 0);}
            try {stmt.setInt(6, form.getRecuo());} catch (NullPointerException e)  {stmt.setNull(6, 0);}
            stmt.setInt(7, form.getModelo());
            try {stmt.setInt(8, form.getAlinhamento());} catch (NullPointerException e)  {stmt.setNull(8, 0);}
            try {stmt.setString(9, form.getFonte());} catch (NullPointerException e)  {stmt.setNull(9, 0);}
            try {stmt.setBoolean(10, form.isPresente());} catch (NullPointerException e)  {stmt.setNull(10, 0);}
            try {stmt.setBoolean(11, form.isModeloAtivo());} catch (NullPointerException e)  {stmt.setNull(11, 0);}
            try {stmt.setInt(12, form.getTamanho());} catch (NullPointerException e)  {stmt.setNull(12, 0);}
            try {stmt.setInt(13, form.getEspacamento());} catch (NullPointerException e)  {stmt.setNull(13, 0);}
                    
                    
            // Executar o scipt
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Deu certo
                // Pegando o código gerado no insert
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // getInt(1) pega o código que foi gerado e que está no primeiro campo do resultSet
                    int id = rs.getInt(1);
                    //Atualiza o ID do prescricao no parâmetro que foi recebido pelo método
                    form.setId(id);
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
    
    public boolean definirModeloFormatacaoComoAtivo(int modelo){
        PreparedStatement stmt = null;
        boolean result = false;
        try {
            String sql = "UPDATE formatacao_prescricao SET "
                    + "modelo_ativo = 0 "
                    + "WHERE modelo_ativo = 1;";
            
            stmt = con.prepareStatement(sql);
            stmt.executeUpdate();
                    
            sql = "UPDATE formatacao_prescricao SET "
                    + "modelo_ativo = 1 "
                    + "WHERE modelo = ?";

            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, modelo);
            
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
