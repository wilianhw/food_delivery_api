package com.algaworks.algafood.infrastructure.service.email;

import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ProcessadorEmailTemplate {

    private final Configuration freeMarkerConfig;

    public ProcessadorEmailTemplate(Configuration freeMarkerConfig) {
        this.freeMarkerConfig = freeMarkerConfig;
    }

    public String processarTemplate(EnvioEmailService.Mensagem mensagem) {
        try {
            Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possivel montar o template do email", e);
        }
    }
}
