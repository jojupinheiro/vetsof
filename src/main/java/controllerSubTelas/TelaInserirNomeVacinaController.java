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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import model.classes.ProdutoVacina;
import model.services.ProdutoVacinaService;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirNomeVacinaController implements Initializable {

    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private ComboBox<ProdutoVacina> cmbCategoriaVacina;
    @FXML    private ListView<ProdutoVacina> listViewVacinas;
    @FXML    private TextField txtVacina;
    @FXML    private TextField txtLaboratorio;
    @FXML    private TextField txtValor;
    
    private List<ProdutoVacina> listaNomesVacina = new ArrayList<>();
    private List<ProdutoVacina> listaTiposVacina = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listViewVacinas.setCellFactory(lv -> new ListCell<ProdutoVacina>() {
            @Override
            protected void updateItem(ProdutoVacina produto, boolean empty) {
                super.updateItem(produto, empty);
                if (empty || produto == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(produto.getNomeVacina() + " (" + produto.getLaboratorioVacina() + ")");
                }
            }
        });
        
        listViewVacinas.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            if(listViewVacinas.getSelectionModel().getSelectedIndex() >= 0){
                new ProdutoVacinaService().excluirNomeVacina(listViewVacinas.getSelectionModel().getSelectedItem());
            }
            listarNomesDeVacinas();
            btnExcluir.setVisible(false);
        });
        
        cmbCategoriaVacina.setOnAction((t) -> {
            listarNomesDeVacinas();
            btnExcluir.setVisible(false);
        });
        
        btnInserir.setOnAction((t) -> {
            if (cmbCategoriaVacina.getSelectionModel().getSelectedIndex() != -1 && !txtVacina.getText().equals("")){
                String nomeVacina = txtVacina.getText().trim();
                String laboratorio = txtLaboratorio.getText().trim();
                float valor = Float.parseFloat(txtValor.getText());
                ProdutoVacina tipo = cmbCategoriaVacina.getValue();
                
                ProdutoVacina produtoVacina = new ProdutoVacina(nomeVacina, tipo.getNomeVacina(), tipo.getIdTipoVacina(), laboratorio, valor);
                new ProdutoVacinaService().salvarOuAtualizarNomeVacina(produtoVacina);
            }
            listarNomesDeVacinas();
        });
        
        listarTiposDeVacinas();
    }    
    
    private void listarNomesDeVacinas() {
        if (cmbCategoriaVacina.getValue() != null) {
            listaNomesVacina = new ProdutoVacinaService().getAll(cmbCategoriaVacina.getValue());    
            ObservableList<ProdutoVacina> listaObsNomesVacina = FXCollections.observableArrayList(listaNomesVacina);                                                                    //
            listViewVacinas.setItems(listaObsNomesVacina);
        }
    }
    
     private void listarTiposDeVacinas() {                                                                                                                                               //
        this.listaTiposVacina = new ProdutoVacinaService().getTiposVacinas();                                                                                                                //
        ObservableList<ProdutoVacina> listaObsTipoVacina = FXCollections.observableArrayList(listaTiposVacina);                                                                         //
        cmbCategoriaVacina.setItems(listaObsTipoVacina);
        
        cmbCategoriaVacina.setCellFactory(param -> new ListCell<ProdutoVacina>() {
            @Override
            protected void updateItem(ProdutoVacina item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTipoVacina());
            }
        });

        cmbCategoriaVacina.setButtonCell(new ListCell<ProdutoVacina>() {
            @Override
            protected void updateItem(ProdutoVacina item, boolean empty) {                                                                                                              
                super.updateItem(item, empty);
                // Define o texto exibido no ComboBox quando um item está selecionado
                if (empty || item == null) {
                    setText("");                                                    // Texto padrão quando vazio                                                                        
                } else {
                    setText(item.getTipoVacina());
                }
            }                                                                                                                                                                           
        });
        
    }
    
}
