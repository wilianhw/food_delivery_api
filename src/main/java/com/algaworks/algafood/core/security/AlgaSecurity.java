package com.algaworks.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class AlgaSecurity {

    private final RestauranteRepository restauranteRepository;
    private final PedidoRepository pedidoRepository;

    public AlgaSecurity(RestauranteRepository restauranteRepository, PedidoRepository pedidoRepository) {
        this.restauranteRepository = restauranteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return Long.parseLong(jwt.getClaim("usuarioId"));
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        if (restauranteId == null) {
            return false;
        }

        return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
    }

    public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }

    public boolean usuarioAuthenticadoIgual(Long usuarioId) {
        return getUsuarioId() != null && usuarioId != null && getUsuarioId().equals(usuarioId);
    }

    public boolean podeGerenciarPedidos(String codigoPedido) {

        return hasAuthority("SCOPE_WRITE") &&
                (hasAuthority("GERENCIAR_PEDIDOS") || gerenciaRestauranteDoPedido(codigoPedido));
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }
}
