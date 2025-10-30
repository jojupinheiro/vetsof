package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.classes.Atendimento;
import model.classes.Clinica;
import model.classes.Exame;
import model.classes.ExameRealizado;
import model.classes.Pet;
import model.classes.ProdutoVacina;
import model.classes.Servico;
import model.classes.ServicoRealizado;
import model.classes.Tutor;
import model.classes.Vacina;
import model.classes.Veterinario;
import model.exceptions.ValidacaoException;
import model.services.AtendimentoService;
import model.services.ClinicaService;
import model.services.ExameService;
import model.services.PetService;
import model.services.ProdutoVacinaService;
import model.services.ServicoService;
import model.services.TutorService;
import model.services.VacinaService;
import view.utils.MascarasFX;
import view.utils.Utils;
import org.controlsfx.control.SearchableComboBox;

/*
*
* @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
*/

public class TelaCadastroAtendimentoController extends MenuPrincipal implements Initializable {

    @FXML    private Button btnAdicionarClinica;
    @FXML    private Button btnAdicionarPet;
    @FXML    private Button btnAdicionarTutor;
    @FXML    private Button btnAdicionarVeterinario;
    @FXML    private Button btnAtualizarHorario;
    @FXML    private Button btnCadastrarExame;
    @FXML    private Button btnCadastrarServico;
    @FXML    private Button btnCadastrarVacina;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserirExame;
    @FXML    private Button btnInserirNomeVacina;
    @FXML    private Button btnInserirServico;
    @FXML    private Button btnInserirTipoVacina;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnLimparExame;
    @FXML    private Button btnLimparServico;
    @FXML    private Button btnLimparVacina;
    @FXML    private Button btnPrescricao;
    @FXML    private Button btnRemoverExame;
    @FXML    private Button btnRemoverServico;
    @FXML    private Button btnRemoverVacina;
    @FXML    private Button btnSalvar;
    @FXML    private Button btnSalvarEPrescricao;
    @FXML    private DatePicker dpDataAtendimento;
    @FXML    private DatePicker dpDtReforco;
    @FXML    private DatePicker dpDtVacina;
    @FXML    private HBox boxDataReforco;
    @FXML    private Label lblErroClinica;
    @FXML    private Label lblErroTutor;
    @FXML    private Label lblErroDataAtendimento;
    @FXML    private Label lblErroDtVacina;
    @FXML    private Label lblErroDtVacinaFutura;
    @FXML    private Label lblErroDtVacinaPassada;
    @FXML    private Label lblErroExame;
    @FXML    private Label lblErroHorario;
    @FXML    private Label lblErroVeterinario;
    @FXML    private Label lblErroPet;
    @FXML    private Label lblErroServico;
    @FXML    private Label lblErroVacina;
    @FXML    private Label lblErroTipoVacina;
    @FXML    private Label lblErroValorTotal;
    @FXML    private Label lblLabelProtocolo;
    @FXML    private Label lblProtocolo;
    @FXML    private Label lblProgramadaOuProximaDose;
    @FXML    private Label lblProximaDose;
    @FXML    private Label lblErroStatusVacina;
    @FXML    private ListView<ServicoRealizado> listViewServicos;
    @FXML    private ListView<ExameRealizado> listViewExames;
    @FXML    private ListView<Vacina> listViewVacinas;
    @FXML    private RadioButton rbAplicada;
    @FXML    private RadioButton rbProgramada;
    @FXML    private RadioButton rbProximaDoseNao;
    @FXML    private RadioButton rbProximaDoseSim;
    @FXML    private SearchableComboBox<Clinica> scmbClinica;
    @FXML    private SearchableComboBox<Exame> scmbExame;
    @FXML    private SearchableComboBox<Pet> scmbPet;
    @FXML    private SearchableComboBox<ProdutoVacina> scmbCategoriaVacina;
    @FXML    private SearchableComboBox<ProdutoVacina> scmbVacina;
    @FXML    private SearchableComboBox<Servico> scmbServico;
    @FXML    private SearchableComboBox<Tutor> scmbTutor;
    @FXML    private SearchableComboBox<Veterinario> scmbVeterinario;
    @FXML    private Spinner<Integer> spnDoseAtual;
    @FXML    private Spinner<Integer> spnDosesTotais;
    @FXML    private Spinner<Integer> spnQuantidadeServico;
    @FXML    private Tab tabDadosAtendimento;
    @FXML    private Tab tabExames;
    @FXML    private Tab tabVacinas;
    @FXML    private TextArea txtAnamnese;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextArea txtObservacaoExame;
    @FXML    private TextArea txtObservacaoServico;
    @FXML    private TextArea txtObservacaoVacina;
    @FXML    private TextArea txtResultadoExame;
    @FXML    private TextField txtHorarioAtendimento;
    @FXML    private TextField txtDiagnostico;
    @FXML    private TextField txtValorExame;
    @FXML    private TextField txtValorTotal;
    @FXML    private TextField txtValorServico;
    @FXML    private TextField txtValorVacina;
    @FXML    private ToggleButton btn21Dias;
    @FXML    private ToggleButton btn30Dias;
    @FXML    private ToggleButton btn45Dias;
    @FXML    private ToggleButton btn6Meses;
    @FXML    private ToggleButton btn1Ano;
    
    private Atendimento atendimento;
    private Clinica clinicaPrincipal = new ClinicaService().getClinicaPrincipal();
    private List<Tutor> listaTutores = new TutorService().getAll(-1, "");
    private List<Exame> listaExames = new ExameService().getAll();
    private List<ServicoRealizado> listaServicosSelecionados = new ArrayList<>();
    private List<ExameRealizado> listaExamesSelecionados = new ArrayList<>();    
    private List<Servico> listaServicos = new ServicoService().getAll();
    private List<Pet> listaPets = new PetService().getAll(-1, "");
    private List<Clinica> listaClinicas = new ClinicaService().getAll(-1, "");
    private List<Veterinario> listaVeterinarios = new ArrayList<>();
    private List<Vacina> listaVacinas;
    private List<Vacina> listaVacinasSelecionadas = new ArrayList<>();
    private List<ProdutoVacina> listaTiposVacina = new ArrayList<>();
    private List<ProdutoVacina> listaNomesVacina = new ArrayList<>();

