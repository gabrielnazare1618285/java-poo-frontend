package com.br.pdvfrontend.view;

import com.br.pdvfrontend.service.CustoService;
public class CustoView {
    public CustoView() {
        CustoService service = new CustoService();
        CustoList list = new CustoList(service);
        list.setVisible(true);
    }
}
