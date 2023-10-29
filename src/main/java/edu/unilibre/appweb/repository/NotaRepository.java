package edu.unilibre.appweb.repository;
import edu.unilibre.appweb.models.NotaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<NotaModel, Integer> {
}
