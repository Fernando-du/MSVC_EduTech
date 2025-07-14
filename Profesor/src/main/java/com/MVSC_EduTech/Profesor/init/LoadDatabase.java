package com.MVSC_EduTech.Profesor.init;


import com.MVSC_EduTech.Profesor.models.Profesor;
import com.MVSC_EduTech.Profesor.repositories.ProfesorRepository;
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
    private ProfesorRepository profesorRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));

        if(profesorRepository.count()==0){
            for(int i=0;i<1000;i++){
                Profesor profesor = new Profesor();

                String numeroString = faker.idNumber().valid().replaceAll("-","");
                String ultimo = numeroString.substring(numeroString.length()-1);
                String restante = numeroString.substring(0,numeroString.length()-1);

                profesor.setRunProfesor(restante+"-"+ultimo);
                logger.info("El rut que agregas es {}", profesor.getRunProfesor());
                profesor.setNombres(faker.name().firstName());
                profesor.setApellidos(faker.name().lastName());
                profesor.setCorreo(faker.internet().emailAddress());
                profesor.setFechaContratacion(LocalDate.now());

                profesor = profesorRepository.save(profesor);

                logger.info("El profesor es: {}", profesor);


            }
        }
    }

}