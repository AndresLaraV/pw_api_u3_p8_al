package uce.edu.web.api.service;

import java.util.List;

import uce.edu.web.api.repository.modelo.Estudiante;

public interface IEstudianteService {
    public Estudiante buscarPorId(Integer id);

    public List<Estudiante> buscarTodos();

    public void actualizarPorId(Estudiante estudiante);

    public void actualizarParcialPorId(Estudiante estudiante);

    public void borrarporID(Integer id);

    public void guardar(Estudiante estudiante);
}