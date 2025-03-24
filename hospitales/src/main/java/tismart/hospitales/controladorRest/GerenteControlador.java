package tismart.hospitales.controladorRest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tismart.hospitales.modelo.Gerente;
import tismart.hospitales.repositorio.GerenteRepositorio;

@RestController
@RequestMapping("/api/gerentes")
public class GerenteControlador {
	 @Autowired
	    private GerenteRepositorio gerenteRepositorio;
	  
	  @GetMapping
	    public List<Gerente> obtenerTodos() {
	        return gerenteRepositorio.findAll();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Gerente> obtenerPorId(@PathVariable int id) {
	        Optional<Gerente> gerente = gerenteRepositorio.findById(id);
	        return gerente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
}
