package io.github.felipeporceli.LibraryAPI.controllers.dto;

import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @NotBlank (message = "Campo obrigatório")
        @ISBN (message = "ISBN inválido")
        String isbn,
        @NotBlank (message = "Campo obrigatório")
        String titulo,
        @NotNull (message = "Campo obrigatório")
        @Past (message = "Não pode ser data futura")
        LocalDate dataPublicacao,
        GeneroLivro genero,
        Double preco,
        @NotNull (message = "Campo obrigatório")
        UUID idAutor)
{

}
