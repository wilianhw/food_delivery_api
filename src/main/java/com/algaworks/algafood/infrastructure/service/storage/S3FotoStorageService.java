package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    private final AmazonS3 amazonS3;

    public S3FotoStorageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void remover(String nomeArquivo) {

    }

    @Override
    public void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {

    }
}
