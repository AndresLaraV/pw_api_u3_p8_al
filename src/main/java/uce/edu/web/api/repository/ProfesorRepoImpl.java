package uce.edu.web.api.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import uce.edu.web.api.repository.modelo.Profesor;

@Transactional
@ApplicationScoped
public class ProfesorRepoImpl implements IProfesorRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Profesor seleccionarPorId(Integer id) {
        return this.entityManager.find(Profesor.class, id);
    }

    @Override
    public List<Profesor> seleccionarTodos() {
        TypedQuery<Profesor> myQuery = this.entityManager.createQuery("SELECT p FROM Profesor p", Profesor.class);
        return myQuery.getResultList();
    }

    @Override
    public void actualizarPorId(Profesor porfesor) {
        this.entityManager.merge(porfesor);
    }

    @Override
    public void actualizarParcialPorId(Profesor profesor) {
        this.entityManager.merge(profesor);
    }

    @Override
    public void borrarporId(Integer id) {
        this.entityManager.remove(this.seleccionarPorId(id));
    }

    @Override
    public void insertar(Profesor profesor) {
        this.entityManager.persist(profesor);
    }

}
// mvn clean package -Dquarkus.package.type=uber-jar
// java -jar pw_api_u3_p8_al-1.0.0-SNAPSHOT-runner.jar