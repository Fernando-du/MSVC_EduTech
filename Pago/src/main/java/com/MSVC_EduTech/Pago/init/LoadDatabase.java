package com.MSVC_EduTech.Pago.init;


import com.MSVC_EduTech.Pago.models.entities.Pago;
import com.MSVC_EduTech.Pago.repositories.PagoRepository;
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
    private PagoRepository pagoRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDatabase.class);
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));

        if(pagoRepository.count()==0){
            for(int i=0;i<1000;i++){
                Pago pago = new Pago();
                pago.setFechaPago(LocalDate.now());
                pago.setValorPago(faker.number().numberBetween(0,100));
                pago.setIdAlumno(Long.valueOf(1L));
                pago.setIdCurso(Long.valueOf(1L));

                pago = pagoRepository.save(pago);
                logger.info("El pago es: {}", pago);
            }
        }
    }

}
