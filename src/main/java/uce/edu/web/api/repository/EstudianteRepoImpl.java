package uce.edu.web.api.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.enterprise.inject.Typed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import uce.edu.web.api.repository.modelo.Estudiante;

@Transactional
@ApplicationScoped
public class EstudianteRepoImpl implements IEstudianteRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Estudiante seleccionarPorId(Integer id) {
        return this.entityManager.find(Estudiante.class, id);
    }

    @Override
    public List<Estudiante> seleccionarTodos() {
        TypedQuery<Estudiante> myQuery = this.entityManager.createQuery("SELECT e FROM Estudiante e", Estudiante.class);
        return myQuery.getResultList();
    }

  @Override
    public void actualizarPorId(Estudiante estudiante) {
        this.entityManager.merge(estudiante);
    }
 
    @Override
    public void actualizarParcialPorId(Estudiante estudiante) {
        this.entityManager.merge(estudiante);
    }

    @Override
    public void borrarporID(Integer id) {
        this.entityManager.remove(this.seleccionarPorId(id));
    }

    @Override
    public void insertar(Estudiante estudiante) {
        this.entityManager.persist(estudiante);
    }

}