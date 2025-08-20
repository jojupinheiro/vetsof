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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
public class TelaInserirCategoriaProdutoController implements Initializable {

    @FXML    private Button btnInserir;
    @FXML    private Button btnExcluir;
    @FXML    private ListView<Produto> listViewCategorias;
    @FXML    private TextField txtCategoriaProdutos;
    
    private List<Produto> listaTiposVacina = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtCategoriaProdutos.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                inserirCategoria();
            }
        });
        
        listViewCategorias.setCellFactory(lv -> new ListCell<Produto>() {
            @Override
            protected void updateItem(Produto produto, boolean empty) {
                super.updateItem(produto, empty);
                if (empty || produto == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(produto.getCategoria());
                }
            }
        });
        
        listViewCategorias.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            
            if(listViewCategorias.getSelectionModel().getSelectedIndex() >= 0){
                new ProdutoService().excluirCategoriaDeProduto(listViewCategorias.getSelectionModel().getSelectedItem());
            }
            listarCategoriasDeProdutos();
            btnExcluir.setVisible(false);
        });
            
            
        btnInserir.setOnAction((t) -> {
            inserirCategoria();
        });
        
        listarCategoriasDeProdutos();
    }    
    
    private void inserirCategoria(){
        String categoria = txtCategoriaProdutos.getText().trim();
            Produto categoriaProduto = new Produto(categoria);
            
            if(!txtCategoriaProdutos.getText().equals("")){
                new ProdutoService().salvarOuAtualizarCategoriaDeProduto(categoriaProduto);
            }
            
            listarCategoriasDeProdutos();
            txtCategoriaProdutos.setText("");
    }
    
    private void listarCategoriasDeProdutos() {                                                                                                                                               
        this.listaTiposVacina = new ProdutoService().getCategoriasDeProduto();                                                                                                             
        ObservableList<Produto> listaObsCategoria = FXCollections.observableArrayList(listaTiposVacina);                                                                         
        listViewCategorias.setItems(listaObsCategoria);                                                                                                                                
    }
    
    
}
