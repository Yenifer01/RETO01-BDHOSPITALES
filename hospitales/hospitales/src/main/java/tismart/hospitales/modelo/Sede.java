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
@Table(name = "SEDE", schema = "SYSTEM")
@JsonPropertyOrder({"idsede", "descsede", "fecharegistro"})
public class Sede {
	@Id
	@Column(name = "IDSEDE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idsede;
	
	@Column(name = "DESCSEDE", nullable = false, length = 100) 
	private String descsede;

	@Column(name = "FECHAREGISTRO", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecharegistro;

	public Sede() {
		super();
	}

	public int getIdsede() {
		return idsede;
	}

	public void setIdsede(int idsede) {
		this.idsede = idsede;
	}

	public String getDescsede() {
		return descsede;
	}

	public void setDescsede(String descsede) {
		this.descsede = descsede;
	}

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	

	
}
