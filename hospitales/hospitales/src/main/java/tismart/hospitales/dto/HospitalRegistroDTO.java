package tismart.hospitales.dto;

import java.math.BigDecimal;

public class HospitalRegistroDTO {
	
	private Integer idDistrito;
    private String nombre;
    private Integer antiguedad;
    private BigDecimal area;
    private Integer idSede;
    private Integer idGerente;
    private Integer idCondicion;

    public HospitalRegistroDTO() {
    }

    public HospitalRegistroDTO(Integer idDistrito, String nombre, Integer antiguedad, BigDecimal area,
                               Integer idSede, Integer idGerente, Integer idCondicion) {
        this.idDistrito = idDistrito;
        this.nombre = nombre;
        this.antiguedad = antiguedad;
        this.area = area;
        this.idSede = idSede;
        this.idGerente = idGerente;
        this.idCondicion = idCondicion;
    }

	public Integer getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(Integer idDistrito) {
		this.idDistrito = idDistrito;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(Integer antiguedad) {
		this.antiguedad = antiguedad;
	}


	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public Integer getIdSede() {
		return idSede;
	}

	public void setIdSede(Integer idSede) {
		this.idSede = idSede;
	}

	public Integer getIdGerente() {
		return idGerente;
	}

	public void setIdGerente(Integer idGerente) {
		this.idGerente = idGerente;
	}

	public Integer getIdCondicion() {
		return idCondicion;
	}

	public void setIdCondicion(Integer idCondicion) {
		this.idCondicion = idCondicion;
	}
    

}
