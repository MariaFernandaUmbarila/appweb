package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.models.NotaModel;
import edu.unilibre.appweb.repository.EstudianteRepository;
import edu.unilibre.appweb.repository.MateriaRepository;
import edu.unilibre.appweb.repository.NotaRepository;
import edu.unilibre.appweb.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;

    private static final String frontApi = AppConstants.frontApi;


    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_grade")
    public ResponseEntity<NotaModel> createGrade(@RequestBody NotaModel nota){
        NotaModel nuevaNota = notaRepository.save(nota);
        return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);
    }

}
