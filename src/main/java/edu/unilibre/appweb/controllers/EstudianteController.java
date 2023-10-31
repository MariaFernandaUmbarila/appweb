package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.EstudianteModel;
import org.springframework.http.HttpStatus;
import edu.unilibre.appweb.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRespository;
    private static final String frontApi = AppConstants.frontApi;


    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_student")
    public ResponseEntity<EstudianteModel> createStudent(@RequestBody EstudianteModel estudiante){
        EstudianteModel nuevoEstudiante = estudianteRespository.save(estudiante);
        return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_student/{id}")
    public ResponseEntity<EstudianteModel> getStudent(@PathVariable Integer id) {
        EstudianteModel estudiante = estudianteRespository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el estudiante con id: " + id)
        );
        return ResponseEntity.ok(estudiante);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_all_students")
    public List<EstudianteModel> getAllStudents(){
        return estudianteRespository.findAll();
    }

    @CrossOrigin(origins = frontApi)
    @PutMapping("/update_student/{id}")
    public EstudianteModel updateStudent(@PathVariable Integer id, @RequestBody EstudianteModel estudiante) {

        EstudianteModel estudianteActual = estudianteRespository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el estudiante con id: " + id)
        );

        estudianteActual.setNombre(estudiante.getNombre());
        estudianteActual.setApellido(estudiante.getApellido());
        estudianteActual.setCorreo(estudiante.getCorreo());
        return estudianteRespository.save(estudianteActual);

    }

    @CrossOrigin(origins = frontApi)
    @DeleteMapping("/delete_student/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable Integer id) {

        //Verifica que el estudiante exista primero
        EstudianteModel estudianteActual = estudianteRespository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el estudiante con id: " + id)
        );
        estudianteRespository.delete(estudianteActual);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
