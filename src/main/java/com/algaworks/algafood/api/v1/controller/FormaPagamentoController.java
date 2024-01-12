package com.algaworks.algafood.api.v1.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    private final FormaPagamentoDisassembler formaPagamentoDisassembler;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final CadastroFormaPagamentoService cadastroFormaPagamentoService;

    public FormaPagamentoController(FormaPagamentoModelAssembler formaPagamentoModelAssembler, FormaPagamentoDisassembler formaPagamentoDisassembler, FormaPagamentoRepository formaPagamentoRepository, CadastroFormaPagamentoService cadastroFormaPagamentoService) {
        this.formaPagamentoModelAssembler = formaPagamentoModelAssembler;
        this.formaPagamentoDisassembler = formaPagamentoDisassembler;
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.cadastroFormaPagamentoService = cadastroFormaPagamentoService;
    }

    @CheckSecurity.FormaPagamento.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        String eTag = "0";

        LocalDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();

        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond(ZoneOffset.UTC));
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

        CollectionModel<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler.toCollectionModel(formaPagamentoRepository.findAll());

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag(eTag)
                .body(formasPagamentoModel);
    }

    @CheckSecurity.FormaPagamento.PodeConsultar
    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @CheckSecurity.FormaPagamento.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoDisassembler.toDomainObject(formaPagamentoInput);

        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamento));
    }

    @CheckSecurity.FormaPagamento.PodeEditar
    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoModel atualizar(
            @PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        formaPagamentoDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        return formaPagamentoModelAssembler.toModel(cadastroFormaPagamentoService.salvar(formaPagamentoAtual));
    }

    @CheckSecurity.FormaPagamento.PodeEditar
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long formaPagamentoId) {
        cadastroFormaPagamentoService.deletar(formaPagamentoId);
    }
}
