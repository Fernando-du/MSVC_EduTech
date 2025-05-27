package com.MSVC_EduTech.Evaluacion.repositories;

import com.MSVC_EduTech.Evaluacion.models.entities.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
