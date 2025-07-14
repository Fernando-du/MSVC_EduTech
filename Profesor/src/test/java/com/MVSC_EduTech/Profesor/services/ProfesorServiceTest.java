package com.MVSC_EduTech.Profesor.services;

import com.MVSC_EduTech.Profesor.exceptions.ProfesorException;
import com.MVSC_EduTech.Profesor.models.Profesor;
import com.MVSC_EduTech.Profesor.repositories.ProfesorRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorServiceImpl profesorService;

    private List<Profesor> profesorList = new ArrayList<>();

    private Profesor profesorPrueba;

    @BeforeEach
    public void setup() {
        profesorPrueba = new Profesor();
        profesorPrueba.setRunProfesor("11342765-9");
        profesorPrueba.setApellidos("Marquez");
        profesorPrueba.setIdProfesor(Long.valueOf(1L));
        profesorPrueba.setNombres("Diego");
        profesorPrueba.setCorreo("diegosa.sad@gmail.com");
        profesorPrueba.setFechaContratacion(LocalDate.now());

        Faker faker = new Faker(Locale.of("es", "CL"));
        if (profesorRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                Profesor profesor = new Profesor();

                String numeroString = faker.idNumber().valid().replaceAll("-", "");
                String ultimo = numeroString.substring(numeroString.length() - 1);
                String restante = numeroString.substring(0, numeroString.length() - 1);

                profesor.setRunProfesor(restante + "-" + ultimo);
                profesor.setNombres(faker.name().firstName());
                profesor.setApellidos(faker.name().lastName());
                profesor.setCorreo(faker.internet().emailAddress());
                profesor.setFechaContratacion(LocalDate.now());

                this.profesorList.add(profesor);

            }
        }

    }

    @Test
    @DisplayName("Devuelve todos los profesores")
    public void shouldFindAllProfesores() {
        this.profesorList.add(profesorPrueba);
        when(profesorRepository.findAll()).thenReturn(this.profesorList);

        List<Profesor> result = profesorService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.profesorPrueba);

        verify(profesorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar un profesor")
    public void shouldFindProfesorById() {
        when(profesorRepository.findById(Long.valueOf(1L))).thenReturn(Optional.of(this.profesorPrueba));

        Profesor result = profesorService.findById(Long.valueOf(1L));
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.profesorPrueba);

        verify(profesorRepository, times(1)).findById(Long.valueOf(1L));
    }

    @Test
    @DisplayName("Debe buscar un profesor que no existe")
    public void shouldNotFindProfesorById() {
        Long idInexistente = (Long) 999L;
        when(profesorRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            profesorService.findById(idInexistente);
        }).isInstanceOf(ProfesorException.class)
                .hasMessageContaining("El profesor con id " + idInexistente + "no se encuentra en la base de datos");

        verify(profesorRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo profesor")
    public void shouldSaveProfesor() {
        when(profesorRepository.save(any(Profesor.class))).thenReturn(this.profesorPrueba);
        Profesor result = profesorService.save(this.profesorPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.profesorPrueba);

        verify(profesorRepository, times(1)).save(this.profesorPrueba);
    }

    @Test
    @DisplayName("Debe eliminar un profesor")
    public void shouldDeleteProfesorById() {
        Long id = 1L;

        profesorService.deleteById(id);

        verify(profesorRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Debe actualizar un Profesor")
    public void shouldUpdateProfesorById() {
        Long id = 1L;
        this.profesorPrueba.setIdProfesor(id);

        Profesor profesorUpdate = new Profesor();
        profesorUpdate.setNombres("Marco");
        profesorUpdate.setApellidos("Cordero");
        profesorUpdate.setRunProfesor("12345678-9");
        profesorUpdate.setCorreo("nuevo@correo.com");
        profesorUpdate.setFechaContratacion(LocalDate.now());

        when(profesorRepository.findById(id)).thenReturn(Optional.of(this.profesorPrueba));
        when(profesorRepository.save(any(Profesor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Profesor result = profesorService.updateById(id, profesorUpdate);

        assertThat(result).isNotNull();
        assertThat(result.getNombres()).isEqualTo("Marco");
        assertThat(result.getCorreo()).isEqualTo("nuevo@correo.com");

        verify(profesorRepository, times(1)).findById(id);
        verify(profesorRepository, times(1)).save(this.profesorPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si no se encuentra el profesor al actualizar")
    public void shouldThrowExceptionWhenProfesorNotFound() {
        Long idInexistente = 99L;

        when(profesorRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> profesorService.updateById(idInexistente, this.profesorPrueba))
                .isInstanceOf(ProfesorException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(profesorRepository, times(1)).findById(idInexistente);
        verify(profesorRepository, never()).save(any(Profesor.class));
    }

}
