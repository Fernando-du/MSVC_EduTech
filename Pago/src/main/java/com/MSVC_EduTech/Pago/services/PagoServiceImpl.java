package com.MSVC_EduTech.Pago.services;

import com.MSVC_EduTech.Pago.exceptions.PagoException;
import com.MSVC_EduTech.Pago.models.entities.Pago;
import com.MSVC_EduTech.Pago.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;


    @Override
    public List<Pago> findAll(){
        return this.pagoRepository.findAll();
    }

    @Override
    public Pago findById(Long id){
        return this.pagoRepository.findById(id).orElseThrow(
                () -> new PagoException("El pago con id "+id+"no se encuentra en la base de datos")
        );
    }
    @Override
    public Pago save(Pago pago) {
        return this.pagoRepository.save(pago);
    }

    @Override
    public void deleteById(Long id){
        pagoRepository.deleteById(id);
    }

    @Override
    public Pago updateById(Long id, Pago pagoUpdate) {
        return pagoRepository.findById(id).map(pago -> {
            pago.setFechaPago(pagoUpdate.getFechaPago());
            pago.setValorPago(pagoUpdate.getValorPago());
            pago.setIdAlumno(pagoUpdate.getIdAlumno());
            pago.setIdCurso(pagoUpdate.getIdCurso());
            return pagoRepository.save(pago);
        }).orElseThrow(
                () -> new PagoException("El pago con id "+id+"no se encuentra en la base de datos")
        );
    }
}
