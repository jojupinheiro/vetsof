package controllerSubTelas;

import application.MenuPrincipal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.classes.DiariaInternacao;
import model.classes.ExameRealizado;
import model.classes.Internado;
import model.classes.Servico;
import model.classes.ServicoRealizado;
import model.exceptions.ValidacaoException;
import model.services.ServicoService;
import org.controlsfx.control.SearchableComboBox;
import view.utils.MascarasFX;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroServicoRealizadoController implements Initializable {
   
    @FXML    private Button btnCadastrarServico;
    @FXML    private Button btnFechar;
    @FXML    private Button btnInserirServico;
    @FXML    private Button btnLimparServico;
    @FXML    private Button btnRemoverServico;
    @FXML    private Label lblDados;
    @FXML    private Label lblErroServico;
    @FXML    private ListView<ServicoRealizado> listViewServicos;
    @FXML    private SearchableComboBox<Servico> scmbServico;
    @FXML    private Spinner<Integer> spnQuantidadeServico;
    @FXML    private TextArea txtObservacaoServico;
    @FXML    private TextField txtValorServico;
    
    private List<ServicoRealizado> listaServicosSelecionados = new ArrayList<>();
    private List<Servico> listaServicos = new ServicoService().getAll();
    private Internado internado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumero(txtValorServico);
        
        btnInserirServico.setOnAction((t) -> new MenuPrincipal().inserirServico(btnLimparServico.getScene().getWindow()));

        btnCadastrarServico.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                
                float valorServicoSelecionado = 0;
                if (!txtValorServico.getText().equals("")){
                    valorServicoSelecionado = Float.parseFloat(txtValorServico.getText());
                }
                String observacoesServicoSelecionado = txtObservacaoServico.getText().trim();
                
                //Atributos obrigatórios
                Servico servico = null;
                
                if(scmbServico.getValue() != null){
                    servico = scmbServico.getValue();
                }else{
                    exc.adicionarErro("Servico", "Selecione um servico para cadastrar!");
                }

                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                int qtdServicoRealizado = spnQuantidadeServico.getValue();
                
                ServicoRealizado servicoRealizado = new ServicoRealizado(servico, valorServicoSelecionado, observacoesServicoSelecionado, qtdServicoRealizado);
                servicoRealizado.setPet(internado.getPet());
                listaServicosSelecionados.add(servicoRealizado);
                
                ObservableList<ServicoRealizado> listaObsExamSel = FXCollections.observableArrayList(listaServicosSelecionados);
                listViewServicos.setItems(listaObsExamSel);
                
                //Resetando os campos para o estado original
                scmbServico.getSelectionModel().select(-1);
                txtValorServico.setText("");
                txtObservacaoServico.setText("");
                spnQuantidadeServico.getValueFactory().setValue(1);

            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
        });
        
        //Configuração da formatação do spinner que seleciona a quantidade de servico------------------------------------
        SpinnerValueFactory<Integer> valueFactoryQtdServico = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30);
        valueFactoryQtdServico.setValue(1);
        spnQuantidadeServico.setValueFactory(valueFactoryQtdServico);
        
        scmbServico.setOnAction((t) -> {
            if (scmbServico.getSelectionModel().getSelectedIndex() != -1){
                txtValorServico.setText(String.valueOf(scmbServico.getValue().getValorServico()));
            }
        });
        
        btnLimparServico.setOnAction((t) -> {
            listaServicosSelecionados.clear();
            listarServicosSelecionados();
        });
        
        btnRemoverServico.setOnAction((t) -> {
            if (listViewServicos.getSelectionModel().getSelectedIndex() >= 0) {
                listaServicosSelecionados.remove(listViewServicos.getSelectionModel().getSelectedItem());
                listarServicosSelecionados();
            }
            btnRemoverServico.setVisible(false);
        });
        
        // Personalizando as células do ComboBox para exibir a Tooltip
        scmbServico.setCellFactory(cb -> new ListCell<Servico>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Servico servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(servico.getNomeServico() + " (R$ " + Utils.imprimeValor(String.valueOf(servico.getValorServico())) + ")");
                    tooltip.setText(servico.getDescricaoServico());
                    setTooltip(tooltip);
                }
            }
        });

        // Adicionar Tooltip também para o item selecionado no ComboBox
        scmbServico.setButtonCell(new ListCell<Servico>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(Servico servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(servico.getNomeServico() + " (R$ " + Utils.imprimeValor(String.valueOf(servico.getValorServico())) + ")");
                    tooltip.setText(servico.getDescricaoServico());
                    setTooltip(tooltip);
                }
            }
        });
        
        //Configuração de como a lista de serviços será formatada
        listViewServicos.setCellFactory(lv -> new ListCell<ServicoRealizado>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(ServicoRealizado servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(servico.getServico().getNomeServico()+ " (R$ " + Utils.imprimeValor(String.valueOf(servico.getValor())) + ")");
                    tooltip.setText(servico.getObservacao());
                    setTooltip(tooltip);
                }
            }
        });
        
        listViewServicos.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                ServicoRealizado servicoRealizado = listViewServicos.getSelectionModel().getSelectedItem();
                txtObservacaoServico.setText(servicoRealizado.getObservacao());
                txtValorServico.setText(String.valueOf(servicoRealizado.getValor()));
                scmbServico.setValue(servicoRealizado.getServico());
                spnQuantidadeServico.getValueFactory().setValue(servicoRealizado.getQuantidade());
            }
        });
        
        listViewServicos.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            btnRemoverServico.setVisible(true);
        });

        btnRemoverServico.setVisible(false);
        listarServicosCadastrados();
    }    
    
    private void listarServicosCadastrados() {
        listaServicos = new ServicoService().getAll();
        ObservableList<Servico> listaObsServicos = FXCollections.observableArrayList(listaServicos);                                                                        
        scmbServico.setItems(listaObsServicos);

        scmbServico.setCellFactory(param -> new ListCell<Servico>() {
            @Override
            protected void updateItem(Servico item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeServico());
            }
        });

        scmbServico.setButtonCell(new ListCell<Servico>() {
            @Override
            protected void updateItem(Servico item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getNomeServico());
                }
            }                                                                                                                                                                           
        });
    }
    
    private void listarServicosSelecionados() {
        ObservableList<ServicoRealizado> listaObsServSel = FXCollections.observableArrayList(listaServicosSelecionados);                                                                             
        listViewServicos.setItems(listaObsServSel);                                                                                                                                        
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroServico.setText(campos.contains("Servico") ? errors.get("Servico") : "");
    }

    public void ajustarTela(Internado internado, DiariaInternacao diaria, ObservableList<ServicoRealizado> servicosDestino){
        this.internado = internado;
        lblDados.setText("Animal: " + internado.getNomePet() + ", " + diaria.getStringData());
        ObservableList<ServicoRealizado> servicosRealizados = FXCollections.observableArrayList();
        listViewServicos.setItems(servicosRealizados);
        listaServicosSelecionados = servicosDestino;
        listarServicosSelecionados();
        
        btnFechar.setOnAction(e -> {
            servicosDestino.addAll(servicosRealizados);
            ((Stage) btnFechar.getScene().getWindow()).close();
        });

        // Garante que os exames sejam transferidos mesmo que o usuário feche pelo "X"
        btnFechar.getScene().getWindow().setOnCloseRequest(e -> servicosDestino.addAll(servicosRealizados));
    }
    
    public ObservableList<ServicoRealizado> getLista(){
        ObservableList<ServicoRealizado> listaObsServSel = FXCollections.observableArrayList(listaServicosSelecionados); 
        return listaObsServSel;
    }
    
}
