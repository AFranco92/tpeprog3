package tpe;

import java.util.ArrayList;
import java.util.LinkedList;

import tp3.Vertice;

public class GrafoVuelos {
	private ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
	
	public ArrayList<Aeropuerto> getVertices() {
		return this.aeropuertos;
	}
	
	public void setAeropuerto(Aeropuerto a) {
		this.aeropuertos.add(a);
	}
	
	private boolean esDirecto(Aeropuerto ao, Aeropuerto ad, String aerolinea) {
		if(ao.contieneAerolinea(aerolinea) && ao.tieneAdyacenteA(ad)) {
			return true;
		}
		return false;
	}
	
	public LinkedList<Object> servicio1(Aeropuerto ao, Aeropuerto ad, String aerolinea, Vuelo v) {
		LinkedList<Object> resultado = new LinkedList<>();
		if ((v.getAeropuertoOrigen().equals(ao) && v.getAeropuertoAlcanzado().equals(ad)) && this.esDirecto(ao, ad, aerolinea)) {
			resultado.add(v.getKilometros());
			resultado.add(v.getAsientos(aerolinea));
		}
		return resultado;
	}
	
	public LinkedList<Object> servicio2(Aeropuerto ao, Aeropuerto ad) {
		LinkedList<Object> resultado = new LinkedList<>();
		resultado.add(this.vuelosDisponibles(ao, ad));
		return resultado;
	}
	
	private ArrayList<Vuelo> vuelosDisponibles(Aeropuerto ao, Aeropuerto ad) {
		ArrayList<Vuelo> vuelosdisponibles = new ArrayList<>();
		
	}
	
	private void DFS(Vuelo v) {
		for(Aeropuerto a : this.aeropuertos) {
			a.setColor("Blanco");
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getColor() == "Blanco") {
				this.DFSVisit(a, v);
			}
		}
	}
	
	private void DFSVisit(Aeropuerto a, Vuelo v) {
		int escalas = 0;
		int km = 0;
		a.setColor("Amarillo");
		escalas++;
		km = v.getKilometros();
		for(Aeropuerto u : a.getAeropuertosAdyacentes()) {
			if(u.getColor() == "Blanco") {
				this.DFSVisit(u, v);
			}
		}
		a.setColor("Negro");
	}
}
