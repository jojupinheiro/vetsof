
package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.classes.Venda;
import model.classes.controleEstoque.Estoque;
import model.classes.controleEstoque.Produto;
import model.enums.FormaPagamento;
import model.exceptions.ValidacaoException;
import model.services.EstoqueService;
import model.services.ProdutoService;
import model.services.VendaService;
import org.controlsfx.control.SearchableComboBox;
import view.utils.MascarasFX;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaVendasController implements Initializable {
    
    @FXML    private Button btnAdicionar;
    @FXML    private Button btnFinalizar;
    @FXML    private Button btnRemover;
    @FXML    private ComboBox<FormaPagamento> cmbFormaPagamento;
    @FXML    private Label lblData;
    @FXML    private Label lblHora;
    @FXML    private Label lblNomeProduto;
    @FXML    private Label lblParcelas;
    @FXML    private Label lblTotal;
    @FXML    private Label lblValorProduto;
    @FXML    private Label lblVendedor;
    @FXML    private SearchableComboBox<Produto> scmbCategoria;
    @FXML    private SearchableComboBox<Estoque> scmbProduto;
    @FXML    private Spinner<Integer> spnNumeroParcelas;
    @FXML    private TableColumn<Estoque, Integer> tableColumnCodigo;
    @FXML    private TableColumn<Estoque, Float> tableColumnPreco;
    @FXML    private TableColumn<Estoque, Produto> tableColumnProduto;
    @FXML    private TableColumn<Estoque, Integer> tableColumnQtd;
    @FXML    private TableColumn<Estoque, Float> tableColumnSubtotal;
    @FXML    private TableView<Estoque> tblVenda;
    @FXML    private TextField txtCliente;
    @FXML    private TextField txtCodigo;
    @FXML    private TextField txtCpf;
    @FXML    private TextField txtDescontoAbs;
    @FXML    private TextField txtDescontoPercent;
    @FXML    private TextField txtQuantidade;
    
    private List<Estoque> produtosVendidos = new ArrayList<>();
    private List<Produto> listaCategoriasProdutos = new ArrayList<>();
    private List<Estoque> listaProdutos = new ArrayList<>();
    private ScheduledExecutorService atualizadorHorario;
    float valorTotal = 0;
    float valorTotalComDesconto = 0;
    float desconto = 0;
    float descontoPercentual = 0;
    String vendedor = Principal.usuarioLogado.getNomeUsuario();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumeroInteiro(txtQuantidade);
        MascarasFX.mascaraNumeroInteiro(txtCodigo);
        MascarasFX.mascaraNumeroInteiro(txtCpf);
        MascarasFX.mascaraNumero(txtDescontoAbs);
//        MascarasFX.mascaraNumero(txtDescontoPercent);
        lblData.setText(Utils.imprimeData(LocalDate.now()));
        lblHora.setText(Utils.imprimeHora(LocalTime.now()));
        lblVendedor.setText(vendedor);
        lblNomeProduto.setText("");
        lblValorProduto.setText("");
        lblTotal.setText("");
        txtQuantidade.setText("1");
        btnRemover.setVisible(false);
        lblParcelas.setVisible(false);
        lblParcelas.setManaged(false);
        spnNumeroParcelas.setVisible(false);
        spnNumeroParcelas.setManaged(false);
        
        btnFinalizar.setOnAction((t) -> finalizarVenda());
        
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("valorVenda"));
        tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
        tableColumnQtd.setCellValueFactory(new PropertyValueFactory<>("quantidadeConsumida"));
        tableColumnSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        
        btnRemover.setOnAction((t) -> produtosVendidos.remove(tblVenda.getSelectionModel().getSelectedItem()));
        
        tblVenda.selectionModelProperty().addListener((t, ov, nv) -> btnRemover.setVisible(nv != null));
        
        btnAdicionar.setOnAction((t) -> adicionarProdutoNaLista());
        
        txtQuantidade.setOnAction((t) -> adicionarProdutoNaLista());
        txtCodigo.setOnAction((t) -> adicionarProdutoNaLista());
        
        txtDescontoAbs.setOnAction((t) -> {
            calcularValorTotal();
            imprimirValorTotal();
            descontoPercentual = desconto * 100 / valorTotal;
            txtDescontoPercent.setText(Utils.imprimePorcentagem(descontoPercentual));
        });
        
        txtDescontoPercent.setOnAction((t) -> {
            aplicarDescontoPercentual();
            calcularValorTotal();
            imprimirValorTotal();
        });

        ObservableList<FormaPagamento> listaObsFormaPagamento = FXCollections.observableArrayList(FormaPagamento.DINHEIRO, FormaPagamento.PIX,
                FormaPagamento.DEBITO_VISTA, FormaPagamento.CREDITO_VISTA, FormaPagamento.DEBITO_PRAZO, FormaPagamento.CREDITO_PRAZO,
                FormaPagamento.DEBITO_PARCELADO, FormaPagamento.CREDITO_PARCELADO);
        cmbFormaPagamento.setItems(listaObsFormaPagamento);
        
        cmbFormaPagamento.setCellFactory(cb -> new ListCell<FormaPagamento>() {
            @Override
            protected void updateItem(FormaPagamento formaPagamento, boolean empty) {
                super.updateItem(formaPagamento, empty);
                if (empty || formaPagamento == null) {
                    setText(null);
                } else {
                    setText(formaPagamento.getNome());
                }
            }
        });
        
        cmbFormaPagamento.setButtonCell(new ListCell<FormaPagamento>() {
            @Override
            protected void updateItem(FormaPagamento formaPagamento, boolean empty) {
                super.updateItem(formaPagamento, empty);
                if (empty || formaPagamento == null) {
                    setText(null);
                } else {
                    setText(formaPagamento.getNome());
                }
            }
        });
        
        cmbFormaPagamento.setValue(FormaPagamento.valueOf(TelaPreferenciasController.valoresPadrao.get(1).getValorPadraoString()));
        
        cmbFormaPagamento.setOnAction((t) -> {
            if(cmbFormaPagamento.getValue() == FormaPagamento.CREDITO_PARCELADO || cmbFormaPagamento.getValue() == FormaPagamento.DEBITO_PARCELADO){
                lblParcelas.setVisible(true);
                lblParcelas.setManaged(true);
                spnNumeroParcelas.setVisible(true);
                spnNumeroParcelas.setManaged(true);
            }else{
                lblParcelas.setVisible(false);
                lblParcelas.setManaged(false);
                spnNumeroParcelas.setVisible(false);
                spnNumeroParcelas.setManaged(false);
            }
        });
        
        listarCategoriasProduto();
        listarProdutos(0);
        
        scmbCategoria.setOnAction((t) -> {
            if (scmbCategoria.getValue() != null) listarProdutos(scmbCategoria.getValue().getIdCategoriaProduto());
            scmbProduto.setDisable(false);
        });
        
        scmbProduto.setOnAction((t) -> {
            if (scmbProduto.getValue() != null){
                lblNomeProduto.setText(scmbProduto.getValue().getNomeProduto());
                lblValorProduto.setText("R$ " + Utils.imprimeValor(String.valueOf(scmbProduto.getValue().getValorVenda())));
                txtCodigo.setText(scmbProduto.getValue().getId()+"");
            }
        });
        
        SpinnerValueFactory<Integer> vfNumeroParcelas = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1, 1);
        spnNumeroParcelas.setValueFactory(vfNumeroParcelas);
    }    
    
    private void limpaCamposProduto(){
        txtCodigo.setText("");
        txtQuantidade.setText("1");
        scmbCategoria.setValue(null);
        scmbProduto.setValue(null);
        lblNomeProduto.setText("");
        lblValorProduto.setText("");
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
        if(idCategoria == 0){
            listaProdutos = new EstoqueService().getAll(-1, "");
        }else{
            listaProdutos = new EstoqueService().getAll(6, String.valueOf(idCategoria));
        }
        
        ObservableList<Estoque> listaObsProdutos = FXCollections.observableArrayList(listaProdutos);
        scmbProduto.setItems(listaObsProdutos);
        
        scmbProduto.setCellFactory(param -> new ListCell<Estoque>() {
            @Override
            protected void updateItem(Estoque item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomeProduto()+ " (" + Utils.imprimeData(item.getDtValidade()) + ")");
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
                    setText(item.getNomeProduto()+ " (" + Utils.imprimeData(item.getDtValidade()) + ")");
                }
            }                                                                                                                                                                           
        });
        atualizarHorario();
        addCloseListenerQuandoJanelaEstiverPronta();
    }
    
    private void atualizarTabela() {
        ObservableList<Estoque> listaObsProdutos = FXCollections.observableArrayList(produtosVendidos);
        //Vinculando a lista observável com a TableView
        tblVenda.setItems(listaObsProdutos);
        Utils.formatTableColumnFloat(tableColumnPreco);
        Utils.formatTableColumnFloat(tableColumnSubtotal);
    }
    
    private void calcularValorTotal(){
        valorTotal = 0;
        for (Estoque p : produtosVendidos){
            valorTotal += (p.getQuantidadeConsumida() * p.getValorVenda());
        }
        
        aplicarDesconto();
    }
    
    private void aplicarDesconto(){
        desconto = txtDescontoAbs.getText().equals("") ? 0 : Float.parseFloat(txtDescontoAbs.getText());
        valorTotalComDesconto = valorTotal - desconto;
    }
    
    private void aplicarDescontoPercentual(){
        float descontoPercentualFloat = Utils.formataFloat(txtDescontoPercent.getText());
        desconto = valorTotal * (descontoPercentualFloat / 100);
        txtDescontoAbs.setText(String.valueOf(desconto));
    }
    
    private void imprimirValorTotal(){
        lblTotal.setText("R$ " + Utils.imprimeValor(String.valueOf(valorTotalComDesconto)));
    }
    
    private void atualizarHorario() {
        // Criando um agendador de tarefas
        atualizadorHorario = Executors.newSingleThreadScheduledExecutor();

        // Executa o método a cada 10 segundos
        atualizadorHorario.scheduleAtFixedRate(() -> {
            Platform.runLater(this::imprimirHorarioAtualizado); // Atualiza a UI na thread principal
        }, 0, 10, TimeUnit.SECONDS);
    }
    
    private void imprimirHorarioAtualizado() {
        lblHora.setText(Utils.imprimeHora(LocalTime.now()));
    }
    
    private void pararAtualizacaoDeHorario() {
        if (atualizadorHorario != null && !atualizadorHorario.isShutdown()) {
            atualizadorHorario.shutdown();
        }
    }
    
    private void addCloseListenerQuandoJanelaEstiverPronta() {
        tblVenda.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> {
                    Stage stage = (Stage) newScene.getWindow();
                    if (stage != null) {
                        stage.setOnCloseRequest(event ->  pararAtualizacaoDeHorario());
                    }
                });
            }
        });
    }
    
    private void adicionarProdutoNaLista(){
        Estoque produto = scmbProduto.getValue();
        produto.setQuantidadeConsumida((int) Integer.valueOf(txtQuantidade.getText()));
        produtosVendidos.add(produto);
        limpaCamposProduto();
        atualizarTabela();
        calcularValorTotal();
        imprimirValorTotal();
    }
    
    private void finalizarVenda(){
        try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                
                if (produtosVendidos.isEmpty()){
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("ERRO");
                    al.setContentText("Insira ao menos um produto!");
                    al.showAndWait();
                    throw new RuntimeException("Deve ser inserido um produto na venda");
                }

                // Ao final de todos os testes de campos, é necessário verificar se existem erros.
                // Se existire, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }
                
                String cliente = txtCliente.getText();
                String cpf = txtCpf.getText();
                FormaPagamento formaPagamento = cmbFormaPagamento.getValue();
                int numeroParcelas = spnNumeroParcelas.getValue();
                
                LocalDateTime dataHora = LocalDateTime.now();
                Venda venda = new Venda(dataHora, produtosVendidos, valorTotalComDesconto, cliente, cpf, vendedor, formaPagamento, numeroParcelas);

                if (new VendaService().salvarOuAtualizar(venda)) {

                    // Deu certo
                    limpaCampos();
                } else {
                    // Deu erro. O retorno do boolean veio false
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("ERRO");
                    al.setContentText("Ocorreu um erro ao inserir!");
                    al.showAndWait();
                }

            } catch (ValidacaoException e) {
                System.out.println("Erro na validação");
                setErrorMessages(e.getErrors());
            } catch (RuntimeException rte){
                System.out.println("ERRO: " + rte.getMessage());
            }
    }
    
    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();
        // Mostrar o erro no label que definimos
//        lblErroNome.setText(campos.contains("Nome") ? errors.get("Nome") : "");
    }
    
    private void limpaCampos(){
        txtCliente.setText("");
        txtCpf.setText("");
        txtCodigo.setText("");
        scmbCategoria.getSelectionModel().select(null);
        scmbProduto.getSelectionModel().select(null);
        txtQuantidade.setText("");
        produtosVendidos.clear();
        
    }

}
