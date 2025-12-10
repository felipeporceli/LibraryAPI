package io.github.felipeporceli.LibraryAPI.controllers.common;

import io.github.felipeporceli.LibraryAPI.controllers.dto.ErroCampo;
import io.github.felipeporceli.LibraryAPI.controllers.dto.ErroResposta;
import io.github.felipeporceli.LibraryAPI.exceptions.CampoInvalidoException;
import io.github.felipeporceli.LibraryAPI.exceptions.OperacaoNaoPermitidaException;
import io.github.felipeporceli.LibraryAPI.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;

// Anotation que captura erros de validação de maneira global na nossa aplicação
@RestControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)

/* Esse metodo captura automaticamente erros de validação (MethodArgumentNotValidException) gerados  quando os dados
   enviados para a API não atendem às regras definidas nos DTOs. Ele coleta todos os erros  de campos, converte cada
   um em um objeto simples contendo o nome do campo e a mensagem de erro, e então retorna uma resposta padronizada
   com status 422, indicando que os dados são inválidos. O objetivo é fornecer ao cliente uma resposta clara e
   organizada sempre que ocorrer um erro de validação. */

public class GlobalExceptionHandler {

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ErroResposta handleMethodArgumentNotValidExcpetion(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação!", listaErros);
    }


    // Exceção de registro duplicado
    @ExceptionHandler (RegistroDuplicadoException.class)
    @ResponseStatus (HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e) {
        return ErroResposta.respostaConflito(e.getMessage());
    }


    // Exceção de Operacao nao permitida
    @ExceptionHandler (OperacaoNaoPermitidaException.class)
    @ResponseStatus (HttpStatus.BAD_REQUEST)
    public ErroResposta handleOperacaoNaoPermitidaException (OperacaoNaoPermitidaException e) {
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus (HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException (CampoInvalidoException e) {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação",
                List.of(new ErroCampo(e.getCampo(), e.getMessage())));
    }


    // Exceção não tratada.
    @ExceptionHandler (RuntimeException.class)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTratados (RuntimeException e) {
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado, entre em contato com o suporte", List.of());
    }

}
