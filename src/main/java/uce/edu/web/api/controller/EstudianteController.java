package uce.edu.web.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
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
import jakarta.ws.rs.PATCH;
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.modelo.Hijo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.Claim;
import uce.edu.web.api.service.IEstudianteService;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.mapper.EstudianteMapper;
import uce.edu.web.api.service.to.EstudianteTo;

@Path("/estudiantes")
public class EstudianteController {
    @Inject
    JsonWebToken jwt;
    @Inject
    @Claim("sub")
    ClaimValue<String> subject;
    @Inject
    private IEstudianteService estudianteService;
    @Inject
    private IHijoService hijoService;

    public EstudianteController() {
    }

    // GET /estudiantes/{id}
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar estudiante por ID", description = "Endpoint para consultar un estudiante por su ID.")
    public Response consultarPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Estudiante estudiante = this.estudianteService.buscarPorId(id);
        if (estudiante == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Estudiante con ID " + id + " no encontrado.")
                    .build();
        }
        EstudianteTo estuTo = EstudianteMapper.toTo(estudiante);
        estuTo.buildURI(uriInfo);
        return Response.status(Response.Status.OK)
                .entity(estuTo)
                .build();
    }

    // GET /estudiantes
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar todos los estudiantes", description = "Endpoint para consultar todos los estudiantes registrados. Permite filtrar por género.")
    public Response consultarTodos(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia, @Context UriInfo uriInfo) {
        System.out.println("Filtrando por genero: " + genero + " y provincia (no usada): " + provincia);

        List<Estudiante> estudiantes = this.estudianteService.buscarTodos(genero);

        List<EstudianteTo> estudiantesTo = estudiantes.stream()
                .map(e -> {
                    EstudianteTo eto = EstudianteMapper.toTo(e);
                    eto.buildURI(uriInfo);
                    return eto;
                })
                .collect(Collectors.toList());

        return Response.status(Response.Status.OK)
                .entity(estudiantesTo)
                .build();
    }

    // POST /estudiantes
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear un nuevo estudiante", description = "Endpoint para registrar un nuevo estudiante.")
    public Response guardar(@RequestBody EstudianteTo estudianteTo, @Context UriInfo uriInfo) {
        estudianteTo.setId(null);
        Estudiante estudiante = EstudianteMapper.toEntity(estudianteTo);
        this.estudianteService.guardar(estudiante);

        // Construir la URI del nuevo recurso creado
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(estudiante.getId())).build();

        EstudianteTo savedEstudianteTo = EstudianteMapper.toTo(estudiante);
        savedEstudianteTo.buildURI(uriInfo); // Construir enlaces HATEOAS

        return Response.created(location)
                .entity(savedEstudianteTo)
                .build();
    }

    // PUT /estudiantes/{id}
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar un estudiante por ID", description = "Endpoint para reemplazar completamente un estudiante existente por su ID.")
    public Response actualizarPorId(@RequestBody EstudianteTo estudianteTo, @PathParam("id") Integer id) {
        if (estudianteTo.getId() != null && !estudianteTo.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El ID en la URL no coincide con el ID en el cuerpo de la solicitud.")
                    .build();
        }

        estudianteTo.setId(id);
        Estudiante estudiante = EstudianteMapper.toEntity(estudianteTo);

        Estudiante estu = this.estudianteService.buscarPorId(id);
        if (estu == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Estudiante con ID " + id + " no encontrado para actualizar.")
                    .build();
        }

        this.estudianteService.actualizarPorId(estudiante);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar parcialmente un estudiante por ID", description = "Endpoint para actualizar parcialmente los campos de un estudiante existente por su ID.")
    public Response actualizarParcialPorId(@RequestBody EstudianteTo estudianteTo, @PathParam("id") Integer id) {
        Estudiante estu = this.estudianteService.buscarPorId(id);

        if (estu == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Estudiante con ID " + id + " no encontrado para actualizar parcialmente.")
                    .build();
        }

        boolean seModificoAlMenosUnCampo = false;

        if (estudianteTo.getNombre() != null) {
            estu.setNombre(estudianteTo.getNombre());
            seModificoAlMenosUnCampo = true;
        }

        if (estudianteTo.getApellido() != null) {
            estu.setApellido(estudianteTo.getApellido());
            seModificoAlMenosUnCampo = true;
        }

        if (estudianteTo.getFechaNacimiento() != null) {
            estu.setFechaNacimiento(estudianteTo.getFechaNacimiento());
            seModificoAlMenosUnCampo = true;
        }

        if (estudianteTo.getCedula() != null) {
            estu.setCedula(estudianteTo.getCedula());
            seModificoAlMenosUnCampo = true;
        }

        if (estudianteTo.getGenero() != null) {
            estu.setGenero(estudianteTo.getGenero());
            seModificoAlMenosUnCampo = true;
        }

        if (!seModificoAlMenosUnCampo) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Debe proporcionar al menos un campo para actualizar.")
                    .build();
        }

        this.estudianteService.actualizarParcialPorId(estu);

        EstudianteTo updatedEstudianteTo = EstudianteMapper.toTo(estu);
        return Response.status(Response.Status.OK)
                .entity(updatedEstudianteTo)
                .build();
    }

    // DELETE /estudiantes/{id}
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un estudiante por ID", description = "Endpoint para eliminar un estudiante existente por su ID.")
    public Response eliminar(@PathParam("id") Integer id) {
        Estudiante estu = this.estudianteService.buscarPorId(id);
        if (estu == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Estudiante con ID " + id + " no encontrado para eliminar.")
                    .build();
        }

        this.estudianteService.borrarporID(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // http://.../estudiantes/1/hijos GET
    @GET
    @Path("/{id}/hijos")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener hijos de un estudiante", description = "Endpoint para obtener la lista de hijos asociados a un estudiante por su ID.")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorEstudianteId(id);
    }

}
// mvn clean package -Dquarkus.package.type=uber-jar
// java -jar pw_api_u3_p8_al-1.0.0-SNAPSHOT-runner.jar