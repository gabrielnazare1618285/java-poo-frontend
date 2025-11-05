package com.br.pdvfrontend.view;

import com.br.pdvfrontend.model.Pessoa;
import com.br.pdvfrontend.service.PessoaService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Component
public class PessoaView extends JFrame {

    private final PessoaService pessoaService;

    // Componentes da UI
    private final JTable tabela;
    private final DefaultTableModel modeloTabela;
    private final JButton btnNovo;
    private final JButton btnEditar;
    private final JButton btnExcluir;

    public PessoaView(PessoaService pessoaService) {
        this.pessoaService = pessoaService;

        // Configurações da Janela
        setTitle("Gerenciamento de Pessoas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tabela
        String[] colunas = {"ID", "Nome", "CPF", "Email"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        tabela = new JTable(modeloTabela);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel de Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnNovo = new JButton("Novo");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        // Adicionar painel principal à janela
        add(painel);

        // Listeners (Ações)
        btnNovo.addActionListener(e -> abrirFormulario(null)); // null para nova pessoa
        btnEditar.addActionListener(e -> abrirFormularioParaEdicao());
        btnExcluir.addActionListener(e -> excluirPessoa());

        // Carregar dados iniciais
        atualizarTabela();
    }

    private void abrirFormulario(Pessoa pessoa) {
        // Cria e exibe o diálogo do formulário
        PessoaForm formDialog = new PessoaForm(this, pessoaService, pessoa, this::atualizarTabela);
        formDialog.setVisible(true);
    }

    private void abrirFormularioParaEdicao() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);
        try {
            Pessoa pessoa = pessoaService.buscarPorId(id);
            abrirFormulario(pessoa);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dados da pessoa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        try {
            List<Pessoa> pessoas = pessoaService.listar();
            if (pessoas != null) {
                for (Pessoa p : pessoas) {
                    modeloTabela.addRow(new Object[]{p.getId(), p.getNome(), p.getCpf(), p.getEmail()});
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dados do servidor:\n" + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPessoa() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Long id = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);
                pessoaService.deletar(id);
                JOptionPane.showMessageDialog(this, "Pessoa excluída com sucesso!");
                atualizarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
