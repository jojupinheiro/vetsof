/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package controllerSubTelas;

import application.Principal;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * FXML Controller class
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaIPController implements Initializable {
   
    @FXML    private Button btnAtualizar;
    @FXML    private TextField txtIp;
    @FXML    private PasswordField txtSenhaBD;
    @FXML    private TextField txtUsuarioBD;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtIp.setText(Principal.ip);
        txtUsuarioBD.setText(Principal.usuarioBanco);
        txtSenhaBD.setText(Principal.descriptografarSenha(Principal.senhaBanco));
        
        btnAtualizar.setOnAction((t) -> {
            Properties props = new Properties();
            String ip = txtIp.getText();
            String usuarioBD = txtUsuarioBD.getText();
            String senhaBD = txtSenhaBD.getText();
            String senhaCriptografada = criptografarSenha(senhaBD);
            
            props.setProperty("ip", ip);
            props.setProperty("usuarioBanco", usuarioBD);
            props.setProperty("senhaBanco", senhaCriptografada);

            try (FileOutputStream output = new FileOutputStream("config.properties")) {
                props.store(output, "Configurações do Aplicativo");
                System.out.println("Configurações salvas!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    
    private String criptografarSenha(String senha) {
        try {
            Key chave = new SecretKeySpec(Principal.CHAVE_SECRETA.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            byte[] senhaCriptografada = cipher.doFinal(senha.getBytes());
            return java.util.Base64.getEncoder().encodeToString(senhaCriptografada);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
