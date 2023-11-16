package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final PedidoRepository pedidoRepository;
    private final CadastroPedidoService cadastroPedidoService;

    public PedidoController(PedidoModelAssembler pedidoModelAssembler, PedidoResumoModelAssembler pedidoResumoModelAssembler, PedidoRepository pedidoRepository, CadastroPedidoService cadastroPedidoService) {
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.pedidoResumoModelAssembler = pedidoResumoModelAssembler;
        this.pedidoRepository = pedidoRepository;
        this.cadastroPedidoService = cadastroPedidoService;
    }

    @GetMapping
    public List<PedidoResumoModel> listar() {
        return pedidoResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId) {
        return pedidoModelAssembler.toModel(cadastroPedidoService.buscarOuFalhar(pedidoId));
    }

}
