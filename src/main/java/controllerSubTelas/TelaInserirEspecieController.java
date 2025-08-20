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
import javafx.scene.input.MouseButton;
import model.classes.utilitario.Especie;
import model.services.PetService;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirEspecieController implements Initializable {

   
    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private ListView<Especie> listViewEspecie;
    @FXML    private TextField txtEspecie;
  
    private List<Especie> listaEspecies = new ArrayList<>();
    
    Especie especie;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        listViewEspecie.setCellFactory(lv -> new ListCell<Especie>() {
            @Override
            protected void updateItem(Especie especie, boolean empty) {
                super.updateItem(especie, empty);
                if (empty || especie == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(especie.getNome());
                }
            }
        });
        
        listViewEspecie.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            
            if(listViewEspecie.getSelectionModel().getSelectedIndex() >= 0){
                new PetService().excluirEspecie(listViewEspecie.getSelectionModel().getSelectedItem());
            }
            listarEspecies();
            btnExcluir.setVisible(false);
        });
            
            
        btnInserir.setOnAction((t) -> {
            String nomeEspecie = txtEspecie.getText().trim();
            if(especie == null){
                especie = new Especie(nomeEspecie);
            }
            
            if(!txtEspecie.getText().equals("")){
                new PetService().inserirEspecie(especie);
                txtEspecie.setText("");
                especie = null;
            }
            
            listarEspecies();
        });
        
        listarEspecies();
    }    
    
    private void listarEspecies() {                                                                                                                                               
        this.listaEspecies = new PetService().getEspecies();                                                                                                             
        ObservableList<Especie> listaObsEspecie = FXCollections.observableArrayList(listaEspecies);                                                                         
        listViewEspecie.setItems(listaObsEspecie);                                                                                                                                
    }
    
}