package edu.unilibre.appweb.repository;

import edu.unilibre.appweb.models.EstudianteDetalleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstudianteDetalleRepository extends JpaRepository<EstudianteDetalleModel, Integer> {
    @Query(value = "select en.curso_codigo as codigo, ma.nombre as nombre, sum(en.valor * en.porcentaje) as valor from estudiante_detalle en inner join curso cu on(en.curso_codigo = cu.codigo) inner join materia ma on(cu.materia_id = ma.id) where estudiante_id = 1 group by en.curso_codigo, ma.nombre", nativeQuery = true)
    List<List<Object>> findNotasByEstudianteId(@Param("estudianteId") Integer estudianteId);

    @Query(value = "select sum(en.porcentaje) as porcentajes from estudiante_detalle en where estudiante_id = 1 group by en.curso_codigo", nativeQuery = true)
    List<List<Object>> findPorcentajesByEstudianteId(@Param("estudianteId") Integer estudianteId);
}
