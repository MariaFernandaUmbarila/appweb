package edu.unilibre.appweb.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/*En la anotación de Table se utilizó el nombre de la vista,
no de la tabla de personas*/
@Getter
@Setter
@Entity
@Table(name="profesor")
public class ProfesorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
}
