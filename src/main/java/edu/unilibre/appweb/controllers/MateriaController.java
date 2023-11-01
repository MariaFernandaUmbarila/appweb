package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.MateriaModel;
import edu.unilibre.appweb.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class MateriaController {

    @Autowired
    private MateriaRepository materiaRepository;
    private static final String frontApi = AppConstants.frontApi;

    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_subject")
    public ResponseEntity<MateriaModel> createSubject(@RequestBody MateriaModel materia){
        MateriaModel nuevaMateria = materiaRepository.save(materia);
        return new ResponseEntity<>(nuevaMateria, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_subject/{id}")
    public ResponseEntity<MateriaModel> getSubject(@PathVariable Integer id) {
        MateriaModel materia = materiaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el estudiante con id: " + id)
        );
        return ResponseEntity.ok(materia);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_all_subjects")
    public List<MateriaModel> getAllSubjects(){
        return materiaRepository.findAll();
    }

    @CrossOrigin(origins = frontApi)
    @PutMapping("/update_subject/{id}")
    public MateriaModel updateSubject(@PathVariable Integer id, @RequestBody MateriaModel materia) {

        MateriaModel materiaActual = materiaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe la materia con id: " + id)
        );

        materiaActual.setNombre(materia.getNombre());
        materiaActual.setCreditos(materia.getCreditos());
        return materiaRepository.save(materiaActual);

    }

    @CrossOrigin(origins = frontApi)
    @DeleteMapping("/delete_subject/{id}")
    public ResponseEntity<HttpStatus> deleteSubject(@PathVariable Integer id) {

        MateriaModel materiaActual = materiaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe la materia con id: " + id)
        );
        materiaRepository.delete(materiaActual);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
