package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoConfirmadoListener {

    private final EnvioEmailService envioEmail;

    public NotificacaoClientePedidoConfirmadoListener(EnvioEmailService envioEmail) {
        this.envioEmail = envioEmail;
    }

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();
        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}
