package edu.unilibre.appweb.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@ControllerAdvice
public class ApplicationException extends ResponseEntityExceptionHandler {

    public final ResponseEntity<ResponseError> ManagerNoDataException
            (NotFoundException ex, WebRequest request){

        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());
        ResponseError error = new ResponseError("Error en la petición: ", detalles);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
