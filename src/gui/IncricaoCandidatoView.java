package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import entities.Evento;
import entities.Inscricao;
import entities.Relatorio;
import service.EventoController;
import service.InscricaoController;
import service.RelatorioController;

public class IncricaoCandidatoView extends JDialog {
    private JTable tabelaInscricoes;
    private JButton btnCancelar, btnConfirmarPresenca, btnGerarRelatorio;
    private DefaultTableModel modeloTabela;
    private EventoController eventoController;
    private InscricaoController inscricaoController;
    private RelatorioController relatorioController;
    private boolean running = true;
    private int usuarioId;

    public IncricaoCandidatoView(JFrame parent, int usuarioId) {
        super(parent, "Gerenciamento de Inscrições", true);
        this.usuarioId = usuarioId;
        inscricaoController = new InscricaoController();
        eventoController = new EventoController();
        relatorioController = new RelatorioController();

        setTitle("Minhas Inscrições");
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "COD Evento","Evento", "Status", "Presença Confirmada"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaInscricoes = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaInscricoes);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        btnCancelar = new JButton("Cancelar Inscrição");
        btnConfirmarPresenca = new JButton("Confirmar Presença");
        btnGerarRelatorio = new JButton("Gerar Relatório");

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnConfirmarPresenca);
        painelBotoes.add(btnGerarRelatorio);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        carregarInscricoes();
        iniciarThreadAtualizacao();

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarInscricao();
            }
        });

        btnConfirmarPresenca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmarPresenca();
            }
        });

        btnGerarRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					gerarRelatorioEvento();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    private void carregarInscricoes() {
        try {
            List<Inscricao> inscricoes = inscricaoController.buscarInscricaoId(usuarioId);
            modeloTabela.setRowCount(0);
            for (Inscricao inscricao : inscricoes) {
                modeloTabela.addRow(new Object[]{
                    inscricao.getId(), inscricao.getEventoId(), eventoController.buscarEventoPorId(inscricao.getEventoId()).getTitulo(), eventoController.buscarEventoPorId(inscricao.getEventoId()).getStatus(),
                    inscricao.isPresencaConfirmada() ? "Sim" : "Não"
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar inscrições: " + e.getMessage());
        }
    }

    private void cancelarInscricao() {
        int linhaSelecionada = tabelaInscricoes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para cancelar a inscrição.");
            return;
        }

        int eventoId = (int) modeloTabela.getValueAt(linhaSelecionada, 1);
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 3);

        if (!status.equals("Aberto")) {
            JOptionPane.showMessageDialog(this, "Você só pode cancelar inscrições enquanto o evento estiver aberto.");
            return;
        }

        try {
            inscricaoController.removerInscricao(usuarioId, eventoId);
            JOptionPane.showMessageDialog(this, "Inscrição cancelada com sucesso!");
            carregarInscricoes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cancelar inscrição: " + e.getMessage());
        }
    }

    private void confirmarPresenca() {
        int linhaSelecionada = tabelaInscricoes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para confirmar presença.");
            return;
        }

        int inscricaoId = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        int eventoId = (int) modeloTabela.getValueAt(linhaSelecionada, 1);

        try {
            Inscricao inscricao = inscricaoController.buscarInscricaoPorId(inscricaoId);
            if (inscricao != null && inscricao.getEventoId() == eventoId) {
                inscricao.setPresencaConfirmada(true);
                inscricaoController.atualizarInscricao(inscricao);
                JOptionPane.showMessageDialog(this, "Presença confirmada com sucesso!");
                carregarInscricoes();
            } else {
                JOptionPane.showMessageDialog(this, "Você não está inscrito neste evento.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao confirmar presença: " + e.getMessage());
        }
    }
    
    private void iniciarThreadAtualizacao() {
        new Thread(() -> {
            while (running) {
                try {
                    List<Evento> eventos = eventoController.listarEventos();
                    for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                        int eventoId = (int) modeloTabela.getValueAt(i, 0);
                        Evento eventoAtualizado = eventoController.buscarEventoPorId(eventoId);
                        if (!modeloTabela.getValueAt(i, 3).equals(eventoAtualizado.getStatus())) {
                            modeloTabela.setValueAt(eventoAtualizado.getStatus(), i, 3);
                            System.out.println("Evento " + eventoId + " atualizado para: " + eventoAtualizado.getStatus());
                        }
                    }
                    Thread.sleep(5000);
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void pararThread() {
        running = false;
    }

    private void gerarRelatorioEvento() throws SQLException {
        int linhaSelecionada = tabelaInscricoes.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para gerar o relatório.");
            return;
        }
        
        int eventoId = (int) tabelaInscricoes.getValueAt(linhaSelecionada, 1); // Supondo que a primeira coluna seja o ID do evento
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
        RelatorioView relatorioView = new RelatorioView(IncricaoCandidatoView(), relatorios);
        relatorioView.setVisible(true);
        
        JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso!");
    }

	private JFrame IncricaoCandidatoView() {
		// TODO Auto-generated method stub
		return null;
	}
}