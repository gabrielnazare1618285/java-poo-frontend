package com.br.pdvfrontend;

import com.br.pdvfrontend.view.PessoaView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.EventQueue;

@SpringBootApplication
public class PdvFrontendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        // Configura o Spring para rodar em modo "headless=false", permitindo UI (Swing/AWT)
        // e define que não é uma aplicação web para não iniciar o Tomcat.
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PdvFrontendApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Override
    public void run(String... args) {
        // Este método é executado após o Spring iniciar
        // Usamos EventQueue.invokeLater para garantir que a UI seja criada na thread correta (Event Dispatch Thread)
        EventQueue.invokeLater(() -> {
            // Pega a instância da nossa view (que é um @Component) do contexto do Spring
            PessoaView pessoaView = SpringApplication.run(PdvFrontendApplication.class, args).getBean(PessoaView.class);
            pessoaView.setVisible(true);
        });
    }
}
