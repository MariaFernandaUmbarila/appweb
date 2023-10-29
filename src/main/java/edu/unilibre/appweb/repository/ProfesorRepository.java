package edu.unilibre.appweb.repository;
import edu.unilibre.appweb.models.ProfesorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<ProfesorModel, Integer> {
}
