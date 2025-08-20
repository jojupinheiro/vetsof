package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
import model.classes.utilitario.ValorPadrao;
import model.exceptions.ValidacaoException;
import model.services.ClinicaService;
import model.services.InternacaoService;
import model.services.PetService;
import model.services.TutorService;
import org.controlsfx.control.CheckComboBox;
import view.utils.Utils;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroInternadoController implements Initializable {
   
    @FXML    private AnchorPane anchorPane;
    @FXML    private Button btnAdicionarPet;
    @FXML    private Button btnAdicionarTutor;
    @FXML    private Button btnAdicionarVeterinario;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovaDiaria;
    @FXML    private Button btnPrescricao;
    @FXML    private Button btnSalvar;
    @FXML    private CheckBox ckbInternacaoAtiva;
    @FXML    private DatePicker dpDtInternacao;
    @FXML    private DatePicker dpDtAlta;
    @FXML    private Label lblErroDtInternacao;
    @FXML    private Label lblErroPet;
    @FXML    private Label lblErroTutor;
    @FXML    private Label lblErroVeterinario;
    @FXML    private SearchableComboBox<Pet> scmbPet;
    @FXML    private SearchableComboBox<Tutor> scmbTutor;
    @FXML    private SearchableComboBox<Veterinario> scmbVeterinario;
    @FXML    private TextArea txtObservacao;
    @FXML    private TextField txtValorDiaria;
    @FXML    private TextField txtValorTotal;
    @FXML    private VBox vBox;
    
    private Internado internado;
    private List<DiariaInternacao> listaDiarias;
    private int counter = 1; // Contador para as Labels
    private int index = 0; //Controla o índice da lista das diárias
    private Clinica clinicaPrincipal = new ClinicaService().getClinicaPrincipal();
    private List<Pet> listaPets = new PetService().getAll(-1, "");
    private List<Tutor> listaTutores = new TutorService().getAll(-1, "");
    private List<Veterinario> listaVeterinarios = new ArrayList<>();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ObservableList<String> listaObsSinaisClinicos = FXCollections.observableArrayList(
            "Anisocoria", "Anúria", "Ataxia", "Cianose", "Claudicação", "Diarreia", "Dispnéia", "Disúria", 
            "Dor", "Espirros", "Febre", "Hematêmese", "Hematoquezia", "Icterícia", "Inclinação de cabeça", 
            "Letargia", "Melena", "Midríase", "Miose", "Nistagmo", "Paraparezia", "Paraplegia", "Polidipsia", 
            "Poliúria", "Propriocepção negativa", "Regurgitação", "Secreção nasal", "Secreção ocular", 
            "Síncope", "Tenesmo", "Tetraparezia", "Tetraplegia", "Tosse", "Vermes nas fezes", "Vômito");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Tooltips
        Tooltip dicaAdicionarVeterinario = new Tooltip("Cadastrar novo veterinário");
        dicaAdicionarVeterinario.setShowDelay(javafx.util.Duration.millis(500));
        btnAdicionarVeterinario.setTooltip(dicaAdicionarVeterinario);
        
        Tooltip dicaAdicionarTutor = new Tooltip("Cadastrar novo tutor");
        dicaAdicionarTutor.setShowDelay(javafx.util.Duration.millis(500));
        btnAdicionarTutor.setTooltip(dicaAdicionarTutor);
        
        Tooltip dicaAdicionarPet = new Tooltip("Cadastrar novo pet");
        dicaAdicionarPet.setShowDelay(javafx.util.Duration.millis(500));
        btnAdicionarPet.setTooltip(dicaAdicionarPet);        
        
        btnAdicionarPet.setOnAction((t) -> {
            if(scmbTutor.getValue() != null){
                new MenuPrincipal().cadastrarPetDoTutor(scmbTutor.getValue(), btnLimpar.getScene().getWindow());
                listarPets();
            }else{
                new MenuPrincipal().cadastrarPet(btnLimpar.getScene().getWindow());
            }
        });
        
        btnAdicionarVeterinario.setOnAction((t) -> {
            new MenuPrincipal().cadastrarVeterinario(btnLimpar.getScene().getWindow());
            listarVeterinarios();
        });
        
        btnAdicionarTutor.setOnAction((t) -> {
            new MenuPrincipal().cadastrarTutor(btnLimpar.getScene().getWindow());
            listarTutores();
        });
        
        btnLimpar.setOnAction((t) -> limparCampos());
        btnCancelar.setOnAction((t) -> ((Stage) btnLimpar.getScene().getWindow()).close());
        
        btnNovaDiaria.setOnAction((t) -> {
            if (validarInternado()){
                Tutor tutor = scmbTutor.getValue();
                Pet pet = scmbPet.getValue();
                Veterinario veterinario = scmbVeterinario.getValue();
                LocalDate dtInternacao = dpDtInternacao.getValue();
                LocalDate dtAlta = dpDtAlta.getValue();
                float valorDiaria = txtValorDiaria.getText().equals("") ? 0 : Utils.formataFloat(txtValorDiaria.getText());
                float valorTotal = txtValorTotal.getText().equals("") ? 0 : Utils.formataFloat(txtValorTotal.getText());
                String observacoes = txtObservacao.getText().trim();
                boolean internacaoAtiva = ckbInternacaoAtiva.isSelected();

                internado = new Internado(pet, veterinario, dtInternacao, dtAlta, valorDiaria, valorTotal, observacoes, internacaoAtiva);
                addHBox();
            }
            calcularValorTotal();
        });
        
        scmbPet.setOnAction((t) -> {
            if(scmbVeterinario.getValue() != null){
                btnNovaDiaria.setDisable(false);
            }
        });
        
        scmbTutor.setOnAction((t) -> {
            if(scmbVeterinario.getValue() == null || scmbPet.getValue() == null){
                btnNovaDiaria.setDisable(true);
            }else{
                btnNovaDiaria.setDisable(false);
            }
        });
        
        scmbVeterinario.setOnAction((t) -> {
            if(scmbPet.getValue() != null){
                btnNovaDiaria.setDisable(false);
            }
        });
        
        dpDtInternacao.setValue(LocalDate.now());
        ckbInternacaoAtiva.setSelected(true);
        
        scmbPet.setDisable(true);
        listarVeterinarios();
        listarTutores();
        scmbTutor.setOnAction((t) -> {
            listarPets();
            scmbPet.setDisable(false);
        });
        
        btnSalvar.setOnAction((t) -> salvar());
        
        txtValorDiaria.textProperty().addListener((t, ov, nv) -> calcularValorTotal());
        
        btnNovaDiaria.setDisable(true);
        
        txtValorDiaria.setText(Utils.imprimeValor(String.valueOf(getValorDiariaPadrao())));
        
        btnExcluir.setVisible(false);
        
        btnExcluir.setOnAction((t) -> {
            
        });
        
        btnPrescricao.setOnAction((t) -> {
            new MenuPrincipal().cadastrarPrescricaoDoInternado(internado, btnLimpar.getScene().getWindow());
        });
    }   
    
    public void setInternado(Internado internado) {
        btnExcluir.setVisible(true);
        for(DiariaInternacao item : internado.getDiaria()){
        }
        this.internado = internado;
        scmbPet.setDisable(false);
        btnNovaDiaria.setDisable(false);
        scmbTutor.setValue(internado.getPet().getTutorPet());
        scmbPet.setValue(internado.getPet());
        scmbVeterinario.setValue(internado.getVeterinario());
        ckbInternacaoAtiva.setSelected(internado.isInternacaoAtiva());
        dpDtInternacao.setValue(internado.getDtInternacao());
        dpDtAlta.setValue(internado.getDtAlta());
        txtObservacao.setText(internado.getObservacoes());
        txtValorDiaria.setText(String.valueOf(internado.getValorDiaria()));
        txtValorTotal.setText(String.valueOf(internado.getValorTotal()));

        for (int i = 0; i < internado.getNumeroDiarias(); i++) {
            addHBox();
        }
        this.listaDiarias = internado.getDiaria();

        int indiceDiaria = 0;

        for (var node : vBox.getChildren()) {
            
            if (node instanceof HBox hbox) {
                for (var innerNode : hbox.getChildren()) {
                    DiariaInternacao diaria = internado.getDiaria().get(indiceDiaria);
                    // Capturar valores do Spinner e DatePicker corretamente
                    if (innerNode instanceof VBox vBoxData) {
                        for (Node child : vBoxData.getChildren()) {
                            if (child instanceof HBox hBoxNumero && hBoxNumero.getChildren().get(1) instanceof Spinner spinner) {
                                spinner.getValueFactory().setValue(diaria.getNumeroDiaria());
                            }
                            if (child instanceof DatePicker datePicker) {
                                datePicker.setValue(diaria.getData());
                            }
                        }
                    }

                    // Coletar os valores das TextAreas
                    if (hbox.getChildren().get(1) instanceof VBox vBoxNotas && vBoxNotas.getChildren().get(1) instanceof TextArea txtNotas) {
                        txtNotas.setText(diaria.getNotas());
                    }

                    if (hbox.getChildren().get(2) instanceof VBox vBoxTratamento && vBoxTratamento.getChildren().get(1) instanceof TextArea txtTratamento) {
                        txtTratamento.setText(diaria.getTratamento());
                    }

                    // Coletar os itens selecionados do CheckComboBox
                    if (hbox.getChildren().get(3) instanceof VBox vBoxSinaisClinicos && vBoxSinaisClinicos.getChildren().get(1) instanceof CheckComboBox ckcmbSinaisClinicos) {
                        if(diaria.getSinaisClinicos() != null && !diaria.getSinaisClinicos().equals("")){
                            List<String> listaSinaisClinicos = Arrays.asList(diaria.getSinaisClinicos().split(","));
                            for(String sinal : listaSinaisClinicos){
                                ckcmbSinaisClinicos.getCheckModel().check(sinal);
                            }
                        }
                    }
                                        
//                    if (hbox.getChildren().get(5) instanceof VBox vBoxConsumo) {
//                        for (Node child : vBoxConsumo.getChildren()) {
//                            if (child instanceof ListView<?> listView) {
//                                ListView<Estoque> estoqueListView = (ListView<Estoque>) listView;
//                                ObservableList<Estoque> produtosConsumidos = FXCollections.observableArrayList(diaria.getListaConsumo());
//                                estoqueListView.setItems(produtosConsumidos);
//                            }
//                        }
//                    }
                    
                    if (hbox.getChildren().get(4) instanceof VBox vBoxConsumo) {
                            if (vBoxConsumo.getChildren().get(1) instanceof ListView<?> listView) {
                                ListView<Estoque> estoqueListView = (ListView<Estoque>) listView;
                                if(diaria.getListaConsumo() != null){
                                    ObservableList<Estoque> produtosConsumidos = FXCollections.observableArrayList(diaria.getListaConsumo());
                                    estoqueListView.setItems(produtosConsumidos);
                                }
                            }
                    }
                    
                    if (hbox.getChildren().get(5) instanceof VBox vBoxExames) {
                            if (vBoxExames.getChildren().get(1) instanceof ListView<?> listView) {
                                ListView<ExameRealizado> estoqueListView = (ListView<ExameRealizado>) listView;
                                if(diaria.getListaExames() != null){
                                    ObservableList<ExameRealizado> examesRealizados = FXCollections.observableArrayList(diaria.getListaExames());
                                    estoqueListView.setItems(examesRealizados);
                                }
                            }
                    }
                    
                    if (hbox.getChildren().get(6) instanceof VBox vBoxServicos) {
                            if (vBoxServicos.getChildren().get(1) instanceof ListView<?> listView) {
                                ListView<ServicoRealizado> estoqueListView = (ListView<ServicoRealizado>) listView;
                                if (diaria.getListaServico() != null){
                                    ObservableList<ServicoRealizado> servicosRealizados = FXCollections.observableArrayList(diaria.getListaServico());
                                    estoqueListView.setItems(servicosRealizados);
                                }
                            }
                    }
                    
                    if (hbox.getChildren().get(7) instanceof VBox vBoxVacinas) {
                            if (vBoxVacinas.getChildren().get(1) instanceof ListView<?> listView) {
                                ListView<Vacina> estoqueListView = (ListView<Vacina>) listView;
                                if (diaria.getListaVacinas() != null){
                                    ObservableList<Vacina> vacinasRealizadas = FXCollections.observableArrayList(diaria.getListaVacinas());
                                    estoqueListView.setItems(vacinasRealizadas);
                                }
                            }
                    }

//                    for (var innerNodes : hbox.getChildren()) {
//
//                        // Coletar os itens da ListView de Consumo
//                        if (innerNodes instanceof VBox vBoxConsumo) {
//                            for (Node child : vBoxConsumo.getChildren()) {
//                                if (child instanceof ListView<?> listView) {
//                                    ObservableList<?> items = listView.getItems();
//                                    if (!items.isEmpty() && items.get(0) instanceof Estoque) {
//                                        ListView<Estoque> estoqueListView = (ListView<Estoque>) listView;
//                                        ObservableList<Estoque> produtosConsumidos = FXCollections.observableArrayList(diaria.getListaConsumo());
//                                        estoqueListView.setItems(produtosConsumidos);
//                                    }
//                                }
//                            }
//                        }
//
//                        // Coletar os itens da ListView de Exames
//                        if (innerNodes instanceof VBox vBoxExames) {
//                            for (Node child : vBoxExames.getChildren()) {
//                                if (child instanceof ListView<?> listView) {
//                                    ObservableList<?> items = listView.getItems();
//                                    if (!items.isEmpty() && items.get(0) instanceof ExameRealizado) {
//                                        ListView<ExameRealizado> exameListView = (ListView<ExameRealizado>) listView;
//                                        ObservableList<ExameRealizado> examesRealizados = FXCollections.observableArrayList(diaria.getListaExames());
//                                        exameListView.setItems(examesRealizados);
//                                    }
//                                }
//                            }
//                        }
//
//                        // Coletar os itens da ListView de Serviços
//                        if (innerNodes instanceof VBox vBoxServicos) {
//                            for (Node child : vBoxServicos.getChildren()) {
//                                if (child instanceof ListView<?> listView) {
//                                    ObservableList<?> items = listView.getItems();
//                                    if (!items.isEmpty() && items.get(0) instanceof ServicoRealizado) {
//                                        ListView<ServicoRealizado> servicoListView = (ListView<ServicoRealizado>) listView;
//                                        ObservableList<ServicoRealizado> servicosRealizados = FXCollections.observableArrayList(diaria.getListaServico());
//                                        servicoListView.setItems(servicosRealizados);
//                                    }
//                                }
//                            }
//                        }
//
//                        // Coletar os itens da ListView de Vacinas
//                        if (innerNodes instanceof VBox vBoxVacinas) {
//                            for (Node child : vBoxVacinas.getChildren()) {
//                                if (child instanceof ListView<?> listView) {
//                                    ObservableList<?> items = listView.getItems();
//                                    if (!items.isEmpty() && items.get(0) instanceof Vacina) {
//                                        ListView<Vacina> vacinaListView = (ListView<Vacina>) listView;
//                                        ObservableList<Vacina> vacinasRealizadas = FXCollections.observableArrayList(diaria.getListaVacinas());
//                                        vacinaListView.setItems(vacinasRealizadas);
//                                    }
//                                }
//                            }
//                        }
//                    }
                }
            }
            indiceDiaria++;
        }
    }
    
    public void ajustarTela(){
        
    }
    
    private float getValorDiariaPadrao(){
        float valorDiariaPadrao = 0;
        for (ValorPadrao item : TelaPreferenciasController.valoresPadrao){
            if(item.getCodigoValorPadrao() == 1){
                valorDiariaPadrao = item.getValorPadraoNumeral();
            }
        }
        return valorDiariaPadrao;
    }

    private void addHBox() {
        DiariaInternacao diaria = new DiariaInternacao();
        if (listaDiarias == null){
            listaDiarias = new ArrayList<>();
        }

        //Cria a HBox que servirá para incluir todas as informações das diárias de internação
        HBox hbox = new HBox(15); // Espaçamento entre os itens da HBox
        hbox.getStyleClass().add("hbox-item");

        VBox vBoxData = new VBox(5);
        vBoxData.setAlignment(Pos.CENTER_LEFT);
        HBox hBoxNumero = new HBox(5);
        Label lblNumero = new Label("Nº: ");
        //DatePicker que mostra q data da diária
        DatePicker dpDataDiaria = new DatePicker(dpDtInternacao.getValue().plusDays(counter - 1));
        dpDataDiaria.setPrefWidth(115);
        //Spinner que mostra o número da diária no início da hBox
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100);
        valueFactory.setValue(counter++);
        Spinner spnNumeroDiaria = new Spinner(valueFactory);
        spnNumeroDiaria.setPrefWidth(60);
        hBoxNumero.getChildren().addAll(lblNumero, spnNumeroDiaria);
        vBoxData.getChildren().addAll(hBoxNumero, dpDataDiaria);
        
        dpDataDiaria.setOnAction((t) -> {
            diaria.setData(dpDataDiaria.getValue());
        });

