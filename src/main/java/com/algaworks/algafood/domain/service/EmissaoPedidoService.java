package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmissaoPedidoService {

    private final CadastroCidadeService cadastroCidade;
    private final CadastroUsuarioService cadastroUsuario;
    private final CadastroRestauranteService cadastroRestaurante;
    private final CadastroProdutoService cadastroProduto;
    private final CadastroFormaPagamentoService cadastroFormaPagamento;
    private final PedidoRepository pedidoRepository;

    public EmissaoPedidoService(CadastroCidadeService cadastroCidade, CadastroUsuarioService cadastroUsuario, CadastroRestauranteService cadastroRestaurante, CadastroProdutoService cadastroProduto, CadastroFormaPagamentoService cadastroFormaPagamento, PedidoRepository pedidoRepository) {
        this.cadastroCidade = cadastroCidade;
        this.cadastroUsuario = cadastroUsuario;
        this.cadastroRestaurante = cadastroRestaurante;
        this.cadastroProduto = cadastroProduto;
        this.cadastroFormaPagamento = cadastroFormaPagamento;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(1L);
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEndereco().getCidade().getId());
        Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEndereco().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = cadastroProduto.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
