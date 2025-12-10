package io.github.felipeporceli.LibraryAPI.controllers.dto;

import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;

import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(UUID id,
                                        String isbn,
                                        String titulo,
                                        LocalDate dataPublicacao,
                                        GeneroLivro genero,
                                        Double preco,
                                        AutorDTO autor) {
}
