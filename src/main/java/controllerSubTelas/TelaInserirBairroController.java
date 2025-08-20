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
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Municipio;
import model.services.UtilitarioService;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaInserirBairroController implements Initializable {

    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private ListView<Bairro> listViewBairro;
    @FXML    private SearchableComboBox<Municipio> scmbMunicipio;
    @FXML    private TextField txtNome;
   
    Bairro bairro;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        listViewBairro.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                bairro = listViewBairro.getSelectionModel().getSelectedItem();
                txtNome.setText(bairro.getNome());
            }
        });
    
        btnExcluir.setOnAction((t) -> {
            if (scmbMunicipio.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o município de onde deseja excluir o bairro!");
                al.showAndWait();
            } else if (listViewBairro.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o bairro que deseja excluir!");
                al.showAndWait();
            } else {
                //pegando os valores inseridos nos combobox
                bairro = listViewBairro.getSelectionModel().getSelectedItem();

                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(bairro + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    //utilizando os valores carregados para excluir do banco
                    new UtilitarioService().excluirBairro(bairro);

                    listarBairros();
                    btnExcluir.setVisible(false);
                }
            }
        });

        btnInserir.setOnAction((t) -> inserirBairro());
        txtNome.setOnAction((t) -> inserirBairro());

        scmbMunicipio.setOnAction((t) -> listarBairros());
        
        listarMunicipios();
    }
    
    private void inserirBairro(){
        if (scmbMunicipio.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione abaixo o município aonde deseja inserir o bairro!");
                al.showAndWait();
            } else {
                Municipio municipioBairro = scmbMunicipio.getValue();
                String nomeBairro = txtNome.getText().trim();
                if (bairro == null) {
                    bairro = new Bairro(nomeBairro, municipioBairro);
                }

                if (new UtilitarioService().inserirOuAtualizarBairro(bairro)) {
                    txtNome.setText("");
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Sucesso");
                    al.setContentText("Bairro inserido com sucesso!");
                    al.showAndWait();
                    txtNome.setText("");
                    txtNome.requestFocus();
                    btnExcluir.setVisible(false);
                    bairro = null;
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("ERRO");
                    al.setContentText("Ocorreu um erro ao inserir!");
                    al.showAndWait();
                }
                listarBairros();
            }
            txtNome.requestFocus();
            btnExcluir.setVisible(false);
    }
    
    public void setMunicipio(Municipio municipio){
        scmbMunicipio.setValue(municipio);
        txtNome.requestFocus();
    }
    
    private void listarBairros() {
        if (scmbMunicipio.getSelectionModel().getSelectedIndex() != -1) {
            List<Bairro> listaBairros = new UtilitarioService().getBairros(scmbMunicipio.getValue());
            ObservableList<Bairro> listaObsBairro = FXCollections.observableArrayList(listaBairros);
            listViewBairro.setItems(listaObsBairro);
        }
    }
    
    private void listarMunicipios() {
        List<Municipio> listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMun = FXCollections.observableArrayList(listaMunicipios);
        scmbMunicipio.setItems(listaObsMun);
    }
    
}
