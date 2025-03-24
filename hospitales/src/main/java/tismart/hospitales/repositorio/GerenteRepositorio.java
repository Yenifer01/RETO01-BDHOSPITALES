package tismart.hospitales.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import  tismart.hospitales.modelo.Gerente;

public interface GerenteRepositorio extends JpaRepository<Gerente, Integer> {

}
