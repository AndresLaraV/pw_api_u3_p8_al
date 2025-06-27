package uce.edu.web.api.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hibernate.annotations.GeneratorType;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.service.IEstudianteService;

@Path("/estudiantes")
public class EstudianteController {
    @Inject
    private IEstudianteService estudianteService;

    @GET
    @Path("/{id}")
    public Estudiante consultarPorId(@PathParam("id") Integer id) {
        return this.estudianteService.buscarPorId(id);
    }

    @GET
    @Path("")
    public List<Estudiante> consultarTodos() {
        return this.estudianteService.buscarTodos();
    }

    @POST
    @Path("")

    public void guardar(Estudiante estudiante) {
        this.estudianteService.guardar(estudiante);
    }

    @PUT
    @Path("/{id}")
    public void actualizarPorId(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        estudiante.setId(id);
        this.estudianteService.actualizarPorId(estudiante);
    }

    @PATCH
    @Path("/{id}")
    public void actualizarParcialPorId(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        estudiante.setId(id);
        Estudiante existente = this.estudianteService.buscarPorId(id);
        if (existente.getNombre() != null) {
            existente.setNombre(estudiante.getNombre());
        }
        if (existente.getApellido() != null) {
            existente.setApellido(estudiante.getApellido());
        }
        if (existente.getFechaNacimiento() != null) {
            existente.setFechaNacimiento(estudiante.getFechaNacimiento());
        }
        this.estudianteService.actualizarParcialPorId(existente);
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Integer id) {
        this.estudianteService.borrarporID(id);
    }

}