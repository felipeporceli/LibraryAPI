package io.github.felipeporceli.LibraryAPI.repository;

import io.github.felipeporceli.LibraryAPI.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    // Query para buscar um autor pelo nome
    List<Autor> findByNome(String nome);

    // Query para buscar um autor pelo nome e nacionalidade
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    // Query para buscar um autor apenas pela nacionalidade
    List<Autor> findByNacionalidade(String nacionalidade);

    /* Query para buscar um autor pelo nome, dataNascimento e Nacionalidade. Ele retorna um Optional pois pode não
    retornar nenhum objeto */
    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade (String nome, LocalDate dataNascimento, String nacionalidade);
}
