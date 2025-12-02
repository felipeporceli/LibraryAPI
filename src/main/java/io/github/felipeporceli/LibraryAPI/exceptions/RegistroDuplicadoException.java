package io.github.felipeporceli.LibraryAPI.exceptions;

// Classe para personalizarmos o lançamento de uma exceção quando um objeto já estiver armazenado no banco
public class RegistroDuplicadoException extends RuntimeException {

    public RegistroDuplicadoException(String message) {
        super(message);
    }

}
