package io.github.felipeporceli.LibraryAPI.controllers;

import io.github.felipeporceli.LibraryAPI.entities.Livro;
import io.github.felipeporceli.LibraryAPI.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:5173")// Vite
@RequiredArgsConstructor // <- Anotation para gerar injeção de dependências automático
public class LivroController {

    private final LivroService service;

    @PostMapping
    public Livro criarLivro(@RequestBody Livro livro) {
        return service.salvar(livro);
    }

    @GetMapping
    public List<Livro> listarLivros() {
        return service.listar();
    }
}
