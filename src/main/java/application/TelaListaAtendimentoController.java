package application;

import application.Principal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.classes.Atendimento;
import model.classes.Clinica;
import model.classes.Pet;
import model.classes.Tutor;
import model.classes.Vacina;
import model.services.AtendimentoService;
import model.services.VacinaService;
import view.utils.Utils;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.CheckBox;
import view.utils.MascarasFX;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaListaAtendimentoController implements Initializable {

    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnNovoAtendimento;
    @FXML    private CheckBox ckbExames;
    @FXML    private CheckBox ckbServicos;
    @FXML    private CheckBox ckbVacinas;
    @FXML    private ComboBox cmbFiltro;
    @FXML    private DatePicker dpDataFim;
    @FXML    private DatePicker dpDataInicio;
    @FXML    private HBox boxDatas;
    @FXML    private TableColumn<Atendimento, Clinica> tableColumnClinica;
    @FXML    private TableColumn<Atendimento, Tutor> tableColumnTutor;
    @FXML    private TableColumn<Atendimento, LocalDate> tableColumnData;
    @FXML    private TableColumn<Atendimento, LocalTime> tableColumnHorario;
    @FXML    private TableColumn<Atendimento, List> tableColumnExame;
    @FXML    private TableColumn<Atendimento, Pet> tableColumnPet;
    @FXML    private TableColumn<Atendimento, Integer> tableColumnProtocolo;
    @FXML    private TableColumn<Atendimento, List> tableColumnServico;
    @FXML    private TableColumn<Atendimento, Float> tableColumnValor;
    @FXML    private TableColumn<Atendimento, List> tableColumnVacinas;
    @FXML    private TableView<Atendimento> tblAtendimento;
    @FXML    private TextField txtBusca;

    List<Atendimento> listaAtendimento;
    private String txtFiltro;
    private int filtroSelecionado = -1;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraData(dpDataFim);
        MascarasFX.mascaraData(dpDataInicio);
        
        //configurar as colunas
        //Dizendo para o tableColumnCod que ele deve buscar o get dos atributos da classe Atendimento
        tableColumnProtocolo.setCellValueFactory(new PropertyValueFactory<>("idAtendimento"));
        tableColumnPet.setCellValueFactory(new PropertyValueFactory<>("pet"));
        tableColumnTutor.setCellValueFactory(new PropertyValueFactory<>("tutor"));
        tableColumnClinica.setCellValueFactory(new PropertyValueFactory<>("clinica"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataAtendimento"));
        tableColumnHorario.setCellValueFactory(new PropertyValueFactory<>("horarioAtendimento"));
        tableColumnValor.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tableColumnExame.setCellValueFactory(new PropertyValueFactory<>("listaExames"));
        tableColumnServico.setCellValueFactory(new PropertyValueFactory<>("listaServico"));
        tableColumnVacinas.setCellValueFactory(new PropertyValueFactory<>("ListaVacinas"));

        tblAtendimento.setRowFactory(
                new Callback<TableView<Atendimento>, TableRow<Atendimento>>() {
            @Override
            public TableRow<Atendimento> call(TableView<Atendimento> tableView) {
                final TableRow<Atendimento> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();

                MenuItem novo = new MenuItem("Novo atendimento");
                novo.setOnAction((t) -> {
                    new MenuPrincipal().cadastrarAtendimento(btnFiltrar.getScene().getWindow());
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem editItem = new MenuItem("Editar atendimento");
                editItem.setOnAction((t) -> {
                    Atendimento atendimento = row.getItem();
                    Window janela = btnFiltrar.getScene().getWindow();
                    List<Vacina> listaVacinas = new VacinaService().getVacinasDoAtendimento(atendimento);
                    new MenuPrincipal().editarAtendimento(atendimento, listaVacinas, janela);
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem verTutor = new MenuItem("Ver Tutor");
                verTutor.setOnAction((t) -> {
                    Tutor tutor = row.getItem().getPet().getTutorPet();
                    Window janela = btnFiltrar.getScene().getWindow();
                    new MenuPrincipal().editarTutor(tutor, janela);
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem verPet = new MenuItem("Ver Pet");
                verPet.setOnAction((t) -> {
                    Pet pet = row.getItem().getPet();
                    Window janela = btnFiltrar.getScene().getWindow();
                    new MenuPrincipal().editarPet(pet, janela);
                    atualizaTabela(filtroSelecionado, txtFiltro);
                });

                MenuItem removeItem = new MenuItem("Excluir atendimento");
                removeItem.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                        al.setTitle("Confirmação");
                        al.setContentText("O atendimento de " + row.getItem().getPet().getNomePet() + " será excluído! Tem certeza?");
                        if (al.showAndWait().get() == ButtonType.OK) {
                            if (new AtendimentoService().excluir(row.getItem())) {
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
                rowMenu.getItems().addAll(novo, editItem, removeItem, verTutor, verPet);

                // only display context menu for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(rowMenu));
                return row;
            }
        });

        atualizaTabela(filtroSelecionado, txtFiltro);
        
        btnNovoAtendimento.setOnAction((t) -> {
            new MenuPrincipal().cadastrarAtendimento(btnFiltrar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        btnLimpar.setOnAction((t) -> {
            cmbFiltro.getSelectionModel().select(-1);
            txtBusca.setText("");
            dpDataFim.setValue(null);
            dpDataInicio.setValue(null);
        });

        btnFiltrar.setOnAction((t) -> {
            if (filtroSelecionado == 4){
                String dtInicial = dpDataInicio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String dtFinal = dpDataFim.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtFiltro = dtInicial + " " + dtFinal;
            }else{
                txtFiltro = txtBusca.getText();
            }
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        txtBusca.setOnAction((t) -> {
            if (filtroSelecionado != 4 && filtroSelecionado != -1){
                txtFiltro = txtBusca.getText();
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });

        //Abrindo a tela do atendimento com as informações setadas sobre o atendimento
        tblAtendimento.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && tblAtendimento.getSelectionModel().getSelectedItem() != null) {
                Atendimento atendimento = tblAtendimento.getSelectionModel().getSelectedItem();
                //criando a tela de Cadastro e passando Pet como parâmetro
                Window janela = btnFiltrar.getScene().getWindow();
                List<Vacina> listaVacinas = new VacinaService().getVacinasDoAtendimento(atendimento);
                new MenuPrincipal().editarAtendimento(atendimento, listaVacinas, janela);
                atualizaTabela(filtroSelecionado, txtFiltro);
            }
        });

        //Funçao excluir acionada pela tecla DELETE
        tblAtendimento.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                Atendimento atendimento = tblAtendimento.getSelectionModel().getSelectedItem();
                al.setContentText("O atendimento de " + atendimento.getPet().getNomePet() + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    if (new AtendimentoService().excluir(atendimento)) {
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
        
        ckbExames.selectedProperty().addListener((t, ov, nv) -> tableColumnExame.setVisible(nv));
        ckbServicos.selectedProperty().addListener((t, ov, nv) -> tableColumnServico.setVisible(nv));
        ckbVacinas.selectedProperty().addListener((t, ov, nv) -> tableColumnVacinas.setVisible(nv));
        tableColumnExame.setVisible(false);
        tableColumnServico.setVisible(false);
        tableColumnVacinas.setVisible(false);
        
        
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("Protocolo", "Tutor", "Pet", "Clínica", 
                "Data", "CPF do tutor", "Nº do chip do pet");
        cmbFiltro.setItems(listaObs);

        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                if (filtroSelecionado == 4) {
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
        boxDatas.setVisible(false);
        boxDatas.setManaged(false);
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
        // Buscar os dados no banco de dados na tabela atendimento
        listaAtendimento = new AtendimentoService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<Atendimento> listaObs = FXCollections.observableArrayList(listaAtendimento);
        //Vinculando a lista observável com a TableView
        tblAtendimento.setItems(listaObs);
        Utils.formatTableColumnDate(tableColumnData);
        Utils.formatTableColumnFloat(tableColumnValor);
        Utils.formatTableColumnList(tableColumnExame);
        Utils.formatTableColumnList(tableColumnServico);
        Utils.formatTableColumnList(tableColumnVacinas);
    }

    public void filtrarPorPet(Pet pet) {
        txtBusca.setText(String.valueOf(pet.getIdPet()));
        cmbFiltro.getSelectionModel().select(8);
        atualizaTabela(8, String.valueOf(pet.getIdPet()));
    }
}
