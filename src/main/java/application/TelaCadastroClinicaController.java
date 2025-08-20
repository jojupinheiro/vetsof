package application;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
import model.services.UtilitarioService;
import model.services.VeterinarioService;
import view.utils.MascarasFX;
import view.utils.Utils;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroClinicaController implements Initializable {

    @FXML    private Button btnAdicionarVeterinario;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnDocumentos;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserirBairro;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private SearchableComboBox<Bairro> scmbBairro;
    @FXML    private SearchableComboBox<Municipio> scmbMunicipio;
    @FXML    private SearchableComboBox<Veterinario> scmbVeterinario;
    @FXML    private DatePicker dpDataCadastro;
    @FXML    private Label lblErroBairro;
    @FXML    private Label lblErroVeterinario;
    @FXML    private Label lblErroCep;
    @FXML    private Label lblErroCidade;
    @FXML    private Label lblErroCnpj;
    @FXML    private Label lblErroNome;
    @FXML    private Label lblErroNumero;
    @FXML    private Label lblErroRua;
    @FXML    private Label lblErroTelefone;
    @FXML    private Label lblErroEmail;
    @FXML    private Label lblNome;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextField txtCep;
    @FXML    private TextField txtCnpj;
    @FXML    private TextField txtEmail;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtNumero;
    @FXML    private TextField txtRua;
    @FXML    private TextField txtTelefone;
    @FXML    private TextField txtTelefoneSec;

    private Clinica clinica;

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
        txtNome.setText(clinica.getNomeClinica());
        if (clinica.getDataCadastro() != null) {
            dpDataCadastro.setValue(clinica.getDataCadastro());
        }
        txtCnpj.setText(Utils.imprimeCNPJ(clinica.getCnpj()));
        txtRua.setText(clinica.getRuaClinica());
        txtNumero.setText(clinica.getNumeroClinica());
        txtCep.setText(clinica.getCepClinica());
        scmbVeterinario.getSelectionModel().select(clinica.getVeterinarioClinica());
        txtTelefone.setText(clinica.getTelefoneClinica());
        txtTelefoneSec.setText(clinica.getTelefoneAlternativoClinica());
        txtEmail.setText(clinica.getEmailClinica());
        txtObservacao.setText(clinica.getObservacaoClinica());
        listarMunicipios();
        scmbMunicipio.getSelectionModel().select(clinica.getMunicipioClinica());
        
        List<Bairro> listaBairros = new UtilitarioService().getBairros(clinica.getMunicipioClinica());
        ObservableList<Bairro> listaObsEsp = FXCollections.observableArrayList(listaBairros);
        scmbBairro.setItems(listaObsEsp);
        
        scmbBairro.getSelectionModel().select(clinica.getBairroClinica());
        
        lblNome.setText(clinica.getNomeClinica());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraData(dpDataCadastro);
        MascarasFX.mascaraCEP(txtCep);
        MascarasFX.mascaraTelefone(txtTelefone);
        MascarasFX.mascaraTelefone(txtTelefoneSec);
        MascarasFX.mascaraCNPJ(txtCnpj);
        MascarasFX.mascaraEmail(txtEmail);
        
        //tooltips
        Tooltip dicaAdicionarMunicipio = new Tooltip("Cadastrar novo município");
        dicaAdicionarMunicipio.setShowDelay(Duration.ZERO);
        btnInserirMunicipio.setTooltip(dicaAdicionarMunicipio);
        
        Tooltip dicaAdicionarBairro = new Tooltip("Cadastrar novo bairro");
        dicaAdicionarBairro.setShowDelay(Duration.ZERO);
        btnInserirBairro.setTooltip(dicaAdicionarBairro);
        
        Tooltip dicaAdicionarVeterinario = new Tooltip("Cadastrar novo veterinário");
        dicaAdicionarVeterinario.setShowDelay(Duration.ZERO);
        btnAdicionarVeterinario.setTooltip(dicaAdicionarVeterinario);
        
        btnInserirMunicipio.setOnAction((t) -> {
            new MenuPrincipal().inserirMunicipio(btnLimpar.getScene().getWindow());
            listarMunicipios();
        });
        
        btnInserirBairro.setOnAction((t) -> {
            new MenuPrincipal().inserirBairro(btnLimpar.getScene().getWindow(), scmbMunicipio.getValue());
            listarBairros();
        });
        
        btnAdicionarVeterinario.setOnAction((t) -> {
            new MenuPrincipal().cadastrarVeterinario(btnLimpar.getScene().getWindow());
            listarVeterinarios();
        });
        
        txtObservacao.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtEmail.requestFocus();
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

            //Testa se é uma inserção ou edição
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                if (clinica == null) {
                    clinica = new Clinica();
                }

                //Insere os valores nos atributos não obrigatorios
                clinica.setTelefoneAlternativoClinica(Utils.formataDados(txtTelefoneSec.getText()));
                clinica.setObservacaoClinica(txtObservacao.getText().trim());

                //Testa se os atributos obrigatorios foram preenchidos
                if (scmbVeterinario.getValue() != null){
                    clinica.setVeterinarioClinica(scmbVeterinario.getSelectionModel().getSelectedItem());
                } else{
                    exc.adicionarErro("Veterinario", "Selecione um veterinário!");
                    System.out.println("Erro no veterinário");
                }
                
                if (txtNome.getText() == null || txtNome.getText().equals("")) {
                    exc.adicionarErro("Nome", "Insira um nome!");
                    System.out.println("Erro no nome");
                } else {
                    //Se estiver preenchido, então atualiza o objeto com o nome
                    clinica.setNomeClinica(txtNome.getText());
                }
                
                if (dpDataCadastro != null) {
                    clinica.setDataCadastro(dpDataCadastro.getValue());
                } else {
                    clinica.setDataCadastro(LocalDate.now());
                }

                if(txtCnpj.getText().equals("") || txtCnpj.getText() == null){
                    exc.adicionarErro("Cnpj", "Insira um CNPJ!");
                    System.out.println("Erro no CNPJ");
                } else if(Utils.formataDados(txtCnpj.getText()).length() != 14){
                    exc.adicionarErro("Cnpj", "Insira um CNPJ válido!");
                }else {
                    clinica.setCnpj(Utils.formataDados(txtCnpj.getText()));
                }
                
                if (txtRua.getText() == null || txtRua.getText().equals("")) {
                    exc.adicionarErro("Rua", "Insira um logradouro!");
                } else {
                    clinica.setRuaClinica(txtRua.getText());
                }

                if (txtNumero.getText() == null || txtNumero.getText().equals("")) {
                    exc.adicionarErro("Numero", "Insira um número e/ou complemento!");
                } else {
                    clinica.setNumeroClinica(txtNumero.getText());
                }

                if (txtCep.getText() == null || txtCep.getText().equals("") || Utils.formataDados(txtCep.getText()).length() != 8) {
                    exc.adicionarErro("Cep", "Insira um CEP válido!");
                } else {
                    clinica.setCepClinica(Utils.formataDados(txtCep.getText()));
                }
                
                if (scmbBairro.getValue() != null) {
                    clinica.setBairroClinica(scmbBairro.getValue());
                } else {
                    exc.adicionarErro("Bairro", "Selecione um bairro!");
                }

                if (scmbMunicipio.getValue() != null) {
                    clinica.setMunicipioClinica(scmbMunicipio.getValue());
                } else {
                    exc.adicionarErro("Cidade", "Selecione um Município!");
                }
                
                if (Utils.formataDados(txtTelefone.getText()).length() != 11 && Utils.formataDados(txtTelefone.getText()).length() != 10) {
                    exc.adicionarErro("Telefone", "Insira um telefone válido!");
                } else {
                    clinica.setTelefoneClinica(Utils.formataDados(txtTelefone.getText()));
                }
                
                if (txtEmail.getText() == null || txtEmail.getText().equals("") || !txtEmail.getText().contains("@") || !txtEmail.getText().contains(".")) {
                    exc.adicionarErro("Email", "Insira um Email válido!");
                } else {
                    clinica.setEmailClinica(txtEmail.getText());
                }


                // Ao final de todos os testes de campos, é necessário verificar se existem erros.
                // Se existire, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                if (new ClinicaService().salvarOuAtualizar(clinica)) {
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
                System.out.println("Erro na validação");
                e.printStackTrace();
                setErrorMessages(e.getErrors());
            }
        });
        
        listarBairros();
        listarMunicipios();
        listarVeterinarios();
        
        scmbMunicipio.setOnAction((t) -> {
            listarBairros();
            scmbBairro.setDisable(false);
        });
        
    }

    public void ajustarTela() {
        if (clinica == null) {
            dpDataCadastro.setValue(LocalDate.now());
            btnExcluir.setVisible(false);
            btnDocumentos.setVisible(false);
            scmbBairro.setDisable(true);
        }
    }
    
    private void listarBairros() {
        if (scmbMunicipio.getSelectionModel().getSelectedIndex() != -1) {
            List<Bairro> listaBairros = new UtilitarioService().getBairros(scmbMunicipio.getValue());
            ObservableList<Bairro> listaObsEsp = FXCollections.observableArrayList(listaBairros);
            scmbBairro.setItems(listaObsEsp);
        }
    }

    private void listarMunicipios() {
        List<Municipio> listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsEsp = FXCollections.observableArrayList(listaMunicipios);
        scmbMunicipio.setItems(listaObsEsp);
    }
    
    private void listarVeterinarios() {
        List<Veterinario> listaVeterinarios = new VeterinarioService().getAll(-1, "");
        ObservableList<Veterinario> listaObsEsp = FXCollections.observableArrayList(listaVeterinarios);
        scmbVeterinario.setItems(listaObsEsp);
    }

    private void limpaCampos() {
        txtNome.setText("");
        dpDataCadastro.setValue(LocalDate.now());
        txtCnpj.setText("");
        txtRua.setText("");
        txtNumero.setText("");
        txtCep.setText("");
        scmbVeterinario.getSelectionModel().select(-1);
        txtTelefone.setText("");
        txtTelefoneSec.setText("");
        txtEmail.setText("");
        txtObservacao.setText("");
        scmbBairro.getSelectionModel().select(-1);
        scmbMunicipio.getSelectionModel().select(-1);
    }

    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroBairro.setText(campos.contains("Bairro") ? errors.get("Bairro") : "");
        lblErroCep.setText(campos.contains("Cep") ? errors.get("Cep") : "");
        lblErroCidade.setText(campos.contains("Cidade") ? errors.get("Cidade") : "");
        lblErroCnpj.setText(campos.contains("Cnpj") ? errors.get("Cnpj") : "");
        lblErroNome.setText(campos.contains("Nome") ? errors.get("Nome") : "");
        lblErroRua.setText(campos.contains("Rua") ? errors.get("Rua") : "");
        lblErroTelefone.setText(campos.contains("Telefone") ? errors.get("Telefone") : "");
        lblErroNumero.setText(campos.contains("Numero") ? errors.get("Numero") : "");
        lblErroEmail.setText(campos.contains("Email") ? errors.get("Email") : "");
        lblErroVeterinario.setText(campos.contains("Veterinario") ? errors.get("Veterinario") : "");
    }
    
    
}
