package application;

import java.net.URL;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.classes.Pet;
import model.classes.SalaEspera;
import model.classes.Tutor;
import model.exceptions.ValidacaoException;
import model.services.PetService;
import model.services.SalaEsperaService;
import model.services.TutorService;
import view.utils.MascarasFX;
import view.utils.Som;
import view.utils.Utils;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaSalaEsperaController implements Initializable {

    @FXML    private Button btnAlterar;
    @FXML    private Button btnAtender;
    @FXML    private Button btnAdicionarTutor;
    @FXML    private Button btnAdicionarPet;
    @FXML    private Button btnAtualizarHorario;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovaJanela;
    @FXML    private Button btnSalvar;
    @FXML    private CheckBox ckbAgendado;
    @FXML    private CheckBox ckbUrgencia;
    @FXML    private Label lblErroHorario;
    @FXML    private Label lblErroPet;
    @FXML    private Label lblErroTutor;
    @FXML    private Label lblHorarioAgendado;
    @FXML    private SearchableComboBox<Pet> scmbPet;
    @FXML    private SearchableComboBox<Tutor> scmbTutor;
    @FXML    private TableColumn<SalaEspera, Pet> tableColumnPaciente;
    @FXML    private TableColumn<SalaEspera, Tutor> tableColumnTutor;
    @FXML    private TableColumn<SalaEspera, LocalTime> tableColumnHorarioChegada;
    @FXML    private TableColumn<SalaEspera, Boolean> tableColumnAgendado;
    @FXML    private TableColumn<SalaEspera, LocalTime> tableColumnHorarioAgendado;
    @FXML    private TableColumn<SalaEspera, Boolean> tableColumnUrgencia;
    @FXML    private TableColumn<SalaEspera, Duration> tableColumnTempoEspera;
    @FXML    private TableView<SalaEspera> tblSalaEspera;
    @FXML    private TextField txtHorarioChegada;
    @FXML    private TextField txtHorarioAgendado;

    List<SalaEspera> listaPacientes = new SalaEsperaService().getAll();
    private ScheduledExecutorService scheduler;
    SalaEspera paciente;
    private List<Tutor> listaTutores = new TutorService().getAll(-1, "");
    private List<Pet> listaPets;
    int contador = 3;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        iniciarAtualizacao();
        
        MascarasFX.mascaraHorario(txtHorarioAgendado);
        MascarasFX.mascaraHorario(txtHorarioChegada);
        
        //Tooltips
        Tooltip dicaAdicionarTutor = new Tooltip("Cadastrar novo tutor");
        dicaAdicionarTutor.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarTutor.setTooltip(dicaAdicionarTutor);
        
        Tooltip dicaAdicionarPet = new Tooltip("Cadastrar novo pet");
        dicaAdicionarPet.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarPet.setTooltip(dicaAdicionarPet);
        
        Tooltip dicaAtualizarHorario = new Tooltip("Atualiza para o horário atual");
        dicaAtualizarHorario.setShowDelay(javafx.util.Duration.ZERO);
        btnAtualizarHorario.setTooltip(dicaAtualizarHorario);
        
        Tooltip dicaAdicionarNaLista = new Tooltip("Adiciona o pet na sala de espera");
        dicaAdicionarNaLista.setShowDelay(javafx.util.Duration.ZERO);
        btnSalvar.setTooltip(dicaAdicionarNaLista);
        
        //Configurando o cabeçalho da tableView para mostrar duas linhas
        Label lblTblHorarioChegada = new Label("Horário de\n  chegada");
        lblTblHorarioChegada.setWrapText(true);
        lblTblHorarioChegada.setAlignment(Pos.CENTER);
        tableColumnHorarioChegada.setText(null);
        tableColumnHorarioChegada.setGraphic(lblTblHorarioChegada);
        
        Label lblTblHorarioAgendado = new Label("  Horário\nagendado");
        lblTblHorarioAgendado.setWrapText(true);
        lblTblHorarioAgendado.setAlignment(Pos.CENTER);
        tableColumnHorarioAgendado.setText(null);
        tableColumnHorarioAgendado.setGraphic(lblTblHorarioAgendado);
        
        Label lblTblTempoEspera = new Label("Tempo de\n   espera");
        lblTblTempoEspera.setWrapText(true);
        lblTblTempoEspera.setAlignment(Pos.CENTER);
        tableColumnTempoEspera.setText(null);
        tableColumnTempoEspera.setGraphic(lblTblTempoEspera);
        //--------------------------------------------
        
        tableColumnPaciente.setCellValueFactory(new PropertyValueFactory<>("pet"));
        tableColumnTutor.setCellValueFactory(new PropertyValueFactory<>("tutor"));
        tableColumnHorarioChegada.setCellValueFactory(new PropertyValueFactory<>("horarioChegada"));
        tableColumnAgendado.setCellValueFactory(new PropertyValueFactory<>("agendado"));
        tableColumnHorarioAgendado.setCellValueFactory(new PropertyValueFactory<>("horarioAgendado"));
        tableColumnUrgencia.setCellValueFactory(new PropertyValueFactory<>("urgencia"));
        tableColumnTempoEspera.setCellValueFactory(new PropertyValueFactory<>("tempoEspera"));
        
        
        //Formata a coluna de tempo de espera
        tableColumnTempoEspera.setCellFactory(column -> new TableCell<SalaEspera, Duration>() {
            @Override
            protected void updateItem(Duration duration, boolean empty) {
                super.updateItem(duration, empty);
                if (empty || duration == null) {
                    setText(null);
                } else {
                    long totalMinutes = (long) duration.toMinutes();
                    long horas = totalMinutes / 60;
                    long minutos = totalMinutes % 60;
                    setText(String.format("%02d:%02d", horas, minutos));
                }
            }
        });

        tblSalaEspera.setRowFactory(
                new Callback<TableView<SalaEspera>, TableRow<SalaEspera>>() {
                    
            @Override
            public TableRow<SalaEspera> call(TableView<SalaEspera> tableView) {
                final TableRow<SalaEspera> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();

                MenuItem editItem = new MenuItem("Editar paciente");
                editItem.setOnAction((t) -> {
                    paciente = row.getItem();
                    carregarCampos(paciente);
                });
                
                MenuItem verPet = new MenuItem("Ver Pet");
                verPet.setOnAction((t) -> {
                    paciente = row.getItem();
                    new MenuPrincipal().editarPet(paciente.getPet(), btnLimpar.getScene().getWindow());
                    paciente = null;
                });
                
                MenuItem verTutor = new MenuItem("Ver Tutor");
                verTutor.setOnAction((t) -> {
                    paciente = row.getItem();
                    new MenuPrincipal().editarTutor(paciente.getPet().getTutorPet(), btnLimpar.getScene().getWindow());
                    paciente = null;
                });
                
                MenuItem verAtendimento = new MenuItem("Ver Atendimentos");
                verAtendimento.setOnAction((t) -> {
                    paciente = row.getItem();
                    new MenuPrincipal().verAtendimentoDoPet(paciente.getPet());
                    paciente = null;
                });

                MenuItem removeItem = new MenuItem("Excluir");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText(row.getItem().getPet().getNomePet() + " será removido da sala de espera! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new SalaEsperaService().excluir(row.getItem())) {
                                Alert mens = new Alert(Alert.AlertType.INFORMATION);
                                mens.initOwner(btnLimpar.getScene().getWindow());
                                mens.setTitle("Removido");
                                mens.setContentText("Paciente removido da sala de espera!");
                                mens.showAndWait();
                                atualizaTabela();
                            }
                        }
                    }
                });
                rowMenu.getItems().addAll(editItem, verPet, verTutor, verAtendimento, removeItem);

                // only display context menu for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(rowMenu));
                return row;
            }
        });

        tblSalaEspera.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && tblSalaEspera.getSelectionModel().getSelectedItem() != null) {
                paciente = tblSalaEspera.getSelectionModel().getSelectedItem();
                carregarCampos(paciente);
                scmbPet.setDisable(false);
                btnSalvar.setVisible(false);
                btnSalvar.setManaged(false);
                btnAlterar.setVisible(true);
                btnAlterar.setManaged(true);
            }
        });
        
        btnAlterar.setOnAction((t) -> {
            salvarPaciente();
            btnSalvar.setVisible(true);
            btnSalvar.setManaged(true);
            btnAlterar.setVisible(false);
            btnAlterar.setManaged(false);
            paciente = null;
        });
        
        // Configurando para permitir a seleção de apenas uma linha por vez
        tblSalaEspera.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Adicionando um listener para executar ação ao selecionar uma linha
        tblSalaEspera.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SalaEspera> obs, SalaEspera oldSelection, SalaEspera newSelection) -> {
            if (newSelection != null) {
                btnAtender.setVisible(true);
                btnExcluir.setVisible(true);
            }else{
                btnAtender.setVisible(false);
                btnExcluir.setVisible(false);
            }
        });

        tblSalaEspera.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                paciente = tblSalaEspera.getSelectionModel().getSelectedItem();
                al.setContentText("O paciente " + paciente.getPet().getNomePet() + " será removido da sala de espera! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    if (new SalaEsperaService().excluir(paciente)) {
                        Alert mens = new Alert(Alert.AlertType.INFORMATION);
                        mens.initOwner(btnLimpar.getScene().getWindow());
                        mens.setTitle("Excluído");
                        mens.setContentText("Registro excluído com sucesso!");
                        mens.showAndWait();
                        atualizaTabela();
                    }
                }
            }
        });
        
        btnNovaJanela.setOnAction((t) -> {
            new MenuPrincipal().cadastrarSalaEsperaNovaJanela(btnLimpar.getScene().getWindow());
        });
        
        btnAtualizarHorario.setOnAction((t) -> {
            String horas = LocalTime.now().getHour() + "";
            String minutos = LocalTime.now().getMinute() + "";
            
            if (horas.length() == 1){
                horas = "0" + horas;
            }
            
            if (minutos.length() == 1){
                minutos = "0" + minutos;
            }
            
            String horario = horas + ":" + minutos;
            txtHorarioChegada.setText(horario);
        });
        
        btnAdicionarPet.setOnAction((t) -> {
            if(scmbTutor.getValue() != null){
                new MenuPrincipal().cadastrarPetDoTutor(scmbTutor.getValue(), btnLimpar.getScene().getWindow());
                listarPets();
            }else{
                new MenuPrincipal().cadastrarPet(btnLimpar.getScene().getWindow());
            }
        });
        
        btnAdicionarTutor.setOnAction((t) -> {
            new MenuPrincipal().cadastrarTutor(btnLimpar.getScene().getWindow());
            listarTutores();
        });
        
        btnAtender.setOnAction((t) -> {
            paciente = tblSalaEspera.getSelectionModel().getSelectedItem();
            Pet pet = paciente.getPet();
            new MenuPrincipal().cadastrarAtendimentoDoPet(pet, btnLimpar.getScene().getWindow());
            new SalaEsperaService().excluir(paciente);
            paciente = null;
            atualizaTabela();
        });
        
        btnExcluir.setOnAction((t) -> {
            paciente = tblSalaEspera.getSelectionModel().getSelectedItem();
            new SalaEsperaService().excluir(paciente);
            paciente = null;
            atualizaTabela();
        });


        btnSalvar.setOnAction((t) -> {
            salvarPaciente();
        });

        btnLimpar.setOnAction((t) -> {
            limpaCampos();
        });
        
        scmbPet.setDisable(true);
        
        scmbTutor.setOnAction((t) -> {
            listarPets();
            scmbPet.setDisable(false);
        });
        
        ckbAgendado.selectedProperty().addListener((o, oldValue, newValue) -> {
            if(ckbAgendado.isSelected()){
                lblHorarioAgendado.setVisible(true);
                txtHorarioAgendado.setVisible(true);
            }else{
                lblHorarioAgendado.setVisible(false);
                txtHorarioAgendado.setVisible(false);
            }
        });
        
        btnAtender.setVisible(false);
        btnExcluir.setVisible(false);
        
        listarTutores();
        atualizaTabela();
        addCloseListenerQuandoJanelaEstiverPronta();
        
        btnAlterar.setVisible(false);
        btnAlterar.setManaged(false);
        
    }

    private void atualizaTabela() {
        
        List<SalaEspera> listaPacientesAnterior = listaPacientes;
        int numeroPacientesAnterior = listaPacientes.size();
        // Buscar os dados no banco de dados na tabela pet
        listaPacientes = new SalaEsperaService().getAll();
        // ObservableList
        ObservableList<SalaEspera> listaObs = FXCollections.observableArrayList(listaPacientes);
        
        int numeroPacientesAtual = listaPacientes.size();
        
        tblSalaEspera.getSortOrder().clear(); // Limpa outras ordenações
        tblSalaEspera.getSortOrder().add(tableColumnHorarioChegada); // Adiciona a coluna à ordem de classificação
        tableColumnHorarioChegada.setSortType(TableColumn.SortType.ASCENDING); // Define a direção (ASCENDING ou DESCENDING)
        tblSalaEspera.sort(); // Aplica a ordenação
        
        int pacienteSelecionado = tblSalaEspera.getSelectionModel().getSelectedIndex();
        
        //Vinculando a lista observável com a TableView
        Platform.runLater(() -> tblSalaEspera.setItems(listaObs));
        
        if (listaPacientesAnterior.hashCode() != listaPacientes.hashCode() && numeroPacientesAnterior <= numeroPacientesAtual) {
            Som.tocarSom("src/main/resources/sounds/pacienteAdicionadoNaSala.wav");
        }
        
    }
    
    private void limpaCampos(){
        scmbTutor.getSelectionModel().select(null);
        scmbPet.getSelectionModel().select(null);
        txtHorarioAgendado.setText("");
        txtHorarioChegada.setText("");
        ckbAgendado.setSelected(false);
        ckbUrgencia.setSelected(false);
        scmbPet.setDisable(true);
        paciente = null;
    }

    private void carregarCampos(SalaEspera paciente) {
        scmbTutor.setValue(paciente.getPet().getTutorPet());
        scmbPet.setValue(paciente.getPet());
        txtHorarioChegada.setText(paciente.getHorarioChegada().toString());
        ckbAgendado.setSelected(paciente.isAgendado());
        ckbUrgencia.setSelected(paciente.isUrgencia());
        if (paciente.isAgendado()) {
            lblHorarioAgendado.setVisible(true);
            txtHorarioAgendado.setVisible(true);
            txtHorarioAgendado.setText(paciente.getHorarioAgendado().toString());
        }
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
    
    public void ajustarTela(){
        btnNovaJanela.setVisible(false);
    }
    
    private void listarTutores() {
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
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroPet.setText(campos.contains("pet") ? errors.get("pet") : "");
        lblErroTutor.setText(campos.contains("tutor") ? errors.get("tutor") : "");
        lblErroHorario.setText(campos.contains("horario") ? errors.get("horario") : "");
    }
    
    public void iniciarAtualizacao() {
        // Criando um agendador de tarefas
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Executa o método a cada 30 segundos
        scheduler.scheduleAtFixedRate(() -> {
            Platform.runLater(this::atualizaTabela); // Atualiza a UI na thread principal
        }, 0, 10, TimeUnit.SECONDS);
    }
    
    public void pararAtualizacao() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
    
    private void addCloseListenerQuandoJanelaEstiverPronta() {
        tblSalaEspera.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> {
                    Stage stage = (Stage) newScene.getWindow();
                    if (stage != null) {
                        stage.setOnCloseRequest(event ->  pararAtualizacao() );
                    }
                });
            }
        });
    }

    private void salvarPaciente() {
        try {
            ValidacaoException exc = new ValidacaoException("Erro validando!!");
            if (paciente == null) {
                paciente = new SalaEspera();
            }

            paciente.setAgendado(ckbAgendado.isSelected());
            if (!txtHorarioAgendado.getText().equals("")) {
                paciente.setHorarioAgendado(LocalTime.parse(txtHorarioAgendado.getText()));
            }
            paciente.setUrgencia(ckbUrgencia.isSelected());

            //Testa se os atributos obrigatorios foram preenchidos
            if (scmbTutor.getValue() != null) {
                Tutor tutorEspera = scmbTutor.getValue();
            } else {
                exc.adicionarErro("tutor", "Selecione o tutor responsável pelo atendimento!");
            }

            if (scmbPet.getValue() != null) {
                Pet petEspera = scmbPet.getValue();
                paciente.setPet(petEspera);
            } else {
                exc.adicionarErro("pet", "Selecione o pet a ser inserido!");
            }

            if (!txtHorarioChegada.getText().equals("")) {
                paciente.setHorarioChegada(LocalTime.parse(txtHorarioChegada.getText()));
            } else {
                exc.adicionarErro("horario", "Informe o horário de chegada do paciente!");
            }

            // Ao final de todos os testes de campos, é necessário verificar se existem erros.
            // Se existirem, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
            if (!exc.getErrors().isEmpty()) {
                throw exc;
            }

            if (new SalaEsperaService().salvarOuAtualizar(paciente)) {
                // Deu certo.
                paciente = null;
                limpaCampos();
                atualizaTabela();
                btnAtender.setVisible(false);
                btnExcluir.setVisible(false);
                scmbPet.setDisable(true);
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
    
}
