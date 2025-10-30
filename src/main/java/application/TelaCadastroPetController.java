package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import model.classes.utilitario.Especie;
import model.classes.Pet;
import model.classes.utilitario.Raca;
import model.classes.Tutor;
import model.exceptions.ValidacaoException;
import model.services.PetService;
import model.services.TutorService;
import view.utils.MascarasFX;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaCadastroPetController implements Initializable {

    @FXML    private Button btnCancelar;
    @FXML    private Button btnExcluir;
    @FXML    private Button btnInserirEspecie;
    @FXML    private Button btnInserirRaca;
    @FXML    private Button btnInserirTutor;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnSalvar;
    @FXML    private Button btnTermoCompromisso;
    @FXML    private Button btnVerAtendimentos;
    @FXML    private Button btnVerTutor;
    @FXML    private CheckComboBox<String> ckCmbTemperamento;
    @FXML    private DatePicker dpDataNasc;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtRfid;
    @FXML    private TextField txtPeso;
    @FXML    private TextArea txtObservacao;
    @FXML    private CheckBox ckbCastrado;
    @FXML    private CheckBox ckbVivo;
    @FXML    private CheckBox ckbAdotado;
    @FXML    private Label lblNome;
    @FXML    private Label lblEspecie;
    @FXML    private Label lblErroEspecie;
    @FXML    private Label lblErroNome;
    @FXML    private Label lblErroRaca;
    @FXML    private Label lblErroSexo;
    @FXML    private Label lblErroTutor;
    @FXML    private Label lblTemperamento;
    @FXML    private RadioButton rbSexoM;
    @FXML    private RadioButton rbSexoF;
    @FXML    private SearchableComboBox<Especie> scmbEspecie;
    @FXML    private SearchableComboBox<Raca> scmbRaca;
    @FXML    private SearchableComboBox<Tutor> scmbTutor;
    @FXML    private Spinner<Integer> spnAnos;
    @FXML    private Spinner<Integer> spnMeses;

    private Pet pet;
    private Pet petCadastrado;

    List<Especie> listaEspecies = new PetService().getEspecies();
    List<Raca> listaRacas;
    List<Tutor> listaTutor = new TutorService().getAll(-1, "");

    public void setTutorPet(Pet pet) {
        this.pet = pet;
        scmbTutor.getSelectionModel().select(pet.getTutorPet());
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        // Carregando o pet para os campos da tela
        txtNome.setText(String.valueOf(pet.getNomePet()));
        scmbEspecie.setValue(pet.getRaca().getEspecie());
        txtRfid.setText(pet.getRfid());
        if (pet.getDataNascimentoPet() != null) {
            dpDataNasc.setValue(pet.getDataNascimentoPet());
        }
        txtPeso.setText(pet.getPesoPet() + "");
        txtObservacao.setText(pet.getObservacao());
        listarRacas();
        scmbRaca.setValue(pet.getRaca());
        scmbTutor.getSelectionModel().select(pet.getTutorPet());
//        cmbTutor.setValue(pet.getTutorPet());
//        cmbTutor.setValue(pet);
        if (pet.isSexoPet()) {
            rbSexoM.setSelected(true);
        } else {
            rbSexoF.setSelected(true);
        }
        if (pet.isVivo()) {
            ckbVivo.setSelected(true);
        } else {
            ckbVivo.setSelected(false);
        }
        if (pet.isCastrado()) {
            ckbCastrado.setSelected(true);
        } else {
            ckbCastrado.setSelected(false);
        }
        if (pet.isAdotado()) {
            ckbAdotado.setSelected(true);
        } else {
            ckbAdotado.setSelected(false);
        }
        lblNome.setText(pet.getNomePet());
        lblEspecie.setText(pet.getRaca().getEspecie().getNome());
        
        if (pet.getListaTemperamento() != null){
            for (String item : pet.getListaTemperamento()) ckCmbTemperamento.getCheckModel().check(item);
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraData(dpDataNasc);
        MascarasFX.mascaraNumero(txtPeso);
        MascarasFX.mascaraNumeroInteiro(txtRfid);
        
        SpinnerValueFactory<Integer> valueFactoryAnos = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 30, 0, 1);
        SpinnerValueFactory<Integer> valueFactoryMeses = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 11, 0, 1);
        
        spnAnos.setValueFactory(valueFactoryAnos);
        spnMeses.setValueFactory(valueFactoryMeses);
        
        spnAnos.valueProperty().addListener((o, oldValue, newValue) -> atualizarData() );
        spnMeses.valueProperty().addListener((o, oldValue, newValue) -> atualizarData() );
        
        //Tooltips
        Tooltip dicaAdicionarTutor = new Tooltip("Cadastrar novo tutor");
        dicaAdicionarTutor.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirTutor.setTooltip(dicaAdicionarTutor);
        
        Tooltip dicaAdicionarEspecie = new Tooltip("Cadastrar nova espécie");
        dicaAdicionarEspecie.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirEspecie.setTooltip(dicaAdicionarEspecie);
        
        Tooltip dicaAdicionarRaça = new Tooltip("Cadastrar nova raça");
        dicaAdicionarRaça.setShowDelay(javafx.util.Duration.ZERO);
        btnInserirRaca.setTooltip(dicaAdicionarRaça);

        btnInserirEspecie.setOnAction((t) -> {
            new MenuPrincipal().inserirEspecie(btnLimpar.getScene().getWindow());
            listarEspecies();
        });
        
        btnInserirRaca.setOnAction((t) -> {
            new MenuPrincipal().inserirRaca(btnLimpar.getScene().getWindow());
            listarRacas();
        });
        
        btnInserirTutor.setOnAction((t) -> {
            new MenuPrincipal().cadastrarTutor(btnLimpar.getScene().getWindow());
            listarTutores();
        });
        
        btnSalvar.setDefaultButton(true);

        btnLimpar.setOnAction((t) -> {
            limpaCampos();
        });

        btnExcluir.setOnAction((t) -> {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION);
            al.setTitle("Confirmação");
            al.setContentText("O pet " + pet.getNomePet() + " será excluído! Tem certeza?");
            if (al.showAndWait().get() == ButtonType.OK) {
                if (new PetService().excluir(pet)) {
                    Alert mens = new Alert(Alert.AlertType.INFORMATION);
                    mens.initOwner(btnSalvar.getScene().getWindow());
                    mens.setTitle("Excluído");
                    mens.setContentText("Registro excluído com sucesso!");
                    mens.showAndWait();
                    ((Stage) btnSalvar.getScene().getWindow()).close();
                }
            }
        });

        btnVerTutor.setOnAction((t) -> {
            Tutor tutor = pet.getTutorPet();
            new MenuPrincipal().editarTutor(tutor, btnSalvar.getScene().getWindow());
        });
        
        btnVerAtendimentos.setOnAction((t) -> {
            
        });

        btnCancelar.setOnAction((t) -> {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        });

        btnSalvar.setOnAction((t) -> {

            //Testa se é uma inserção ou edição
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                if (pet == null) {
                    pet = new Pet();
                }

                //Insere os valores nos atributos não obrigatorios
                pet.setRfid(txtRfid.getText());
                if (!(txtPeso.getText().equals("") || txtPeso.getText() == null)) {
                    pet.setPesoPet(Double.parseDouble(txtPeso.getText()));
                }
                if (dpDataNasc != null) {
                    pet.setDataNascimentoPet(dpDataNasc.getValue());
                } else {
                    pet.setDataNascimentoPet(null);
                }
                pet.setObservacao(txtObservacao.getText().trim());
                if (ckbCastrado.isSelected()) {
                    pet.setCastrado(true);
                } else {
                    pet.setCastrado(false);
                }
                if (ckbVivo.isSelected()) {
                    pet.setVivo(true);
                } else {
                    pet.setVivo(false);
                }
                if (ckbAdotado.isSelected()) {
                    pet.setAdotado(true);
                } else {
                    pet.setAdotado(false);
                }

                if (rbSexoM.isSelected()) {
                    pet.setSexoPet(true);
                } else {
                    pet.setSexoPet(false);
                }
                
                List<String> listaTemperamento = ckCmbTemperamento.getCheckModel().getCheckedItems();
                pet.setListaTemperamento(listaTemperamento);

                //Testa se os atributos obrigatorios foram preenchidos
                if (txtNome.getText() == null || txtNome.getText().equals("")) {
                    exc.adicionarErro("Nome", "Insira um nome!");
                    System.out.println("Erro no nome");
                } else {
                    //Se estiver preenchido, então atualiza o objeto com o nome
                    pet.setNomePet(txtNome.getText());
                }

                if (scmbEspecie.getValue() != null) {
//                    pet.setEspecie(cmbEspecie.getValue().toString());
                } else {
                    exc.adicionarErro("Especie", "Selecione uma espécie!");
                    System.out.println("erro na especie");
                }

                if (scmbRaca.getValue() != null) {
                    pet.setRaca(scmbRaca.getValue());
                } else {
                    exc.adicionarErro("Raca", "Selecione uma raça!");
                    System.out.println("erro na raça");
                }
                if (scmbTutor.getValue() != null) {
                    pet.setTutorPet(scmbTutor.getValue());
                } else {
                    exc.adicionarErro("Tutor", "Selecione um tutor!");
                    System.out.println("erro no tutor");
                }


                // Ao final de todos os testes de campos, é necessário verificar se existem erros.
                // Se existire, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                if (new PetService().salvarOuAtualizar(pet)) {
//                    if (rbFilaSim.isSelected()) {
//                        new FilaCastracaoService().incluirNaFila(pet);
//                    }
//                    if (rbFilaNao.isSelected()) {
//                        new FilaCastracaoService().removerDaFila(pet);
//                    }

                    // Deu certo
                    // Posso fechar a janela
                    this.petCadastrado = pet;
                    ((Stage) btnCancelar.getScene().getWindow()).close();
                } else {
                    // Deu erro. O retorno do boolean veio false
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("ERRO");
                    al.setContentText("Ocorreu um erro ao inserir!");
                    al.showAndWait();
                }

            } catch (ValidacaoException e) {
                System.out.println("Erro na validação");
                e.printStackTrace();
                setErrorMessages(e.getErrors());
            }
        });
        
        ObservableList<String> listaObsTemperamento = FXCollections.observableArrayList("Afetuoso", "Agressivo", "Assustado",
                 "Covarde", "Curioso", "Dócil", "Estressado","Feroz", "Hiperativo", "Preguiçoso", "Territorialista", "Tímido", "Tranquilo");
        ckCmbTemperamento.getItems().addAll(listaObsTemperamento);
        
        // Vinculando o Label aos itens selecionados
        lblTemperamento.textProperty().bind(Bindings.createStringBinding(() -> {
            // Obtém os itens selecionados
            ObservableList<String> selectedItems = ckCmbTemperamento.getCheckModel().getCheckedItems();
            // Retorna os itens formatados como string
            return "Selecionados: " + String.join(", ", selectedItems);
        }, ckCmbTemperamento.getCheckModel().getCheckedItems()));
        
        scmbEspecie.setOnAction((t) -> {
            listarRacas();
            scmbRaca.setDisable(false);
        });

        listarTutores();
        listarEspecies();

    }

    public void ajustarTela() {
        if (pet == null || pet.getIdPet() == 0) {
            btnExcluir.setVisible(false);
            btnVerAtendimentos.setVisible(false);
            btnVerTutor.setVisible(false);
            lblNome.setText("");
            lblEspecie.setText("");
            btnTermoCompromisso.setVisible(false);
            scmbRaca.setDisable(true);
            ckbVivo.setSelected(true);
        } else {
            btnExcluir.setVisible(true);
            btnTermoCompromisso.setVisible(false);
        }
    }
    
    private void atualizarData(){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataCorrigida = dataAtual.minusYears(spnAnos.getValue()).minusMonths(spnMeses.getValue());
        
        dpDataNasc.setValue(dataCorrigida);
    }

    private void listarTutores() {
        ObservableList<Tutor> listaObs = FXCollections.observableArrayList(listaTutor);
        scmbTutor.setItems(listaObs);
    }

    private void listarEspecies() {
        ObservableList<Especie> listaObsEsp = FXCollections.observableArrayList(listaEspecies);
        scmbEspecie.setItems(listaObsEsp);
    }

    private void listarRacas() {
        if (scmbEspecie.getSelectionModel().getSelectedIndex() != -1) {
            listaRacas = new PetService().getRacas(scmbEspecie.getValue());
            ObservableList<Raca> listaObsRaca = FXCollections.observableArrayList(listaRacas);
            scmbRaca.setItems(listaObsRaca);
        }
    }

    private void limpaCampos() {
        txtNome.setText("");
        txtRfid.setText("");
        dpDataNasc.setValue(null);
        txtPeso.setText("");
        scmbEspecie.setValue(null);
        scmbRaca.setValue(null);
        scmbTutor.setValue(null);
        rbSexoF.setSelected(false);
        rbSexoM.setSelected(false);
        ckbCastrado.setSelected(false);
        ckbVivo.setSelected(false);
        ckbAdotado.setSelected(false);
    }

    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();
        // Mostrar o erro no label que definimos
        lblErroNome.setText(campos.contains("Nome") ? errors.get("Nome") : "");
        lblErroSexo.setText(campos.contains("Sexo") ? errors.get("Sexo") : "");
        lblErroRaca.setText(campos.contains("Raca") ? errors.get("Raca") : "");
        lblErroTutor.setText(campos.contains("Tutor") ? errors.get("Tutor") : "");
        lblErroEspecie.setText(campos.contains("Especie") ? errors.get("Especie") : "");
    }
    
    public Pet getPetCadastrado(){
        return this.petCadastrado;
    }
}
