package io.github.felipeporceli.LibraryAPI.controllers.mappers;

import io.github.felipeporceli.LibraryAPI.controllers.dto.UsuarioDTO;
import io.github.felipeporceli.LibraryAPI.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}

