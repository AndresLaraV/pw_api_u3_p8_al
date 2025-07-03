package uce.edu.web.api.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IProfesorService;

@Path("/profesores")
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarProfesorPorId(@PathParam("id") Integer id) {
        return Response.status(Response.Status.ACCEPTED).entity(this.profesorService.buscarPorId(id)).build();
    }

    // ?materia=Programacion&ciudad=quito
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar Profesores", description = "Este endpoint obtiene una lista de todos los profesores")
    public Response consultarProfesores(@QueryParam("materia") String materia,
            @QueryParam("provincia") String provincia) {
        System.out.println(provincia);
        return Response.status(Response.Status.CREATED).entity(this.profesorService.buscarTodos()).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Guardar Profesor", description = "Guarda un nuevo profesor en el sistema")
    public Response guardar(Profesor profesor) {
        this.profesorService.guardar(profesor);
        return Response.status(Response.Status.CREATED).entity("Profesor guardado").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizar(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        this.profesorService.actualizarPorId(profesor);
        return Response.status(Response.Status.OK).entity("Profesor actualizado").build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarParcial(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        Profesor p = this.profesorService.buscarPorId(id);
        if (profesor.getNombre() != null) {
            p.setNombre(profesor.getNombre());
        }
        if (profesor.getApellido() != null) {
            p.setApellido(profesor.getApellido());
        }
        if (profesor.getEmail() != null) {
            p.setEmail(profesor.getEmail());
        }
        this.profesorService.actualizarPorId(p);
        return Response.status(Response.Status.OK).entity("Profesor actualizado parcialmente").build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        this.profesorService.eliminarPorId(id);
        return Response.status(Response.Status.NO_CONTENT).entity("Profesor eliminado").build();
    }

}