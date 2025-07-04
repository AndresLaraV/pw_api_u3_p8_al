package uce.edu.web.api.service.to;

import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.controller.ProfesorController;

public class ProfesorTo {
    private Integer id;

    private String nombre;

    private String apellido;

    private String asignatura;

    private String tipoContrato;

    private BigDecimal salario;

    private String email;

    public Map<String, String> _links = new HashMap<>();

    public ProfesorTo(Integer id, String nombre, String apellido, String asignatura, String tipoContrato,
            BigDecimal salario, String email, UriInfo uriInfo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.asignatura = asignatura;
        this.tipoContrato = tipoContrato;
        this.salario = salario;
        this.email = email;

        URI todosHijos = uriInfo.getBaseUriBuilder().path(ProfesorController.class)
                .path(ProfesorController.class, "obtenerHijosPorId").build(id);
        _links.put("hijos", todosHijos.toString());
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
