package io.github.felipeporceli.LibraryAPI.controllers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;


//Interface com métodos genéricos para aplicarmos na nossa aplicação.
public interface GenericController {

    default URI gerarHeaderLocation(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
