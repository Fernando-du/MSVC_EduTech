package com.MSVC_EduTech.Alumno.repositories;

import com.MSVC_EduTech.Alumno.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository <Alumno,Long>{
}
