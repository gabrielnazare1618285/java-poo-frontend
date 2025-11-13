package com.br;

import com.br.pdvfrontend.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainApp {
    public static void main(String[] args) {
        try {
            // Tenta usar Nimbus (melhor suporte a customizações)
            boolean nimbusFound = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusFound = true;
                    break;
                }
            }

            // Se Nimbus não estiver disponível, usa o Look and Feel do sistema
            if (!nimbusFound) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }

            // Configurações adicionais para garantir que botões personalizados funcionem
            UIManager.put("Button.background", new java.awt.Color(220, 20, 60));
            UIManager.put("Button.foreground", java.awt.Color.WHITE);
            UIManager.put("Button.select", new java.awt.Color(180, 15, 50));

        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
        });
    }
}