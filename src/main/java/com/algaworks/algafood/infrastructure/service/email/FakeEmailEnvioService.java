package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Slf4j
public class FakeEmailEnvioService implements EnvioEmailService {

    private final freemarker.template.Configuration freeMarkerConfig;

    public FakeEmailEnvioService(freemarker.template.Configuration freeMarkerConfig) {
        this.freeMarkerConfig = freeMarkerConfig;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }

    private String processarTemplate(Mensagem mensagem) {
        try {
            Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possivel montar o template do email", e);
        }
    }
}
