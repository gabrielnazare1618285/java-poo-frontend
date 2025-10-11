package com.br.pdvfrontend.util;

import com.br.pdvfrontend.view.PessoaView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.EventQueue;

@SpringBootApplication(scanBasePackages = "com.br.pdvfrontend")
public class PdvFrontendApplication implements CommandLineRunner {

    @Autowired
    private PessoaView pessoaView;

    public static void main(String[] args) {
        // Configura o Spring para rodar em modo "headless=false", permitindo UI (Swing/AWT)
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PdvFrontendApplication.class);
        builder.headless(false);
        builder.run(args);
    }

    @Override
    public void run(String... args) {
        // Este método é executado após o Spring iniciar
        // Usamos EventQueue.invokeLater para garantir que a UI seja criada na thread correta (Event Dispatch Thread)
        EventQueue.invokeLater(() -> {
            // Usa a instância da view injetada pelo Spring
            pessoaView.setVisible(true);
        });
    }
}
