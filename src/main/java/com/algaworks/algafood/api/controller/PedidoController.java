package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;
    private final PedidoResumoModelAssembler pedidoResumoModelAssembler;
    private final PedidoRepository pedidoRepository;
    private final CadastroPedidoService cadastroPedidoService;

    public PedidoController(PedidoModelAssembler pedidoModelAssembler, PedidoInputDisassembler pedidoInputDisassembler, PedidoResumoModelAssembler pedidoResumoModelAssembler, PedidoRepository pedidoRepository, CadastroPedidoService cadastroPedidoService) {
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.pedidoInputDisassembler = pedidoInputDisassembler;
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

    @PostMapping
    public PedidoModel cadastrar(@RequestBody PedidoInput pedidoInput) {
        Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

        return pedidoModelAssembler.toModel(cadastroPedidoService.emitir(pedido));
    }
}
