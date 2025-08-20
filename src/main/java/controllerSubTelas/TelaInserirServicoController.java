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
import model.classes.Servico;
import model.services.ServicoService;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaInserirServicoController implements Initializable {

    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserir;
    @FXML    private Label lblErroNome;
    @FXML    private ListView<Servico> listViewServicos;
    @FXML    private TextArea txtDescricao;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtValor;
    
    Servico servico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        listViewServicos.setCellFactory(lv -> new ListCell<Servico>() {
            @Override
            protected void updateItem(Servico servico, boolean empty) {
                super.updateItem(servico, empty);
                if (empty || servico == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(servico.getNomeServico() + " - R$ " + servico.getValorServico());
                }
            }
        });

        listViewServicos.setOnMouseClicked((mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                btnExcluir.setVisible(true);
            }
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                servico = listViewServicos.getSelectionModel().getSelectedItem();
                txtNome.setText(servico.getNomeServico());
                txtDescricao.setText(servico.getDescricaoServico());
                txtValor.setText(String.valueOf(servico.getValorServico()));
            }
        });

        btnExcluir.setOnAction((t) -> {
            if (listViewServicos.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o servico que deseja excluir!");
                al.showAndWait();
            } else {
                servico = listViewServicos.getSelectionModel().getSelectedItem();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(servico.getNomeServico() + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    new ServicoService().excluir(servico);
                    listarServicos();
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
                lblErroNome.setText("Insira um nome para o servico!");
            } else {
                String nome = txtNome.getText().trim();
                String descricao = txtDescricao.getText().trim();
                float valor = 0;
                if (!txtValor.getText().equals("")) {
                    valor = Float.parseFloat(txtValor.getText());
                }
                if (servico == null) {
                    servico = new Servico(nome, valor, descricao);
                }
                servico.setNomeServico(nome);
                servico.setDescricaoServico(descricao);
                servico.setValorServico(valor);
                if (new ServicoService().salvarOuAtualizar(servico)) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Sucesso");
                    al.setContentText("Servico inserido com sucesso!");
                    al.showAndWait();
                    listarServicos();
                    txtNome.setText("");
                    txtDescricao.setText("");
                    txtValor.setText("");
                    lblErroNome.setText("");
                    servico = null;
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
        listarServicos();
    }

    private void listarServicos() {
        List<Servico> listaServicos = new ServicoService().getAll();
        ObservableList<Servico> listaObsServico = FXCollections.observableArrayList(listaServicos);
        listViewServicos.setItems(listaObsServico);
    }

}
