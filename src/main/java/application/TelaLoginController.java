package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.classes.Usuario;
import model.services.UsuarioService;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaLoginController implements Initializable {

    @FXML    Button btnEntrar;
    @FXML    Button btnBD;
    @FXML    Button btnCancelar;
    @FXML    TextField txtUsuario;
    @FXML    PasswordField txtSenha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtUsuario.setText("teste");
        txtSenha.setText("teste");
        btnEntrar.setOnAction((t) -> {
            entrar();
        });

        //Fechando o programa
        btnCancelar.setOnAction((t) -> {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        });

        txtSenha.setOnAction((t) -> {
            entrar();
        });

        txtUsuario.setOnAction((t) -> {
            entrar();
        });
        
        btnBD.setOnAction((t) -> {
            Properties props = new Properties();

            try {
                Parent parent = FXMLLoader.load(getClass().getResource("TelaIP.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setTitle("Cadastro de IP");
                stage.setScene(scene);
                scene.getStylesheets().add(TelaPreferenciasController.estilo);
                stage.setMinWidth(550);
                stage.setMinHeight(300);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(TelaLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        

    }

    public void entrar() {
        String testeUsuario = txtUsuario.getText();
        String testeSenha = txtSenha.getText();
        Usuario usuarioTeste = new Usuario(testeUsuario, testeSenha);
        Usuario usuarioLogado = new UsuarioService().efetuarLogin(usuarioTeste);

        if (usuarioLogado != null) {
            //Salvando o usuario logado
            Principal.usuarioLogado = usuarioLogado;

            try {
                Parent parent = FXMLLoader.load(getClass().getResource("TelaListas.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setTitle("VetSof");
                stage.setScene(scene);
                scene.getStylesheets().add(TelaPreferenciasController.estilo);
                stage.setMinWidth(1400);
                stage.setMinHeight(800);
                stage.show();
                
                //Delay para maximizar a tela
//                PauseTransition delay = new PauseTransition(Duration.millis(50));
//                delay.setOnFinished(e -> stage.setMaximized(true));
//                delay.play();
                
                ((Stage) btnEntrar.getScene().getWindow()).close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (txtUsuario.getText().equals("") || txtSenha.getText().equals("")) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Erro de login");
            al.setContentText("Preencha os campos usuário e senha!");
            al.showAndWait();

        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Erro de login");
            al.setContentText("Nome de usuário ou senha incorreto");
            al.showAndWait();
        }

    }

}
