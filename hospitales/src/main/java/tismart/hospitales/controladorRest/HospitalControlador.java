package tismart.hospitales.controladorRest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tismart.hospitales.dto.HospitalActualizarDTO;
import tismart.hospitales.dto.HospitalDTO;
import tismart.hospitales.dto.HospitalRegistroDTO;
import tismart.hospitales.servicio.HospitalServicio;

@RestController
@RequestMapping("/api/hospitales")
public class HospitalControlador {
	 
	 private final HospitalServicio hospitalService;

	    public HospitalControlador(HospitalServicio hospitalService) {
	        this.hospitalService = hospitalService;
	    }
	    @GetMapping(value = "/listar", produces = "application/json")
	    public ResponseEntity<List<HospitalDTO>> listarHospitales(
	        @RequestParam(defaultValue = "") String nomHospital,
	        @RequestParam(defaultValue = "") String descDistrito,
	        @RequestParam(defaultValue = "") String descCondicion,
	        @RequestParam(defaultValue = "") String gerente,
	        @RequestParam(defaultValue = "") String sede) {

	        try {
	            List<HospitalDTO> hospitales = hospitalService.obtenerHospitales(nomHospital, descDistrito, descCondicion, gerente, sede);
	            
	            if (hospitales.isEmpty()) {
	                return ResponseEntity.noContent().build();
	            }
	            
	            return ResponseEntity.ok(hospitales);
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().body(null);
	        }
	    }
	    
	    @GetMapping(value = "/listar/{idHospital}", produces = "application/json")
	    public ResponseEntity<HospitalDTO> listarHospitalPorId(@PathVariable int idHospital) {
	        try {
	            HospitalDTO hospital = hospitalService.obtenerHospitalPorId(idHospital);
	            
	            if (hospital == null) {
	                return ResponseEntity.notFound().build(); 
	            }
	            
	            return ResponseEntity.ok(hospital);
	        } catch (Exception e) {
	            return ResponseEntity.internalServerError().build(); 
	        }
	    }
	    
	    @PostMapping("/registrar")
	    public ResponseEntity<Map<String, Object>> registrarHospital(@RequestBody HospitalRegistroDTO hospitalRegistroDTO) {
	        Map<String, Object> response = new HashMap<>();
	        
	        try {
	            hospitalService.registrarHospital(
	                hospitalRegistroDTO.getIdDistrito(),
	                hospitalRegistroDTO.getNombre(),
	                hospitalRegistroDTO.getAntiguedad(),
	                hospitalRegistroDTO.getArea(),
	                hospitalRegistroDTO.getIdSede(),
	                hospitalRegistroDTO.getIdGerente(),
	                hospitalRegistroDTO.getIdCondicion()
	            );

	            response.put("result", "pass"); 
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.put("result", "fail");
	            response.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }
	    
	    @PutMapping("/actualizar/{idHospital}")
	    public ResponseEntity<Map<String, Object>> actualizarHospital(
	            @PathVariable int idHospital,
	            @RequestBody HospitalActualizarDTO hospitalActualizarDTO) {
	        
	        Map<String, Object> response = new HashMap<>();
	        
	        try {
	            hospitalService.actualizarHospital(
	                idHospital,
	                hospitalActualizarDTO.getIdDistrito(),
	                hospitalActualizarDTO.getNombre(),
	                hospitalActualizarDTO.getAntiguedad(),
	                hospitalActualizarDTO.getArea(),
	                hospitalActualizarDTO.getIdSede(),
	                hospitalActualizarDTO.getIdGerente(),
	                hospitalActualizarDTO.getIdCondicion()
	            );

	            response.put("result", "pass");
	            response.put("message", "Hospital actualizado correctamente.");
	            return ResponseEntity.ok(response);

	        } catch (Exception e) {
	            response.put("result", "fail");
	            response.put("error", e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }
	    
	    @DeleteMapping("/eliminar/{id}")
	    public ResponseEntity<Map<String, String>> eliminarHospital(@PathVariable int id) {
	        try {
	            hospitalService.eliminarHospital(id);
	            Map<String, String> response = new HashMap<>();
	            response.put("message", "Hospital eliminado correctamente.");
	            return ResponseEntity.ok(response); 
	        } catch (Exception e) {
	            Map<String, String> errorResponse = new HashMap<>();
	            errorResponse.put("error", "Error al eliminar el hospital: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	        }
	    }



}
