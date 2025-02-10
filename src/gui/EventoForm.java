package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;
import service.EventoController;
import entities.Evento;

public class EventoForm extends JDialog {
    private EventoController eventoController;
    private Evento evento;
    private JTextField txtTitulo, txtDescricao, txtDuracao, txtLocal, txtCapacidade, txtCategoria, txtPreco;
    private int txtId;
    private JComboBox<String> comboStatus;
    private JSpinner spinnerDataHora;
    private JButton btnSalvar, btnCancelar;

    public EventoForm(JFrame parent, Evento evento, int usuarioId) {
        super(parent, "Gerenciar Evento", true);
        this.eventoController = new EventoController();
        this.evento = evento;
        this.txtId = usuarioId;
        
        setSize(500, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(10, 2, 10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelMain.setBackground(new Color(240, 240, 240));
        
        addLabelAndField(panelMain, "Título:", txtTitulo = new JTextField());
        addLabelAndField(panelMain, "Descrição:", txtDescricao = new JTextField());
        
        panelMain.add(new JLabel("Data e Hora:"));
        spinnerDataHora = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerDataHora, "yyyy-MM-dd HH:mm:ss");
        spinnerDataHora.setEditor(editor);
        panelMain.add(spinnerDataHora);
        
        addLabelAndField(panelMain, "Duração (minutos):", txtDuracao = new JTextField());
        addLabelAndField(panelMain, "Local:", txtLocal = new JTextField());
        addLabelAndField(panelMain, "Capacidade Máxima:", txtCapacidade = new JTextField());
        
        panelMain.add(new JLabel("Status:"));
        comboStatus = new JComboBox<>(new String[]{"Aberto", "Fechado", "Encerrado", "Cancelado"});
        panelMain.add(comboStatus);
        
        addLabelAndField(panelMain, "Categoria:", txtCategoria = new JTextField());
        addLabelAndField(panelMain, "Preço:", txtPreco = new JTextField());
        
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelButtons.setBackground(new Color(220, 220, 220));
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(0, 150, 0));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.addActionListener(this::salvarEvento);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(200, 0, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> dispose());
        
        panelButtons.add(btnSalvar);
        panelButtons.add(btnCancelar);
        
        add(panelMain, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        if (evento != null) {
            preencherDados();
        }

    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField textField) {
        panel.add(new JLabel(labelText));
        panel.add(textField);
    }

    private void preencherDados() {
        txtTitulo.setText(evento.getTitulo());
        txtDescricao.setText(evento.getDescricao());
        spinnerDataHora.setValue(evento.getDataHora());
        txtDuracao.setText(String.valueOf(evento.getDuracao()));
        txtLocal.setText(evento.getLocal());
        txtCapacidade.setText(String.valueOf(evento.getCapacidadeMaxima()));
        comboStatus.setSelectedItem(evento.getStatus());
        txtCategoria.setText(evento.getCategoria());
        txtPreco.setText(String.valueOf(evento.getPreco()));
    }

    private void salvarEvento(ActionEvent e) {
        try {
            if (evento == null) {
                evento = new Evento();
            }

            evento.setTitulo(txtTitulo.getText());
            evento.setDescricao(txtDescricao.getText());
            evento.setDataHora((Date) spinnerDataHora.getValue());
            evento.setDuracao(Integer.parseInt(txtDuracao.getText()));
            evento.setLocal(txtLocal.getText());
            evento.setCapacidadeMaxima(Integer.parseInt(txtCapacidade.getText()));
            evento.setStatus("Fechado");
            evento.setCategoria(txtCategoria.getText());
            evento.setPreco(Double.parseDouble(txtPreco.getText()));
            evento.setOrganizadorId(txtId);
            
            if (evento.getId() == 0) {
                eventoController.criarEvento(evento);
            } else {
                eventoController.atualizarEvento(evento);
            }
            
            JOptionPane.showMessageDialog(this, "Evento salvo com sucesso!");
            dispose();

            if (getParent() instanceof EventoView) {
                ((EventoView) getParent()).atualizarListaEventos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique os campos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}