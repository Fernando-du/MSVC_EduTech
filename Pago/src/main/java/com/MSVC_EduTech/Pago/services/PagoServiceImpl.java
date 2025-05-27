package com.MSVC_EduTech.Pago.services;

import com.MSVC_EduTech.Pago.clients.AlumnoClientRest;
import com.MSVC_EduTech.Pago.clients.CursoClientRest;
import com.MSVC_EduTech.Pago.exceptions.PagoException;
import com.MSVC_EduTech.Pago.models.Alumno;
import com.MSVC_EduTech.Pago.models.Curso;
import com.MSVC_EduTech.Pago.models.entities.Pago;
import com.MSVC_EduTech.Pago.repositories.PagoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private AlumnoClientRest alumnoClientRest;

    @Autowired
    private CursoClientRest cursoClientRest;

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
        try{
            Alumno alumno = this.alumnoClientRest.findById(pago.getIdAlumno());
            Curso curso = this.cursoClientRest.findById(pago.getIdCurso());
        }catch (FeignException ex) {
            throw new PagoException("Existen problemas con la asoción");
        }
        return this.pagoRepository.save(pago);
    }

    @Override
    public void deleteById(Long id){
        pagoRepository.deleteById(id);
    }

    @Override
    public Pago updateById(Long id, Pago pagoUpdate) {
        try{
            Alumno alumno = this.alumnoClientRest.findById(pagoUpdate.getIdAlumno());
            Curso curso = this.cursoClientRest.findById(pagoUpdate.getIdCurso());
        }catch (FeignException ex) {
            throw new PagoException("Existen problemas con la asoción");
        }
        return pagoRepository.findById(id).map(pago -> {
            pago.setFechaPago(pagoUpdate.getFechaPago());
            pago.setValorPago(pagoUpdate.getValorPago());
            return pagoRepository.save(pago);
        }).orElseThrow(
                () -> new PagoException("El pago con id "+id+"no se encuentra en la base de datos")
        );
    }
}
