package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import model.classes.Internado;
import model.classes.Pet;
import model.classes.Tutor;
import model.classes.Veterinario;
import model.services.InternacaoService;
import model.services.PetService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaInternacaoController implements Initializable {

    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovoInternado;
    @FXML    private CheckBox ckbSomenteInternados;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private TableColumn<Internado, Integer> tableColumnDiarias;
    @FXML    private TableColumn<Internado, LocalDate> tableColumnDtAlta;
    @FXML    private TableColumn<Internado, LocalDate> tableColumnDtInternacao;
    @FXML    private TableColumn<Internado, Pet> tableColumnNome;
    @FXML    private TableColumn<Internado, Boolean> tableColumnInternado;
    @FXML    private TableColumn<Internado, Tutor> tableColumnTutor;
    @FXML    private TableColumn<Internado, Float> tableColumnValorDiaria;
    @FXML    private TableColumn<Internado, Float> tableColumnValorTotal;
    @FXML    private TableColumn<Internado, Veterinario> tableColumnVeterinario;
    @FXML    private TableView<Internado> tblInternacao;
    @FXML    private TextField txtBusca;
    
    
    private String txtFiltro = "";
    private int filtroSelecionado = -1;
    List<Internado> listaInternados = new InternacaoService().getAll(filtroSelecionado, txtFiltro);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnDiarias.setCellValueFactory(new PropertyValueFactory<>("NumeroDiarias"));
        tableColumnDtAlta.setCellValueFactory(new PropertyValueFactory<>("DtAlta"));
        tableColumnDtInternacao.setCellValueFactory(new PropertyValueFactory<>("DtInternacao"));
        tableColumnInternado.setCellValueFactory(new PropertyValueFactory<>("InternacaoAtiva"));
        tableColumnTutor.setCellValueFactory(new PropertyValueFactory<>("Tutor"));
        tableColumnValorDiaria.setCellValueFactory(new PropertyValueFactory<>("ValorDiaria"));
        tableColumnValorTotal.setCellValueFactory(new PropertyValueFactory<>("ValorTotal"));
        tableColumnVeterinario.setCellValueFactory(new PropertyValueFactory<>("Veterinario"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomePet"));
        
        tblInternacao.setRowFactory(
                new Callback<TableView<Internado>, TableRow<Internado>>() {
            @Override
            public TableRow<Internado> call(TableView<Internado> tableView) {
                final TableRow<Internado> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem novo = new MenuItem("Adicionar pet à internação");
                novo.setOnAction((t) -> {
                    new MenuPrincipal().cadastrarInternado(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                    filtrarLista();
                });

                MenuItem editItem = new MenuItem("Editar Internado");
                editItem.setOnAction((t) -> {
                    Internado internado = row.getItem();
                    new MenuPrincipal().editarInternado(internado, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                    filtrarLista();
                });

                MenuItem verPet = new MenuItem("Ver Cadastro do Pet");
                verPet.setOnAction((t) -> {
                    Pet pet = row.getItem().getPet();
                    new MenuPrincipal().editarPet(pet, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                    filtrarLista();
                });
                
                MenuItem removerDaSala = new MenuItem("Remover da Sala de Internação");
                removerDaSala.setOnAction((t) -> {
                    Internado internado = row.getItem();
                    internado.setInternacaoAtiva(false);
                    new InternacaoService().salvarOuAtualizarInternado(internado);
                    atualizaTabela(filtroSelecionado, txtFiltro);
                    filtrarLista();
                });

                MenuItem removeItem = new MenuItem("Excluir registro");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText(row.getItem().getNomePet() + " será excluído! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new InternacaoService().excluir(row.getItem())) {
                                Alert mens = new Alert(Alert.AlertType.INFORMATION);
                                mens.initOwner(btnFiltrar.getScene().getWindow());
                                mens.setTitle("Excluído");
                                mens.setContentText("Registro excluído com sucesso!");
                                mens.showAndWait();
                                atualizaTabela(filtroSelecionado, txtFiltro);
                                filtrarLista();
                            }
                        }
                    }
                });
                rowMenu.getItems().addAll(novo, editItem, removeItem, verPet, removerDaSala);

                // only display context menu for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(rowMenu));
                return row;
            }
        });
        
        tblInternacao.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Internado internado = tblInternacao.getSelectionModel().getSelectedItem();
                //criando a tela de Cadastro e passando Pet como parâmetro
                new MenuPrincipal().editarInternado(internado, btnFiltrar.getScene().getWindow());
                atualizaTabela(filtroSelecionado, txtFiltro);
                filtrarLista();
            }
        });
        
        btnNovoInternado.setOnAction((t) -> {
            new MenuPrincipal().cadastrarInternado(btnFiltrar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
            filtrarLista();
        });
        
        ckbSomenteInternados.selectedProperty().addListener((t, ov, nv) -> {
            tableColumnInternado.setVisible(ckbSomenteInternados.isSelected() ? false : true);
            filtrarLista();
        });
        ckbSomenteInternados.setSelected(true);
    }    
    
    private void atualizaTabela(int filtroSelecionado, String txtFiltro){
        // Buscar os dados no banco de dados na tabela atendimento
        listaInternados = new InternacaoService().getAll(filtroSelecionado, txtFiltro);
        
    }
    
    private void filtrarLista(){
        // ObservableList
        ObservableList<Internado> listaObs = FXCollections.observableArrayList(listaInternados);
        
        // Criar a lista filtrável
        FilteredList<Internado> listaObsFiltrada = new FilteredList<>(listaObs, p -> true);
        
        listaObsFiltrada.setPredicate(item -> {
            if(!ckbSomenteInternados.isSelected()){
                return true;
            }
            return item.isInternacaoAtiva();
        });
        
        //Vinculando a lista observável com a TableView
        tblInternacao.setItems(listaObsFiltrada);
        
        Utils.formatTableColumnDate(tableColumnDtAlta);
        Utils.formatTableColumnDate(tableColumnDtInternacao);
        Utils.formatTableColumnFloat(tableColumnValorTotal);
        Utils.formatTableColumnFloat(tableColumnValorDiaria);
    }
    
}
