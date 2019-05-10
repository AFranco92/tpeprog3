package tpe;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Vuelo {
	private int kilometros;
	private boolean escabotaje;
	private ArrayList<String> aerolineas = new ArrayList<>();
	private Map<String, Integer> aerolineasyasientos = new HashMap<>();
	private Aeropuerto aeropuertoorigen;
	private Aeropuerto aeropuertoalcanzado;
	
	public Vuelo(Aeropuerto ao, Aeropuerto aa, int km) {
		this.aeropuertoorigen = ao;
		this.aeropuertoalcanzado = aa;
		this.kilometros = km;
	}
	
	public int getKilometros() {
		return this.kilometros;
	}
	public void setKilometros(int kilometros) {
		this.kilometros = kilometros;
	}
	public boolean esCabotaje(Aeropuerto ad) {
		return this.aeropuertoorigen.tieneAdyacenteA(ad);
	}
	public void setEsCabotaje(boolean escabotaje) {
		this.escabotaje = escabotaje;
	}
	public Aeropuerto getAeropuertoOrigen() {
		return this.aeropuertoorigen;
	}
	public void setAeropuertoOrigen(Aeropuerto aeropuertoorigen) {
		this.aeropuertoorigen = aeropuertoorigen;
	}
	public Aeropuerto getAeropuertoAlcanzado() {
		return this.aeropuertoalcanzado;
	}
	public void setAeropuertoAlcanzado(Aeropuerto aeropuertoalcanzado) {
		this.aeropuertoalcanzado = aeropuertoalcanzado;
	}
	
	public ArrayList<String> getAerolineas() {
		return this.aerolineas;
	}
	
	public void setAerolinea(String a) {
		this.aerolineas.add(a);
	}
	
	public Map<String, Integer> getAerolineasYAsientos() {
		return this.aerolineasyasientos;
	}
	
	public boolean contieneAerolinea(String a) {
		return this.aerolineasyasientos.containsKey(a);
	}
	
	public int getAsientos(String a) {
		return this.aerolineasyasientos.get(a);
	}
	
	public void setAerolineaYasientos(String a, Integer as) {
		this.aerolineasyasientos.put(a, as);
	}
}
