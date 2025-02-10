package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dao.UsuarioDAO;
import entities.Usuario;
import service.UsuarioController;

public class LoginView extends JFrame {
    private JFrame frame;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    public LoginView() {
        new UsuarioController();
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Login");
        frame.setBounds(100, 100, 500, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título da Tela
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0x00796B)); // Cor verde
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.getContentPane().add(titleLabel, gbc);

        // Email
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.getContentPane().add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField();
        gbc.gridx = 1;
        frame.getContentPane().add(txtEmail, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.getContentPane().add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField();
        gbc.gridx = 1;
        frame.getContentPane().add(txtSenha, gbc);

        // Botão de Login
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0x00796B));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUsuario();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.getContentPane().add(btnLogin, gbc);

        // Link para cadastro (caso o usuário ainda não tenha conta)
        JButton btnCadastro = new JButton("Cadastrar-se");
        btnCadastro.setFont(new Font("Arial", Font.PLAIN, 14));
        btnCadastro.setForeground(new Color(0x00796B));
        btnCadastro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCadastro.setBackground(Color.WHITE);
        btnCadastro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCadastro();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.getContentPane().add(btnCadastro, gbc);
    }

    private void loginUsuario() {
        String email = txtEmail.getText();
        String senha = new String(txtSenha.getPassword());
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.autenticarUsuario(email, senha);

        try {
            if (usuario != null) {
            	if("Administrador".equals(usuario.getTipoUsuario())) {
                JOptionPane.showMessageDialog(null, "Bem-vindo, " + usuario.getNome());
                dispose();
                EventoView eventoView = new EventoView(usuario);
                eventoView.setVisible(true);
                frame.setVisible(false);}
            	else {JOptionPane.showMessageDialog(null, "Bem-vindo, " + usuario.getNome());
                dispose();
                InscricaoEventosView eventoView = new InscricaoEventosView(usuario);
                eventoView.setVisible(true);
                frame.setVisible(false);}
            } else {
                JOptionPane.showMessageDialog(frame, "Email ou senha inválidos.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao realizar login: " + ex.getMessage());
        }
    }

    private void abrirCadastro() {
        CadastroUsuarioView cadastroView = new CadastroUsuarioView();
        cadastroView.exibirTela();
        frame.setVisible(false);  // Fecha a tela de login
    }

    public void exibirTela() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginView window = new LoginView();
                    window.exibirTela();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
