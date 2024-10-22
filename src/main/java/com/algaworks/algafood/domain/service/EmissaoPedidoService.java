package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
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
    private final AlgaSecurity algaSecurity;
    private final PedidoRepository pedidoRepository;

    public EmissaoPedidoService(CadastroCidadeService cadastroCidade, CadastroUsuarioService cadastroUsuario, CadastroRestauranteService cadastroRestaurante, CadastroProdutoService cadastroProduto, CadastroFormaPagamentoService cadastroFormaPagamento, AlgaSecurity algaSecurity, PedidoRepository pedidoRepository) {
        this.cadastroCidade = cadastroCidade;
        this.cadastroUsuario = cadastroUsuario;
        this.cadastroRestaurante = cadastroRestaurante;
        this.cadastroProduto = cadastroProduto;
        this.cadastroFormaPagamento = cadastroFormaPagamento;
        this.algaSecurity = algaSecurity;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido buscarOuFalhar(String codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        pedido.setCliente(new Usuario());
        pedido.getCliente().setId(algaSecurity.getUsuarioId());
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
