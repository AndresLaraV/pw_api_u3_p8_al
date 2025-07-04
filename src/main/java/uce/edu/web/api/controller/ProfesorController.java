package uce.edu.web.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
//import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IProfesorService;
import uce.edu.web.api.service.to.ProfesorTo;

@Path("/profesores")
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarProfesorPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        ProfesorTo prof = this.profesorService.buscarPorId(id, uriInfo);
        return Response.status(Response.Status.OK)
                .entity(prof)
                .build();
    }

    // ?asignatura=Sociales&provincia=tulcan
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar Profesores", description = "Este endpoint obtiene una lista de todos los profesores")
    public Response consultarProfesores(@QueryParam("asignatura") String asignatura,
            @QueryParam("provincia") String provincia) {
        System.out.println(provincia);
        return Response.status(Response.Status.CREATED).entity(this.profesorService.buscarTodos()).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Guardar Profesor", description = "Guarda un nuevo profesor en el sistema")
    public Response guardar(@RequestBody Profesor profesor) {
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

    // @PATCH
    // @Path("/{id}")
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response actualizarParcial(@RequestBody Profesor profesor,
    // @PathParam("id") Integer id) {
    // profesor.setId(id);
    // Profesor p = this.profesorService.buscarPorId(id);
    // if (profesor.getNombre() != null) {
    // p.setNombre(profesor.getNombre());
    // }
    // if (profesor.getApellido() != null) {
    // p.setApellido(profesor.getApellido());
    // }
    // if (profesor.getEmail() != null) {
    // p.setEmail(profesor.getEmail());
    // }
    // this.profesorService.actualizarPorId(p);
    // return Response.status(Response.Status.OK).entity("Profesor actualizado
    // parcialmente").build();
    // }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        this.profesorService.borrarporId(id);
        return Response.status(Response.Status.NO_CONTENT).entity("Profesor eliminado").build();
    }

    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {

        Hijo h1 = new Hijo();
        Hijo h2 = new Hijo();
        h1.setNombre("Carmencita");
        h2.setNombre("Jaime Enrique");
        h1.setApellido("Lara");
        h2.setApellido("Aymara");

        List<Hijo> hijos = new ArrayList<>();
        hijos.add(h1);
        hijos.add(h2);
        return hijos;
    }
}
// mvn clean package -Dquarkus.package.type=uber-jar
// java -jar pw_api_u3_p8_al-1.0.0-SNAPSHOT-runner.jar
// http://localhost:8081/api/matricula/v1/profesores