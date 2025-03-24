package tismart.hospitales.controladorRest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tismart.hospitales.modelo.Distrito;
import tismart.hospitales.repositorio.DistritoRepositorio;

@RestController
@RequestMapping("/api/distritos")
public class DistritoControlador {
	 @Autowired
	    private DistritoRepositorio distritoRepositorio;
	  
	  @GetMapping
	    public List<Distrito> obtenerTodos() {
	        return distritoRepositorio.findAll();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Distrito> obtenerPorId(@PathVariable int id) {
	        Optional<Distrito> distrito = distritoRepositorio.findById(id);
	        return distrito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

}
