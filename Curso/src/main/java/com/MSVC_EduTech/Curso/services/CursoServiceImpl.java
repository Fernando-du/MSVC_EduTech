package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.clients.ProfesorClientRest;
import com.MSVC_EduTech.Curso.dtos.CursoDTO;
import com.MSVC_EduTech.Curso.dtos.ProfesorDTO;
import com.MSVC_EduTech.Curso.exceptions.CursoException;
import com.MSVC_EduTech.Curso.models.Profesor;
import com.MSVC_EduTech.Curso.models.entities.Curso;
import com.MSVC_EduTech.Curso.repositories.CursoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorClientRest profesorClientRest;

    @Override
    public List<CursoDTO> findAll() {
        return cursoRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public CursoDTO findById(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new CursoException("El curso con id " + id + " no se encuentra en la base de datos"));
        return mapToDTO(curso);
    }

    @Override
    public CursoDTO save(CursoDTO dto) {
        if (dto.getIdProfesor() == null) {
            throw new IllegalArgumentException("El curso debe tener un profesor asignado");
        }

        validarProfesorExistente(dto.getIdProfesor());

        Curso curso = mapToEntity(dto);
        Curso cursoGuardado = cursoRepository.save(curso);
        return mapToDTO(cursoGuardado);
    }

    @Override
    public CursoDTO updateById(Long id, CursoDTO dto) {
        return cursoRepository.findById(id).map(curso -> {

            curso.setNombre(dto.getNombre());
            curso.setSeccion(dto.getSeccion());
            curso.setDuracion(dto.getDuracion());
            curso.setEstado(dto.getEstado());
            curso.setFechaInicio(dto.getFechaInicio());
            curso.setFechaTermino(dto.getFechaTermino());

            if (dto.getIdProfesor() != null) {
                validarProfesorExistente(dto.getIdProfesor());
                curso.setIdProfesor(dto.getIdProfesor());
            } else {
                curso.setIdProfesor(null);
            }

            Curso actualizado = cursoRepository.save(curso);
            return mapToDTO(actualizado);

        }).orElseThrow(() -> new CursoException("El curso con id " + id + " no se encuentra en la base de datos"));
    }

    @Override
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }

    // Validación con cliente Feign
    private void validarProfesorExistente(Long idProfesor) {
        try {
            profesorClientRest.findById(idProfesor);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("El profesor con ID " + idProfesor + " no existe");
        }
    }

    // Mapper privado para convertir entidad a DTO
    private CursoDTO mapToDTO(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(curso.getIdCurso());
        dto.setNombre(curso.getNombre());
        dto.setSeccion(curso.getSeccion());
        dto.setFechaInicio(curso.getFechaInicio());
        dto.setFechaTermino(curso.getFechaTermino());
        dto.setEstado(curso.getEstado());
        dto.setDuracion(curso.getDuracion());
        dto.setIdProfesor(curso.getIdProfesor());
        return dto;
    }

    // Mapper privado para convertir DTO a entidad
    private Curso mapToEntity(CursoDTO dto) {
        Curso curso = new Curso();
        curso.setIdCurso(dto.getIdCurso());
        curso.setNombre(dto.getNombre());
        curso.setSeccion(dto.getSeccion());
        curso.setFechaInicio(dto.getFechaInicio());
        curso.setFechaTermino(dto.getFechaTermino());
        curso.setEstado(dto.getEstado());
        curso.setDuracion(dto.getDuracion());
        curso.setIdProfesor(dto.getIdProfesor());
        return curso;
    }
}
