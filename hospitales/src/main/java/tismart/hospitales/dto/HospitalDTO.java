package tismart.hospitales.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HospitalDTO {
	private Integer idHospital;
    private String descDistrito;
    private String nombre;
    private Integer antiguedad;
    private Double area;
    private String descSede;
    private String descGerente;
    private String descCondicion;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yy")
    private Date fechaRegistro;

    public HospitalDTO(int idHospital, String descDistrito, String nombre, int antiguedad, double area, 
                       String descSede, String descGerente, String descCondicion, Date fechaRegistro) {
        this.idHospital = idHospital;
        this.descDistrito = descDistrito;
        this.nombre = nombre;
        this.antiguedad = antiguedad;
        this.area = area;
        this.descSede = descSede;
        this.descGerente = descGerente;
        this.descCondicion = descCondicion;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    public String getDescDistrito() {
        return descDistrito;
    }

    public void setDescDistrito(String descDistrito) {
        this.descDistrito = descDistrito;
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getDescSede() {
        return descSede;
    }

    public void setDescSede(String descSede) {
        this.descSede = descSede;
    }

    public String getDescGerente() {
        return descGerente;
    }

    public void setDescGerente(String descGerente) {
        this.descGerente = descGerente;
    }

    public String getDescCondicion() {
        return descCondicion;
    }

    public void setDescCondicion(String descCondicion) {
        this.descCondicion = descCondicion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
