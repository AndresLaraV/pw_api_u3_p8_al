package uce.edu.web.api.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
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
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.service.IEstudianteService;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.mapper.EstudianteMapper;
import uce.edu.web.api.service.to.EstudianteTo;

@Path("/estudiantes")
public class EstudianteController {
    @Inject
    private IEstudianteService estudianteService;

    @Inject
    private IHijoService hijoService;

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Response consultarPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        EstudianteTo estu = EstudianteMapper.toTo(this.estudianteService.buscarPorId(id));
        estu.buildURI(uriInfo);
        return Response.status(Response.Status.OK)
                .entity(estu)
                .build();
    }

    // ?genero=F&provincia=pichincha
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar todos los estudiantes", description = "Enpoint para consultar todos los estudiantes registrados")
    public Response consultarTodos(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia) {
        System.out.println(provincia);
        return Response.status(Response.Status.OK)
                .entity(this.estudianteService.buscarTodos(genero))
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public void guardar(@RequestBody Estudiante estudiante) {
        this.estudianteService.guardar(estudiante);
    }

    @PUT
    @Path("/{id}")
    @Consumes
    public Response actualizarPorId(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // @PATCH
    // @Path("/{id}")
    // @Consumes(MediaType.APPLICATION_JSON)
    // public Response actualizarParcialPorId(@RequestBody Estudiante estudiante,
    // @PathParam("id") Integer id) {
    // estudiante.setId(id);
    // Estudiante e = this.estudianteService.buscarPorId(id);
    // if (e == null) {
    // return Response.status(Response.Status.NOT_FOUND)
    // .entity("Estudiante no encontrado")
    // .build();
    // }
    // if (estudiante.getNombre() != null) {
    // e.setNombre(estudiante.getNombre());
    // }
    // if (estudiante.getApellido() != null) {
    // e.setApellido(estudiante.getApellido());
    // }
    // if (estudiante.getFechaNacimiento() != null) {
    // e.setFechaNacimiento(estudiante.getFechaNacimiento());
    // }
    // if (estudiante.getCedula() != null) {
    // e.setCedula(estudiante.getCedula());
    // }
    // if (estudiante.getGenero() != null) {
    // e.setGenero(estudiante.getGenero());
    // }
    // // Optionally, persist the changes here
    // // this.estudianteService.actualizar(e);
    // return Response.status(Response.Status.OK)
    // .entity("Actualizacion parcial correcta")
    // .build();
    // }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // http://.../estudiantes/1/hijos GET
    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorEstudianteId(id);
    }

}
// mvn clean package -Dquarkus.package.type=uber-jar
// java -jar pw_api_u3_p8_al-1.0.0-SNAPSHOT-runner.jar