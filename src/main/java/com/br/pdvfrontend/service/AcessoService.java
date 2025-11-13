package com.br.pdvfrontend.service;

import com.br.pdvfrontend.dto.AcessoRequest;
import com.br.pdvfrontend.dto.AcessoResponse;
import com.br.pdvfrontend.model.Acesso;
import com.br.pdvfrontend.util.HttpClient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AcessoService {

    public AcessoService() {
    }

    public void addAcesso(Acesso acesso) {
        try {
            AcessoRequest request = new AcessoRequest(
                    acesso.getUsuario(),
                    acesso.getSenha()
            );

            HttpClient.post("/acessos", request, AcessoResponse.class);
            // Mensagem removida para não aparecer no login automático
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar acesso: " + e.getMessage(), e);
        }
    }

    public void addAcessoComPessoa(String usuario, String senha, Long pessoaId, String role) {
        try {
            AcessoRequest request = new AcessoRequest(usuario, senha, pessoaId, role);
            HttpClient.post("/acessos", request, AcessoResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar acesso: " + e.getMessage(), e);
        }
    }

    public List<Acesso> getAllAcessos() {
        try {
            AcessoResponse[] responses = HttpClient.getArray("/acessos", AcessoResponse[].class);
            List<Acesso> acessos = new ArrayList<>();
            for (AcessoResponse response : responses) {
                Acesso acesso = new Acesso(
                        response.getUsuario(),
                        response.getSenha()
                );
                acesso.setId(response.getId());
                acesso.setRole(response.getRole()); // ADICIONAR ROLE
                acesso.setNomeCompleto(response.getNomeCompleto());
                acessos.add(acesso);
            }
            return acessos;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void removeAcesso(Long id) {
        try {
            HttpClient.delete("/acessos/" + id);
            JOptionPane.showMessageDialog(null,
                    "Acesso removido com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao remover acesso: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateAcesso(Long id, Acesso acesso) {
        try {
            AcessoRequest request = new AcessoRequest(
                    acesso.getUsuario(),
                    acesso.getSenha()
            );

            HttpClient.put("/acessos/" + id, request, AcessoResponse.class);
            JOptionPane.showMessageDialog(null,
                    "Acesso atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao atualizar acesso: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Verifica se já existe um acesso com o usuário especificado
     */
    public boolean usuarioJaExiste(String usuario) {
        try {
            List<Acesso> acessos = getAllAcessos();
            return acessos.stream()
                    .anyMatch(acesso -> acesso.getUsuario().equals(usuario));
        } catch (Exception e) {
            System.err.println("Erro ao verificar usuário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Faz login no sistema
     */
    public AcessoResponse login(String usuario, String senha) {
        try {
            AcessoRequest request = new AcessoRequest(usuario, senha);
            AcessoResponse response = HttpClient.post("/acessos/login", request, AcessoResponse.class);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Usuário ou senha incorretos!");
        }
    }

    /**
     * Verifica se já existe um administrador no sistema
     */
    public boolean hasAdmin() {
        try {
            List<Acesso> acessos = getAllAcessos();

            if (acessos == null || acessos.isEmpty()) {
                return false;
            }

            // Verifica se existe algum acesso com role ADMIN
            boolean hasAdminRole = acessos.stream()
                    .anyMatch(acesso -> acesso.getRole() != null && "ADMIN".equalsIgnoreCase(acesso.getRole()));

            System.out.println("Verificação de admin: " + hasAdminRole + " (Total de acessos: " + acessos.size() + ")");

            return hasAdminRole;
        } catch (Exception e) {
            System.err.println("Erro ao verificar admin: " + e.getMessage());
            e.printStackTrace();
            // Em caso de erro, assume que não tem admin para permitir criação
            return false;
        }
    }

    /**
     * Cria o primeiro administrador do sistema
     */
    public void criarPrimeiroAdmin(String usuario, String senha) {
        try {
            // Verifica se já existe um admin
            if (hasAdmin()) {
                throw new RuntimeException("Já existe um administrador no sistema!");
            }

            AcessoRequest request = new AcessoRequest(usuario, senha, null, "ADMIN");
            HttpClient.post("/acessos/primeiro-admin", request, AcessoResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar administrador: " + e.getMessage(), e);
        }
    }
}
