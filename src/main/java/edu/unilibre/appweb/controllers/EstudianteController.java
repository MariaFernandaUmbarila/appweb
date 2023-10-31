package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.EstudianteDetalleMapping;
import edu.unilibre.appweb.models.EstudianteDetalleModel;
import edu.unilibre.appweb.models.EstudianteModel;
import edu.unilibre.appweb.repository.EstudianteDetalleRepository;
import edu.unilibre.appweb.repository.EstudianteRepository;
import edu.unilibre.appweb.services.EstudianteDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRespository;
    @Autowired
    private EstudianteDetalleRepository estudianteDetalleRepository;
    @Autowired
    private EstudianteDetalleService estudianteDetalleService;
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

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_student_summary/{id}")
    public String getStudentSummary(@PathVariable Integer id){
        List<List<Object>> respuestaLista = estudianteDetalleRepository.findNotasByEstudianteId(id);
        return estudianteDetalleService.mapListToJson(respuestaLista);
    }

    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_grade")
    public ResponseEntity<EstudianteDetalleModel> createGrade(@RequestBody EstudianteDetalleMapping notaDto){
        EstudianteDetalleModel nuevaNota = estudianteDetalleRepository.save(estudianteDetalleService.mapDtoToModel(notaDto));
        return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);
    }

}
