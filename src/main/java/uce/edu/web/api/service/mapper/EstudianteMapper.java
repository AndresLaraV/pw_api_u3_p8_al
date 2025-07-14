package uce.edu.web.api.service.mapper;

import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.service.to.EstudianteTo;

public class EstudianteMapper {

    public static EstudianteTo toTo(Estudiante estudiante) {

        if (estudiante == null) {
            return null;
        }

        EstudianteTo eTo = new EstudianteTo();
        eTo.setId(estudiante.getId());
        eTo.setNombre(estudiante.getNombre());
        eTo.setApellido(estudiante.getApellido()); // Mapeado
        eTo.setFechaNacimiento(estudiante.getFechaNacimiento()); // Mapeado
        eTo.setCedula(estudiante.getCedula()); // Mapeado
        eTo.setGenero(estudiante.getGenero()); // Mapeado
        return eTo;

    }

    public static Estudiante toEntity(EstudianteTo estudianteTo) {
        if (estudianteTo == null) {
            return null;
        }

        Estudiante e = new Estudiante();
        e.setId(estudianteTo.getId()); 
        e.setNombre(estudianteTo.getNombre());
        e.setApellido(estudianteTo.getApellido()); // Mapeado
        e.setFechaNacimiento(estudianteTo.getFechaNacimiento()); // Mapeado
        e.setCedula(estudianteTo.getCedula()); // Mapeado
        e.setGenero(estudianteTo.getGenero()); // Mapeado
        return e;
    }

}