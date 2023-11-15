package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestaurante {

    private final RestauranteRepository restauranteRepository;
    private final CadastroCozinha cadastroCozinha;
    private final CadastroCidade cadastroCidade;
    private final CadastroFormaPagamento cadastroFormaPagamento;

    public CadastroRestaurante(RestauranteRepository restauranteRepository, CadastroCozinha cadastroCozinha, CadastroCidade cadastroCidade, CadastroFormaPagamento cadastroFormaPagamento) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroCozinha = cadastroCozinha;
        this.cadastroCidade = cadastroCidade;
        this.cadastroFormaPagamento = cadastroFormaPagamento;
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
        Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void apagar(Long restauranteId) {
        buscarOuFalhar(restauranteId);
        try {
            restauranteRepository.deleteById(restauranteId);
            restauranteRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Restaurante de código %d não pode ser apagado pois está em uso", restauranteId)
            );
        }
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        restaurante.ativar();
    }

    @Transactional
    public void desassociar(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        restaurante.desassociarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void associar(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);

        restaurante.associarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void inativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);

        restaurante.inativar();
    }
}
