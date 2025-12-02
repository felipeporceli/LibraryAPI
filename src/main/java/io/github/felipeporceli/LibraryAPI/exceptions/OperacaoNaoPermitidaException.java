package io.github.felipeporceli.LibraryAPI.exceptions;

// Classe para personalizarmos o lançamento de uma exceção quando a operação não for permitida
public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }
}
