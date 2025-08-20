package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.classes.Administrador;
import model.classes.Funcionario;
import model.classes.Usuario;
import model.exceptions.ValidacaoException;
import model.services.UsuarioService;
import view.utils.MascarasFX;

/**
 * FXML Controller class
 *
 * @author juliano
 */
public class TelaListaUsuarioController implements Initializable {

    @FXML    private MenuItem btnCadastrarAtendimento;
    @FXML    private MenuItem btnCadastrarClinica;
    @FXML    private MenuItem btnCadastrarPet;
    @FXML    private MenuItem btnCadastrarTutor;
    @FXML    private MenuItem btnCadastrarVeterinario;
    @FXML    private MenuItem btnCadastrarUsuario;
    @FXML    private MenuItem btnAdministrador;
    @FXML    private Button btnCancelar;
    @FXML    private Button btnFiltrar;
    @FXML    private Button btnLimpar;
    @FXML    private Button btnLimparCampos;
    @FXML    private Button btnSalvar;
    @FXML    private MenuItem btnVerAtendimento;
    @FXML    private MenuItem btnVerClinica;
    @FXML    private MenuItem btnVerPet;
    @FXML    private MenuItem btnVerTutor;
    @FXML    private MenuItem btnVerVeterinario;
    @FXML    private ComboBox<String> cmbFiltro;
    @FXML    private ComboBox cmbTipoUsuario;
    @FXML    private Menu menuPropriedades;
    @FXML    private TableColumn<Administrador, String> tableColumnCargo;
    @FXML    private TableColumn<Usuario, String> tableColumnEmail;
    @FXML    private TableColumn<Usuario, String> tableColumnNome;
    @FXML    private TableColumn<Usuario, Boolean> tableColumnTipoUsuario;
    @FXML    private TableColumn<Usuario, String> tableColumnUsuario;
    @FXML    private TableView<Usuario> tblUsuario;
    @FXML    private TextField txtBusca;
    @FXML    private TextField txtCargo;
    @FXML    private TextField txtEmail;
    @FXML    private TextField txtLogin;
    @FXML    private TextField txtNome;
    @FXML    private TextField txtSenha;

    List<Usuario> listaUsuarios;
    private String txtFiltro;
    private int filtroSelecionado = -1;
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;

