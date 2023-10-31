package edu.unilibre.appweb.services;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.*;
import edu.unilibre.appweb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstudianteNotaService {

    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public EstudianteDetalleModel mapDtoToModel(EstudianteDetalleMapping notaDto) {

        EstudianteDetalleModel nuevaNota = new EstudianteDetalleModel();

        EstudianteModel estudiante = estudianteRepository.findById(notaDto.getEstudiante()).orElseThrow(
                () -> new NotFoundException("No existe el estudiante con id: " + notaDto.getEstudiante()));
        nuevaNota.setEstudiante(estudiante);

        CursoModel curso = cursoRepository.findByCodigo(notaDto.getCodigo()).orElseThrow(
                () -> new NotFoundException("No existe curso con id: " + notaDto.getCodigo()));
        nuevaNota.setCodigo(curso);

        if (notaDto.getObservacion() != null) {
            nuevaNota.setObservacion(notaDto.getObservacion());
        } else throw new NotFoundException("Campo de observación vacío");

        if (notaDto.getPorcentaje() != null) {
            nuevaNota.setPorcentaje(notaDto.getPorcentaje());
        } else throw new NotFoundException("Campo de porcentaje vacío");

        if (notaDto.getValor() != null) {
            nuevaNota.setValor(notaDto.getValor());
        } else throw new NotFoundException("Campo de porcentaje vacío");

        return nuevaNota;

    }
}
