package com.br.pdvfrontend.view;

import com.br.pdvfrontend.service.ProdutoService;
public class ProdutoView {
    public ProdutoView() {
        ProdutoService service = new ProdutoService();
        ProdutoList list = new ProdutoList(service);
        list.setVisible(true);
    }
}