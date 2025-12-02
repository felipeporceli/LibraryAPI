package io.github.felipeporceli.LibraryAPI.repository;

import io.github.felipeporceli.LibraryAPI.entities.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Pedro Guimarães");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1974, 01, 24));

        Autor autorSalvo = repository.save(autor);
        System.out.println("Autor salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("70d89920-524f-4811-89f6-4325f3b3e01b");
        Optional<Autor> possivelAutor = repository.findById(id);
        if (possivelAutor.isPresent()) {
            Autor autoEncontrado = possivelAutor.get();
            System.out.print("Dados do autor: ");
            System.out.print(possivelAutor.get());
            autoEncontrado.setDataNascimento(LocalDate.of(1990, 04,10));
            repository.save(autoEncontrado);
        }
    }

    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("019bfed1-9b28-4507-9345-79fcc33191b1");
        repository.deleteById(id);
    }


}
