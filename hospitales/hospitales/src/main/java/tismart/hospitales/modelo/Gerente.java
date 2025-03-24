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
@Table(name="GERENTE")
@JsonPropertyOrder({"idgerente","descgerente", "fecharegistro"})
public class Gerente {
	@Id
	@Column(name = "IDGERENTE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idgerente;
	
	@Column(name = "DESCGERENTE", nullable = false, length = 100) 
	private String descgerente;

	@Column(name = "FECHAREGISTRO", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecharegistro;
	
	public Gerente() {
		super();
	}

	public int getIdgerente() {
		return idgerente;
	}

	public void setIdgerente(int idgerente) {
		this.idgerente = idgerente;
	}

	public String getDescgerente() {
		return descgerente;
	}

	public void setDescgerente(String descgerente) {
		this.descgerente = descgerente;
	}

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	

	
	
	
}
