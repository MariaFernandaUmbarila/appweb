package edu.unilibre.appweb.repository;
import edu.unilibre.appweb.models.EstudianteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<EstudianteModel, Integer> {
}
