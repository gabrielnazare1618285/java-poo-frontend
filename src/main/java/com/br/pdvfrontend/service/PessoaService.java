package com.br.pdvfrontend.service;

import com.br.pdvfrontend.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PessoaService {

    private final String BASE_URL = "http://localhost:8080/api/v1/pessoas";
    private final RestTemplate restTemplate;

    @Autowired
    public PessoaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Pessoa> listar() {
        ResponseEntity<Pessoa[]> response = restTemplate.getForEntity(BASE_URL, Pessoa[].class);
        if (response.getBody() != null) {
            return Arrays.asList(response.getBody());
        }
        return List.of(); // Retorna lista vazia se a resposta for nula
    }

    public Pessoa buscarPorId(Long id) {
        String url = BASE_URL + "/" + id;
        ResponseEntity<Pessoa> response = restTemplate.getForEntity(url, Pessoa.class);
        return response.getBody();
    }

    public Pessoa salvar(Pessoa pessoa) {
        ResponseEntity<Pessoa> response = restTemplate.postForEntity(BASE_URL, pessoa, Pessoa.class);
        return response.getBody();
    }

    public void atualizar(Pessoa pessoa) {
        restTemplate.put(BASE_URL + "/" + pessoa.getId(), pessoa);
    }

    public void deletar(Long id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}
