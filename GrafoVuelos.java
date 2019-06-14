package tpe;

import java.util.ArrayList;
import java.util.LinkedList;

public class GrafoVuelos {
	private ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();

	public void getAeropuertos() {
		for (Aeropuerto aeropuerto : this.aeropuertos) {
			System.out.println(aeropuerto.toString());
		}
	}
	
	public String getReservas() {
		String salida = null;
		for (Aeropuerto aeropuerto : this.aeropuertos) {
			for (Reserva reserva : aeropuerto.getReservas()) {
				salida += reserva;
			}
		}
		return salida;
	}

	public void setAeropuerto(Aeropuerto a) {
		this.aeropuertos.add(a);
	}
	
	public void setAeropuertos(ArrayList<Aeropuerto> aeropuerto	) {
		this.aeropuertos.addAll(aeropuerto);
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
		Aeropuerto aeropuertoOrigen = this.getAeropuerto(ao);
		Aeropuerto aeropuertoDestino =  this.getAeropuerto(ad);

		if (aeropuertoOrigen != null && aeropuertoDestino != null) {
			if (this.sonAdyacentes(aeropuertoOrigen, aeropuertoDestino)) {
				if(aeropuertoOrigen.contieneAerolinea(aerolinea)){
					Vuelo vuelo = new Vuelo();
					vuelo = aeropuertoOrigen.getVuelo(aerolinea);
					resultado.add(vuelo.getKilometros());
					resultado.add(vuelo.getAsientosDisponibles(aerolinea));
				}
			}
		}
		return resultado;
	}

	/*Servicio 2
	Para un par de aeropuertos de origen y destino, obtener todos los vuelos disponibles (directos o con
	escalas) que se pueden tomar sin utilizar una aerolínea determinada. Para cada vuelo indicar la
	aerolínea que se puede tomar, el número de escalas a realizar y la cantidad total de kilómetros a  recorrer.*/

	public ArrayList<Object> servicio2(String ao, String ad) {
		return this.Servicio2DFS(ao, ad);
	}

	/*Servicio 3
	Obtener todos los vuelos directos disponibles desde un país a otro, es decir, donde no se encuentren
	reservados todos los asientos. Para cada vuelo se deberá indicar los aeropuertos de origen y de destino,
	las aerolíneas con pasajes disponibles y la distancia en kilómetros.*/
	
	public ArrayList<Object> Servicio3(String paiso, String paisd) { 
		ArrayList<Object> resultado = new ArrayList<>();
		for (Aeropuerto aerop : this.aeropuertos) {
			if (aerop.getPais().equals(paiso)) {
				Aeropuerto aeropOrigen = aerop;
				for(Vuelo vuelo : aeropOrigen.getVuelos()) {
					if(vuelo.getAeropuertoDestino().getPais().equals(paisd)) {
						Aeropuerto aeropDestino = vuelo.getAeropuertoDestino();
						resultado.add(aeropOrigen.getNombre());
						resultado.add(aeropDestino.getNombre());
						resultado.add(vuelo.getAerolineasDisponibles());
						resultado.add(vuelo.getKilometros());
					}
				}
				aerop.setEstado("Finalizado");
			}
		}
		return resultado;
	}

	public ArrayList<Object> servicio3(String paiso, String paisd) {
		return this.Servicio3(paiso, paisd);
	}


	private ArrayList<Object> Servicio2DFS(String ao, String ad) {
		Aeropuerto aeropOrigen = new Aeropuerto();
		Aeropuerto aeropDestino = new Aeropuerto();
		ArrayList<Object> resultado = new ArrayList<>();
		int km = 0;
		int escalas = -1;
		for(Aeropuerto a : this.aeropuertos) {
			a.setEstado("No visitado");
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getNombre().equals(ao)) {
				aeropOrigen = a;
			} else if(a.getNombre().equals(ad)) {
				aeropDestino = a;
			}
		}
		for(Aeropuerto a : this.aeropuertos) {
			if(a.getNombre().equals(ao)) {
				aeropOrigen = a;
			}
			if(a.getEstado() == "Finalizado") {
				return resultado;
			}
			else if(a.getEstado() == "No visitado") {
				this.DFSVisit(aeropOrigen, aeropDestino, resultado, km, escalas);
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
				ady.setEstado("Finalizado");
			}
		}
		return resultado;
	}
}

