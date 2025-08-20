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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.classes.Clinica;
import model.classes.Tutor;
import model.services.ClinicaService;
import model.services.TutorService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaListaClinicaController implements Initializable {

    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovaClinica;
    @FXML    private ComboBox cmbFiltro;
    @FXML    private TableColumn<Clinica, String> tableColumnCnpj;
    @FXML    private TableColumn<Clinica, String> tableColumnEmail;
    @FXML    private TableColumn<Clinica, String> tableColumnFoneAlt;
    @FXML    private TableColumn<Clinica, String> tableColumnFonePrinc;
    @FXML    private TableColumn<Clinica, String> tableColumnLogradouro;
    @FXML    private TableColumn<Clinica, String> tableColumnMunicipio;
    @FXML    private TableColumn<Clinica, String> tableColumnNome;
    @FXML    private TableColumn<Clinica, String> tableColumnVeterinario;
    @FXML    private TableView<Clinica> tblClinica;
    @FXML    private TextField txtBusca;

    List<Clinica> listaClinica;
    private String txtFiltro;
    private int filtroSelecionado = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeClinica"));
        tableColumnCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("emailClinica"));
        tableColumnVeterinario.setCellValueFactory(new PropertyValueFactory<>("veterinarioClinica"));
        tableColumnLogradouro.setCellValueFactory(new PropertyValueFactory<>("logradouro"));
        tableColumnMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipioClinica"));
        tableColumnFonePrinc.setCellValueFactory(new PropertyValueFactory<>("telefoneClinica"));
        tableColumnFoneAlt.setCellValueFactory(new PropertyValueFactory<>("telefoneAlternativoClinica"));

        tblClinica.setRowFactory(
                new Callback<TableView<Clinica>, TableRow<Clinica>>() {
            @Override
            public TableRow<Clinica> call(TableView<Clinica> tableView) {
                final TableRow<Clinica> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem novo = new MenuItem("Nova clínica");
                novo.setOnAction((t) -> {
                    new MenuPrincipal().cadastrarClinica(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem editItem = new MenuItem("Editar clínica");
                editItem.setOnAction((t) -> {
                    Clinica clinica = row.getItem();
                    new MenuPrincipal().editarClinica(clinica, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem removeItem = new MenuItem("Excluir");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText(row.getItem().getNomeClinica() + " será excluído! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new ClinicaService().excluir(row.getItem())) {
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

        tblClinica.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Clinica clinica = tblClinica.getSelectionModel().getSelectedItem();
                //criando a tela de Cadastro e passando Tutor como parâmetro
                new MenuPrincipal().editarClinica(clinica, btnFiltrar.getScene().getWindow());
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });

        tblClinica.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                Clinica clinica = tblClinica.getSelectionModel().getSelectedItem();
                al.setContentText("A clínica " + clinica.getNomeClinica() + " será excluída! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    if (new ClinicaService().excluir(clinica)) {
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

        ObservableList<String> listaObs = FXCollections.observableArrayList("Nome", "CNPJ",
                "Veterinário responsável", "Município");
        cmbFiltro.setItems(listaObs);

        atualizaTabela(filtroSelecionado, txtFiltro);

        btnNovaClinica.setOnAction((t) -> {
            new MenuPrincipal().cadastrarClinica(btnLimpar.getScene().getWindow());
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
        listaClinica = new ClinicaService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<Clinica> listaObs = FXCollections.observableArrayList(listaClinica);
        //Vinculando a lista observável com a TableView
        tblClinica.setItems(listaObs);
        Utils.formatTableColumnFone(tableColumnFonePrinc);
        Utils.formatTableColumnFone(tableColumnFoneAlt);
        Utils.formatTableColumnCnpj(tableColumnCnpj);
    }

}
