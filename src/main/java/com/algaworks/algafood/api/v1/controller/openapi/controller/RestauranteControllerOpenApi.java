package com.algaworks.algafood.api.v1.controller.openapi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurante", description = "Gerencia restaurantes")
public interface RestauranteControllerOpenApi {

    @Operation(summary = "Lista restaurantes", parameters = {
            @Parameter(name = "projecao",
                    description = "Nome da projeção",
                    example = "apenas-nome",
                    in = ParameterIn.QUERY,
                    required = false
            )
    })
    CollectionModel<RestauranteBasicoModel> list();

    @Operation(hidden = true)
    Restaurante consultarPorNome(String nome, Long cozinhaId);

    @Operation(hidden = true)
    Restaurante consultarPorQueryNomeada(String nome, Long cozinhaId);

    @Operation(hidden = true)
    List<Restaurante> consultarPorNomeTaxaFrete(
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    );

    @Operation(hidden = true)
    List<Restaurante> consultarPorNomeTaxaFreteCriteria(
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    );

    @Operation(hidden = true)
    List<Restaurante> consultarPorNomeTaxaFreteSDJ(String nome);

    @Operation(summary = "Busca um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    RestauranteModel buscar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            Long restauranteId
    );

    @Operation(summary = "Cadastra um novo restaurante",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurante cadastrado")
            })
    RestauranteModel cadastrar(RestauranteInput restauranteInput);

    @Operation(summary = "Busca um restaurante por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    RestauranteModel atualizar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            Long restauranteId,
            @RequestBody(description = "Representação de um novo restaurante")
            RestauranteInput restauranteInput
    );

    @Operation(summary = "Exclui um restaurante por ID")
    void apagar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            Long restauranteId
    );

    @Operation(summary = "Abre um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ResponseEntity<Void> ativar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            Long restauranteId
    );

    @Operation(summary = "Ativa múltiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
    })
    void ativarMultiplos(
            @RequestBody(description = "IDs de restaurantes")
            List<Long> restauranteIds
    );

    @Operation(summary = "Inativa múltiplos restaurantes", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
    })
    void inativarMultiplos(
            @RequestBody(description = "IDs de restaurantes")
            List<Long> restauranteIds
    );

    @Operation(summary = "Desativa um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ResponseEntity<Void> inativar(
            @Parameter(description = "ID de um restaurante")
            Long restauranteId
    );

    @Operation(summary = "Abre um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ResponseEntity<Void> abrir(
            @Parameter(description = "ID de um restaurante")
            Long restauranteId
    );

    @Operation(summary = "Fecha um restaurante por ID", responses = {
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ResponseEntity<Void> fechar(
            @Parameter(description = "ID de um restaurante")
            Long restauranteId
    );
}
