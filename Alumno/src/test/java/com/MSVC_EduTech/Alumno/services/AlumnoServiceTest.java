package com.MSVC_EduTech.Alumno.services;

import com.MSVC_EduTech.Alumno.models.Alumno;
import com.MSVC_EduTech.Alumno.repositories.AlumnoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceTest {

    @Mock
    private AlumnoRepository  alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    private List<Alumno> alumnoList = new ArrayList<>();

    private Alumno alumnoPrueba;

    @BeforeEach
    public void setup(){
        Faker faker = new Faker(Locale.of("es","CL"));
        for(int i=0;i<100;i++){
            Alumno alumno = new Alumno();

            String numeroString = faker.idNumber().valid().replaceAll("-","");
            String ultimo = numeroString.substring(numeroString.length()-1);
            String restante = numeroString.substring(0,numeroString.length()-1);

            alumno.setRunAlumno(restante+"-"+ultimo);

            alumno.setNombres(faker.name().firstName());
            alumno.setApellidos(faker.name().lastName());
            alumno.setCorreo(faker.internet().emailAddress());
            alumno.setEstadoEstudiante("Activo");
            alumno.setFechaRegistro(LocalDate.now());

            this.alumnoList.add(alumno);

        }
        this.alumnoPrueba = this.alumnoList.get(0);
    }

    @Test
    @DisplayName("Devuelve todos los alumnos")
    public void shouldFindAllAlumnos() {
        when(alumnoRepository.findAll()).thenReturn(this.alumnoList);
        List<Alumno> result = alumnoService.findAll();
        assertThat(result).hasSize(100);
        assertThat(result).contains(this.alumnoPrueba);

        verify(alumnoRepository, times(1)).findAll();
    }
}
