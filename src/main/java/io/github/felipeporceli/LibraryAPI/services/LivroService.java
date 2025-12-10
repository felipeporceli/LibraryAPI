package io.github.felipeporceli.LibraryAPI.services;

import io.github.felipeporceli.LibraryAPI.controllers.specs.LivroSpecs;
import io.github.felipeporceli.LibraryAPI.entities.Livro;
import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;
import io.github.felipeporceli.LibraryAPI.repository.LivroRepository;
import io.github.felipeporceli.LibraryAPI.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor // <- Anotation para gerar construtor injeção de dependências automático
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator validator;

    public Livro salvar(Livro livro) {
        validator.formatarIsbn(livro);
        validator.validar(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId (UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar (Livro livro) {
        livroRepository.delete(livro);
    }


 /* O metodo cria uma Specification inicial “vazia” (TRUE) e vai adicionando filtros somente quando parâmetros são
    informados, resultando em uma busca dinâmica e flexível. */

    public Page<Livro> pesquisa (String isbn,
                                 String nomeAutor,
                                 String titulo,
                                 GeneroLivro genero,
                                 Integer anoPublicacao,
                                 Integer pagina,
                                 Integer tamanhoPagina) {

        // SELECT * FROM livro where 0 = 0
        Specification<Livro> specification = Specification.where(((root, query, cb) -> cb.conjunction() ));

        if (isbn != null) {
            specification = specification.and(LivroSpecs.isbnEqual(isbn));
        }

        if (titulo != null ) {
            specification = specification.and(LivroSpecs.tituloLike(titulo));
        }

        if (genero != null ) {
            specification = specification.and(LivroSpecs.generoEqual(genero));
        }

        if (anoPublicacao != null) {
            specification = specification.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return livroRepository.findAll(specification, pageRequest);

    }

    public void atualizar(Livro livro) {

        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já esteja cadastrado no " +
                    "banco de dados");

        }
        validator.validar(livro);
        livroRepository.save(livro);

    }
}
