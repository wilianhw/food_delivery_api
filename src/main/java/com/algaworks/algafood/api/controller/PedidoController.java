package com.algaworks.algafood.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpec;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final EmissaoPedidoService emissaoPedidoService;
    private final PedidoRepository pedidoRepository;
    private final PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    public PedidoController(PedidoModelAssembler pedidoModelAssembler, PedidoInputDisassembler pedidoInputDisassembler, PedidoResumoModelAssembler pedidoResumoModelAssembler, PedidoRepository pedidoRepository, EmissaoPedidoService emissaoPedidoService, PagedResourcesAssembler<Pedido> pagedResourcesAssembler) {
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.pedidoInputDisassembler = pedidoInputDisassembler;
        this.pedidoResumoModelAssembler = pedidoResumoModelAssembler;
        this.pedidoRepository = pedidoRepository;
        this.emissaoPedidoService = emissaoPedidoService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @CheckSecurity.Pedidos.PodePesquisar
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable) {
        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpec.usandoFiltro(filtro), pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido) {
        return pedidoModelAssembler.toModel(emissaoPedidoService.buscarOuFalhar(codigoPedido));
    }

    @CheckSecurity.Pedidos.PodeCriar
    @PostMapping
    public PedidoModel cadastrar(@RequestBody PedidoInput pedidoInput) {
        Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

        return pedidoModelAssembler.toModel(emissaoPedidoService.emitir(pedido));
    }
}
