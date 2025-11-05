package com.br.pdvfrontend.view;

import com.br.pdvfrontend.model.Pessoa;
import com.br.pdvfrontend.service.PessoaService;
import javax.swing.*;
import java.awt.*;

public class PessoaForm extends JDialog {

    private final JTextField txtNome, txtCpf, txtEmail, txtTelefone, txtEndereco;
    private final PessoaService pessoaService;
    private Pessoa pessoa; // Pode ser nulo para uma nova pessoa
    private final Runnable onSaveCallback; // Para atualizar a tabela do pai

    public PessoaForm(Frame parent, PessoaService pessoaService, Pessoa pessoa, Runnable onSaveCallback) {
        super(parent, true); // true para diálogo modal
        this.pessoaService = pessoaService;
        this.pessoa = pessoa;
        this.onSaveCallback = onSaveCallback;

        setTitle(pessoa == null ? "Nova Pessoa" : "Editar Pessoa");
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);

        // Painel do Formulário
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        formPanel.add(txtNome);

        formPanel.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        formPanel.add(txtCpf);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        formPanel.add(txtTelefone);

        formPanel.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        formPanel.add(txtEndereco);

        // Painel de Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Se for edição, preenche os campos
        if (pessoa != null) {
            txtNome.setText(pessoa.getNome());
            txtCpf.setText(pessoa.getCpf());
            txtEmail.setText(pessoa.getEmail());
            txtTelefone.setText(pessoa.getTelefone());
            txtEndereco.setText(pessoa.getEndereco());
        }
    }

    private void salvar() {
        // Cria um novo objeto Pessoa se estivermos criando um novo
        if (pessoa == null) {
            pessoa = new Pessoa();
        }

        // Preenche o objeto com os dados do formulário
        pessoa.setNome(txtNome.getText());
        pessoa.setCpf(txtCpf.getText());
        pessoa.setEmail(txtEmail.getText());
        pessoa.setTelefone(txtTelefone.getText());
        pessoa.setEndereco(txtEndereco.getText());

        try {
            if (pessoa.getId() == null) {
                pessoaService.salvar(pessoa);
                JOptionPane.showMessageDialog(this, "Pessoa salva com sucesso!");
            } else {
                pessoaService.atualizar(pessoa);
                JOptionPane.showMessageDialog(this, "Pessoa atualizada com sucesso!");
            }

            // Executa o callback para atualizar a tabela do pai
            if (onSaveCallback != null) {
                onSaveCallback.run();
            }

            // Fecha o diálogo
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
