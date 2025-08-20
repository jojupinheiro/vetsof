package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.classes.utilitario.Bairro;
import model.classes.Clinica;
import model.classes.utilitario.Municipio;
import model.classes.Veterinario;
import model.exceptions.ValidacaoException;
import model.services.ClinicaService;
import model.services.VeterinarioService;
import model.services.UtilitarioService;
import view.utils.MascarasFX;
import view.utils.Utils;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroVeterinarioController implements Initializable {

    @FXML    private Button btnAdicionarClinica;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnDocumentos;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserirBairro;
    @FXML    private Button btnInserirClinica;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnLimparClinicas;
    @FXML    private Button btnRemoverClinica;
    @FXML    private Button btnSalvar;
    @FXML    private Button btnVerClinicas;
    @FXML    private Label lblNome;
    @FXML    private Label lblErroBairro;
    @FXML    private Label lblErroCep;
    @FXML    private Label lblErroCidade;
    @FXML    private Label lblErroCpf;
    @FXML    private Label lblErroNome;
    @FXML    private Label lblErroRua;
    @FXML    private Label lblErroSexo;
    @FXML    private Label lblErroNumero;
    @FXML    private Label lblErroTelefone;
    @FXML    private ListView<Clinica> listViewClinicas;
    @FXML    private RadioButton rbSexoF;
    @FXML    private RadioButton rbSexoM;
    @FXML    private SearchableComboBox<Bairro> scmbBairro;
    @FXML    private SearchableComboBox<Municipio> scmbMunicipio;
    @FXML    private SearchableComboBox<Clinica> scmbClinica;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextField txtCpf;
    @FXML    private TextField txtCrmv;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtNumero;
    @FXML    private TextField txtEmail;
    @FXML    private TextField txtCep;
    @FXML    private TextField txtRua;
    @FXML    private TextField txtTelefone;

    private Veterinario veterinario;
    List<Clinica> listaClinicas;
    List<Clinica> listaClinicasSelecionadas = new ArrayList<>();

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
        // Carregando o veterinario para os campos da tela
        txtNome.setText(veterinario.getNome());           //Setar o nome
        txtCpf.setText(Utils.imprimeCPF(veterinario.getCpf()));             //Setar o CPF ou CNPJ
        txtCrmv.setText(veterinario.getCrmv());
        txtRua.setText(veterinario.getRua());             //Setar a rua
        txtNumero.setText(veterinario.getNumero());       //Setar o número do endereço
        scmbMunicipio.getSelectionModel().select(veterinario.getMunicipio()); //Setar o municipio
        
        List<Bairro> listaBairros = new UtilitarioService().getBairros(veterinario.getMunicipio());
        ObservableList<Bairro> listaObsBairro = FXCollections.observableArrayList(listaBairros);
        scmbBairro.setItems(listaObsBairro);
        scmbBairro.getSelectionModel().select(veterinario.getBairro());    //Setar o bairro
        txtObservacao.setText(veterinario.getObservacao());          //Setar a observacao
        txtTelefone.setText(veterinario.getTelefone());              //Setar o telefone principal
        if (veterinario.isSexo()) {                                         //Seta o sexo
            rbSexoM.setSelected(true);
        } else {
            rbSexoF.setSelected(true);
        }
        txtEmail.setText(veterinario.getEmail());                    //Seta o email
        txtCep.setText(veterinario.getCep());                             //Seta o Cep
        lblNome.setText(veterinario.getNome());
        this.listaClinicasSelecionadas = veterinario.getListaClinicas();
        ObservableList<Clinica> listaObsClinicas = FXCollections.observableArrayList(listaClinicasSelecionadas);
        listViewClinicas.setItems(listaObsClinicas);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraCEP(txtCep);
        MascarasFX.mascaraTelefone(txtTelefone);
        MascarasFX.mascaraCPF(txtCpf);
        
        //Tooltip
        Tooltip dicaAdicionarMunicipio = new Tooltip("Cadastrar novo município");
        dicaAdicionarMunicipio.setShowDelay(Duration.ZERO);
        btnInserirMunicipio.setTooltip(dicaAdicionarMunicipio);
        
        Tooltip dicaAdicionarBairro = new Tooltip("Cadastrar novo bairro");
        dicaAdicionarBairro.setShowDelay(Duration.ZERO);
        btnInserirBairro.setTooltip(dicaAdicionarBairro);
        
        Tooltip dicaAdicionarClinica = new Tooltip("Cadastrar nova clínica");
        dicaAdicionarClinica.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirClinica.setTooltip(dicaAdicionarClinica);
        
        Tooltip dicaAdicionarClinicaNaLista = new Tooltip("Inserir clínica selecionada na lista de \nclínicas atendidas pelo veterinário");
        dicaAdicionarClinicaNaLista.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarClinica.setTooltip(dicaAdicionarClinicaNaLista);

        btnAdicionarClinica.setOnAction((t) -> {
            listaClinicasSelecionadas.add(scmbClinica.getValue());
            listarClinicasNaListView();
        });
        
        btnRemoverClinica.setOnAction((t) -> {
            listaClinicasSelecionadas.remove(listViewClinicas.getSelectionModel().getSelectedItem());
            listarClinicasNaListView();
        });
        
        btnInserirMunicipio.setOnAction((t) -> {
            new MenuPrincipal().inserirMunicipio(btnLimpar.getScene().getWindow());
            listarMunicipios();
        });
        
        btnInserirBairro.setOnAction((t) -> {
            new MenuPrincipal().inserirBairro(btnLimpar.getScene().getWindow(), scmbMunicipio.getValue());
            listarBairros();
        });
        
        btnLimparClinicas.setOnAction((t) -> {
            listaClinicasSelecionadas.clear();
            listarClinicasNaListView();
        });
        
        txtObservacao.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtEmail.requestFocus();
            }
        });
        
        txtNumero.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                scmbMunicipio.requestFocus();
            }
        });
        
        btnLimpar.setOnAction((t) -> {
            limpaCampos();
        });

        btnCancelar.setOnAction((t) -> {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        });

        btnSalvar.setDefaultButton(true);
        
        btnSalvar.setOnAction((t) -> {
            ValidacaoException exc = new ValidacaoException("Erro validando!!");

            //Testa se é uma inserção ou edição
            try {
                if (veterinario == null) {
                    veterinario = new Veterinario();
                }

                //Insere os valores nos atributos não obrigatorios
                veterinario.setEmail(txtEmail.getText());

                veterinario.setObservacao(txtObservacao.getText().trim());

                if (rbSexoF.isSelected() == false && rbSexoM.isSelected() == false) {
                    exc.adicionarErro("Sexo", "Selecione um sexo!");
                } else {
                    if (rbSexoM.isSelected()) {
                        veterinario.setSexo(true);
                    } else {
                        veterinario.setSexo(false);
                    }
                }

                veterinario.setListaClinicas(listaClinicasSelecionadas);

                //Testa se os atributos obrigatorios foram preenchidos
                if (txtNome.getText() == null || txtNome.getText().equals("")) {
                    exc.adicionarErro("Nome", "Insira um nome!");
                } else {
                    //Se estiver preenchido, então atualiza o objeto com o nome
                    veterinario.setNome(txtNome.getText());
                }

                if (txtCpf.getText() == null || txtCpf.getText().equals("") || !(Utils.formataDados(txtCpf.getText()).length() == 11 || Utils.formataDados(txtCpf.getText()).length() == 14)) {
                    exc.adicionarErro("Cpf", "Insira um CPF ou CNPJ válido!");
                } else {
                    if (Utils.isCPF(Utils.formataDados(txtCpf.getText())) || Utils.formataDados(txtCpf.getText()).length() == 14) {
                        veterinario.setCpf(Utils.formataDados(txtCpf.getText()));
                    } else {
                        exc.adicionarErro("Cpf", "Insira um CPF válido!");
                    }
                }
                
                if(!txtCrmv.getText().equals("")){
                    veterinario.setCrmv(Utils.formataDados(txtCrmv.getText()));
                }else{
                    exc.adicionarErro("Crmv", "Insira um CRMV válido!");
                }

                if (txtRua.getText() == null || txtRua.getText().equals("")) {
                    exc.adicionarErro("Rua", "Insira um nome de rua!");
                } else {
                    veterinario.setRua(txtRua.getText());
                }

                if (txtNumero.getText() == null || txtNumero.getText().equals("")) {
                    exc.adicionarErro("Numero", "Insira um número e/ou complemento!");
                } else {
                    veterinario.setNumero(txtNumero.getText());
                }

                if (txtCep.getText() == null || txtCep.getText().equals("") || Utils.formataDados(txtCep.getText()).length() != 8) {
                    exc.adicionarErro("Cep", "Insira um CEP válido!");
                } else {
                    veterinario.setCep(Utils.formataDados(txtCep.getText()));
                }

                if (scmbBairro.getValue() != null) {
                    veterinario.setBairro(scmbBairro.getSelectionModel().getSelectedItem());
                } else {
                    exc.adicionarErro("Bairro", "Selecione um bairro!");
                }

                if (scmbMunicipio.getValue() != null) {
                    veterinario.setMunicipio(scmbMunicipio.getSelectionModel().getSelectedItem());
                } else {
                    exc.adicionarErro("Cidade", "Selecione um Município!");
                }

                if (Utils.formataDados(txtTelefone.getText()).length() != 11) {
                    exc.adicionarErro("Telefone", "Insira um telefone válido!");
                } else {
                    veterinario.setTelefone(Utils.formataDados(txtTelefone.getText()));
                }


                // Ao final de todos os testes de campos, é necessário verificar se existem erros.
                // Se existire, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                if (new VeterinarioService().salvarOuAtualizar(veterinario)) {
                    // Deu certo
                    // Posso fechar a janela
                    ((Stage) btnCancelar.getScene().getWindow()).close();
                } else {
                    // Deu erro. O retorno do boolean veio false
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("ERRO");
                    al.setContentText("Ocorreu um erro ao inserir!");
                    al.showAndWait();
                }

            } catch (ValidacaoException e) {
                e.printStackTrace();
                setErrorMessages(e.getErrors());
            }
        });
        
        scmbMunicipio.setOnAction((t) -> {
            listarBairros();
            scmbBairro.setDisable(false);
        });
        
        btnInserirClinica.setOnAction((t) -> {
            new MenuPrincipal().cadastrarClinica(btnLimpar.getScene().getWindow());
            listarClinicasNoComboBox();
        });

        listarMunicipios();
        listarBairros();
        listarClinicasNoComboBox();
    }
    
    public void ajustarTela() {
        if (veterinario == null) {
            lblNome.setText("");
            btnDocumentos.setVisible(false);
            btnExcluir.setVisible(false);
            scmbBairro.setDisable(false);
            btnVerClinicas.setVisible(false);
            btnExcluir.setVisible(false);
            scmbBairro.setDisable(true);
        } else {
            btnExcluir.setVisible(true);
        }
    }

    private void listarBairros() {
        if (scmbMunicipio.getSelectionModel().getSelectedIndex() != -1) {
            List<Bairro> listaBairros = new UtilitarioService().getBairros(scmbMunicipio.getSelectionModel().getSelectedItem());
            ObservableList<Bairro> listaObsEsp = FXCollections.observableArrayList(listaBairros);
            scmbBairro.setItems(listaObsEsp);
        }
    }

    private void listarMunicipios() {
        List<Municipio> listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsEsp = FXCollections.observableArrayList(listaMunicipios);
        scmbMunicipio.setItems(listaObsEsp);
    }
    
    private void listarClinicasNaListView(){
        ObservableList<Clinica> listaObsClinicasSelecionadas = FXCollections.observableArrayList(listaClinicasSelecionadas);
        listViewClinicas.setItems(listaObsClinicasSelecionadas);
    }
    
    private void listarClinicasNoComboBox() {
        listaClinicas = new ClinicaService().getAll(-1, "");
        ObservableList<Clinica> listaObsClinicas = FXCollections.observableArrayList(listaClinicas);
        scmbClinica.setItems(listaObsClinicas);
//        cmbClinica.getItems().addAll(listaClinicas);
        scmbClinica.setCellFactory(param -> new ListCell<Clinica>() {
            @Override
            protected void updateItem(Clinica item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeClinica() + " - " + item.getMunicipioClinica());
            }
        });

        scmbClinica.setButtonCell(new ListCell<Clinica>() {
            @Override
            protected void updateItem(Clinica item, boolean empty) {
                super.updateItem(item, empty);

                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("Selecione uma clínica"); // Texto padrão quando vazio
                } else {
                    setText(item.getNomeClinica() + " - " + item.getMunicipioClinica());
                }
            }
        });
    }

    private void limpaCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtRua.setText("");
        txtNumero.setText("");
        txtTelefone.setText("");
        txtObservacao.setText("");
        scmbBairro.setValue(null);
        scmbMunicipio.setValue(null);
        rbSexoF.setSelected(false);
        rbSexoM.setSelected(false);
        listViewClinicas.getSelectionModel().select(null);
    }

    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroBairro.setText(campos.contains("Bairro") ? errors.get("Bairro") : "");
        lblErroCep.setText(campos.contains("Cep") ? errors.get("Cep") : "");
        lblErroCidade.setText(campos.contains("Cidade") ? errors.get("Cidade") : "");
        lblErroCpf.setText(campos.contains("Cpf") ? errors.get("Cpf") : "");
        lblErroNome.setText(campos.contains("Nome") ? errors.get("Nome") : "");
        lblErroRua.setText(campos.contains("Rua") ? errors.get("Rua") : "");
        lblErroTelefone.setText(campos.contains("Telefone") ? errors.get("Telefone") : "");
        lblErroNumero.setText(campos.contains("Numero") ? errors.get("Numero") : "");
        lblErroSexo.setText(campos.contains("Sexo") ? errors.get("Sexo") : "");
    }
    
}
