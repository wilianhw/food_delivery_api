package com.algaworks.algafood.infrastructure.service.email;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SmptEnvioEmailService implements EnvioEmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public SmptEnvioEmailService(JavaMailSender mailSender, EmailProperties emailProperties) {
        this.mailSender = mailSender;
        this.emailProperties = emailProperties;
    }

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            messageHelper.setFrom(emailProperties.getRemetente());
            messageHelper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            messageHelper.setSubject(mensagem.getAssunto());
            messageHelper.setText(mensagem.getCorpo(), true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar email", e);
        }
    }
}
