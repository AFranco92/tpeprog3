package tpe;

public class Sistema {

	public static void main(String[] args) {
		GrafoVuelos grafo = new GrafoVuelos();
		Aeropuerto a1 = new Aeropuerto("Ezeiza", "ARG");
		Aeropuerto a2 = new Aeropuerto("El prat", "ESP");
		Aeropuerto a3 = new Aeropuerto("Kennedy", "USA");
		Vuelo v1 = new Vuelo(a1, a2, 5000);
		Vuelo v2 = new Vuelo(a2, a3, 12000);
		Reserva r1 = new Reserva(a1, a2, "Aerolineas Argentinas", 10);
		grafo.setAeropuerto(a1);
		grafo.setAeropuerto(a2);
		grafo.setAeropuerto(a3);
		a1.setAeropuertoAdyacente(a2);
		a2.setAeropuertoAdyacente(a3);
		a1.setVuelo(v1);
		a2.setVuelo(v2);
		v1.setAerolineaYasientos("Aerolineas Argentinas", 50);
		v2.setAerolineaYasientos("United Airlines", 70);
		grafo.servicio1(a1, a3, "Aerolineas Argentinas", v2);
		grafo.servicio2(a1, a3);
		grafo.servicio3("ARG", "ESP");
	}
}
