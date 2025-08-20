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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import model.classes.ExameRealizado;
import model.classes.ProdutoVacina;
import model.services.ProdutoVacinaService;
import view.utils.Utils;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirTipoVacinaController implements Initializable {

    @FXML    private Button btnInserir;
    @FXML    private Button btnExcluir;
    @FXML    private ListView<ProdutoVacina> listViewCategorias;
    @FXML    private TextField txtCategoriaVacinas;
    
    private List<ProdutoVacina> listaTiposVacina = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        listViewCategorias.setCellFactory(lv -> new ListCell<ProdutoVacina>() {
            @Override
            protected void updateItem(ProdutoVacina produto, boolean empty) {
                super.updateItem(produto, empty);
                if (empty || produto == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(produto.getTipoVacina());
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
                new ProdutoVacinaService().excluirTipoVacina(listViewCategorias.getSelectionModel().getSelectedItem());
            }
            listarTiposDeVacinas();
            btnExcluir.setVisible(false);
        });
            
            
        btnInserir.setOnAction((t) -> {
            String categoria = txtCategoriaVacinas.getText().trim();
            ProdutoVacina tipoVacina = new ProdutoVacina(categoria);
            
            if(!txtCategoriaVacinas.getText().equals("")){
                new ProdutoVacinaService().salvarOuAtualizarTipoVacina(tipoVacina);
            }
            
            listarTiposDeVacinas();
        });
        
        listarTiposDeVacinas();
    }    
    
    private void listarTiposDeVacinas() {                                                                                                                                               
        this.listaTiposVacina = new ProdutoVacinaService().getTiposVacinas();                                                                                                             
        ObservableList<ProdutoVacina> listaObsTipoVacina = FXCollections.observableArrayList(listaTiposVacina);                                                                         
        listViewCategorias.setItems(listaObsTipoVacina);                                                                                                                                
    }
    
}
