package tismart.hospitales.dto;

public class HospitalEliminarDTO {
	private String mensaje;

   
    public HospitalEliminarDTO() {
		super();
	}

	public HospitalEliminarDTO(String mensaje) {
		this.mensaje = mensaje;
	}


	public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
	
	
	
}
