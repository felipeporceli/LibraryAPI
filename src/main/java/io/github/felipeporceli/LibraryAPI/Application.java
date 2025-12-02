package io.github.felipeporceli.LibraryAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// Habilitando o módulo de auditoria do JPA para utilizarmos as anotations @LastModifiedDate e @CreatedDate
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);


	}

}


