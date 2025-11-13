package com.br.pdvfrontend.view;

import com.br.pdvfrontend.service.ContatoService;

import javax.swing.*;

public class ContatoView extends JFrame {

    public ContatoView() {
        // Cria o serviço de contatos
        ContatoService contatoService = new ContatoService();

        // Abre o gerenciador de contatos
        ContatoList contatoList = new ContatoList(contatoService);
        contatoList.setVisible(true);

        // Fecha esta janela já que abrimos o ContatoList
        this.dispose();
    }
}
