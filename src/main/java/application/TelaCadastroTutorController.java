package application;

import application.Principal;
import java.net.URL;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Municipio;
import model.classes.Tutor;
import model.exceptions.ValidacaoException;
import model.services.TutorService;
import model.services.UtilitarioService;
import view.utils.MascarasFX;
import view.utils.Utils;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroTutorController implements Initializable {

    @FXML    private Button btnCancelar;
    @FXML    private Button btnDocumentos;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnInserirBairro;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private Button btnVerPets;
    @FXML    private SearchableComboBox<Bairro> scmbBairro;
    @FXML    private SearchableComboBox<Municipio> scmbMunicipio;
    @FXML    private ComboBox cmbFaixaRenda;
    @FXML    private ComboBox cmbTipoTutor;
    @FXML    private DatePicker dpDataNasc;
    @FXML    private Label lblNome;
    @FXML    private Label lblErroBairro;
    @FXML    private Label lblErroCep;
    @FXML    private Label lblErroCidade;
    @FXML    private Label lblErroCpf;
    @FXML    private Label lblErroNome;
    @FXML    private Label lblErroRua;
    @FXML    private Label lblErroNumero;
    @FXML    private Label lblErroTelefone;
    @FXML    private RadioButton rbSexoF;
    @FXML    private RadioButton rbSexoM;
    @FXML    private RadioButton rbCpf;
    @FXML    private RadioButton rbCnpj;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextField txtCpf;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtNumero;
    @FXML    private TextField txtEmail;
    @FXML    private TextField txtCep;
    @FXML    private TextField txtRua;
    @FXML    private TextField txtTelefone;
    @FXML    private TextField txtTelefoneSec;

    private Tutor tutor;
    private Tutor tutorSalvo;

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
        // Carregando o pet para os campos da tela
        txtNome.setText(String.valueOf(tutor.getNome()));           //Setar o nome
        if (tutor.getDtNasc() != null) {                            //Setar a data de nascimento
            dpDataNasc.setValue(tutor.getDtNasc());
        }
        if (String.valueOf(tutor.getCpf()).length() == 11) {
            rbCpf.setSelected(true);
        } else {
            rbCnpj.setSelected(true);
        }
        if (String.valueOf(tutor.getCpf()).length() == 11) {
            txtCpf.setText(Utils.imprimeCPF(String.valueOf(tutor.getCpf())));             //Setar o CPF ou CNPJ
        } else {
            txtCpf.setText(Utils.imprimeCNPJ(String.valueOf(tutor.getCpf())));
        }
        txtRua.setText(String.valueOf(tutor.getRua()));             //Setar a rua
        txtNumero.setText(String.valueOf(tutor.getNumero()));       //Setar o número do endereço
        scmbMunicipio.getSelectionModel().select(tutor.getMunicipio()); //Setar o municipio

        //listar os bairros
        List<Bairro> listaBairros = new UtilitarioService().getBairros(tutor.getMunicipio());
        ObservableList<Bairro> listaObsEsp = FXCollections.observableArrayList(listaBairros);
        scmbBairro.setItems(listaObsEsp);
        scmbBairro.getSelectionModel().select(tutor.getBairro());    //Setar o bairro
        txtObservacao.setText(tutor.getObservacaoTutor());          //Setar a observacao
        txtTelefone.setText(tutor.getTelefoneTutor());              //Setar o telefone principal
        txtTelefoneSec.setText(tutor.getTelefoneAlternativoTutor());//Setar o telefone alternativo
        if (tutor.isSexo()) {                                         //Seta o sexo
            rbSexoM.setSelected(true);
        } else {
            rbSexoF.setSelected(true);
        }
        cmbTipoTutor.setValue(tutor.getTipoTutor());                //Seta o tipo de tutor
        cmbFaixaRenda.setValue(tutor.getFaixaRenda());              //Seta a faixa de renda
        txtEmail.setText(tutor.getEmailTutor());                    //Seta o email
        txtCep.setText(tutor.getCep());                             //Seta o Cep
        lblNome.setText(tutor.getNome());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraData(dpDataNasc);
        MascarasFX.mascaraCEP(txtCep);
        MascarasFX.mascaraTelefone(txtTelefone);
        MascarasFX.mascaraTelefone(txtTelefoneSec);
        
        //Tooltip
        Tooltip dicaAdicionarMunicipio = new Tooltip("Cadastrar novo município");
        dicaAdicionarMunicipio.setShowDelay(Duration.ZERO);
        btnInserirMunicipio.setTooltip(dicaAdicionarMunicipio);
        
        Tooltip dicaAdicionarBairro = new Tooltip("Cadastrar novo bairro");
        dicaAdicionarBairro.setShowDelay(Duration.ZERO);
        btnInserirBairro.setTooltip(dicaAdicionarBairro);

        rbCpf.setOnAction((t) -> {
            txtCpf.setEditable(true);
            txtCpf.setDisable(false);
            MascarasFX.mascaraCPF(txtCpf);
        });

        rbCnpj.setOnAction((t) -> {
            txtCpf.setEditable(true);
            txtCpf.setDisable(false);
            MascarasFX.mascaraCNPJ(txtCpf);
        });

        btnLimpar.setOnAction((t) -> {
            limpaCampos();
        });
        
        btnInserirMunicipio.setOnAction((t) -> {
            new MenuPrincipal().inserirMunicipio(btnLimpar.getScene().getWindow());
            listarMunicipios();
        });
        
        btnInserirBairro.setOnAction((t) -> {
            new MenuPrincipal().inserirBairro(btnLimpar.getScene().getWindow(), scmbMunicipio.getValue());
            listarBairros();
        });

        btnExcluir.setOnAction((t) -> {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Confirmação");
            al.setContentText(tutor.getNome() + " será excluído! Tem certeza?");
            if (al.showAndWait().get() == ButtonType.OK) {
                if (new TutorService().excluir(tutor)) {
                    Alert mens = new Alert(Alert.AlertType.INFORMATION);
                    mens.initOwner(btnSalvar.getScene().getWindow());
                    mens.setTitle("Excluído");
                    mens.setContentText("Registro excluído com sucesso!");
                    mens.showAndWait();
                }
            }
        });

        btnCancelar.setOnAction((t) -> {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        });

        btnSalvar.setDefaultButton(true);

        btnSalvar.setOnAction((t) -> {
            ValidacaoException exc = new ValidacaoException("Erro validando!!");

            //Testa se é uma inserção ou edição
            try {
                if (tutor == null) {
                    tutor = new Tutor();
                }

                //Insere os valores nos atributos não obrigatorios
                tutor.setEmailTutor(txtEmail.getText());
                if (cmbTipoTutor.getSelectionModel().getSelectedItem() != null) {
                    tutor.setTipoTutor(cmbTipoTutor.getSelectionModel().getSelectedItem().toString());
                }
                if (dpDataNasc != null) {
                    tutor.setDtNasc(dpDataNasc.getValue());
                } else {
                    tutor.setDtNasc(null);
                }
                if (cmbFaixaRenda.getSelectionModel().getSelectedIndex() != -1) {
                    tutor.setFaixaRenda(cmbFaixaRenda.getSelectionModel().getSelectedIndex());
                }
                if (txtTelefoneSec.getText() != null) {
                    tutor.setTelefoneAlternativoTutor(Utils.formataDados(txtTelefoneSec.getText()));
                }
                tutor.setObservacaoTutor(txtObservacao.getText().trim());

                if (rbSexoM.isSelected()) {
                    tutor.setSexo(true);
                } else {
                    tutor.setSexo(false);
                }

                //Testa se os atributos obrigatorios foram preenchidos
                if (txtNome.getText() == null || txtNome.getText().equals("")) {
                    exc.adicionarErro("Nome", "Insira um nome!");
                } else {
                    //Se estiver preenchido, então atualiza o objeto com o nome
                    tutor.setNome(txtNome.getText());
                }

                if (txtCpf.getText() == null || txtCpf.getText().equals("") || !(Utils.formataDados(txtCpf.getText()).length() == 11 || Utils.formataDados(txtCpf.getText()).length() == 14)) {
                    exc.adicionarErro("Cpf", "Insira um CPF ou CNPJ válido!");
                } else {
                    if (Utils.isCPF(Utils.formataDados(txtCpf.getText())) || Utils.formataDados(txtCpf.getText()).length() == 14) {
                        tutor.setCpf(Utils.formataDados(txtCpf.getText()));
                    } else {
                        exc.adicionarErro("Cpf", "Insira um CPF válido!");
                    }
                }

                if (txtRua.getText() == null || txtRua.getText().equals("")) {
                    exc.adicionarErro("Rua", "Insira um nome de rua!");
                } else {
                    tutor.setRua(txtRua.getText());
                }

                if (txtNumero.getText() == null || txtNumero.getText().equals("")) {
                    exc.adicionarErro("Numero", "Insira um número e/ou complemento!");
                } else {
                    tutor.setNumero(txtNumero.getText());
                }

                if (txtCep.getText() == null || txtCep.getText().equals("") || Utils.formataDados(txtCep.getText()).length() != 8) {
                    exc.adicionarErro("Cep", "Insira um CEP válido!");
                } else {
                    tutor.setCep(Utils.formataDados(txtCep.getText()));
                }

                if (scmbBairro.getValue() != null) {
                    tutor.setBairro(scmbBairro.getSelectionModel().getSelectedItem());
                } else {
                    exc.adicionarErro("Bairro", "Selecione um bairro!");
                }

                if (scmbMunicipio.getValue() != null) {
                    tutor.setMunicipio(scmbMunicipio.getSelectionModel().getSelectedItem());
                } else {
                    exc.adicionarErro("Cidade", "Selecione um Município!");
                }

                if (Utils.formataDados(txtTelefone.getText()).length() != 11) {
                    exc.adicionarErro("Telefone", "Insira um telefone válido!");
                } else {
                    tutor.setTelefoneTutor(Utils.formataDados(txtTelefone.getText()));
                }

                // Ao final de todos os testes de campos, é necessário verificar se existem erros.
                // Se existire, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                if (new TutorService().salvarOuAtualizar(tutor)) {
                    // Deu certo
                    // Posso fechar a janela
                    this.tutorSalvo = tutor;
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

        ObservableList<String> listaObsTipoTutor = FXCollections.observableArrayList("Pessoa física", "Pessoa jurídica", "ONG", "Protetor independente");
        cmbTipoTutor.setItems(listaObsTipoTutor);

        ObservableList<String> listaObsFaixaRenda = FXCollections.observableArrayList("Até R$ 2.640", "De R$ 2.640,01 a R$ 4.400",
                "De R$ 4.400,01 a R$ 8.000");
        cmbFaixaRenda.setItems(listaObsFaixaRenda);

        scmbMunicipio.setOnAction((t) -> {
            listarBairros();
            scmbBairro.setDisable(false);
        });

        listarMunicipios();
        listarBairros();
    }

    public void ajustarTela() {
        if (tutor == null) {
            lblNome.setText("");
            btnDocumentos.setVisible(false);
            btnVerPets.setVisible(false);
            btnExcluir.setVisible(false);
            txtCpf.setDisable(true);
            scmbBairro.setDisable(true);
        } else {
            scmbBairro.setDisable(false);
            btnExcluir.setVisible(true);
            btnDocumentos.setVisible(false);
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

    private void limpaCampos() {
        txtNome.setText("");
        dpDataNasc.setValue(null);
        txtCpf.setText("");
        txtRua.setText("");
        txtNumero.setText("");
        txtTelefone.setText("");
        txtTelefoneSec.setText("");
        txtObservacao.setText("");
        scmbBairro.setValue(null);
        scmbMunicipio.setValue(null);
        cmbTipoTutor.setValue(null);
        cmbFaixaRenda.setValue(null);
        rbSexoF.setSelected(false);
        rbSexoM.setSelected(false);
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
    }

    public Tutor getTutorSalvo(){
        return this.tutorSalvo;
    }
}
