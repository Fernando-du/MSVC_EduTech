package com.MSVC_EduTech.Curso.init;

import com.MSVC_EduTech.Curso.models.entities.Curso;
import com.MSVC_EduTech.Curso.repositories.CursoRepository;
import com.MSVC_EduTech.Curso.repositories.CursoRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Profile("dev")
@Component
public abstract class LoadDatabase implements CommandLineRunner {

    @Autowired
    private CursoRepository cursoRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es", "CL"));
        if (cursoRepository.count() == 0) {
            for (int i = 0; i < 1000; i++) {
                Curso curso = new Curso();
                curso.setDuracion(faker.number().numberBetween(1, 1000));
                curso.setEstado("Abierto");
                curso.setNombre(faker.name().fullName());
                curso.setSeccion(faker.lorem().sentence());
                curso.setFechaInicio(LocalDate.now());
                curso.setFechaTermino(LocalDate.now().plusDays(30));
                curso.setIdProfesor(Long.valueOf(1L));

                curso =  cursoRepository.save(curso);
            }
        }
    }
}