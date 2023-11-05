package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrada"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    ERRO_NEGOCIO( "Violação de regra de negócio", "/erro-negocio"),
    MENSAGEM_IMCOMPREENSIVEL("Mensagem incompreensivel", "/mensagem-incompreensivel"),
    PROPRIEDADE_IGNORADA("Propriedade ignorada", "/propriedade-ignorada"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
    ERRO_DE_SISTEMA("Erro interno do sistema", "/erro-sistema");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "http://algafood.com.br" + path;
    }
}
