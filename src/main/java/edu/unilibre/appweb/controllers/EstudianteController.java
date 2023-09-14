package edu.unilibre.appweb.controllers;
import edu.unilibre.appweb.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRespository;

}
