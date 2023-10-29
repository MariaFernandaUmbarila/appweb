package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.models.NotaModel;
import edu.unilibre.appweb.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;
    private final String frontApi = "http://localhost:63342/";


    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_student_grades/{id}")
    public ResponseEntity<NotaModel> getStudentGrades(@PathVariable Integer id){
        return null;
    }

}
