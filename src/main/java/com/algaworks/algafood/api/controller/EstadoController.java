package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstado;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estado")
public class EstadoController {

    private final EstadoRepository estadoRepository;
    private final CadastroEstado cadastroEstado;

    public EstadoController(EstadoRepository estadoRepository, CadastroEstado cadastroEstado) {
        this.estadoRepository = estadoRepository;
        this.cadastroEstado = cadastroEstado;
    }

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody Estado estado) {
        estado = cadastroEstado.salvar(estado);

        return ResponseEntity.ok(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity atualizar(
            @PathVariable Long estadoId,
            @RequestBody Estado estado
    ) {
        try {
            Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

            BeanUtils.copyProperties(estado, estadoAtual, "id");

            return ResponseEntity.ok(estadoAtual);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<?> deletar(@PathVariable Long restauranteId) {
        try {
            Estado estado = cadastroEstado.buscarOuFalhar(restauranteId);

            cadastroEstado.apagar(estado);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
