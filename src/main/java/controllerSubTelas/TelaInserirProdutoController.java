/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllerSubTelas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import model.classes.controleEstoque.Produto;
import model.services.ProdutoService;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaInserirProdutoController implements Initializable {

    
    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private ComboBox<Produto> cmbCategoriaProduto;
    @FXML    private ListView<Produto> listViewProdutos;
    @FXML    private TextField txtProduto;
    @FXML    private TextField txtFabricante;
    @FXML    private TextArea txtDescricao;
    
    private List<Produto> listaProdutos = new ArrayList<>();
    private List<Produto> listaCategoriasProdutos = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        txtProduto.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                inserirProduto();
            }
        });
        txtFabricante.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                inserirProduto();
            }
        });
        txtDescricao.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.TAB) {
                btnInserir.requestFocus();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                inserirProduto();
            }
        });
        
        
        listViewProdutos.setCellFactory(lv -> new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto produto, boolean empty) {
                super.updateItem(produto, empty);
                if (empty || produto == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(produto.getNome()+ " (" + produto.getFabricante()+ ")");
                }
            }
        });
        
        listViewProdutos.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            if(listViewProdutos.getSelectionModel().getSelectedIndex() >= 0){
                new ProdutoService().excluir(listViewProdutos.getSelectionModel().getSelectedItem());
            }
            listarProdutos();
            btnExcluir.setVisible(false);
        });
        
        cmbCategoriaProduto.setOnAction((t) -> {
            listarProdutos();
            btnExcluir.setVisible(false);
        });
        
        btnInserir.setOnAction((t) -> {
            inserirProduto();
        });
        
        listarCategoriasDeProdutos();
    }    
    
    public void setCategoriaDeProduto(Produto produto){
        cmbCategoriaProduto.setValue(produto);
        txtProduto.requestFocus();
    }
    
    private void inserirProduto(){
        if (cmbCategoriaProduto.getSelectionModel().getSelectedIndex() != -1 && !txtProduto.getText().equals("")){
                String nomeProduto = txtProduto.getText().trim();
                String fabricante = txtFabricante.getText().trim();
                String descricao = txtDescricao.getText().trim();
                String categoria = cmbCategoriaProduto.getValue().getCategoria();
                int idCategoria = cmbCategoriaProduto.getValue().getIdCategoriaProduto();
                
                Produto produto = new Produto(nomeProduto, categoria, descricao, fabricante);
                produto.setIdCategoriaProduto(idCategoria);
                new ProdutoService().salvarOuAtualizarProduto(produto);
            }
            listarProdutos();
            txtProduto.setText("");
            txtDescricao.setText("");
    }
    
    private void listarProdutos() {
        if (cmbCategoriaProduto.getValue() != null) {
            int idCategoria = cmbCategoriaProduto.getValue().getIdCategoriaProduto();
            listaProdutos = new ProdutoService().getProdutosDaCategoria(idCategoria);    
            ObservableList<Produto> listaObsNomesProduto = FXCollections.observableArrayList(listaProdutos);                                                                    //
            listViewProdutos.setItems(listaObsNomesProduto);
        }
    }
    
     private void listarCategoriasDeProdutos() {                                                                                                                                               //
        this.listaCategoriasProdutos = new ProdutoService().getCategoriasDeProduto();                                                                                                                //
        ObservableList<Produto> listaObsCategoriasProdutos = FXCollections.observableArrayList(listaCategoriasProdutos);                                                                         //
        cmbCategoriaProduto.setItems(listaObsCategoriasProdutos);
        
        cmbCategoriaProduto.setCellFactory(param -> new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getCategoria());
            }
        });

        cmbCategoriaProduto.setButtonCell(new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getCategoria());
                }
            }                                                                                                                                                                           
        });
        
    }
    
}
