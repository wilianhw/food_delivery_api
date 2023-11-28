package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedido;
    private final EnvioEmailService envioEmail;

    public FluxoPedidoService(EmissaoPedidoService emissaoPedido, EnvioEmailService envioEmail) {
        this.emissaoPedido = emissaoPedido;
        this.envioEmail = envioEmail;
    }

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);
        pedido.confirmar();

        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }

    @Transactional
    public void entregar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        pedido.cancelar();
    }
}
