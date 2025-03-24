package tismart.hospitales.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;


import tismart.hospitales.modelo.Sede;

public interface SedeRepositorio  extends JpaRepository<Sede, Integer> {

}
