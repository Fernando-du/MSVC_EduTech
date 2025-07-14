package com.MSVC_EduTech.Evaluacion.init;

import com.MSVC_EduTech.Evaluacion.models.entities.Evaluacion;
import com.MSVC_EduTech.Evaluacion.repositories.EvaluacionRepository;
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
    private EvaluacionRepository EvaluacionRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));

        if(EvaluacionRepository.count()==0){
            for(int i=0;i<1000;i++){
                Evaluacion evaluacion = new Evaluacion();
                evaluacion.setTipo(faker.lorem().word());
                evaluacion.setNombre(faker.lorem().word());
                evaluacion.setIdCurso(Long.valueOf(1L);
                evaluacion.setIdProfesor(Long.valueOf(1L));
                evaluacion.setFechaRealizacion();



            }
        }
    }

}