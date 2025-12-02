package io.github.felipeporceli.LibraryAPI.validator;

import io.github.felipeporceli.LibraryAPI.entities.Autor;
import io.github.felipeporceli.LibraryAPI.exceptions.RegistroDuplicadoException;
import io.github.felipeporceli.LibraryAPI.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private final AutorRepository repository;

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado!");
        }

    }


    // Metodo para verificar se o autor já existe no banco. Se sim = True, se não = False.
    private boolean existeAutorCadastrado(Autor autor) {

        // Realizando busca de autor e instanciando objeto do tipo Autor com os parâmetros enviados.
        Optional<Autor> autorEcontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        // Verificando se o ID é nulo, se nulo, é porque não há um autor cadastrado.
        if (autor.getId() == null) {
            return autorEcontrado.isPresent();
        }

        // Verificando se esse mesmo autor encontrado no banco não é o mesmo que eu estou tentando atualizar,
        return !autor.getId().equals(autorEcontrado.get().getId()) && autorEcontrado.isPresent();
    }

    }
