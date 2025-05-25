package com.MSVC_EduTech.Pago.services;

import com.MSVC_EduTech.Pago.models.Pago;

import java.util.List;

public interface PagoService {
    List<Pago> findAll();
    Pago findById(Long id);
    Pago save(Pago pago);
    Pago updateById(Long id, Pago pagoUpdate);
    Pago deleteById(Long id);
}
