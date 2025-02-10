package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entities.Evento;

public class EventoDAO extends DAO {

    // Adiciona um novo evento ao banco de dados
    public void adicionarEvento(Evento evento) throws SQLException {
        String sql = "INSERT INTO eventos (titulo, descricao, data_hora, duracao, local, capacidade_maxima, status, categoria, preco, organizador_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getDescricao());
            stmt.setTimestamp(3, new Timestamp(evento.getDataHora().getTime()));
            stmt.setInt(4, evento.getDuracao());
            stmt.setString(5, evento.getLocal());
            stmt.setInt(6, evento.getCapacidadeMaxima());
            stmt.setString(7, evento.getStatus());
            stmt.setString(8, evento.getCategoria());
            stmt.setDouble(9, evento.getPreco());
            stmt.setInt(10, evento.getOrganizadorId());
            stmt.executeUpdate();
        }
    }

    // Atualiza um evento existente
    public void atualizarEvento(Evento evento) throws SQLException {
        String sql = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, duracao = ?, local = ?, capacidade_maxima = ?, status = ?, categoria = ?, preco = ?, organizador_id = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getDescricao());
            stmt.setTimestamp(3, new Timestamp(evento.getDataHora().getTime()));
            stmt.setInt(4, evento.getDuracao());
            stmt.setString(5, evento.getLocal());
            stmt.setInt(6, evento.getCapacidadeMaxima());
            stmt.setString(7, evento.getStatus());
            stmt.setString(8, evento.getCategoria());
            stmt.setDouble(9, evento.getPreco());
            stmt.setInt(10, evento.getOrganizadorId());
            stmt.setInt(11, evento.getId());
            stmt.executeUpdate();
        }
    }

    // Deleta um evento pelo ID
    public void deletarEvento(int id) throws SQLException {
        String sql = "DELETE FROM eventos WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Busca um evento pelo ID
    public Evento buscarEventoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM eventos WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Evento(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_hora"),
                        rs.getInt("duracao"),
                        rs.getString("local"),
                        rs.getInt("capacidade_maxima"),
                        rs.getString("status"),
                        rs.getString("categoria"),
                        rs.getDouble("preco"),
                        rs.getInt("organizador_id")
                    );
                }
            }
        }
        return null;
    }

    // Retorna uma lista de todos os eventos
    public List<Evento> listarEventos() throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM eventos";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                eventos.add(new Evento(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getTimestamp("data_hora"),
                    rs.getInt("duracao"),
                    rs.getString("local"),
                    rs.getInt("capacidade_maxima"),
                    rs.getString("status"),
                    rs.getString("categoria"),
                    rs.getDouble("preco"),
                    rs.getInt("organizador_id")
                ));
            }
        }
        return eventos;
    }
}
