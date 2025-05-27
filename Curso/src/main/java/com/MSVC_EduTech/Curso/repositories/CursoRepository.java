package com.MSVC_EduTech.Curso.repositories;

import com.MSVC_EduTech.Curso.models.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
