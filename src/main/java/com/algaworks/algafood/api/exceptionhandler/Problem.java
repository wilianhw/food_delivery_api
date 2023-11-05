package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {
    private String title;
    private int status;
    private String type;
    private String detail;

    private String userMessage;
    private LocalDateTime timestamp;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private String nome;
        private String userMessage;
    }
}