    public void setAtendimento(Atendimento atendimento, List<Vacina> listaVacinas) {
        this.atendimento = atendimento;
        this.listaVacinas = listaVacinas;
        // Carregando o atendimento para os campos da tela
        lblProtocolo.setText(String.valueOf(atendimento.getIdAtendimento())); //Setando o protocolo do atendimento
        lblLabelProtocolo.setText("Protocolo: ");
        scmbPet.setValue(atendimento.getPet()); //Setando o pet que foi atendido
        scmbPet.setDisable(false);
        scmbTutor.setValue(atendimento.getTutor());
        listarClinicas();
        scmbClinica.getSelectionModel().select(atendimento.getClinica()); //Setando a clínica que realizou o atendimento
        listarVeterinarios();
        scmbVeterinario.getSelectionModel().select(atendimento.getVeterinario());
        scmbVeterinario.setDisable(false);
        dpDataAtendimento.setValue(atendimento.getDataAtendimento()); //Setando a data do atendimento
        txtHorarioAtendimento.setText(String.valueOf(atendimento.getHorarioAtendimento())); //Setando o horário do atendimento
        txtValorTotal.setText(String.valueOf(atendimento.getValorTotal())); //Setando o valor total do atendimento
        txtObservacao.setText(atendimento.getDescricao()); //Setando a descrição do atendimento
        txtAnamnese.setText(atendimento.getAnamnese());
        txtDiagnostico.setText(atendimento.getDiagnostico());
        
        // Listando as vacinas na listView
        for(Vacina item : listaVacinas){
            listaVacinasSelecionadas.add(item);
        }
        ObservableList<Vacina> listaObsVacSel = FXCollections.observableArrayList(listaVacinasSelecionadas);
        listViewVacinas.setItems(listaObsVacSel);

        ObservableList<ExameRealizado> listaObsExamSel = FXCollections.observableArrayList(atendimento.getListaExames());
        listViewExames.setItems(listaObsExamSel);
        listaExamesSelecionados = atendimento.getListaExames();

        ObservableList<ServicoRealizado> listaObsServSel = FXCollections.observableArrayList(atendimento.getListaServico());
        listViewServicos.setItems(listaObsServSel);
        listaServicosSelecionados = atendimento.getListaServico();

//        selecionarExamesRealizadosListView();
//        selecionarServicosRealizadosListView();
    }

