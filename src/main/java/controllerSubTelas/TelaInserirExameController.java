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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import model.classes.Exame;
import model.services.ExameService;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirExameController implements Initializable {

    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private Label lblErroNome;
    @FXML    private ListView<Exame> listViewExames;
    @FXML    private TextArea txtDescricao;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtValor;
    
    Exame exame;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listViewExames.setCellFactory(lv -> new ListCell<Exame>() {
            @Override
            protected void updateItem(Exame exame, boolean empty) {
                super.updateItem(exame, empty);
                if (empty || exame == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(exame.getNomeExame() + " - R$ " + exame.getValorExame());
                }
            }
        });

        listViewExames.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                exame = listViewExames.getSelectionModel().getSelectedItem();
                txtNome.setText(exame.getNomeExame());
                txtDescricao.setText(exame.getDescricaoExame());
                txtValor.setText(String.valueOf(exame.getValorExame()));
            }
            
        });

        btnExcluir.setOnAction((t) -> {
            if (listViewExames.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o exame que deseja excluir!");
                al.showAndWait();
            } else {
                Exame exame = listViewExames.getSelectionModel().getSelectedItem();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(exame.getNomeExame() + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    new ExameService().excluir(exame);
                    listarExames();
                    txtNome.setText("");
                    txtDescricao.setText("");
                    txtValor.setText("");
                    txtNome.requestFocus();
                }
            }
            btnExcluir.setVisible(false);
        });
      
        btnInserir.setOnAction((t) -> {
            if (txtNome.getText().equals("")) {
                lblErroNome.setText("Insira um nome para o exame!");
            } else {
                String nome = txtNome.getText().trim();
                String descricao = txtDescricao.getText().trim();
                float valor = 0;
                if (!txtValor.getText().equals("")) {
                    valor = Float.parseFloat(txtValor.getText());
                }
                if (exame == null) {
                    exame = new Exame(nome, valor, descricao);
                }
                exame.setNomeExame(nome);
                exame.setDescricaoExame(descricao);
                exame.setValorExame(valor);
                if (new ExameService().salvarOuAtualizar(exame)) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Sucesso");
                    al.setContentText("Exame inserido com sucesso!");
                    al.showAndWait();
                    listarExames();
                    txtNome.setText("");
                    txtDescricao.setText("");
                    txtValor.setText("");
                    lblErroNome.setText("");
                    exame = null;
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("ERRO");
                    al.setContentText("Ocorreu um erro ao inserir!");
                    al.showAndWait();
                }
            }
            txtNome.requestFocus();
            btnExcluir.setVisible(false);
        }
        );
        listarExames();
    }

    private void listarExames() {
        List<Exame> listaExames = new ExameService().getAll();
        ObservableList<Exame> listaObsExame = FXCollections.observableArrayList(listaExames);
        listViewExames.setItems(listaObsExame);
    }

}
