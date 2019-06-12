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

	/*Servicio 1
	Verificar si existe un vuelo directo (es decir, sin escalas) entre un aeropuerto de origen y uno de
	destino, para una aerolínea particular. De existir, se desea conocer los kilómetros que requiere el viaje
	y la cantidad de asientos que se encuentran disponibles (es decir, no están reservados).*/

	public Aeropuerto getAeropuerto(String nombreAeropuerto){
		Aeropuerto resultado = new Aeropuerto();
		for (Aeropuerto aerop : this.aeropuertos) {
			if(aerop.getNombre().equals(nombreAeropuerto)){
				resultado = aerop;
				return resultado;
			}
		}
		return resultado;
	}

	public boolean sonAdyacentes(Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino){
		return aeropuertoOrigen.tieneAdyacenteA(aeropuertoDestino);
	}
	
	public ArrayList<Object> servicio1(String ao, String ad, String aerolinea) {
		ArrayList<Object> resultado = new ArrayList<>();
		Vuelo aeropuertoOrigen = new Aeropuerto();
		Vuelo aeropuertoDestino = new Aeropuerto();

		aeropuertoOrigen = this.getAeropuerto(ao);
		aeropuertoDestino = this.getAeropuerto(ad);
		
		if (aeropuertoOrigen != null && aeropuertoDestino != null) {
			if (this.sonAdyacentes(aeropuertoOrigen, aeropuertoDestino)) {
				if(aeropuertoOrigen.contieneAerolinea(aerolinea)){
					Vuelo vuelo = new Vuelo();
					vuelo = aeropuertoOrigen.getVuelo(a);
					resultado.add(vuelo.getKilometros());
					resultado.add(vuelo.getAsientosDisponibles());
				}
			}
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
			a.setEstado("No visitado");
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getEstado() == "No visitado") {
				this.DFSVisit(a, ad, resultado, km, escalas);
			}
		}
		return resultado;
	}

	private ArrayList<Object> DFSVisit(Aeropuerto a, Aeropuerto ad, ArrayList<Object> resultado, int km, int escalas) {
		a.setEstado("Visitado");
		if(a.equals(ad)) {
			return resultado;
		}
		else {
			for(Aeropuerto ady : a.getAeropuertosAdyacentes()) {
				km += a.getVuelo(ady).getKilometros();
				if(ady.getEstado() == "No visitado") {
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
			a.setEstado("No visitado");
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getEstado() == "No visitado") {
				this.DFSVisit2(paiso, paisd, a, resultado, km);
			}
		}
		return resultado;
	}

	private ArrayList<Object> DFSVisit2(String paiso, String paisd, Aeropuerto a, ArrayList<Object> resultado, int km) {
		a.setEstado("Visitado");
		if(a.getPais().equals(paisd)) {
			return resultado;
		}
		else if(a.getPais().equals(paiso)) {
			resultado.add(a);
		}
		for(Aeropuerto ady : a.getAeropuertosAdyacentes()) {
			km += a.getVuelo(ady).getKilometros();
			if(ady.getPais().equals(paisd) && a.getPais().equals(paiso) && ady.getEstado() == "No visitado") {
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
