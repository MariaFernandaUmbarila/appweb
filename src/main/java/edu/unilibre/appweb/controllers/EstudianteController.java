package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.models.EstudianteModel;
import org.springframework.http.HttpStatus;
import edu.unilibre.appweb.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRespository;

    @CrossOrigin(origins = "http://localhost:63342/")
    @PostMapping("/create_student")
    public ResponseEntity<EstudianteModel> createEstudiante(@RequestBody EstudianteModel estudiante){
        //Utiliza el JPA para
        EstudianteModel nuevoEstudiante =  estudianteRespository.save(estudiante);
        return new ResponseEntity<>(nuevoEstudiante, HttpStatus.CREATED);

    }

    @CrossOrigin(origins = "http://localhost:63342/")
    @GetMapping("/get_student/{id}")
    public Optional<EstudianteModel> getEstudiante(@PathVariable Integer id) {
        return estudianteRespository.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:63342/")
    @GetMapping("/get_all_students")
    public List<EstudianteModel> getAllEstudiantes(){
        return estudianteRespository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:63342/")
    @PutMapping("/update_student/{id}")
    public ResponseEntity<EstudianteModel> updateEstudiante(@PathVariable Integer id, @RequestBody EstudianteModel estudiante) {

        //Verifica que el estudiante exista primero
        Optional<EstudianteModel> estudianteActual = estudianteRespository.findById(id);
        boolean estudianteExiste = estudianteActual.isPresent();

        if (!estudianteExiste) {
            return new ResponseEntity<EstudianteModel>(HttpStatus.NOT_FOUND);
        }else{
            estudianteActual.get().setNombre(estudiante.getNombre());
            estudianteActual.get().setApellido(estudiante.getApellido());
            estudianteActual.get().setCorreo(estudiante.getCorreo());
            estudianteRespository.save(estudianteActual.get());
            return new ResponseEntity<EstudianteModel>(HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:63342/")
    @DeleteMapping("/delete_student/{id}")
    public ResponseEntity<HttpStatus> borraEstudiante(@PathVariable Integer id) {

        //Verifica que el estudiante exista primero
        Optional<EstudianteModel> estudianteActual = estudianteRespository.findById(id);
        boolean estudianteExiste = estudianteActual.isPresent();

        if (!estudianteExiste) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            estudianteRespository.delete(estudianteActual.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
