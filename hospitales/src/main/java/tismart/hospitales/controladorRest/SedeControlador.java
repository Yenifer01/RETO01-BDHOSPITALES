package tismart.hospitales.controladorRest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tismart.hospitales.modelo.Sede;
import tismart.hospitales.repositorio.SedeRepositorio;

@RestController
@RequestMapping("/api/sedes")
public class SedeControlador {
	@Autowired
    private SedeRepositorio sedeRepositorio;
  
	@GetMapping
    public List<Sede> obtenerTodos() {
        return sedeRepositorio.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sede> obtenerPorId(@PathVariable int id) {
        Optional<Sede> sede = sedeRepositorio.findById(id);
        return sede.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
