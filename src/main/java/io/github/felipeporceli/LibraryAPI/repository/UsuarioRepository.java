package io.github.felipeporceli.LibraryAPI.repository;

import io.github.felipeporceli.LibraryAPI.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository <Usuario, UUID> {

    Usuario findByLogin(String login);
}
