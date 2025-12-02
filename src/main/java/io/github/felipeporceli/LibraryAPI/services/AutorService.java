package io.github.felipeporceli.LibraryAPI.services;

import io.github.felipeporceli.LibraryAPI.entities.Autor;
import io.github.felipeporceli.LibraryAPI.exceptions.OperacaoNaoPermitidaException;
import io.github.felipeporceli.LibraryAPI.repository.AutorRepository;
import io.github.felipeporceli.LibraryAPI.repository.LivroRepository;
import io.github.felipeporceli.LibraryAPI.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // <- Anotation para gerar construtor injeção de dependências automático
public class AutorService {

    // Injeções de dependência.
    private final AutorValidator validator;
    private final AutorRepository repository;
    private final LivroRepository livroRepository;

    // Metodo para salvar o autor a nivel de serviço.
    public Autor salvar (Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    // Metodo para buscar um autor pelo id.
    public Optional<Autor> obterId (UUID id) {
        return repository.findById(id);
    }

    /* Metodo para deletar um autor, também é realizado a verificação se ele tem um livro no nome dele cadastrado no
    banco, caso tenha, o usuario recebe uma exceção do tipo "OperacaoNaoPermitidaException". A nível de serviço. */
    public void deletar (Autor autor) {
        if (possuilivro(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor que possui livros cadastrados");
        }
        repository.delete(autor);
    }

    /* Query by Example (QBE) é uma técnica no Spring Data que permite criar consultas dinâmicas de forma simples,
    usando uma instância de um objeto como um "exemplo". Utilizamos um objeto do tipo "Matcher" onde definimos como
    cada campo deve ser comparado (igual, contém, começa com, ignora maiúsculas, etc.) */

    public List<Autor> pesquisaByExample (String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    /* Metodo para atualizar um autor. É realizado a verificação se o ID realmente existe na base de dados, caso ele não
    exista, é lançado uma exceção do tipo IllegalArgumentException */
    public void atualizar (Autor autor) {
        if(autor.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja cadastrado no " +
                    "banco de dados");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public boolean possuilivro (Autor autor) {
        return livroRepository.existsByAutor(autor);
    }
}
