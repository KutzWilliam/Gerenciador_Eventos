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
    private boolean running = true;
    private int usuarioId; // ID do usuário logado (deve ser passado no login)

    public InscricaoEventosView(Usuario usuario) {
        this.usuarioId = usuario.getId();
        eventoController = new EventoController();
        inscricaoController = new InscricaoController();

        setTitle("Gerenciamento de Inscrições");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        Color primaryColor = new Color(0x00796B);
        Font titleFont = new Font("Arial", Font.BOLD, 20);

        // Layout principal
        JLabel painelPrincipal = new JLabel("Gerenciamento de Inscrições", SwingConstants.CENTER);
        painelPrincipal.setFont(titleFont);
        painelPrincipal.setForeground(primaryColor);
        add(painelPrincipal, BorderLayout.NORTH);
        
        // Tabela de eventos
        String[] colunas = {"ID", "Nome","Duração", "Valor","Local","Capacidade", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaEventos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaEventos);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnInscrever = criarBotao("Inscrever-se");
        JButton btnCancelar = criarBotao("Cancelar Inscrição");
        JButton btnConfirmarPresenca = criarBotao("Acompanhar Inscriçôes");

        painelBotoes.add(btnInscrever);
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnConfirmarPresenca);

        add(painelBotoes, BorderLayout.SOUTH);


        carregarEventos();
        iniciarThreadAtualizacao();

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
    
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setBackground(new Color(0x00796B));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return botao;
    }

    public void carregarEventos() {
        try {
            List<Evento> eventos = eventoController.listarEventos();
            modeloTabela.setRowCount(0); // Limpar tabela

            for (Evento evento : eventos) {
                modeloTabela.addRow(new Object[]{
                    evento.getId(), evento.getTitulo(), evento.getDuracao(), evento.getPreco(), evento.getLocal(), evento.getCapacidadeMaxima(), evento.getStatus()
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
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 6);
        
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
        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 6);
        
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
    
    private void iniciarThreadAtualizacao() {
        new Thread(() -> {
            while (running) {
                try {
                    List<Evento> eventos = eventoController.listarEventos();
                    for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                        int eventoId = (int) modeloTabela.getValueAt(i, 0);
                        Evento eventoAtualizado = eventoController.buscarEventoPorId(eventoId);
                        if (!modeloTabela.getValueAt(i, 6).equals(eventoAtualizado.getStatus())) {
                            modeloTabela.setValueAt(eventoAtualizado.getStatus(), i, 6);
                            System.out.println("Evento " + eventoId + " atualizado para: " + eventoAtualizado.getStatus());
                        }
                        if (!modeloTabela.getValueAt(i, 3).equals(eventoAtualizado.getPreco())) {
                            modeloTabela.setValueAt(eventoAtualizado.getPreco(), i, 3);
                            System.out.println("Preço " + eventoId + " atualizado para: " + eventoAtualizado.getPreco());
                        }
                        if (!modeloTabela.getValueAt(i, 4).equals(eventoAtualizado.getLocal())) {
                            modeloTabela.setValueAt(eventoAtualizado.getLocal(), i, 4);
                            System.out.println("Local " + eventoId + " atualizado para: " + eventoAtualizado.getLocal());
                        }
                        if (!modeloTabela.getValueAt(i, 5).equals(eventoAtualizado.getCapacidadeMaxima())) {
                            modeloTabela.setValueAt(eventoAtualizado.getCapacidadeMaxima(), i, 5);
                            System.out.println("Capacidade " + eventoId + " atualizado para: " + eventoAtualizado.getCapacidadeMaxima());
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

    private void incricoesCandidato() {
    	new IncricaoCandidatoView(this, usuarioId).setVisible(true);
    }
}