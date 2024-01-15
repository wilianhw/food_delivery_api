package com.algaworks.algafood.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class RestauranteIdInput {

    @Schema(example = "1")
    private Long id;
}
