package io.github.felipeporceli.LibraryAPI.entities;

import io.github.felipeporceli.LibraryAPI.entities.enums.GeneroLivro;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "livro")
@Data
@EntityListeners(AuditingEntityListener.class)
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

    // Anotation que registra a data que a entidade foi criada.
    @CreatedDate
    @Column (name = "data_cadastro")
    private LocalDateTime dataCadastro;


    // Anotation que registra a última alteração feita na entidade.
    @LastModifiedDate
    @Column (name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column (name = "id_usuario")
    private UUID idUsuario;


}
