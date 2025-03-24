package tismart.hospitales.servicio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tismart.hospitales.dto.HospitalDTO;
import tismart.hospitales.repositorio.HospitalRepositorio;

@Service
public class HospitalServicio {
    
    private final HospitalRepositorio hospitalRepositorio;

    public HospitalServicio(HospitalRepositorio hospitalRepositorio) {
        this.hospitalRepositorio = hospitalRepositorio;
    }

    @Transactional
    public List<HospitalDTO> obtenerHospitales(String nomHospital, String descDistrito, String descCondicion,
                                               String gerente, String sede) {
        List<Object[]> resultado = hospitalRepositorio.listarHospitales(nomHospital, descDistrito, descCondicion, gerente, sede);

        return resultado.stream().map(fila -> new HospitalDTO(
                ((BigDecimal) fila[0]).intValue(), 
                (String) fila[1], 
                (String) fila[2], 
                ((BigDecimal) fila[3]).intValue(), 
                ((BigDecimal) fila[4]).doubleValue(), 
                (String) fila[5], 
                (String) fila[6], 
                (String) fila[7], 
                fila[8] != null ? (Date) fila[8] : null 
        )).collect(Collectors.toList());
    }
    
    @Transactional
    public HospitalDTO obtenerHospitalPorId(int idHospital) {
        Object[] fila = hospitalRepositorio.buscarHospitalPorId(idHospital);
        
        if (fila == null) {
            return null;
        }

        return new HospitalDTO(
            (fila[0] instanceof BigDecimal) ? ((BigDecimal) fila[0]).intValue() : 0, 
            (String) fila[1],  
            (String) fila[2],  
            (fila[3] instanceof BigDecimal) ? ((BigDecimal) fila[3]).intValue() : 0, 
            (fila[4] instanceof BigDecimal) ? ((BigDecimal) fila[4]).doubleValue() : 0.0, 
            (String) fila[5],  
            (String) fila[6],  
            (String) fila[7],  
            (fila[8] instanceof Timestamp) ? new Date(((Timestamp) fila[8]).getTime()) : null
        );
    }
    
    public void registrarHospital(int idDistrito, String nombre, int antiguedad, BigDecimal area, 
    							int idSede, int idGerente, int idCondicion) {
		hospitalRepositorio.registrarHospital(idDistrito, nombre, antiguedad, area, idSede, idGerente, idCondicion);
	}
    
    public void actualizarHospital(int idHospital, int idDistrito, String nombre, int antiguedad, BigDecimal area, 
            int idSede, int idGerente, int idCondicion) {
		try {
			hospitalRepositorio.actualizarHospital(idHospital, idDistrito, nombre, antiguedad, area, idSede, idGerente, idCondicion);
			} catch (Exception e) {
				throw new RuntimeException("Error al actualizar hospital: " + e.getMessage());
			}
		}
    
    public String eliminarHospital(int idHospital) {
        try {
            hospitalRepositorio.eliminarHospital(idHospital);
            return "Hospital eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar el hospital: " + e.getMessage();
        }
    }
    
    

}
