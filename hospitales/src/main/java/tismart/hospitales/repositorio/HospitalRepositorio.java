package tismart.hospitales.repositorio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.StoredProcedureQuery;

@Repository
public class HospitalRepositorio {
	
	@PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Object[]> listarHospitales(String nomHospital, String descDistrito, String descCondicion, 
                                           String gerente, String sede) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SP_HOSPITAL_LISTAR");

        query.registerStoredProcedureParameter("p_NomHospital", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_descDistrito", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_descCondicion", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_gerente", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_sede", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_result", void.class, ParameterMode.REF_CURSOR); 

        query.setParameter("p_NomHospital", (nomHospital != null && !nomHospital.isEmpty()) ? nomHospital : null);
        query.setParameter("p_descDistrito", (descDistrito != null && !descDistrito.isEmpty()) ? descDistrito : null);
        query.setParameter("p_descCondicion", (descCondicion != null && !descCondicion.isEmpty()) ? descCondicion : null);
        query.setParameter("p_gerente", (gerente != null && !gerente.isEmpty()) ? gerente : null);
        query.setParameter("p_sede", (sede != null && !sede.isEmpty()) ? sede : null);

        query.execute();

        return query.getResultList();
    }
    public Object[] buscarHospitalPorId(int idHospital) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SP_HOSPITAL_LISTAR_POR_ID");

        query.registerStoredProcedureParameter("p_idHospital", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_result", void.class, ParameterMode.REF_CURSOR); 
        query.setParameter("p_idHospital", idHospital);
        query.execute();
        Object resultado = query.getSingleResult(); 
        return (Object[]) resultado; 
    }
    
    public void registrarHospital(int idDistrito, String nombre, int antiguedad, BigDecimal area, 
        int idSede, int idGerente, int idCondicion) {
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SP_HOSPITAL_REGISTRAR");
		
		query.registerStoredProcedureParameter("p_idDistrito", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_Nombre", String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_Antiguedad", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_Area", BigDecimal.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_idSede", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_idGerente", Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter("p_idCondicion", Integer.class, ParameterMode.IN);
		
		query.setParameter("p_idDistrito", idDistrito);
		query.setParameter("p_Nombre", nombre);
		query.setParameter("p_Antiguedad", antiguedad);
		query.setParameter("p_Area", area);
		query.setParameter("p_idSede", idSede);
		query.setParameter("p_idGerente", idGerente);
		query.setParameter("p_idCondicion", idCondicion);
		
		query.execute();
		}
    
    public void actualizarHospital(int idHospital, int idDistrito, String nombre, int antiguedad, BigDecimal area, 
            int idSede, int idGerente, int idCondicion) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SP_HOSPITAL_ACTUALIZAR");

            query.registerStoredProcedureParameter("p_idHospital", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_idDistrito", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_Nombre", String.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_Antiguedad", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_Area", BigDecimal.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_idSede", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_idGerente", Integer.class, ParameterMode.IN);
            query.registerStoredProcedureParameter("p_idCondicion", Integer.class, ParameterMode.IN);

            query.setParameter("p_idHospital", idHospital);
            query.setParameter("p_idDistrito", idDistrito);
            query.setParameter("p_Nombre", nombre);
            query.setParameter("p_Antiguedad", antiguedad);
            query.setParameter("p_Area", area);
            query.setParameter("p_idSede", idSede);
            query.setParameter("p_idGerente", idGerente);
            query.setParameter("p_idCondicion", idCondicion);

            query.execute();
            System.out.println("Hospital actualizado correctamente.");
        } catch (PersistenceException e) {
            if (e.getMessage().contains("No se ha realizado ningún cambio en los datos")) {
                System.out.println("No se realizó ninguna actualización.");
            } else {
                System.out.println("Error al actualizar el hospital: " + e.getMessage());
            }
        }
    }
    
    public void eliminarHospital(int idHospital) {
        try {
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("SP_HOSPITAL_ELIMINAR");

            query.registerStoredProcedureParameter("p_idHospital", Integer.class, ParameterMode.IN);
            query.setParameter("p_idHospital", idHospital);

            query.execute();

            System.out.println("Hospital eliminado correctamente.");
        } catch (PersistenceException e) {
            if (e.getMessage().contains("No se encontró el hospital")) {
                System.out.println("Error: El hospital con ID " + idHospital + " no existe.");
            } else {
                System.out.println("Error al eliminar el hospital: " + e.getMessage());
            }
        }
    }




}
