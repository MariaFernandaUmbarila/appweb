package edu.unilibre.appweb.repository;
import edu.unilibre.appweb.models.EstudianteDetalleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstudianteDetalleRepository extends JpaRepository<EstudianteDetalleModel, Integer> {
    @Query(value = "select en.curso_codigo as codigo, ma.nombre as nombre, sum(en.valor * (en.porcentaje / 100)) as valor, CONCAT(es.nombre, ' ', es.apellido) as estudiante from estudiante_detalle en inner join curso cu on(en.curso_codigo = cu.codigo) inner join materia ma on(cu.materia_id = ma.id) inner join estudiante es on(en.estudiante_id = es.id) where estudiante_id = :estudianteId group by en.curso_codigo, ma.nombre, es.nombre, es.apellido", nativeQuery = true)
    List<List<Object>> findNotasByEstudianteId(@Param("estudianteId") Integer estudianteId);

    @Query(value = "select sum(en.porcentaje) as porcentajes from estudiante_detalle en where estudiante_id = :estudianteId group by en.curso_codigo", nativeQuery = true)
    List<Object> findPorcentajesByEstudianteId(@Param("estudianteId") Integer estudianteId);

    @Query(value = "select sum(en.porcentaje) as porcentajes from estudiante_detalle en where estudiante_id = :estudianteId and en.curso_codigo = :cursoId", nativeQuery = true)
    Object findPorcentajesByEstudianteCursoId(@Param("estudianteId") Integer estudianteId, @Param("cursoId") String cursoId);

    @Query(value = "select en.id from estudiante_detalle en where estudiante_id = :estudianteId and en.curso_codigo = :cursoId and en.porcentaje = :porcentaje LIMIT 1", nativeQuery = true)
    Integer findByDetalle(@Param("estudianteId") Integer estudianteId, @Param("cursoId") String cursoId, @Param("porcentaje") Double porcentaje);

}
