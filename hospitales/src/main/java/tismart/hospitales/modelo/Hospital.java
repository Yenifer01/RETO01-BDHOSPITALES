package tismart.hospitales.modelo;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "HOSPITAL")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDHOSPITAL")
    private int idHospital;

    @ManyToOne
    @JoinColumn(name = "IDDISTRITO")
    private Distrito distrito;

    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;

    @Column(name = "ANTIGUEDAD", nullable = false)
    private int antiguedad;

    @Column(name = "IDAREA", precision = 5, scale = 2) // 10 dígitos en total, 2 decimales
    private BigDecimal area;


    @ManyToOne
    @JoinColumn(name = "IDSEDE", nullable = false)
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "IDGERENTE", nullable = false)
    private Gerente gerente;

    @ManyToOne
    @JoinColumn(name = "IDCONDICION", nullable = false)
    private Condicion condicion;

    @Column(name = "FECHAREGISTRO", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegistro;

    public Hospital() {
        super();
    }

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public Condicion getCondicion() {
        return condicion;
    }

    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }

	public Date getFecharegistro() {
		return fecharegistro;
	}

	public void setFecharegistro(Date fecharegistro) {
		this.fecharegistro = fecharegistro;
	}

    
}
