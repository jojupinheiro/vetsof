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
import model.classes.utilitario.Municipio;
import model.services.UtilitarioService;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirMunicipioController implements Initializable {

    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private ListView<Municipio> listViewMunicipio;
    @FXML    private ComboBox<String> cmbEstado;
    @FXML    private TextField txtMunicipio;
    
    private List<Municipio> listaMunicipios = new ArrayList<>();
    
    Municipio municipio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        listViewMunicipio.setCellFactory(lv -> new ListCell<Municipio>() {
            @Override
            protected void updateItem(Municipio municipio, boolean empty) {
                super.updateItem(municipio, empty);
                if (empty || municipio == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(municipio.getNome() + " - " + municipio.getEstado());
                }
            }
        });
        
        listViewMunicipio.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            
            if(listViewMunicipio.getSelectionModel().getSelectedIndex() >= 0){
                new UtilitarioService().excluirMunicipio(listViewMunicipio.getSelectionModel().getSelectedItem());
            }
            listarMunicipios();
            btnExcluir.setVisible(false);
        });
            
            
        btnInserir.setOnAction((t) -> {
            String nomeMunicipio = txtMunicipio.getText().trim();
            String estado = cmbEstado.getValue();
            if(municipio == null){
                municipio = new Municipio(nomeMunicipio, estado);
            }
            
            if(!txtMunicipio.getText().equals("") && estado != null){
                new UtilitarioService().inserirOuAtualizarMunicipio(municipio);
                txtMunicipio.setText("");
                cmbEstado.getSelectionModel().select(null);
                municipio = null;
            }
            
            listarMunicipios();
        });
        
        ObservableList<String> estados = FXCollections.observableArrayList("AC", "AL", "AM", "AP", "BA", "CE", "DF", 
                "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");
        cmbEstado.setItems(estados);
        
        listarMunicipios();
    }    
    
    private void listarMunicipios() {                                                                                                                                               
        this.listaMunicipios = new UtilitarioService().getMunicipios();                                                                                                             
        ObservableList<Municipio> listaObsMunicipio = FXCollections.observableArrayList(listaMunicipios);                                                                         
        listViewMunicipio.setItems(listaObsMunicipio);                                                                                                                                
    }
    
}