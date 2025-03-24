package tismart.hospitales.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import tismart.hospitales.modelo.Condicion;

public interface CondicionRepositorio extends JpaRepository<Condicion, Integer> {

}
