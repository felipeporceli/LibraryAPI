package io.github.felipeporceli.LibraryAPI.repository;

import io.github.felipeporceli.LibraryAPI.entities.Autor;
import io.github.felipeporceli.LibraryAPI.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {
    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn (String isbn);

}
