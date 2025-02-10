package service;

import java.sql.SQLException;
import java.util.List;

import dao.UsuarioDAO;
import entities.Administrador;
import entities.Participante;
import entities.Usuario;

public class UsuarioController {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Cadastrar um usuário
    public void cadastrarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.adicionarUsuario(usuario);
    }
    
    public void atualizarParticipante(Usuario usuario, Participante participante) throws SQLException {
        usuarioDAO.atualizarParticipante(usuario, participante);
    }
    
    public void atualizarAdministrador(Usuario usuario, Administrador administrador) throws SQLException {
        usuarioDAO.atualizarAdministrador(usuario, administrador);
    }

    // Atualizar um usuário
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.atualizarUsuario(usuario);
    }

    // Deletar um usuário
    public void deletarUsuario(int id) throws SQLException {
        usuarioDAO.deletarUsuario(id);
    }

    // Buscar um usuário por ID
    public Usuario buscarUsuarioPorId(int id) throws SQLException {
        return usuarioDAO.buscarUsuarioPorId(id);
    }

    // Buscar todos os usuários
    public List<Usuario> buscarTodosUsuarios() throws SQLException {
        return usuarioDAO.buscarTodosUsuarios();
    }
    
    public void autenticarUsuario(String email, String senha) throws SQLException {
        usuarioDAO.autenticarUsuario(email, senha);
    }
}
