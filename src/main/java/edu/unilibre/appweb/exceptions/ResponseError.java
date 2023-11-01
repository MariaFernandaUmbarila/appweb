package edu.unilibre.appweb.exceptions;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ResponseError {
    public ResponseError(String mensaje, List<String> detalles){
        super();
        this.mensaje = mensaje;
    }

    private String mensaje;
    private List<String> detalles;

}
