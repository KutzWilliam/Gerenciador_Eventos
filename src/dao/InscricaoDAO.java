package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entities.Inscricao;

public class InscricaoDAO extends DAO {
    public void adicionarInscricao(Inscricao inscricao) throws SQLException {
        String sql = "INSERT INTO inscricoes (id, usuario_id, evento_id, data_inscricao, status, presenca_confirmada) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, inscricao.getId());
            stmt.setInt(2, inscricao.getUsuarioId());
            stmt.setInt(3, inscricao.getEventoId());
            stmt.setDate(4, new java.sql.Date(inscricao.getDataInscricao().getTime()));
            stmt.setString(5, inscricao.getStatus());
            stmt.setBoolean(6, inscricao.isPresencaConfirmada());
            stmt.executeUpdate();
        }
    }

    public void atualizarInscricao(Inscricao inscricao) throws SQLException {
        String sql = "UPDATE inscricoes SET status = ?, presenca_confirmada = ? WHERE usuario_id = ? AND evento_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, inscricao.getStatus());
            stmt.setBoolean(2, inscricao.isPresencaConfirmada());
            stmt.setInt(3, inscricao.getUsuarioId());
            stmt.setInt(4, inscricao.getEventoId());
            stmt.executeUpdate();
        }
    }

    public void removerInscricao(int usuarioId, int eventoId) throws SQLException {
        String sql = "DELETE FROM inscricoes WHERE usuario_id = ? AND evento_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, eventoId);
            stmt.executeUpdate();
        }
    }

    public List<Inscricao> listarInscricoes() throws SQLException {
        List<Inscricao> inscricoes = new ArrayList<>();
        String sql = "SELECT * FROM inscricoes";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Inscricao inscricao = new Inscricao(
                	rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("evento_id"),
                    rs.getDate("data_inscricao"),
                    rs.getString("status"),
                    rs.getBoolean("presenca_confirmada")
                );
                inscricoes.add(inscricao);
            }
        }
        return inscricoes;
    }
    
    public List<Inscricao> buscarInscricaoIdUsuario(int id) throws SQLException {
    	List<Inscricao> inscricoes = new ArrayList<>();
        String sql = "SELECT * FROM inscricoes WHERE usuario_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, id);
        	ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inscricao inscricao = new Inscricao(
                	rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("evento_id"),
                    rs.getDate("data_inscricao"),
                    rs.getString("status"),
                    rs.getBoolean("presenca_confirmada")
                );
                inscricoes.add(inscricao);
            }
        }
        return inscricoes;
    }
    
    public Inscricao buscarInscricaoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM inscricoes WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Inscricao(
                    		rs.getInt("id"),
                            rs.getInt("usuario_id"),
                            rs.getInt("evento_id"),
                            rs.getDate("data_inscricao"),
                            rs.getString("status"),
                            rs.getBoolean("presenca_confirmada")
                    );
                }
            }
        }
        return null;
    }
    
    public List<Inscricao> buscarInscricaoIdEvento(int id) throws SQLException {
    	List<Inscricao> inscricoes = new ArrayList<>();
        String sql = "SELECT * FROM inscricoes WHERE evento_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, id);
        	ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Inscricao inscricao = new Inscricao(
                	rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("evento_id"),
                    rs.getDate("data_inscricao"),
                    rs.getString("status"),
                    rs.getBoolean("presenca_confirmada")
                );
                inscricoes.add(inscricao);
            }
        }
        return inscricoes;
    }
}
