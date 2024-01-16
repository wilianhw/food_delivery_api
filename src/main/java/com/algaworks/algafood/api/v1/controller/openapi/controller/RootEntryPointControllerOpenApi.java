package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.RepresentationModel;

import com.algaworks.algafood.api.v1.controller.RootEntryPointController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface RootEntryPointControllerOpenApi {
    public static class RootEntryPointModel extends RepresentationModel<RootEntryPointController.RootEntryPointModel> {

    }

    @Operation(hidden = true)
    RootEntryPointModel root();
}
