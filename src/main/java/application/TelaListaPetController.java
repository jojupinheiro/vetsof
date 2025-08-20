package application;

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
import javafx.util.Callback;
import model.classes.Atendimento;
import model.classes.Pet;
import model.classes.Tutor;
import model.services.PetService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaListaPetController implements Initializable {

    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovoPet;
    @FXML    private ComboBox cmbFiltro;
    @FXML    private TableColumn<Pet, String> tableColumnNome;
    @FXML    private TableColumn<Pet, LocalDate> tableColumnDtNascimento;
    @FXML    private TableColumn<Pet, Boolean> tableColumnSexo;
    @FXML    private TableColumn<Pet, String> tableColumnRfid;
    @FXML    private TableColumn<Pet, String> tableColumnEspecie;
    @FXML    private TableColumn<Pet, Boolean> tableColumnCastrado;
    @FXML    private TableColumn<Pet, Boolean> tableColumnAdotado;
    @FXML    private TableColumn<Pet, Double> tableColumnPeso;
    @FXML    private TableColumn<Pet, String> tableColumnRaca;
    @FXML    private TableColumn<Pet, Tutor> tableColumnTutor;
    @FXML    private TableColumn<Pet, Boolean> tableColumnVivo;
    @FXML    private TableView<Pet> tblPets;
    @FXML    private TextField txtBusca;

    List<Pet> listaPet;
    private String txtFiltro;
    private int filtroSelecionado = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //configurar as colunas
        //Dizendo para o tableColumnCod que ele deve buscar o get dos atributos da classe Pet
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomePet"));
        tableColumnEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        tableColumnRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexoPet"));
        tableColumnTutor.setCellValueFactory(new PropertyValueFactory<>("NomeTutor"));
        tableColumnCastrado.setCellValueFactory(new PropertyValueFactory<>("castrado"));
        tableColumnDtNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimentoPet"));
        tableColumnPeso.setCellValueFactory(new PropertyValueFactory<>("pesoPet"));
        tableColumnRfid.setCellValueFactory(new PropertyValueFactory<>("rfid"));
        tableColumnVivo.setCellValueFactory(new PropertyValueFactory<>("vivo"));
        tableColumnAdotado.setCellValueFactory(new PropertyValueFactory<>("adotado"));

        tblPets.setRowFactory(
                new Callback<TableView<Pet>, TableRow<Pet>>() {
            @Override
            public TableRow<Pet> call(TableView<Pet> tableView) {
                final TableRow<Pet> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem novo = new MenuItem("Novo Pet");
                novo.setOnAction((t) -> {
                    new MenuPrincipal().cadastrarPet(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem editItem = new MenuItem("Editar Pet");
                editItem.setOnAction((t) -> {
                    Pet pet = row.getItem();
                    new MenuPrincipal().editarPet(pet, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem verTutor = new MenuItem("Ver Tutor");
                verTutor.setOnAction((t) -> {
                    Tutor tutor = row.getItem().getTutorPet();
                    new MenuPrincipal().editarTutor(tutor, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });
                
                MenuItem novoAtendimento = new MenuItem("Novo atendimento");
                novoAtendimento.setOnAction((t) -> {
                    Pet pet = row.getItem();
                    new MenuPrincipal().cadastrarAtendimentoDoPet(pet, btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem removeItem = new MenuItem("Excluir");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText(row.getItem().getNomePet() + " será excluído! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new PetService().excluir(row.getItem())) {
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
                rowMenu.getItems().addAll(novo, editItem, removeItem, verTutor, novoAtendimento);

                // only display context menu for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(rowMenu));
                return row;
            }
        });

        atualizaTabela(filtroSelecionado, txtFiltro);

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
        
        btnNovoPet.setOnAction((t) -> {
            new MenuPrincipal().cadastrarPet(btnLimpar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        //Abrindo a tela do pet com as informações setadas sobre o pet
        tblPets.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                Pet pet = tblPets.getSelectionModel().getSelectedItem();
                //criando a tela de Cadastro e passando Pet como parâmetro
                new MenuPrincipal().editarPet(pet, btnFiltrar.getScene().getWindow());
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });
        
        tblPets.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                Pet pet = tblPets.getSelectionModel().getSelectedItem();
                al.setContentText("O pet " + pet.getNomePet()+ " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    if (new PetService().excluir(pet)) {
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

        ObservableList<String> listaObs = FXCollections.observableArrayList("Nome", "Espécie", "Raça", "Sexo - Macho", "Sexo - Fêmea",
                "Tutor", "Castrado", "Não castrado", "Adotado", "Não adotado", "Nº chip", "Vivo", "Morto");
        cmbFiltro.setItems(listaObs);

        //Atualiza o filtro para o que for inserido no comboBox
//        try {
//            filtro = cmbFiltro.getValue().toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            filtro = "";
//        }
        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                atualizaTabela(filtroSelecionado, txtFiltro);
            } catch (Exception e) {
                e.printStackTrace();
                filtroSelecionado = 0;
            }
        });
    }

    public String getBusca() {
        return txtFiltro;
    }

    public void setBusca(String busca) {
        this.txtFiltro = txtFiltro;
    }

    public int getFiltro() {
        return filtroSelecionado;
    }

    public void setFiltro(String filtro) {
        this.filtroSelecionado = filtroSelecionado;
    }

    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        // Buscar os dados no banco de dados na tabela pet
        listaPet = new PetService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<Pet> listaObs = FXCollections.observableArrayList(listaPet);
        //Vinculando a lista observável com a TableView
        tblPets.setItems(listaObs);
        Utils.formatTableColumnDate(tableColumnDtNascimento);
        Utils.formatTableColumnPeso(tableColumnPeso);
    }
    
    public void filtrarPorTutor(Tutor tutor){
        txtBusca.setText(tutor.getNome());
        cmbFiltro.getSelectionModel().select(5);
        atualizaTabela(5, tutor.getNome());
    }
}
