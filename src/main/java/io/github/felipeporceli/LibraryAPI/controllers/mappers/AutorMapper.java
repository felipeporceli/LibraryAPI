package io.github.felipeporceli.LibraryAPI.controllers.mappers;

import io.github.felipeporceli.LibraryAPI.controllers.dto.AutorDTO;
import io.github.felipeporceli.LibraryAPI.entities.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);

}
