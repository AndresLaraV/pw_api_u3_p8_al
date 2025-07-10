package uce.edu.web.api.repository.modelo;
 
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "hijo")
public class Hijo {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hijo_id")
    private Integer id;
 
    @Column(name = "hijo_nombre")
    private String nombre;
 
    @Column(name = "hijo_apellido")
    private String apellido;
 
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hijo_estudiante")
    private Estudiante estudiante;
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public Estudiante getEstudiante() {
        return estudiante;
    }
 
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getApellido() {
        return apellido;
    }
 
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
   
}