        if (usuario.isTipoUsuario()) {
            cmbTipoUsuario.getSelectionModel().select(0);
        } else {
            cmbTipoUsuario.getSelectionModel().select(1);
        }
        txtNome.setText(usuario.getNomeUsuario());
        txtLogin.setText(usuario.getLogin());
        txtEmail.setText(usuario.getEmail());
        txtSenha.setText(usuario.getSenha());
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        MascarasFX.mascaraEmail(txtEmail);

        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));
        tableColumnUsuario.setCellValueFactory(new PropertyValueFactory<>("login"));
        tableColumnCargo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Administrador, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Administrador, String> p) {
                if (p.getValue() instanceof Administrador) {
                    return new ObservableValue<String>() {
                        @Override
                        public void removeListener(InvalidationListener arg0) {
                        }

                        @Override
                        public void addListener(InvalidationListener arg0) {
                        }

                        @Override
                        public void removeListener(ChangeListener<? super String> listener) {
                        }

                        @Override
                        public String getValue() {
                            return ((Administrador) p.getValue()).getCargo();
                        }

                        @Override
                        public void addListener(ChangeListener<? super String> listener) {
                        }
                    };
                }
                return null;
            }
        }
        );
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));

        btnAdministrador.setOnAction((t) -> {
            new MenuPrincipal().telaAdministrador(btnFiltrar.getScene().getWindow());
        });

        btnCadastrarAtendimento.setOnAction((t) -> {
            new MenuPrincipal().cadastrarAtendimento(btnFiltrar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        btnCadastrarClinica.setOnAction((t) -> {
            new MenuPrincipal().cadastrarClinica(btnFiltrar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        btnCadastrarPet.setOnAction((t) -> {
            new MenuPrincipal().cadastrarPet(btnLimpar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        btnCadastrarTutor.setOnAction((t) -> {
            new MenuPrincipal().cadastrarTutor(btnFiltrar.getScene().getWindow());
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        btnCadastrarUsuario.setOnAction((t) -> {
            new MenuPrincipal().cadastrarUsuario(btnFiltrar.getScene().getWindow());
        });
        
        btnCadastrarVeterinario.setOnAction((t) -> {
            new MenuPrincipal().cadastrarVeterinario(btnFiltrar.getScene().getWindow());
        });

        btnVerAtendimento.setOnAction((t) -> {
            new MenuPrincipal().verAtendimento((Stage) btnFiltrar.getScene().getWindow());
        });

        btnVerClinica.setOnAction((t) -> {
            new MenuPrincipal().verClinica((Stage) btnFiltrar.getScene().getWindow());
        });

        btnVerPet.setOnAction((t) -> {
            new MenuPrincipal().verPet((Stage) btnFiltrar.getScene().getWindow());
        });

        btnVerTutor.setOnAction((t) -> {
            new MenuPrincipal().verTutor((Stage) btnFiltrar.getScene().getWindow());
        });
        
        btnVerVeterinario.setOnAction((t) -> {
            new MenuPrincipal().verVeterinario((Stage) btnFiltrar.getScene().getWindow());
        });

        btnSalvar.setOnAction((t) -> {
            try {
                ValidacaoException exc = new ValidacaoException("Erro validando!!");
                String nome = "";
                String email = "";
                String senha = "";
                String cargo;
                int idFuncional = 0;
                String login = "";
                boolean tipoUsuario;

                if (txtNome.getText().equals("")) {
                    exc.adicionarErro("Nome", "Insira um nome!");
                } else {
                    nome = txtNome.getText();
                }

                if (txtEmail.getText().equals("")) {
                    exc.adicionarErro("email", "Insira um E-mail!");
                } else {
                    email = txtEmail.getText();
                }

                if (txtSenha.getText().equals("")) {
                    exc.adicionarErro("Senha", "Insira uma senha!");
                } else {
                    senha = txtSenha.getText();
                }

                if (txtLogin.getText().equals("")) {
                    exc.adicionarErro("Login", "Insira um login!");
                } else {
                    login = txtLogin.getText();
                }

                tipoUsuario = false;
                if (cmbTipoUsuario.getSelectionModel().getSelectedIndex() == 0) {
                    tipoUsuario = true;
                } else if (cmbTipoUsuario.getSelectionModel().getSelectedIndex() == 1) {
                    tipoUsuario = false;
                } else {
                    exc.adicionarErro("TipoUsuario", "Selecione um tipo de usuário!");
                }

                cargo = txtCargo.getText();

                if (usuario == null) {
                    if (tipoUsuario) { // Administrador
                        usuario = new Administrador(cargo, nome, senha, email, tipoUsuario, login);
                    } else {
                        usuario = new Funcionario(nome, senha, email, tipoUsuario, login);
                    }
                }

                // Ao final de todos os testes de campos, é necessário verificar se existem erros.
                // Se existire, aí sim eu disparo uma EXCEPTION que será capturada pelo CATCH
                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }

                if (new UsuarioService().salvarOuAtualizar(usuario)) {
                    // Deu certo
                    // Posso fechar a janela
//                    ((Stage) btnCancelar.getScene().getWindow()).close();
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
            atualizaTabela(filtroSelecionado, txtFiltro);
        });

        btnLimparCampos.setOnAction((t) -> {
            cmbTipoUsuario.getSelectionModel().select(-1);
            txtNome.setText("");
            txtCargo.setText("");
            txtLogin.setText("");
            txtEmail.setText("");
            txtSenha.setText("");
        });

        btnCancelar.setOnAction((t) -> {
            ((Stage) btnCancelar.getScene().getWindow()).close();
        });

        if (Principal.usuarioLogado.isTipoUsuario()) {
            menuPropriedades.setVisible(true);
            btnCadastrarUsuario.setVisible(true);
        }

        List<String> tipos = new ArrayList();
        tipos.add("Administrador");
        tipos.add("Comum");
        ObservableList<String> listaTipos = FXCollections.observableArrayList(tipos);
        cmbTipoUsuario.setItems(listaTipos);
        atualizaTabela(filtroSelecionado, txtFiltro);

    }

    public void atualizaTabela(int filtroSelecionado, String txtFiltro) {
        // Buscar os dados no banco de dados na tabela pet
        listaUsuarios = new UsuarioService().getAll(filtroSelecionado, txtFiltro);
        // ObservableList
        ObservableList<Usuario> listaObs = FXCollections.observableArrayList(listaUsuarios);
        //Vinculando a lista observável com a TableView
        tblUsuario.setItems(listaObs);
    }

    private void setErrorMessages(Map<String, String> errors) {
        // Pegar todos os campos de erro
        Set<String> campos = errors.keySet();
        // Mostrar o erro no label que definimos
//        lblErroNome.setText(campos.contains("Nome") ? errors.get("Nome") : "");
//        lblErroSexo.setText(campos.contains("Sexo") ? errors.get("Sexo") : "");
//        lblErroRaca.setText(campos.contains("Raca") ? errors.get("Raca") : "");
//        lblErroTutor.setText(campos.contains("Tutor") ? errors.get("Tutor") : "");
//        lblErroEspecie.setText(campos.contains("Especie") ? errors.get("Especie") : "");
    }
}
