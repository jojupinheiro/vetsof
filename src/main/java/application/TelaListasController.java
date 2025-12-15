package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * Tela mãe das telas de listagem. Possui métodos para inserir as outras telas dentro dela
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class TelaListasController extends MenuPrincipal implements Initializable {

    @FXML    private BorderPane borderPane;
    @FXML    private Button btnAtendimentos;
    @FXML    private Button btnClinicas;
    @FXML    private Button btnEstoque;
    @FXML    private Button btnInternacao;
    @FXML    private Button btnPets;
    @FXML    private Button btnPrescricao;
    @FXML    private Button btnSalaEspera;
    @FXML    private Button btnTutores;
    @FXML    private Button btnVendas;
    @FXML    private Button btnVeterinarios;
    @FXML    private MenuItem miAdministrador;
    @FXML    private MenuItem miCadastrarAtendimento;
    @FXML    private MenuItem miCadastrarClinica;
    @FXML    private MenuItem miCadastrarInternado;
    @FXML    private MenuItem miCadastrarPet;
    @FXML    private MenuItem miCadastrarPrescricao;
    @FXML    private MenuItem miCadastrarTutor;
    @FXML    private MenuItem miCadastrarUsuario;
    @FXML    private MenuItem miCadastrarVeterinario;
    @FXML    private MenuItem miEstoque;
    @FXML    private MenuItem miPreferencias;
    @FXML    private MenuItem miReposicaoHidrica;
    @FXML    private MenuItem miRelatorios;
    @FXML    private MenuItem miVerAtendimento;
    @FXML    private MenuItem miVerClinica;
    @FXML    private MenuItem miVerPet;
    @FXML    private MenuItem miVerPrescricao;
    @FXML    private MenuItem miVerSalaEspera;
    @FXML    private MenuItem miVerSalaInternacao;
    @FXML    private MenuItem miVerTutor;
    @FXML    private MenuItem miVendas;
    @FXML    private MenuItem miVerVeterinario;
    @FXML    private Menu menuPropriedades;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (Principal.usuarioLogado.isTipoUsuario()) {
            menuPropriedades.setVisible(true);
            miCadastrarUsuario.setVisible(true);
        }
        
        miAdministrador.setOnAction((t) -> {
            telaAdministrador(btnVeterinarios.getScene().getWindow());
        });
        
        miPreferencias.setOnAction((t) -> {
            telaPreferencias(btnVeterinarios.getScene().getWindow());
            aplicarModoUso();
        });

        miCadastrarAtendimento.setOnAction((t) -> {
            cadastrarAtendimento(btnVeterinarios.getScene().getWindow());
        });

        miCadastrarClinica.setOnAction((t) -> {
            cadastrarClinica(btnVeterinarios.getScene().getWindow());
        });
        
        miCadastrarInternado.setOnAction((t) -> {
            cadastrarInternado(btnVeterinarios.getScene().getWindow());
        });

        miCadastrarPet.setOnAction((t) -> {
            cadastrarPet(btnVeterinarios.getScene().getWindow());
        });
        
        miCadastrarPrescricao.setOnAction((t) -> {
            cadastrarPrescricao(btnVeterinarios.getScene().getWindow());
        });

        miCadastrarTutor.setOnAction((t) -> {
            cadastrarTutor(btnVeterinarios.getScene().getWindow());
        });

        miCadastrarUsuario.setOnAction((t) -> {
            cadastrarUsuario(btnVeterinarios.getScene().getWindow());
        });

        miCadastrarVeterinario.setOnAction((t) -> {
            cadastrarVeterinario(btnVeterinarios.getScene().getWindow());
        });
        
        miReposicaoHidrica.setOnAction((t) -> {
            calculadoraReposicaoHidrica(btnVeterinarios.getScene().getWindow());
        });
        
        miRelatorios.setOnAction((t) -> {
            
        });

        miVerAtendimento.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaAtendimento.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            verAtendimento((Stage) btnVeterinarios.getScene().getWindow());
        });
        
        btnAtendimentos.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaAtendimento.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        miVerClinica.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaClinica.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            verClinica((Stage) btnVeterinarios.getScene().getWindow());
        });
        
        btnClinicas.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaClinica.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        miVerPet.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaPet.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            verPet((Stage) btnVeterinarios.getScene().getWindow());
        });
        
        btnPets.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaPet.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        miVerPrescricao.setOnAction((t) -> {
            try {
                AnchorPane view;
                view = FXMLLoader.load(getClass().getResource("TelaListaPrescricao.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        btnPrescricao.setOnAction((t) -> {
            try {
                AnchorPane view;
                view = FXMLLoader.load(getClass().getResource("TelaListaPrescricao.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });

        miVerTutor.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaTutor.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            verTutor((Stage) btnVeterinarios.getScene().getWindow());
        });
        
        miVerSalaEspera.setOnAction((t) -> {
            new MenuPrincipal().cadastrarSalaEsperaNovaJanela(btnVeterinarios.getScene().getWindow());
        });
        
        btnTutores.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaTutor.fxml"));
                borderPane.setCenter(view);
            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        miVerVeterinario.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaVeterinario.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            verVeterinario((Stage) btnVeterinarios.getScene().getWindow());
        });
        
        btnVeterinarios.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaListaVeterinario.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnSalaEspera.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaSalaEspera.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnInternacao.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaInternacao.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        miVerSalaInternacao.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaInternacao.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnEstoque.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaEstoque.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        miEstoque.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaEstoque.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnVendas.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaVendas.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        miVendas.setOnAction((t) -> {
            try {
                AnchorPane view = FXMLLoader.load(getClass().getResource("TelaVendas.fxml"));
                borderPane.setCenter(view);

            } catch (IOException ex) {
                Logger.getLogger(TelaListasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        aplicarModoUso();

    }    
    
    private void aplicarModoUso(){
        if (TelaPreferenciasController.preferencias.get(1) == 1){   //Se o software estiver no modo clinica
//            btnClinicas.setDisable(true);
            btnClinicas.setVisible(false);
            btnClinicas.setManaged(false);
            miVerClinica.setVisible(false);
            miCadastrarClinica.setVisible(false);
        }else{                                                  //Se o software estiver no modo instituição
            miCadastrarPrescricao.setVisible(false);
            btnPrescricao.setVisible(false);
            btnPrescricao.setManaged(false);
            btnEstoque.setVisible(false);
            btnEstoque.setManaged(false);
            btnSalaEspera.setVisible(false);
            btnSalaEspera.setManaged(false);
            miVerPrescricao.setVisible(false);
            miVerSalaEspera.setVisible(false);
            miEstoque.setVisible(false);
            miVendas.setVisible(false);
            miCadastrarInternado.setVisible(false);
            btnInternacao.setVisible(false);
            btnInternacao.setManaged(false);
        }
    }
   
}
