package io.github.felipeporceli.LibraryAPI.validator;

import io.github.felipeporceli.LibraryAPI.entities.Livro;
import io.github.felipeporceli.LibraryAPI.exceptions.CampoInvalidoException;
import io.github.felipeporceli.LibraryAPI.exceptions.RegistroDuplicadoException;
import io.github.felipeporceli.LibraryAPI.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final static int ANO_EXIGENCIA_PRECO = 2020;
    private final LivroRepository repository;

    private boolean existeLivroComISBN(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if (livro.getId() != null) {
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));

    }

    private boolean isPrecoObrigatorioNulo (Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }


    public void validar (Livro livro) {
        if (existeLivroComISBN(livro)) {
            throw new RegistroDuplicadoException("ISBN já cadastrado!");
        }

        if (isPrecoObrigatorioNulo(livro)) {
            throw new CampoInvalidoException("preco", "Para livros com o ano de publicação a partir de 2020, o preco é obrigatório.");
        }

    }

    public void formatarIsbn (Livro livro) {
        livro.setIsbn(livro.getIsbn().replaceAll("\\D", ""));
    }
}
