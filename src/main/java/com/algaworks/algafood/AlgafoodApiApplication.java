package com.algaworks.algafood;

import com.algaworks.algafood.core.io.Base64ProtocolResolver;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

    public static void main(String[] args) {

        var app = new SpringApplication(AlgafoodApiApplication.class);
        app.addListeners(new Base64ProtocolResolver());
        app.run(args);
    }
}
