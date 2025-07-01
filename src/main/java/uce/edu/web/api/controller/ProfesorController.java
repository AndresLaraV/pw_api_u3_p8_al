package uce.edu.web.api.controller;

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IProfesorService;

@Path("/profesores")
public class ProfesorController {
    @Inject
    private IProfesorService profesorService;

    @GET
    @Path("/{id}")
    public Profesor consultarPorId(@PathParam("id") Integer id) {
        return this.profesorService.buscarPorId(id);
    }

    @GET
    @Path("")
    public List<Profesor> consultarTodos() {
        return this.profesorService.buscarTodos();
    }

    @POST
    @Path("")
    public void guardar(Profesor profesor) {
        this.profesorService.guardar(profesor);
    }

    @PUT
    @Path("/{id}")
    public void actualizar(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        this.profesorService.actualizarPorId(profesor);
    }

    @PATCH
    @Path("/{id}")
    public void actualizarParcial(Profesor profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        Profesor p = this.profesorService.buscarPorId(id);
        if (p == null) {
            throw new RuntimeException("Profesor no encontrado");
        }
        if (profesor.getNombre() != null) {
            p.setNombre(profesor.getNombre());
        }
        if (profesor.getApellido() != null) {
            p.setApellido(profesor.getApellido());
        }
        if (profesor.getAsignatura() != null) {
            p.setAsignatura(profesor.getAsignatura());
        }
        if (profesor.getEmail() != null) {
            p.setEmail(profesor.getEmail());
        }
        if (profesor.getTipoContrato() != null) {
            p.setTipoContrato(profesor.getTipoContrato());
        }
        if (profesor.getSalario() != null) {
            p.setSalario(profesor.getSalario());
        }
        this.profesorService.actualizarParcialPorId(p);
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Integer id) {
        this.profesorService.borrarporId(id);
        }
}
