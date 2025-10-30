package application;

import application.Principal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import model.classes.Pet;
import model.classes.Tutor;
import model.services.PetService;
import model.services.TutorService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaListaTutorController implements Initializable {

    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovoTutor;
    @FXML    private ComboBox cmbFiltro;
    @FXML    private TableColumn<Tutor, String> tableColumnBairro;
    @FXML    private TableColumn<Tutor, String> tableColumnCPF;
    @FXML    private TableColumn<Tutor, LocalDate> tableColumnDtNasc;
    @FXML    private TableColumn<Tutor, Integer> tableColumnFaixaRenda;
    @FXML    private TableColumn<Tutor, String> tableColumnLogradouro;
    @FXML    private TableColumn<Tutor, String> tableColumnMunicipio;
    @FXML    private TableColumn<Tutor, String> tableColumnNome;
    @FXML    private TableColumn<Tutor, Boolean> tableColumnSexo;
    @FXML    private TableColumn<Tutor, String> tableColumnTelefone;
    @FXML    private TableColumn<Tutor, String> tableColumnTipoTutor;
    @FXML    private TableView<Tutor> tblTutor;
    @FXML    private TextField txtBusca;

    List<Tutor> listaTutor;
    private String txtFiltro;
    private int filtroSelecionado = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        tableColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnDtNasc.setCellValueFactory(new PropertyValueFactory<>("dtNasc"));
        tableColumnFaixaRenda.setCellValueFactory(new PropertyValueFactory<>("faixaRenda"));
        tableColumnLogradouro.setCellValueFactory(new PropertyValueFactory<>("logradouro"));
        tableColumnMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefoneTutor"));
        tableColumnTipoTutor.setCellValueFactory(new PropertyValueFactory<>("tipoTutor"));
  
        tblTutor.setRowFactory(new Callback<TableView<Tutor>, TableRow<Tutor>>() {
            @Override
            public TableRow<Tutor> call(TableView<Tutor> tableView) {
                // Cria a TableRow que terá o Tooltip
                final TableRow<Tutor> row = new TableRow<Tutor>() {
                    private final Tooltip tooltip = new Tooltip();

                    @Override
                    protected void updateItem(Tutor tutor, boolean empty) {
                        super.updateItem(tutor, empty);
                        if (empty || tutor == null) {
                            setTooltip(null);
                        } else {
                            tooltip.setText("Observações: " + tutor.getObservacaoTutor());
                            tooltip.setShowDelay(Duration.millis(500));
                            setTooltip(tooltip);
                        }
                    }
                };

                // Cria o ContextMenu (menu de clique direito)
                final ContextMenu rowMenu = new ContextMenu();

                MenuItem novo = new MenuItem("Novo Tutor");
                novo.setOnAction((t) -> {
                    new MenuPrincipal().cadastrarTutor(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem verPets = new MenuItem("Ver Pets");
                verPets.setOnAction((t) -> {
                    Tutor tutor = row.getItem();
                    new MenuPrincipal().verPetFiltrado((Stage) btnFiltrar.getScene().getWindow(), tutor);
                });

                MenuItem novoPet = new MenuItem("Adicionar Pet");
                novoPet.setOnAction((t) -> {
                    Tutor tutor = row.getItem();
                    new MenuPrincipal().cadastrarPetDoTutor(tutor, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem editItem = new MenuItem("Editar Tutor");
                editItem.setOnAction((t) -> {
                    Tutor tutor = row.getItem();
                    new MenuPrincipal().editarTutor(tutor, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem removeItem = new MenuItem("Excluir Tutor");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText(row.getItem().getNome() + " será excluído! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new TutorService().excluir(row.getItem())) {
                                Alert mens = new Alert(Alert.AlertType.INFORMATION);
                                mens.initOwner(btnFiltrar.getScene().getWindow());
                                mens.setTitle("Excluído");
                                mens.setContentText("Registro excluído com sucesso!");
                                mens.showAndWait();
                                atualizaTabela(filtroSelecionado, txtFiltro);
                            }
                        }
                    }
                });

                rowMenu.getItems().addAll(novo, editItem, removeItem, verPets, novoPet);

                // Associa o ContextMenu à linha, apenas se a linha não estiver vazia
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(rowMenu));

                return row;
            }
        });

        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF", "Nome", "Sexo - Masculino",
                "Sexo - Feminino", "Data de Nascimento",
                "Bairro", "Município", "Tipo", "Renda opção 1", "Renda opção 2", "Renda opção 3",
                "Observação", "Telefone");
        cmbFiltro.setItems(listaObs);

        atualizaTabela(filtroSelecionado, txtFiltro);

        tblTutor.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Tutor tutor = tblTutor.getSelectionModel().getSelectedItem();
                //criando a tela de Cadastro e passando Tutor como parâmetro
                new MenuPrincipal().editarTutor(tutor, btnFiltrar.getScene().getWindow());
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        tblTutor.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                Tutor tutor = tblTutor.getSelectionModel().getSelectedItem();
                al.setContentText("O tutor " + tutor.getNome()+ " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    if (new TutorService().excluir(tutor)) {
                        Alert mens = new Alert(Alert.AlertType.INFORMATION);
                        mens.initOwner(btnFiltrar.getScene().getWindow());
                        mens.setTitle("Excluído");
                        mens.setContentText("Registro excluído com sucesso!");
                        mens.showAndWait();
                        atualizaTabela(filtroSelecionado, txtFiltro);
                    }
                }
            }
        });
        
        btnNovoTutor.setOnAction((t) -> {
            new MenuPrincipal().cadastrarTutor(btnFiltrar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                atualizaTabela(filtroSelecionado, txtFiltro);
            } catch (Exception e) {
                e.printStackTrace();
                filtroSelecionado = 0;
            }
        });

        btnLimpar.setOnAction((t) -> {
            cmbFiltro.getSelectionModel().select(-1);
            txtBusca.setText("");
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        btnFiltrar.setOnAction((t) -> {
            txtFiltro = txtBusca.getText();
            filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        txtBusca.setOnAction((t) -> {
            txtFiltro = txtBusca.getText();
            filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
            atualizaTabela(filtroSelecionado, txtFiltro);
        });
        
        
        
        ajustarTela();
    }
    
    public void ajustarTela(){
        if (TelaPreferenciasController.preferencias.get(1) == 1){      //Se estiver no modo clínica
            tableColumnFaixaRenda.setVisible(false);
        }
    }

    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        // Buscar os dados no banco de dados na tabela pet
        listaTutor = new TutorService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<Tutor> listaObs = FXCollections.observableArrayList(listaTutor);
        //Vinculando a lista observável com a TableView
        tblTutor.setItems(listaObs);
        Utils.formatTableColumnDate(tableColumnDtNasc);
        Utils.formatTableColumnFone(tableColumnTelefone);
        Utils.formatTableColumnFaixaRenda(tableColumnFaixaRenda);
        Utils.formatTableColumnCpfOuCnpj(tableColumnCPF);
    }
    
}
