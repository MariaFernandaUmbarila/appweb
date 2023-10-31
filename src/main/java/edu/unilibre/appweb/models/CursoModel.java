package edu.unilibre.appweb.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="curso")
public class CursoModel {

    @Id
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "materia_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MateriaModel materiaModel;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "profesor_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProfesorModel profesorModel;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;
}