//        String dataDiaria = dpDtInternacao.getValue().plusDays(counter - 1).format(dateFormatter);
//        Label lblNumeroDiaria = new Label(String.valueOf(counter++) + " - " + dataDiaria);  // Criar e incrementar contador
//        lblNumeroDiaria.getStyleClass().addAll("lblDiaria");
        //Cria o textField das notas com seu label
        VBox vBoxNotas = new VBox(0);
        TextArea txtNotas = new TextArea();
        Label lblNotas = new Label("Notas");
        txtNotas.setWrapText(true);
        txtNotas.setPrefSize(180, 80);
        vBoxNotas.getChildren().addAll(lblNotas, txtNotas);

        //Cria o textField do tratamento com seu label
        VBox vBoxTratamento = new VBox(0);
        TextArea txtTratamento = new TextArea();
        Label lblTratamento = new Label("Tratamento");
        txtTratamento.setWrapText(true);
        txtTratamento.setPrefSize(180, 80);
        vBoxTratamento.getChildren().addAll(lblTratamento, txtTratamento);

        //Cria o comboBox dos sinais clínicos com seu label
        VBox vBoxSinaisClinicos = new VBox(0);
        CheckComboBox ckcmbSinaisClinicos = new CheckComboBox(listaObsSinaisClinicos);
        Label lblSinaisClinicos = new Label("Sinais clínicos");
        ckcmbSinaisClinicos.setPrefSize(180, 20);
        Label lblSinaisSelecionados = new Label();
        lblSinaisSelecionados.textProperty().bind(Bindings.createStringBinding(() -> {
            // Obtém os itens selecionados
            ObservableList<String> selectedItems = ckcmbSinaisClinicos.getCheckModel().getCheckedItems();
            // Retorna os itens formatados como string
            return "Selecionados: " + String.join(", ", selectedItems);
        }, ckcmbSinaisClinicos.getCheckModel().getCheckedItems()));
        lblSinaisSelecionados.setWrapText(true);
        lblSinaisSelecionados.setMaxWidth(180);
        vBoxSinaisClinicos.getChildren().addAll(lblSinaisClinicos, ckcmbSinaisClinicos, lblSinaisSelecionados);

        //Cria o ListView do consumo com seu label
        VBox vBoxConsumo = new VBox(5);
        HBox hBoxConsumo = new HBox(30);
        hBoxConsumo.setPrefWidth(200);
        hBoxConsumo.setAlignment(Pos.BOTTOM_LEFT);
        Label lblConsumo = new Label("Consumo");
        Button btnAdicionarConsumo = new Button("Editar");
        btnAdicionarConsumo.getStyleClass().addAll("botoesTransparentes");
        hBoxConsumo.getChildren().addAll(lblConsumo, btnAdicionarConsumo);
        ListView listViewConsumo = new ListView<Estoque>();
        listViewConsumo.setPrefSize(200, 75);
        vBoxConsumo.getChildren().addAll(hBoxConsumo, listViewConsumo);
        ObservableList<Estoque> produtosConsumidos = FXCollections.observableArrayList();
        listViewConsumo.setItems(produtosConsumidos);

        btnAdicionarConsumo.setOnAction((t) -> {
            if (validarInternado()) {
                listViewConsumo.setItems(new MenuPrincipal().cadastrarProdutoConsumido(btnLimpar.getScene().getWindow(), internado, diaria, listViewConsumo.getItems()));
                calcularValorTotal();
            }
        });

        //Cria o ListView dos exames com seu label
        VBox vBoxExames = new VBox(5);
        HBox hBoxExames = new HBox(30);
        hBoxExames.setPrefWidth(200);
        hBoxExames.setAlignment(Pos.BOTTOM_LEFT);
        Label lblExames = new Label("Exames");
        Button btnAdicionarExame = new Button("Editar");
        btnAdicionarExame.getStyleClass().addAll("botoesTransparentes");
        hBoxExames.getChildren().addAll(lblExames, btnAdicionarExame);
        ListView listViewExames = new ListView<ExameRealizado>();
        listViewExames.setPrefSize(200, 75);
        vBoxExames.getChildren().addAll(hBoxExames, listViewExames);
        ObservableList<ExameRealizado> examesRealizados = FXCollections.observableArrayList();
        listViewExames.setItems(examesRealizados);

        btnAdicionarExame.setOnAction((t) -> {
            if (validarInternado()) {
                listViewExames.setItems(new MenuPrincipal().cadastrarExameRealizado(btnLimpar.getScene().getWindow(), internado, diaria, listViewExames.getItems()));
                calcularValorTotal();
            }
        });

        //Cria o ListView dos Serviços com seu label
        VBox vBoxServicos = new VBox(5);
        HBox hBoxServicos = new HBox(30);
        hBoxServicos.setPrefWidth(200);
        hBoxServicos.setAlignment(Pos.BOTTOM_LEFT);
        Label lblServicos = new Label("Serviços");
        Button btnAdicionarServico = new Button("Editar");
        btnAdicionarServico.getStyleClass().addAll("botoesTransparentes");
        hBoxServicos.getChildren().addAll(lblServicos, btnAdicionarServico);
        ListView listViewServicos = new ListView<ServicoRealizado>();
        listViewServicos.setPrefSize(200, 75);
        vBoxServicos.getChildren().addAll(hBoxServicos, listViewServicos);
        ObservableList<ServicoRealizado> servicosRealizados = FXCollections.observableArrayList();
        listViewServicos.setItems(servicosRealizados);
        btnAdicionarServico.setOnAction((t) -> {
            if (validarInternado()) {
                listViewServicos.setItems(new MenuPrincipal().cadastrarServicoRealizado(btnLimpar.getScene().getWindow(), internado, diaria, listViewServicos.getItems()));
                calcularValorTotal();
            }
        });

        //Cria o ListView das Vacinas com seu label
        VBox vBoxVacinas = new VBox(5);
        HBox hBoxVacinas = new HBox(30);
        hBoxVacinas.setPrefWidth(200);
        hBoxVacinas.setAlignment(Pos.BOTTOM_LEFT);
        Label lblVacinas = new Label("Vacinas");
        Button btnAdicionarVacina = new Button("Editar");
        btnAdicionarVacina.getStyleClass().addAll("botoesTransparentes");
        hBoxVacinas.getChildren().addAll(lblVacinas, btnAdicionarVacina);
        ListView listViewVacinas = new ListView<Vacina>();
        listViewVacinas.setPrefSize(200, 75);
        vBoxVacinas.getChildren().addAll(hBoxVacinas, listViewVacinas);
        ObservableList<Vacina> vacinasRealizadas = FXCollections.observableArrayList();
        listViewVacinas.setItems(vacinasRealizadas);
        btnAdicionarVacina.setOnAction((t) -> {
            if (validarInternado()) {
                listViewVacinas.setItems(new MenuPrincipal().cadastrarVacina(btnLimpar.getScene().getWindow(), internado, diaria, listViewVacinas.getItems()));
                calcularValorTotal();
            }
        });

        Button removeButton = new Button("Remover");
        removeButton.getStyleClass().addAll("botoesTransparentes");
        // Remover a HBox ao clicar no botão de remover
        removeButton.setOnAction(e -> {
            vBox.getChildren().remove(hbox);
            calcularValorTotal();
        });

        hbox.getChildren().addAll(vBoxData, vBoxNotas, vBoxTratamento, vBoxSinaisClinicos, vBoxConsumo, vBoxExames, vBoxServicos, vBoxVacinas, removeButton);
        vBox.getChildren().add(hbox);

        listaDiarias.add(diaria);
        listaDiarias.get(index).setData(dpDataDiaria.getValue());
        index++;
    }
    
    private void salvar() {
        if (listaDiarias == null){
            listaDiarias = new ArrayList<>();
        }
        // Percorrer todas as HBoxes dentro da VBox
        for (var node : vBox.getChildren()) {
            if (node instanceof HBox hbox) {

                // Variáveis para armazenar os valores
                int numeroDiaria = 0;
                LocalDate dataDiaria = null;
                String notas = "";
                String tratamento = "";
                List<String> sinaisClinicos = new ArrayList<>();
                List<ExameRealizado> exames = new ArrayList<>();
                List<ServicoRealizado> servicos = new ArrayList<>();
                List<Vacina> vacinas = new ArrayList<>();
                List<Estoque> consumo = new ArrayList<>();

                for (var innerNode : hbox.getChildren()) {

                    // Capturar valores do Spinner e DatePicker corretamente
                    if (innerNode instanceof VBox vBoxData) {
                        for (Node child : vBoxData.getChildren()) {
                            if (child instanceof HBox hBoxNumero && hBoxNumero.getChildren().get(1) instanceof Spinner spinner) {
                                numeroDiaria = (Integer) spinner.getValue();
                            }
                            if (child instanceof DatePicker datePicker) {
                                dataDiaria = datePicker.getValue();
                            }
                        }
                    }

                    // Coletar os valores das TextAreas
                    if (hbox.getChildren().get(1) instanceof VBox vBoxNotas && vBoxNotas.getChildren().get(1) instanceof TextArea txtNotas) {
                        notas = txtNotas.getText();
                    }

                    if (hbox.getChildren().get(2) instanceof VBox vBoxTratamento && vBoxTratamento.getChildren().get(1) instanceof TextArea txtTratamento) {
                        tratamento = txtTratamento.getText();
                    }

                    // Coletar os itens selecionados do CheckComboBox
                    if (innerNode instanceof VBox vBoxSinaisClinicos && vBoxSinaisClinicos.getChildren().get(1) instanceof CheckComboBox ckcmbSinaisClinicos) {
                        sinaisClinicos.addAll(ckcmbSinaisClinicos.getCheckModel().getCheckedItems());
                    }

                    for (var innerNodes : hbox.getChildren()) {

                        // Coletar os itens da ListView de Consumo
                        if (innerNodes instanceof VBox vBoxConsumo) {
                            for (Node child : vBoxConsumo.getChildren()) {
                                if (child instanceof ListView<?> listView) {
                                    ObservableList<?> items = listView.getItems();
                                    if (!items.isEmpty() && items.get(0) instanceof Estoque) {
                                        consumo = new ArrayList<>((ObservableList<Estoque>) items);
                                    }
                                }
                            }
                        }

                        // Coletar os itens da ListView de Exames
                        if (innerNodes instanceof VBox vBoxExames) {
                            for (Node child : vBoxExames.getChildren()) {
                                if (child instanceof ListView<?> listView) {
                                    ObservableList<?> items = listView.getItems();
                                    if (!items.isEmpty() && items.get(0) instanceof ExameRealizado) {
                                        exames = new ArrayList<>((ObservableList<ExameRealizado>) items);
                                    }
                                }
                            }
                        }

                        // Coletar os itens da ListView de Serviços
                        if (innerNodes instanceof VBox vBoxServicos) {
                            for (Node child : vBoxServicos.getChildren()) {
                                if (child instanceof ListView<?> listView) {
                                    ObservableList<?> items = listView.getItems();
                                    if (!items.isEmpty() && items.get(0) instanceof ServicoRealizado) {
                                        servicos = new ArrayList<>((ObservableList<ServicoRealizado>) items);
                                    }
                                }
                            }
                        }

                        // Coletar os itens da ListView de Vacinas
                        if (innerNodes instanceof VBox vBoxVacinas) {
                            for (Node child : vBoxVacinas.getChildren()) {
                                if (child instanceof ListView<?> listView) {
                                    ObservableList<?> items = listView.getItems();
                                    if (!items.isEmpty() && items.get(0) instanceof Vacina) {
                                        vacinas = new ArrayList<>((ObservableList<Vacina>) items);
                                    }
                                }
                            }
                        }
                    }
                }

                // Criar um novo objeto DiariaInternacao com os valores coletados, atualizando o objeto se já existir
                DiariaInternacao diaria = new DiariaInternacao(numeroDiaria, notas, tratamento, 
                    String.join(",", sinaisClinicos), dataDiaria, servicos, exames, vacinas);
                diaria.setListaConsumo(consumo);
                
                List<LocalDate> listaDatasDiarias = new ArrayList<>();
                for (DiariaInternacao item : listaDiarias){
                    listaDatasDiarias.add(item.getData());
                }
                
                int indiceDiaria = -1;
                DiariaInternacao diariaTeste = null;
                if (listaDatasDiarias.contains(dataDiaria)){
                    indiceDiaria = listaDatasDiarias.indexOf(dataDiaria);
                    diariaTeste = listaDiarias.get(indiceDiaria);
                    int idDiaria = diariaTeste.getId();
                    diaria.setId(idDiaria);
                }
                

//                diaria.setData(dataDiaria);
//                diaria.setListaConsumo(consumo);
//                diaria.setListaExames(exames);
//                diaria.setListaServico(servicos);
//                diaria.setListaVacinas(vacinas);
//                diaria.setNotas(notas);
//                diaria.setNumeroDiaria(numeroDiaria);
//                diaria.setSinaisClinicos(String.join(",", sinaisClinicos));
//                diaria.setTratamento(tratamento);

                //Verifica se a diária com a data especificada já exitia na lista. 
                //Caso afirmativo, altera a diaria existente. Caso negativo, insere uma nova diaria na lista
                if (indiceDiaria == -1) {
                    System.out.println("telaCadastroInternadoController L 684 - caí no IF" );
                    listaDiarias.add(diaria);
                }else{
                    System.out.println("telaCadastroInternadoController L 687 - caí no ELSE - " + indiceDiaria );
                    listaDiarias.set(indiceDiaria, diaria);
                }
                System.out.println(listaDiarias);
                
                // Exemplo de saída para conferência
                System.out.println("----------------------------------------------------------------------");
                System.out.println("Id internado: " + internado.getId());
                System.out.println("Id da diária: " + diaria.getId());
                System.out.println("Número da Diária: " + numeroDiaria);
                System.out.println("Data da diária: " + (dataDiaria != null ? dataDiaria.format(dateFormatter) : "null"));
                System.out.println("Notas: " + notas);
                System.out.println("Tratamento: " + tratamento);
                System.out.println("Sinais Clínicos: " + String.join(", ", sinaisClinicos));
                System.out.println("Consumo: " + consumo);
                System.out.println("Exames: " + exames);
                System.out.println("Serviços: " + servicos);
                System.out.println("Vacinas: " + vacinas);
                System.out.println("______________________________________________________________________");

            }
        }
        
        internado.setDiaria(listaDiarias);
        internado.setPet(scmbPet.getValue());
        internado.setVeterinario(scmbVeterinario.getValue());
        internado.setInternacaoAtiva(ckbInternacaoAtiva.isSelected());
        internado.setDtInternacao(dpDtInternacao.getValue());
        internado.setDtAlta(dpDtAlta.getValue());
        internado.setValorDiaria(Utils.formataFloat(txtValorDiaria.getText()));
        internado.setValorTotal(Utils.formataFloat(txtValorTotal.getText()));
        internado.setObservacoes(txtObservacao.getText());
        
        if(new InternacaoService().salvarOuAtualizarInternado(internado)){
            for(DiariaInternacao item : listaDiarias){
                if(new InternacaoService().salvarOuAtualizarDiaria(internado, item)){
                    
                }
            }
            
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Sucesso!");
            al.setContentText("Animal internado atualizado com sucesso!");
            al.showAndWait();
            ((Stage) btnCancelar.getScene().getWindow()).close();
        } else {
            // Deu erro. O retorno do boolean veio false
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERRO");
            al.setContentText("Ocorreu um erro ao inserir!");
            al.showAndWait();
        }
}

    private void calcularValorTotal(){
        float valorTotal = 0;
        int numeroDiarias = 0;
        for (var node : vBox.getChildren()) {
            if (node instanceof HBox hbox) {
                // Variáveis para armazenar os valores
                List<ExameRealizado> exames = new ArrayList<>();
                List<ServicoRealizado> servicos = new ArrayList<>();
                List<Vacina> vacinas = new ArrayList<>();
                List<Estoque> consumo = new ArrayList<>();

                for (var innerNodes : hbox.getChildren()) {

                    // Coletar os itens da ListView de Consumo
                    if (innerNodes instanceof VBox vBoxConsumo) {
                        for (Node child : vBoxConsumo.getChildren()) {
                            if (child instanceof ListView<?> listView) {
                                ObservableList<?> items = listView.getItems();
                                if (!items.isEmpty() && items.get(0) instanceof Estoque) {
                                    consumo = new ArrayList<>((ObservableList<Estoque>) items);
                                    for (Estoque item : consumo) {
                                        valorTotal += (item.getValorVenda() * item.getQuantidadeConsumida());
                                    }
                                }
                            }
                        }
                    }

                    // Coletar os itens da ListView de Exames
                    if (innerNodes instanceof VBox vBoxExames) {
                        for (Node child : vBoxExames.getChildren()) {
                            if (child instanceof ListView<?> listView) {
                                ObservableList<?> items = listView.getItems();
                                if (!items.isEmpty() && items.get(0) instanceof ExameRealizado) {
                                    exames = new ArrayList<>((ObservableList<ExameRealizado>) items);
                                    for (ExameRealizado item : exames) {
                                        valorTotal += item.getValor();
                                    }
                                }
                            }
                        }
                    }

                    // Coletar os itens da ListView de Serviços
                    if (innerNodes instanceof VBox vBoxServicos) {
                        for (Node child : vBoxServicos.getChildren()) {
                            if (child instanceof ListView<?> listView) {
                                ObservableList<?> items = listView.getItems();
                                if (!items.isEmpty() && items.get(0) instanceof ServicoRealizado) {
                                    servicos = new ArrayList<>((ObservableList<ServicoRealizado>) items);
                                    for (ServicoRealizado item : servicos) {
                                        valorTotal += (item.getValor() * item.getQuantidade());
                                    }
                                }
                            }
                        }
                    }

                    // Coletar os itens da ListView de Vacinas
                    if (innerNodes instanceof VBox vBoxVacinas) {
                        for (Node child : vBoxVacinas.getChildren()) {
                            if (child instanceof ListView<?> listView) {
                                ObservableList<?> items = listView.getItems();
                                if (!items.isEmpty() && items.get(0) instanceof Vacina) {
                                    vacinas = new ArrayList<>((ObservableList<Vacina>) items);
                                    for (Vacina item : vacinas) {
                                        valorTotal += item.getValor();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            numeroDiarias ++;
        }
        valorTotal = valorTotal + numeroDiarias * Utils.formataFloat(txtValorDiaria.getText());
        txtValorTotal.setText(Utils.imprimeValor(String.valueOf(valorTotal)));
    }
    
    private boolean validarInternado() {
        boolean result = false;
        try {
            ValidacaoException exc = new ValidacaoException("Erro validando!!");
            
            if (dpDtInternacao.getValue() == null) exc.adicionarErro("DataInternacao", "Insira a data de internação!");
            if (scmbTutor.getValue() == null) exc.adicionarErro("Tutor", "Selecione um tutor!");
            if (scmbPet.getValue() == null) exc.adicionarErro("Pet", "Selecione um Pet!");
            if (scmbVeterinario.getValue() == null) exc.adicionarErro("Veterinario", "Selecione um Veterinário!");
            
            if (!exc.getErrors().isEmpty()) {
                throw exc;
            }
            result = true;
        } catch (ValidacaoException e) {
            System.out.println("Erro na validação");
            setErrorMessages(e.getErrors());
        }
        return result;
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

    private void listarTutores() {
        ObservableList<Tutor> listaObsTutores = FXCollections.observableArrayList(listaTutores);
//        cmbTutor.setItems(listaObsTutores);
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
        listaVeterinarios = new ClinicaService().getVeterinariosDaClinica(clinicaPrincipal.getIdClinica(), clinicaPrincipal.getVeterinarioClinica().getId());
        ObservableList<Veterinario> listaObsVeterinario = FXCollections.observableArrayList(listaVeterinarios);
        scmbVeterinario.setItems(listaObsVeterinario);
    }
    
    private void limparCampos(){
        scmbPet.setValue(null);
        scmbPet.setDisable(true);
        scmbTutor.setValue(null);
        scmbVeterinario.setValue(null);
        dpDtInternacao.setValue(LocalDate.now());
        dpDtAlta.setValue(null);
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroPet.setText(campos.contains("Pet") ? errors.get("Pet") : "");
        lblErroTutor.setText(campos.contains("Tutor") ? errors.get("Tutor") : "");
        lblErroVeterinario.setText(campos.contains("Veterinario") ? errors.get("Veterinario") : "");
        lblErroDtInternacao.setText(campos.contains("DataInternacao") ? errors.get("DataInternacao") : "");
    }

}
