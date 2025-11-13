package com.br.pdvfrontend.view;

import com.br.pdvfrontend.service.PessoaService;

public class PessoaView {
    public PessoaView() {
        PessoaService pessoaService = new PessoaService();
        PessoaList pessoaList = new PessoaList(pessoaService);
        pessoaList.setVisible(true);
    }
}
