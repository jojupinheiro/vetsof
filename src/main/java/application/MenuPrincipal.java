package application;

import controllerSubTelas.TelaCadastroExameRealizadoController;
import controllerSubTelas.TelaCadastroProdutoConsumidoController;
import controllerSubTelas.TelaCadastroServicoRealizadoController;
import controllerSubTelas.TelaCadastroVacinaController;
import controllerSubTelas.TelaInserirBairroController;
import controllerSubTelas.TelaInserirCategoriaProdutoController;
import controllerSubTelas.TelaInserirProdutoController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.classes.Atendimento;
import model.classes.Clinica;
import model.classes.DiariaInternacao;
import model.classes.ExameRealizado;
import model.classes.Internado;
import model.classes.Pet;
import model.classes.prescricoes.Prescricao;
import model.classes.SalaEspera;
import model.classes.ServicoRealizado;
import model.classes.Tutor;
import model.classes.Vacina;
import model.classes.Veterinario;
import model.classes.controleEstoque.Estoque;
import model.classes.controleEstoque.Produto;
import model.classes.utilitario.Municipio;

/**
 *
 * @author João Juliano Pinheiro - joaojulianopinheiro@hotmail.com - Venâncio Aires - RS
 */
public class MenuPrincipal {

    public void cadastrarAtendimento(Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroAtendimento.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Atendimentos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(690);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroAtendimentoController cont = loader.getController();
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarAtendimentoDoPet(Pet pet, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroAtendimento.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Atendimentos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(690);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroAtendimentoController cont = loader.getController();
            cont.setPetDoAtendimento(pet);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarClinica(Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroClinica.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Clínicas");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroClinicaController cont = loader.getController();
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cadastrarEstoqueNovaJanela(Window janela) {
        try {
            URL url = getClass().getResource("TelaEstoque.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de estoque");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            TelaEstoqueController cont = loader.getController();
//            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    public void cadastrarExameRealizado(Window janela, Internado internado, DiariaInternacao diaria) {
//        try {
//            URL url = getClass().getResource("TelaCadastroExameRealizado.fxml");
//            FXMLLoader loader = new FXMLLoader(url);
//            Parent parent = loader.load();
//            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.setTitle("Cadastro de Exame Realizado");
//            stage.setScene(scene);
//            scene.getStylesheets().add(TelaPreferenciasController.estilo);
//            stage.centerOnScreen();
//            stage.setMinWidth(700);
//            stage.setMinHeight(450);
//            stage.initOwner(janela);
//            stage.initModality(Modality.WINDOW_MODAL);
//            TelaCadastroExameRealizadoController cont = loader.getController();
//            cont.ajustarTela(internado, diaria);
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    public ObservableList<ExameRealizado> cadastrarExameRealizado(Window janela, Internado internado, DiariaInternacao diaria, ObservableList<ExameRealizado> examesDestino) {
        try {
            URL url = getClass().getResource("TelaCadastroExameRealizado.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Exame Realizado");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(450);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroExameRealizadoController cont = loader.getController();
            cont.ajustarTela(internado, diaria, examesDestino);
            stage.showAndWait();
            ObservableList<ExameRealizado> examesRealizados = cont.getLista();
            return examesRealizados;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void cadastrarInternado(Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroInternado.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Internados");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroInternadoController cont = loader.getController();
//            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarPet(Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroPet.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Pets");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroPetController cont = loader.getController();
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarPetDoTutor(Tutor tutor, Window janela) {
        Pet pet = new Pet();
        pet.setTutorPet(tutor);
        try {
            URL url = getClass().getResource("TelaCadastroPet.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Pets");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroPetController cont = loader.getController();
            cont.setTutorPet(pet);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cadastrarPrescricao(Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroPrescricao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Prescrição");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1300);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroPrescricaoController cont = loader.getController();
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cadastrarPrescricaoDoAtendimento(Atendimento atendimento, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroPrescricao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Prescrição");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1300);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroPrescricaoController cont = loader.getController();
            cont.setAtendimento(atendimento);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cadastrarPrescricaoDoInternado(Internado internado, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroPrescricao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Prescrição");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1300);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroPrescricaoController cont = loader.getController();
            cont.setInternado(internado);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ObservableList<Estoque> cadastrarProdutoConsumido(Window janela, Internado internado, DiariaInternacao diaria, ObservableList<Estoque> produtosDestino) {
        try {
            URL url = getClass().getResource("TelaCadastroProdutoConsumido.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Produtos Consumidos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(450);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroProdutoConsumidoController cont = loader.getController();
            cont.ajustarTela(internado, diaria, produtosDestino);
            stage.showAndWait();
            ObservableList<Estoque> produtosConsumidos = cont.getLista();
            return produtosConsumidos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void cadastrarSalaEspera(Window janela) {
        try {
            URL url = getClass().getResource("TelaSalaEspera.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Sala de Espera");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaSalaEsperaController cont = loader.getController();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cadastrarSalaEsperaNovaJanela(Window janela) {
        try {
            URL url = getClass().getResource("TelaSalaEspera.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Sala de Espera");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(900);
            stage.setMinHeight(600);
            TelaSalaEsperaController cont = loader.getController();
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ObservableList<ServicoRealizado> cadastrarServicoRealizado(Window janela, Internado internado, DiariaInternacao diaria, ObservableList<ServicoRealizado> servicosDestino) {
        try {
            URL url = getClass().getResource("TelaCadastroServicoRealizado.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Serviço Realizado");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(450);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroServicoRealizadoController cont = loader.getController();
            cont.ajustarTela(internado, diaria, servicosDestino);
            stage.showAndWait();
            ObservableList<ServicoRealizado> servicosRealizados = cont.getLista();
            return servicosRealizados;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cadastrarTutor(Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroTutor.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Tutores");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroTutorController cont = loader.getController();
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarUsuario(Window janela) {
        try {
            URL url = getClass().getResource("TelaListaUsuario.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Usuários");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaListaUsuarioController cont = loader.getController();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ObservableList<Vacina> cadastrarVacina(Window janela, Internado internado, DiariaInternacao diaria, ObservableList<Vacina> vacinasDestino) {
        try {
            URL url = getClass().getResource("TelaCadastroVacina.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Vacina");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(900);
            stage.setMinHeight(700);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroVacinaController cont = loader.getController();
            cont.ajustarTela(internado, diaria, vacinasDestino);
            stage.showAndWait();
            ObservableList<Vacina> vacinasRealizadas = cont.getLista();
            return vacinasRealizadas;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cadastrarVeterinario(Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroVeterinario.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Veterinários");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroVeterinarioController cont = loader.getController();
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculadoraReposicaoHidrica(Window janela) {
        try {
            URL url = getClass().getResource("TelaReposicaoHidrica.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Calculadora de reposição hídrica e energética");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1171);
            stage.setWidth(1171);
            stage.setHeight(816);
            stage.setMinHeight(816);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void editarAtendimento(Atendimento atendimento, List<Vacina> listaVacinas, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroAtendimento.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Atendimentos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(690);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroAtendimentoController cont = loader.getController();
            cont.setAtendimento(atendimento, listaVacinas);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editarClinica(Clinica clinica, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroClinica.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Clínicas");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroClinicaController cont = loader.getController();
            cont.setClinica(clinica);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void editarInternado(Internado internado, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroInternado.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroInternadoController cont = loader.getController();
            cont.setInternado(internado);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarPet(Pet pet, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroPet.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroPetController cont = loader.getController();
            cont.setPet(pet);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void editarPrescricao(Prescricao prescricao, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroPrescricao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1300);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroPrescricaoController cont = loader.getController();
            cont.setPrescricao(prescricao);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarTutor(Tutor tutor, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroTutor.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Tutores");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroTutorController cont = loader.getController();
            cont.setTutor(tutor);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editarVeterinario(Veterinario veterinario, Window janela) {
        try {
            URL url = getClass().getResource("TelaCadastroVeterinario.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Tutores");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaCadastroVeterinarioController cont = loader.getController();
            cont.setVeterinario(veterinario);
            cont.ajustarTela();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void gerarRelatorioGastosClinica(Window janela){
        try {
            URL url = getClass().getResource("TelaRelatorioGastosClinica.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Relatório de despesas dass clínicas");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void inserirBairro(Window janela, Municipio municipio) {
        try {
            URL url = getClass().getResource("TelaInserirBairro.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir bairros");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(300);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaInserirBairroController cont = loader.getController();
            if (municipio != null) cont.setMunicipio(municipio);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirCategoriaProduto(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirCategoriaProduto.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir categoria de produto");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(200);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirEspecie(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirEspecie.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir Espécies");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(200);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirExame(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirExame.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir exames");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(300);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirMunicipio(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirMunicipio.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir Municípios");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(200);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirNomesDeVacina(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirNomeVacina.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir vacinas");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(325);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirProduto(Window janela, Produto produto) {
        try {
            URL url = getClass().getResource("TelaInserirProduto.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir produtos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(400);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaInserirProdutoController cont = loader.getController();
            if (produto != null) cont.setCategoriaDeProduto(produto);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirRaca(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirRaca.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir raças");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(300);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void inserirServico(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirServico.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir serviços");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(300);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserirTiposDeVacina(Window janela) {
        try {
            URL url = getClass().getResource("TelaInserirTipoVacina.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Inserir categoria de vacina");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.centerOnScreen();
            stage.setMinWidth(700);
            stage.setMinHeight(200);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verAtendimento(Stage janela) {
        try {
            URL url = getClass().getResource("TelaListaAtendimento.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Atendimentos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verAtendimentoFiltrado(Stage janela, Pet pet) {
        try {
            URL url = getClass().getResource("TelaListaAtendimento.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Atendimentos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            TelaListaAtendimentoController cont = loader.getController();
            cont.filtrarPorPet(pet);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void verAtendimentoDoPet(Pet pet) {
        try {
            URL url = getClass().getResource("TelaListaAtendimento.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Atendimentos");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMinWidth(1000);
            stage.setMinHeight(600);
            TelaListaAtendimentoController cont = loader.getController();
            cont.filtrarPorPet(pet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verClinica(Stage janela) {
        try {
            URL url = getClass().getResource("TelaListaClinica.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Clínicas");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void verInternacao(Stage janela) {
        try {
            URL url = getClass().getResource("TelaInternacao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Internados");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            TelaInternacaoController cont = loader.getController();
//            cont.ajustarTela();
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verPet(Stage janela) {
        try {
            URL url = getClass().getResource("TelaListaPet.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Pets");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(900);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verPetFiltrado(Stage janela, Tutor tutor) {
        try {
            URL url = getClass().getResource("TelaListaPet.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Pets");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(900);
            TelaListaPetController cont = loader.getController();
            cont.filtrarPorTutor(tutor);
//            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void verPrescricao(Stage janela) {
        try {
            URL url = getClass().getResource("TelaListaPrescricao.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Prescricoes");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verTutor(Stage janela) {
        try {
            URL url = getClass().getResource("TelaListaTutor.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Tutores");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            TelaListaTutorController cont = loader.getController();
            cont.ajustarTela();
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1200);
            stage.setMinHeight(800);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verVeterinario(Stage janela) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("TelaListaVeterinario.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Consulta de Veterinários");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
            stage.show();
            stage.setMaximized(true);
            stage.setMinWidth(1000);
            stage.setMinHeight(600);
            janela.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void telaAdministrador(Window janela) {
        try {
            URL url = getClass().getResource("TelaAdministrador.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Área do administrador");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
//            stage.setResizable(false);
            stage.setMinWidth(1100);
            stage.setMinHeight(450);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void telaPreferencias(Window janela) {
        try {
            URL url = getClass().getResource("TelaPreferencias.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Preferências");
            stage.setScene(scene);
            scene.getStylesheets().add(TelaPreferenciasController.estilo);
//            stage.setResizable(false);
            stage.setMinWidth(1200);
            stage.setMinHeight(820);
            stage.initOwner(janela);
            stage.initModality(Modality.WINDOW_MODAL);
            TelaPreferenciasController cont = loader.getController();
            cont.setDadosClinica();
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
