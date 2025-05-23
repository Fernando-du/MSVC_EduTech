package com.MVSC_EduTech.Profesor.repositories;

import com.MVSC_EduTech.Profesor.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
}
