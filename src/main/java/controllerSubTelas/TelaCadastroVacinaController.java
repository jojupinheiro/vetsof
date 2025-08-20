
package controllerSubTelas;

import application.MenuPrincipal;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.classes.DiariaInternacao;
import model.classes.Internado;
import model.classes.ProdutoVacina;
import model.classes.ServicoRealizado;
import model.classes.Vacina;
import model.exceptions.ValidacaoException;
import model.services.ProdutoVacinaService;
import org.controlsfx.control.SearchableComboBox;
import view.utils.MascarasFX;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroVacinaController implements Initializable {
   
    @FXML    private HBox boxDataReforco;
    @FXML    private ToggleButton btn1Ano;
    @FXML    private ToggleButton btn21Dias;
    @FXML    private ToggleButton btn30Dias;
    @FXML    private ToggleButton btn45Dias;
    @FXML    private ToggleButton btn6Meses;
    @FXML    private Button btnCadastrarVacina;
    @FXML    private Button btnFechar;
    @FXML    private Button btnInserirNomeVacina;
    @FXML    private Button btnInserirTipoVacina;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnLimparVacina;
    @FXML    private Button btnRemoverVacina;
    @FXML    private DatePicker dpDtReforco;
    @FXML    private DatePicker dpDtVacina;
    @FXML    private Label lblDados;
    @FXML    private Label lblErroDtVacina;
    @FXML    private Label lblErroDtVacinaFutura;
    @FXML    private Label lblErroDtVacinaPassada;
    @FXML    private Label lblErroStatusVacina;
    @FXML    private Label lblErroTipoVacina;
    @FXML    private Label lblErroVacina;
    @FXML    private Label lblProgramadaOuProximaDose;
    @FXML    private Label lblProximaDose;
    @FXML    private ListView<Vacina> listViewVacinas;
    @FXML    private RadioButton rbAplicada;
    @FXML    private RadioButton rbProgramada;
    @FXML    private RadioButton rbProximaDoseNao;
    @FXML    private RadioButton rbProximaDoseSim;
    @FXML    private SearchableComboBox<ProdutoVacina> scmbCategoriaVacina;
    @FXML    private SearchableComboBox<ProdutoVacina> scmbVacina;
    @FXML    private Spinner<Integer> spnDoseAtual;
    @FXML    private Spinner<Integer> spnDosesTotais;
    @FXML    private TextArea txtObservacaoVacina;
    @FXML    private TextField txtValorVacina;
    
    private List<Vacina> listaVacinas;
    private List<Vacina> listaVacinasSelecionadas = new ArrayList<>();
    private List<ProdutoVacina> listaTiposVacina = new ArrayList<>();
    private List<ProdutoVacina> listaNomesVacina = new ArrayList<>();
    private Internado internado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumero(txtValorVacina);
        MascarasFX.mascaraData(dpDtVacina);
        MascarasFX.mascaraData(dpDtReforco);
        
        dpDtVacina.setValue(LocalDate.now());
        
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
                    vacina = new Vacina(produtoVacina, dtVacina, aplicada, observacaoVacina, doseAtual, dosesTotais, temProximaDose, dtProximaDose, valorVacinaCadastrada);
                    
                }else{
                    vacina = new Vacina(produtoVacina, dtVacina, aplicada, observacaoVacina, doseAtual, dosesTotais, temProximaDose, dtProximaDose, valorVacinaCadastrada);
                }
                
                vacina.setPet(internado.getPet());
                
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

                //----------------------------------------------------------------------
                
            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
        });

        btnLimparVacina.setOnAction((t) -> {
            listaVacinasSelecionadas.clear();
            listarVacinasSelecionadas();
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

    }    
    
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
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroTipoVacina.setText(campos.contains("CategoriaVacina") ? errors.get("CategoriaVacina") : "");
        lblErroVacina.setText(campos.contains("Vacina") ? errors.get("Vacina") : "");
        lblErroDtVacina.setText(campos.contains("DataVacina") ? errors.get("DataVacina") : "");
        lblErroDtVacinaFutura.setText(campos.contains("DataFutura") ? errors.get("DataFutura") : "");
        lblErroDtVacinaPassada.setText(campos.contains("DataPassada") ? errors.get("DataPassada") : "");
        lblErroStatusVacina.setText(campos.contains("StatusVacina") ? errors.get("StatusVacina") : "");
    }

    public void ajustarTela(Internado internado, DiariaInternacao diaria, ObservableList<Vacina> vacinasDestino){
        this.internado = internado;
        lblDados.setText("Animal: " + internado.getNomePet() + ", " + diaria.getStringData());
        ObservableList<Vacina> VacinasRealizadas = FXCollections.observableArrayList();
        listViewVacinas.setItems(VacinasRealizadas);
        listaVacinasSelecionadas = vacinasDestino;
        listarVacinasSelecionadas();
        dpDtVacina.setValue(diaria.getData());
        
        btnFechar.setOnAction(e -> {
            vacinasDestino.addAll(VacinasRealizadas);
            ((Stage) btnFechar.getScene().getWindow()).close();
        });

        // Garante que os exames sejam transferidos mesmo que o usuário feche pelo "X"
        btnFechar.getScene().getWindow().setOnCloseRequest(e -> vacinasDestino.addAll(VacinasRealizadas));
    }
    
    public ObservableList<Vacina> getLista(){
        ObservableList<Vacina> listaObsVacSel = FXCollections.observableArrayList(listaVacinasSelecionadas); 
        return listaObsVacSel;
    }
    
}
