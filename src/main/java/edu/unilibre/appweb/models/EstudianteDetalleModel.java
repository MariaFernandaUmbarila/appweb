package edu.unilibre.appweb.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name= "estudiante_detalle")
public class EstudianteDetalleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String observacion;
    private Double valor;
    private Double porcentaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_codigo", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CursoModel codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EstudianteModel estudiante;

}
