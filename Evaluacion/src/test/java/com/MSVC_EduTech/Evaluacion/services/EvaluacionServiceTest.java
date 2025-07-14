package com.MSVC_EduTech.Evaluacion.services;

import com.MSVC_EduTech.Evaluacion.clients.CursoClientRest;
import com.MSVC_EduTech.Evaluacion.clients.ProfesorClientRest;
import com.MSVC_EduTech.Evaluacion.exceptions.EvaluacionException;
import com.MSVC_EduTech.Evaluacion.models.Curso;
import com.MSVC_EduTech.Evaluacion.models.Profesor;
import com.MSVC_EduTech.Evaluacion.models.entities.Evaluacion;
import com.MSVC_EduTech.Evaluacion.repositories.EvaluacionRepository;
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
public class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private ProfesorClientRest  profesorClientRest;

    @Mock
    private CursoClientRest cursoClientRest;

    @InjectMocks
    EvaluacionServiceImpl evaluacionService;

    private List<Evaluacion> evaluacionList = new ArrayList<>();
    private Evaluacion evaluacionPrueba;
    private Profesor profesorPrueba;
    private Curso cursoPrueba;

    @BeforeEach
    public void setUp() {
        profesorPrueba = new Profesor();
        profesorPrueba.setIdProfesor(Long.valueOf(1L));
        profesorPrueba.setNombres("Juan");
        profesorPrueba.setApellidos("Lopez");
        profesorPrueba.setCorreo("jausn.dasd@gmail.com");
        profesorPrueba.setFechaContratacion(LocalDate.now());

        cursoPrueba = new Curso();
        cursoPrueba.setIdCurso(Long.valueOf(1L));
        cursoPrueba.setNombre("Matematica 2");
        cursoPrueba.setDuracion(Integer.valueOf(18));
        cursoPrueba.setEstado("Abierto");
        cursoPrueba.setSeccion("003-F");
        cursoPrueba.setFechaInicio(LocalDate.now());
        cursoPrueba.setFechaTermino(LocalDate.now().plusDays(30));

        evaluacionPrueba = new Evaluacion();
        evaluacionPrueba.setIdEvaluacion(Long.valueOf(1L));
        evaluacionPrueba.setTipo("Examen");
        evaluacionPrueba.setIdProfesor(Long.valueOf(1L));
        evaluacionPrueba.setIdCurso(Long.valueOf(1L));
        evaluacionPrueba.setNombre("Examen Final Matematica");
        evaluacionPrueba.setFechaRealizacion(LocalDate.now());

        Faker faker = new Faker(Locale.of("es", "CL"));
            for (int i = 0; i < 100; i++) {
                Evaluacion evaluacion = new Evaluacion();
                evaluacion.setTipo(faker.lorem().word());
                evaluacion.setNombre(faker.lorem().word());
                evaluacion.setIdCurso(Long.valueOf(1L));
                evaluacion.setIdProfesor(Long.valueOf(1L));
                evaluacion.setFechaRealizacion(LocalDate.now());

                this.evaluacionList.add(evaluacion);

            }
    }

    @Test
    @DisplayName("Devuelve todos los Evaluaciones")
    public void shouldFindAllEvaluacion() {
        this.evaluacionList.add(evaluacionPrueba);
        when(evaluacionRepository.findAll()).thenReturn(this.evaluacionList);

        List<Evaluacion> result = evaluacionService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.evaluacionPrueba);

        verify(evaluacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar una evaluación")
    public void shouldFindEvaluacionById() {
        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacionPrueba));

        Evaluacion result = evaluacionService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Examen Final Matematica");

        verify(evaluacionRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si la evaluación no existe")
    public void shouldThrowExceptionWhenEvaluacionNotFound() {
        when(evaluacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> evaluacionService.findById(99L))
                .isInstanceOf(EvaluacionException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(evaluacionRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar una evaluación")
    public void shouldSaveEvaluacionWhenProfesorAndCursoExist() {
        when(profesorClientRest.findById(1L)).thenReturn(profesorPrueba);
        when(cursoClientRest.findById(1L)).thenReturn(cursoPrueba);
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacionPrueba);

        Evaluacion result = evaluacionService.save(evaluacionPrueba);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Examen Final Matematica");

        verify(profesorClientRest, times(1)).findById(1L);
        verify(cursoClientRest, times(1)).findById(1L);
        verify(evaluacionRepository, times(1)).save(evaluacionPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si profesor o curso no existen al guardar evaluación")
    public void shouldThrowExceptionWhenProfesorOrCursoNotFound() {
        when(profesorClientRest.findById(1L)).thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> evaluacionService.save(evaluacionPrueba))
                .isInstanceOf(EvaluacionException.class)
                .hasMessageContaining("El profesor no existe o existen problemas");

        verify(profesorClientRest, times(1)).findById(1L);
        verify(cursoClientRest, never()).findById(any());
        verify(evaluacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar una evaluación ")
    public void shouldUpdateEvaluacionById() {
        Long id = 1L;
        Evaluacion evaluacionUpdate = new Evaluacion();
        evaluacionUpdate.setNombre("Prueba Parcial");
        evaluacionUpdate.setTipo("Control");
        evaluacionUpdate.setFechaRealizacion(LocalDate.now().plusDays(5));

        when(evaluacionRepository.findById(id)).thenReturn(Optional.of(evaluacionPrueba));
        when(evaluacionRepository.save(any(Evaluacion.class))).thenAnswer(inv -> inv.getArgument(0));

        Evaluacion result = evaluacionService.updateById(id, evaluacionUpdate);

        assertThat(result.getNombre()).isEqualTo("Prueba Parcial");
        assertThat(result.getTipo()).isEqualTo("Control");

        verify(evaluacionRepository, times(1)).findById(id);
        verify(evaluacionRepository, times(1)).save(evaluacionPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si la evaluación no existe al actualizar")
    public void shouldThrowExceptionWhenUpdatingNonExistentEvaluacion() {
        Long id = 999L;
        when(evaluacionRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> evaluacionService.updateById(id, evaluacionPrueba))
                .isInstanceOf(EvaluacionException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(evaluacionRepository, times(1)).findById(id);
        verify(evaluacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar una evaluación")
    public void shouldDeleteEvaluacionById() {
        Long id = 1L;

        evaluacionService.deleteById(id);

        verify(evaluacionRepository, times(1)).deleteById(id);
    }

}
