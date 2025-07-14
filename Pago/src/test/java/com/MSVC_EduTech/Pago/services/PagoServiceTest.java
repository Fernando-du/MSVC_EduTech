package com.MSVC_EduTech.Pago.services;

import com.MSVC_EduTech.Pago.clients.AlumnoClientRest;
import com.MSVC_EduTech.Pago.clients.CursoClientRest;
import com.MSVC_EduTech.Pago.exceptions.PagoException;
import com.MSVC_EduTech.Pago.models.Alumno;
import com.MSVC_EduTech.Pago.models.Curso;
import com.MSVC_EduTech.Pago.models.entities.Pago;
import com.MSVC_EduTech.Pago.repositories.PagoRepository;
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
public class PagoServiceTest {

    @Mock
    private PagoRepository  pagoRepository;

    @Mock
    private AlumnoClientRest alumnoClientRest;

    @Mock
    private CursoClientRest cursoClientRest;

    @InjectMocks
    private PagoServiceImpl   pagoService;

    private List<Pago> pagoList = new ArrayList<>();
    private Pago pagoPrueba;
    private Curso cursoPrueba;
    private Alumno alumnoPrueba;

    @BeforeEach
    public void setUp() {
        cursoPrueba = new Curso();
        cursoPrueba.setIdCurso(Long.valueOf(1L));
        cursoPrueba.setNombre("Matematica 2");
        cursoPrueba.setDuracion(Integer.valueOf(18));
        cursoPrueba.setEstado("Abierto");
        cursoPrueba.setSeccion("003-F");
        cursoPrueba.setFechaInicio(LocalDate.now());
        cursoPrueba.setFechaTermino(LocalDate.now().plusDays(30));

        alumnoPrueba = new Alumno();
        alumnoPrueba.setIdAlumno(Long.valueOf(1L));
        alumnoPrueba.setRunAlumno("12312434-6");
        alumnoPrueba.setNombres("Pedro");
        alumnoPrueba.setApellidos("Rojas");
        alumnoPrueba.setFechaRegistro(LocalDate.now());
        alumnoPrueba.setEstadoEstudiante("Activo");
        alumnoPrueba.setCorreo("peredor.sad@gmail.com");

        pagoPrueba = new Pago();
        pagoPrueba.setIdPago(Long.valueOf(1L));
        pagoPrueba.setIdCurso(Long.valueOf(1L));
        pagoPrueba.setIdAlumno(Long.valueOf(1L));
        pagoPrueba.setValorPago(Integer.valueOf(18));
        pagoPrueba.setFechaPago(LocalDate.now().plusDays(60));

        Faker faker = new Faker(Locale.of("es","CL"));
        for(int i=0;i<100;i++){
            Pago pago = new Pago();
            pago.setFechaPago(LocalDate.now());
            pago.setValorPago(faker.number().numberBetween(0,100));
            pago.setIdAlumno(Long.valueOf(1L));
            pago.setIdCurso(Long.valueOf(1L));

            this.pagoList.add(pago);
        }
    }

    @Test
    @DisplayName("Devuelve todos los Pagos")
    public void shouldFindAllPago() {
        this.pagoList.add(pagoPrueba);
        when(pagoRepository.findAll()).thenReturn(this.pagoList);

        List<Pago> result = pagoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.pagoPrueba);

        verify(pagoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar un pago")
    public void shouldFindPagoById() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoPrueba));

        Pago result = pagoService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getValorPago()).isEqualTo(18);

        verify(pagoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el pago no existe")
    public void shouldThrowExceptionWhenPagoNotFound() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pagoService.findById(99L))
                .isInstanceOf(PagoException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(pagoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe guardar un pago")
    public void shouldSavePagoWhenAlumnoAndCursoExist() {
        when(alumnoClientRest.findById(1L)).thenReturn(alumnoPrueba);
        when(cursoClientRest.findById(1L)).thenReturn(cursoPrueba);
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoPrueba);

        Pago result = pagoService.save(pagoPrueba);

        assertThat(result).isNotNull();
        assertThat(result.getValorPago()).isEqualTo(18);

        verify(alumnoClientRest, times(1)).findById(1L);
        verify(cursoClientRest, times(1)).findById(1L);
        verify(pagoRepository, times(1)).save(pagoPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si alumno o curso no existen al guardar pago")
    public void shouldThrowExceptionWhenAlumnoOrCursoNotFound() {
        when(alumnoClientRest.findById(1L)).thenThrow(FeignException.NotFound.class);

        assertThatThrownBy(() -> pagoService.save(pagoPrueba))
                .isInstanceOf(PagoException.class)
                .hasMessageContaining("El Alumno no existe o existen problemas");

        verify(alumnoClientRest, times(1)).findById(1L);
        verify(cursoClientRest, never()).findById(any());
        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar un pago")
    public void shouldUpdatePagoById() {
        Long id = 1L;
        Pago pagoUpdate = new Pago();
        pagoUpdate.setValorPago(25000);
        pagoUpdate.setFechaPago(LocalDate.now().plusDays(3));
        pagoUpdate.setIdAlumno(1L);
        pagoUpdate.setIdCurso(1L);

        when(pagoRepository.findById(id)).thenReturn(Optional.of(pagoPrueba));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(inv -> inv.getArgument(0));

        Pago result = pagoService.updateById(id, pagoUpdate);

        assertThat(result.getValorPago()).isEqualTo(25000);
        assertThat(result.getFechaPago()).isEqualTo(pagoUpdate.getFechaPago());

        verify(pagoRepository, times(1)).findById(id);
        verify(pagoRepository, times(1)).save(pagoPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción si el pago no existe al actualizar")
    public void shouldThrowExceptionWhenUpdatingNonExistentPago() {
        Long id = 999L;
        when(pagoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pagoService.updateById(id, pagoPrueba))
                .isInstanceOf(PagoException.class)
                .hasMessageContaining("no se encuentra en la base de datos");

        verify(pagoRepository, times(1)).findById(id);
        verify(pagoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe eliminar un pago")
    public void shouldDeletePagoById() {
        Long id = 1L;

        pagoService.deleteById(id);

        verify(pagoRepository, times(1)).deleteById(id);
    }

}
