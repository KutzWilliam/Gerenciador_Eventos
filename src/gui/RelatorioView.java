package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

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

		String[] colunas = { "ID", "Evento ID", "Nome do Evento", "Descrição", "Duração", "Data", "Local",
				"Data Geração", };
		modeloTabela = new DefaultTableModel(colunas, 0);
		tabelaRelatorio = new JTable(modeloTabela);
		JScrollPane scrollPane = new JScrollPane(tabelaRelatorio);
		painelPrincipal.add(scrollPane, BorderLayout.CENTER);

		JButton btnDownload = new JButton("Download Relatório");
		btnDownload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					exportarParaCSV();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JPanel painelBotoes = new JPanel();
		painelBotoes.add(btnDownload);
		painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

		add(painelPrincipal);

		exibirRelatorio();

	}

	private void exibirRelatorio() throws SQLException {

		for (Relatorio relatorio : relatorios) {
			int eventoId = relatorio.getEventoId();
			modeloTabela.addRow(new Object[] { relatorio.getId(), relatorio.getEventoId(),
					eventoController.buscarEventoPorId(eventoId).getTitulo(),
					eventoController.buscarEventoPorId(eventoId).getDescricao(),
					eventoController.buscarEventoPorId(eventoId).getDuracao(),
					eventoController.buscarEventoPorId(eventoId).getDataHora(),
					eventoController.buscarEventoPorId(eventoId).getLocal(), relatorio.getDataGeracao(), });
		}
	}

	private void exportarParaCSV() throws SQLException {
		Random gerador = new Random();
		int id = gerador.nextInt(500);
		String userHome = System.getProperty("user.home");
		String caminhoDownload = Paths.get(userHome, "Downloads", id + " relatorio.csv").toString();

		try (FileWriter writer = new FileWriter(new File(caminhoDownload))) {
			// Escrever cabeçalho
			writer.write(      
						 "Relatorio do Evento:\n");
			// Escrever os dados da tabela
			for (Relatorio relatorio : relatorios) {
				int eventoId = relatorio.getEventoId();
				writer.write("ID: " + relatorio.getId() + "\n"
						+ "Evento ID: " + relatorio.getEventoId() + "\n"
						+ "Nome do Evento: " + eventoController.buscarEventoPorId(eventoId).getTitulo() + "\n"
						+ "Descricao: " + eventoController.buscarEventoPorId(eventoId).getDescricao() + "\n"
						+ "Duracao: " + eventoController.buscarEventoPorId(eventoId).getDuracao() + " Minutos\n"
						+ "Data: " + eventoController.buscarEventoPorId(eventoId).getDataHora() + "\n"
						+ "Local: " + eventoController.buscarEventoPorId(eventoId).getLocal() + "\n"
						+ "Data Geracao: " + relatorio.getDataGeracao()+ "\n");
			}

			JOptionPane.showMessageDialog(this, "Relatório salvo em: " + caminhoDownload, "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao salvar o relatório", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}
