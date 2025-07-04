package uce.edu.web.api.service;

import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.service.to.EstudianteTo;
import uce.edu.web.api.repository.IEstudianteRepo;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriInfo;

@ApplicationScoped
public class EstudianteServiceImpl implements IEstudianteService {

    @Inject
    private IEstudianteRepo estudianteRepo;

    @Override
    public EstudianteTo buscarPorId(Integer id, UriInfo uriInfo) {
        Estudiante e1 = this.estudianteRepo.seleccionarPorId(id);
        EstudianteTo e = new EstudianteTo(e1.getId(), e1.getNombre(), e1.getApellido(), e1.getFechaNacimiento(),
                e1.getCedula(), e1.getGenero(), uriInfo);
        return e;
        
    }

    @Override
    public List<Estudiante> buscarTodos(String genero) {
        return this.estudianteRepo.seleccionarTodos(genero);
    }

    @Override
    public void borrarporID(Integer id) {
        this.estudianteRepo.borrarporID(id);
    }

    @Override
    public void actualizarPorId(Estudiante estudiante) {
        this.estudianteRepo.actualizarPorId(estudiante);
    }

    @Override
    public void actualizarParcialPorId(Estudiante estudiante) {
        this.estudianteRepo.actualizarParcialPorId(estudiante);
    }

    @Override
    public void guardar(Estudiante estudiante) {
        this.estudianteRepo.insertar(estudiante);
    }

}