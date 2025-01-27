package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

public class SandBoxEnvioEmailService implements EnvioEmailService {
    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;
    private final Configuration freeMarkerConfig;

    public SandBoxEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties, Configuration freeMarkerConfig) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
        this.freeMarkerConfig = freeMarkerConfig;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processarTemplate(mensagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setFrom(emailProperties.getRemetente());
            messageHelper.setTo(emailProperties.getSandbox().getDestinatario());
            messageHelper.setSubject(mensagem.getAssunto());
            messageHelper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar email", e);
        }
    }

    protected String processarTemplate(Mensagem mensagem) {
        try {
            Template template = freeMarkerConfig.getTemplate(mensagem.getCorpo());

            return FreeMarkerTemplateUtils.processTemplateIntoString(
                    template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possivel montar o template do email", e);
        }
    }
}
