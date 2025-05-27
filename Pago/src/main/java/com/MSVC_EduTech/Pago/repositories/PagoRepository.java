package com.MSVC_EduTech.Pago.repositories;

import com.MSVC_EduTech.Pago.models.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository <Pago, Long> {
}
