package com.MSVC_EduTech.Nota.init;

import com.MSVC_EduTech.Nota.models.entities.Nota;
import com.MSVC_EduTech.Nota.repositories.NotaRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("dev")
@Component
public abstract class LoadDatabase implements CommandLineRunner {

    @Autowired
    private NotaRepository notaRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));

        if(notaRepository.count()==0){
            for(int i=0;i<1000;i++){
                Nota nota = new Nota();
                nota.setIdAlumno(Long.valueOf(1L));
                nota.setIdEvaluacion(Long.valueOf(1L));
                nota.setValorNota(Double.valueOf(faker.number().numberBetween(0,100)));
                logger.info("la nota que gregas es {}", nota.getValorNota());
                nota = notaRepository.save(nota);
                logger.info("La nota creada es: {}", nota);


            }
        }
    }

}
