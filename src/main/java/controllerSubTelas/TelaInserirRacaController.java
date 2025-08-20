/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllerSubTelas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import model.classes.utilitario.Raca;
import model.classes.utilitario.Especie;
import model.services.PetService;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirRacaController implements Initializable {

    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private ComboBox<Especie> cmbEspecie;
    @FXML    private ListView<Raca> listViewRaca;
    @FXML    private TextField txtNome;
    
    Raca raca;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        listViewRaca.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                raca = listViewRaca.getSelectionModel().getSelectedItem();
                txtNome.setText(raca.getNome());
            }
        });
        
        btnExcluir.setOnAction((t) -> {
            if (cmbEspecie.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione a espécie da qual deseja excluir a raça!");
                al.showAndWait();
            } else if (listViewRaca.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o raça que deseja excluir!");
                al.showAndWait();
            } else {
                //pegando os valores inseridos nos combobox
                raca = listViewRaca.getSelectionModel().getSelectedItem();

                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(raca + " será excluída! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    //utilizando os valores carregados para excluir do banco
                    new PetService().excluirRaca(raca);

                    listarRacas();
                    btnExcluir.setVisible(false);
                }
            }
        });
        
        btnInserir.setOnAction((t) -> {
            if (cmbEspecie.getSelectionModel().getSelectedIndex() == -1) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERRO");
            al.setContentText("Selecione a espécie na qual deseja inserir a raça!");
            al.showAndWait();
        } else {
            Especie especieRaca = cmbEspecie.getValue();
            String nomeRaca = txtNome.getText().trim();
            if (raca == null){
                raca = new Raca(nomeRaca, especieRaca);
            }

            if (new PetService().inserirRaca(raca)) {
                txtNome.setText("");
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Raça inserida com sucesso!");
                al.showAndWait();
                txtNome.setText("");
                txtNome.requestFocus();
                btnExcluir.setVisible(false);
                raca = null;
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Ocorreu um erro ao inserir!");
                al.showAndWait();
            }
            listarRacas();
        }
        txtNome.requestFocus();
        btnExcluir.setVisible(false);
        });
        
        cmbEspecie.setOnAction((t) -> {
            listarRacas();
        });
        
        listarEspecies();
    }    
    
    private void listarRacas() {
        if (cmbEspecie.getSelectionModel().getSelectedIndex() != -1) {
            List<Raca> listaRacas = new PetService().getRacas(cmbEspecie.getValue());
            ObservableList<Raca> listaObsRaca = FXCollections.observableArrayList(listaRacas);
            listViewRaca.setItems(listaObsRaca);
        }
    }
    
    private void listarEspecies() {
        List<Especie> listaEspecies = new PetService().getEspecies();
        ObservableList<Especie> listaObsEspecie = FXCollections.observableArrayList(listaEspecies);
        cmbEspecie.setItems(listaObsEspecie);
    }
    
}
