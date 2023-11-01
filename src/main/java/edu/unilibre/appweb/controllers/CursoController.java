package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.CursoModel;
import edu.unilibre.appweb.models.MateriaModel;
import edu.unilibre.appweb.models.ProfesorModel;
import edu.unilibre.appweb.repository.CursoRepository;
import edu.unilibre.appweb.repository.MateriaRepository;
import edu.unilibre.appweb.repository.ProfesorRepository;
import edu.unilibre.appweb.services.CursoMapping;
import edu.unilibre.appweb.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    private static final String frontApi = AppConstants.frontApi;

    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_course")
    public ResponseEntity<CursoModel> createCourse(@RequestBody CursoMapping curso){
        CursoModel nuevoCurso = cursoService.mapDtoToModel(curso);
        cursoRepository.save(nuevoCurso);
        return ResponseEntity.ok(nuevoCurso);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_course/{codigo}")
    public ResponseEntity<CursoModel> getCourse(@PathVariable String codigo) {
        CursoModel curso = cursoRepository.findByCodigo(codigo).orElseThrow(
                () -> new NotFoundException("No existe el curso con código: " + codigo)
        );
        return ResponseEntity.ok(curso);
    }

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_all_courses")
    public List<CursoModel> getAllCourses(){
        return cursoRepository.findAll();
    }

    @CrossOrigin(origins = frontApi)
    @PutMapping("/update_course/{codigo}")
    public ResponseEntity<CursoModel> updateCourse(@PathVariable String codigo, @RequestBody CursoMapping curso) {

        CursoModel cursoActual = cursoRepository.findByCodigo(codigo).orElseThrow(
                () -> new NotFoundException("No existe el curso con código: " + codigo)
        );

        MateriaModel materiaAct = materiaRepository.getReferenceById(curso.getMateria());
        if (materiaAct.getId() <= 0) throw new NotFoundException("No fue encontrada la materia con id: " + curso.getMateria());

        ProfesorModel profesAct = profesorRepository.getReferenceById(curso.getProfesor());
        if (profesAct.getId() <= 0) throw new NotFoundException("No fue encontrado el profesor con id: " + curso.getProfesor());

        cursoActual.setMateriaModel(materiaAct);
        cursoActual.setProfesorModel(profesAct);

        if(curso.getFechaFin() != null) cursoActual.setFechaFin(curso.getFechaFin());
        if(curso.getFechaInicio() != null) cursoActual.setFechaInicio(curso.getFechaInicio());

        cursoRepository.save(cursoActual);
        return ResponseEntity.ok(cursoActual);

    }


    @CrossOrigin(origins = frontApi)
    @DeleteMapping("/delete_course/{codigo}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable String codigo) {

        CursoModel cursoActual = cursoRepository.findByCodigo(codigo).orElseThrow(
                () -> new NotFoundException("No existe el curso con código: " + codigo)
        );
        cursoRepository.delete(cursoActual);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
