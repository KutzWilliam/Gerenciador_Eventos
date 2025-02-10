package gui;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import entities.Administrador;
import entities.Usuario;
import service.UsuarioController;

public class CadastroAdministradorView extends JFrame {
    private Usuario usuario;
    private JTextField txtCargo;
    private JDateChooser dcDataContratacao;
    private UsuarioController usuarioController;

    public CadastroAdministradorView(Usuario usuario) {
        this.usuario = usuario;
        usuarioController = new UsuarioController();
        initialize();
    }

    public void initialize() {
        setTitle("Cadastro de Administrador");
        setBounds(100, 100, 500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título da Tela
        JLabel titleLabel = new JLabel("Cadastro de Administrador", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0x00796B)); // Cor verde
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(titleLabel, gbc);

        // Cargo
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(new JLabel("Cargo:"), gbc);
        txtCargo = new JTextField();
        gbc.gridx = 1;
        getContentPane().add(txtCargo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        getContentPane().add(new JLabel("Data de Contratação:"), gbc);
        dcDataContratacao = new JDateChooser();
        gbc.gridx = 1;
        getContentPane().add(dcDataContratacao, gbc);

        // Botão de Cadastro
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(0x00796B));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnCadastrar.addActionListener(e -> cadastrarAdministrador());
        gbc.gridx = 1;
        gbc.gridy = 3;
        getContentPane().add(btnCadastrar, gbc);
    }

    private void cadastrarAdministrador() {
        String cargo = txtCargo.getText();
        java.util.Date dataContratacao = dcDataContratacao.getDate();
        Administrador administrador = new Administrador(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), cargo, dataContratacao);

        try {
            usuarioController.atualizarAdministrador(usuario, administrador);
            JOptionPane.showMessageDialog(this, "Administrador cadastrado com sucesso!");
            dispose();
            LoginView loginView = new LoginView();
            loginView.exibirTela();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar administrador: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Usuario usuario = new Usuario(1, "Nome", "email@mail.com", "senha", "Administrador");
                CadastroAdministradorView window = new CadastroAdministradorView(usuario);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
