package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.classes.utilitario.Bairro;
import model.classes.Clinica;
import model.classes.prescricoes.FormatacaoPrescricao;
import model.classes.utilitario.Municipio;
import model.classes.Veterinario;
import model.classes.utilitario.ValorPadrao;
import model.enums.FormaPagamento;
import model.exceptions.ValidacaoException;
import model.services.ClinicaService;
import model.services.PrescricaoService;
import model.services.UtilitarioService;
import model.services.VeterinarioService;
import org.controlsfx.control.SearchableComboBox;
import view.utils.MascarasFX;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaPreferenciasController implements Initializable {

    @FXML    private Button btnAdicionarVeterinario;
    @FXML    private Button btnAdicionarVeterinario2;
    @FXML    private Button btnAdicionarVeterinarioNaLista;
    @FXML    private Button btnCancelarClinica;
    @FXML    private Button btnCarregar;
    @FXML    private Button btnCarregarLogoPersonalizado;
    @FXML    private Button btnCarregarLogotipo;
    @FXML    private Button btnInserirBairro;
    @FXML    private Button btnInserirMunicipio;
    @FXML    private Button btnLimparClinica;
    @FXML    private Button btnLimparListVeterinario;
    @FXML    private Button btnPadrao;
    @FXML    private Button btnRemoverVeterinario;
    @FXML    private Button btnSalvar;
    @FXML    private Button btnSalvarClinica;
    @FXML    private Button btnSalvarValoresPadrao;
    @FXML    private CheckBox ckbDescricaoClinicaItalico;
    @FXML    private CheckBox ckbDescricaoClinicaNegrito;
    @FXML    private CheckBox ckbFraseInicioItalico;
    @FXML    private CheckBox ckbFraseInicioNegrito;
    @FXML    private CheckBox ckbFormaUsoItalico;
    @FXML    private CheckBox ckbFormaUsoNegrito;
    @FXML    private CheckBox ckbNomeClinicaItalico;
    @FXML    private CheckBox ckbNomeClinicaNegrito;
    @FXML    private CheckBox ckbObservacoesItalico;
    @FXML    private CheckBox ckbObservacoesNegrito;
    @FXML    private CheckBox ckbPacienteEspecie;
    @FXML    private CheckBox ckbPacienteIdade;
    @FXML    private CheckBox ckbPacienteItalico;
    @FXML    private CheckBox ckbPacienteNegrito;
    @FXML    private CheckBox ckbPacienteMicrochip;
    @FXML    private CheckBox ckbPacientePeso;
    @FXML    private CheckBox ckbPacienteRaca;
    @FXML    private CheckBox ckbPosologiaItalico;
    @FXML    private CheckBox ckbProdutoItalico;
    @FXML    private CheckBox ckbPosologiaNegrito;
    @FXML    private CheckBox ckbProdutoNegrito;
    @FXML    private CheckBox ckbRodapeEmail;
    @FXML    private CheckBox ckbRodapeEndereco;
    @FXML    private CheckBox ckbRodapeItalico;
    @FXML    private CheckBox ckbRodapeNegrito;
    @FXML    private CheckBox ckbRodapeTelefone;
    @FXML    private CheckBox ckbTutorCpf;
    @FXML    private CheckBox ckbTutorItalico;
    @FXML    private CheckBox ckbTutorNegrito;
    @FXML    private CheckBox ckbTutorTelefone;
    @FXML    private CheckBox ckbVeterinarioItalico;
    @FXML    private CheckBox ckbVeterinarioNegrito;
    @FXML    private ColorPicker cpDescricaoClinica;
    @FXML    private ColorPicker cpFraseInicio;
    @FXML    private ColorPicker cpFormaUso;
    @FXML    private ColorPicker cpNomeClinica;
    @FXML    private ColorPicker cpObservacoes;
    @FXML    private ColorPicker cpPaciente;
    @FXML    private ColorPicker cpPosologia;
    @FXML    private ColorPicker cpProduto;
    @FXML    private ColorPicker cpRodape;
    @FXML    private ColorPicker cpTutor;
    @FXML    private ColorPicker cpVeterinario;
    @FXML    private ComboBox<Bairro> cmbBairro;
    @FXML    private ComboBox<Integer> cmbModelo;
    @FXML    private ComboBox<Municipio> cmbMunicipio;
    @FXML    private ComboBox<String> cmbAlinhamentoVeterinario;
    @FXML    private ComboBox<String> cmbEstilo;
    @FXML    private ComboBox<Veterinario> cmbVeterinarioResponsavel;
    @FXML    private ComboBox<Veterinario> cmbOutrosVeterinarios;
    @FXML    private ImageView imgLogo;
    @FXML    private Label lblCpfTutor;
    @FXML    private Label lblDescricaoClinica;
    @FXML    private Label lblErroBairro;
    @FXML    private Label lblErroCep;
    @FXML    private Label lblErroCnpj;
    @FXML    private Label lblErroCidade;
    @FXML    private Label lblErroEmail;
    @FXML    private Label lblErroNome;
    @FXML    private Label lblErroNumero;
    @FXML    private Label lblErroRua;
    @FXML    private Label lblErroTelefone;
    @FXML    private Label lblErroVeterinario;
    @FXML    private Label lblEspeciePaciente;
    @FXML    private Label lblFraseInicio;
    @FXML    private Label lblFoneTutor;
    @FXML    private Label lblFormaUso;
    @FXML    private Label lblFormaUso2;
    @FXML    private Label lblIdadePaciente;
    @FXML    private Label lblMicrochipPaciente;
    @FXML    private Label lblNomeClinica;
    @FXML    private Label lblNomePaciente;
    @FXML    private Label lblNomeTutor;
    @FXML    private Label lblObservacoes;
    @FXML    private Label lblObservacoes2;
    @FXML    private Label lblPesoPaciente;
    @FXML    private Label lblPosologia;
    @FXML    private Label lblPosologia2;
    @FXML    private Label lblProduto;
    @FXML    private Label lblProduto2;
    @FXML    private Label lblRacaPaciente;
    @FXML    private Label lblRodapeData;
    @FXML    private Label lblRodapeEndereco;
    @FXML    private Label lblRodapeEmail;
    @FXML    private Label lblRodapeTelefone;
    @FXML    private Label lblVeterinario;
    @FXML    private Label lblVeterinario2;
    @FXML    private Label lblVeterinario3;
    @FXML    private ListView<Veterinario> listViewVeterinarios;
    @FXML    private RadioButton rbLogoPersonalizado;
    @FXML    private RadioButton rbLogoVetsof;
    @FXML    private RadioButton rbModoClinica;
    @FXML    private RadioButton rbModoInstituicao;
    @FXML    private RadioButton rbFormatoLogoPaisagem;
    @FXML    private RadioButton rbFormatoLogoQuadrado;
    @FXML    private RadioButton rbFormatoLogoRetrato;
    @FXML    private RadioButton rbTamanhoLogoGrande;
    @FXML    private RadioButton rbTamanhoLogoMedio;
    @FXML    private RadioButton rbTamanhoLogoPequeno;
    @FXML    private SearchableComboBox<FormaPagamento> scmbFormaPagamento;
    @FXML    private Spinner<Integer> spnDescricaoClinica;
    @FXML    private Spinner<Integer> spnEspacamentoVeterinario;
    @FXML    private Spinner<Integer> spnFraseInicio;
    @FXML    private Spinner<Integer> spnFormaUso;
    @FXML    private Spinner<Integer> spnNomeClinica;
    @FXML    private Spinner<Integer> spnObservacoes;
    @FXML    private Spinner<Integer> spnPaciente;
    @FXML    private Spinner<Integer> spnPosologia;
    @FXML    private Spinner<Integer> spnProduto;
    @FXML    private Spinner<Integer> spnRodape;
    @FXML    private Spinner<Integer> spnRecuoEsqLogo;
    @FXML    private Spinner<Integer> spnRecuoFormaUso;
    @FXML    private Spinner<Integer> spnRecuoObservacoes;
    @FXML    private Spinner<Integer> spnRecuoPaciente;
    @FXML    private Spinner<Integer> spnRecuoPosologia;
    @FXML    private Spinner<Integer> spnRecuoProduto;
    @FXML    private Spinner<Integer> spnRecuoTopoLogo;
    @FXML    private Spinner<Integer> spnRecuoTutor;
    @FXML    private Spinner<Integer> spnRecuoVeterinario;
    @FXML    private Spinner<Integer> spnTutor;
    @FXML    private Spinner<Integer> spnVeterinario;
    @FXML    private Tab tabDadosClinica;
    @FXML    private Tab tabModeloPrescricao;
    @FXML    private TabPane tabPane;
    @FXML    private TextArea txtObservacoes;
    @FXML    private TextField txtCep;
    @FXML    private TextField txtCnpj;
    @FXML    private TextField txtDescricaoClinica;
    @FXML    private TextField txtEmail;
    @FXML    private TextField txtFraseInicio;
    @FXML    private TextField txtNomeClinica;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtNumero;
    @FXML    private TextField txtRazaoSocial;
    @FXML    private TextField txtRua;
    @FXML    private TextField txtTelefone;
    @FXML    private TextField txtTelefoneSec;
    @FXML    private TextField txtValorDiariaInternacao;
    @FXML    private ToggleGroup formatoLogo;
    @FXML    private ToggleGroup tamanhoLogo;
    @FXML    private VBox boxVeterinario;
    
    public static String estilo;
    public static Map<Integer, Integer> preferencias;
    public static List<ValorPadrao> valoresPadrao;
    
    private Clinica clinica = new ClinicaService().getClinicaPrincipal();
    private List<FormatacaoPrescricao> listaFormatacoes = new PrescricaoService().getFormatacaoAtivaDaPrescricao();
    List<Veterinario> listaVeterinariosSelecionados = new ClinicaService().getVeterinariosDaClinica(1);
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        // EDITOR DE PRESCRIÇÕES #####################################################################################################################################################################
        //############################################################################################################################################################################################
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Escolha uma imagem para logotipo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens .png", "*.png"));
        
        btnCarregarLogotipo.setOnAction((t) -> {
            File arquivoSelecionado = fileChooser.showOpenDialog(btnSalvar.getScene().getWindow());
            if (arquivoSelecionado != null) {
                String extensao;
                
                int indicePonto = arquivoSelecionado.getName().lastIndexOf(".");
                if (indicePonto > 0 && indicePonto < arquivoSelecionado.getName().length() - 1) {
                    extensao = "." + arquivoSelecionado.getName().substring(indicePonto + 1);
                } else {
                    extensao = "";
                }

                try {
                    // Obtendo o diretório do programa (onde o .jar está)
                    File pastaPrograma = new File(System.getProperty("user.dir"));
                    File destino = new File(pastaPrograma, "src/main/java/pdf/logo" + cmbModelo.getValue() + extensao);

                    // Copiar o arquivo para a pasta do programa
                    copiarArquivo(arquivoSelecionado, destino);
                    
                    Image imagem = new Image(arquivoSelecionado.toURI().toString());
                    imgLogo.setImage(imagem);
                    imgLogo.setFitHeight(60);
                    imgLogo.setFitWidth(60);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Nenhum arquivo foi selecionado.");
            }
            formatarLabels();
        });
        
        formatoLogo.selectedToggleProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        tamanhoLogo.selectedToggleProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        
        btnCarregar.setOnAction((t) -> {
            listaFormatacoes = new PrescricaoService().getFormatacaoDoModeloDaPrescricao(cmbModelo.getSelectionModel().getSelectedItem());
            aplicarFormatacao();
        });
        
        btnSalvar.setOnAction((t) -> {
            salvarFormatacao();
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Sucesso");
            al.setContentText("Formatação salva na posição " + cmbModelo.getSelectionModel().getSelectedItem() + "!\n"
                    + "Deseja aplicar essa formatação como a formatação atual?");
            if (al.showAndWait().get() == ButtonType.OK) {
                definirModeloComoAtivo();
            }
        });
        
        btnPadrao.setOnAction((t) -> {
            definirModeloComoAtivo();
            Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Modelo de prescrição ativo alterado para Modelo nº " + cmbModelo.getSelectionModel().getSelectedItem());
                al.showAndWait();
        });
        
        // Faz os labels de exemplo da formatação da precrição imitarem o que for digitado no text field.
        lblNomeClinica.textProperty().bind(txtNomeClinica.textProperty());
        lblDescricaoClinica.textProperty().bind(txtDescricaoClinica.textProperty());
        lblFraseInicio.textProperty().bind(txtFraseInicio.textProperty());
        
        //CheckBoxes
        ckbNomeClinicaNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbDescricaoClinicaNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbDescricaoClinicaItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbNomeClinicaItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbFraseInicioItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbFraseInicioNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbFormaUsoItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbFormaUsoNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbProdutoItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbProdutoNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPosologiaNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPosologiaItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbObservacoesItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbObservacoesNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPacienteItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPacienteNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPacienteEspecie.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPacienteIdade.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPacienteRaca.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPacientePeso.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbPacienteMicrochip.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbTutorItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbTutorNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbTutorCpf.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbTutorTelefone.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbRodapeItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbRodapeNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbRodapeEmail.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbRodapeTelefone.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbRodapeEndereco.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbVeterinarioItalico.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        ckbVeterinarioNegrito.selectedProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        
        
        // ColorPickers
        cpNomeClinica.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpDescricaoClinica.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpFraseInicio.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpFormaUso.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpProduto.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpPosologia.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpObservacoes.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpPaciente.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpTutor.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpRodape.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        cpVeterinario.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels());
        
        //  Spinners - início:
        SpinnerValueFactory<Integer> valueFactoryNomeClinica = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 16, 1);
        SpinnerValueFactory<Integer> valueFactoryDescricaoClinica = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 12, 1);
        SpinnerValueFactory<Integer> valueFactoryFraseInicio = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 9, 1);
        SpinnerValueFactory<Integer> valueFactoryTamanhoFormaUso = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 12, 1);
        SpinnerValueFactory<Integer> valueFactoryRecuoFormaUso = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 5);
        SpinnerValueFactory<Integer> valueFactoryRecuoProduto = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 10, 5);
        SpinnerValueFactory<Integer> valueFactoryRecuoPosologia = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 5, 5);
        SpinnerValueFactory<Integer> valueFactoryRecuoObservacoes = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 5, 5);
        SpinnerValueFactory<Integer> valueFactoryTamanhoProduto = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 12, 1);
        SpinnerValueFactory<Integer> valueFactoryTamanhoObservacoes = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 12, 1);
        SpinnerValueFactory<Integer> valueFactoryTamanhoPosologia = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 12, 1);
        SpinnerValueFactory<Integer> valueFactoryTamanhoTutor = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 10, 1);
        SpinnerValueFactory<Integer> valueFactoryRecuoTutor = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 5);
        SpinnerValueFactory<Integer> valueFactoryTamanhoPaciente = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 10, 1);
        SpinnerValueFactory<Integer> valueFactoryRecuoPaciente = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 5);
        SpinnerValueFactory<Integer> valueFactoryTamanhoRodape = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 10, 1);
        SpinnerValueFactory<Integer> valueFactoryRecuoVeterinario = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 5);
        SpinnerValueFactory<Integer> valueFactoryTamanhoVeterinario = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 10, 1);
        SpinnerValueFactory<Integer> valueFactoryEspacamentoVeterinario = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0, 5);
        SpinnerValueFactory<Integer> valueFactoryRecuoEsqLogo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 10, 5);
        SpinnerValueFactory<Integer> valueFactoryRecuoTopoLogo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 10, 5);
        
        spnNomeClinica.setValueFactory(valueFactoryNomeClinica);
        spnDescricaoClinica.setValueFactory(valueFactoryDescricaoClinica);
        spnFraseInicio.setValueFactory(valueFactoryFraseInicio);
        spnFormaUso.setValueFactory(valueFactoryTamanhoFormaUso);
        spnRecuoFormaUso.setValueFactory(valueFactoryRecuoFormaUso);
        spnRecuoProduto.setValueFactory(valueFactoryRecuoProduto);
        spnProduto.setValueFactory(valueFactoryTamanhoProduto);
        spnPosologia.setValueFactory(valueFactoryTamanhoPosologia);
        spnRecuoPosologia.setValueFactory(valueFactoryRecuoPosologia);
        spnObservacoes.setValueFactory(valueFactoryTamanhoObservacoes);
        spnRecuoObservacoes.setValueFactory(valueFactoryRecuoObservacoes);
        spnRecuoTutor.setValueFactory(valueFactoryRecuoTutor);
        spnTutor.setValueFactory(valueFactoryTamanhoTutor);
        spnRecuoPaciente.setValueFactory(valueFactoryRecuoPaciente);
        spnPaciente.setValueFactory(valueFactoryTamanhoPaciente);
        spnRodape.setValueFactory(valueFactoryTamanhoRodape);
        spnVeterinario.setValueFactory(valueFactoryTamanhoVeterinario);
        spnRecuoVeterinario.setValueFactory(valueFactoryRecuoVeterinario);
        spnEspacamentoVeterinario.setValueFactory(valueFactoryEspacamentoVeterinario);
        spnRecuoEsqLogo.setValueFactory(valueFactoryRecuoEsqLogo);
        spnRecuoTopoLogo.setValueFactory(valueFactoryRecuoTopoLogo);
        
        spnNomeClinica.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnDescricaoClinica.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnFraseInicio.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnFormaUso.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoFormaUso.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoProduto.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnProduto.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnPosologia.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoPosologia.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnObservacoes.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoObservacoes.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoTutor.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnTutor.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoPaciente.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnPaciente.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRodape.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnVeterinario.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoVeterinario.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnEspacamentoVeterinario.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoEsqLogo.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        spnRecuoTopoLogo.valueProperty().addListener((o, oldValue, newValue) -> formatarLabels() );
        //  Spinners - fim:
        
        ObservableList<Integer> listaObs = FXCollections.observableArrayList(1, 2, 3, 4, 5);
        cmbModelo.setItems(listaObs);
        if (!listaFormatacoes.isEmpty()){
            cmbModelo.getSelectionModel().select(listaFormatacoes.get(0).getModelo() - 1);
        }else{
            cmbModelo.getSelectionModel().select(0);
        }
        
        ObservableList<String> listaObsAlinhamento = FXCollections.observableArrayList("À esquerda", "Centralizado", "À direita");
        cmbAlinhamentoVeterinario.setItems(listaObsAlinhamento);
        if (listaFormatacoes.isEmpty()){
            cmbAlinhamentoVeterinario.getSelectionModel().select(2);
        }
        
        cmbAlinhamentoVeterinario.getSelectionModel().selectedIndexProperty().addListener((t, oldValue, newValue) -> {
            formatarLabels();
            if ((int) newValue == 0 || (int) newValue == 2){
                spnRecuoVeterinario.setVisible(true);
            }else{
                spnRecuoVeterinario.setVisible(false);
            }
        });
        
        aplicarFormatacao();
        formatarLabels();
        
        // FIM DO EDITOR DE PRESCRIÇÕES #####################################################################################################################################################################
        //###################################################################################################################################################################################################
        
        
        // CLINICA PRINCIPAL=================================================================================================================================================================================
        //===================================================================================================================================================================================================
        MascarasFX.mascaraCEP(txtCep);
        MascarasFX.mascaraTelefone(txtTelefone);
        MascarasFX.mascaraTelefone(txtTelefoneSec);
        MascarasFX.mascaraCNPJ(txtCnpj);
        MascarasFX.mascaraEmail(txtEmail);
        
        //Tooltips
        
        Tooltip dicaAdicionarVeterinarioNaLista = new Tooltip("Adicionar veterinário à lista da clínica");
        dicaAdicionarVeterinarioNaLista.setShowDelay(Duration.ZERO);
        btnAdicionarVeterinarioNaLista.setTooltip(dicaAdicionarVeterinarioNaLista);
        
        Tooltip dicaAdicionarVeterinario = new Tooltip("Cadastrar novo veterinário");
        dicaAdicionarVeterinario.setShowDelay(Duration.ZERO);
        btnAdicionarVeterinario.setTooltip(dicaAdicionarVeterinario);
        btnAdicionarVeterinario2.setTooltip(dicaAdicionarVeterinario);        
        
        Tooltip dicaAdicionarMunicipio = new Tooltip("Cadastrar novo município");
        dicaAdicionarMunicipio.setShowDelay(Duration.ZERO);
        btnInserirMunicipio.setTooltip(dicaAdicionarMunicipio);
        
        Tooltip dicaAdicionarBairro = new Tooltip("Cadastrar novo bairro");
        dicaAdicionarBairro.setShowDelay(Duration.ZERO);
        btnInserirBairro.setTooltip(dicaAdicionarBairro);
        
        btnCancelarClinica.setOnAction((t) -> ((Stage) btnCancelarClinica.getScene().getWindow()).close());
        
        btnLimparClinica.setOnAction((t) -> limparCamposClinica());
        
        btnAdicionarVeterinario.setOnAction((t) -> {
            new MenuPrincipal().cadastrarVeterinario(btnSalvar.getScene().getWindow());
            listarVeterinarios();
        });
        
        btnAdicionarVeterinario2.setOnAction((t) -> {
            new MenuPrincipal().cadastrarVeterinario(btnSalvar.getScene().getWindow());
            listarVeterinarios();
        });
        
        btnAdicionarVeterinarioNaLista.setOnAction((t) -> {
            listaVeterinariosSelecionados.add(cmbOutrosVeterinarios.getValue());
            listarVeterinariosNaListView();
            cmbOutrosVeterinarios.getSelectionModel().select(-1);
        });
        
        btnRemoverVeterinario.setOnAction((t) -> {
            listaVeterinariosSelecionados.remove(listViewVeterinarios.getSelectionModel().getSelectedItem());
            listarVeterinariosNaListView();
        });
        
        btnLimparListVeterinario.setOnAction((t) -> {
            listaVeterinariosSelecionados.clear();
            listarVeterinariosNaListView();
        });
        
        btnSalvarClinica.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                if (clinica == null) {
                    clinica = new Clinica();
                }

                //Insere os valores nos atributos não obrigatorios
                clinica.setTelefoneAlternativoClinica(Utils.formataDados(txtTelefoneSec.getText()));
                clinica.setObservacaoClinica(txtObservacoes.getText());
                clinica.setRazaoSocial(txtRazaoSocial.getText());

                //Testa se os atributos obrigatorios foram preenchidos
                if (cmbVeterinarioResponsavel.getValue() != null){
                    clinica.setVeterinarioClinica(cmbVeterinarioResponsavel.getSelectionModel().getSelectedItem());
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
                
                if (cmbBairro.getValue() != null) {
                    clinica.setBairroClinica(cmbBairro.getValue());
                } else {
                    exc.adicionarErro("Bairro", "Selecione um bairro!");
                }

                if (cmbMunicipio.getValue() != null) {
                    clinica.setMunicipioClinica(cmbMunicipio.getValue());
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
                    new ClinicaService().excluirVeterinariosDaClinica(clinica);
                    new ClinicaService().inserirVeterinariosNaClinica(listaVeterinariosSelecionados, clinica.getIdClinica());
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Sucesso");
                    al.setContentText("Dados da clínica atualizados com sucesso!");
                    al.showAndWait();
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

        if (cmbMunicipio.getSelectionModel().getSelectedIndex() != -1) {
            listarMunicipios();
        }
        listarVeterinarios();

        cmbMunicipio.setOnAction((t) -> {
            listarBairros();
            cmbBairro.setDisable(false);
        });
        
        listarMunicipios();
        
        
        // FIM CLINICA PRINCIPAL=============================================================================================================================================================================
        //===================================================================================================================================================================================================
        
        
        // PREFERENCIAS -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        
        ObservableList<String> listaObsEstilo = FXCollections.observableArrayList("Padrão", "BlueSky");
        cmbEstilo.setItems(listaObsEstilo);

        setIndiceDoEstiloAtual();
        
        cmbEstilo.getSelectionModel().selectedIndexProperty().addListener((t, oldValue, newValue) -> {
            if(newValue != oldValue){
                setEstilo(cmbEstilo.getSelectionModel().getSelectedItem());
                new UtilitarioService().trocaEstiloAtivo(estilo);
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Estilo alterado para " + getNomeEstilo() + "!\n"
                        + "Reinicie o programa para aplicar a mudança.");
                al.showAndWait();
            }
            
        });
        
        rbModoClinica.setOnAction((t) -> {
            atualizarPreferencias();
            ajustarTelaPreferencias();
        });
        
        rbModoInstituicao.setOnAction((t) -> {
            atualizarPreferencias();
            ajustarTelaPreferencias();
        });
        
        ajustarTelaPreferencias();
        
        // VALORES PADRÃO ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        MascarasFX.mascaraNumero(txtValorDiariaInternacao);
        
        btnSalvarValoresPadrao.setOnAction((t) -> {
            
            for (ValorPadrao item : valoresPadrao){       //Percorre a lista dos valores padrão para salvar no BD
                switch (item.getCodigoValorPadrao()) {
                    case 1 -> //código do valor da diária de internação
                        item.setValorPadraoNumeral(Float.parseFloat(txtValorDiariaInternacao.getText()));
                    case 2 -> //código do método de pagamento padrão
                        item.setValorPadraoString(scmbFormaPagamento.getValue().toString());
                    default -> {
                    }
                }
            }
            
            if (new UtilitarioService().atualizarValoresPadrao(valoresPadrao)){
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Valores padrão atualizados com sucesso!");
                al.showAndWait();
            }
        });
        
        ObservableList<FormaPagamento> listaObsFormaPagamento = FXCollections.observableArrayList(FormaPagamento.DINHEIRO, FormaPagamento.PIX,
                FormaPagamento.DEBITO_VISTA, FormaPagamento.CREDITO_VISTA, FormaPagamento.DEBITO_PRAZO, FormaPagamento.CREDITO_PRAZO,
                FormaPagamento.DEBITO_PARCELADO, FormaPagamento.DEBITO_PARCELADO);
        scmbFormaPagamento.setItems(listaObsFormaPagamento);
        
        scmbFormaPagamento.setCellFactory(cb -> new ListCell<FormaPagamento>() {
            @Override
            protected void updateItem(FormaPagamento formaPagamento, boolean empty) {
                super.updateItem(formaPagamento, empty);
                if (empty || formaPagamento == null) {
                    setText(null);
                } else {
                    setText(formaPagamento.getNome());
                }
            }
        });
        
        scmbFormaPagamento.setButtonCell(new ListCell<FormaPagamento>() {
            @Override
            protected void updateItem(FormaPagamento formaPagamento, boolean empty) {
                super.updateItem(formaPagamento, empty);
                if (empty || formaPagamento == null) {
                    setText(null);
                } else {
                    setText(formaPagamento.getNome());
                }
            }
        });
        
        setValoresPadrao();
     }  
    
    private void copiarArquivo(File origem, File destino) throws IOException {
        try (InputStream in = new FileInputStream(origem);
             OutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
    
    private void formatarLabels(){
        //Atributos do logotipo
        int tamanhoLogotipo;
        if (rbTamanhoLogoPequeno.isSelected()){
            tamanhoLogotipo = 1;
        }else if (rbTamanhoLogoMedio.isSelected()){
            tamanhoLogotipo = 2;
        }else {
            tamanhoLogotipo = 3;
        }
        int formatoLogotipo;
        if (rbFormatoLogoQuadrado.isSelected()){
            formatoLogotipo = 1;
        }else if (rbFormatoLogoPaisagem.isSelected()){
            formatoLogotipo = 2;
        }else {
            formatoLogotipo = 3;
        }
        int recuoEsquerdaLogotipo = spnRecuoEsqLogo.getValue();
        int recuoTopoLogotipo = spnRecuoTopoLogo.getValue();
        imgLogo.setPreserveRatio(true);
        imgLogo.setSmooth(true);
        
        switch (tamanhoLogotipo) {
            case 1:
                switch (formatoLogotipo) {
                    case 1:
                        imgLogo.setFitWidth(45);
                        imgLogo.setFitHeight(45);
                        break;
                    case 2:
                        imgLogo.setFitWidth(60);
                        imgLogo.setFitHeight(45);
                        break;
                    case 3:
                        imgLogo.setFitWidth(45);
                        imgLogo.setFitHeight(60);
                        break;
                }
                break;
            case 2:
                switch (formatoLogotipo) {
                    case 1:
                        imgLogo.setFitWidth(60);
                        imgLogo.setFitHeight(60);
                        break;
                    case 2:
                        imgLogo.setFitWidth(90);
                        imgLogo.setFitHeight(60);
                        break;
                    case 3:
                        imgLogo.setFitWidth(60);
                        imgLogo.setFitHeight(90);
                        break;
                }
                break;
            case 3:
                switch (formatoLogotipo) {
                    case 1:
                        imgLogo.setFitWidth(75);
                        imgLogo.setFitHeight(90);
                        break;
                    case 2:
                        imgLogo.setFitWidth(110);
                        imgLogo.setFitHeight(75);
                        break;
                    case 3:
                        imgLogo.setFitWidth(75);
                        imgLogo.setFitHeight(110);
                        break;
                }
                break;
        }
        
        imgLogo.setTranslateX(spnRecuoEsqLogo.getValue()/1.3);
        imgLogo.setTranslateY(spnRecuoTopoLogo.getValue()/1.3);
        
        //Atributos nome da clínica
        int tamanhoNome = spnNomeClinica.getValue();
        String italicoNome = ckbNomeClinicaItalico.isSelected() ? "italic" : "normal";
        String negritoNome = ckbNomeClinicaNegrito.isSelected() ? "bolder" : "normal";
        String corNome = "#" + cpNomeClinica.getValue().toString().substring(2, 8);
        
        //Atributos descrição da clínica
        int tamanhoDescricao = spnDescricaoClinica.getValue();
        String italicoDescricao = ckbDescricaoClinicaItalico.isSelected() ? "italic" : "normal";
        String negritoDescricao = ckbDescricaoClinicaNegrito.isSelected() ? "bolder" : "normal";
        String corDescricao = "#" + cpDescricaoClinica.getValue().toString().substring(2, 8);
        
        //Atributos frase de inicio do corpo
        int tamanhoFraseInicio = spnFraseInicio.getValue();
        String italicoFraseInicio = ckbFraseInicioItalico.isSelected() ? "italic" : "normal";
        String negritoFraseInicio = ckbFraseInicioNegrito.isSelected() ? "bolder" : "normal";
        String corFraseInicio = "#" + cpFraseInicio.getValue().toString().substring(2, 8);
        
        //Atributos Forma de uso dos medicamentos
        int tamanhoFormaUso = spnFormaUso.getValue();
        String italicoFormaUso = ckbFormaUsoItalico.isSelected() ? "italic" : "normal";
        String negritoFormaUso = ckbFormaUsoNegrito.isSelected() ? "bolder" : "normal";
        String corFormaUso = "#" + cpFormaUso.getValue().toString().substring(2, 8);
        int recuoFormaUso = spnRecuoFormaUso.getValue();
        
        //Atributos Produtos
        int tamanhoProduto = spnProduto.getValue();
        String italicoProduto = ckbProdutoItalico.isSelected() ? "italic" : "normal";
        String negritoProduto = ckbProdutoNegrito.isSelected() ? "bolder" : "normal";
        String corProduto = "#" + cpProduto.getValue().toString().substring(2, 8);
        int recuoProduto = spnRecuoProduto.getValue();
        
        //Atributos Posologia
        int tamanhoPosologia = spnPosologia.getValue();
        String italicoPosologia = ckbPosologiaItalico.isSelected() ? "italic" : "normal";
        String negritoPosologia = ckbPosologiaNegrito.isSelected() ? "bolder" : "normal";
        String corPosologia = "#" + cpPosologia.getValue().toString().substring(2, 8);
        int recuoPosologia = spnRecuoPosologia.getValue();
        
        //Atributos Observacoes
        int tamanhoObservacoes = spnObservacoes.getValue();
        String italicoObservacoes = ckbObservacoesItalico.isSelected() ? "italic" : "normal";
        String negritoObservacoes = ckbObservacoesNegrito.isSelected() ? "bolder" : "normal";
        String corObservacoes = "#" + cpObservacoes.getValue().toString().substring(2, 8);
        int recuoObservacoes = spnRecuoObservacoes.getValue();
        
        //Atributos Tutor
        int tamanhoTutor = spnTutor.getValue();
        String italicoTutor = ckbTutorItalico.isSelected() ? "italic" : "normal";
        String negritoTutor = ckbTutorNegrito.isSelected() ? "bolder" : "normal";
        String corTutor = "#" + cpTutor.getValue().toString().substring(2, 8);
        int recuoTutor = spnRecuoTutor.getValue();
        
        //Atributos Paciente
        int tamanhoPaciente = spnPaciente.getValue();
        String italicoPaciente = ckbPacienteItalico.isSelected() ? "italic" : "normal";
        String negritoPaciente = ckbPacienteNegrito.isSelected() ? "bolder" : "normal";
        String corPaciente = "#" + cpPaciente.getValue().toString().substring(2, 8);
        int recuoPaciente = spnRecuoPaciente.getValue();
        
        //Atributos Rodape
        int tamanhoRodape = spnRodape.getValue();
        String italicoRodape = ckbRodapeItalico.isSelected() ? "italic" : "normal";
        String negritoRodape = ckbRodapeNegrito.isSelected() ? "bolder" : "normal";
        String corRodape = "#" + cpRodape.getValue().toString().substring(2, 8);
        
        //Atributos Veterinario
        int tamanhoVeterinario = spnVeterinario.getValue();
        String italicoVeterinario = ckbVeterinarioItalico.isSelected() ? "italic" : "normal";
        String negritoVeterinario = ckbVeterinarioNegrito.isSelected() ? "bolder" : "normal";
        String corVeterinario = getCorHex(cpVeterinario.getValue());
        int espacamentoVeterinario = spnEspacamentoVeterinario.getValue();
        int recuoVeterinario = spnRecuoVeterinario.getValue();
        int alinhamentoVeterinario = cmbAlinhamentoVeterinario.getSelectionModel().getSelectedIndex();
        String recuoSelecionadoVeterinario;
        String alinhamentoVet;
        switch (alinhamentoVeterinario) {
            case 0:
                alinhamentoVet = "top-left";
                recuoSelecionadoVeterinario = "0px 0px 0px " + recuoVeterinario + "px; ";
                break;
            case 2:
                alinhamentoVet = "top-right";
                recuoSelecionadoVeterinario = "0px " + recuoVeterinario + "px 0px 0px; ";
                break;
            default:
                alinhamentoVet = "top-center";
                recuoSelecionadoVeterinario = "0px; ";
                break;
        }
       
        lblNomeClinica.setStyle(
                "-fx-font-weight: " + negritoNome + "; " +
                "-fx-font-size: " + tamanhoNome + "; " +
                "-fx-font-style: " + italicoNome + "; " +
                "-fx-text-fill: " + corNome );
        
        lblDescricaoClinica.setStyle(
                "-fx-font-weight: " + negritoDescricao + "; " +
                "-fx-font-size: " + tamanhoDescricao + "; " +
                "-fx-font-style: " + italicoDescricao + "; " +
                "-fx-text-fill: " + corDescricao );
        
        lblFraseInicio.setStyle(
                "-fx-font-weight: " + negritoFraseInicio + "; " +
                "-fx-font-size: " + tamanhoFraseInicio + "; " +
                "-fx-font-style: " + italicoFraseInicio + "; " +
                "-fx-text-fill: " + corFraseInicio );
        
        lblFormaUso.setStyle(
                "-fx-font-weight: " + negritoFormaUso + "; " +
                "-fx-font-size: " + tamanhoFormaUso + "; " +
                "-fx-font-style: " + italicoFormaUso + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoFormaUso + "px; " +
                "-fx-text-fill: " + corFormaUso );
        
        lblFormaUso2.setStyle(
                "-fx-font-weight: " + negritoFormaUso + "; " +
                "-fx-font-size: " + tamanhoFormaUso + "; " +
                "-fx-font-style: " + italicoFormaUso + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoFormaUso + "px; " +
                "-fx-text-fill: " + corFormaUso );
        
        lblProduto.setStyle(
                "-fx-font-weight: " + negritoProduto + "; " +
                "-fx-font-size: " + tamanhoProduto + "; " +
                "-fx-font-style: " + italicoProduto + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoProduto + "px; " +
                "-fx-text-fill: " + corProduto );
        
        lblProduto2.setStyle(
                "-fx-font-weight: " + negritoProduto + "; " +
                "-fx-font-size: " + tamanhoProduto + "; " +
                "-fx-font-style: " + italicoProduto + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoProduto + "px; " +
                "-fx-text-fill: " + corProduto );
        
        lblPosologia.setStyle(
                "-fx-font-weight: " + negritoPosologia + "; " +
                "-fx-font-size: " + tamanhoPosologia + "; " +
                "-fx-font-style: " + italicoPosologia + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoPosologia + "px; " +
                "-fx-text-fill: " + corPosologia );
        
        lblPosologia2.setStyle(
                "-fx-font-weight: " + negritoPosologia + "; " +
                "-fx-font-size: " + tamanhoPosologia + "; " +
                "-fx-font-style: " + italicoPosologia + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoPosologia + "px; " +
                "-fx-text-fill: " + corPosologia );
        
        lblObservacoes.setStyle(
                "-fx-font-weight: " + negritoObservacoes + "; " +
                "-fx-font-size: " + tamanhoObservacoes + "; " +
                "-fx-font-style: " + italicoObservacoes + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoObservacoes + "px; " +
                "-fx-text-fill: " + corObservacoes );
        
        lblObservacoes2.setStyle(
                "-fx-font-weight: " + negritoObservacoes + "; " +
                "-fx-font-size: " + tamanhoObservacoes + "; " +
                "-fx-font-style: " + italicoObservacoes + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoObservacoes + "px; " +
                "-fx-text-fill: " + corObservacoes );
        
        lblVeterinario.setStyle(
                "-fx-font-weight: " + negritoVeterinario + "; " +
                "-fx-font-size: " + tamanhoVeterinario + "; " +
                "-fx-font-style: " + italicoVeterinario + "; " +
                "-fx-label-padding: " + recuoSelecionadoVeterinario +
                "-fx-text-fill: " + corVeterinario );
        
        lblVeterinario2.setStyle(
                "-fx-font-weight: " + negritoVeterinario + "; " +
                "-fx-font-size: " + tamanhoVeterinario + "; " +
                "-fx-font-style: " + italicoVeterinario + "; " +
                "-fx-label-padding: " + recuoSelecionadoVeterinario +
                "-fx-text-fill: " + corVeterinario );
        
        lblVeterinario3.setStyle(
                "-fx-font-weight: " + negritoVeterinario + "; " +
                "-fx-font-size: " + tamanhoVeterinario + "; " +
                "-fx-font-style: " + italicoVeterinario + "; " +
                "-fx-label-padding: " + recuoSelecionadoVeterinario +
                "-fx-text-fill: " + corVeterinario );
        
        boxVeterinario.setStyle(
                "-fx-alignment: " + alinhamentoVet + "; " +
                "-fx-padding: " + espacamentoVeterinario + "px 0px 0px 0px");
        
        lblNomeTutor.setStyle(
                "-fx-font-weight: " + negritoTutor + "; " +
                "-fx-font-size: " + tamanhoTutor + "; " +
                "-fx-font-style: " + italicoTutor + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoTutor + "px; " +
                "-fx-text-fill: " + corTutor );
        
        if (ckbTutorCpf.isSelected()){
            lblCpfTutor.setVisible(true);
            lblCpfTutor.setStyle(
                    "-fx-font-weight: " + negritoTutor + "; " +
                    "-fx-font-size: " + tamanhoTutor + "; " +
                    "-fx-font-style: " + italicoTutor + "; " +
                    "-fx-label-padding: 0px 0px 0px " + recuoTutor + "px; " +
                    "-fx-text-fill: " + corTutor );
        }else{
            lblCpfTutor.setVisible(false);
        }
        
        if (ckbTutorTelefone.isSelected()){
            lblFoneTutor.setVisible(true);
            lblFoneTutor.setStyle(
                    "-fx-font-weight: " + negritoTutor + "; " +
                    "-fx-font-size: " + tamanhoTutor + "; " +
                    "-fx-font-style: " + italicoTutor + "; " +
                    "-fx-label-padding: 0px 0px 0px " + recuoTutor + "px; " +
                    "-fx-text-fill: " + corTutor );
        }else{
            lblFoneTutor.setVisible(false);
        }
        
        lblNomePaciente.setStyle(
                "-fx-font-weight: " + negritoPaciente + "; " +
                "-fx-font-size: " + tamanhoPaciente + "; " +
                "-fx-font-style: " + italicoPaciente + "; " +
                "-fx-label-padding: 0px 0px 0px " + recuoPaciente + "px; " +
                "-fx-text-fill: " + corPaciente );
        
        if (ckbPacienteEspecie.isSelected()){
            lblEspeciePaciente.setVisible(true);
            lblEspeciePaciente.setStyle(
                    "-fx-font-weight: " + negritoPaciente + "; " +
                    "-fx-font-size: " + tamanhoPaciente + "; " +
                    "-fx-font-style: " + italicoPaciente + "; " +
                    "-fx-label-padding: 0px 0px 0px " + recuoPaciente + "px; " +
                    "-fx-text-fill: " + corPaciente );
        }else{
            lblEspeciePaciente.setVisible(false);
        }
        
        if (ckbPacienteIdade.isSelected()){
            lblIdadePaciente.setVisible(true);
            lblIdadePaciente.setStyle(
                    "-fx-font-weight: " + negritoPaciente + "; " +
                    "-fx-font-size: " + tamanhoPaciente + "; " +
                    "-fx-font-style: " + italicoPaciente + "; " +
                    "-fx-label-padding: 0px 0px 0px " + recuoPaciente + "px; " +
                    "-fx-text-fill: " + corPaciente );
        }else{
            lblIdadePaciente.setVisible(false);
        }
        
        if(ckbPacienteRaca.isSelected()){
            lblRacaPaciente.setVisible(true);
            lblRacaPaciente.setStyle(
                    "-fx-font-weight: " + negritoPaciente + "; " +
                    "-fx-font-size: " + tamanhoPaciente + "; " +
                    "-fx-font-style: " + italicoPaciente + "; " +
                    "-fx-label-padding: 0px 0px 0px " + recuoPaciente + "px; " +
                    "-fx-text-fill: " + corPaciente );
        }else{
            lblRacaPaciente.setVisible(false);
        }
        
        if(ckbPacientePeso.isSelected()){
            lblPesoPaciente.setVisible(true);
            lblPesoPaciente.setStyle(
                    "-fx-font-weight: " + negritoPaciente + "; " +
                    "-fx-font-size: " + tamanhoPaciente + "; " +
                    "-fx-font-style: " + italicoPaciente + "; " +
                    "-fx-label-padding: 0px 0px 0px " + recuoPaciente + "px; " +
                    "-fx-text-fill: " + corPaciente );
        }else{
            lblPesoPaciente.setVisible(false);
        }
        
        if(ckbPacienteMicrochip.isSelected()){
            lblMicrochipPaciente.setVisible(true);
            lblMicrochipPaciente.setStyle(
                    "-fx-font-weight: " + negritoPaciente + "; " +
                    "-fx-font-size: " + tamanhoPaciente + "; " +
                    "-fx-font-style: " + italicoPaciente + "; " +
                    "-fx-label-padding: 0px 0px 0px " + recuoPaciente + "px; " +
                    "-fx-text-fill: " + corPaciente );
        }else{
            lblMicrochipPaciente.setVisible(false);
        }
        
        lblRodapeData.setStyle(
                "-fx-font-weight: " + negritoRodape + "; "
                + "-fx-font-size: " + tamanhoRodape + "; "
                + "-fx-font-style: " + italicoRodape + "; "
                + "-fx-text-fill: " + corRodape);
        
        if(ckbRodapeEmail.isSelected()){
            lblRodapeEmail.setVisible(true);
            lblRodapeEmail.setStyle(
                    "-fx-font-weight: " + negritoRodape + "; " +
                    "-fx-font-size: " + tamanhoRodape + "; " +
                    "-fx-font-style: " + italicoRodape + "; " +
                    "-fx-text-fill: " + corRodape );
        }else{
            lblRodapeEmail.setVisible(false);
        }
        
        if(ckbRodapeTelefone.isSelected()){
            lblRodapeTelefone.setVisible(true);
            lblRodapeTelefone.setStyle(
                    "-fx-font-weight: " + negritoRodape + "; " +
                    "-fx-font-size: " + tamanhoRodape + "; " +
                    "-fx-font-style: " + italicoRodape + "; " +
                    "-fx-text-fill: " + corRodape );
        }else{
            lblRodapeTelefone.setVisible(false);
        }
        
        if(ckbRodapeEndereco.isSelected()){
            lblRodapeEndereco.setVisible(true);
            lblRodapeEndereco.setStyle(
                    "-fx-font-weight: " + negritoRodape + "; " +
                    "-fx-font-size: " + tamanhoRodape + "; " +
                    "-fx-font-style: " + italicoRodape + "; " +
                    "-fx-text-fill: " + corRodape );
        }else{
            lblRodapeEndereco.setVisible(false);
        }
    }
    
    /*   Números relacionados aos campos a serem formatados:
            1-Nome da clinica
            2-Descrição da clinica (junto ao nome)
            3-Nome do Tutor (A formatação se extende a todos os dados do tutor)
            4-CPF do Tutor
            5-Telefone do Tutor
            6-Nome do Paciente (A formatação se extende a todos os dados do paciente)
            7-Espécie do Paciente
            8-Raça do Paciente
            9-Peso do Paciente
            10-Microchip do Paciente
            11-Frase inicial do corpo da prescrição
            12-Forma de uso dos medicamentos prescritos
            13-Produtos e quantidades prescritos
            14-Posologia
            15-Observações da prescrição
            16-Data do rodapé (A formatação se extende a todo o rodapé)
            17-Email do rodapé
            18-Endereço do rodapé
            19-Telefone do rodapé
            20-Veterinário
            21-Idade do paciente
            22-Logotipo
    */
    
    private void salvarFormatacao(){
        
        FormatacaoPrescricao formNomeClinica;
        FormatacaoPrescricao formDescricaoClinica;
        FormatacaoPrescricao formNomeTutor;
        FormatacaoPrescricao formCpfTutor;
        FormatacaoPrescricao formTelefoneTutor;
        FormatacaoPrescricao formNomePaciente;
        FormatacaoPrescricao formEspeciePaciente;
        FormatacaoPrescricao formRacaPaciente;
        FormatacaoPrescricao formPesoPaciente;
        FormatacaoPrescricao formMicrochipPaciente;
        FormatacaoPrescricao formFraseInicial;
        FormatacaoPrescricao formFormaUso;
        FormatacaoPrescricao formProduto;
        FormatacaoPrescricao formPosologia;
        FormatacaoPrescricao formObservacoes;
        FormatacaoPrescricao formRodapeData;
        FormatacaoPrescricao formRodapeEmail;
        FormatacaoPrescricao formRodapeEndereco;
        FormatacaoPrescricao formRodapeTelefone;
        FormatacaoPrescricao formVeterinario;
        FormatacaoPrescricao formIdadePaciente;
        FormatacaoPrescricao formLogotipo;
        
        if (!listaFormatacoes.isEmpty() && listaFormatacoes.get(0).getModelo() == cmbModelo.getValue()){
            formNomeClinica = listaFormatacoes.get(0);
            formDescricaoClinica = listaFormatacoes.get(1);
            formNomeTutor = listaFormatacoes.get(2);
            formCpfTutor = listaFormatacoes.get(3);
            formTelefoneTutor = listaFormatacoes.get(4);
            formNomePaciente = listaFormatacoes.get(5);
            formEspeciePaciente = listaFormatacoes.get(6);
            formRacaPaciente = listaFormatacoes.get(7);
            formPesoPaciente = listaFormatacoes.get(8);
            formMicrochipPaciente = listaFormatacoes.get(9);
            formFraseInicial = listaFormatacoes.get(10);
            formFormaUso = listaFormatacoes.get(11);
            formProduto = listaFormatacoes.get(12);
            formPosologia = listaFormatacoes.get(13);
            formObservacoes = listaFormatacoes.get(14);
            formRodapeData = listaFormatacoes.get(15);
            formRodapeEmail = listaFormatacoes.get(16);
            formRodapeEndereco = listaFormatacoes.get(17);
            formRodapeTelefone = listaFormatacoes.get(18);
            formVeterinario = listaFormatacoes.get(19);
            formIdadePaciente = listaFormatacoes.get(20);
            formLogotipo = listaFormatacoes.get(21);
        }else{
            formNomeClinica = new FormatacaoPrescricao();
            formDescricaoClinica = new FormatacaoPrescricao();
            formNomeTutor = new FormatacaoPrescricao();
            formCpfTutor = new FormatacaoPrescricao();
            formTelefoneTutor = new FormatacaoPrescricao();
            formNomePaciente = new FormatacaoPrescricao();
            formEspeciePaciente = new FormatacaoPrescricao();
            formRacaPaciente = new FormatacaoPrescricao();
            formPesoPaciente = new FormatacaoPrescricao();
            formMicrochipPaciente = new FormatacaoPrescricao();
            formFraseInicial = new FormatacaoPrescricao();
            formFormaUso = new FormatacaoPrescricao();
            formProduto = new FormatacaoPrescricao();
            formPosologia = new FormatacaoPrescricao();
            formObservacoes = new FormatacaoPrescricao();
            formRodapeData = new FormatacaoPrescricao();
            formRodapeEmail = new FormatacaoPrescricao();
            formRodapeEndereco = new FormatacaoPrescricao();
            formRodapeTelefone = new FormatacaoPrescricao();
            formVeterinario = new FormatacaoPrescricao();
            formIdadePaciente = new FormatacaoPrescricao();
            formLogotipo = new FormatacaoPrescricao();
        }
        
        formNomeClinica.setItem(1);
        formNomeClinica.setTexto(txtNomeClinica.getText());
        formNomeClinica.setTamanho(spnNomeClinica.getValue());
        formNomeClinica.setCor(getCorHex(cpNomeClinica.getValue()));
        formNomeClinica.setNegrito(ckbNomeClinicaNegrito.isSelected());
        formNomeClinica.setItalico(ckbNomeClinicaItalico.isSelected());
        formNomeClinica.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formNomeClinica);
        
        formDescricaoClinica.setItem(2);
        formDescricaoClinica.setTexto(txtDescricaoClinica.getText());
        formDescricaoClinica.setTamanho(spnDescricaoClinica.getValue());
        formDescricaoClinica.setCor(getCorHex(cpDescricaoClinica.getValue()));
        formDescricaoClinica.setNegrito(ckbDescricaoClinicaNegrito.isSelected());
        formDescricaoClinica.setItalico(ckbDescricaoClinicaItalico.isSelected());
        formDescricaoClinica.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formDescricaoClinica);
        
        formNomeTutor.setItem(3);
        formNomeTutor.setTamanho(spnTutor.getValue());
        formNomeTutor.setRecuo(spnRecuoTutor.getValue());
        formNomeTutor.setCor(getCorHex(cpTutor.getValue()));
        formNomeTutor.setNegrito(ckbTutorNegrito.isSelected());
        formNomeTutor.setItalico(ckbTutorItalico.isSelected());
        formNomeTutor.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formNomeTutor);
        
        formCpfTutor.setItem(4);
        formCpfTutor.setTamanho(spnTutor.getValue());
        formCpfTutor.setRecuo(spnRecuoTutor.getValue());
        formCpfTutor.setCor(getCorHex(cpTutor.getValue()));
        formCpfTutor.setNegrito(ckbTutorNegrito.isSelected());
        formCpfTutor.setItalico(ckbTutorItalico.isSelected());
        formCpfTutor.setPresente(ckbTutorCpf.isSelected());
        formCpfTutor.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formCpfTutor);
        
        formTelefoneTutor.setItem(5);
        formTelefoneTutor.setTamanho(spnTutor.getValue());
        formTelefoneTutor.setRecuo(spnRecuoTutor.getValue());
        formTelefoneTutor.setCor(getCorHex(cpTutor.getValue()));
        formTelefoneTutor.setNegrito(ckbTutorNegrito.isSelected());
        formTelefoneTutor.setItalico(ckbTutorItalico.isSelected());
        formTelefoneTutor.setPresente(ckbTutorTelefone.isSelected());
        formTelefoneTutor.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formTelefoneTutor);
        
        formNomePaciente.setItem(6);
        formNomePaciente.setTamanho(spnPaciente.getValue());
        formNomePaciente.setRecuo(spnRecuoPaciente.getValue());
        formNomePaciente.setCor(getCorHex(cpPaciente.getValue()));
        formNomePaciente.setNegrito(ckbPacienteNegrito.isSelected());
        formNomePaciente.setItalico(ckbPacienteItalico.isSelected());
        formNomePaciente.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formNomePaciente);

        formEspeciePaciente.setItem(7);
        formEspeciePaciente.setTamanho(spnPaciente.getValue());
        formEspeciePaciente.setRecuo(spnRecuoPaciente.getValue());
        formEspeciePaciente.setCor(getCorHex(cpPaciente.getValue()));
        formEspeciePaciente.setNegrito(ckbPacienteNegrito.isSelected());
        formEspeciePaciente.setItalico(ckbPacienteItalico.isSelected());
        formEspeciePaciente.setPresente(ckbPacienteEspecie.isSelected());
        formEspeciePaciente.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formEspeciePaciente);
        
        formRacaPaciente.setItem(8);
        formRacaPaciente.setTamanho(spnPaciente.getValue());
        formRacaPaciente.setRecuo(spnRecuoPaciente.getValue());
        formRacaPaciente.setCor(getCorHex(cpPaciente.getValue()));
        formRacaPaciente.setNegrito(ckbPacienteNegrito.isSelected());
        formRacaPaciente.setItalico(ckbPacienteItalico.isSelected());
        formRacaPaciente.setPresente(ckbPacienteRaca.isSelected());
        formRacaPaciente.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formRacaPaciente);
        
        formPesoPaciente.setItem(9);
        formPesoPaciente.setTamanho(spnPaciente.getValue());
        formPesoPaciente.setRecuo(spnRecuoPaciente.getValue());
        formPesoPaciente.setCor(getCorHex(cpPaciente.getValue()));
        formPesoPaciente.setNegrito(ckbPacienteNegrito.isSelected());
        formPesoPaciente.setItalico(ckbPacienteItalico.isSelected());
        formPesoPaciente.setPresente(ckbPacientePeso.isSelected());
        formPesoPaciente.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formPesoPaciente);
        
        formMicrochipPaciente.setItem(10);
        formMicrochipPaciente.setTamanho(spnPaciente.getValue());
        formMicrochipPaciente.setRecuo(spnRecuoPaciente.getValue());
        formMicrochipPaciente.setCor(getCorHex(cpPaciente.getValue()));
        formMicrochipPaciente.setNegrito(ckbPacienteNegrito.isSelected());
        formMicrochipPaciente.setItalico(ckbPacienteItalico.isSelected());
        formMicrochipPaciente.setPresente(ckbPacienteMicrochip.isSelected());
        formMicrochipPaciente.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formMicrochipPaciente);
        
        formFraseInicial.setItem(11);
        formFraseInicial.setTexto(txtFraseInicio.getText());
        formFraseInicial.setTamanho(spnFraseInicio.getValue());
        formFraseInicial.setCor(getCorHex(cpFraseInicio.getValue()));
        formFraseInicial.setNegrito(ckbFraseInicioNegrito.isSelected());
        formFraseInicial.setItalico(ckbFraseInicioItalico.isSelected());
        formFraseInicial.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formFraseInicial);
        
        formFormaUso.setItem(12);
        formFormaUso.setTamanho(spnFormaUso.getValue());
        formFormaUso.setRecuo(spnRecuoFormaUso.getValue());
        formFormaUso.setCor(getCorHex(cpFormaUso.getValue()));
        formFormaUso.setNegrito(ckbFormaUsoNegrito.isSelected());
        formFormaUso.setItalico(ckbFormaUsoItalico.isSelected());
        formFormaUso.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formFormaUso);
        
        formProduto.setItem(13);
        formProduto.setTamanho(spnProduto.getValue());
        formProduto.setRecuo(spnRecuoProduto.getValue());
        formProduto.setCor(getCorHex(cpProduto.getValue()));
        formProduto.setNegrito(ckbProdutoNegrito.isSelected());
        formProduto.setItalico(ckbProdutoItalico.isSelected());
        formProduto.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formProduto);
        
        formPosologia.setItem(14);
        formPosologia.setTamanho(spnPosologia.getValue());
        formPosologia.setRecuo(spnRecuoPosologia.getValue());
        formPosologia.setCor(getCorHex(cpPosologia.getValue()));
        formPosologia.setNegrito(ckbPosologiaNegrito.isSelected());
        formPosologia.setItalico(ckbPosologiaItalico.isSelected());
        formPosologia.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formPosologia);
        
        formObservacoes.setItem(15);
        formObservacoes.setTamanho(spnObservacoes.getValue());
        formObservacoes.setRecuo(spnRecuoObservacoes.getValue());
        formObservacoes.setCor(getCorHex(cpObservacoes.getValue()));
        formObservacoes.setNegrito(ckbObservacoesNegrito.isSelected());
        formObservacoes.setItalico(ckbObservacoesItalico.isSelected());
        formObservacoes.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formObservacoes);
        
        formRodapeData.setItem(16);
        formRodapeData.setTamanho(spnRodape.getValue());
        formRodapeData.setCor(getCorHex(cpRodape.getValue()));
        formRodapeData.setNegrito(ckbRodapeNegrito.isSelected());
        formRodapeData.setItalico(ckbRodapeItalico.isSelected());
        formRodapeData.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formRodapeData);
        
        formRodapeEmail.setItem(17);
        formRodapeEmail.setTamanho(spnRodape.getValue());
        formRodapeEmail.setCor(getCorHex(cpRodape.getValue()));
        formRodapeEmail.setNegrito(ckbRodapeNegrito.isSelected());
        formRodapeEmail.setItalico(ckbRodapeItalico.isSelected());
        formRodapeEmail.setPresente(ckbRodapeEmail.isSelected());
        formRodapeEmail.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formRodapeEmail);
        
        formRodapeEndereco.setItem(18);
        formRodapeEndereco.setTamanho(spnRodape.getValue());
        formRodapeEndereco.setCor(getCorHex(cpRodape.getValue()));
        formRodapeEndereco.setNegrito(ckbRodapeNegrito.isSelected());
        formRodapeEndereco.setItalico(ckbRodapeItalico.isSelected());
        formRodapeEndereco.setPresente(ckbRodapeEndereco.isSelected());
        formRodapeEndereco.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formRodapeEndereco);
        
        formRodapeTelefone.setItem(19);
        formRodapeTelefone.setTamanho(spnRodape.getValue());
        formRodapeTelefone.setCor(getCorHex(cpRodape.getValue()));
        formRodapeTelefone.setNegrito(ckbRodapeNegrito.isSelected());
        formRodapeTelefone.setItalico(ckbRodapeItalico.isSelected());
        formRodapeTelefone.setPresente(ckbRodapeTelefone.isSelected());
        formRodapeTelefone.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formRodapeTelefone);
        
        formVeterinario.setItem(20);
        formVeterinario.setTamanho(spnVeterinario.getValue());
        formVeterinario.setCor(getCorHex(cpVeterinario.getValue()));
        formVeterinario.setNegrito(ckbVeterinarioNegrito.isSelected());
        formVeterinario.setItalico(ckbVeterinarioItalico.isSelected());
        formVeterinario.setEspacamento(spnEspacamentoVeterinario.getValue());
        formVeterinario.setAlinhamento(cmbAlinhamentoVeterinario.getSelectionModel().getSelectedIndex());
        formVeterinario.setRecuo(spnRecuoVeterinario.getValue());
        formVeterinario.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formVeterinario);
        
        formIdadePaciente.setItem(21);
        formIdadePaciente.setTamanho(spnPaciente.getValue());
        formIdadePaciente.setRecuo(spnRecuoPaciente.getValue());
        formIdadePaciente.setCor(getCorHex(cpPaciente.getValue()));
        formIdadePaciente.setNegrito(ckbPacienteNegrito.isSelected());
        formIdadePaciente.setItalico(ckbPacienteItalico.isSelected());
        formIdadePaciente.setPresente(ckbPacienteIdade.isSelected());
        formIdadePaciente.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formIdadePaciente);
        
        
        int tamanhoLogotipo;
        if (rbTamanhoLogoPequeno.isSelected()){
            tamanhoLogotipo = 1;
        }else if (rbTamanhoLogoMedio.isSelected()){
            tamanhoLogotipo = 2;
        }else {
            tamanhoLogotipo = 3;
        }
        int formatoLogotipo;
        if (rbFormatoLogoQuadrado.isSelected()){
            formatoLogotipo = 1;
        }else if (rbFormatoLogoPaisagem.isSelected()){
            formatoLogotipo = 2;
        }else {
            formatoLogotipo = 3;
        }
        formLogotipo.setItem(22);
        formLogotipo.setTamanho(tamanhoLogotipo); 
        formLogotipo.setAlinhamento(formatoLogotipo);
        formLogotipo.setRecuo(spnRecuoEsqLogo.getValue());
        formLogotipo.setEspacamento(spnRecuoTopoLogo.getValue());
        formLogotipo.setModelo(cmbModelo.getValue());
        listaFormatacoes.add(formLogotipo);
        
        new PrescricaoService().salvarOuAtualizarFormatacao(listaFormatacoes);
    }
    
    private Image getCaminhoLogo(){
        File pastaPrograma = new File(System.getProperty("user.dir"));
        File destino = new File(pastaPrograma, "logo" + cmbModelo.getValue() + ".png");
        Image imagem = new Image(destino.toURI().toString());
        return imagem;
    }
    
    private void aplicarFormatacao(){
        
        imgLogo.setImage(getCaminhoLogo());
        for (FormatacaoPrescricao form : listaFormatacoes){
            switch (form.getItem()){
                case 1:
                    txtNomeClinica.setText(form.getTexto());
                    spnNomeClinica.getValueFactory().setValue(form.getTamanho());
                    cpNomeClinica.setValue(Color.web(form.getCor()));
                    ckbNomeClinicaItalico.setSelected(form.isItalico());
                    ckbNomeClinicaNegrito.setSelected(form.isNegrito());
                    break;
                case 2:
                    txtDescricaoClinica.setText(form.getTexto());
                    spnDescricaoClinica.getValueFactory().setValue(form.getTamanho());
                    cpDescricaoClinica.setValue(Color.web(form.getCor()));
                    ckbDescricaoClinicaItalico.setSelected(form.isItalico());
                    ckbDescricaoClinicaNegrito.setSelected(form.isNegrito());
                    break;
                case 3:
                    spnDescricaoClinica.getValueFactory().setValue(form.getTamanho());
                    spnRecuoTutor.getValueFactory().setValue(form.getRecuo());
                    cpTutor.setValue(Color.web(form.getCor()));
                    ckbTutorItalico.setSelected(form.isItalico());
                    ckbTutorNegrito.setSelected(form.isNegrito());
                    break;
                case 4:
                    ckbTutorCpf.setSelected(form.isPresente());
                    break;
                case 5:
                    ckbTutorTelefone.setSelected(form.isPresente());
                    break;
                case 6:
                    spnPaciente.getValueFactory().setValue(form.getTamanho());
                    spnRecuoPaciente.getValueFactory().setValue(form.getRecuo());
                    cpPaciente.setValue(Color.web(form.getCor()));
                    ckbPacienteItalico.setSelected(form.isItalico());
                    ckbPacienteNegrito.setSelected(form.isNegrito());
                    break;
                case 7:
                    ckbPacienteEspecie.setSelected(form.isPresente());
                    break;
                case 8:
                    ckbPacienteRaca.setSelected(form.isPresente());
                    break;
                case 9:
                    ckbPacientePeso.setSelected(form.isPresente());
                    break;
                case 10:
                    ckbPacienteMicrochip.setSelected(form.isPresente());
                    break;
                case 11:
                    txtFraseInicio.setText(form.getTexto());
                    spnFraseInicio.getValueFactory().setValue(form.getTamanho());
                    cpFraseInicio.setValue(Color.web(form.getCor()));
                    ckbFraseInicioItalico.setSelected(form.isItalico());
                    ckbFraseInicioNegrito.setSelected(form.isNegrito());
                    break;
                case 12:
                    spnFormaUso.getValueFactory().setValue(form.getTamanho());
                    spnRecuoFormaUso.getValueFactory().setValue(form.getRecuo());
                    cpFormaUso.setValue(Color.web(form.getCor()));
                    ckbFormaUsoItalico.setSelected(form.isItalico());
                    ckbFormaUsoNegrito.setSelected(form.isNegrito());
                    break;
                case 13:
                    spnProduto.getValueFactory().setValue(form.getTamanho());
                    spnRecuoProduto.getValueFactory().setValue(form.getRecuo());
                    cpProduto.setValue(Color.web(form.getCor()));
                    ckbProdutoItalico.setSelected(form.isItalico());
                    ckbProdutoNegrito.setSelected(form.isNegrito());
                    break;
                case 14:
                    spnPosologia.getValueFactory().setValue(form.getTamanho());
                    spnRecuoPosologia.getValueFactory().setValue(form.getRecuo());
                    cpPosologia.setValue(Color.web(form.getCor()));
                    ckbPosologiaItalico.setSelected(form.isItalico());
                    ckbPosologiaNegrito.setSelected(form.isNegrito());
                    break;
                case 15:
                    spnObservacoes.getValueFactory().setValue(form.getTamanho());
                    spnRecuoObservacoes.getValueFactory().setValue(form.getRecuo());
                    cpObservacoes.setValue(Color.web(form.getCor()));
                    ckbObservacoesItalico.setSelected(form.isItalico());
                    ckbObservacoesNegrito.setSelected(form.isNegrito());
                    break;
                case 16:
                    spnRodape.getValueFactory().setValue(form.getTamanho());
                    cpRodape.setValue(Color.web(form.getCor()));
                    ckbRodapeItalico.setSelected(form.isItalico());
                    ckbRodapeNegrito.setSelected(form.isNegrito());
                    break;
                case 17:
                    ckbRodapeEmail.setSelected(form.isPresente());
                    break;
                case 18:
                    ckbRodapeEndereco.setSelected(form.isPresente());
                    break;
                case 19:
                    ckbRodapeTelefone.setSelected(form.isPresente());
                    break;
                case 20:
                    spnVeterinario.getValueFactory().setValue(form.getTamanho());
                    cpVeterinario.setValue(Color.web(form.getCor()));
                    ckbVeterinarioItalico.setSelected(form.isItalico());
                    ckbVeterinarioNegrito.setSelected(form.isNegrito());
                    spnEspacamentoVeterinario.getValueFactory().setValue(form.getEspacamento());
                    cmbAlinhamentoVeterinario.getSelectionModel().select(form.getAlinhamento());
                    spnRecuoVeterinario.getValueFactory().setValue(form.getRecuo());
                    break;
                case 21:
                    ckbPacienteIdade.setSelected(form.isPresente());
                    break;
                case 22:
                    switch(form.getTamanho()){
                        case 1:
                            rbTamanhoLogoPequeno.setSelected(true);
                            break;
                        case 2:
                            rbTamanhoLogoMedio.setSelected(true);
                            break;
                        case 3:
                            rbTamanhoLogoGrande.setSelected(true);
                            break;
                        default:
                            break;
                    }
                    switch(form.getAlinhamento()){
                        case 1:
                            rbFormatoLogoQuadrado.setSelected(true);
                            break;
                        case 2:
                            rbFormatoLogoPaisagem.setSelected(true);
                            break;
                        case 3:
                            rbFormatoLogoRetrato.setSelected(true);
                            break;
                        default:
                            break;
                    }
                    spnRecuoEsqLogo.getValueFactory().setValue(form.getRecuo());
                    spnRecuoTopoLogo.getValueFactory().setValue(form.getEspacamento());
                    break;
                default:
                    limpaCampos();
                    break;
            }
        }
        
        if (listaFormatacoes.isEmpty()) {
            limpaCampos();
        }
        
        formatarLabels();
    }
    
    private void definirModeloComoAtivo(){
        int modelo = cmbModelo.getValue();
        new PrescricaoService().definirModeloFormatacaoComoAtivo(modelo);
    }
    
    private String getCorHex(Color cor){
        return "#" + cor.toString().substring(2, 8);
    }
    
    private void limpaCampos() {
        txtNomeClinica.setText("");
        spnNomeClinica.getValueFactory().setValue(12);
        cpNomeClinica.setValue(Color.web("#000000"));
        ckbNomeClinicaItalico.setSelected(false);
        ckbNomeClinicaNegrito.setSelected(false);
        txtDescricaoClinica.setText("");
        spnDescricaoClinica.getValueFactory().setValue(12);
        cpDescricaoClinica.setValue(Color.web("#000000"));
        ckbDescricaoClinicaItalico.setSelected(false);
        ckbDescricaoClinicaNegrito.setSelected(false);
        spnTutor.getValueFactory().setValue(12);
        cpTutor.setValue(Color.web("#000000"));
        ckbTutorItalico.setSelected(false);
        ckbTutorNegrito.setSelected(false);
        spnRecuoTutor.getValueFactory().setValue(0);
        ckbTutorCpf.setSelected(false);
        ckbTutorTelefone.setSelected(false);
        spnPaciente.getValueFactory().setValue(12);
        cpPaciente.setValue(Color.web("#000000"));
        ckbPacienteItalico.setSelected(false);
        ckbPacienteNegrito.setSelected(false);
        spnRecuoPaciente.getValueFactory().setValue(0);
        ckbPacienteEspecie.setSelected(false);
        ckbPacienteRaca.setSelected(false);
        ckbPacientePeso.setSelected(false);
        ckbPacienteIdade.setSelected(false);
        ckbPacienteMicrochip.setSelected(false);
        txtFraseInicio.setText("");
        spnFraseInicio.getValueFactory().setValue(12);
        cpFraseInicio.setValue(Color.web("#000000"));
        ckbFraseInicioItalico.setSelected(false);
        ckbFraseInicioNegrito.setSelected(false);
        spnFormaUso.getValueFactory().setValue(12);
        cpFormaUso.setValue(Color.web("#000000"));
        ckbFormaUsoItalico.setSelected(false);
        ckbFormaUsoNegrito.setSelected(false);
        spnRecuoFormaUso.getValueFactory().setValue(0);
        spnProduto.getValueFactory().setValue(12);
        cpProduto.setValue(Color.web("#000000"));
        ckbProdutoItalico.setSelected(false);
        ckbProdutoNegrito.setSelected(false);
        spnRecuoProduto.getValueFactory().setValue(0);
        spnPosologia.getValueFactory().setValue(12);
        cpPosologia.setValue(Color.web("#000000"));
        ckbPosologiaItalico.setSelected(false);
        ckbPosologiaNegrito.setSelected(false);
        spnRecuoPosologia.getValueFactory().setValue(0);
        spnObservacoes.getValueFactory().setValue(12);
        cpObservacoes.setValue(Color.web("#000000"));
        ckbObservacoesItalico.setSelected(false);
        ckbObservacoesNegrito.setSelected(false);
        spnRecuoObservacoes.getValueFactory().setValue(0);
        spnRodape.getValueFactory().setValue(12);
        cpRodape.setValue(Color.web("#000000"));
        ckbRodapeItalico.setSelected(false);
        ckbRodapeNegrito.setSelected(false);
        ckbRodapeEmail.setSelected(false);
        ckbRodapeEndereco.setSelected(false);
        ckbRodapeTelefone.setSelected(false);
        cmbAlinhamentoVeterinario.getSelectionModel().select(2);
        cpVeterinario.setValue(Color.web("#000000"));
        spnVeterinario.getValueFactory().setValue(11);
        spnEspacamentoVeterinario.getValueFactory().setValue(0);
        spnRecuoVeterinario.getValueFactory().setValue(0);
        ckbVeterinarioItalico.setSelected(false);
        ckbVeterinarioNegrito.setSelected(false);
        rbTamanhoLogoMedio.setSelected(true);
        rbFormatoLogoQuadrado.setSelected(true);
    }
    
    private void limparCamposClinica(){
        txtNome.setText("");
        txtRazaoSocial.setText("");
        txtCnpj.setText("");
        txtRua.setText("");
        txtNumero.setText("");
        txtTelefone.setText("");
        txtTelefoneSec.setText("");
        txtEmail.setText("");
        txtObservacoes.setText("");
        txtCep.setText("");
        listaVeterinariosSelecionados.clear();
        listarVeterinariosNaListView();
        cmbMunicipio.getSelectionModel().select(null);
        cmbBairro.getSelectionModel().select(null);
        cmbVeterinarioResponsavel.getSelectionModel().select(null);
        cmbOutrosVeterinarios.getSelectionModel().select(null);
    }
    
    public static void setEstilo(String estiloEscolhido){
        if (estiloEscolhido.equals("Padrão")){
            estiloEscolhido = "estiloPadrao.css";
        }else if(estiloEscolhido.equals("BlueSky")){
            estiloEscolhido = "blueSky.css";
        }
        
        estilo = "styles/" + estiloEscolhido;
    }
    
    public void setIndiceDoEstiloAtual(){
        
        if (estilo.substring(7).equals("estiloPadrao.css")){
            cmbEstilo.getSelectionModel().select(0);
        }else if (estilo.substring(7).equals("blueSky.css")){
            cmbEstilo.getSelectionModel().select(1);
        }
    }
    
    public String getNomeEstilo(){
        if (estilo.substring(7).equals("estiloPadrao.css")){
            return "Estilo Padrão";
        }else if (estilo.substring(7).equals("blueSky.css")){
            return "Blue Sky";
        }else{
            return "";
        }
        
    }
    
    /*  Números relacionados às preferências (Primeira coluna é o nome da preferência, segunda é a lista das opções):
    1 [1,2] - Modo de uso [clinica, instituicao]
    2 [1,2] - Logotipo do software [Logo VetSof, Logo personalizado]
    
    */
    
    private void ajustarTelaPreferencias(){
        if (preferencias.get(1) == 1){
            rbModoClinica.setSelected(true);
            if (!tabPane.getTabs().contains(tabDadosClinica)) tabPane.getTabs().add(tabDadosClinica);
            if (!tabPane.getTabs().contains(tabModeloPrescricao)) tabPane.getTabs().add(tabModeloPrescricao);
        }else{
            rbModoInstituicao.setSelected(true);
            tabPane.getTabs().remove(tabDadosClinica);
            tabPane.getTabs().remove(tabModeloPrescricao);
        }
        
        if (preferencias.get(2) == 1){
            rbLogoVetsof.setSelected(true);
        }else{
            rbLogoPersonalizado.setSelected(true);
        }
    }
    
    private void atualizarPreferencias(){
        preferencias = new TreeMap<>();
        
        if (rbModoClinica.isSelected()){
            preferencias.put(1, 1);
        }else{
            preferencias.put(1, 2);
        }
        if (rbLogoVetsof.isSelected()){
            preferencias.put(2, 1);
        }else{
            preferencias.put(2, 2);
        }
        new UtilitarioService().atualizarPreferencias(preferencias);
    }
    
    
    //MÉTODOS PARA CLINICA PRINCIPAL #############################################################################################################################################################
    
    private void listarBairros() {
        if (cmbMunicipio.getSelectionModel().getSelectedIndex() != -1) {
            List<Bairro> listaBairros = new UtilitarioService().getBairros(cmbMunicipio.getValue());
            ObservableList<Bairro> listaObsEsp = FXCollections.observableArrayList(listaBairros);
            cmbBairro.setItems(listaObsEsp);
        }
    }
    
    public void setDadosClinica() {
        clinica = new ClinicaService().getClinicaPrincipal();
        txtNome.setText(clinica.getNomeClinica());
        txtRazaoSocial.setText(clinica.getRazaoSocial());
        txtCnpj.setText(Utils.imprimeCNPJ(clinica.getCnpj()));
        txtRua.setText(clinica.getRuaClinica());
        txtNumero.setText(clinica.getNumeroClinica());
        txtCep.setText(Utils.imprimeCep(clinica.getCepClinica()));
        txtTelefone.setText(Utils.imprimeTelefone(clinica.getTelefoneClinica()));
        txtTelefoneSec.setText(Utils.imprimeTelefone(clinica.getTelefoneAlternativoClinica()));
        txtEmail.setText(clinica.getEmailClinica());
        txtObservacoes.setText(clinica.getObservacaoClinica());
        cmbMunicipio.setValue(clinica.getMunicipioClinica());
        listarBairros();
        cmbBairro.setValue(clinica.getBairroClinica());
        cmbVeterinarioResponsavel.setValue(clinica.getVeterinarioClinica());
        ObservableList<Veterinario> listaObsVeterinarios = FXCollections.observableArrayList(new ClinicaService().getVeterinariosDaClinica(clinica.getIdClinica()));
        listViewVeterinarios.setItems(listaObsVeterinarios);

    }
    
    public void setValoresPadrao(){
        for (ValorPadrao item : valoresPadrao){
            switch (item.getCodigoValorPadrao()) {
                case 1 -> txtValorDiariaInternacao.setText(Utils.imprimeValor(String.valueOf(item.getValorPadraoNumeral())));
                case 2 -> scmbFormaPagamento.setValue(FormaPagamento.valueOf(item.getValorPadraoString()));
                default -> {
                }
            }
        }
    }

    private void listarMunicipios() {
        List<Municipio> listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsEsp = FXCollections.observableArrayList(listaMunicipios);
        cmbMunicipio.setItems(listaObsEsp);
    }
    
    private void listarVeterinarios() {
        List<Veterinario> listaVeterinarios = new VeterinarioService().getAll(-1, "");
        ObservableList<Veterinario> listaObsEsp = FXCollections.observableArrayList(listaVeterinarios);
        cmbVeterinarioResponsavel.setItems(listaObsEsp);
        cmbOutrosVeterinarios.setItems(listaObsEsp);
    }
    
    private void listarVeterinariosNaListView() {
        ObservableList<Veterinario> listaObsVeterinariosSelecionados = FXCollections.observableArrayList(listaVeterinariosSelecionados);
        listViewVeterinarios.setItems(listaObsVeterinariosSelecionados);
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
