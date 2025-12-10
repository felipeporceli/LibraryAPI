package io.github.felipeporceli.LibraryAPI.controllers;

import io.github.felipeporceli.LibraryAPI.controllers.dto.CadastroLivroDTO;
import io.github.felipeporceli.LibraryAPI.controllers.dto.ErroResposta;
import io.github.felipeporceli.LibraryAPI.controllers.dto.ResultadoPesquisaLivroDTO;
import io.github.felipeporceli.LibraryAPI.controllers.mappers.LivroMapper;
import io.github.felipeporceli.LibraryAPI.entities.Livro;
import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;
import io.github.felipeporceli.LibraryAPI.exceptions.RegistroDuplicadoException;
import io.github.felipeporceli.LibraryAPI.services.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor // <- Anotation para gerar injeção de dependências automático
public class LivroController implements GenericController {

    private final LivroService service;

    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        // Mapear DTO para entidade
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        URI location = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes (@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar (@PathVariable("id") String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa (
            @RequestParam (value = "isbn", required = false)
            String isbn,

            @RequestParam (value = "autor", required = false)
            String nomeAutor,

            @RequestParam (value = "titulo", required = false)
            String titulo,

            @RequestParam (value = "genero", required = false)
            GeneroLivro genero,

            @RequestParam (value = "ano-publicacao", required = false)
            Integer anoPublicacao,

            @RequestParam (value = "pagina", defaultValue = "0")
            Integer pagina,

            @RequestParam (value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ) {
        Page<Livro> paginaResultado = service.pesquisa(isbn, nomeAutor, titulo, genero, anoPublicacao, pagina, tamanhoPagina);
        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping ("{id}")
    public ResponseEntity<Object> atualizar (@PathVariable("id") String id, @RequestBody CadastroLivroDTO dto) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAux = mapper.toEntity(dto);
                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                    livro.setIsbn(entidadeAux.getIsbn());
                    livro.setPreco(entidadeAux.getPreco());
                    livro.setGenero(entidadeAux.getGenero());
                    livro.setTitulo(entidadeAux.getTitulo());
                    livro.setAutor(entidadeAux.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();


                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
