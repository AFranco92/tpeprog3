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
	
	public String getAerolinea() {
		return this.aerolinea;
	}
	
	public int getAsientosReservados() {
		return this.asientosreservados;
	}
}
