package edu.unilibre.appweb.services;
import edu.unilibre.appweb.exceptions.NotFoundException;
import edu.unilibre.appweb.models.CursoModel;
import edu.unilibre.appweb.models.MateriaModel;
import edu.unilibre.appweb.models.ProfesorModel;
import edu.unilibre.appweb.repository.MateriaRepository;
import edu.unilibre.appweb.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    public CursoModel mapDtoToModel(CursoMapping cursoMapping){

        CursoModel nuevoCurso = new CursoModel();

        MateriaModel materia = materiaRepository.findById(cursoMapping.getMateria()).orElseThrow(
                () -> new NotFoundException("No existe el estudiante con id: " + cursoMapping.getMateria()));
        nuevoCurso.setMateriaModel(materia);

        ProfesorModel profesor = profesorRepository.findById(cursoMapping.getProfesor()).orElseThrow(
                () -> new NotFoundException("No existe el profesor con id: " + cursoMapping.getProfesor()));
        nuevoCurso.setProfesorModel(profesor);

        if (cursoMapping.getCodigo() != null){
            nuevoCurso.setCodigo(cursoMapping.getCodigo());
        } else throw new NotFoundException("Campo de código vacío");

        if (cursoMapping.getFechaInicio() != null){
            nuevoCurso.setFechaFin(cursoMapping.getFechaInicio());
        } else throw new NotFoundException("Campo de fecha de inicio vacío");

        if (cursoMapping.getFechaFin() != null){
            nuevoCurso.setFechaFin(cursoMapping.getFechaFin());
        } else throw new NotFoundException("Campo de fecha de finalización vacío");

        return nuevoCurso;
    }
}
