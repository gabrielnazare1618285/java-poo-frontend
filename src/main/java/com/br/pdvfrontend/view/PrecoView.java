package com.br.pdvfrontend.view;

import com.br.pdvfrontend.service.PrecoService;
public class PrecoView {
    public PrecoView() {
        PrecoService service = new PrecoService();
        PrecoList list = new PrecoList(service);
        list.setVisible(true);
    }
}