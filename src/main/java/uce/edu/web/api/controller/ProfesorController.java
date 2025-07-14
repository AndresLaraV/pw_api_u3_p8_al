package uce.edu.web.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IProfesorService;
import uce.edu.web.api.service.mapper.ProfesorMapper; 
import uce.edu.web.api.service.to.ProfesorTo;     

@Path("/profesores")
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    // GET /profesores/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar profesor por ID", description = "Endpoint para consultar un profesor por su ID.")
    public Response consultarProfesorPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Profesor profesor = this.profesorService.buscarPorId(id);
        if (profesor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Profesor con ID " + id + " no encontrado.")
                    .build();
        }
        ProfesorTo pTo = ProfesorMapper.toTo(profesor);
        pTo.buildURI(uriInfo); // Construir enlaces HATEOAS
        return Response.status(Response.Status.OK).entity(pTo).build();
    }

    // GET /profesores
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar todos los profesores", description = "Endpoint para consultar todos los profesores. Permite filtrar por asignatura.")
    public Response consultarProfesores(@QueryParam("asignatura") String asignatura,
            @QueryParam("provincia") String provincia, @Context UriInfo uriInfo) {

        List<Profesor> profesores = this.profesorService.buscarTodos();

        List<ProfesorTo> profesoresTo = profesores.stream()
                .map(p -> {
                    ProfesorTo pTo = ProfesorMapper.toTo(p);
                    pTo.buildURI(uriInfo); 
                    return pTo;
                })
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK).entity(profesoresTo).build();
    }

    // POST /profesores
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear un nuevo profesor", description = "Endpoint para registrar un nuevo profesor.")
    public Response guardar(ProfesorTo profesorTo, @Context UriInfo uriInfo) {
        profesorTo.setId(null); 
        Profesor profesor = ProfesorMapper.toEntity(profesorTo);
        this.profesorService.guardar(profesor);

        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(profesor.getId())).build();
        
        ProfesorTo savedProfesorTo = ProfesorMapper.toTo(profesor);
        savedProfesorTo.buildURI(uriInfo);

        return Response.created(location)
                       .entity(savedProfesorTo)
                       .build();
    }

    // PUT /profesores/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar un profesor por ID", description = "Endpoint para reemplazar completamente un profesor existente por su ID.")
    public Response actualizar(ProfesorTo profesorTo, @PathParam("id") Integer id) {
        if (profesorTo.getId() != null && !profesorTo.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El ID en la URL no coincide con el ID en el cuerpo de la solicitud.")
                    .build();
        }

        Profesor existingProfesor = this.profesorService.buscarPorId(id);
        if (existingProfesor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Profesor con ID " + id + " no encontrado para actualizar.")
                    .build();
        }

        profesorTo.setId(id); 
        Profesor profesor = ProfesorMapper.toEntity(profesorTo);
        this.profesorService.actualizarPorId(profesor);
        return Response.status(Response.Status.NO_CONTENT).build(); 
    }

    // PATCH /profesores/{id}
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar parcialmente un profesor por ID", description = "Endpoint para actualizar parcialmente los campos de un profesor existente por su ID.")
    public Response actualizarParcial(ProfesorTo profesorTo, @PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Profesor existingProfesor = this.profesorService.buscarPorId(id);
        if (existingProfesor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Profesor con ID " + id + " no encontrado para actualización parcial.")
                    .build();
        }
        if (profesorTo.getNombre() != null) {
            existingProfesor.setNombre(profesorTo.getNombre());
        }
        if (profesorTo.getApellido() != null) {
            existingProfesor.setApellido(profesorTo.getApellido());
        }
        if (profesorTo.getAsignatura() != null) {
            existingProfesor.setAsignatura(profesorTo.getAsignatura());
        }
        if (profesorTo.getTipoContrato() != null) {
            existingProfesor.setTipoContrato(profesorTo.getTipoContrato());
        }
        if (profesorTo.getSalario() != null) {
            existingProfesor.setSalario(profesorTo.getSalario());
        }
        if (profesorTo.getEmail() != null) {
            existingProfesor.setEmail(profesorTo.getEmail());
        }

        this.profesorService.actualizarParcialPorId(existingProfesor);

        ProfesorTo updatedProfesorTo = ProfesorMapper.toTo(existingProfesor);
        updatedProfesorTo.buildURI(uriInfo);
        return Response.status(Response.Status.OK).entity(updatedProfesorTo).build();
    }

    // DELETE /profesores/{id}
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un profesor por ID", description = "Endpoint para eliminar un profesor existente por su ID.")
    public Response eliminar(@PathParam("id") Integer id) {
        Profesor existingProfesor = this.profesorService.buscarPorId(id);
        if (existingProfesor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Profesor con ID " + id + " no encontrado para eliminar.")
                    .build();
        }
        this.profesorService.borrarporId(id);
        return Response.status(Response.Status.NO_CONTENT).build(); 
    }

}