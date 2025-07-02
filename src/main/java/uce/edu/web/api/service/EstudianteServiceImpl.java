package uce.edu.web.api.service;

import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.IEstudianteRepo;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EstudianteServiceImpl implements IEstudianteService {

    @Inject
    private IEstudianteRepo estudianteRepo;

    @Override
    public Estudiante buscarPorId(Integer id) {
        return this.estudianteRepo.seleccionarPorId(id);

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