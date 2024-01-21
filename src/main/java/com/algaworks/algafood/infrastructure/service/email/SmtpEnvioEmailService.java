package com.algaworks.algafood.infrastructure.service.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import jakarta.mail.internet.MimeMessage;

public class SmtpEnvioEmailService implements EnvioEmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;
    private final ProcessadorEmailTemplate processadorEmailTemplate;

    public SmtpEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties, ProcessadorEmailTemplate processadorEmailTemplate) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
        this.processadorEmailTemplate = processadorEmailTemplate;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processadorEmailTemplate.processarTemplate(mensagem);

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setFrom(emailProperties.getRemetente());
            messageHelper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            messageHelper.setSubject(mensagem.getAssunto());
            messageHelper.setText(corpo, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar email", e);
        }
    }
}
