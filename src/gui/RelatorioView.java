package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import entities.Relatorio;
import service.EventoController;

public class RelatorioView extends JDialog {
    private JTable tabelaRelatorio;
    private DefaultTableModel modeloTabela;
    private List<Relatorio> relatorios;
    private EventoController eventoController;

    public RelatorioView(JFrame parent, List<Relatorio> relatorios) throws SQLException {
        super(parent, "Relatório do Evento", true);
        this.relatorios = relatorios;
        eventoController = new EventoController();

        setTitle("Relatório de Participantes");
        setSize(800, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Evento ID","Nome do Evento","Descrição","Duração","Data","Local" ,"Data Geração",};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaRelatorio = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaRelatorio);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(painelPrincipal);

        exibirRelatorio();

    }

    private void exibirRelatorio() throws SQLException {
    	
        for (Relatorio relatorio : relatorios) {
        	int eventoId = relatorio.getEventoId();
            modeloTabela.addRow(new Object[]{
                relatorio.getId(),
                relatorio.getEventoId(),
                eventoController.buscarEventoPorId(eventoId).getTitulo(),
                eventoController.buscarEventoPorId(eventoId).getDescricao(),
                eventoController.buscarEventoPorId(eventoId).getDuracao(),
                eventoController.buscarEventoPorId(eventoId).getDataHora(),
                eventoController.buscarEventoPorId(eventoId).getLocal(),
                relatorio.getDataGeracao(),
            });
        }
    }
}
