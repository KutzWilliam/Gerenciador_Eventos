package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import service.EventoController;
import service.RelatorioController;
import entities.Evento;
import entities.Relatorio;
import entities.Usuario;

public class EventoView extends JFrame {
    private EventoController eventoController;
    private JTable tableEventos;
    private DefaultTableModel tableModel;
    private JComboBox<String> comboStatus;
    int usuarioId;

    public EventoView(Usuario usuario) {
        eventoController = new EventoController();
        setTitle("Gerenciamento de Eventos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Estilização da interface
        Color primaryColor = new Color(0x00796B);
        Font titleFont = new Font("Arial", Font.BOLD, 20);

        // Painel de título
        JLabel titleLabel = new JLabel("Gerenciamento de Eventos", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryColor);
        add(titleLabel, BorderLayout.NORTH);

        // Painel superior com botões de ação
        JPanel panelTop = new JPanel(new FlowLayout());
        JButton btnAdicionar = criarBotao("Adicionar Evento");
        JButton btnEditar = criarBotao("Editar Evento");
        JButton btnDeletar = criarBotao("Deletar Evento");
        JButton btnAlterarStatus = criarBotao("Alterar Status");
        
        panelTop.add(btnAdicionar);
        panelTop.add(btnEditar);
        panelTop.add(btnDeletar);
        panelTop.add(btnAlterarStatus);
        
        add(panelTop, BorderLayout.NORTH);

        // Tabela de eventos
        tableModel = new DefaultTableModel(new String[]{"ID", "Título", "Descrição", "Data", "Duração", "Local", "Capacidade", "Status", "Categoria", "Preço"}, 0);
        tableEventos = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableEventos);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de filtro por status
        JPanel panelFilter = new JPanel();
        comboStatus = new JComboBox<>(new String[]{"Todos", "Aberto", "Fechado", "Encerrado", "Cancelado"});
        JButton btnFiltrar = criarBotao("Filtrar");
        JButton bntRelatorio = criarBotao("Gerar Relatorio");
        panelFilter.add(new JLabel("Filtrar por Status:"));
        panelFilter.add(comboStatus);
        panelFilter.add(btnFiltrar);
        panelFilter.add(bntRelatorio);
        add(panelFilter, BorderLayout.SOUTH);

        // Ações dos botões
        btnAdicionar.addActionListener(this::adicionarEvento);
        btnEditar.addActionListener(this::editarEvento);
        btnDeletar.addActionListener(this::deletarEvento);
        btnAlterarStatus.addActionListener(this::alterarStatusEvento);
        btnFiltrar.addActionListener(this::filtrarEventos);
        bntRelatorio.addActionListener(e -> {
			try {
				gerarRelatorioEvento(e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        
        usuarioId = usuario.getId();

        carregarEventos();
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(0x00796B));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return botao;
    }

    private void carregarEventos() {
        tableModel.setRowCount(0);
        try {
            List<Evento> eventos = eventoController.listarEventos();
            for (Evento evento : eventos) {
                tableModel.addRow(new Object[]{
                        evento.getId(), evento.getTitulo(), evento.getDescricao(),
                        evento.getDataHora(), evento.getDuracao(), evento.getLocal(),
                        evento.getCapacidadeMaxima(), evento.getStatus(), evento.getCategoria(),
                        evento.getPreco()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar eventos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarEvento(ActionEvent e) {
        new EventoForm(this, null, usuarioId).setVisible(true);
    }

    private void editarEvento(ActionEvent e) {
        int selectedRow = tableEventos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para editar.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            Evento evento = eventoController.buscarEventoPorId(id);
            if (evento != null) {
                new EventoForm(this, evento, usuarioId).setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletarEvento(ActionEvent e) {
        int selectedRow = tableEventos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para deletar.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            eventoController.deletarEvento(id);
            carregarEventos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarStatusEvento(ActionEvent e) {
        int selectedRow = tableEventos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para alterar o status.");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String novoStatus = JOptionPane.showInputDialog(this, "Digite o novo status (Aberto, Fechado, Encerrado, Cancelado):");
        if (novoStatus != null && (novoStatus.equalsIgnoreCase("Aberto") || novoStatus.equalsIgnoreCase("Fechado") ||
                novoStatus.equalsIgnoreCase("Encerrado") || novoStatus.equalsIgnoreCase("Cancelado"))) {
            try {
                Evento evento = eventoController.buscarEventoPorId(id);
                if (evento != null) {
                    evento.setStatus(novoStatus);
                    eventoController.atualizarEvento(evento);
                    carregarEventos();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao alterar status: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Status inválido.");
        }
    }

    private void filtrarEventos(ActionEvent e) {
        String statusSelecionado = (String) comboStatus.getSelectedItem();
        tableModel.setRowCount(0);
        try {
            List<Evento> eventos = eventoController.listarEventos();
            for (Evento evento : eventos) {
                if (statusSelecionado.equals("Todos") || evento.getStatus().equalsIgnoreCase(statusSelecionado)) {
                    tableModel.addRow(new Object[]{
                            evento.getId(), evento.getTitulo(), evento.getDescricao(),
                            evento.getDataHora(), evento.getDuracao(), evento.getLocal(),
                            evento.getCapacidadeMaxima(), evento.getStatus(), evento.getCategoria(),
                            evento.getPreco()
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao filtrar eventos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarListaEventos() {
        carregarEventos();
    }
    
    private void gerarRelatorioEvento(ActionEvent e) throws SQLException {
        int linhaSelecionada = tableEventos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para gerar o relatório.");
            return;
        }
        
        int eventoId = (int) tableEventos.getValueAt(linhaSelecionada, 0); // Supondo que a primeira coluna seja o ID do evento
        RelatorioController relatorioController = new RelatorioController();
        List<Relatorio> relatorios = relatorioController.obterRelatorioParticipantesPorEvento(eventoId);
        
        if (relatorios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum participante confirmado para este evento.");
            return;
        }
        
        // Gera o relatório no banco
        for (Relatorio relatorio : relatorios) {
            relatorioController.gerarRelatorio(relatorio);
        }
        
        // Abre a tela do relatório
        RelatorioAdminView relatorioView = new RelatorioAdminView(EventoView(), relatorios);
        relatorioView.setVisible(true);
        
        JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso!");
    }

    private JFrame EventoView() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventoView(null).setVisible(true));
    }
}