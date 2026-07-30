package com.itb.inf3cm.pizzariabemmelhor.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.itb.inf3cm.pizzariabemmelhor.model.enums.Permission.*;


@Getter
@AllArgsConstructor
public enum TipoUsuario {

    ADMIN (
            Set.of(
                FUNCIONARIO_READ,
                FUNCIONARIO_CREATE,
                FUNCIONARIO_UPDATE,
                FUNCIONARIO_DELETE,
                FUNCIONARIO_LIST,
                CLIENTE_LIST,
                CLIENTE_READ,
                CLIENTE_MANAGE,
                PEDIDO_READ,
                PEDIDO_LIST,
                PEDIDO_MANAGE,
                PRODUTO_CREATE,
                PRODUTO_READ,
                PRODUTO_UPDATE,
                PRODUTO_DELETE,
                CATEGORIA_CREATE,
                CATEGORIA_READ,
                CATEGORIA_UPDATE,
                CATEGORIA_DELETE

         )
    ),
    CLIENTE (
            Set.of(
                    CLIENTE_READ,
                    CLIENTE_UPDATE,
                    PEDIDO_READ,
                    PEDIDO_CREATE,
                    PRODUTO_READ,
                    CATEGORIA_READ
            )
    ),
    FUNCIONARIO (
            Set.of(
                    FUNCIONARIO_READ,
                    FUNCIONARIO_UPDATE,
                    CLIENTE_LIST,
                    CLIENTE_READ,
                    CLIENTE_MANAGE,
                    PEDIDO_READ,
                    PEDIDO_LIST,
                    PEDIDO_MANAGE,
                    PRODUTO_CREATE,
                    PRODUTO_READ,
                    PRODUTO_UPDATE,
                    PRODUTO_DELETE,
                    PRODUTO_MANAGE,
                    CATEGORIA_CREATE,
                    CATEGORIA_READ,
                    CATEGORIA_UPDATE,
                    CATEGORIA_DELETE,
                    CATEGORIA_MANAGE
            )
    );

    private final Set<Permission> permissions;

    // Método que retorna o que cada tipo de usuário poderá realizar no sistema "permissões"

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
      authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
      return authorities;
    }


}
