package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {
    @Schema(example = "Dados inválidos")
    private String title;

    @Schema(example = "400")
    private int status;

    @Schema(example = "https://exemplo-url-erros.com.br")
    private String type;

    @Schema(example = "Um ou mais campos estão inválidos, Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(example = "Um ou mais campos estão inválidos, Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @Schema(example = "2022-07-15T11:21:50.802245498Z")
    private LocalDateTime timestamp;

    @Schema(example = "Lista de campos que geraram com erro")
    private List<Object> objects;

    @Schema(name = "ObjetoProblema")
    @Getter
    @Builder
    public static class Object {

        @Schema(example = "preco")
        private String nome;

        @Schema(example = "O preço é inválido")
        private String userMessage;
    }
}
