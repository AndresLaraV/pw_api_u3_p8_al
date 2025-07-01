package uce.edu.web.api.repository;

import java.util.List;
import uce.edu.web.api.repository.modelo.Profesor;

public interface IProfesorRepo {
   public Profesor seleccionarPorId(Integer id);

   public List<Profesor> seleccionarTodos();

   public void actualizarPorId(Profesor porfesor);

   public void actualizarParcialPorId(Profesor profesor);

   public void borrarporId(Integer id);

   public void insertar(Profesor profesor);
}
