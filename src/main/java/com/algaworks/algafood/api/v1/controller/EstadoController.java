package com.algaworks.algafood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.EstadoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.EstadoInputDisassembler;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/estados")
public class EstadoController implements com.algaworks.algafood.api.v1.controller.openapi.controller.EstadoControllerOpenApi {

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
