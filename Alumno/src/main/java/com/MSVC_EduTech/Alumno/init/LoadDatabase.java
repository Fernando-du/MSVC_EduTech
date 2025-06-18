package com.MSVC_EduTech.Alumno.init;

import com.MSVC_EduTech.Alumno.models.Alumno;
import com.MSVC_EduTech.Alumno.repositories.AlumnoRepository;
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

public class LoadDatabase implements CommandLineRunner {

    @Autowired
    private AlumnoRepository repositories;

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if(repositories.count()==0){
            for(int i=0;i<1000;i++){
                Alumno alumno = new Alumno();

                String numeroString = faker.idNumber().valid().replaceAll("-","");
                String ultimo = numeroString.substring(numeroString.length()-1);
                String restante = numeroString.substring(0,numeroString.length()-1);

                alumno.setRunAlumno(restante+"-"+ultimo);

                alumno.setNombres(faker.name().firstName());
                alumno.setApellidos(faker.name().lastName());
                alumno.setCorreo(faker.internet().emailAddress());
                alumno.setEstadoEstudiante("");
                alumno.setFechaRegistro(LocalDate.now());
                logger.info("El rut que agregaste es{}:", alumno.getRunAlumno());

                alumno = repositories.save(alumno);

                logger.info("El alumno es: {}", alumno);
            }
        }

    }
}
