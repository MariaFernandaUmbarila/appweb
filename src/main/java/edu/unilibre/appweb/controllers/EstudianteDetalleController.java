package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.exceptions.ResponseError;
import edu.unilibre.appweb.models.EstudianteDetalleModel;
import edu.unilibre.appweb.repository.EstudianteDetalleRepository;
import edu.unilibre.appweb.services.EstudianteDetalleMapping;
import edu.unilibre.appweb.services.EstudianteDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class EstudianteDetalleController {

    @Autowired
    private EstudianteDetalleRepository estudianteDetalleRepository;
    @Autowired
    private EstudianteDetalleService estudianteDetalleService;
    private static final String frontApi = AppConstants.frontApi;

    @CrossOrigin(origins = frontApi)
    @GetMapping("/get_student_summary/{id}")
    public Object getStudentSummary(@PathVariable Integer id){

        List<List<Object>> respuestaLista = estudianteDetalleRepository.findNotasByEstudianteId(id);
        return estudianteDetalleService.mapListToJson(respuestaLista);
    }

    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_grade")
    public Object createGrade(@RequestBody EstudianteDetalleMapping notaDto){

        EstudianteDetalleModel nuevaNota = estudianteDetalleService.mapDtoToModel(notaDto);
        BigDecimal porcentajes = (BigDecimal) estudianteDetalleRepository.findPorcentajesByEstudianteCursoId(nuevaNota.getEstudiante().getId(), nuevaNota.getCodigo().getCodigo());

        if (porcentajes != null){
            Double suma = porcentajes.doubleValue() + notaDto.getPorcentaje().doubleValue();

            if (porcentajes.doubleValue() >= 100.0 || suma > 100.0) {
                List<String> detalles = new ArrayList<>();
                detalles.add(porcentajes.toString());
                return new ResponseError("Los porcentajes suman más de 100 porciento para este curso", detalles);
            }
        }

        estudianteDetalleRepository.save(nuevaNota);
        return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);

    }

    @CrossOrigin(origins = frontApi)
    @PostMapping("/get_grade_details")
    public ResponseEntity<Optional<EstudianteDetalleModel>> getGradeDetails(@RequestBody EstudianteDetalleMapping notaDto){
        Integer notaId = estudianteDetalleRepository.findByDetalle(notaDto.getEstudiante(), notaDto.getCodigo(), notaDto.getPorcentaje());
        if (notaId > 0) {
            Optional<EstudianteDetalleModel> detalle = estudianteDetalleRepository.findById(notaId);
            return ResponseEntity.ok(detalle);
        } else throw new NotFoundException("No fue encontrada la nota a eliminar");
    }

    @CrossOrigin(origins = frontApi)
    @PostMapping("/delete_grade")
    public ResponseEntity<HttpStatus> deleteStudent(@RequestBody EstudianteDetalleMapping notaDto) {

        Integer notaAEliminar = estudianteDetalleRepository.findByDetalle(notaDto.getEstudiante(), notaDto.getCodigo(), notaDto.getPorcentaje());
        if (notaAEliminar > 0) {
            estudianteDetalleRepository.delete(estudianteDetalleRepository.getReferenceById(notaAEliminar));
        } else throw new NotFoundException("No fue encontrada la nota a eliminar");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @CrossOrigin(origins = frontApi)
    @PutMapping("/update_grade/{stuId}/{cursCod}/{porcen}")
    public Object updateGrade(@PathVariable Integer stuId, @PathVariable String cursCod, @PathVariable Double porcen, @RequestBody EstudianteDetalleMapping notaDto) {

        Integer notaId = estudianteDetalleRepository.findByDetalle(stuId, cursCod, porcen);
        if (notaId > 0) {
            EstudianteDetalleModel detalle = estudianteDetalleRepository.getReferenceById(notaId);
            BigDecimal porcentajes = (BigDecimal) estudianteDetalleRepository.findPorcentajesByEstudianteCursoId(detalle.getEstudiante().getId(), detalle.getCodigo().getCodigo());
            Double suma = porcentajes.doubleValue() + notaDto.getPorcentaje().doubleValue();

            if (porcentajes.doubleValue() >= 100.0 || suma > 100.0) {
                List<String> detalles = new ArrayList<>();
                detalles.add(porcentajes.toString());
                return new ResponseError("Los porcentajes suman más de 100 porciento para este curso", detalles);
            }else{
                detalle.setValor(notaDto.getValor());
                detalle.setObservacion(notaDto.getObservacion());
                detalle.setPorcentaje(notaDto.getPorcentaje());
                return estudianteDetalleRepository.save(detalle);
            }
        } else throw new NotFoundException("No fue encontrada la nota a actualizar");

    }
}