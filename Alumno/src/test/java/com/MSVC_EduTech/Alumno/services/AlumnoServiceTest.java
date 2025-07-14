package com.MSVC_EduTech.Alumno.services;

import com.MSVC_EduTech.Alumno.exceptions.AlumnoException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
        alumnoPrueba = new Alumno();
        alumnoPrueba.setIdAlumno(Long.valueOf(1L));
        alumnoPrueba.setRunAlumno("12312434-6");
        alumnoPrueba.setNombres("Pedro");
        alumnoPrueba.setApellidos("Rojas");
        alumnoPrueba.setFechaRegistro(LocalDate.now());
        alumnoPrueba.setEstadoEstudiante("Activo");
        alumnoPrueba.setCorreo("peredor.sad@gmail.com");
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
    }

    @Test
    @DisplayName("Devuelve todos los alumnos")
    public void shouldFindAllAlumnos() {
        this.alumnoList.add(alumnoPrueba);
        when(alumnoRepository.findAll()).thenReturn(this.alumnoList);

        List<Alumno> result = alumnoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.alumnoPrueba);

        verify(alumnoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un alumno")
    public void shouldFindAlumnoById() {
        when(alumnoRepository.findById(Long.valueOf(1L))).thenReturn(Optional.of(this.alumnoPrueba));

        Alumno result = alumnoService.findById(Long.valueOf(1L));
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.alumnoPrueba);

        verify(alumnoRepository, times(1)).findById(Long.valueOf(1L));
    }

    @Test
    @DisplayName("Debe buscar un alumno que no existe")
    public void shouldNotFindAlumnoById() {
        Long idInexistente = (Long) 999L;
        when(alumnoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(()->{
            alumnoService.findById(idInexistente);
        }).isInstanceOf(AlumnoException.class)
                .hasMessageContaining("El alumno con id " + idInexistente + "no se encuentra en la base de datos");

        verify(alumnoRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo alumno")
    public void shouldSaveAlumno() {
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(this.alumnoPrueba);
        Alumno result = alumnoService.save(this.alumnoPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.alumnoPrueba);

        verify(alumnoRepository, times(1)).save(this.alumnoPrueba);
    }

    @Test
    @DisplayName("Debe eliminar un alumno")
    public void shouldDeleteAlumnoById() {
        Long id = 1L;

        alumnoService.deleteById(id);

        verify(alumnoRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Debe actualizar un alumno")
    public void shouldUpdateAlumnoById() {
        Long id = 1L;
        this.alumnoPrueba.setIdAlumno(id);

        Alumno alumnoUpdate = new Alumno();
        alumnoUpdate.setNombres("Marco");
        alumnoUpdate.setApellidos("Cordero");
        alumnoUpdate.setRunAlumno("12345678-9");
        alumnoUpdate.setCorreo("nuevo@correo.com");
        alumnoUpdate.setEstadoEstudiante("Activo");
        alumnoUpdate.setFechaRegistro(LocalDate.now());

        when(alumnoRepository.findById(id)).thenReturn(Optional.of(this.alumnoPrueba));
        when(alumnoRepository.save(any(Alumno.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Alumno result = alumnoService.updateById(id, alumnoUpdate);

        assertThat(result).isNotNull();
        assertThat(result.getNombres()).isEqualTo("Marco");
        assertThat(result.getCorreo()).isEqualTo("nuevo@correo.com");

        verify(alumnoRepository, times(1)).findById(id);
        verify(alumnoRepository, times(1)).save(this.alumnoPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si no se encuentra el alumno al actualizar")
    public void shouldThrowExceptionWhenAlumnoNotFound() {
        Long idInexistente = 99L;

        when(alumnoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alumnoService.updateById(idInexistente, this.alumnoPrueba))
                .isInstanceOf(AlumnoException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(alumnoRepository, times(1)).findById(idInexistente);
        verify(alumnoRepository, never()).save(any(Alumno.class));
    }

}
