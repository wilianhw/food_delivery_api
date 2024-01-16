package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Usuarios", description = "Gerencia os usuários")
public interface UsuarioControllerOpenApi {

    @Operation(summary = "Lista os usuários")
    CollectionModel<UsuarioModel> listar();

    @Operation(summary = "Busca um usuário por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    UsuarioModel buscar(@Parameter(description = "ID de um usuário", example = "1", required = true) Long usuarioId);

    @Operation(summary = "Cadastra um usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
    })
    UsuarioModel cadastrar(@RequestBody(description = "Representação de um usuário") UsuarioComSenhaInput usuarioInput);

    @Operation(summary = "Atualiza um usuário por ID", responses = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))})
    })
    UsuarioModel atualizar(
            @Parameter(description = "ID de um usuário", example = "1", required = true) Long usuarioId,
            @RequestBody(description = "Representação de um usuário") UsuarioInput usuarioInput);

    @Operation(summary = "Exclui um usuário por ID", responses = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    void deletar(@Parameter(description = "ID de um usuário", example = "1", required = true) Long usuarioId);

    @Operation(summary = "Atualiza a senha de um usuário", responses = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))})
    })
    void atualizarSenha(
            @Parameter(description = "ID de um usuário", example = "1", required = true) Long usuarioId,
            @RequestBody(description = "Representação de um usuário") SenhaInput senhaInput);
}
