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
        Estudiante e = this.estudianteService.buscarPorId(id);
        if (e.getNombre() != null) {
            e.setNombre(estudiante.getNombre());
        }
        if (e.getApellido() != null) {
            e.setApellido(estudiante.getApellido());
        }
        if (e.getFechaNacimiento() != null) {
            e.setFechaNacimiento(estudiante.getFechaNacimiento());
        }
        if (e.getCedula() != null) {
            e.setCedula(estudiante.getCedula());
        }
        this.estudianteService.actualizarParcialPorId(e);
    }

    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Integer id) {
        this.estudianteService.borrarporID(id);
    }

}