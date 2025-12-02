package io.github.felipeporceli.LibraryAPI.repository;

import io.github.felipeporceli.LibraryAPI.entities.Autor;
import io.github.felipeporceli.LibraryAPI.entities.Livro;
import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setTitulo("A fuga das galinhas");
        livro.setDataPublicacao(LocalDate.of(2023, 10, 04));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setPreco(69.99);
        livro.setIsbn("978-3-16-148410-0");

        Autor autor = autorRepository
                .findById(UUID.fromString("7aaadf31-c273-4670-b840-806a21bcbc05"))
                .orElse(null);
        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarTest() {
        UUID id = UUID.fromString("c94414a3-f617-464f-8069-c369cd818c98");
        Livro livroAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("fc5bfad8-afa2-48f2-868d-8a9ccab1d8ba");
        Autor autorAtualizar = autorRepository.findById(idAutor).orElse(null);

        livroAtualizar.setAutor(autorAtualizar);

        repository.save(livroAtualizar);
    }

    @Test
    void buscarLivroTest() {
        UUID id = UUID.fromString("c94414a3-f617-464f-8069-c369cd818c98");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());

    }

}