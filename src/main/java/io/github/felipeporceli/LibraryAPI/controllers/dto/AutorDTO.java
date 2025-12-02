package io.github.felipeporceli.LibraryAPI.controllers.dto;

import io.github.felipeporceli.LibraryAPI.entities.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;


/* Essa entidade é para utilizarmos o padrão DTO no nosso projeto. O padrão DTO é usado para realizar transferência de
 dados, seja como saída ou entrada. No caso do projeto vamos utilizar para que o usuário mapeia apenas o nome,
 dataNascimento e nacionalidade para a criação de um objeto do tipo Autor.*/

public record
AutorDTO (UUID id,
          @NotBlank (message = "Campo obrigatório")
          @Size (min = 3, max = 100, message = "Campo fora do tamanho permitido")
          String nome,
          @NotNull (message = "Campo obrigatório")
          @Past (message = "Data de nascimento não pode ser uma data futura")
          LocalDate dataNascimento,
          @NotBlank (message = "Campo obrigatório")
          @Size (min = 3, max = 50, message = "Campo fora do tamanho permitido")
          String nacionalidade) {

    // Metodo para criar o objeto do tipo Autor
    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
