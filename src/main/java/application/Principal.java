
package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import model.classes.Usuario;
import model.services.UtilitarioService;

/**
 *
 * @author herrmann
 */
public class Principal extends Application {
    
    //Usuario logado no sistema
    public static Usuario usuarioLogado;
    public static Properties prop;
    public static String ip;
    public static String usuarioBanco;
    public static String senhaBanco;
    public static final String CHAVE_SECRETA = "3433654613433654"; // Deve ter 16 caracteres
    
    @Override
    public void start(Stage stage) {
        
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("TelaLogin.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.setTitle("VetSof");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            ip = props.getProperty("ip");
        } catch (IOException e) {
            System.out.println("Não encontrado o IP Salvo");
            e.printStackTrace();
        }
        
        try (FileInputStream input = new FileInputStream("config.properties")) {
            usuarioBanco = props.getProperty("usuarioBanco");
        } catch (IOException e) {
            System.out.println("Não encontrado o Usuário Salvo");
            e.printStackTrace();
        }
        
        try (FileInputStream input = new FileInputStream("config.properties")) {
            senhaBanco = descriptografarSenha(props.getProperty("senhaBanco"));
        } catch (IOException e) {
            System.out.println("Não encontrado a senha IP Salva");
            e.printStackTrace();
        }
        
        //Carregar variáveis estáticas
        TelaPreferenciasController.estilo = "styles/" + new UtilitarioService().getEstiloAtivo();
        TelaPreferenciasController.preferencias = new UtilitarioService().getPreferencias();
        TelaPreferenciasController.valoresPadrao = new UtilitarioService().getValoresPadrao();
        
        launch(args);
    }
    
    public static String descriptografarSenha(String senhaCriptografada) {
        try {
            Key chave = new SecretKeySpec(CHAVE_SECRETA.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, chave);
            byte[] senhaDecodificada = java.util.Base64.getDecoder().decode(senhaCriptografada);
            return new String(cipher.doFinal(senhaDecodificada));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
