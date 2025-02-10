package service;

import java.sql.SQLException;
import java.util.List;

import dao.InscricaoDAO;
import entities.Inscricao;

public class InscricaoController {
    private InscricaoDAO inscricaoDAO = new InscricaoDAO();

    public void inscreverUsuario(Inscricao inscricao) throws SQLException {
        inscricaoDAO.adicionarInscricao(inscricao);
    }

    public void atualizarInscricao(Inscricao inscricao) throws SQLException {
        inscricaoDAO.atualizarInscricao(inscricao);
    }

    public void removerInscricao(int usuarioId, int eventoId) throws SQLException {
        inscricaoDAO.removerInscricao(usuarioId, eventoId);
    }

    public List<Inscricao> listarInscricoes() throws SQLException {
        return inscricaoDAO.listarInscricoes();
    }

    public List<Inscricao> buscarInscricaoId(int id) throws SQLException {
        return inscricaoDAO.buscarInscricaoIdUsuario(id);
    }
    
    public Inscricao buscarInscricaoPorId(int id) throws SQLException {
        return inscricaoDAO.buscarInscricaoPorId(id);
    }
    
    public List<Inscricao> buscarInscricaoPorIdEvento(int id) throws SQLException {
        return inscricaoDAO.buscarInscricaoIdEvento(id);
    }
}