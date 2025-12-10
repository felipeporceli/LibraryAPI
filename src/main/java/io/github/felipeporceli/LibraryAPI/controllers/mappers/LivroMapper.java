package io.github.felipeporceli.LibraryAPI.controllers.mappers;

import io.github.felipeporceli.LibraryAPI.controllers.dto.CadastroLivroDTO;
import io.github.felipeporceli.LibraryAPI.controllers.dto.ResultadoPesquisaLivroDTO;
import io.github.felipeporceli.LibraryAPI.entities.Livro;
import io.github.felipeporceli.LibraryAPI.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

// Classe para mapeamento de um CadastroLivroDTO para um Livro pronto pra ser salvo no banco de dados. No caso estamos
// mapeando os parâmetros do autor do nosso Livro que está sendo cadastrado.


// Diz ao MapStruct para gerar o Mapper como um Bean do Spring.
@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    // Preencher o atributo "autor" (no caso, a entidade autor) na entidade Livro. Como não é apenas copiar
    // um campo (exige busca no banco), está sendo usado o parâmetro "expression" com código Java manual.
    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
