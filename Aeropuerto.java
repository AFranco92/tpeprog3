package tpe;

import java.util.ArrayList;

public class Aeropuerto {
	private String estado;
	private String nombre;
	private String pais;
	private String ciudad;
	private ArrayList<Aeropuerto> aeropuertosadyacentes = new ArrayList<>();
	private ArrayList<Vuelo> vuelos = new ArrayList<>();
	private ArrayList<Reserva> reservas = new ArrayList<>();
	
	public Aeropuerto(String n, String p) {
		this.nombre = n;
		this.pais = p;
	}
	public Aeropuerto() {
		
	}
	public Aeropuerto(String nombre, String pais, String ciudad) {
		this.nombre = nombre;
		this.pais = pais;
		this.ciudad = ciudad;
	}
	
	public String toString() {
		return this.pais.toString();
	}
	public void getDatos() {
		System.out.println(this.nombre + "\n");
		System.out.println(this.pais + "\n");
	}
	
	public ArrayList<Reserva> getReservas() {
		return this.reservas;
	}
	public void setReservas(ArrayList<Reserva> reservas) {
		this.reservas.addAll(reservas);
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public void setEstado(String c) {
		this.estado = c;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String n) {
		this.nombre = n;
	}
	
	public String getPais() {
		return this.pais;
	}
	
	public void setPais(String p) {
		this.pais = p;
	}
	
	public ArrayList<Aeropuerto> getAeropuertosAdyacentes() {
		return this.aeropuertosadyacentes;
	}
	
	public void setAeropuertoAdyacente(Aeropuerto a) {
		this.aeropuertosadyacentes.add(a);
	}
	
	public ArrayList<Vuelo> getVuelos() {
		return this.vuelos;
	}
	
	public Vuelo getVuelo(Aeropuerto a) {
		Vuelo resultado = new Vuelo();
		for(int i = 0; i < this.vuelos.size(); i++) {
			if(this.vuelos.get(i).getAeropuertoAlcanzado().equals(a)) {
				resultado = this.vuelos.get(i);
			}
		}
		return resultado;
	}
	
	public boolean tieneAdyacenteA(Aeropuerto a) {
		return this.getAeropuertosAdyacentes().contains(a);
	}
	
	public boolean equals(Object o) {
		Aeropuerto a = (Aeropuerto) o;
		return this.getNombre().equals(a.getNombre());
	}
	
	public void setVuelo(Vuelo v) {
		this.vuelos.add(v);
		this.setAeropuertoAdyacente(v.getAeropuertoAlcanzado());
		v.getAeropuertoAlcanzado().setAeropuertoAdyacente(this);
	}
	
	public boolean contieneAerolinea(String a) {
		for(int i = 0; i < this.getVuelos().size(); i++) {
			if(this.getVuelos().get(i).getAerolineasYAsientos().containsKey(a)) {
				return true;
			}
		}
		return false;
	}

	public Vuelo getVuelo(String a) {
		boolean condicion = true;
		while (condicion) {
			for (Vuelo vuelo : this.vuelos) {
				if(vuelo.getAerolineasYAsientos().containsKey(a))
					return vuelo;
					condicion = false;
			}
		}
		return null;
	}
	
	public void setVuelos(ArrayList<Vuelo> vuelos) {
		this.vuelos.addAll(vuelos);
	}
	
	
	
}