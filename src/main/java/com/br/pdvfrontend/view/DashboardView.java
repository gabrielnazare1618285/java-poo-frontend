package com.br.pdvfrontend.view;

import com.br.pdvfrontend.dto.AcessoResponse;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    // Cores modernas - preto, branco e vermelho
    private static final Color PRIMARY_COLOR = new Color(220, 20, 60);
    private static final Color SECONDARY_COLOR = new Color(30, 30, 30);
    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(50, 50, 50);
    private static final Color HOVER_COLOR = new Color(180, 15, 50);

    private AcessoResponse usuarioLogado;

    public DashboardView(AcessoResponse usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("PDV Posto de Combustível - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 768));

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Barra lateral
        JPanel sidebar = createSidebar();

        // Painel de conteúdo
        JPanel contentPanel = createContentPanel();

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(SECONDARY_COLOR);
        sidebar.setPreferredSize(new Dimension(280, getHeight()));

        // Cabeçalho do sidebar
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        JLabel lblTitle = new JLabel("PDV POSTO");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("Bem-vindo, " + usuarioLogado.getUsuario());
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setForeground(PRIMARY_COLOR);
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUser.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel lblRole = new JLabel("(" + usuarioLogado.getRole() + ")");
        lblRole.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblRole.setForeground(new Color(200, 200, 200));
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(lblTitle);
        headerPanel.add(lblUser);
        headerPanel.add(lblRole);

        // Menu de navegação
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(SECONDARY_COLOR);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 20, 15));

        // Botão Gerenciar Bombas (disponível para todos)
        addMenuButton(menuPanel, "⛽ Gerenciar Bombas", PRIMARY_COLOR, () -> openBombaView());

        // Verifica se é ADMIN para mostrar o botão Cadastrar Frentista
        String role = usuarioLogado.getRole() != null ? usuarioLogado.getRole() : "FRENTISTA";
        if ("ADMIN".equalsIgnoreCase(role)) {
            // Separador
            JSeparator separator = new JSeparator();
            separator.setForeground(new Color(60, 60, 60));
            separator.setMaximumSize(new Dimension(250, 1));
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            menuPanel.add(separator);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            // Botão especial para cadastrar frentista (só para ADMIN)
            JButton btnCadastrarFrentista = createSidebarButton("➕ Cadastrar Frentista", PRIMARY_COLOR);
            btnCadastrarFrentista.addActionListener(e -> openCadastrarFrentista());
            menuPanel.add(btnCadastrarFrentista);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        // Botão de sair
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBackground(SECONDARY_COLOR);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JButton btnSair = createSidebarButton("🚪 Sair", PRIMARY_COLOR);
        btnSair.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView().setVisible(true);
            }
        });
        footerPanel.add(btnSair);

        sidebar.add(headerPanel, BorderLayout.NORTH);
        sidebar.add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        sidebar.add(footerPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private void addMenuButton(JPanel panel, String text, Color bgColor, Runnable action) {
        JButton button = createSidebarButton(text, bgColor);
        button.addActionListener(e -> action.run());
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
    }

    private JButton createSidebarButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(250, 45));
        button.setMaximumSize(new Dimension(250, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = bgColor;

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (bgColor == PRIMARY_COLOR) {
                    button.setBackground(HOVER_COLOR);
                } else {
                    button.setBackground(new Color(60, 60, 60));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_COLOR);

        // Barra superior
        JPanel topBar = new JPanel();
        topBar.setLayout(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(getWidth(), 80));
        topBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(230, 230, 230)));

        JPanel topBarContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 25));
        topBarContent.setBackground(Color.WHITE);

        String role = usuarioLogado.getRole() != null ? usuarioLogado.getRole() : "FRENTISTA";
        String pageTitle = "FRENTISTA".equalsIgnoreCase(role) ? "Gerenciamento de Bombas" : "Dashboard Principal";

        JLabel lblPageTitle = new JLabel(pageTitle);
        lblPageTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblPageTitle.setForeground(TEXT_COLOR);

        topBarContent.add(lblPageTitle);
        topBar.add(topBarContent);

        // Área de trabalho principal
        JPanel workArea = new JPanel();
        workArea.setBackground(BACKGROUND_COLOR);
        workArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // VERIFICA O ROLE DO USUÁRIO
        if ("FRENTISTA".equalsIgnoreCase(role)) {
            // FRENTISTA: Mostra apenas mensagem para ir ao menu "Gerenciar Bombas"
            workArea.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
            messagePanel.setBackground(CARD_COLOR);
            messagePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                    BorderFactory.createEmptyBorder(40, 60, 40, 60)
            ));

            JLabel lblIcon = new JLabel("⛽", SwingConstants.CENTER);
            lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 72));
            lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblMessage = new JLabel("Clique em \"Gerenciar Bombas\" no menu lateral", SwingConstants.CENTER);
            lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lblMessage.setForeground(TEXT_COLOR);
            lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblMessage.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

            JLabel lblSubMessage = new JLabel("para registrar abastecimentos", SwingConstants.CENTER);
            lblSubMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblSubMessage.setForeground(new Color(120, 120, 120));
            lblSubMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

            messagePanel.add(lblIcon);
            messagePanel.add(lblMessage);
            messagePanel.add(lblSubMessage);

            workArea.add(messagePanel, gbc);

        } else {
            // ADMIN: Mostra todos os cards
            workArea.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;

            gbc.gridx = 0;
            gbc.gridy = 0;
            workArea.add(createClickableCard("👥 Pessoas", "Gerenciar cadastro de pessoas", PRIMARY_COLOR, () -> openPessoaView()), gbc);

            gbc.gridx = 1;
            workArea.add(createClickableCard("🛒 Produtos", "Gerenciar produtos", SECONDARY_COLOR, () -> openProdutoView()), gbc);

            gbc.gridx = 2;
            workArea.add(createClickableCard("📦 Estoque", "Controlar estoque", PRIMARY_COLOR, () -> openEstoqueView()), gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            workArea.add(createClickableCard("💰 Preços", "Gerenciar preços", SECONDARY_COLOR, () -> openPrecoView()), gbc);

            gbc.gridx = 1;
            workArea.add(createClickableCard("💳 Custos", "Controlar custos", PRIMARY_COLOR, () -> openCustoView()), gbc);

            gbc.gridx = 2;
            workArea.add(createClickableCard("📞 Contatos", "Gerenciar contatos", SECONDARY_COLOR, () -> openContatoView()), gbc);
        }

        contentPanel.add(topBar, BorderLayout.NORTH);
        contentPanel.add(workArea, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createClickableCard(String title, String description, Color accentColor, Runnable onClick) {
        JPanel card = createInfoCard(title, description, accentColor);

        // Adiciona clique no card inteiro
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (onClick != null) {
                    onClick.run();
                }
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accentColor, 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        // Adiciona clique também nos componentes internos
        for (Component comp : card.getComponents()) {
            comp.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (onClick != null) {
                        onClick.run();
                    }
                }
            });
            if (comp instanceof JPanel) {
                for (Component subComp : ((JPanel) comp).getComponents()) {
                    subComp.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            if (onClick != null) {
                                onClick.run();
                            }
                        }
                    });
                }
            }
        }

        return card;
    }

    private JPanel createInfoCard(String title, String description, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(280, 140));

        // Painel de conteúdo
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CARD_COLOR);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(TEXT_COLOR);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescription = new JLabel(description);
        lblDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDescription.setForeground(new Color(120, 120, 120));
        lblDescription.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDescription.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        contentPanel.add(lblTitle);
        contentPanel.add(lblDescription);

        // Indicador de cor
        JPanel colorIndicator = new JPanel();
        colorIndicator.setBackground(accentColor);
        colorIndicator.setPreferredSize(new Dimension(4, 140));

        card.add(colorIndicator, BorderLayout.WEST);
        card.add(contentPanel, BorderLayout.CENTER);


        return card;
    }

    private void openPessoaView() {
        new PessoaView();
    }

    private void openProdutoView() {
        new ProdutoView();
    }

    private void openEstoqueView() {
        new EstoqueView();
    }

    private void openPrecoView() {
        new PrecoView();
    }

    private void openCustoView() {
        new CustoView();
    }

    private void openContatoView() {
        new ContatoView().setVisible(true);
    }

    private void openBombaView() {
        new BombaPanelView().setVisible(true);
    }

    private void openCadastrarFrentista() {
        new RegisterView().setVisible(true);
    }
}