    public void setPetDoAtendimento(Pet pet) {
        scmbTutor.setValue(pet.getTutorPet());
        scmbPet.setValue(pet);
        scmbPet.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MascarasFX.mascaraData(dpDataAtendimento);
        MascarasFX.mascaraData(dpDtVacina);
        MascarasFX.mascaraData(dpDtReforco);
        MascarasFX.mascaraHorario(txtHorarioAtendimento);
        MascarasFX.mascaraNumero(txtValorTotal);
        MascarasFX.mascaraNumero(txtValorExame);
        MascarasFX.mascaraNumero(txtValorServico);
        MascarasFX.mascaraNumero(txtValorVacina);
        
        //Tooltips
        Tooltip dicaAdicionarVeterinario = new Tooltip("Cadastrar novo veterinário");
        dicaAdicionarVeterinario.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarVeterinario.setTooltip(dicaAdicionarVeterinario);
        
        Tooltip dicaAdicionarTutor = new Tooltip("Cadastrar novo tutor");
        dicaAdicionarTutor.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarTutor.setTooltip(dicaAdicionarTutor);
        
        Tooltip dicaAdicionarPet = new Tooltip("Cadastrar novo pet");
        dicaAdicionarPet.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarPet.setTooltip(dicaAdicionarPet);
        
        Tooltip dicaAdicionarClinica = new Tooltip("Cadastrar nova clínica");
        dicaAdicionarClinica.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarClinica.setTooltip(dicaAdicionarClinica);
        
        Tooltip dicaAtualizarHorario = new Tooltip("Atualiza para o horário atual");
        dicaAtualizarHorario.setShowDelay(javafx.util.Duration.ZERO);
        btnAtualizarHorario.setTooltip(dicaAtualizarHorario);
        
        Tooltip dicaAdicionarExame = new Tooltip("Cadastrar novo exame");
        dicaAdicionarExame.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirExame.setTooltip(dicaAdicionarExame);
        
        Tooltip dicaAdicionarServico = new Tooltip("Cadastrar novo serviço");
        dicaAdicionarServico.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirServico.setTooltip(dicaAdicionarServico);
        
        Tooltip dicaAdicionarTipoVacina = new Tooltip("Cadastrar nova categoria de vacina");
        dicaAdicionarTipoVacina.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirTipoVacina.setTooltip(dicaAdicionarTipoVacina);
        
        Tooltip dicaAdicionarVacina = new Tooltip("Cadastrar nova vacina");
        dicaAdicionarVacina.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirNomeVacina.setTooltip(dicaAdicionarVacina);
        
        Tooltip dicaCadastrarExame = new Tooltip("Insere o exame selecionado na lista de exames realizados no atendimento");
        dicaCadastrarExame.setShowDelay(javafx.util.Duration.millis(500));
        btnCadastrarExame.setTooltip(dicaCadastrarExame);
        
        Tooltip dicaCadastrarServico = new Tooltip("Insere o serviço selecionado na lista de serviços realizados no atendimento");
        dicaCadastrarServico.setShowDelay(javafx.util.Duration.millis(500));
        btnCadastrarServico.setTooltip(dicaCadastrarServico);
        
        Tooltip dicaCadastrarVacina = new Tooltip("Insere a vacina selecionada na lista de vacinas aplicadas ou programadas no atendimento");
        dicaCadastrarVacina.setShowDelay(javafx.util.Duration.millis(500));
        btnCadastrarVacina.setTooltip(dicaCadastrarVacina);

        // Define para onde vai o cursor ao se pressionar TAB nas caixas de texto----------------------------------
        txtHorarioAtendimento.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtAnamnese.requestFocus();
            }
        });
        txtAnamnese.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtObservacao.requestFocus();
            }
        });
        txtObservacao.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtDiagnostico.requestFocus();
            }
        });
        txtDiagnostico.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtValorTotal.requestFocus();
            }
        });
        txtValorTotal.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB || keyEvent.getCode() == KeyCode.ENTER) {
                btnSalvar.requestFocus();
            }
        });
        txtObservacaoVacina.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                btnCadastrarVacina.requestFocus();
            }
        });
        
        // EXAMES
        txtObservacaoExame.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtValorExame.requestFocus();
            }
        });
        txtValorExame.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtResultadoExame.requestFocus();
            }
        });
        txtResultadoExame.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                btnCadastrarExame.requestFocus();
            }
        });
        
        // SERVIÇOS
        txtObservacaoServico.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtValorServico.requestFocus();
            }
        });
        txtValorServico.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                btnCadastrarServico.requestFocus();
            }
        });
        //---------------------------------------------------------------------------------------------------------

        btnInserirExame.setOnAction((t) -> {
            inserirExame(btnLimpar.getScene().getWindow());
        });
        
        btnInserirServico.setOnAction((t) -> {
            inserirServico(btnLimpar.getScene().getWindow());
        });
        
        btnAdicionarTutor.setOnAction((t) -> {
            Tutor novoTutor = cadastrarTutor(btnLimpar.getScene().getWindow());
            listarTutores();
            scmbTutor.setValue(novoTutor);
        });
        
        btnAdicionarClinica.setOnAction((t) -> {
            cadastrarClinica(btnLimpar.getScene().getWindow());
            listarClinicas();
        });
        
        btnAdicionarPet.setOnAction((t) -> {
            if(scmbTutor.getValue() != null){
                Pet novoPet = new MenuPrincipal().cadastrarPetDoTutor(scmbTutor.getValue(), btnLimpar.getScene().getWindow());
                listarPets();
                scmbPet.setValue(novoPet);
            }else{
                new MenuPrincipal().cadastrarPet(btnLimpar.getScene().getWindow());
            }
        });
        
        btnAdicionarVeterinario.setOnAction((t) -> {
            cadastrarVeterinario(btnLimpar.getScene().getWindow());
            listarVeterinarios();
        });
        
        btnAtualizarHorario.setOnAction((t) -> Utils.atualizarHorario(txtHorarioAtendimento));
        
        btnPrescricao.setOnAction((t) -> {
            cadastrarPrescricaoDoAtendimento(atendimento, btnLimpar.getScene().getWindow());
        });
        
        btnLimpar.setOnAction((t) -> {
            limpaCamposAtendimento();
        });

        btnCancelar.setOnAction((t) -> {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        });

        btnExcluir.setOnAction((t) -> {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Confirmação");
            al.setContentText("O atendimento de " + atendimento.getPet().getNomePet() + " será excluído! Tem certeza?");
            if (al.showAndWait().get() == ButtonType.OK) {
                if (new AtendimentoService().excluir(atendimento)) {
                    Alert mens = new Alert(Alert.AlertType.INFORMATION);
                    mens.initOwner(btnSalvar.getScene().getWindow());
                    mens.setTitle("Excluído");
                    mens.setContentText("Registro excluído com sucesso!");
                    mens.showAndWait();
                    ((Stage) btnSalvar.getScene().getWindow()).close();
                }
            }
        });

        btnSalvar.setDefaultButton(true);

        btnSalvar.setOnAction((t) -> {
            salvarAtendimento();
        });
        
        btnSalvarEPrescricao.setOnAction((t) -> {
            salvarAtendimento();
            cadastrarPrescricaoDoAtendimento(atendimento, btnLimpar.getScene().getWindow());
        });
        
        scmbClinica.setButtonCell(new ListCell<Clinica>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Clinica clinica, boolean empty) {
                super.updateItem(clinica, empty);
                if (empty || clinica == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(clinica.getNomeClinica());
                }
            }
        });

        listarTutores();
        listarClinicas();
        listarPets();
        listarExamesCadastrados();
        listarServicosCadastrados();

        
        scmbTutor.setOnAction((t) -> {
            listarPets();
            scmbPet.setDisable(false);
        });

        scmbClinica.setOnAction((t) -> {
            listarVeterinarios();
            scmbVeterinario.setDisable(false);
        });
        
        // SEÇÃO DE EXAMES E SERVIÇOS =================================================================================================================================================================
        
        btnCadastrarExame.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                
                float valorExameSelecionado = 0;
                if (!txtValorExame.getText().equals("")){
                    valorExameSelecionado = Float.parseFloat(txtValorExame.getText());
                }
                String observacoesExameSelecionado = txtObservacaoExame.getText().trim();
                String ResultadoExame = txtResultadoExame.getText();
                
                //Atributos obrigatórios
                Exame exame = null;
                
                if(scmbExame.getValue() != null){
                    exame = scmbExame.getValue();
                }else{
                    exc.adicionarErro("Exame", "Selecione um exame para cadastrar!");
                }

                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                ExameRealizado exameRealizado = new ExameRealizado(exame, valorExameSelecionado, observacoesExameSelecionado, ResultadoExame);
                listaExamesSelecionados.add(exameRealizado);
                
                ObservableList<ExameRealizado> listaObsExamSel = FXCollections.observableArrayList(listaExamesSelecionados);
                listViewExames.setItems(listaObsExamSel);
                
                //Resetando os campos para o estado original
                scmbExame.getSelectionModel().select(-1);
                txtValorExame.setText("");
                txtObservacaoExame.setText("");
                txtResultadoExame.setText("");

                calcularValorTotal();
            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
        });
        
        scmbExame.setOnAction((t) -> {
            if (scmbExame.getSelectionModel().getSelectedIndex() != -1){
                txtValorExame.setText(String.valueOf(scmbExame.getValue().getValorExame()));
            }
        });
        
        btnLimparExame.setOnAction((t) -> {
            listaExamesSelecionados.clear();
            listarExamesSelecionados();
            calcularValorTotal();
        });
        
        btnRemoverExame.setOnAction((t) -> {
            if (listViewExames.getSelectionModel().getSelectedIndex() >= 0) {
                listaExamesSelecionados.remove(listViewExames.getSelectionModel().getSelectedItem());
                listarExamesSelecionados();
            }
            btnRemoverExame.setVisible(false);
            calcularValorTotal();
        });
        
        
        btnCadastrarServico.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                
                float valorServicoSelecionado = 0;
                if (!txtValorServico.getText().equals("")){
                    valorServicoSelecionado = Float.parseFloat(txtValorServico.getText());
                }
                String observacoesServicoSelecionado = txtObservacaoServico.getText().trim();
                
                //Atributos obrigatórios
                Servico servico = null;
                
                if(scmbServico.getValue() != null){
                    servico = scmbServico.getValue();
                }else{
                    exc.adicionarErro("Servico", "Selecione um servico para cadastrar!");
                }

                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                int qtdServicoRealizado = spnQuantidadeServico.getValue();
                
                ServicoRealizado servicoRealizado = new ServicoRealizado(servico, valorServicoSelecionado, observacoesServicoSelecionado, qtdServicoRealizado);
                listaServicosSelecionados.add(servicoRealizado);
                
                ObservableList<ServicoRealizado> listaObsExamSel = FXCollections.observableArrayList(listaServicosSelecionados);
                listViewServicos.setItems(listaObsExamSel);
                
                //Resetando os campos para o estado original
                scmbServico.getSelectionModel().select(-1);
                txtValorServico.setText("");
                txtObservacaoServico.setText("");
                spnQuantidadeServico.getValueFactory().setValue(1);

                calcularValorTotal();
            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
        });
        
        //Configuração da formatação do spinner que seleciona a quantidade de servico------------------------------------
        SpinnerValueFactory<Integer> valueFactoryQtdServico = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30);
        valueFactoryQtdServico.setValue(1);
        spnQuantidadeServico.setValueFactory(valueFactoryQtdServico);
        spnQuantidadeServico.setOnMouseClicked((t) -> {
            double valorMultiplicado = spnQuantidadeServico.getValue() * scmbServico.getValue().getValorServico();
            txtValorServico.setText(String.valueOf(valorMultiplicado));
        });
        
        scmbServico.setOnAction((t) -> {
            if (scmbServico.getSelectionModel().getSelectedIndex() != -1){
                txtValorServico.setText(String.valueOf(scmbServico.getValue().getValorServico()));
            }
        });
        
        btnLimparServico.setOnAction((t) -> {
            listaServicosSelecionados.clear();
            listarServicosSelecionados();
            calcularValorTotal();
        });
        
        btnRemoverServico.setOnAction((t) -> {
            if (listViewServicos.getSelectionModel().getSelectedIndex() >= 0) {
                listaServicosSelecionados.remove(listViewServicos.getSelectionModel().getSelectedItem());
                listarServicosSelecionados();
            }
            btnRemoverServico.setVisible(false);
            calcularValorTotal();
        });

        // Personalizando as células do ComboBox para exibir a Tooltip
        scmbExame.setCellFactory(cb -> new ListCell<Exame>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Exame exame, boolean empty) {
                super.updateItem(exame, empty);
                if (empty || exame == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(exame.getNomeExame() + " (R$ " + Utils.imprimeValor(String.valueOf(exame.getValorExame())) + ")");
                    tooltip.setText(exame.getDescricaoExame());
                    setTooltip(tooltip);
                }
            }
        });

        // Adicionar Tooltip também para o item selecionado no ComboBox
        scmbExame.setButtonCell(new ListCell<Exame>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Exame exame, boolean empty) {
                super.updateItem(exame, empty);
                if (empty || exame == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(exame.getNomeExame() + " (R$ " + Utils.imprimeValor(String.valueOf(exame.getValorExame())) + ")");
                    tooltip.setText(exame.getDescricaoExame());
                    setTooltip(tooltip);
                }
            }
        });
        
        // Personalizando as células do ComboBox para exibir a Tooltip
        scmbServico.setCellFactory(cb -> new ListCell<Servico>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Servico servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(servico.getNomeServico() + " (R$ " + Utils.imprimeValor(String.valueOf(servico.getValorServico())) + ")");
                    tooltip.setText(servico.getDescricaoServico());
                    setTooltip(tooltip);
                }
            }
        });

        // Adicionar Tooltip também para o item selecionado no ComboBox
        scmbServico.setButtonCell(new ListCell<Servico>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Servico servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(servico.getNomeServico() + " (R$ " + Utils.imprimeValor(String.valueOf(servico.getValorServico())) + ")");
                    tooltip.setText(servico.getDescricaoServico());
                    setTooltip(tooltip);
                }
            }
        });
        
        //Configuração de como a lista de exames será formatada
        listViewExames.setCellFactory(lv -> new ListCell<ExameRealizado>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(ExameRealizado exame, boolean empty) {
                super.updateItem(exame, empty);
                if (empty || exame == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(exame.getExame().getNomeExame() + " (R$ " + Utils.imprimeValor(String.valueOf(exame.getValor())) + ")");
                    tooltip.setText(exame.getObservacao());
                    setTooltip(tooltip);
                }
            }
        });
        
        listViewExames.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                ExameRealizado exameRealizado = listViewExames.getSelectionModel().getSelectedItem();
                txtObservacaoExame.setText(exameRealizado.getObservacao());
                txtValorExame.setText(String.valueOf(exameRealizado.getValor()));
                txtResultadoExame.setText(exameRealizado.getResultado());
                scmbExame.setValue(exameRealizado.getExame());
            }
        });
        
        listViewExames.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnRemoverExame.setVisible(true);
        });

        btnRemoverExame.setVisible(false);
        
        //Configuração de como a lista de serviços será formatada
        listViewServicos.setCellFactory(lv -> new ListCell<ServicoRealizado>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(ServicoRealizado servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(servico.getServico().getNomeServico()+ " (R$ " + Utils.imprimeValor(String.valueOf(servico.getValor())) + ")");
                    tooltip.setText(servico.getObservacao());
                    setTooltip(tooltip);
                }
            }
        });
        
        listViewServicos.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                ServicoRealizado servicoRealizado = listViewServicos.getSelectionModel().getSelectedItem();
                txtObservacaoServico.setText(servicoRealizado.getObservacao());
                txtValorServico.setText(String.valueOf(servicoRealizado.getValor()));
                scmbServico.setValue(servicoRealizado.getServico());
                spnQuantidadeServico.getValueFactory().setValue(servicoRealizado.getQuantidade());
            }
        });
        
        listViewServicos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnRemoverServico.setVisible(true);
        });

        btnRemoverServico.setVisible(false);
        
        // SEÇÃO DE CADASTRO DE VACINAS ===============================================================================================================================================================
        //=============================================================================================================================================================================================
        btnInserirTipoVacina.setOnAction((t) -> {
            new MenuPrincipal().inserirTiposDeVacina(btnLimpar.getScene().getWindow());
            listarTiposDeVacinas();
        });
        
        btnInserirNomeVacina.setOnAction((t) -> {
            new MenuPrincipal().inserirNomesDeVacina(btnLimpar.getScene().getWindow());
        });
        
        scmbVacina.setOnAction((t) -> {
            if (scmbVacina.getSelectionModel().getSelectedIndex() != -1){
                txtValorVacina.setText(String.valueOf(scmbVacina.getValue().getValorVacina()));
            }
        });
        
        dpDataAtendimento.setOnAction((t) -> {
            if(dpDataAtendimento.getValue() != null){
                dpDtVacina.setValue(dpDataAtendimento.getValue());
            }
        });

        rbAplicada.setOnAction((t) -> {
            lblProximaDose.setVisible(true);
            rbProximaDoseSim.setVisible(true);
            rbProximaDoseNao.setVisible(true);
            rbProximaDoseNao.setSelected(false);
            rbProximaDoseSim.setSelected(false);
        });
        
        rbProgramada.setOnAction((t) -> {
            lblProximaDose.setVisible(false);
            rbProximaDoseSim.setVisible(false);
            rbProximaDoseNao.setVisible(false);
            boxDataReforco.setVisible(false);
        });

        rbProximaDoseSim.setOnAction((t) -> {
            boxDataReforco.setVisible(true);
        });

        rbProximaDoseNao.setOnAction((t) -> {
            boxDataReforco.setVisible(false);
        });
        
        //Preparando a tela para receber novo cadastro de vacina
        lblProximaDose.setVisible(false);
        rbProximaDoseSim.setVisible(false);
        rbProximaDoseNao.setVisible(false);
        boxDataReforco.setVisible(false);

        btn21Dias.setOnAction((t) -> {
            LocalDate data = dpDtVacina.getValue();
            data = data.plusDays(21);
            dpDtReforco.setValue(data);
        });
        btn30Dias.setOnAction((t) -> {
            LocalDate data = dpDtVacina.getValue();
            data = data.plusDays(30);
            dpDtReforco.setValue(data);
        });
        btn45Dias.setOnAction((t) -> {
            LocalDate data = dpDtVacina.getValue();
            data = data.plusDays(45);
            dpDtReforco.setValue(data);
        });
        btn6Meses.setOnAction((t) -> {
            LocalDate data = dpDtVacina.getValue();
            data = data.plusMonths(6);
            dpDtReforco.setValue(data);
        });
        btn1Ano.setOnAction((t) -> {
            LocalDate data = dpDtVacina.getValue();
            data = data.plusYears(1);
            dpDtReforco.setValue(data);
        });
        
        btnCadastrarVacina.setOnAction((ActionEvent t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                
                //Atributos obrigatórios
                LocalDate dtVacina = null;
                ProdutoVacina produtoVacina = null;
                boolean aplicada = false;
                
                if(scmbVacina.getValue() != null){
                    produtoVacina = scmbVacina.getValue();
                }else{
                    exc.adicionarErro("Vacina", "Selecione uma vacina para cadastrar!");
                }
                
                if(scmbCategoriaVacina.getValue() != null){
                }else{
                    exc.adicionarErro("CategoriaVacina", "Selecione uma categoria de vacina!");
                }
                
                if(dpDtVacina.getValue() == null){
                    exc.adicionarErro("DataVacina", "Selecione uma data para vacina!");
                }else if(rbAplicada.isSelected() && dpDtVacina.getValue().isAfter(LocalDate.now())){
                    exc.adicionarErro("DataFutura", "Se a vacina foi aplicada, a data deve ser passada!");
                }else if(rbProgramada.isSelected() && dpDtVacina.getValue().isBefore(LocalDate.now())){
                    exc.adicionarErro("DataPassada", "Se a vacina foi programada, a data deve ser futura!");
                }else{
                    dtVacina = dpDtVacina.getValue();
                }
                
                if(rbAplicada.isSelected() || rbProgramada.isSelected()){
                    aplicada = rbAplicada.isSelected();
                }else{
                    exc.adicionarErro("StatusVacina", "Selecione se a vacina foi aplicada ou programada!");
                }
                
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }
                
                String observacaoVacina = txtObservacaoVacina.getText();
                int doseAtual = 0;
                if (spnDoseAtual.getValue() != 0){
                    doseAtual = spnDoseAtual.getValue();
                }
                
                int dosesTotais = 0;
                if (spnDosesTotais.getValue() != 0){
                    dosesTotais = spnDosesTotais.getValue();
                }
//                if (rbAplicada.isSelected() && dpDtProximaDose.getValue().){
//                    
//                }

                float valorVacinaCadastrada = Float.parseFloat(txtValorVacina.getText());

                boolean temProximaDose = rbProximaDoseSim.isSelected();
                LocalDate dtProximaDose;
                if(temProximaDose){
                    dtProximaDose = dpDtReforco.getValue();
                }else{
                    dtProximaDose = null;
                }
                Vacina vacina;
                if (aplicada){
                    vacina = new Vacina(produtoVacina, aplicada, observacaoVacina, doseAtual, dosesTotais, temProximaDose, dtProximaDose, valorVacinaCadastrada);
                }else{
                    vacina = new Vacina(produtoVacina, dtVacina, aplicada, observacaoVacina, doseAtual, dosesTotais, temProximaDose, dtProximaDose, valorVacinaCadastrada);
                }
                
                    
                
                listaVacinasSelecionadas.add(vacina);
                
                ObservableList<Vacina> listaObsVacSel = FXCollections.observableArrayList(listaVacinasSelecionadas);
                listViewVacinas.setItems(listaObsVacSel);
                
                //Resetando os campos para o estado original --------------------------
                scmbCategoriaVacina.getSelectionModel().select(-1);
//                cmbVacina.getSelectionModel().select(-1);
                scmbVacina.setDisable(true);
                rbAplicada.setSelected(false);
                rbProgramada.setSelected(false);
                lblProximaDose.setVisible(false);
                rbProximaDoseSim.setVisible(false);
                rbProximaDoseNao.setVisible(false);
                rbProximaDoseNao.setSelected(true);
                boxDataReforco.setVisible(false);
                spnDoseAtual.getValueFactory().setValue(1);
                spnDosesTotais.getValueFactory().setValue(1);
                txtValorVacina.setText("");
                txtObservacaoVacina.setText("");
                //Testa se a data do atendimento está preenchida. Se true, coloca ela como data de vacina, se não, coloca a data atual.
                if (dpDataAtendimento.getValue() != null) {
                    dpDtVacina.setValue(dpDataAtendimento.getValue());
                } else {
                    dpDtVacina.setValue(LocalDate.now());
                }
                //----------------------------------------------------------------------
                
                calcularValorTotal();
            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
        });

        btnLimparVacina.setOnAction((t) -> {
            listaVacinasSelecionadas.clear();
            listarVacinasSelecionadas();
            calcularValorTotal();
        });
        
        btnRemoverVacina.setOnAction((t) -> {
            if(listViewVacinas.getSelectionModel().getSelectedIndex() > -1){
                listaVacinasSelecionadas.remove(listViewVacinas.getSelectionModel().getSelectedItem());
                listarVacinasSelecionadas();
            }
            btnRemoverVacina.setVisible(false);
        });
        
        scmbCategoriaVacina.setOnAction((t) -> {
            listarNomesDeVacinas();
            scmbVacina.setDisable(false);
        });
        
        //Configuração de como a lista de vacinas será formatada
        listViewVacinas.setCellFactory(param -> new ListCell<Vacina>() {
            @Override
            protected void updateItem(Vacina item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getProdutoVacina().getNomeVacina() + " (" + item.getProdutoVacina().getTipoVacina() + ") - " + item.getProdutoVacina().getLaboratorioVacina());
            }
        });
        
        listViewVacinas.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Vacina vacina = listViewVacinas.getSelectionModel().getSelectedItem();
                txtObservacaoVacina.setText(vacina.getObservacao());
                txtValorVacina.setText(String.valueOf(vacina.getValor()));
                spnDoseAtual.getValueFactory().setValue(vacina.getDoseAtual());
                spnDosesTotais.getValueFactory().setValue(vacina.getDosesTotais());
                if (vacina.isAplicada()){
                    rbAplicada.setSelected(true);
                    rbProgramada.setSelected(false);
                    rbProximaDoseSim.setVisible(true);
                    rbProximaDoseNao.setVisible(true);
                    lblProximaDose.setVisible(true);
                }else{
                    rbAplicada.setSelected(false);
                    rbProgramada.setSelected(true);
                }
                
                dpDtVacina.setValue(vacina.getDtVacina());
                if (vacina.isTemProximaDose()) {
                    rbProximaDoseSim.setSelected(true);
                    dpDtReforco.setValue(vacina.getDtProximaDose());
                    boxDataReforco.setVisible(true);
                }
                
                listarTiposDeVacinas();
                scmbCategoriaVacina.setValue(vacina.getProdutoVacina());
                listarNomesDeVacinas();
                scmbVacina.setValue(vacina.getProdutoVacina());
            }
        });

        listViewVacinas.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnRemoverVacina.setVisible(true);
        });
        btnRemoverVacina.setVisible(false);
        
        listarTiposDeVacinas();
        
        //Configuração da formatação dos spinners que selecionam as doses de vacinas------------------------------------
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        valueFactory1.setValue(1);
        valueFactory2.setValue(1);
        spnDoseAtual.setValueFactory(valueFactory1);
        spnDosesTotais.setValueFactory(valueFactory2);
        spnDoseAtual.setOnMouseClicked((t) -> {
            if(spnDoseAtual.getValue() > spnDosesTotais.getValue()){
                spnDosesTotais.getValueFactory().setValue(spnDoseAtual.getValue());
            }
        });
        //--------------------------------------------------------------------------------------------------------------
                
        // FIM DO CADASTRO DE VACINAS ===============================================================================================================================================================
    }

    public void ajustarTela() {

        if (atendimento == null) {
            btnExcluir.setVisible(false);
            dpDataAtendimento.setValue(LocalDate.now());
            lblLabelProtocolo.setText("");
            dpDtVacina.setValue(LocalDate.now());
            btnPrescricao.setVisible(false);
            if(scmbPet.getValue() == null){
                scmbPet.setDisable(true);
            }else{
                scmbPet.setDisable(false);
            }
            Utils.atualizarHorario(txtHorarioAtendimento);
        } else {
            if (dpDataAtendimento.getValue() != null) {
                dpDtVacina.setValue(dpDataAtendimento.getValue());
            } else {
                dpDtVacina.setValue(LocalDate.now());
            }
            btnExcluir.setVisible(true);
            scmbPet.setDisable(false);
            btnPrescricao.setVisible(true);
        }
        
        //Ajusta as configurações para modo clínica ou instituição
        if (TelaPreferenciasController.preferencias.get(1) == 1){
            scmbClinica.setDisable(true);
            btnAdicionarClinica.setDisable(true);
            scmbClinica.setValue(clinicaPrincipal);
            scmbVeterinario.setDisable(false);
            listarVeterinarios();
        }else{
            scmbClinica.setDisable(false);
            btnAdicionarClinica.setDisable(false);
        }
        
    }

    private void limpaCamposAtendimento() {
        dpDataAtendimento.setValue(null);
        scmbClinica.setValue(null);
        scmbPet.setValue(null);
        scmbTutor.setValue(null);
        txtHorarioAtendimento.setText("");
        txtValorTotal.setText("");
        txtObservacao.setText("");
    }

    private void listarTutores() {
        listaTutores = new TutorService().getAll(-1, "");
        ObservableList<Tutor> listaObsTutores = FXCollections.observableArrayList(listaTutores);
        scmbTutor.setItems(listaObsTutores);
        
        scmbTutor.setCellFactory(param -> new ListCell<Tutor>() {
            @Override
            protected void updateItem(Tutor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome() + " - " + Utils.imprimeCPFouCNPJ(item.getCpf()));
            }
        });

        scmbTutor.setButtonCell(new ListCell<Tutor>() {
            @Override
            protected void updateItem(Tutor item, boolean empty) {
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("Selecione um tutor"); // Texto padrão quando vazio
                } else {
                    setText(item.getNome() + " - " + Utils.imprimeCPFouCNPJ(item.getCpf()));
                }
            }
        });
    }

    private void listarVeterinarios() {
        if (TelaPreferenciasController.preferencias.get(1) == 1) {
            listaVeterinarios = new ClinicaService().getVeterinariosDaClinica(clinicaPrincipal.getIdClinica(), clinicaPrincipal.getVeterinarioClinica().getId());
            ObservableList<Veterinario> listaObsVeterinario = FXCollections.observableArrayList(listaVeterinarios);
            scmbVeterinario.setItems(listaObsVeterinario);
        } else {
            if (scmbClinica.getSelectionModel().getSelectedIndex() != -1) {
//            listaPets = new PetService().getAll(13, String.valueOf(cmbTutor.getValue().getIdTutor()));
//            listaVeterinarios = new VeterinarioService().getAll(13, String.valueOf(cmbClinica.getValue().getIdClinica()));
                listaVeterinarios = new ClinicaService().getVeterinariosDaClinica(scmbClinica.getValue().getIdClinica(), scmbClinica.getValue().getVeterinarioClinica().getId());
            }
            ObservableList<Veterinario> listaObsVeterinario = FXCollections.observableArrayList(listaVeterinarios);
            scmbVeterinario.setItems(listaObsVeterinario);
        }
    }

    private void listarClinicas() {
        ObservableList<Clinica> listaObsClinicas = FXCollections.observableArrayList(listaClinicas);
        scmbClinica.setItems(listaObsClinicas);
//        cmbClinica.getItems().addAll(listaClinicas);
        scmbClinica.setCellFactory(param -> new ListCell<Clinica>() {
            @Override
            protected void updateItem(Clinica item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeClinica() + " - " + item.getMunicipioClinica()); // Substitua "getNome" pelo atributo desejado
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

    private void listarPets() {
        if (scmbTutor.getValue() != null) {
//            listaPets = new PetService().getAll(13, String.valueOf(cmbTutor.getValue().getIdTutor()));
            listaPets = new PetService().getAll(13, scmbTutor.getValue().getIdTutor() + "");
        }
        ObservableList<Pet> listaObsPet = FXCollections.observableArrayList(listaPets);
        scmbPet.setItems(listaObsPet);

        scmbPet.setCellFactory(param -> new ListCell<Pet>() {
            @Override
            protected void updateItem(Pet item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomePet() + " - " + item.getRaca().getEspecie().getNome());
            }
        });

        scmbPet.setButtonCell(new ListCell<Pet>() {
            @Override
            protected void updateItem(Pet item, boolean empty) {
                super.updateItem(item, empty);

                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("Selecione um pet"); // Texto padrão quando vazio
                } else {
                    setText(item.getNomePet() + " - " + item.getRaca().getEspecie().getNome());
                }
            }
        });
    }

    private void calcularValorTotal(){
        float valor = 0.0f;
        for (ExameRealizado exame : listaExamesSelecionados){
            valor += exame.getValor();
        }
        for (ServicoRealizado servico : listaServicosSelecionados){
            valor += servico.getValor();
        }
        for (Vacina vacina : listaVacinasSelecionadas){
            valor += vacina.getValor();
        }
            txtValorTotal.setText(String.valueOf(valor));
    }
    
    private void salvarAtendimento(){
        //Testa se é uma inserção ou edição
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                if (atendimento == null) {
                    atendimento = new Atendimento();
                }

                //Testa se os atributos obrigatorios foram preenchidos
                if (scmbVeterinario.getValue() != null) {
                    Veterinario veterinarioAtendimento = scmbVeterinario.getValue();
                    atendimento.setVeterinario(veterinarioAtendimento);
                } else {
                    exc.adicionarErro("Veterinario", "Selecione o Veterinário responsável pelo atendimento!");
                }

                if (scmbTutor.getValue() != null) {
                } else {
                    exc.adicionarErro("Tutor", "Selecione o tutor responsável pelo atendimento!");
                }

                if (scmbPet.getValue() != null) {
//                  Se estiver preenchido, então atualiza o objeto com o pet que recebeu o atendimento
                    atendimento.setPet(scmbPet.getValue());
                } else {
                    exc.adicionarErro("Pet", "Selecione o pet que recebeu o atendimento!");
                }

                if (scmbClinica.getValue() != null) {
                    Clinica clinica = scmbClinica.getSelectionModel().getSelectedItem();
                    atendimento.setClinica(clinica);
                } else {
                    exc.adicionarErro("Clinica", "Selecione o local do atendimento!");

                }

                if (dpDataAtendimento == null) {
                    exc.adicionarErro("Data", "Insira a data do atendimento!");
                } else {
                    atendimento.setDataAtendimento(dpDataAtendimento.getValue());
                }

                if (txtHorarioAtendimento.getText() == null || txtHorarioAtendimento.getText().equals("")) {
                    exc.adicionarErro("Horario", "Insira o horário do atendimento");
                } else {
                    atendimento.setHorarioAtendimento(LocalTime.parse(txtHorarioAtendimento.getText()));
                }

                if (txtValorTotal.getText() == null || txtValorTotal.getText().equals("")) {
                    exc.adicionarErro("Valor", "Insira o valor total do atendimento!");
                } else {
                    //Obtém o texto do campo e converte para float
                    String textoValor = txtValorTotal.getText();
//                    float valorTotal = Utils.formataFloat(textoValor);
                    atendimento.setValorTotal(Float.valueOf(textoValor));
                }

                atendimento.setAnamnese(txtAnamnese.getText().trim());
                atendimento.setDescricao(txtObservacao.getText().trim());
                atendimento.setDiagnostico(txtDiagnostico.getText().trim());

                // Ao final de todos os testes de campos, é necessário verificar se existem erros.
                // Se existirem, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                Pet pet = atendimento.getPet();
                
                for (Vacina item : listaVacinasSelecionadas) {
                    item.setPet(pet);
                    item.setAtendimento(atendimento);
                    if (item.isAplicada()) {
                        item.setDtVacina(atendimento.getDataAtendimento());
                    }
                }
                for (ExameRealizado item : listaExamesSelecionados) {
                    item.setPet(pet);
                    item.setAtendimento(atendimento);
                }

                for (ServicoRealizado item : listaServicosSelecionados) {
                    item.setPet(pet);
                    item.setAtendimento(atendimento);
                }
                atendimento.setListaServico(listaServicosSelecionados);
                atendimento.setListaExames(listaExamesSelecionados);
                
                if (new AtendimentoService().salvarOuAtualizar(atendimento)) {
                    
                    // Deu certo. Remover as vacinas anteriores
                    new VacinaService().excluirVacinaDoAtendimento(atendimento);
                    
                    
                    //Adicionar as vacinas novas:
                    for (Vacina item : listaVacinasSelecionadas){
                        new VacinaService().inserir(item);
                    }
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Atendimento cadastrado");
                    al.setContentText("O atendimento foi cadastrado com sucesso!");
                    al.showAndWait();
                    // Posso fechar a janela
//                    ((Stage) btnCancelar.getScene().getWindow()).close();
                    btnPrescricao.setVisible(true);
                    btnExcluir.setVisible(true);
                } else {
                    // Deu erro. O retorno do boolean veio false
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("ERRO");
                    al.setContentText("Ocorreu um erro ao inserir!");
                    al.showAndWait();
                }

            } catch (ValidacaoException e) {
                System.out.println("Erro na validação");
                setErrorMessages(e.getErrors());
            }
    }
    
    // MÉTODOS PARA VACINAS  ============================================================================================================================================================
    private void listarTiposDeVacinas() {                                                                                                                                               //
        this.listaTiposVacina = new ProdutoVacinaService().getTiposVacinas();                                                                                                                //
        ObservableList<ProdutoVacina> listaObsTipoVacina = FXCollections.observableArrayList(listaTiposVacina);                                                                         //
        scmbCategoriaVacina.setItems(listaObsTipoVacina);                                                                                                                                //
                                                                                                                                                                                        //
        scmbCategoriaVacina.setCellFactory(param -> new ListCell<ProdutoVacina>() {                                                                                                      //
            @Override
            protected void updateItem(ProdutoVacina item, boolean empty) {
                super.updateItem(item, empty);                                                                                                                                          //
                setText(empty || item == null ? null : item.getTipoVacina());
            }
        });                                                                                                                                                                             //

        scmbCategoriaVacina.setButtonCell(new ListCell<ProdutoVacina>() {
            @Override
            protected void updateItem(ProdutoVacina item, boolean empty) {                                                                                                              //
                super.updateItem(item, empty);

                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {                                                                                                                                            //
                    setText("Selecione"); // Texto padrão quando vazio
                } else {
                    setText(item.getTipoVacina());
                }
            }
        });                                                                                                                                                                             //
    }

    private void listarNomesDeVacinas() {
        if (scmbCategoriaVacina.getValue() != null) {
            listaNomesVacina = new ProdutoVacinaService().getAll(scmbCategoriaVacina.getValue());    
            ObservableList<ProdutoVacina> listaObsNomesVacina = FXCollections.observableArrayList(listaNomesVacina);                                                                    //
            scmbVacina.setItems(listaObsNomesVacina);
        }

        scmbVacina.setCellFactory(param -> new ListCell<ProdutoVacina>() {
            @Override
            protected void updateItem(ProdutoVacina item, boolean empty) {                                                                                                              //
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeVacina());
            }
        });

        scmbVacina.setButtonCell(new ListCell<ProdutoVacina>() {
            @Override
            protected void updateItem(ProdutoVacina item, boolean empty) {                                                                                                              //
                super.updateItem(item, empty);

                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        //
                } else {
                    setText(item.getNomeVacina());
                }
            }                                                                                                                                                                           //
        });
    }

    private void listarVacinasSelecionadas() {
        ObservableList<Vacina> listaObsVacSel = FXCollections.observableArrayList(listaVacinasSelecionadas);                                                                             //
        listViewVacinas.setItems(listaObsVacSel);                                                                                                                                        //
    }                                                                                                                                                                                    //
                                                                                                                                                                                         //
        // FIM DOS MÉTODOS PARA VACINAS  ==================================================================================================================================================


    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroPet.setText(campos.contains("Pet") ? errors.get("Pet") : "");
        lblErroClinica.setText(campos.contains("Clinica") ? errors.get("Clinica") : "");
        lblErroDataAtendimento.setText(campos.contains("Data") ? errors.get("Data") : "");
        lblErroHorario.setText(campos.contains("Horario") ? errors.get("Horario") : "");
        lblErroValorTotal.setText(campos.contains("Valor") ? errors.get("Valor") : "");
        lblErroTutor.setText(campos.contains("Tutor") ? errors.get("Tutor") : "");
        lblErroVeterinario.setText(campos.contains("Veterinario") ? errors.get("Veterinario") : "");
        lblErroTipoVacina.setText(campos.contains("CategoriaVacina") ? errors.get("CategoriaVacina") : "");
        lblErroVacina.setText(campos.contains("Vacina") ? errors.get("Vacina") : "");
        lblErroDtVacina.setText(campos.contains("DataVacina") ? errors.get("DataVacina") : "");
        lblErroDtVacinaFutura.setText(campos.contains("DataFutura") ? errors.get("DataFutura") : "");
        lblErroDtVacinaPassada.setText(campos.contains("DataPassada") ? errors.get("DataPassada") : "");
        lblErroStatusVacina.setText(campos.contains("StatusVacina") ? errors.get("StatusVacina") : "");
        lblErroExame.setText(campos.contains("Exame") ? errors.get("Exame") : "");
        lblErroServico.setText(campos.contains("Servico") ? errors.get("Servico") : "");
    }
    
    
    // MÉTODOS PARA EXAMES E SERVIÇOS ==================================================================================================================================================

    private void listarExamesCadastrados() {
        listaExames = new ExameService().getAll();
        ObservableList<Exame> listaObsExames = FXCollections.observableArrayList(listaExames);                                                                        
        scmbExame.setItems(listaObsExames);

        scmbExame.setCellFactory(param -> new ListCell<Exame>() {
            @Override
            protected void updateItem(Exame item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeExame());
            }
        });

        scmbExame.setButtonCell(new ListCell<Exame>() {
            @Override
            protected void updateItem(Exame item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getNomeExame());
                }
            }                                                                                                                                                                           
        });
    }
    
    private void listarServicosCadastrados() {
        listaServicos = new ServicoService().getAll();
        ObservableList<Servico> listaObsServicos = FXCollections.observableArrayList(listaServicos);                                                                        
        scmbServico.setItems(listaObsServicos);

        scmbServico.setCellFactory(param -> new ListCell<Servico>() {
            @Override
            protected void updateItem(Servico item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeServico());
            }
        });

        scmbServico.setButtonCell(new ListCell<Servico>() {
            @Override
            protected void updateItem(Servico item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getNomeServico());
                }
            }                                                                                                                                                                           
        });
    }
    
    private void listarExamesSelecionados() {
        ObservableList<ExameRealizado> listaObsExamSel = FXCollections.observableArrayList(listaExamesSelecionados);                                                                             
        listViewExames.setItems(listaObsExamSel);                                                                                                                                        
    }    
    
    private void listarServicosSelecionados() {
        ObservableList<ServicoRealizado> listaObsServSel = FXCollections.observableArrayList(listaServicosSelecionados);                                                                             
        listViewServicos.setItems(listaObsServSel);                                                                                                                                        
    }
    
}
