package edu.unilibre.appweb.services;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class CursoMapping {
    private String codigo;
    private Integer materia;
    private Integer profesor;
    private Date fechaInicio;
    private Date fechaFin;
}
