package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import model.classes.Clinica;
import model.classes.utilitario.Especie;
import model.classes.Pet;
import model.classes.prescricoes.Prescricao;
import model.classes.Tutor;
import model.classes.Veterinario;
import model.services.PrescricaoService;
import model.services.VeterinarioService;
import view.utils.MascarasFX;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaListaPrescricaoController implements Initializable {
    
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovaPrescricao;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private DatePicker dpDataFim;
    @FXML    private DatePicker dpDataInicio;
    @FXML    private HBox boxDatas;
    @FXML    private TableColumn<Prescricao, LocalDate> tableColumnData;
    @FXML    private TableColumn<Prescricao, Especie> tableColumnEspecie;
    @FXML    private TableColumn<Prescricao, Pet> tableColumnPet;
    @FXML    private TableColumn<Prescricao, Integer> tableColumnProtocolo;
    @FXML    private TableColumn<Prescricao, Tutor> tableColumnTutor;
    @FXML    private TableColumn<Prescricao, Clinica> tableColumnClinica;
    @FXML    private TableColumn<Prescricao, Veterinario> tableColumnVeterinario;
    @FXML    private TableView<Prescricao> tblPrescricoes;
    @FXML    private TextField txtBusca;
    
    List<Prescricao> listaPrescricao;
    private String txtFiltro;
    private int filtroSelecionado = -1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraData(dpDataFim);
        MascarasFX.mascaraData(dpDataInicio);
        
        
        tableColumnProtocolo.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnPet.setCellValueFactory(new PropertyValueFactory<>("NomePet"));
        tableColumnEspecie.setCellValueFactory(new PropertyValueFactory<>("NomeEspecie"));
        tableColumnTutor.setCellValueFactory(new PropertyValueFactory<>("NomeTutor"));
        tableColumnClinica.setCellValueFactory(new PropertyValueFactory<>("NomeClinica"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnVeterinario.setCellValueFactory(new PropertyValueFactory<>("NomeVeterinario")); 

        tblPrescricoes.setRowFactory(
                new Callback<TableView<Prescricao>, TableRow<Prescricao>>() {
            @Override
            public TableRow<Prescricao> call(TableView<Prescricao> tableView) {
                final TableRow<Prescricao> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem novo = new MenuItem("Nova Prescrição");
                novo.setOnAction((t) -> {
                    new MenuPrincipal().cadastrarVeterinario(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem editItem = new MenuItem("Editar Prescrição");
                editItem.setOnAction((t) -> {
                    Prescricao prescricao = row.getItem();
                    new MenuPrincipal().editarPrescricao(prescricao, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem removeItem = new MenuItem("Excluir");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText("A prescrição de " + row.getItem().getPet().getNomePet()+ " será excluída! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new PrescricaoService().excluir(row.getItem())) {
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
        
        tblPrescricoes.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Prescricao prescricao = tblPrescricoes.getSelectionModel().getSelectedItem();
                //criando a tela de Cadastro e passando Tutor como parâmetro
                new MenuPrincipal().editarPrescricao(prescricao, btnFiltrar.getScene().getWindow());
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        btnNovaPrescricao.setOnAction((t) -> {
            new MenuPrincipal().cadastrarPrescricao(btnFiltrar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("CPF do tutor", "Data", "Espécie",
                 "Pet", "Protocolo", "Tutor", "Veterinário");
        cmbFiltro.setItems(listaObs);
        
        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                if (filtroSelecionado == 1) {
                    txtBusca.setVisible(false);
                    txtBusca.setManaged(false);
                    boxDatas.setVisible(true);
                    boxDatas.setManaged(true);
                } else {
                    txtBusca.setVisible(true);
                    txtBusca.setManaged(true);
                    boxDatas.setVisible(false);
                    boxDatas.setManaged(false);
                    atualizaTabela(filtroSelecionado, txtFiltro);
                }

            } catch (Exception e) {
                filtroSelecionado = 0;
                e.printStackTrace();
            }
        });
        
        btnLimpar.setOnAction((t) -> {
            cmbFiltro.getSelectionModel().select(-1);
            txtBusca.setText("");
            atualizaTabela(filtroSelecionado, txtFiltro);
            dpDataFim.setValue(null);
            dpDataInicio.setValue(null);
        });

        btnFiltrar.setOnAction((t) -> {
            if (filtroSelecionado == 1){
                String dtInicial = dpDataInicio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String dtFinal = dpDataFim.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtFiltro = dtInicial + " " + dtFinal;
            }else{
                txtFiltro = txtBusca.getText();
            }
            atualizaTabela(filtroSelecionado, txtFiltro);
        });
        
        txtBusca.setOnAction((t) -> {
            if (filtroSelecionado != 1 && filtroSelecionado != -1){
                txtFiltro = txtBusca.getText();
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        atualizaTabela(filtroSelecionado, txtFiltro);
        boxDatas.setVisible(false);
        boxDatas.setManaged(false);
    }    
    
    
    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        listaPrescricao = new PrescricaoService().getAll(filtroSelecionado, txtFiltro);
        ObservableList<Prescricao> listaObsPrescricoes = FXCollections.observableArrayList(listaPrescricao);
        tblPrescricoes.setItems(listaObsPrescricoes);
        Utils.formatTableColumnDate(tableColumnData);
    }
}
