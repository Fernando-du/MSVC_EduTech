package com.MSVC_EduTech.Curso.services;

import com.MSVC_EduTech.Curso.dtos.CursoDTO;
import com.MSVC_EduTech.Curso.exceptions.CursoException;
import com.MSVC_EduTech.Curso.models.Profesor;
import com.MSVC_EduTech.Curso.models.entities.Curso;
import com.MSVC_EduTech.Curso.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService {

        @Autowired
        private CursoRepository cursoRepository;

        private CursoDTO mapToDTO(Curso curso) {
            return new CursoDTO(
                    curso.getIdCurso(),
                    curso.getNombre(),
                    curso.getSeccion(),
                    curso.getFechaInicio(),
                    curso.getFechaTermino(),
                    curso.getEstado(),
                    curso.getDuracion(),
                    curso.getProfesor() != null ? curso.getProfesor().getIdProfesor() : null
            );
        }

        private Curso mapToEntity(CursoDTO dto) {
            Curso curso = new Curso();
            curso.setIdCurso(dto.getIdCurso());
            curso.setNombre(dto.getNombre());
            curso.setSeccion(dto.getSeccion());
            curso.setFechaInicio(dto.getFechaInicio());
            curso.setFechaTermino(dto.getFechaTermino());
            curso.setEstado(dto.getEstado());
            curso.setDuracion(dto.getDuracion());

            if(dto.getIdProfesor() != null) {
                Profesor profesor = new Profesor();
                profesor.setIdProfesor(dto.getIdProfesor());
                curso.setProfesor(profesor);
            }
            return curso;
        }

        @Override
        public List<CursoDTO> findAll() {
            return cursoRepository.findAll().stream()
                    .map(this::mapToDTO)
                    .toList();
        }

        @Override
        public CursoDTO findById(Long id) {
            Curso curso = cursoRepository.findById(id).orElseThrow(
                    () -> new CursoException("El curso con id " + id + " no se encuentra en la base de datos")
            );
            return mapToDTO(curso);
        }

        @Override
        public CursoDTO save(CursoDTO cursoDTO) {
            Curso curso = mapToEntity(cursoDTO);
            Curso cursoGuardado = cursoRepository.save(curso);
            return mapToDTO(cursoGuardado);
        }

        @Override
        public void deleteById(Long id) {
            cursoRepository.deleteById(id);
        }

        @Override
        public CursoDTO updateById(Long id, CursoDTO cursoDTO) {
            return cursoRepository.findById(id).map(curso -> {
                curso.setNombre(cursoDTO.getNombre());
                curso.setEstado(cursoDTO.getEstado());
                curso.setSeccion(cursoDTO.getSeccion());
                curso.setDuracion(cursoDTO.getDuracion());
                curso.setFechaInicio(cursoDTO.getFechaInicio());
                curso.setFechaTermino(cursoDTO.getFechaTermino());

                if(cursoDTO.getIdProfesor() != null) {
                    Profesor profesor = new Profesor();
                    profesor.setIdProfesor(cursoDTO.getIdProfesor());
                    curso.setProfesor(profesor);
                }

                Curso cursoActualizado = cursoRepository.save(curso);
                return mapToDTO(cursoActualizado);
            }).orElseThrow(
                    () -> new CursoException("El curso con id " + id + " no se encuentra en la base de datos")
            );
        }
}
