package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Administrador;
import entities.Participante;
import entities.Usuario;

public class UsuarioDAO extends DAO {

	// Adicionar um usuário
	public void adicionarUsuario(Usuario usuario) throws SQLException {
		String sql = "INSERT INTO usuarios (id, nome, email, senha, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, usuario.getId());
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, usuario.getEmail());
			stmt.setString(4, usuario.getSenha());
			stmt.setString(5, usuario.getTipoUsuario());
			stmt.executeUpdate();
		}
	}

	public void atualizarParticipante(Usuario usuario, Participante participante) throws SQLException {
		String sql = "UPDATE usuarios SET cpf = ?, data_nascimento = ? WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, participante.getCpf());
			stmt.setDate(2, new java.sql.Date(participante.getDataNascimento().getTime()));
			stmt.setInt(3, usuario.getId());
			stmt.executeUpdate();
		}
	}

	public void atualizarAdministrador(Usuario usuario, Administrador administrador) throws SQLException {
		String sql = "UPDATE usuarios SET cargo = ?, data_contratacao = ? WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, administrador.getCargo());
			stmt.setDate(2, new java.sql.Date(administrador.getDataContratacao().getTime()));
			stmt.setInt(3, usuario.getId());
			stmt.executeUpdate();
		}
	}

	// Atualizar um usuário
	public void atualizarUsuario(Usuario usuario) throws SQLException {
		String sql = "UPDATE usuarios SET nome = ?, email = ?, senha = ?, tipo_usuario = ? WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getSenha());
			stmt.setString(4, usuario.getTipoUsuario());
			stmt.setInt(5, usuario.getId());
			stmt.executeUpdate();
		}
	}

	// Deletar um usuário
	public void deletarUsuario(int id) throws SQLException {
		String sql = "DELETE FROM usuarios WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	// Buscar um usuário por ID
	public Usuario buscarUsuarioPorId(int id) throws SQLException {
		String sql = "SELECT * FROM usuarios WHERE id = ?";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return mapearUsuario(rs);
			}
			return null;
		}
	}

	// Buscar todos os usuários
	public List<Usuario> buscarTodosUsuarios() throws SQLException {
		String sql = "SELECT * FROM usuarios";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			List<Usuario> usuarios = new ArrayList<>();
			while (rs.next()) {
				usuarios.add(mapearUsuario(rs));
			}
			return usuarios;
		}
	}

	// Método auxiliar para mapear o ResultSet para um objeto Usuario
	private Usuario mapearUsuario(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String nome = rs.getString("nome");
		String email = rs.getString("email");
		String senha = rs.getString("senha");
		String tipoUsuario = rs.getString("tipo_usuario");

		// Verificando se o tipo de usuário é Participante ou Administrador
		if ("Participante".equals(tipoUsuario)) {
			return new Participante(id, nome, email, senha, rs.getDate("data_nascimento"), rs.getString("cpf"));
		} else if ("Administrador".equals(tipoUsuario)) {
			return new Administrador(id, nome, email, senha, rs.getString("cargo"), rs.getDate("data_contratacao"));
		}

		return new Usuario(id, nome, email, senha, tipoUsuario);
	}
	
    public Usuario autenticarUsuario(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTipoUsuario(rs.getString("tipo_usuario"));
                return usuario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
