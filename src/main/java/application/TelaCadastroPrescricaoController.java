package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.classes.Atendimento;
import model.classes.Clinica;
import model.classes.ExameRealizado;
import model.classes.Internado;
import model.classes.Pet;
import model.classes.prescricoes.Prescricao;
import model.classes.prescricoes.ProdutoPrescrito;
import model.classes.Tutor;
import model.classes.Veterinario;
import model.exceptions.ValidacaoException;
import model.services.ClinicaService;
import model.services.PetService;
import model.services.PrescricaoService;
import model.services.TutorService;
import pdf.ImpressaoPdf;
import view.utils.MascarasFX;
import view.utils.Utils;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroPrescricaoController extends MenuPrincipal implements Initializable {

    @FXML    private Button btnAdicionar;
    @FXML    private Button btnAdicionarClinica;
    @FXML    private Button btnAdicionarPet;
    @FXML    private Button btnAdicionarTutor;
    @FXML    private Button btnAdicionarVeterinario;
    @FXML    private Button btnCalcular;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnImprimir;
    @FXML    private Button btnLimparLista;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnRemoverProduto;
    @FXML    private CheckBox ckbHumano;
    @FXML    private CheckBox ckbVeterinario;
    @FXML    private ComboBox<String> cmbFormaUso;
    @FXML    private ComboBox<Clinica> cmbClinica;
    @FXML    private DatePicker dpData;
    @FXML    private Label lblDoseFinal;
    @FXML    private Label lblErroClinica;
    @FXML    private Label lblErroData;
    @FXML    private Label lblErroPet;
    @FXML    private Label lblErroTutor;
    @FXML    private Label lblErroVeterinario;
    @FXML    private Label lblPeso;
    @FXML    private ListView<ProdutoPrescrito> listViewProdutosInseridos;
    @FXML    private SearchableComboBox<Pet> scmbPet;
    @FXML    private SearchableComboBox<Tutor> scmbTutor;
    @FXML    private SearchableComboBox<Veterinario> scmbVeterinario;
    @FXML    private TextArea txtObservacoes;
    @FXML    private TextArea txtPosologia;
    @FXML    private TextField txtDoseDesejada;
    @FXML    private TextField txtPeso;
    @FXML    private TextField txtProduto;
    @FXML    private TextField txtQuantidade;

    private Prescricao prescricao;
    private Atendimento atendimento;
    private List<ProdutoPrescrito> listaProdutosReceitados = new ArrayList<>();
    private List<Pet> listaPets = new PetService().getAll(-1, "");
    private List<Veterinario> listaVeterinarios;
    private List<Tutor> listaTutores;
    private List<Clinica> listaClinicas;
    private Clinica clinica = new ClinicaService().getClinicaPrincipal();

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
        listarTutores();
        scmbTutor.getSelectionModel().select(atendimento.getTutor());
        listarPets();
        scmbPet.getSelectionModel().select(atendimento.getPet());
        listarClinicas();
        cmbClinica.getSelectionModel().select(atendimento.getClinica());
        listarVeterinarios();
        scmbVeterinario.getSelectionModel().select(atendimento.getVeterinario());
        dpData.setValue(atendimento.getDataAtendimento());
        scmbPet.setDisable(false);
        scmbVeterinario.setDisable(false);
        txtPeso.setText(atendimento.getPet().getPesoPet() + "");
    }
    
    public void setInternado(Internado internado){
        scmbTutor.getSelectionModel().select(internado.getPet().getTutorPet());
        listarPets();
        scmbPet.getSelectionModel().select(internado.getPet());
        scmbVeterinario.getSelectionModel().select(internado.getVeterinario());
        txtPeso.setText(internado.getPet().getPesoPet() + "");
        scmbPet.setDisable(false);
        scmbVeterinario.setDisable(false);
    }
    
    public void setPrescricao(Prescricao prescricao){
        this.prescricao = prescricao;
        txtObservacoes.setText(prescricao.getObservacoes());
        dpData.setValue(prescricao.getData());
        listarTutores();
        scmbTutor.getSelectionModel().select(prescricao.getPet().getTutorPet());
        listarPets();
        scmbPet.getSelectionModel().select(prescricao.getPet());
        listarClinicas();
        cmbClinica.getSelectionModel().select(prescricao.getClinica());
        listarVeterinarios();
        scmbVeterinario.getSelectionModel().select(prescricao.getVeterinario());
        dpData.setValue(prescricao.getData());
        scmbPet.setDisable(false);
        scmbVeterinario.setDisable(false);
        
        Map<String, Map<String, String[]>> mapProdutos = new PrescricaoService().getProdutosDaPrescricao(prescricao.getId());
        Set<String> formasDeUso = mapProdutos.keySet();
        for(String forma : formasDeUso){
           Set<String> setProdutos = mapProdutos.get(forma).keySet();
           for(String produto : setProdutos){
               String[] atributosProduto = new String[2];
               atributosProduto = mapProdutos.get(forma).get(produto);
               String quantidadeProduto = atributosProduto[0];
               String posologiaProduto = atributosProduto[1];
               
               ProdutoPrescrito produtoPrescrito = new ProdutoPrescrito(forma, produto, quantidadeProduto, posologiaProduto);
               listaProdutosReceitados.add(produtoPrescrito);
           }
        }
        
        listarProdutos();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraData(dpData);
        MascarasFX.mascaraNumero(txtPeso);
        MascarasFX.mascaraNumero(txtDoseDesejada);
        
        //Tooltips
        Tooltip dicaAdicionarVeterinario = new Tooltip("Cadastrar novo veterinário");
        dicaAdicionarVeterinario.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarVeterinario.setTooltip(dicaAdicionarVeterinario);
        
        Tooltip dicaAdicionarTutor = new Tooltip("Cadastrar novo tutor");
        dicaAdicionarTutor.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarTutor.setTooltip(dicaAdicionarTutor);
        
        Tooltip dicaAdicionarPet = new Tooltip("Cadastrar novo pet");
        dicaAdicionarPet.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarPet.setTooltip(dicaAdicionarPet);
        
        Tooltip dicaAdicionarClinica = new Tooltip("Cadastrar nova clínica");
        dicaAdicionarClinica.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionarClinica.setTooltip(dicaAdicionarClinica);
        
        Tooltip dicaAdicionarNaPrescricao = new Tooltip("Adiciona o produto informado na prescrição");
        dicaAdicionarNaPrescricao.setShowDelay(javafx.util.Duration.ZERO);
        btnAdicionar.setTooltip(dicaAdicionarNaPrescricao);

        // Define para onde vai o cursor ao se pressionar TAB nas caixas de texto----------------------------------
        cmbFormaUso.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtProduto.requestFocus();
            }
        });
        txtProduto.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                ckbHumano.requestFocus();
            }
        });
        ckbHumano.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                ckbVeterinario.requestFocus();
            }
        });
        ckbVeterinario.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtQuantidade.requestFocus();
            }
        });
        txtQuantidade.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtPosologia.requestFocus();
            }
        });
        txtPosologia.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                btnAdicionar.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                adicionarProduto();
            }
        });
        btnAdicionar.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtObservacoes.requestFocus();
            }
        });
        txtObservacoes.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                btnImprimir.requestFocus();
            }
        });
        cmbFormaUso.setOnAction((t) -> {
            txtProduto.requestFocus();
        });
        //----------------------------------------------------------------------------------------------

        String farmaciaHumana = " (Farmácia humana)";
        String farmaciaVeterinaria = " (Farmácia veterinária)";
        
        ckbHumano.setOnAction((t) -> {
            String string = txtProduto.getText();
            String novaString;
            if (ckbHumano.isSelected()){
                ckbVeterinario.setSelected(false);
                if (string.contains(farmaciaVeterinaria)) {
                    novaString = string.substring(0, string.length() - farmaciaVeterinaria.length());
                    txtProduto.setText(novaString);
                }
                txtProduto.setText(txtProduto.getText() + farmaciaHumana);
                txtQuantidade.requestFocus();
            }else{
                try {
                    novaString = string.substring(0, string.length() - farmaciaHumana.length());
                    txtProduto.setText(novaString);
                } catch (StringIndexOutOfBoundsException e) {
                }
                
            }

        });
        
        
        ckbVeterinario.setOnAction((t) -> {
            String string = txtProduto.getText();
            String novaString;
            if (ckbVeterinario.isSelected()) {
                ckbHumano.setSelected(false);
                if (string.contains(farmaciaHumana)) {
                    novaString = string.substring(0, string.length() - farmaciaHumana.length());
                    txtProduto.setText(novaString);
                }
                txtProduto.setText(txtProduto.getText() + farmaciaVeterinaria);
                txtQuantidade.requestFocus();
            }else{
                try {
                    novaString = string.substring(0, string.length() - farmaciaVeterinaria.length());
                    txtProduto.setText(novaString);
                } catch (StringIndexOutOfBoundsException e) {
                }
                
            }
            
        });
        
        btnAdicionarTutor.setOnAction((t) -> {
            cadastrarTutor(btnLimpar.getScene().getWindow());
            listarTutores();
        });
        
        btnAdicionarClinica.setOnAction((t) -> {
            cadastrarClinica(btnLimpar.getScene().getWindow());
            listarClinicas();
        });
        
        btnAdicionarPet.setOnAction((t) -> {
            cadastrarPet(btnLimpar.getScene().getWindow());
            listarPets();
        });
        
        btnAdicionarVeterinario.setOnAction((t) -> {
            cadastrarVeterinario(btnLimpar.getScene().getWindow());
            listarVeterinarios();
        });
        
        btnLimparLista.setOnAction((t) -> {
            listaProdutosReceitados.clear();
            if (prescricao != null) prescricao.limparListaProdutos();
            listarProdutos();
        });
        
        btnLimpar.setOnAction((t) -> {
            limpaCampos();
        });
        
        btnCancelar.setOnAction((t) -> {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        });

        btnRemoverProduto.setOnAction((t) -> {
            if (listViewProdutosInseridos.getSelectionModel().getSelectedIndex() >= 0) {
                this.listaProdutosReceitados.remove(listViewProdutosInseridos.getSelectionModel().getSelectedItem());
                if (prescricao != null) prescricao.removerProduto(listViewProdutosInseridos.getSelectionModel().getSelectedItem());
                listarProdutos();
            }
        });

        btnAdicionar.setOnAction((t) -> {
            adicionarProduto();
        });
        
        btnCalcular.setOnAction((t) -> {
            double doseFinal = Double.parseDouble(txtDoseDesejada.getText()) * Double.parseDouble(txtPeso.getText());
            lblDoseFinal.setText("Dose desejada: " + String.valueOf(doseFinal));
        });

        btnImprimir.setOnAction((t) -> {
            ValidacaoException exc = new ValidacaoException("Erro validando!!");

            try {
                if (prescricao == null) {
                    prescricao = new Prescricao();
                }
                
                prescricao.setObservacoes(txtObservacoes.getText().trim());
                prescricao.setAtendimento(atendimento);

                if (scmbTutor.getValue() != null){
                }else{
                    exc.adicionarErro("Tutor", "Insira um tutor!");
                }
                
                if (scmbPet.getValue() != null){
                    prescricao.setPet(scmbPet.getValue());
                }else{
                    exc.adicionarErro("Pet", "Insira um pet!");
                }
                
                if (cmbClinica.getValue() != null){
                    prescricao.setClinica(cmbClinica.getValue());
                }else{
                    exc.adicionarErro("Clinica", "Insira uma clínica!");
                }
                
                if (scmbVeterinario.getValue() != null){
                    prescricao.setVeterinario(scmbVeterinario.getValue());
                }else{
                    exc.adicionarErro("Veterinario", "Insira um veterinário!");
                }
                
                if (dpData.getValue() != null){
                    prescricao.setData(dpData.getValue());
                }else{
                    exc.adicionarErro("Data", "Insira uma data!");
                }
                
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }
                
                for (ProdutoPrescrito prod : listaProdutosReceitados) {
                    prescricao.adicionarProduto(prod);
                }
                
                if (new PrescricaoService().salvarOuAtualizar(prescricao)){
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Sucesso");
                    al.setContentText("Prescrição no formato PDF gerada com sucesso!");
                    al.showAndWait();
                }
                
                new ImpressaoPdf(prescricao);
            } catch (ValidacaoException e) {
                setErrorMessages(e.getErrors());
            }
        });

        ObservableList<String> listaObsFormasDeUso = FXCollections.observableArrayList("USO INTRA-AURICULAR", "USO INTRAMUSCULAR", "USO INTRAVENOSO", "USO OFTÁLMICO", "USO ORAL", "USO SUBCUTÂNEO", "USO TÓPICO");
        cmbFormaUso.setItems(listaObsFormasDeUso);

        ObservableList<ProdutoPrescrito> listaObsProdutosReceitados = FXCollections.observableArrayList(listaProdutosReceitados);
        listViewProdutosInseridos.setItems(listaObsProdutosReceitados);

        //Preencher os campos da tela ao se clicar duas vezes sobre um item da lista
        listViewProdutosInseridos.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                ProdutoPrescrito produtoReceitado = listViewProdutosInseridos.getSelectionModel().getSelectedItem();
                cmbFormaUso.setValue(produtoReceitado.getFormaUso());
                txtProduto.setText(produtoReceitado.getProduto());
                txtQuantidade.setText(produtoReceitado.getQuantidade());
                txtPosologia.setText(produtoReceitado.getPosologia());
            }
        });

        // Formatação da lista de produtos inseridos na prescrição
        listViewProdutosInseridos.setCellFactory(lv -> new ListCell<ProdutoPrescrito>() {
            private Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(ProdutoPrescrito prod, boolean empty) {
                super.updateItem(prod, empty);
                if (empty || prod == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(prod.getProduto() + " (" + prod.getQuantidade() + ")");
                    tooltip.setText(prod.getFormaUso() + " - " + prod.getPosologia());
                    setTooltip(tooltip);
                }
            }
        });
        
        cmbFormaUso.setValue("USO ORAL");
        
        scmbTutor.setOnAction((t) -> {
            scmbPet.setDisable(false);
            lblPeso.setText("");
            txtPeso.setText("");
            listarPets();
        });

        cmbClinica.setOnAction((t) -> {
            listarVeterinarios();
            scmbVeterinario.setDisable(false);
        });
        
        scmbPet.setOnAction((t) -> {
            if(scmbPet.getValue() != null){
                String peso = Utils.imprimePeso(String.valueOf(scmbPet.getValue().getPesoPet()));
                lblPeso.setText("Peso: " + peso);
                txtPeso.setText(String.valueOf(scmbPet.getValue().getPesoPet()));
            }
        });
        
        listarClinicas();
        listarPets();
        listarTutores();
        listarVeterinarios();
    }
    
    public void ajustarTela() {
        if (prescricao == null || prescricao.getId() == 0) {
            dpData.setValue(LocalDate.now());
        } else {

        }
        
        if (TelaPreferenciasController.preferencias.get(1) == 1){
            cmbClinica.setDisable(true);
            cmbClinica.setValue(clinica);
            scmbVeterinario.setDisable(false);
        }
    }
    
    private void limpaCampos() {
        dpData.setValue(LocalDate.now());
        cmbClinica.setValue(null);
        scmbPet.setValue(null);
        scmbTutor.setValue(null);
        scmbVeterinario.setValue(null);
        txtPeso.setText("");
        lblPeso.setText("");
        lblDoseFinal.setText("");
        txtObservacoes.setText("");
        txtDoseDesejada.setText("");
        cmbFormaUso.setValue("");
        txtProduto.setText("");
        txtPosologia.setText("");
        txtQuantidade.setText("");
    }
    
    private void listarTutores() {
        listaTutores = new TutorService().getAll(-1, "");
        ObservableList<Tutor> listaObsTutores = FXCollections.observableArrayList(listaTutores);
        scmbTutor.setItems(listaObsTutores);
//        cmbTutor.getItems().addAll(listaTutores);

        scmbTutor.setCellFactory(param -> new ListCell<Tutor>() {
            @Override
            protected void updateItem(Tutor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome() + " - " + Utils.imprimeCPFouCNPJ(item.getCpf()));
            }
        });

        scmbTutor.setButtonCell(new ListCell<Tutor>() {
            @Override
            protected void updateItem(Tutor item, boolean empty) {
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("Selecione um tutor"); // Texto padrão quando vazio
                } else {
                    setText(item.getNome() + " - " + Utils.imprimeCPFouCNPJ(item.getCpf()));
                }
            }
        });
    }

    private void listarVeterinarios() {
        listaVeterinarios = new ClinicaService().getVeterinariosDaClinica(clinica.getIdClinica(), clinica.getVeterinarioClinica().getId());
        ObservableList<Veterinario> listaObsVeterinarios = FXCollections.observableArrayList(listaVeterinarios);
        scmbVeterinario.setItems(listaObsVeterinarios);        
    }

    private void listarClinicas() {
        listaClinicas = new ClinicaService().getAll(-1, "");
        ObservableList<Clinica> listaObsClinicas = FXCollections.observableArrayList(listaClinicas);
        cmbClinica.setItems(listaObsClinicas);
//        cmbClinica.getItems().addAll(listaClinicas);
        cmbClinica.setCellFactory(param -> new ListCell<Clinica>() {
            @Override
            protected void updateItem(Clinica item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeClinica() + " - " + item.getMunicipioClinica()); // Substitua "getNome" pelo atributo desejado
            }
        });

        cmbClinica.setButtonCell(new ListCell<Clinica>() {
            @Override
            protected void updateItem(Clinica item, boolean empty) {
                super.updateItem(item, empty);

                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("Selecione uma clínica"); // Texto padrão quando vazio
                } else {
                    setText(item.getNomeClinica() + " - " + item.getMunicipioClinica());
                }
            }
        });
    }

    private void listarPets() {
        if (scmbTutor.getSelectionModel().getSelectedIndex() != -1) {
//            listaPets = new PetService().getAll(13, String.valueOf(cmbTutor.getValue().getIdTutor()));
            listaPets = new PetService().getAll(13, scmbTutor.getValue().getIdTutor() + "");
        }
        ObservableList<Pet> listaObsPet = FXCollections.observableArrayList(listaPets);
        scmbPet.setItems(listaObsPet);

        scmbPet.setCellFactory(param -> new ListCell<Pet>() {
            @Override
            protected void updateItem(Pet item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomePet() + " - " + item.getRaca().getEspecie().getNome());
            }
        });

        scmbPet.setButtonCell(new ListCell<Pet>() {
            @Override
            protected void updateItem(Pet item, boolean empty) {
                super.updateItem(item, empty);

                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("Selecione um pet"); // Texto padrão quando vazio
                } else {
                    setText(item.getNomePet() + " - " + item.getRaca().getEspecie().getNome());
                }
            }
        });
    }

    private void listarProdutos() {
        ObservableList<ProdutoPrescrito> listaObsProdutosReceitados = FXCollections.observableArrayList(listaProdutosReceitados);
        listViewProdutosInseridos.setItems(listaObsProdutosReceitados);
    }

    private void adicionarProduto() {
        String formaUso = cmbFormaUso.getValue();
        String posologia = txtPosologia.getText();
        String produto = txtProduto.getText();
        String quantidade = txtQuantidade.getText();

        ProdutoPrescrito produtoReceitado = new ProdutoPrescrito(formaUso, produto, quantidade, posologia);
        listaProdutosReceitados.add(produtoReceitado);

        //Retorna os valores para a forma inicial
        txtProduto.requestFocus();
        txtPosologia.setText("");
        txtProduto.setText("");
        txtQuantidade.setText("");
        ckbHumano.setSelected(false);
        ckbVeterinario.setSelected(false);
        listarProdutos();
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroTutor.setText(campos.contains("Tutor") ? errors.get("Tutor") : "");
        lblErroClinica.setText(campos.contains("Clinica") ? errors.get("Clinica") : "");
        lblErroPet.setText(campos.contains("Pet") ? errors.get("Pet") : "");
        lblErroVeterinario.setText(campos.contains("Veterinario") ? errors.get("Veterinario") : "");
        lblErroData.setText(campos.contains("Data") ? errors.get("Data") : "");
    }

}
