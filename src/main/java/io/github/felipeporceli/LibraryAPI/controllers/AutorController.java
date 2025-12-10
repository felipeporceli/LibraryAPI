package io.github.felipeporceli.LibraryAPI.controllers;

import io.github.felipeporceli.LibraryAPI.controllers.dto.AutorDTO;
import io.github.felipeporceli.LibraryAPI.controllers.dto.ErroResposta;
import io.github.felipeporceli.LibraryAPI.controllers.mappers.AutorMapper;
import io.github.felipeporceli.LibraryAPI.entities.Autor;
import io.github.felipeporceli.LibraryAPI.exceptions.OperacaoNaoPermitidaException;
import io.github.felipeporceli.LibraryAPI.exceptions.RegistroDuplicadoException;
import io.github.felipeporceli.LibraryAPI.services.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


/* A classe AutorController é responsável por receber as requisições HTTP e de responder para o usuário. Também é
responsável por acionar a camada lógica de negócio (AutorService) Para poder realizar as operações */

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor // <- Anotation para gerar injeção de dependências automático
public class AutorController implements GenericController {

    // Injeção de dependência para podermos chamar a camada de negócios
    private final AutorService service;
    private final AutorMapper mapper;


    // Metodo para podermos salvar um autor
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);

        // Esse código monta dinamicamente a URL para o recurso recém-criado usando o ID dele, seguindo o padrão REST
        // para retornar no header "Location" a URI onde o recurso pode ser consultado.
        URI location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    // Metodo para podermos consultar um autor atráves do seu ID passado na URL.
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterId(idAutor);

        return service
                .obterId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Metodo para podermos deletar um autor atráves do seu ID passado na URL.
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    // Metodo para podermos pesquisar um autor filtrando pro Nome & Nacionalidade.
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                    @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    /* Metodo para podermos atualizar as informações de um autor. O ID é enviado pela URL, e as informações pelo Body da
    requisicao */
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,
            @RequestBody @Valid AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());
        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }

}

