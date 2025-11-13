package com.br.pdvfrontend.view;

import com.br.pdvfrontend.service.EstoqueService;
public class EstoqueView {
    public EstoqueView() {
        EstoqueService service = new EstoqueService();
        EstoqueList list = new EstoqueList(service);
        list.setVisible(true);
    }
}