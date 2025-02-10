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
import entities.Usuario;
import service.EventoController;
import service.InscricaoController;

public class InscricaoEventosView extends JFrame {
    private JTable tabelaEventos;
    private JButton btnInscrever, btnCancelar, btnConfirmarPresenca;
    private DefaultTableModel modeloTabela;
    private EventoController eventoController;
    private InscricaoController inscricaoController;
    private int usuarioId; // ID do usuário logado (deve ser passado no login)

    public InscricaoEventosView(Usuario usuario) {
        this.usuarioId = usuario.getId();
        eventoController = new EventoController();
        inscricaoController = new InscricaoController();

        setTitle("Gerenciamento de Inscrições");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Layout principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        
        // Tabela de eventos
        String[] colunas = {"ID", "Nome", "Capacidade", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaEventos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaEventos);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        btnInscrever = new JButton("Inscrever-se");
        btnCancelar = new JButton("Cancelar Inscrição");
        btnConfirmarPresenca = new JButton("Acompanhar Inscriçôes");

        painelBotoes.add(btnInscrever);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnConfirmarPresenca);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        carregarEventos();

        // Ações dos botões
        btnInscrever.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					inscreverUsuario();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarInscricao();
            }
        });

        btnConfirmarPresenca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	incricoesCandidato();
            }
        });
    }

    private void carregarEventos() {
        try {
            List<Evento> eventos = eventoController.listarEventos();
            modeloTabela.setRowCount(0); // Limpar tabela

            for (Evento evento : eventos) {
                modeloTabela.addRow(new Object[]{
                    evento.getId(), evento.getTitulo(), evento.getCapacidadeMaxima(), evento.getStatus()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar eventos: " + e.getMessage());
        }
    }

    private void inscreverUsuario() throws SQLException {
        int linhaSelecionada = tabelaEventos.getSelectedRow();
        Random gerador = new Random();
        int id = gerador.nextInt(500);
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para se inscrever.");
            return;
        }

        int eventoId = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
        
        if (!status.equals("Aberto")) {
            JOptionPane.showMessageDialog(this, "As inscrições só podem ser feitas em eventos abertos.");
            return;
        }

        
        if(eventoController.buscarEventoPorId(eventoId).getCapacidadeMaxima() == inscricaoController.buscarInscricaoPorIdEvento(eventoId).size()) {
        	JOptionPane.showMessageDialog(this, "A capacidade maxima de inscrições já foi atiginda");
            return;
        }

        try {
            Inscricao inscricao = new Inscricao(id, usuarioId, eventoId, new java.util.Date(), "ativa", false);
            inscricaoController.inscreverUsuario(inscricao);
            JOptionPane.showMessageDialog(this, "Inscrição realizada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao se inscrever: " + e.getMessage());
        }
    }

    private void cancelarInscricao() {
        int linhaSelecionada = tabelaEventos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento para cancelar a inscrição.");
            return;
        }

        int eventoId = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 3);
        
        if (!status.equals("Aberto")) {
            JOptionPane.showMessageDialog(this, "Você só pode cancelar inscrições enquanto o evento estiver aberto.");
            return;
        }

        try {
            inscricaoController.removerInscricao(usuarioId, eventoId);
            JOptionPane.showMessageDialog(this, "Inscrição cancelada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cancelar inscrição: " + e.getMessage());
        }
    }

    private void incricoesCandidato() {
    	new IncricaoCandidatoView(this, usuarioId).setVisible(true);
    }
}