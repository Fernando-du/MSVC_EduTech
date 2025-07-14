package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.clients.ProfesorClientRest;
import com.MSVC_EduTech.Curso.exceptions.CursoException;
import com.MSVC_EduTech.Curso.models.Profesor;
import com.MSVC_EduTech.Curso.models.entities.Curso;
import com.MSVC_EduTech.Curso.repositories.CursoRepository;
import feign.FeignException;
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
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private ProfesorClientRest  profesorClientRest;

    @InjectMocks
    private CursoServiceImpl cursoService;

    private List<Curso> cursoList = new ArrayList<>();
    private Profesor profesorPrueba;
    private Curso cursoPrueba;

    @BeforeEach
    public void setUp(){
        profesorPrueba = new Profesor();
        profesorPrueba.setIdProfesor(Long.valueOf(1L));
        profesorPrueba.setNombres("Juan");
        profesorPrueba.setApellidos("Lopez");
        profesorPrueba.setCorreo("jausn.dasd@gmail.com");
        profesorPrueba.setFechaContratacion(LocalDate.now());

        cursoPrueba = new Curso();
        cursoPrueba.setIdCurso(Long.valueOf(1L));
        cursoPrueba.setIdProfesor(Long.valueOf(1L));
        cursoPrueba.setNombre("Matematica 2");
        cursoPrueba.setDuracion(Integer.valueOf(18));
        cursoPrueba.setEstado("Abierto");
        cursoPrueba.setSeccion("003-F");
        cursoPrueba.setFechaInicio(LocalDate.now());
        cursoPrueba.setFechaTermino(LocalDate.now().plusDays(30));
        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            Curso curso = new Curso();
            curso.setDuracion(faker.number().numberBetween(1, 1000));
            curso.setEstado("Abierto");
            curso.setNombre(faker.name().fullName());
            curso.setSeccion(faker.lorem().sentence());
            curso.setFechaInicio(LocalDate.now());
            curso.setFechaTermino(LocalDate.now().plusDays(30));
            curso.setIdProfesor(Long.valueOf(1L));

            this.cursoList.add(curso);
        }

    }

    @Test
    @DisplayName("Devuelve todos los cursos")
    public void shouldFindAllCursos() {
        this.cursoList.add(cursoPrueba);
        when(cursoRepository.findAll()).thenReturn(this.cursoList);

        List<Curso> result = cursoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.cursoPrueba);

        verify(cursoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar un curso")
    public void shouldFindCursoById() {
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(this.cursoPrueba));

        Curso result = cursoService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Matematica 2");
        verify(cursoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si curso no existe")
    public void shouldThrowExceptionWhenCursoNotFound() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursoService.findById(99L))
                .isInstanceOf(CursoException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(cursoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un curso")
    public void shouldSaveCursoWhenProfesorExists() {
        when(profesorClientRest.findById(1L)).thenReturn(profesorPrueba);
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoPrueba);

        Curso result = cursoService.save(cursoPrueba);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Matematica 2");

        verify(profesorClientRest, times(1)).findById(1L);
        verify(cursoRepository, times(1)).save(cursoPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si profesor no existe al guardar curso")
    public void shouldThrowExceptionWhenProfesorDoesNotExist() {
        when(profesorClientRest.findById(1L)).thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> cursoService.save(cursoPrueba))
                .isInstanceOf(CursoException.class)
                .hasMessageContaining("El profesor no existe o existen problemas");

        verify(profesorClientRest, times(1)).findById(1L);
        verify(cursoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un curso")
    public void shouldUpdateCursoById() {
        Long id = 1L;
        Curso cursoUpdate = new Curso();
        cursoUpdate.setNombre("Física");
        cursoUpdate.setSeccion("001-A");
        cursoUpdate.setDuracion(30);
        cursoUpdate.setFechaInicio(LocalDate.now());
        cursoUpdate.setFechaTermino(LocalDate.now().plusDays(40));
        cursoUpdate.setEstado("Cerrado");

        when(cursoRepository.findById(id)).thenReturn(Optional.of(cursoPrueba));
        when(cursoRepository.save(any(Curso.class))).thenAnswer(inv -> inv.getArgument(0));

        Curso result = cursoService.updateById(id, cursoUpdate);

        assertThat(result.getNombre()).isEqualTo("Física");
        assertThat(result.getSeccion()).isEqualTo("001-A");

        verify(cursoRepository, times(1)).findById(id);
        verify(cursoRepository, times(1)).save(cursoPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el curso no existe al actualizar")
    public void shouldThrowExceptionWhenUpdatingNonExistentCurso() {
        Long id = 999L;
        when(cursoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cursoService.updateById(id, cursoPrueba))
                .isInstanceOf(CursoException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(cursoRepository, times(1)).findById(id);
        verify(cursoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un curso")
    public void shouldDeleteCursoById() {
        Long id = 1L;

        cursoService.deleteById(id);

        verify(cursoRepository, times(1)).deleteById(id);
    }


}
