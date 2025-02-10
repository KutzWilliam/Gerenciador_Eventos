package gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;
import entities.Administrador;
import entities.Participante;
import entities.Usuario;
import service.UsuarioController;
import com.toedter.calendar.JDateChooser;

public class CadastroUsuarioView extends JFrame {
    private JFrame frame;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JComboBox<String> cbTipoUsuario;
    private UsuarioController usuarioController;

    public CadastroUsuarioView() {
        usuarioController = new UsuarioController();
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Cadastro de Usuário");
        frame.setBounds(100, 100, 500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título da Tela
        JLabel titleLabel = new JLabel("Cadastro de Usuário", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0x00796B)); // Cor verde
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.getContentPane().add(titleLabel, gbc);

        // Nome
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.getContentPane().add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField();
        gbc.gridx = 1;
        frame.getContentPane().add(txtNome, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.getContentPane().add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField();
        gbc.gridx = 1;
        frame.getContentPane().add(txtEmail, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.getContentPane().add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField();
        gbc.gridx = 1;
        frame.getContentPane().add(txtSenha, gbc);

        // Tipo de Usuário
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.getContentPane().add(new JLabel("Tipo de Usuário:"), gbc);
        cbTipoUsuario = new JComboBox<>(new String[] { "Participante", "Administrador" });
        gbc.gridx = 1;
        frame.getContentPane().add(cbTipoUsuario, gbc);

        // Botão de Cadastro
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(0x00796B));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.getContentPane().add(btnCadastrar, gbc);
    }

    private void cadastrarUsuario() {
        Random gerador = new Random();
        int id = gerador.nextInt(500);
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        String tipoUsuario = (String) cbTipoUsuario.getSelectedItem();

        Usuario usuario = new Usuario(id, nome, email, senha, tipoUsuario);

        try {
            usuarioController.cadastrarUsuario(usuario);
            JOptionPane.showMessageDialog(frame, "Usuário cadastrado com sucesso!");

            if ("Participante".equals(tipoUsuario)) {
                new CadastroParticipanteView(usuario).setVisible(true); // Redireciona para o Participante
            } else {
                new CadastroAdministradorView(usuario).setVisible(true); // Redireciona para o Administrador
            }
            frame.dispose(); // Fecha a tela de cadastro
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao cadastrar usuário: " + ex.getMessage());
        }
    }

    public void exibirTela() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CadastroUsuarioView window = new CadastroUsuarioView();
                    window.exibirTela();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}