package com.br.pdvfrontend.view;

import com.br.pdvfrontend.dto.AcessoResponse;
import com.br.pdvfrontend.service.AcessoService;
import com.br.pdvfrontend.util.SessionManager;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    // Cores: Preto, Branco e Vermelho
    private static final Color PRIMARY_COLOR = new Color(220, 20, 60); // Vermelho
    private static final Color SECONDARY_COLOR = new Color(30, 30, 30); // Preto
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250); // Branco
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    private static final Color HOVER_COLOR = new Color(180, 15, 50);

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private AcessoService acessoService;
    private SessionManager sessionManager;

    public LoginView() {
        acessoService = new AcessoService();
        sessionManager = SessionManager.getInstance();

        setTitle("PDV Posto de Combustível");
        setSize(550, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(500, 650));

        initComponents();
        checkFirstAccess();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Painel de cadastro do primeiro admin
        JPanel adminPanel = createFirstAdminPanel();

        // Painel de login normal
        JPanel loginPanel = createLoginPanel();

        cardPanel.add(adminPanel, "ADMIN");
        cardPanel.add(loginPanel, "LOGIN");

        add(cardPanel);
    }

    private void checkFirstAccess() {
        try {
            boolean hasAdmin = acessoService.hasAdmin();
            if (!hasAdmin) {
                cardLayout.show(cardPanel, "ADMIN");
            } else {
                cardLayout.show(cardPanel, "LOGIN");
            }
        } catch (Exception e) {
            e.printStackTrace();
            cardLayout.show(cardPanel, "ADMIN");
        }
    }

    private JPanel createFirstAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(550, 120));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("PRIMEIRO ACESSO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel lblSubtitle = new JLabel("Crie o Administrador do Sistema", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(PRIMARY_COLOR);
        lblSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);

        // Container centralizado
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBackground(BACKGROUND_COLOR);

        // Painel de campos
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBackground(BACKGROUND_COLOR);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        fieldsPanel.setMaximumSize(new Dimension(400, 600));

        JTextField txtUsuarioAdmin = createStyledTextField();
        JPasswordField txtSenhaAdmin = createStyledPasswordField();
        JPasswordField txtConfirmarSenha = createStyledPasswordField();

        addFieldCentered(fieldsPanel, "Usuário Administrador", txtUsuarioAdmin);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addFieldCentered(fieldsPanel, "Senha", txtSenhaAdmin);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addFieldCentered(fieldsPanel, "Confirmar Senha", txtConfirmarSenha);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        JButton btnCriar = createStyledButton("CRIAR ADMINISTRADOR", PRIMARY_COLOR);
        btnCriar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCriar.addActionListener(e -> {
            String usuario = txtUsuarioAdmin.getText().trim();
            String senha = new String(txtSenhaAdmin.getPassword());
            String confirmar = new String(txtConfirmarSenha.getPassword());

            if (usuario.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!senha.equals(confirmar)) {
                JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                acessoService.criarPrimeiroAdmin(usuario, senha);
                JOptionPane.showMessageDialog(this, "Administrador criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                // Limpa os campos
                txtUsuarioAdmin.setText("");
                txtSenhaAdmin.setText("");
                txtConfirmarSenha.setText("");
                // Muda para tela de login
                cardLayout.show(cardPanel, "LOGIN");
            } catch (Exception ex) {
                String message = ex.getMessage();
                if (message != null && message.contains("Já existe um administrador")) {
                    JOptionPane.showMessageDialog(this,
                            "Administrador já cadastrado! Use a tela de login.",
                            "Informação",
                            JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(cardPanel, "LOGIN");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Erro ao criar administrador: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        fieldsPanel.add(btnCriar);

        centerContainer.add(fieldsPanel);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerContainer, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);

        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(550, 120));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("PDV POSTO", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel lblSubtitle = new JLabel("Sistema de Gerenciamento", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitle.setForeground(PRIMARY_COLOR);
        lblSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);

        // Container centralizado
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setBackground(BACKGROUND_COLOR);

        // Campos
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBackground(BACKGROUND_COLOR);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        fieldsPanel.setMaximumSize(new Dimension(400, 500));

        JLabel lblFormTitle = new JLabel("Acesso ao Sistema");
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFormTitle.setForeground(TEXT_COLOR);
        lblFormTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.add(lblFormTitle);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        txtUsuario = createStyledTextField();
        txtSenha = createStyledPasswordField();

        addFieldCentered(fieldsPanel, "Usuário", txtUsuario);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addFieldCentered(fieldsPanel, "Senha", txtSenha);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        JButton btnLogin = createStyledButton("ENTRAR", PRIMARY_COLOR);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> handleLogin());

        fieldsPanel.add(btnLogin);

        centerContainer.add(fieldsPanel);

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerContainer, BorderLayout.CENTER);

        return panel;
    }

    private void addFieldCentered(JPanel panel, String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(TEXT_COLOR);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(field);
    }

    private void handleLogin() {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            AcessoResponse response = acessoService.login(usuario, senha);

            if (response != null) {
                sessionManager.setCurrentUser(response);
                this.dispose();

                if ("ADMIN".equals(response.getRole())) {
                    new DashboardView(response).setVisible(true);
                } else {
                    // Frentista só acessa bombas
                    new BombaPanelView().setVisible(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addField(JPanel panel, String label, JComponent field) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(TEXT_COLOR);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(field);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(380, 45));
        field.setMaximumSize(new Dimension(380, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(380, 45));
        field.setMaximumSize(new Dimension(380, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(380, 45));
        button.setMaximumSize(new Dimension(380, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
