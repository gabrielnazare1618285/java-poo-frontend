package com.br.pdvfrontend.view;

import com.br.pdvfrontend.model.Pessoa;
import com.br.pdvfrontend.service.AcessoService;
import com.br.pdvfrontend.service.PessoaService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RegisterView extends JFrame {
    // Cores: Preto, Branco e Vermelho
    private static final Color PRIMARY_COLOR = new Color(220, 20, 60); // Vermelho
    private static final Color SECONDARY_COLOR = new Color(30, 30, 30); // Preto
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250); // Branco
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    private static final Color HOVER_COLOR = new Color(180, 15, 50);

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> cmbPessoas;
    private AcessoService acessoService;
    private PessoaService pessoaService;
    private List<Pessoa> pessoas;

    public RegisterView() {
        this.acessoService = new AcessoService();
        this.pessoaService = new PessoaService();
        initComponents();
        carregarPessoas();
    }

    private void initComponents() {
        setTitle("Cadastrar Frentista - Sistema PDV");
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(500, 600));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Cabeçalho
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(500, 100));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("CADASTRAR FRENTISTA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel lblSubtitle = new JLabel("Crie usuário e senha para um frentista", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(PRIMARY_COLOR);
        lblSubtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(lblSubtitle, BorderLayout.SOUTH);

        // Campos
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBackground(BACKGROUND_COLOR);
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 30, 60));

        // Seleção de pessoa
        JLabel lblPessoa = new JLabel("Selecionar Pessoa:");
        lblPessoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPessoa.setForeground(TEXT_COLOR);
        lblPessoa.setAlignmentX(Component.LEFT_ALIGNMENT);

        cmbPessoas = new JComboBox<>();
        cmbPessoas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbPessoas.setPreferredSize(new Dimension(380, 45));
        cmbPessoas.setMaximumSize(new Dimension(380, 45));
        cmbPessoas.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldsPanel.add(lblPessoa);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(cmbPessoas);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo usuário
        JLabel lblUsuario = new JLabel("Usuário de Acesso:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setForeground(TEXT_COLOR);
        lblUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setPreferredSize(new Dimension(380, 45));
        txtUsername.setMaximumSize(new Dimension(380, 45));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        txtUsername.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldsPanel.add(lblUsuario);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(txtUsername);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Campo senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSenha.setForeground(TEXT_COLOR);
        lblSenha.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setPreferredSize(new Dimension(380, 45));
        txtPassword.setMaximumSize(new Dimension(380, 45));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        txtPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldsPanel.add(lblSenha);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(txtPassword);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Confirmar senha
        JLabel lblConfirmar = new JLabel("Confirmar Senha:");
        lblConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblConfirmar.setForeground(TEXT_COLOR);
        lblConfirmar.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtConfirmPassword.setPreferredSize(new Dimension(380, 45));
        txtConfirmPassword.setMaximumSize(new Dimension(380, 45));
        txtConfirmPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        txtConfirmPassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        fieldsPanel.add(lblConfirmar);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        fieldsPanel.add(txtConfirmPassword);
        fieldsPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(400, 60));

        JButton btnCancelar = createStyledButton("CANCELAR", SECONDARY_COLOR);
        btnCancelar.setPreferredSize(new Dimension(180, 45));
        btnCancelar.addActionListener(e -> dispose());

        JButton btnCadastrar = createStyledButton("CADASTRAR", PRIMARY_COLOR);
        btnCadastrar.setPreferredSize(new Dimension(180, 45));
        btnCadastrar.addActionListener(e -> criarAcesso());

        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnCadastrar);

        fieldsPanel.add(buttonPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void carregarPessoas() {
        try {
            pessoas = pessoaService.getAllPessoas();
            cmbPessoas.removeAllItems();
            cmbPessoas.addItem("-- Selecione uma pessoa --");

            for (Pessoa pessoa : pessoas) {
                // Adiciona todas as pessoas para seleção
                cmbPessoas.addItem(pessoa.getNomeCompleto());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar pessoas: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarAcesso() {
        if (cmbPessoas.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma pessoa!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String usuario = txtUsername.getText().trim();
        String senha = new String(txtPassword.getPassword());
        String confirmarSenha = new String(txtConfirmPassword.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this,
                    "As senhas não coincidem!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Pessoa pessoaSelecionada = pessoas.get(cmbPessoas.getSelectedIndex() - 1);
            acessoService.addAcessoComPessoa(usuario, senha, pessoaSelecionada.getId(), "FRENTISTA");

            JOptionPane.showMessageDialog(this,
                    "Acesso criado com sucesso para " + pessoaSelecionada.getNomeCompleto() + "!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            carregarPessoas();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao criar acesso: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        cmbPessoas.setSelectedIndex(0);
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (bgColor == PRIMARY_COLOR) {
                    button.setBackground(HOVER_COLOR);
                } else {
                    button.setBackground(new Color(50, 50, 50));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
