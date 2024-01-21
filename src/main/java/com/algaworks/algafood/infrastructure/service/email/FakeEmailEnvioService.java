package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailEnvioService implements EnvioEmailService {

    private final ProcessadorEmailTemplate processadorEmailTemplate;

    public FakeEmailEnvioService(ProcessadorEmailTemplate processadorEmailTemplate) {
        this.processadorEmailTemplate = processadorEmailTemplate;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        String corpo = processadorEmailTemplate.processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }
}
