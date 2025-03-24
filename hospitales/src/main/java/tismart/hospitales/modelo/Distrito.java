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
@Table(name="DISTRITO")
@JsonPropertyOrder({"iddistrito", "idprovincia", "descdistrito", "fecharegistro"})
public class Distrito {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDDISTRITO")
	private int iddistrito;
	
	@Column(name = "IDPROVINCIA", nullable = false)
	private int idprovincia;
	
	@Column(name = "DESCDISTRITO", nullable = false, length = 100) 
	private String descdistrito;

	@Column(name = "FECHAREGISTRO", updatable = false, insertable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecharegistro;

	public Distrito() {
		super();
	}

	public int getIddistrito() {
		return iddistrito;
	}

	public void setIddistrito(int iddistrito) {
		this.iddistrito = iddistrito;
	}

	public int getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(int idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getDescdistrito() {
		return descdistrito;
	}

	public void setDescdistrito(String descdistrito) {
		this.descdistrito = descdistrito;
	}

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

	

}
