package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController {

    private final EstadoModelAssembler estadoModelAssembler;
    private final EstadoInputDisassembler estadoInputDisassembler;
    private final EstadoRepository estadoRepository;
    private final CadastroEstadoService cadastroEstadoService;

    public EstadoController(EstadoModelAssembler estadoModelAssembler, EstadoInputDisassembler estadoInputDisassembler, EstadoRepository estadoRepository, CadastroEstadoService cadastroEstadoService) {
        this.estadoModelAssembler = estadoModelAssembler;
        this.estadoInputDisassembler = estadoInputDisassembler;
        this.estadoRepository = estadoRepository;
        this.cadastroEstadoService = cadastroEstadoService;
    }

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping("/{estadoId}")
    public EstadoModel buscar(@PathVariable Long estadoId) {
        return estadoModelAssembler.toModel(cadastroEstadoService.buscarOuFalhar(estadoId));
    }

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping
    public CollectionModel<EstadoModel> listar() {
        return estadoModelAssembler.toCollectionModel(estadoRepository.findAll());
    }

    @CheckSecurity.Estados.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel cadastrar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

        return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estado));
    }

    @CheckSecurity.Estados.PodeEditar
    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(
            @PathVariable Long estadoId,
            @RequestBody @Valid EstadoInput estadoInput
    ) {
        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        return estadoModelAssembler.toModel(cadastroEstadoService.salvar(estadoAtual));
    }

    @CheckSecurity.Estados.PodeEditar
    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long restauranteId) {
        cadastroEstadoService.apagar(restauranteId);
    }
}