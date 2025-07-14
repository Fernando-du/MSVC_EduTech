package com.MSVC_EduTech.Nota.services;

import com.MSVC_EduTech.Nota.clients.AlumnoClientRest;
import com.MSVC_EduTech.Nota.clients.EvaluacionClientRest;
import com.MSVC_EduTech.Nota.exceptions.NotaException;
import com.MSVC_EduTech.Nota.models.Alumno;
import com.MSVC_EduTech.Nota.models.Evaluacion;
import com.MSVC_EduTech.Nota.models.entities.Nota;
import com.MSVC_EduTech.Nota.repositories.NotaRepository;
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
public class NotaServiceTest {

    @Mock
    private NotaRepository notaRepository;

    @Mock
    private EvaluacionClientRest evaluacionClientRest;

    @Mock
    private AlumnoClientRest  alumnoClientRest;

    @InjectMocks
    private NotaServiceImpl notaService;

    private List<Nota> notaList = new ArrayList<>();
    private Evaluacion evaluacionPrueba;
    private Alumno alumnoPrueba;
    private Nota notaPrueba;

    @BeforeEach
    public void setUp() {
        evaluacionPrueba = new Evaluacion();
        evaluacionPrueba.setIdEvaluacion(Long.valueOf(1L));
        evaluacionPrueba.setTipo("Examen");
        evaluacionPrueba.setIdProfesor(Long.valueOf(1L));
        evaluacionPrueba.setIdCurso(Long.valueOf(1L));
        evaluacionPrueba.setNombre("Examen Final Matematica");
        evaluacionPrueba.setFechaRealizacion(LocalDate.now());

        alumnoPrueba = new Alumno();
        alumnoPrueba.setIdAlumno(Long.valueOf(1L));
        alumnoPrueba.setRunAlumno("12312434-6");
        alumnoPrueba.setNombres("Pedro");
        alumnoPrueba.setApellidos("Rojas");
        alumnoPrueba.setFechaRegistro(LocalDate.now());
        alumnoPrueba.setEstadoEstudiante("Activo");
        alumnoPrueba.setCorreo("peredor.sad@gmail.com");

        notaPrueba = new Nota();
        notaPrueba.setIdNota(Long.valueOf(1L));
        notaPrueba.setIdEvaluacion(Long.valueOf(1L));
        notaPrueba.setIdAlumno(Long.valueOf(1L));
        notaPrueba.setValorNota(4.5);

        Faker faker = new Faker(Locale.of("es","CL"));
        for(int i=0;i<100;i++){
            Nota nota = new Nota();
            nota.setIdAlumno(Long.valueOf(1L));
            nota.setIdEvaluacion(Long.valueOf(1L));
            nota.setValorNota(Double.valueOf(faker.number().numberBetween(0,100)));

            this.notaList.add(nota);
        }

    }

    @Test
    @DisplayName("Devuelve todos los Notas")
    public void shouldFindAllNota() {
        this.notaList.add(notaPrueba);
        when(notaRepository.findAll()).thenReturn(this.notaList);

        List<Nota> result = notaService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.notaPrueba);

        verify(notaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar una nota")
    public void shouldFindNotaById() {
        when(notaRepository.findById(1L)).thenReturn(Optional.of(notaPrueba));

        Nota result = notaService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getValorNota()).isEqualTo(4.5);

        verify(notaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si la nota no existe")
    public void shouldThrowExceptionWhenNotaNotFound() {
        when(notaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notaService.findById(999L))
                .isInstanceOf(NotaException.class)
                .hasMessageContaining("no existe");

        verify(notaRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Debe guardar una nota")
    public void shouldSaveNotaWhenAlumnoAndEvaluacionExist() {
        when(alumnoClientRest.findById(1L)).thenReturn(alumnoPrueba);
        when(evaluacionClientRest.findById(1L)).thenReturn(evaluacionPrueba);
        when(notaRepository.save(any(Nota.class))).thenReturn(notaPrueba);

        Nota result = notaService.save(notaPrueba);

        assertThat(result).isNotNull();
        assertThat(result.getValorNota()).isEqualTo(4.5);

        verify(alumnoClientRest, times(1)).findById(1L);
        verify(evaluacionClientRest, times(1)).findById(1L);
        verify(notaRepository, times(1)).save(notaPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si alumno o evaluación no existen al guardar nota")
    public void shouldThrowExceptionWhenAlumnoOrEvaluacionNotFound() {
        when(alumnoClientRest.findById(1L)).thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> notaService.save(notaPrueba))
                .isInstanceOf(NotaException.class)
                .hasMessageContaining("El Alumno no existe");

        verify(alumnoClientRest, times(1)).findById(1L);
        verify(evaluacionClientRest, never()).findById(any());
        verify(notaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar una nota")
    public void shouldUpdateNotaById() {
        Long id = 1L;
        Nota notaUpdate = new Nota();
        notaUpdate.setValorNota(6.8);
        notaUpdate.setIdAlumno(1L);
        notaUpdate.setIdEvaluacion(1L);

        when(notaRepository.findById(id)).thenReturn(Optional.of(notaPrueba));
        when(notaRepository.save(any(Nota.class))).thenAnswer(inv -> inv.getArgument(0));

        Nota result = notaService.updateById(id, notaUpdate);

        assertThat(result.getValorNota()).isEqualTo(6.8);

        verify(notaRepository, times(1)).findById(id);
        verify(notaRepository, times(1)).save(notaPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si la nota no existe al actualizar")
    public void shouldThrowExceptionWhenUpdatingNonExistentNota() {
        Long id = 999L;
        when(notaRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notaService.updateById(id, notaPrueba))
                .isInstanceOf(NotaException.class)
                .hasMessageContaining("no existe");

        verify(notaRepository, times(1)).findById(id);
        verify(notaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar una nota")
    public void shouldDeleteNotaById() {
        Long id = 1L;

        notaService.deleteById(id);

        verify(notaRepository, times(1)).deleteById(id);
    }

}
