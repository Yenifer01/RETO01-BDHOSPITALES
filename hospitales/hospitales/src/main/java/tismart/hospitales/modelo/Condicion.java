package tismart.hospitales.modelo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="CONDICION")
@JsonPropertyOrder({"idcondicion","desccondicion", "fecharegistro"})
public class Condicion {
	@Id
	@Column(name = "IDCONDICION")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idcondicion;
	
	@Column(name = "DESCCONDICION", nullable = false, length = 100) 
	private String desccondicion;

	@Column(name = "FECHAREGISTRO", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecharegistro;
	
	public Condicion() {
		super();
	}

	public int getIdcondicion() {
		return idcondicion;
	}

	public void setIdcondicion(int idcondicion) {
		this.idcondicion = idcondicion;
	}

	public String getDesccondicion() {
		return desccondicion;
	}

	public void setDesccondicion(String desccondicion) {
		this.desccondicion = desccondicion;
	}

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}
		
	
	
	

	
	

}
