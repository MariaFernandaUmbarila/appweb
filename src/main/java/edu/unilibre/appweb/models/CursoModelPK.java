package edu.unilibre.appweb.models;
import java.io.Serializable;

/*Ya que la llave primaria es una llave compuesta,
* se crea una clase aparte para su manejo*/

public class CursoModelPK implements Serializable {
    private MateriaModel materiaModel;
    private ProfesorModel profesorModel;
    private String codigo;

}
