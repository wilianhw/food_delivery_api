package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    private final FotoProdutoModelAssembler fotoProdutoModelAssembler;
    private final CadastroProdutoService cadastroProduto;
    private final CatalogoFotoProdutoService catalogoFotoProduto;

    public RestauranteFotoProdutoController(FotoProdutoModelAssembler fotoProdutoModelAssembler, CadastroProdutoService cadastroProduto, CatalogoFotoProdutoService catalogoFotoProduto) {
        this.fotoProdutoModelAssembler = fotoProdutoModelAssembler;
        this.cadastroProduto = cadastroProduto;
        this.catalogoFotoProduto = catalogoFotoProduto;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput
    ) {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoProdutoSalva = catalogoFotoProduto.salvar(fotoProduto);

        return fotoProdutoModelAssembler.toModel(fotoProdutoSalva);
    }
}
