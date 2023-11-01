package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.exceptions.ResponseError;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
    public Object getStudentSummary(@PathVariable Integer id){

        List<List<Object>> porcentajes = estudianteDetalleRepository.findPorcentajesByEstudianteId(id);
        List<List<Object>> respuestaLista = estudianteDetalleRepository.findNotasByEstudianteId(id);

        for (int i=0; i < porcentajes.size(); i++) {
            List<Object> item = porcentajes.get(i);
            BigDecimal sumaPorcentaje = (BigDecimal) item.get(i);
            if (sumaPorcentaje.doubleValue() != 100.0) {
                List<String> detalles = new ArrayList<>();
                detalles.add("Porcentaje suma: " + item.get(i));
                respuestaLista.remove(i);
                return new ResponseError("Se omitió un registro en el resumen", detalles);
            }else{
                for (List<Object> itemL : respuestaLista) {
                    BigDecimal valorCrudo = (BigDecimal) itemL.get(2);
                    Double valorRedondeado = valorCrudo.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    itemL.set(2, valorRedondeado);
                }
            }
        }

        return estudianteDetalleService.mapListToJson(respuestaLista);
    }

    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_grade")
    public Object createGrade(@RequestBody EstudianteDetalleMapping notaDto){

        EstudianteDetalleModel nuevaNota = estudianteDetalleService.mapDtoToModel(notaDto);
        BigDecimal porcentajes = (BigDecimal) estudianteDetalleRepository.findPorcentajesByEstudianteCursoId(nuevaNota.getEstudiante().getId(), nuevaNota.getCodigo().getCodigo());

        if (porcentajes.doubleValue() >= 100.0) {
            List<String> detalles = new ArrayList<>();
            detalles.add(porcentajes.toString());
            return new ResponseError("Los porcentajes suman más de 100 porciento para este curso", detalles);
        }

        Double suma = porcentajes.doubleValue() + notaDto.getPorcentaje().doubleValue();

        if (suma > 100.0){
            List<String> detalles = new ArrayList<>();
            detalles.add(porcentajes.toString());
            return new ResponseError("Los porcentajes suman más de 100 porciento para este curso", detalles);
        }

        estudianteDetalleRepository.save(nuevaNota);
        return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);

    }

}
