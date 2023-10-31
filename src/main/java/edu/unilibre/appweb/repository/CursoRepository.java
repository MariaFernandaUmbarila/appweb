package edu.unilibre.appweb.repository;
import edu.unilibre.appweb.models.CursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<CursoModel, Integer> {
    Optional<CursoModel> findByCodigo(String codigo);
}
