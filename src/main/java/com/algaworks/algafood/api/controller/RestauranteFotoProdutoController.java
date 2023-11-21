package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput
    ) {
        String nomeArquivo = UUID.randomUUID() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

        Path arquivoFoto = Path.of("/home/wilian/catalogo", nomeArquivo);

        try {
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {

        }
    }
}
