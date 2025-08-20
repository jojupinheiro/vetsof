package controllerSubTelas;

import application.MenuPrincipal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.stage.Stage;
import model.classes.DiariaInternacao;
import model.classes.Exame;
import model.classes.ExameRealizado;
import model.classes.Internado;
import model.classes.Servico;
import model.classes.ServicoRealizado;
import model.classes.controleEstoque.Estoque;
import model.classes.controleEstoque.Produto;
import model.exceptions.ValidacaoException;
import model.services.EstoqueService;
import model.services.ProdutoService;
import model.services.ServicoService;
import org.controlsfx.control.SearchableComboBox;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaCadastroProdutoConsumidoController implements Initializable {
   
    @FXML    private Button btnCadastrar;
    @FXML    private Button btnFechar;
    @FXML    private Button btnInserirCategoria;
    @FXML    private Button btnInserirProdutoEmEstoque;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnRemover;
    @FXML    private Label lblDados;
    @FXML    private Label lblDtAquisicao;
    @FXML    private Label lblDtFabricacao;
    @FXML    private Label lblDtValidade;
    @FXML    private Label lblErroCategoria;
    @FXML    private Label lblErroProduto;
    @FXML    private Label lblQuantidade;
    @FXML    private Label lblValorCusto;
    @FXML    private ListView<Estoque> listViewProdutos;
    @FXML    private SearchableComboBox<Produto> scmbCategoria;
    @FXML    private SearchableComboBox<Estoque> scmbProduto;
    @FXML    private Spinner<Integer> spnQuantidade;
    @FXML    private TextField txtValor;
    
    private List<Estoque> listaProdutosConsumidos = new ArrayList<>();
    private List<Estoque> listaProdutos = new ArrayList<>();
    private ObservableList<Estoque> estoqueList = FXCollections.observableArrayList();
    private ObservableList<Estoque> filteredList = FXCollections.observableArrayList();
    private List<Produto> listaCategoriasProdutos;
    private Estoque estoque;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnLimpar.setOnAction((t) -> {
            listaProdutosConsumidos.clear();
            listarProdutosConsumidos();
        });
        
        btnRemover.setOnAction((t) -> {
            if(listViewProdutos.getSelectionModel().getSelectedItem() != null){
                listaProdutosConsumidos.remove(listViewProdutos.getSelectionModel().getSelectedItem());
                listarProdutosConsumidos();
            }
        });
        
        btnInserirCategoria.setOnAction((t) -> {
            new MenuPrincipal().inserirCategoriaProduto(btnCadastrar.getScene().getWindow());
            listarCategoriasProduto();
        });
        
        btnInserirProdutoEmEstoque.setOnAction((t) -> {
            new MenuPrincipal().cadastrarEstoqueNovaJanela(btnCadastrar.getScene().getWindow());
        });
        
        btnCadastrar.setOnAction((t) -> {
            cadastrar();
        });
        
        scmbProduto.setOnAction((t) -> {
            if (scmbProduto.getValue() != null){
                Estoque produto = scmbProduto.getValue();
                lblDtAquisicao.setText(produto.getDtAquisicao() == null ? "" : "Data de aquisição: " + produto.getDtAquisicao().format(format));
                lblDtFabricacao.setText(produto.getDtFabricacao() == null ? "" : "Data de fabricação: " + produto.getDtFabricacao().format(format));
                lblDtValidade.setText(produto.getDtValidade() == null ? "" : "Data de validade: " + produto.getDtValidade().format(format));
                lblQuantidade.setText("Quantidade disponível: " + String.valueOf(produto.getQuantidade()));
                lblValorCusto.setText(produto.getValorCusto() == 0 ? "" : "Valor de custo: R$" + Utils.imprimeValor(String.valueOf(produto.getValorCusto())));
                txtValor.setText(produto.getValorVenda() == 0 ? "" : String.valueOf(produto.getValorVenda()));
            }
        });
        
        scmbCategoria.setOnAction((t) -> {
            if (scmbCategoria.getValue() != null) listarProdutos(scmbCategoria.getValue().getIdCategoriaProduto());
            scmbProduto.setDisable(false);
        });
        
        lblDtAquisicao.setText("");
        lblDtFabricacao.setText("");
        lblDtValidade.setText("");
        lblQuantidade.setText("");
        lblValorCusto.setText("");
        
        listarCategoriasProduto();
        scmbProduto.setDisable(true);
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1, 1);
        spnQuantidade.setValueFactory(valueFactory);
    }    
    
    private void cadastrar(){
        try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                
                float valorProdutoSelecionado = 0;
                if (!txtValor.getText().equals("")){
                    valorProdutoSelecionado = Float.parseFloat(txtValor.getText());
                }
                
                //Atributos obrigatórios
                
                Estoque produtoEmEstoque = null;
                if(scmbProduto.getValue() != null){
                    produtoEmEstoque = scmbProduto.getValue();
                }else{
                    exc.adicionarErro("Produto", "Selecione um produto para cadastrar!");
                }
                
                if(scmbCategoria.getValue() != null){
                }else{
                    exc.adicionarErro("Categoria", "Selecione uma categoria produto!");
                }

                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }
                
                int id = scmbProduto.getValue().getId();
                Produto produto = produtoEmEstoque.getProduto();
                int quantidadeConsumida = spnQuantidade.getValue();
                int quantidade = (scmbProduto.getValue().getQuantidade() - quantidadeConsumida) < 0 ? 0 : (scmbProduto.getValue().getQuantidade() - quantidadeConsumida);
                float valorCusto = produtoEmEstoque.getValorCusto();
                float valorVenda = produtoEmEstoque.getValorVenda();
                LocalDate dtAquisicao = produtoEmEstoque.getDtAquisicao();
                LocalDate dtFabricacao = produtoEmEstoque.getDtFabricacao();
                LocalDate dtValidade = produtoEmEstoque.getDtValidade();

                Estoque produtoConsumido = new Estoque(id, produto, quantidade, valorCusto, valorVenda, dtAquisicao, dtFabricacao, dtValidade);
                produtoConsumido.setQuantidadeConsumida(quantidadeConsumida);
                listaProdutosConsumidos.add(produtoConsumido);
                
                ObservableList<Estoque> listaObsProdCons = FXCollections.observableArrayList(listaProdutosConsumidos);
                listViewProdutos.setItems(listaObsProdCons);
                
                //Resetando os campos para o estado original
                scmbCategoria.getSelectionModel().select(-1);
                scmbProduto.getSelectionModel().select(-1);
                txtValor.setText("");
                spnQuantidade.getValueFactory().setValue(1);
                lblDtAquisicao.setText("");
                lblDtFabricacao.setText("");
                lblDtValidade.setText("");
                lblQuantidade.setText("");
                lblValorCusto.setText("");
                
                listarProdutosConsumidos();

            }catch(ValidacaoException e){
                setErrorMessages(e.getErrors());
            }
    }
    
    private void listarProdutosConsumidos() {
        ObservableList<Estoque> listaObsProdCons = FXCollections.observableArrayList(listaProdutosConsumidos);                                                                             
        listViewProdutos.setItems(listaObsProdCons);                                                                                                                                        
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();

        // Mostrar o erro no label que definimos
        lblErroCategoria.setText(campos.contains("Categoria") ? errors.get("Categoria") : "");
        lblErroProduto.setText(campos.contains("Produto") ? errors.get("Produto") : "");
    }

    public void ajustarTela(Internado internado, DiariaInternacao diaria, ObservableList<Estoque> produtosDestino){
        lblDados.setText("Animal: " + internado.getNomePet() + ", " + diaria.getStringData());
        ObservableList<Estoque> produtosConsumidos = FXCollections.observableArrayList();
        listViewProdutos.setItems(produtosConsumidos);
        listaProdutosConsumidos = produtosDestino;
        listarProdutosConsumidos();
        
        btnFechar.setOnAction(e -> {
            produtosDestino.addAll(produtosConsumidos);
            ((Stage) btnFechar.getScene().getWindow()).close();
        });

        // Garante que os exames sejam transferidos mesmo que o usuário feche pelo "X"
        btnFechar.getScene().getWindow().setOnCloseRequest(e -> produtosDestino.addAll(produtosConsumidos));
    }
    
    public ObservableList<Estoque> getLista(){
        ObservableList<Estoque> listaObsProdCons = FXCollections.observableArrayList(listaProdutosConsumidos); 
        return listaObsProdCons;
    }
    
    private void listarCategoriasProduto(){
        listaCategoriasProdutos = new ProdutoService().getCategoriasDeProduto();
        ObservableList<Produto> listaObsCategorias = FXCollections.observableArrayList(listaCategoriasProdutos);
        scmbCategoria.setItems(listaObsCategorias);
        
        scmbCategoria.setCellFactory(param -> new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoria());
            }
        });

        scmbCategoria.setButtonCell(new ListCell<Produto>() {
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
        listaProdutos = new EstoqueService().getAll(6, String.valueOf(idCategoria));
        ObservableList<Estoque> listaObsProdutos = FXCollections.observableArrayList(listaProdutos);
        scmbProduto.setItems(listaObsProdutos);
        
        scmbProduto.setCellFactory(param -> new ListCell<Estoque>() {
            @Override
            protected void updateItem(Estoque item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeProduto()+ " (" + item.getDtValidade().format(format) + ")");
            }
        });

        scmbProduto.setButtonCell(new ListCell<Estoque>() {
            @Override
            protected void updateItem(Estoque item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getNomeProduto()+ " (" + item.getDtValidade().format(format) + ")");
                }
            }                                                                                                                                                                           
        });
    }
    
}
