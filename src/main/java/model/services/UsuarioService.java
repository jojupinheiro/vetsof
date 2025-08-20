package model.services;

import java.util.List;
import model.classes.Usuario;
import model.dao.UsuarioDAO;
import model.db.DB;

/**
 *
 * @author juliano
 */
public class UsuarioService {
    private UsuarioDAO dao;

    public UsuarioService() {
        dao = new UsuarioDAO(DB.getConnection());
    }

    public List<Usuario> getAll(int filtroSelecionado, String txtFiltro) {
        return dao.getAll(filtroSelecionado, txtFiltro);
    }

    public boolean salvarOuAtualizar(Usuario usuario) {
        //tenho que testar se é uma inclusão ou uma alteração
        if (usuario.getIdUsuario() <= 0) {
            //é inclusão
            return dao.inserir(usuario);

        } else {
            // é uma alteração
            return dao.editar(usuario);
        }
    }
    
    public boolean excluir(Usuario usuario){
        return dao.excluir(usuario);
    }
    
    public Usuario efetuarLogin(Usuario usuario){
        return dao.efetuarLogin(usuario);
    }
    
}


