package com.br.pdvfrontend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pessoa {

    private Long id;

    @JsonProperty("nomeCompleto")
    private String nome;

    @JsonProperty("cpfCnpj")
    private String cpf;

    private String email;
    private String telefone;
    private String endereco;

    @JsonProperty("numeroCtps")
    private Long numeroCtps;

    @JsonProperty("dataNascimento")
    private String dataNascimento; // pode usar LocalDate se preferir

    public Pessoa() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Long getNumeroCtps() { return numeroCtps; }
    public void setNumeroCtps(Long numeroCtps) { this.numeroCtps = numeroCtps; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    @Override
    public String toString() {
        return nome;
    }
}
