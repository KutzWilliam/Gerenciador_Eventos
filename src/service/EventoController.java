package service;

import java.sql.SQLException;
import java.util.List;

import dao.EventoDAO;
import entities.Evento;

public class EventoController {
    private EventoDAO eventoDAO = new EventoDAO();

    // Criar um novo evento
    public void criarEvento(Evento evento) throws SQLException {
        eventoDAO.adicionarEvento(evento);
    }

    // Atualizar um evento existente
    public void atualizarEvento(Evento evento) throws SQLException {
        eventoDAO.atualizarEvento(evento);
    }

    // Deletar um evento pelo ID
    public void deletarEvento(int id) throws SQLException {
        eventoDAO.deletarEvento(id);
    }

    // Buscar um evento pelo ID
    public Evento buscarEventoPorId(int id) throws SQLException {
        return eventoDAO.buscarEventoPorId(id);
    }

    // Listar todos os eventos
    public List<Evento> listarEventos() throws SQLException {
        return eventoDAO.listarEventos();
    }
}