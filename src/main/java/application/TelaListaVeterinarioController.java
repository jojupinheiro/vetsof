
package application;

import application.Principal;
import java.net.URL;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.classes.Clinica;
import model.classes.Tutor;
import model.classes.Veterinario;
import model.services.ClinicaService;
import model.services.TutorService;
import model.services.VeterinarioService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaListaVeterinarioController implements Initializable {

    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovoVeterinario;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private TableColumn<Veterinario, String> tableColumnCpf;
    @FXML    private TableColumn<Veterinario, String> tableColumnCrmv;
    @FXML    private TableColumn<Veterinario, String> tableColumnEmail;
    @FXML    private TableColumn<Veterinario, List> tableColumnLocalOndeAtende;
    @FXML    private TableColumn<Veterinario, String> tableColumnMunicipio;
    @FXML    private TableColumn<Veterinario, String> tableColumnNome;
    @FXML    private TableColumn<Veterinario, String> tableColumnTelefone;
    @FXML    private TableView<Veterinario> tblVeterinario;
    @FXML    private TextField txtBusca;

    List<Veterinario> listaVeterinario;
    private String txtFiltro;
    private int filtroSelecionado = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tableColumnCrmv.setCellValueFactory(new PropertyValueFactory<>("crmv"));
        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tableColumnLocalOndeAtende.setCellValueFactory(new PropertyValueFactory<>("listaClinicas")); 
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        
        tblVeterinario.setRowFactory(
                new Callback<TableView<Veterinario>, TableRow<Veterinario>>() {
            @Override
            public TableRow<Veterinario> call(TableView<Veterinario> tableView) {
                final TableRow<Veterinario> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem novo = new MenuItem("Novo Veterinário");
                novo.setOnAction((t) -> {
                    new MenuPrincipal().cadastrarVeterinario(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem editItem = new MenuItem("Editar Veterinário");
                editItem.setOnAction((t) -> {
                    Veterinario veterinario = row.getItem();
                    new MenuPrincipal().editarVeterinario(veterinario, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem removeItem = new MenuItem("Excluir");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText(row.getItem().getNome() + " será excluído! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new VeterinarioService().excluir(row.getItem())) {
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
                rowMenu.getItems().addAll(novo, editItem, removeItem);

                // only display context menu for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(rowMenu));
                return row;
            }
        });
        
        tblVeterinario.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Veterinario veterinario = tblVeterinario.getSelectionModel().getSelectedItem();
                //criando a tela de Cadastro e passando Tutor como parâmetro
                new MenuPrincipal().editarVeterinario(veterinario, btnFiltrar.getScene().getWindow());
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        tblVeterinario.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                Veterinario veterinario = tblVeterinario.getSelectionModel().getSelectedItem();
                al.setContentText("O Veterinário " + veterinario.getNome() + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    if (new VeterinarioService().excluir(veterinario)) {
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

        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF","Nome","Sexo Masculino", "Sexo Feminino",
                "CRMV","Bairro","Município");
        cmbFiltro.setItems(listaObs);

        atualizaTabela(filtroSelecionado, txtFiltro);
        
        btnNovoVeterinario.setOnAction((t) -> {
            new MenuPrincipal().cadastrarVeterinario(btnFiltrar.getScene().getWindow());
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
    }

    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        // Buscar os dados no banco de dados na tabela pet
        listaVeterinario = new VeterinarioService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<Veterinario> listaObs = FXCollections.observableArrayList(listaVeterinario);
        //Vinculando a lista observável com a TableView
        tblVeterinario.setItems(listaObs);
        Utils.formatTableColumnFone(tableColumnTelefone);
        Utils.formatTableColumnList(tableColumnLocalOndeAtende);
        Utils.formatTableColumnCpfOuCnpj(tableColumnCpf);
    }

}
