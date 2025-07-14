package uce.edu.web.api.service.mapper;

import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.to.ProfesorTo;

public class ProfesorMapper {

    public static ProfesorTo toTo(Profesor profesor) {
        if (profesor == null) {
            return null;
        }

        ProfesorTo pTo = new ProfesorTo();
        pTo.setId(profesor.getId());
        pTo.setNombre(profesor.getNombre());
        pTo.setApellido(profesor.getApellido());
        pTo.setAsignatura(profesor.getAsignatura());
        pTo.setTipoContrato(profesor.getTipoContrato());
        pTo.setSalario(profesor.getSalario());
        pTo.setEmail(profesor.getEmail());
        return pTo;
    }

    public static Profesor toEntity(ProfesorTo profesorTo) {
        if (profesorTo == null) {
            return null;
        }

        Profesor p = new Profesor();
        p.setId(profesorTo.getId());
        p.setNombre(profesorTo.getNombre());
        p.setApellido(profesorTo.getApellido());
        p.setAsignatura(profesorTo.getAsignatura());
        p.setTipoContrato(profesorTo.getTipoContrato());
        p.setSalario(profesorTo.getSalario());
        p.setEmail(profesorTo.getEmail());
        return p;
    }
}