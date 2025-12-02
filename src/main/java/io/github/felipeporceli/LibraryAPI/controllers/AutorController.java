package io.github.felipeporceli.LibraryAPI.controllers;

import io.github.felipeporceli.LibraryAPI.controllers.dto.AutorDTO;
import io.github.felipeporceli.LibraryAPI.controllers.dto.ErroResposta;
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
public class AutorController {

    // Injeção de dependência para podermos chamar a camada de negócios
    private final AutorService service;


    // Metodo para podermos salvar um autor
    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody @Valid AutorDTO autor) {
        try {
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            // Esse código monta dinamicamente a URL para o recurso recém-criado usando o ID dele, seguindo o padrão REST
            // para retornar no header "Location" a URI onde o recurso pode ser consultado.
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    // Metodo para podermos consultar um autor atráves do seu ID passado na URL.
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes (@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterId(idAutor);

        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    // Metodo para podermos deletar um autor atráves do seu ID passado na URL.
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar (@PathVariable("id") String id) {
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    // Metodo para podermos pesquisar um autor filtrando pro Nome & Nacionalidade.
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar (@RequestParam(value = "nome", required = false) String nome,
                                                     @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());
    return ResponseEntity.ok(lista);
    }

    /* Metodo para podermos atualizar as informações de um autor. O ID é enviado pela URL, e as informações pelo Body da
    requisicao */
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar (
            @PathVariable ("id") String id,
            @RequestBody @Valid AutorDTO dto) {

        try {
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
        } catch (RegistroDuplicadoException e) {
            var erroDto = ErroResposta.respostaConflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    }

