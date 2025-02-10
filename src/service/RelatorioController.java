package service;

import java.sql.SQLException;
import java.util.List;


import dao.RelatorioDAO;


import entities.Relatorio;

public class RelatorioController {
    private RelatorioDAO relatorioDAO = new RelatorioDAO();

    public void gerarRelatorio(Relatorio relatorio) throws SQLException {
        relatorioDAO.adicionarRelatorio(relatorio);
    }

    public Relatorio obterRelatorioPorId(int id) throws SQLException {
        return relatorioDAO.obterRelatorioPorId(id);
    }

    public List<Relatorio> listarRelatorios() throws SQLException {
        return relatorioDAO.listarRelatorios();
    }

    public void excluirRelatorio(int id) throws SQLException {
        relatorioDAO.excluirRelatorio(id);
    }

    public List<Relatorio> obterRelatorioParticipantesPorEvento(int eventoId) throws SQLException {
        return relatorioDAO.obterRelatorioParticipantesPorEvento(eventoId);
    }
}