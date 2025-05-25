package com.MSVC_EduTech.Nota.repositories;

import com.MSVC_EduTech.Nota.models.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {





}

