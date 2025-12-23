package io.github.felipeporceli.LibraryAPI.controllers.dto;

import java.util.List;

public record UsuarioDTO(String login,
                         String senha,
                         List<String> roles) {
}
