package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import model.classes.controleEstoque.Estoque;
import model.classes.controleEstoque.Produto;
import model.services.EstoqueService;
import model.services.ProdutoService;
import org.controlsfx.control.SearchableComboBox;
import view.utils.MascarasFX;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaEstoqueController implements Initializable {
    
    @FXML    private Button btnAdicionarAoEstoque;
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnInserirCategoriaProduto;
    @FXML    private Button btnInserirProduto;
    @FXML    private CheckBox ckbIgnorarDatas;
    @FXML    private CheckBox ckbMostrarValores;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private DatePicker dpAquisicao;
    @FXML    private DatePicker dpFabricacao;
    @FXML    private DatePicker dpDataFim;
    @FXML    private DatePicker dpDataInicio;
    @FXML    private DatePicker dpValidade;
    @FXML    private HBox boxDatas;
    @FXML    private Label lblErroValidacao;
    @FXML    private SearchableComboBox<Produto> scmbCategoriaProduto;
    @FXML    private SearchableComboBox<Produto> scmbProduto;
    @FXML    private TableColumn<Estoque, LocalDate> tableColumnAquisicao;
    @FXML    private TableColumn<Estoque, Produto> tableColumnCategoria;
    @FXML    private TableColumn<Estoque, LocalDate> tableColumnFabricacao;
    @FXML    private TableColumn<Estoque, Produto> tableColumnFabricante;
    @FXML    private TableColumn<Estoque, Produto> tableColumnNome;
    @FXML    private TableColumn<Estoque, Integer> tableColumnQtd;
    @FXML    private TableColumn<Estoque, LocalDate> tableColumnValidade;
    @FXML    private TableColumn<Estoque, Float> tableColumnValorCusto;
    @FXML    private TableColumn<Estoque, Float> tableColumnValorVenda;
    @FXML    private TableView<Estoque> tblProduto;
    @FXML    private TextField txtBusca;
    @FXML    private TextField txtQuantidade;
    @FXML    private TextField txtValorCusto;
    @FXML    private TextField txtValorVenda;

    private ObservableList<Estoque> estoqueList = FXCollections.observableArrayList();
    private ObservableList<Estoque> filteredList = FXCollections.observableArrayList();
    private List<Produto> listaProdutos;
    private List<Produto> listaCategoriasProdutos;
    private String txtFiltro;
    private int filtroSelecionado = -1;
    private Estoque estoque;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumeroInteiro(txtQuantidade);
        MascarasFX.mascaraData(dpAquisicao);
        MascarasFX.mascaraData(dpValidade);
        MascarasFX.mascaraData(dpFabricacao);
        MascarasFX.mascaraNumero(txtValorCusto);
        MascarasFX.mascaraNumero(txtValorVenda);
        
        //Comportamento do TAB
        scmbCategoriaProduto.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                scmbProduto.requestFocus();
            }
        });
        scmbProduto.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtQuantidade.requestFocus();
            }
        });
        txtQuantidade.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtValorCusto.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                adicionarAoEstoque();
            }
        });
        txtValorCusto.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtValorVenda.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                adicionarAoEstoque();
            }
        });
        txtValorVenda.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                dpValidade.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                adicionarAoEstoque();
            }
        });
        dpValidade.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                dpFabricacao.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                adicionarAoEstoque();
            }
        });
        dpFabricacao.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                dpAquisicao.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                adicionarAoEstoque();
            }
        });
        dpAquisicao.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                btnAdicionarAoEstoque.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                adicionarAoEstoque();
            }
        });
        
        
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoriaProduto"));
        tableColumnFabricante.setCellValueFactory(new PropertyValueFactory<>("fabricanteProduto"));
        tableColumnValidade.setCellValueFactory(new PropertyValueFactory<>("dtValidade"));
        tableColumnFabricacao.setCellValueFactory(new PropertyValueFactory<>("dtFabricacao"));
        tableColumnAquisicao.setCellValueFactory(new PropertyValueFactory<>("dtAquisicao"));
        tableColumnQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnValorCusto.setCellValueFactory(new PropertyValueFactory<>("valorCusto"));
        tableColumnValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        
        ckbIgnorarDatas.selectedProperty().addListener((ck, oldValue, newValue) -> {
            if (ckbIgnorarDatas.isSelected()){
                tableColumnValidade.setVisible(false);
                tableColumnAquisicao.setVisible(false);
                tableColumnFabricacao.setVisible(false);
            }else{
                tableColumnValidade.setVisible(true);
                tableColumnAquisicao.setVisible(true);
                tableColumnFabricacao.setVisible(true);
            }
            atualizaTabela();
            });
        
        ckbMostrarValores.selectedProperty().addListener((ck, oldValue, newValue) -> {
            if (ckbMostrarValores.isSelected()){
                tableColumnValorCusto.setVisible(true);
                tableColumnValorVenda.setVisible(true);
            }else{
                tableColumnValorCusto.setVisible(false);
                tableColumnValorVenda.setVisible(false);
            }
        });
        
        btnInserirCategoriaProduto.setOnAction((t) -> {
            new MenuPrincipal().inserirCategoriaProduto(btnFiltrar.getScene().getWindow());
            listarCategoriasProduto();
        });
        
        btnInserirProduto.setOnAction((t) -> {
            new MenuPrincipal().inserirProduto(btnFiltrar.getScene().getWindow(), scmbCategoriaProduto.getValue());
            if (scmbCategoriaProduto.getValue() != null){
                listarProdutos(scmbCategoriaProduto.getValue().getIdCategoriaProduto());
            }
        });
        
        scmbCategoriaProduto.setOnAction((t) -> {
            if (scmbCategoriaProduto.getValue() != null){
                listarProdutos(scmbCategoriaProduto.getSelectionModel().getSelectedItem().getIdCategoriaProduto());
                scmbProduto.setDisable(false);
            }
        });
        
        tblProduto.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)&& tblProduto.getSelectionModel().getSelectedItem() != null) {
                estoque = tblProduto.getSelectionModel().getSelectedItem();
                
                scmbCategoriaProduto.getSelectionModel().select(estoque.getProduto());
                scmbProduto.setValue(estoque.getProduto());
                txtQuantidade.setText(String.valueOf(estoque.getQuantidade()));
                txtValorCusto.setText(String.valueOf(estoque.getValorCusto()));
                txtValorVenda.setText(String.valueOf(estoque.getValorVenda()));
                dpAquisicao.setValue(estoque.getDtAquisicao());
                dpFabricacao.setValue(estoque.getDtFabricacao());
                dpValidade.setValue(estoque.getDtValidade());
            }
        });
        
        btnAdicionarAoEstoque.setOnAction((t) -> {
            adicionarAoEstoque();
        });
        
        tblProduto.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.DELETE) {
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                estoque = tblProduto.getSelectionModel().getSelectedItem();
                al.setContentText("O tutor " + estoque.getNomeProduto()+ " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    if (new EstoqueService().excluir(estoque)) {
                        Alert mens = new Alert(Alert.AlertType.INFORMATION);
                        mens.initOwner(btnFiltrar.getScene().getWindow());
                        mens.setTitle("Excluído");
                        mens.setContentText("Registro excluído com sucesso!");
                        mens.showAndWait();
                        atualizaTabela();
                        estoque = null;
                    }
                }
            }
        });
        
        ObservableList<String> listaObs = FXCollections.observableArrayList("Categoria", "Data de aquisição", "Data de fabricação", "Data de validade", "Fabricante", "Nome");
        cmbFiltro.setItems(listaObs);
        
        cmbFiltro.setOnAction((t) -> {
            try {
                filtroSelecionado = cmbFiltro.getSelectionModel().getSelectedIndex();
                if (filtroSelecionado == 1 || filtroSelecionado == 2 || filtroSelecionado == 3) {
                    txtBusca.setVisible(false);
                    txtBusca.setManaged(false);
                    boxDatas.setVisible(true);
                    boxDatas.setManaged(true);
                } else {
                    txtBusca.setVisible(true);
                    txtBusca.setManaged(true);
                    boxDatas.setVisible(false);
                    boxDatas.setManaged(false);
                }
            } catch (Exception e) {
                filtroSelecionado = 0;
                e.printStackTrace();
            }
        });
        
        btnFiltrar.setOnAction((t) -> {
            filtrar();
        });
        
        txtBusca.setOnAction((t) -> filtrar());
        
        btnLimpar.setOnAction((t) -> {
            cmbFiltro.setValue(null);
            txtBusca.setText("");
        });
        
        dpAquisicao.setValue(LocalDate.now());
        
        scmbProduto.setDisable(true);
        listarCategoriasProduto();
        atualizaTabela();
        boxDatas.setVisible(false);
        boxDatas.setManaged(false);
    }
    
    private void filtrar(){
        if (filtroSelecionado == 1 || filtroSelecionado == 2 || filtroSelecionado == 3){
                String dtInicial = dpDataInicio.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String dtFinal = dpDataFim.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                txtFiltro = dtInicial + " " + dtFinal;
            }else{
                txtFiltro = txtBusca.getText();
            }
            atualizaTabela();
    }

    private void atualizaTabela() {
        List<Estoque> listaEstoque = new EstoqueService().getAll(filtroSelecionado, txtFiltro);
        estoqueList.setAll(listaEstoque);

        if (ckbIgnorarDatas.isSelected()) {
            // Agrupar por produto e calcular soma de quantidades e média ponderada dos valores
            Map<Produto, List<Estoque>> agrupado = estoqueList.stream()
                    .collect(Collectors.groupingBy(Estoque::getProduto));

            List<Estoque> estoqueAgrupado = agrupado.entrySet().stream()
                    .map(entry -> {
                        Produto produto = entry.getKey();
                        List<Estoque> estoques = entry.getValue();

                        // Soma total das quantidades
                        int totalQuantidade = estoques.stream()
                                .mapToInt(Estoque::getQuantidade)
                                .sum();

                        // Média ponderada do valor de custo
                        double custoPonderado = estoques.stream()
                                .mapToDouble(e -> e.getValorCusto() * e.getQuantidade())
                                .sum() / totalQuantidade;

                        // Média ponderada do valor de venda
                        double vendaPonderada = estoques.stream()
                                .mapToDouble(e -> e.getValorVenda() * e.getQuantidade())
                                .sum() / totalQuantidade;

                        // Criando novo objeto Estoque com os valores agregados
                        return new Estoque(produto, totalQuantidade, (float) custoPonderado, (float) vendaPonderada, null, null, null);
                    })
                    .collect(Collectors.toList());

            filteredList.setAll(estoqueAgrupado);
    } else {
        // Mostrar todos os registros individualmente
        filteredList.setAll(estoqueList);
    }

        tblProduto.setItems(filteredList);
        Utils.formatTableColumnDate(tableColumnValidade);
        Utils.formatTableColumnDate(tableColumnAquisicao);
        Utils.formatTableColumnDate(tableColumnFabricacao);
        Utils.formatTableColumnFloat(tableColumnValorCusto);
        Utils.formatTableColumnFloat(tableColumnValorVenda);
        tblProduto.refresh();
    }
    
    private void limpaCampos(){
        scmbProduto.setValue(null);
        txtQuantidade.setText("");
        dpAquisicao.setValue(LocalDate.now());
        dpFabricacao.setValue(null);
        dpValidade.setValue(null);
        estoque = null;
    }
    
    private void adicionarAoEstoque() {
        if (scmbCategoriaProduto != null && scmbProduto != null && !txtQuantidade.getText().equals("")) {
            lblErroValidacao.setText("");
            Produto produto = scmbProduto.getValue();
            int quantidade = Integer.parseInt(txtQuantidade.getText());
            float valorCusto = Float.parseFloat(txtValorCusto.getText());
            float valorVenda = Float.parseFloat(txtValorVenda.getText());
            LocalDate dtValidade = dpValidade.getValue();
            LocalDate dtAquisicao = dpAquisicao.getValue();
            LocalDate dtFabricacao = dpFabricacao.getValue();

            if (estoque == null) {
                estoque = new Estoque(produto, quantidade, valorCusto, valorVenda, dtAquisicao, dtFabricacao, dtValidade);
            } else {
                estoque.setProduto(produto);
                estoque.setQuantidade(quantidade);
                estoque.setDtAquisicao(dtAquisicao);
                estoque.setDtFabricacao(dtFabricacao);
                estoque.setDtValidade(dtValidade);
            }

            new EstoqueService().salvarOuAtualizar(estoque);
            atualizaTabela();
            limpaCampos();
            estoque = null;
        }else{
            lblErroValidacao.setText("Preencha os campos de produto e quantidade!");
        }
    }

    private void listarCategoriasProduto(){
        listaCategoriasProdutos = new ProdutoService().getCategoriasDeProduto();
        ObservableList<Produto> listaObsCategorias = FXCollections.observableArrayList(listaCategoriasProdutos);
        scmbCategoriaProduto.setItems(listaObsCategorias);
        
        scmbCategoriaProduto.setCellFactory(param -> new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoria());
            }
        });

        scmbCategoriaProduto.setButtonCell(new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("Selecione");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getCategoria());
                }
            }                                                                                                                                                                           
        });
    }
    
    private void listarProdutos(int idCategoria){
        listaProdutos = new ProdutoService().getProdutosDaCategoria(idCategoria);
        ObservableList<Produto> listaObsProdutos = FXCollections.observableArrayList(listaProdutos);
        scmbProduto.setItems(listaObsProdutos);
        
        scmbProduto.setCellFactory(param -> new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome() + " (" + item.getFabricante() + ")");
            }
        });

        scmbProduto.setButtonCell(new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getNome() + " (" + item.getFabricante() + ")");
                }
            }                                                                                                                                                                           
        });
    }
    
    
}
