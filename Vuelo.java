package tpeprog3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Vuelo {
	private int kilometros;
	private boolean escabotaje;
	private Map<String, Integer> aerolineasyasientos = new HashMap<>();
	private Aeropuerto aeropuertoorigen;
	private Aeropuerto aeropuertoalcanzado;
	private ArrayList<Reserva> reservas = new ArrayList<>();

	public Vuelo(Aeropuerto ao, Aeropuerto aa, int km) {
		this.aeropuertoorigen = ao;
		this.aeropuertoalcanzado = aa;
		this.kilometros = km;
	}

	public Vuelo() {

	}

	public ArrayList<Reserva> getReservas() {
		return reservas;
	}

	public void setReserva(Reserva reserva) {
		this.reservas.add(reserva);
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

	public Set<String> getAerolineas() {
		return this.aerolineasyasientos.keySet();
	}

	public ArrayList<String> getAerolineasDisponibles() {
		ArrayList<String> resultado = new ArrayList<>();
		for(int i = 0; i < this.reservas.size(); i++) {
			for (String key : this.aerolineasyasientos.keySet()) {
				if(this.reservas.get(i).getAerolinea().equals(key)) {
					if(this.aerolineasyasientos.get(key) - this.reservas.get(i).getAsientosReservados() > 0) {
						resultado.add(key);
					}
				}
			}
		}
		return resultado;
	}

	public Collection<Integer> getAsientosEnTotal() {
		return this.aerolineasyasientos.values();
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

	public int getAsientosDisponibles(String aerolinea) {
		int resultado = 0;
		for(int i = 0; i < this.reservas.size(); i++) {
			if(this.reservas.get(i).getAerolinea().equals(aerolinea)) {
				if(this.aerolineasyasientos.get(aerolinea) - this.reservas.get(i).getAsientosReservados() > 0) {
					resultado = this.aerolineasyasientos.get(aerolinea) - this.reservas.get(i).getAsientosReservados();
				}
			}
		}
		return resultado;
	}

	public void setAerolineaYasientos(String a, Integer as) {
		this.aerolineasyasientos.put(a, as);
	}
}
