package com.br.pdvfrontend.service;

import com.br.pdvfrontend.model.Pessoa;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PessoaService {

    // URL base da sua API backend. Altere se for diferente.
    private final String API_URL = "http://localhost:8080/pessoas";

    private final RestTemplate restTemplate;

    public PessoaService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Pessoa> listarTodos() {
        // Faz uma requisição GET para /pessoas e espera um array de Pessoa
        Pessoa[] pessoas = restTemplate.getForObject(API_URL, Pessoa[].class);
        return Arrays.asList(pessoas);
    }

    public Pessoa salvar(Pessoa pessoa) {
        // Faz uma requisição POST para /pessoas, enviando o objeto pessoa no corpo
        return restTemplate.postForObject(API_URL, pessoa, Pessoa.class);
    }

    public void atualizar(Pessoa pessoa) {
        // Faz uma requisição PUT para /pessoas/{id}
        restTemplate.put(API_URL + "/" + pessoa.getId(), pessoa);
    }

    public void deletar(Long id) {
        // Faz uma requisição DELETE para /pessoas/{id}
        restTemplate.delete(API_URL + "/" + id);
    }
}
