package io.github.felipeporceli.LibraryAPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table (name = "autor", schema = "public")
@Getter
@Setter
@ToString
//Anotation para poder utilizar as anotations @LastModifiedDate e @CreatedDate
@EntityListeners(AuditingEntityListener.class)
public class Autor {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column (name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column (name = "nacionalidade")
    private String nacionalidade;


    //Anotation que registra a data que a entidade foi criada.
    @CreatedDate
    @Column (name = "data_cadastro")
    private LocalDateTime dataCadastro;


    // Anotation que registra a última alteração feita na entidade.
    @LastModifiedDate
    @Column (name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column (name = "id_usuario")
    private UUID idUsuario;



    //@OneToMany(mappedBy = "id")
    @Transient
    private List<Livro> livros;
}
