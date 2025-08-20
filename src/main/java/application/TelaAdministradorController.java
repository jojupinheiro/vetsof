package application;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import model.classes.utilitario.Bairro;
import model.classes.utilitario.Especie;
import model.classes.Exame;
import model.classes.utilitario.Municipio;
import model.classes.utilitario.Raca;
import model.classes.Servico;
import model.services.ExameService;
import model.services.PetService;
import model.services.ServicoService;
import model.services.UtilitarioService;
import view.utils.MascarasFX;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaAdministradorController implements Initializable {

    @FXML    private Button btnDeletarBairro;
    @FXML    private Button btnDeletarEspecie;
    @FXML    private Button btnDeletarExame;
    @FXML    private Button btnDeletarMunicipio;
    @FXML    private Button btnDeletarRaca;
    @FXML    private Button btnDeletarServico;
    @FXML    private Button btnSalvarBairro;
    @FXML    private Button btnSalvarEspecie;
    @FXML    private Button btnSalvarExame;
    @FXML    private Button btnCarregarExame;
    @FXML    private Button btnLimparExame;
    @FXML    private Button btnSalvarMunicipio;
    @FXML    private Button btnSalvarRaca;
    @FXML    private Button btnSalvarServico;
    @FXML    private Button btnCarregarServico;
    @FXML    private Button btnLimparServico;
    @FXML    private Button btnUsuarios;
    @FXML    private ComboBox<Bairro> cmbBairro;
    @FXML    private ComboBox<Municipio> cmbMunicipio;
    @FXML    private ComboBox<Municipio> cmbInserirMunicipioBairro;
    @FXML    private ComboBox<Municipio> cmbMunicipioBairro;
    @FXML    private ComboBox<Especie> cmbEspecie;
    @FXML    private ComboBox<Especie> cmbEspecieRaca;
    @FXML    private ComboBox<Exame> cmbExame;
    @FXML    private ComboBox<Especie> cmbInserirEspecieRaca;
    @FXML    private ComboBox<Raca> cmbRaca;
    @FXML    private ComboBox<Servico> cmbServico;
    @FXML    private Label lblErroBairro;
    @FXML    private Label lblErroDeletarBairro;
    @FXML    private Label lblErroDeletarEspecie;
    @FXML    private Label lblErroDeletarMunicipio;
    @FXML    private Label lblErroDeletarRacaEspecie;
    @FXML    private Label lblErroDeletarTipoExame;
    @FXML    private Label lblErroDeletarTipoServico;
    @FXML    private Label lblErroEspecie;
    @FXML    private Label lblErroExame;
    @FXML    private Label lblErroMunicipio;
    @FXML    private Label lblErroRacaEspecie;
    @FXML    private Label lblErroServico;
    @FXML    private TextField txtBairro;
    @FXML    private TextField txtMunicipio;
    @FXML    private TextField txtEstado;
    @FXML    private TextField txtEspecie;
    @FXML    private TextField txtExame;
    @FXML    private TextField txtValorExame;
    @FXML    private TextArea txtDescricaoExame;
    @FXML    private TextField txtRaca;
    @FXML    private TextField txtServico;
    @FXML    private TextField txtValorServico;
    @FXML    private TextArea txtDescricaoServico;

    List<Especie> listaEspecies = new PetService().getEspecies();
    List<Raca> listaRacas;

    Servico servico;
    Exame exame;

    private void setServico(Servico servico) {
        this.servico = servico;

        txtServico.setText(servico.getNomeServico());
        txtDescricaoServico.setText(servico.getDescricaoServico());
        txtValorServico.setText(String.valueOf(servico.getValorServico()));
        lblErroServico.setText("ID: " + servico.getIdServico());
    }

    private void setExame(Exame exame) {
        this.exame = exame;

        txtExame.setText(exame.getNomeExame());
        txtDescricaoExame.setText(exame.getDescricaoExame());
        txtValorExame.setText(String.valueOf(exame.getValorExame()));
        lblErroExame.setText("ID: " + exame.getIdExame());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraNumero(txtValorExame);
        MascarasFX.mascaraNumero(txtValorServico);

        btnUsuarios.setOnAction((t) -> {
            new MenuPrincipal().cadastrarUsuario(btnUsuarios.getScene().getWindow());
        });
        
        txtMunicipio.setOnAction((t) -> {
            salvarMunicipio();
        });

        txtEstado.setOnAction((t) -> {
            salvarMunicipio();
        });

        btnSalvarMunicipio.setOnAction((t) -> {
            salvarMunicipio();
        });

        txtBairro.setOnAction((t) -> {
            salvarBairro();
        });

        btnSalvarBairro.setOnAction((t) -> {
            salvarBairro();
        });

        txtExame.setOnAction((t) -> {
            salvarExame();
        });

        txtDescricaoExame.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                salvarExame();
            }
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtValorExame.requestFocus();
            }
        });

        txtValorExame.setOnAction((t) -> {
            salvarExame();
        });

        btnSalvarExame.setOnAction((t) -> {
            salvarExame();
        });

        txtDescricaoServico.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                salvarServico();
            }
            if (keyEvent.getCode() == KeyCode.TAB) {
                txtValorServico.requestFocus();
            }
        });

        txtValorServico.setOnAction((t) -> {
            salvarServico();
        });

        txtServico.setOnAction((t) -> {
            salvarServico();
        });

        btnSalvarServico.setOnAction((t) -> {
            salvarServico();
        });

        btnSalvarEspecie.setOnAction((t) -> {
            salvarEspecie();
        });

        txtEspecie.setOnAction((t) -> {
            salvarEspecie();
        });

        txtRaca.setOnAction((t) -> {
            salvarRaca();
        });

        btnSalvarRaca.setOnAction((t) -> {
            salvarRaca();
        });

        btnCarregarServico.setOnAction((t) -> {
            if (cmbServico.getSelectionModel().getSelectedIndex() == -1) {
                lblErroServico.setText("Selecione um serviço!");
            } else {
                //Retorna o objeto serviço indicado pelo comboBox, e o carrega nos campos de texto da janela
                setServico(new ServicoService().getServico(cmbServico.getSelectionModel().getSelectedItem().toString()));
            }
        });

        btnLimparServico.setOnAction((t) -> {
            txtServico.setText("");
            txtDescricaoServico.setText("");
            txtValorServico.setText("");
            lblErroServico.setText("");
            cmbServico.getSelectionModel().select(-1);
            servico = null;
            txtServico.requestFocus();
        });

        btnCarregarExame.setOnAction((t) -> {
            if (cmbExame.getSelectionModel().getSelectedIndex() == -1) {
                lblErroExame.setText("Selecione um exame!");
            } else {
                //Retorna o objeto serviço indicado pelo comboBox, e o carrega nos campos de texto da janela
                setExame(new ExameService().getExame(cmbExame.getSelectionModel().getSelectedItem().toString()));
            }
        });

        btnLimparExame.setOnAction((t) -> {
            txtExame.setText("");
            txtDescricaoExame.setText("");
            txtValorExame.setText("");
            lblErroExame.setText("");
            cmbExame.getSelectionModel().select(-1);
            exame = null;
            txtExame.requestFocus();
        });

        btnDeletarExame.setOnAction((t) -> {
            if (cmbExame.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o exame que deseja excluir!");
                al.showAndWait();
            } else {
                exame = (Exame) cmbExame.getSelectionModel().getSelectedItem();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(exame.getNomeExame() + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    new ExameService().excluir(exame);
                    listarExames();
                    txtExame.setText("");
                    txtDescricaoExame.setText("");
                    txtValorExame.setText("");
                    lblErroExame.setText("");
                    cmbExame.getSelectionModel().select(-1);
                    exame = null;
                    txtExame.requestFocus();
                }
            }
        });

        btnDeletarServico.setOnAction((t) -> {
            if (cmbServico.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o serviço que deseja excluir!");
                al.showAndWait();
            } else {
                servico = (Servico) cmbServico.getSelectionModel().getSelectedItem();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(servico.getNomeServico() + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    new ServicoService().excluir(servico);
                    listarServicos();
                    txtServico.setText("");
                    txtDescricaoServico.setText("");
                    txtValorServico.setText("");
                    lblErroServico.setText("");
                    cmbServico.getSelectionModel().select(-1);
                    servico = null;
                    txtServico.requestFocus();
                }
            }
        });

        btnDeletarMunicipio.setOnAction((t) -> {
            if (cmbMunicipio.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o município que deseja excluir!");
                al.showAndWait();
            } else {
                Municipio municipio = cmbMunicipio.getSelectionModel().getSelectedItem();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(municipio + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    new UtilitarioService().excluirMunicipio(municipio);
                    listarMunicipios();
                }
            }
        });

        btnDeletarBairro.setOnAction((t) -> {
            if (cmbMunicipio.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione abaixo o município de onde deseja excluir o bairro!");
                al.showAndWait();
            } else if (cmbBairro.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione o bairro que deseja excluir!");
                al.showAndWait();
            } else {
                //pegando os valores inseridos nos combobox
                Bairro bairro = cmbBairro.getSelectionModel().getSelectedItem();

                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(bairro + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    //utilizando os valores carregados para excluir do banco
                    new UtilitarioService().excluirBairro(bairro);

                    listarBairros();
                }
            }
        });

        btnDeletarEspecie.setOnAction((t) -> {
            if (cmbEspecie.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione a espécie que deseja excluir!");
                al.showAndWait();
            } else {
                Especie especieExc = cmbEspecie.getValue();
                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(especieExc + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    new PetService().excluirEspecie(especieExc);
                    listarEspecies();
                }
            }
        });

        btnDeletarRaca.setOnAction((t) -> {
            if (cmbEspecieRaca.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione a espécie da raça que deseja excluir!");
                al.showAndWait();
            } else if (cmbRaca.getSelectionModel().getSelectedIndex() == -1) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Selecione a raça que deseja excluir!");
                al.showAndWait();
            } else {
                //pegando os valores inseridos nos combobox
                Raca racaExc = cmbRaca.getValue();
                Especie especieExc = cmbEspecieRaca.getValue();

                Alert al = new Alert(Alert.AlertType.CONFIRMATION);
                al.setTitle("Confirmação");
                al.setContentText(racaExc + " será excluído! Tem certeza?");
                if (al.showAndWait().get() == ButtonType.OK) {
                    //utilizando os valores carregados para excluir do banco
                    new PetService().excluirRaca(racaExc);
                    listarEspecies();
                    listarRacas();
                }
            }
        });

        //Carregando valores do ComboBox
        cmbMunicipioBairro.setOnAction((t) -> {
            listarBairros();
        });

        cmbEspecieRaca.setOnAction((t) -> {
            listarRacas();
        });

        listarMunicipios();
        listarBairros();
        listarEspecies();
        listarRacas();
        listarServicos();
        listarExames();
    }

    private void listarServicos() {
        List<Servico> listaServicos = new ServicoService().getAll();
        ObservableList<Servico> listaObsServ = FXCollections.observableArrayList(listaServicos);
        cmbServico.setItems(listaObsServ);
    }

    private void listarExames() {
        List<Exame> listaExames = new ExameService().getAll();
        ObservableList<Exame> listaObsExame = FXCollections.observableArrayList(listaExames);
        cmbExame.setItems(listaObsExame);
    }

    private void listarBairros() {
        if (cmbMunicipioBairro.getSelectionModel().getSelectedIndex() != -1) {
            List<Bairro> listaBairros = new UtilitarioService().getBairros(cmbMunicipioBairro.getValue());
            ObservableList<Bairro> listaObsBairro = FXCollections.observableArrayList(listaBairros);
            cmbBairro.setItems(listaObsBairro);
        }
    }

    private void listarMunicipios() {
        List<Municipio> listaMunicipios = new UtilitarioService().getMunicipios();
        ObservableList<Municipio> listaObsMun = FXCollections.observableArrayList(listaMunicipios);
        cmbMunicipio.setItems(listaObsMun);
        cmbMunicipioBairro.setItems(listaObsMun);
        cmbInserirMunicipioBairro.setItems(listaObsMun);
    }

    private void listarEspecies() {
        listaEspecies = new PetService().getEspecies();
        ObservableList<Especie> listaObsEsp = FXCollections.observableArrayList(listaEspecies);
        cmbEspecie.setItems(listaObsEsp);
        cmbEspecieRaca.setItems(listaObsEsp);
        cmbInserirEspecieRaca.setItems(listaObsEsp);
    }

    private void listarRacas() {
        if (cmbEspecieRaca.getSelectionModel().getSelectedIndex() != -1) {
            listaRacas = new PetService().getRacas(cmbEspecieRaca.getValue());
            ObservableList<Raca> listaObsRaca = FXCollections.observableArrayList(listaRacas);
            cmbRaca.setItems(listaObsRaca);
        }
    }

    private void salvarMunicipio() {
        String nomeMunicipio = txtMunicipio.getText();
        String estado = txtEstado.getText();
        Municipio municipio = new Municipio(nomeMunicipio, estado);

        if (new UtilitarioService().inserirOuAtualizarMunicipio(municipio)) {
            txtMunicipio.setText("");
            txtEstado.setText("");
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Sucesso");
            al.setContentText("Município inserido com sucesso!");
            al.showAndWait();
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERRO");
            al.setContentText("Ocorreu um erro ao inserir!");
            al.showAndWait();
        }
        listarMunicipios();
        txtMunicipio.requestFocus();
    }

    private void salvarBairro() {
        if (cmbInserirMunicipioBairro.getSelectionModel().getSelectedIndex() == -1) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("ERRO");
            al.setContentText("Selecione abaixo o município aonde deseja inserir o bairro!");
            al.showAndWait();
        } else {
            Municipio municipioBairro = cmbInserirMunicipioBairro.getValue();
            String nomeBairro = txtBairro.getText().trim();
            Bairro bairro = new Bairro(nomeBairro, municipioBairro);

            if (new UtilitarioService().inserirOuAtualizarBairro(bairro)) {
                txtBairro.setText("");
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Bairro inserido com sucesso!");
                al.showAndWait();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Ocorreu um erro ao inserir!");
                al.showAndWait();
            }
            listarBairros();
        }
        txtBairro.requestFocus();
    }

    private void salvarExame() {
        if (txtExame.getText().equals("")) {
            lblErroExame.setText("Insira um nome para o exame!");
        } else {
            String nomeExame = txtExame.getText().trim();
            String descricaoExame = txtDescricaoExame.getText().trim();
            float valorExame = 0;
            if (!txtValorExame.getText().equals("")) {
                valorExame = Float.parseFloat(txtValorExame.getText());
            }
            if (exame == null) {
                exame = new Exame(nomeExame, valorExame, descricaoExame);
            }
            exame.setNomeExame(nomeExame);
            exame.setDescricaoExame(descricaoExame);
            exame.setValorExame(valorExame);
            if (new ExameService().salvarOuAtualizar(exame)) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Exame inserido com sucesso!");
                al.showAndWait();
                listarExames();
                txtExame.setText("");
                txtDescricaoExame.setText("");
                txtValorExame.setText("");
                lblErroExame.setText("");
                exame = null;
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Ocorreu um erro ao inserir!");
                al.showAndWait();
            }
        }

        txtExame.requestFocus();
    }

    private void salvarServico() {
        if (txtServico.getText().equals("")) {
            lblErroServico.setText("Insira um nome para o serviço!");
        } else {
            String nomeServico = txtServico.getText().trim();
            String descricaoServico = txtDescricaoServico.getText().trim();
            float valorServico = 0;
            if (!txtValorServico.getText().equals("")) {
                valorServico = Float.parseFloat(txtValorServico.getText());
            }
            if (servico == null) {
                servico = new Servico(nomeServico, valorServico, descricaoServico);
            }
            servico.setNomeServico(txtServico.getText());
            servico.setDescricaoServico(txtDescricaoServico.getText());
            if(!txtValorServico.getText().equals("")){
                servico.setValorServico(Float.parseFloat(txtValorServico.getText()));
            }
            if (new ServicoService().salvarOuAtualizar(servico)) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Servico inserido com sucesso!");
                al.showAndWait();
                listarServicos();
                txtServico.setText("");
                txtDescricaoServico.setText("");
                txtValorServico.setText("");
                lblErroServico.setText("");
                servico = null;
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Ocorreu um erro ao inserir!");
                al.showAndWait();
            }
        }

        txtServico.requestFocus();
    }

    private void salvarEspecie() {
        if (txtEspecie.getText().equals("")) {
            lblErroEspecie.setText("Insira um nome para a espécie!");
        } else {
            String nomeEspecie = txtEspecie.getText().trim();
            Especie especie = new Especie(nomeEspecie);
            if (new PetService().inserirEspecie(especie)) {
                txtEspecie.setText("");
                listarEspecies();
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Espécie inserida com sucesso!");
                al.showAndWait();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Ocorreu um erro ao inserir!");
                al.showAndWait();
            }
        }
    }

    private void salvarRaca() {
        if (txtRaca.getText().equals("")) {
            lblErroRacaEspecie.setText("Insira o nome da raça a inserir!");
        } else if (cmbInserirEspecieRaca.getValue() != null) {
            String nomeRaca = txtRaca.getText().trim();
            Especie especie = cmbInserirEspecieRaca.getValue();
            Raca raca = new Raca(nomeRaca, especie);
            if (new PetService().inserirRaca(raca)) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Sucesso");
                al.setContentText("Raça inserida com sucesso!");
                al.showAndWait();
                txtRaca.setText("");
                listarRacas();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("ERRO");
                al.setContentText("Ocorreu um erro ao inserir!");
                al.showAndWait();
            }
        } else {
            lblErroRacaEspecie.setText("Selecione a espécie da qual deseja inserir a raça!");
        }
        txtRaca.requestFocus();
    }

}
