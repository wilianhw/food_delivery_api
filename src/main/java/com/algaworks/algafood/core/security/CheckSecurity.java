package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultarCozinhas {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditarCozinhas {
        }
    }

    public @interface Restaurantes {
        @PreAuthorize("hasAuthority('SCOPE_READ') && isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultarRestaurantes {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarCadastro {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') && " +
                "hasAuthority('EDITAR_RESTAURANTES') ||" +
                "@algaSecurity.gerenciaRestaurante(#restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarFuncionamento {
        }
    }

    public @interface Pedidos {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.usuarioAuthenticadoIgual(returnObject.cliente.id) or " +
                "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeBuscar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.usuarioAuthenticadoIgual(#filtro.clienteId) or " +
                "@algaSecurity.gerenciaRestaurante(#filtro.restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodePesquisar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeCriar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('GERENCIAR_PEDIDOS') or" +
                "@algaSecurity.gerenciaRestauranteDoPedido(#codigoPedido)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarPedidos {
        }
    }

    public @interface FormaPagamento {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface Cidades {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface Estados {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }
    }

    public @interface UsuarioGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "@algaSecurity.usuarioAuthenticadoIgual(#filtro.clienteId)")
        public @interface PodeAlterarPropriaSenha {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "@algaSecurity.usuarioAuthenticadoIgual(#usuarioId) or " +
                "hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeAlterarUsuario {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }
    }

    public @interface Estatisticas {
        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GERAR_RELATORIOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }
    }
}
