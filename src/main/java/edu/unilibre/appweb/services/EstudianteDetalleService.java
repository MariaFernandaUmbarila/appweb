package edu.unilibre.appweb.services;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.CursoModel;
import edu.unilibre.appweb.models.EstudianteDetalleModel;
import edu.unilibre.appweb.models.EstudianteModel;
import edu.unilibre.appweb.repository.CursoRepository;
import edu.unilibre.appweb.repository.EstudianteRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstudianteDetalleService {

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

    public String mapListToJson(List<List<Object>>  dataList){
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < dataList.size(); i++) {
            List<Object> item = dataList.get(i);
            if (item.size() >= 3) {
                JSONObject innerJsonObject = new JSONObject();
                innerJsonObject.put("codigo", item.get(0));
                innerJsonObject.put("nombre", item.get(1));
                innerJsonObject.put("valor", item.get(2));
                jsonObject.put("item" + (i + 1), innerJsonObject);
            }
        }
        return jsonObject.toJSONString();

    }
}
