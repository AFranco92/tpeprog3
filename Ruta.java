package testProg3;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class Ruta {
    private double distancia;
    private int cabotaje;
    private HashMap<String,Integer> vuelos = new HashMap<>();
    private Aeropuerto aeropuertoOrigen;
    private Aeropuerto aeropuertoDestino;

    public Ruta(double distancia, int cabotaje, Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino){
        this.distancia = distancia;
        this.cabotaje = cabotaje;
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
    }
    public Ruta() {
		// TODO Auto-generated constructor stub
	}
	public double getDistancia(){
        return this.distancia;
    }
    public int getAsientos(String aerolinea){
        return this.vuelos.get(aerolinea);
    }
    public boolean contieneAerolinea(String aerolinea){
        return this.vuelos.containsKey(aerolinea);
    }
    public Aeropuerto getOrigen(){
        return this.aeropuertoOrigen;
    }
    public Aeropuerto getDestino(){
        return this.aeropuertoDestino;
    }
    public Set<String> getAerolineas(){
        return this.vuelos.keySet();
    }
    public Collection<Integer> getAsientos(){
        return this.vuelos.values();
    }
    public HashMap<String,Integer> getVuelos(){
    	return this.vuelos;
    }
    public void setVuelo(String aerolinea, int asientos){
        this.vuelos.put(aerolinea, asientos);
    }
    public void printDatos(){
        System.out.println("Distancia : " + this.distancia);
        System.out.println("Cabotaje : " + this.cabotaje);
        System.out.println("Aeropuerto Origen : " + this.aeropuertoOrigen.getNombre());
        System.out.println("Aeropuerto Destino : " + this.aeropuertoDestino.getNombre());
    }
}
