package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.ProfesorModel;
import edu.unilibre.appweb.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProfesorController {

    @Autowired
    private ProfesorRepository profesorRepository;
    private static final String frontApi = AppConstants.frontApi;

    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_teacher")
    public ResponseEntity<ProfesorModel> createTeacher(@RequestBody ProfesorModel profesor){
        ProfesorModel nuevoProfesor =  profesorRepository.save(profesor);
        return new ResponseEntity<>(nuevoProfesor, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_teacher/{id}")
    public ResponseEntity<ProfesorModel> getTeacher(@PathVariable Integer id){
        ProfesorModel profesor = profesorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el profesor con id: " + id)
        );
        return ResponseEntity.ok(profesor);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_all_teachers")
    public List<ProfesorModel> getAllTeachers(){ return profesorRepository.findAll(); }

    @CrossOrigin(origins = frontApi)
    @PutMapping("/update_teacher/{id}")
    public ProfesorModel updateTeacher(@PathVariable Integer id, @RequestBody ProfesorModel profesor){

        ProfesorModel profesorActual = profesorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el profesor con id: " + id)
        );
        profesorActual.setNombre(profesor.getNombre());
        profesorActual.setApellido(profesor.getApellido());
        profesorActual.setCorreo(profesor.getCorreo());
        return profesorRepository.save(profesorActual);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/delete_teacher/{id}")
    public ResponseEntity<ProfesorModel> deteleTeacher(@PathVariable Integer id){
        ProfesorModel profesor = profesorRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No existe el profesor con id: " + id)
        );
        profesorRepository.delete(profesor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
