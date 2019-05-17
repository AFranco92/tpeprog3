package tpeprog3;

import java.util.ArrayList;
import java.util.LinkedList;

public class GrafoVuelos {
	private ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();

	public ArrayList<Aeropuerto> getAeropuertos() {
		return this.aeropuertos;
	}

	public void setAeropuerto(Aeropuerto a) {
		this.aeropuertos.add(a);
	}

	private boolean esDirecto(Aeropuerto ao, Aeropuerto ad, String aerolinea) {
		return ao.contieneAerolinea(aerolinea) && ao.tieneAdyacenteA(ad);
	}

	public ArrayList<Object> servicio1(Aeropuerto ao, Aeropuerto ad, String aerolinea, Vuelo v) {
		ArrayList<Object> resultado = new ArrayList<>();
		if ((v.getAeropuertoOrigen().equals(ao) && v.getAeropuertoAlcanzado().equals(ad)) && this.esDirecto(ao, ad, aerolinea)) {
			resultado.add(v.getKilometros());
			resultado.add(v.getAsientosDisponibles(aerolinea));
		}
		return resultado;
	}

	public ArrayList<Object> servicio2(Aeropuerto ao, Aeropuerto ad) {
		return this.Servicio2DFS(ao, ad);
	}

	private ArrayList<Object> Servicio2DFS(Aeropuerto ao, Aeropuerto ad) {
		ArrayList<Object> resultado = new ArrayList<>();
		int km = 0;
		int escalas = -1;
		for(Aeropuerto a : this.aeropuertos) {
			a.setColor("No visitado");
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getColor() == "Finalizado") {
				return resultado;
			}
			else if(a.getColor() == "No visitado") {
				this.DFSVisit(a, ad, resultado, km, escalas);
			}
		}
		return resultado;
	}

	private ArrayList<Object> DFSVisit(Aeropuerto a, Aeropuerto ad, ArrayList<Object> resultado, int km, int escalas) {
		a.setColor("Visitado");
		if(a.equals(ad)) {
			return resultado;
		}
		else {
			for(Aeropuerto ady : a.getAeropuertosAdyacentes()) {
				km += a.getVuelo(ady).getKilometros();
				if(ady.getColor() == "No visitado") {
					escalas++;
					resultado.addAll(a.getVuelo(ady).getAerolineas());
					resultado.add(km);
					resultado.add(escalas);
					this.DFSVisit(ady, ad, resultado, km, escalas);
				}
			}
		}
		return resultado;
	}

	private ArrayList<Object> Servicio3DFS(String paiso, String paisd) {
		ArrayList<Object> resultado = new ArrayList<>();
		int km = 0;
		for(Aeropuerto a : this.aeropuertos) {
			a.setColor("No visitado");
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getColor() == "No visitado") {
				this.DFSVisit2(paiso, paisd, a, resultado, km);
			}
		}
		return resultado;
	}

	private ArrayList<Object> DFSVisit2(String paiso, String paisd, Aeropuerto a, ArrayList<Object> resultado, int km) {
		a.setColor("Visitado");
		if(a.getPais().equals(paisd)) {
			return resultado;
		}
		else if(a.getPais().equals(paiso)) {
			resultado.add(a);
		}
		for(Aeropuerto ady : a.getAeropuertosAdyacentes()) {
			km += a.getVuelo(ady).getKilometros();
			if(ady.getPais().equals(paisd) && a.getPais().equals(paiso) && ady.getColor() == "No visitado") {
				resultado.add(ady);
				resultado.addAll(a.getVuelo(ady).getAerolineasDisponibles());
				resultado.add(km);
				this.DFSVisit2(paiso, paisd, ady, resultado, km);
			}
		}
		return resultado;
	}

	public ArrayList<Object> servicio3(String paiso, String paisd) {
		return this.Servicio3DFS(paiso, paisd);
	}

}
