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

	/*Para un par de aeropuertos de origen y destino, obtener todos los vuelos disponibles (directos o con
	escalas) que se pueden tomar sin utilizar una aerolínea determinada. Para cada vuelo indicar la
	aerolínea que se puede tomar, el número de escalas a realizar y la cantidad total de kilómetros a
	recorrer.*/
	
	public ArrayList<Object> servicio2(Aeropuerto ao, Aeropuerto ad) {
		return this.DFS(ao, ad);
	}
	
	private ArrayList<Object> DFS(Aeropuerto ao, Aeropuerto ad) {
		ArrayList<Object> resultado = new ArrayList<>();
		int km = 0;
		for(Aeropuerto a : this.aeropuertos) {
			a.setColor("No visitado");
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getColor() == "Finalizado") {
				return resultado;
			}
			else if(a.getColor() == "No visitado") {
				this.DFSVisit(a, ad, resultado, km);
			}
		}
		return resultado;
	}
	
	private ArrayList<Object> DFSVisit(Aeropuerto a, Aeropuerto ad, ArrayList<Object> resultado, int km) {
		a.setColor("Visitado");
		if(a.equals(ad)) {
			return resultado;
		}
		else {
			for(Aeropuerto ady : a.getAeropuertosAdyacentes()) {
				km += a.getVuelo(ady).getKilometros();
				if(ady.getColor() == "No visitado") {
					resultado.addAll(a.getVuelo(ady).getAerolineas());
					resultado.add(km);
					this.DFSVisit(ady, ad, resultado, km);
				}
			}
		}
		return resultado;
	}
}
