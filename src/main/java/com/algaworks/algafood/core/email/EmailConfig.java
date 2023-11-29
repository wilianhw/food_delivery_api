package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.FakeEmailEnvioService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    private final EmailProperties emailProperties;
    private final JavaMailSender mailSender;
    private final freemarker.template.Configuration freeMarkerConfig;

    public EmailConfig(EmailProperties emailProperties, JavaMailSender mailSender, freemarker.template.Configuration freeMarkerConfig) {
        this.emailProperties = emailProperties;
        this.mailSender = mailSender;
        this.freeMarkerConfig = freeMarkerConfig;
    }

    @Bean
    public EnvioEmailService envioEmailService() {
        if (EmailProperties.Implementacao.FAKE.equals(emailProperties.getImpl()))
            return new FakeEmailEnvioService(freeMarkerConfig);
        else
            return new SmtpEnvioEmailService(mailSender, emailProperties, freeMarkerConfig);
    }
}
