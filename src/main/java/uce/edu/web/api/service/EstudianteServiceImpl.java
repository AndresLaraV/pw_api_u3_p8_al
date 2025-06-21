package uce.edu.web.api.service;

import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.IEstudianteRepo;
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

}