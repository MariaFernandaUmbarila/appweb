package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.constants.AppConstants;
import edu.unilibre.appweb.models.EstudianteDetalleMapping;
import edu.unilibre.appweb.models.EstudianteDetalleModel;
import edu.unilibre.appweb.repository.EstudianteDetalleRepository;
import edu.unilibre.appweb.services.EstudianteNotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class NotaController {

    @Autowired
    private EstudianteDetalleRepository notaRepository;
    @Autowired
    private EstudianteNotaService notaService;
    private static final String frontApi = AppConstants.frontApi;

    @CrossOrigin(origins = frontApi)
    @PostMapping("/create_grade")
    public ResponseEntity<EstudianteDetalleModel> createGrade(@RequestBody EstudianteDetalleMapping notaDto){
        EstudianteDetalleModel nuevaNota = notaRepository.save(notaService.mapDtoToModel(notaDto));
        return new ResponseEntity<>(nuevaNota, HttpStatus.CREATED);
    }

}
