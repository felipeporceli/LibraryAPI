package io.github.felipeporceli.LibraryAPI.controllers.specs;

import io.github.felipeporceli.LibraryAPI.entities.Livro;
import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    // root - Representa a entidade que está sendo consultada, no caso "Livro".
    // query - Representa a consulta que está sendo construída, mas não é usada nesse caso.
    // cb - CriteriaBuilder, que cria as condições (where) dinamicamente.

    // Metodo que retorna uma Specification para filtrar entidades pelo campo isbn.
    // SELECT * FROM livro WHERE isbn = :isbn
    public static Specification<Livro> isbnEqual(String isbn) {
        return ((root, query, cb) -> cb.equal(root.get("isbn"), isbn) );
    }

    // SELECT * FROM livro WHERE UPPER(titulo) LIKE '%titulo.toUpperCase%'
    public static Specification<Livro> tituloLike (String titulo) {
        return (root, query, cb)
                -> cb.like( cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%"  );
    }

    // SELECT * FROM livro where genero = :genero
    public static Specification<Livro> generoEqual (GeneroLivro genero) {
        return ((root, query, cb) -> cb.equal(root.get("genero"), genero) );
    }

    // SELECT to_char (data_publicacao, 'YYYY') from livro = :anoPublicacao
    public static Specification<Livro> anoPublicacaoEqual (Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal( cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")),
                        anoPublicacao.toString());
    }


    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
            return cb.like(cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }


}
