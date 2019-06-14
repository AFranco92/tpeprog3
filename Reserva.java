package tpe;

public class Reserva {
	private Aeropuerto aeropuertoorigen;
	private Aeropuerto aeropuertodestino;
	private String aerolinea;
	private int asientosreservados;
	
	public Reserva(Aeropuerto ao, Aeropuerto ad, String aerolinea, int asientosreservados) {
		this.aeropuertoorigen = ao;
		this.aeropuertodestino = ad;
		this.aerolinea = aerolinea;
		this.asientosreservados = asientosreservados;
	}
	
	public Aeropuerto getAeropuertoorigen() {
		return aeropuertoorigen;
	}

	public void setAeropuertoorigen(Aeropuerto aeropuertoorigen) {
		this.aeropuertoorigen = aeropuertoorigen;
	}

	public Aeropuerto getAeropuertodestino() {
		return aeropuertodestino;
	}

	public void setAeropuertodestino(Aeropuerto aeropuertodestino) {
		this.aeropuertodestino = aeropuertodestino;
	}

	public int getAsientosreservados() {
		return asientosreservados;
	}

	public void setAsientosreservados(int asientosreservados) {
		this.asientosreservados = asientosreservados;
	}

	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}

	public String getAerolinea() {
		return this.aerolinea;
	}
	
	public int getAsientosReservados() {
		return this.asientosreservados;
	}
	
}
