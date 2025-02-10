package gui;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import entities.Participante;
import entities.Usuario;
import com.toedter.calendar.JDateChooser;
import service.UsuarioController;

public class CadastroParticipanteView extends JFrame {
    private Usuario usuario;
    private JFormattedTextField txtCpf;
    private JDateChooser dcDataNascimento;
    private UsuarioController usuarioController;

    public CadastroParticipanteView(Usuario usuario) {
        this.usuario = usuario;
        usuarioController = new UsuarioController();
        initialize();
    }

    public void initialize() {
        setTitle("Cadastro de Participante");
        setBounds(100, 100, 500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título da Tela
        JLabel titleLabel = new JLabel("Cadastro de Participante", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0x00796B)); // Cor verde
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(titleLabel, gbc);

        // CPF   
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("CPF:"), gbc);

        try {
            MaskFormatter cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(cpfMask);
            txtCpf.setColumns(10);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        gbc.gridx = 1;
        add(txtCpf, gbc);

        // Data de Nascimento
        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(new JLabel("Data de Nascimento:"), gbc);
        dcDataNascimento = new JDateChooser();
        gbc.gridx = 1;
        getContentPane().add(dcDataNascimento, gbc);

        // Botão de Cadastro
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(0x00796B));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCadastrar.addActionListener(e -> cadastrarParticipante());
        gbc.gridx = 1;
        gbc.gridy = 3;
        getContentPane().add(btnCadastrar, gbc);
    }

    private void cadastrarParticipante() {
        String cpf = txtCpf.getText();
        java.util.Date dataNascimento = dcDataNascimento.getDate();
        Participante participante = new Participante(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), dataNascimento, cpf);

        try {
            usuarioController.atualizarParticipante(usuario, participante);
            JOptionPane.showMessageDialog(this, "Participante cadastrado com sucesso!");
            dispose();
            LoginView loginView = new LoginView();
            loginView.exibirTela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar participante: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Usuario usuario = new Usuario(1, "Nome", "email@mail.com", "senha", "Participante");
                CadastroParticipanteView window = new CadastroParticipanteView(usuario);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
