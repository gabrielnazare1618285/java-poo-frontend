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
    private final JTextField txtId;
    private final JTextField txtNome;
    private final JTextField txtCpf;
    private final JTextField txtEmail;
    private final JTextField txtTelefone;
    private final JTextField txtEndereco;
    private final JButton btnSalvar;
    private final JButton btnExcluir;
    private final JButton btnNovo;

    public PessoaView(PessoaService pessoaService) {
        this.pessoaService = pessoaService;

        // Configurações da Janela
        setTitle("Cadastro de Pessoas");
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

        // Formulário de Cadastro
        JPanel painelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();
        txtEndereco = new JTextField();

        painelFormulario.add(new JLabel("ID:"));
        painelFormulario.add(txtId);
        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(txtNome);
        painelFormulario.add(new JLabel("CPF:"));
        painelFormulario.add(txtCpf);
        painelFormulario.add(new JLabel("Email:"));
        painelFormulario.add(txtEmail);
        painelFormulario.add(new JLabel("Telefone:"));
        painelFormulario.add(txtTelefone);
        painelFormulario.add(new JLabel("Endereço:"));
        painelFormulario.add(txtEndereco);

        painel.add(painelFormulario, BorderLayout.NORTH);

        // Painel de Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        // Adicionar painel principal à janela
        add(painel);

        // Listeners (Ações)
        tabela.getSelectionModel().addListSelectionListener(e -> preencherFormularioComLinhaSelecionada());
        btnNovo.addActionListener(e -> limparFormulario());
        btnSalvar.addActionListener(e -> salvarPessoa());
        btnExcluir.addActionListener(e -> excluirPessoa());

        // Carregar dados iniciais
        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        try {
            List<Pessoa> pessoas = pessoaService.listarTodos();
            for (Pessoa p : pessoas) {
                modeloTabela.addRow(new Object[]{p.getId(), p.getNome(), p.getCpf(), p.getEmail()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar dados do servidor:\n" + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            Long id = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);
            String nome = (String) modeloTabela.getValueAt(linhaSelecionada, 1);

            // Para obter todos os dados, teríamos que fazer uma busca por ID
            // ou já ter todos os dados carregados. Para simplificar, vamos
            // buscar apenas os dados já presentes na tabela.
            // Em uma aplicação real, aqui você faria pessoaService.buscarPorId(id)
            txtId.setText(id.toString());
            txtNome.setText(nome);

            // Limpamos o resto para evitar confusão. O ideal seria ter todos os dados.
            List<Pessoa> pessoas;
            try {
                pessoas = pessoaService.listarTodos();
                Pessoa pessoaSelecionada = pessoas.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
                if (pessoaSelecionada != null) {
                    txtCpf.setText(pessoaSelecionada.getCpf());
                    txtEmail.setText(pessoaSelecionada.getEmail());
                    txtTelefone.setText(pessoaSelecionada.getTelefone());
                    txtEndereco.setText(pessoaSelecionada.getEndereco());
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Não foi possível carregar todos os dados da pessoa.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void salvarPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(txtNome.getText());
        pessoa.setCpf(txtCpf.getText());
        pessoa.setEmail(txtEmail.getText());
        pessoa.setTelefone(txtTelefone.getText());
        pessoa.setEndereco(txtEndereco.getText());

        try {
            if (txtId.getText().isEmpty()) {
                // Criar novo
                pessoaService.salvar(pessoa);
                JOptionPane.showMessageDialog(this, "Pessoa salva com sucesso!");
            } else {
                // Atualizar existente
                pessoa.setId(Long.parseLong(txtId.getText()));
                pessoaService.atualizar(pessoa);
                JOptionPane.showMessageDialog(this, "Pessoa atualizada com sucesso!");
            }
            limparFormulario();
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPessoa() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Long id = Long.parseLong(txtId.getText());
                pessoaService.deletar(id);
                JOptionPane.showMessageDialog(this, "Pessoa excluída com sucesso!");
                limparFormulario();
                atualizarTabela();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparFormulario() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }
}
