package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entities.Relatorio;

public class RelatorioDAO extends DAO {
    public void adicionarRelatorio(Relatorio relatorio) throws SQLException {
        String sql = "INSERT INTO relatorios (id, usuario_id, evento_id, tipo, data_geracao, conteudo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, relatorio.getId());
        	stmt.setInt(2, relatorio.getUsuarioId());
        	stmt.setInt(3, relatorio.getEventoId());
            stmt.setString(4, relatorio.getTipo());
            stmt.setTimestamp(5, new Timestamp(relatorio.getDataGeracao().getTime()));
            stmt.setString(6, relatorio.getConteudo());
            stmt.executeUpdate();
        }
    }

    public Relatorio obterRelatorioPorId(int id) throws SQLException {
        String sql = "SELECT * FROM relatorios WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Relatorio(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("evento_id"),
                        rs.getString("tipo"),
                        rs.getTimestamp("data_geracao"),
                        rs.getString("conteudo")
                    );
                }
            }
        }
        return null;
    }

    public List<Relatorio> listarRelatorios() throws SQLException {
        List<Relatorio> relatorios = new ArrayList<>();
        String sql = "SELECT * FROM relatorios";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                relatorios.add(new Relatorio(
                		rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("evento_id"),
                        rs.getString("tipo"),
                        rs.getTimestamp("data_geracao"),
                        rs.getString("conteudo")
                ));
            }
        }
        return relatorios;
    }

    public void excluirRelatorio(int id) throws SQLException {
        String sql = "DELETE FROM relatorios WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public List<Relatorio> obterRelatorioParticipantesPorEvento(int eventoId) throws SQLException {
        List<Relatorio> relatorios = new ArrayList<>();
        String sql = "SELECT i.usuario_id, u.nome, u.email FROM inscricoes i JOIN usuarios u ON i.usuario_id = u.id WHERE i.evento_id = ? AND i.presenca_confirmada = TRUE";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	Random gerador = new Random();
                    int id = gerador.nextInt(500);
                    relatorios.add(new Relatorio(
                    		id, rs.getInt("usuario_id"), eventoId, "Participantes Evento", new Timestamp(System.currentTimeMillis()),
                        "Nome: " + rs.getString("nome") + ", Email: " + rs.getString("email")
                    ));
                }
            }
        }
        return relatorios;
    }
}