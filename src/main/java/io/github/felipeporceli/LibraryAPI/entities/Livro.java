package io.github.felipeporceli.LibraryAPI.entities;

import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table (name = "livro")
@Data
public class Livro {
   @Id
   @Column (name = "id")
   @GeneratedValue (strategy = GenerationType.UUID)
   private UUID id;

   @Column (name = "isbn", length = 20, nullable = false)
   private String isbn;

    @Column (name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Enumerated (EnumType.STRING)
    @Column (name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column (name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Column (name = "preco",nullable = false)
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;




}
