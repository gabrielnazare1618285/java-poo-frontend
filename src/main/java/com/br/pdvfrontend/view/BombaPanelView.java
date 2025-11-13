package com.br.pdvfrontend.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BombaPanelView extends JFrame {

    private static final Color PRIMARY_COLOR = new Color(220, 20, 60); // Vermelho
    private static final Color SECONDARY_COLOR = new Color(30, 30, 30); // Preto
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250); // Branco claro
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    private static final Color BUTTON_HOVER_COLOR = new Color(180, 15, 50);
    private static final Color ACTIVE_COLOR = new Color(46, 204, 113); // Verde para status ativo

    public BombaPanelView() {
        setTitle("Gerenciar Bombas de Combustível");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Barra superior
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());
        topBar.setBackground(SECONDARY_COLOR);
        topBar.setPreferredSize(new Dimension(getWidth(), 80));

        JLabel lblTitle = new JLabel("⛽ GERENCIAMENTO DE BOMBAS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setBackground(PRIMARY_COLOR);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setOpaque(true);
        btnVoltar.setContentAreaFilled(true);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setPreferredSize(new Dimension(120, 45));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnVoltar.addActionListener(e -> dispose());

        // Efeito hover no botão Voltar
        btnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVoltar.setBackground(BUTTON_HOVER_COLOR);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVoltar.setBackground(PRIMARY_COLOR);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 17));
        btnPanel.setBackground(SECONDARY_COLOR);
        btnPanel.add(btnVoltar);

        topBar.add(lblTitle, BorderLayout.WEST);
        topBar.add(btnPanel, BorderLayout.EAST);

        // Painel central com bombas (3 bombas em linha)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = 0;

        // Criando 3 bombas em uma linha
        for (int i = 0; i < 3; i++) {
            gbc.gridx = i;
            centerPanel.add(createBombaCard(i + 1), gbc);
        }

        add(topBar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createBombaCard(int numeroBomba) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        card.setPreferredSize(new Dimension(350, 200));

        // Cabeçalho da bomba
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JLabel lblNumero = new JLabel("BOMBA " + numeroBomba);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNumero.setForeground(TEXT_COLOR);
        lblNumero.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblStatus = new JLabel("● DISPONÍVEL", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatus.setForeground(ACTIVE_COLOR);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(CARD_COLOR);
        titlePanel.add(lblNumero, BorderLayout.CENTER);
        titlePanel.add(lblStatus, BorderLayout.SOUTH);

        headerPanel.add(titlePanel);

        // Botão de ação centralizado
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(CARD_COLOR);

        JButton btnRegistrar = new JButton("REGISTRAR ABASTECIMENTO");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(PRIMARY_COLOR);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setOpaque(true);
        btnRegistrar.setContentAreaFilled(true);
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setPreferredSize(new Dimension(280, 45));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnRegistrar.addActionListener(e -> registrarAbastecimento(numeroBomba));

        // Efeito hover
        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(PRIMARY_COLOR);
            }
        });

        buttonPanel.add(btnRegistrar);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(buttonPanel, BorderLayout.CENTER);

        return card;
    }

    private void registrarAbastecimento(int numeroBomba) {
        BombaManagerView bombaManager = new BombaManagerView(numeroBomba);
        bombaManager.setVisible(true);
    }
}