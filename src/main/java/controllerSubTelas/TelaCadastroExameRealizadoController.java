package controllerSubTelas;

import application.MenuPrincipal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.classes.DiariaInternacao;
import model.classes.Exame;
import model.classes.ExameRealizado;
import model.classes.Internado;
import model.exceptions.ValidacaoException;
import model.services.ExameService;
import org.controlsfx.control.SearchableComboBox;
import view.utils.MascarasFX;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroExameRealizadoController implements Initializable {
   
    @FXML    private Button btnCadastrarExame;
    @FXML    private Button btnFechar;
    @FXML    private Button btnInserirExame;
    @FXML    private Button btnLimparExame;
    @FXML    private Button btnRemoverExame;
    @FXML    private Label lblDados;
    @FXML    private Label lblErroExame;
    @FXML    private ListView<ExameRealizado> listViewExames;
    @FXML    private SearchableComboBox<Exame> scmbExame;
    @FXML    private TextArea txtObservacaoExame;
    @FXML    private TextArea txtResultadoExame;
    @FXML    private TextField txtValorExame;
    
    private List<ExameRealizado> listaExamesSelecionados = new ArrayList<>();   
    private Internado internado;
    private List<Exame> listaExames = new ExameService().getAll();
//    ObservableList<ExameRealizado> examesDestino;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumero(txtValorExame);
        
        btnInserirExame.setOnAction((t) -> new MenuPrincipal().inserirExame(btnLimparExame.getScene().getWindow()));
        
        btnCadastrarExame.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                
                float valorExameSelecionado = 0;
                if (!txtValorExame.getText().equals("")){
                    valorExameSelecionado = Float.parseFloat(txtValorExame.getText());
                }
                String observacoesExameSelecionado = txtObservacaoExame.getText().trim();
                String ResultadoExame = txtResultadoExame.getText();
                
                //Atributos obrigatórios
                Exame exame = null;
                
                if(scmbExame.getValue() != null){
                    exame = scmbExame.getValue();
                }else{
                    exc.adicionarErro("Exame", "Selecione um exame para cadastrar!");
                }

                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                ExameRealizado exameRealizado = new ExameRealizado(exame, valorExameSelecionado, observacoesExameSelecionado, ResultadoExame);
                exameRealizado.setPet(internado.getPet());
                listaExamesSelecionados.add(exameRealizado);
                
                ObservableList<ExameRealizado> listaObsExamSel = FXCollections.observableArrayList(listaExamesSelecionados);
                listViewExames.setItems(listaObsExamSel);
                
                //Resetando os campos para o estado original
                scmbExame.getSelectionModel().select(-1);
                txtValorExame.setText("");
                txtObservacaoExame.setText("");
                txtResultadoExame.setText("");

            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
        });
        
        scmbExame.setOnAction((t) -> {
            if (scmbExame.getSelectionModel().getSelectedIndex() != -1){
                txtValorExame.setText(String.valueOf(scmbExame.getValue().getValorExame()));
            }
        });
        
        btnLimparExame.setOnAction((t) -> {
            listaExamesSelecionados.clear();
            listarExamesSelecionados();
        });
        
        btnRemoverExame.setOnAction((t) -> {
            if (listViewExames.getSelectionModel().getSelectedIndex() >= 0) {
                listaExamesSelecionados.remove(listViewExames.getSelectionModel().getSelectedItem());
                listarExamesSelecionados();
            }
            btnRemoverExame.setVisible(false);
        });
        
        // Personalizando as células do ComboBox para exibir a Tooltip
        scmbExame.setCellFactory(cb -> new ListCell<Exame>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Exame exame, boolean empty) {
                super.updateItem(exame, empty);
                if (empty || exame == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(exame.getNomeExame() + " (R$ " + Utils.imprimeValor(String.valueOf(exame.getValorExame())) + ")");
                    tooltip.setText(exame.getDescricaoExame());
                    setTooltip(tooltip);
                }
            }
        });

        // Adicionar Tooltip também para o item selecionado no ComboBox
        scmbExame.setButtonCell(new ListCell<Exame>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Exame exame, boolean empty) {
                super.updateItem(exame, empty);
                if (empty || exame == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(exame.getNomeExame() + " (R$ " + Utils.imprimeValor(String.valueOf(exame.getValorExame())) + ")");
                    tooltip.setText(exame.getDescricaoExame());
                    setTooltip(tooltip);
                }
            }
        });
        
        //Configuração de como a lista de exames será formatada
        listViewExames.setCellFactory(lv -> new ListCell<ExameRealizado>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(ExameRealizado exame, boolean empty) {
                super.updateItem(exame, empty);
                if (empty || exame == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(exame.getExame().getNomeExame() + " (R$ " + Utils.imprimeValor(String.valueOf(exame.getValor())) + ")");
                    tooltip.setText(exame.getObservacao());
                    setTooltip(tooltip);
                }
            }
        });
        
        listViewExames.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                ExameRealizado exameRealizado = listViewExames.getSelectionModel().getSelectedItem();
                txtObservacaoExame.setText(exameRealizado.getObservacao());
                txtValorExame.setText(String.valueOf(exameRealizado.getValor()));
                txtResultadoExame.setText(exameRealizado.getResultado());
                scmbExame.setValue(exameRealizado.getExame());
            }
        });
        
        listViewExames.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnRemoverExame.setVisible(true);
        });

        btnRemoverExame.setVisible(false);
        listarExamesCadastrados();
        
        
    }
    
    private void listarExamesSelecionados() {
        ObservableList<ExameRealizado> listaObsExamSel = FXCollections.observableArrayList(listaExamesSelecionados);                                                                             
        listViewExames.setItems(listaObsExamSel);                                                                                                                                        
    }    
    
    private void listarExamesCadastrados() {
        listaExames = new ExameService().getAll();
        ObservableList<Exame> listaObsExames = FXCollections.observableArrayList(listaExames);                                                                        
        scmbExame.setItems(listaObsExames);

        scmbExame.setCellFactory(param -> new ListCell<Exame>() {
            @Override
            protected void updateItem(Exame item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeExame());
            }
        });

        scmbExame.setButtonCell(new ListCell<Exame>() {
            @Override
            protected void updateItem(Exame item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getNomeExame());
                }
            }                                                                                                                                                                           
        });
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroExame.setText(campos.contains("Exame") ? errors.get("Exame") : "");
    }
    
    public void ajustarTela(Internado internado, DiariaInternacao diaria, ObservableList<ExameRealizado> examesDestino){
        this.internado = internado;
        lblDados.setText("Animal: " + internado.getNomePet() + ", " + diaria.getStringData());
        ObservableList<ExameRealizado> examesRealizados = FXCollections.observableArrayList();
        listViewExames.setItems(examesRealizados);
        listaExamesSelecionados = examesDestino;
        listarExamesSelecionados();
        
        btnFechar.setOnAction(e -> {
            examesDestino.addAll(examesRealizados);
            ((Stage) btnFechar.getScene().getWindow()).close();
        });

        // Garante que os exames sejam transferidos mesmo que o usuário feche pelo "X"
        btnFechar.getScene().getWindow().setOnCloseRequest(e -> examesDestino.addAll(examesRealizados));
    }
    
    public ObservableList<ExameRealizado> getLista(){
        ObservableList<ExameRealizado> listaObsExamSel = FXCollections.observableArrayList(listaExamesSelecionados); 
        return listaObsExamSel;
    }

}
