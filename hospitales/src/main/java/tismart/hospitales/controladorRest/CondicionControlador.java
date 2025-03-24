package tismart.hospitales.controladorRest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tismart.hospitales.modelo.Condicion;
import tismart.hospitales.repositorio.CondicionRepositorio;

@RestController
@RequestMapping("/api/condiciones")
public class CondicionControlador {
	
	 @Autowired
	    private CondicionRepositorio condicionRepositorio;
	  
	  @GetMapping
	    public List<Condicion> obtenerTodos() {
	        return condicionRepositorio.findAll();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Condicion> obtenerPorId(@PathVariable int id) {
	        Optional<Condicion> condicion = condicionRepositorio.findById(id);
	        return condicion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }

}
