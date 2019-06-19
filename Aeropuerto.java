package tpe;

import java.util.ArrayList;

public class Aeropuerto {
    private String nombre;
    private String pais;
    private String ciudad;
    private String estado;
    private ArrayList<Ruta> rutas = new ArrayList<>();

    public Aeropuerto(String nombre, String ciudad, String pais){
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
    }
    public Aeropuerto(){
        this.nombre= "";
        this.pais= "";
        this.ciudad= "";
    }

    public String getNombre(){
        return this.nombre;
    }
    public ArrayList<Ruta> getRutas(){
        return this.rutas;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }
	public String getEstado() {
		return this.estado;
	}
	public String getPais() {
		return this.pais;
    }
    public void setRutas(ArrayList<Ruta> rutas){
        this.rutas.addAll(rutas);
    }
    public void setRuta(Ruta ruta){
        this.rutas.add(ruta);
    }
    public void printDatos(){
        System.out.println("Nombre  : " + this.nombre);
        System.out.println("Pais  : " + this.pais);
        System.out.println("Ciudad  : " + this.ciudad);
        for (Ruta ruta : this.rutas) {
            System.out.println("Datos ruta : ");
            ruta.printDatos();
        }
    }

    public ArrayList<Aeropuerto> getAdyacentes(){
        ArrayList<Aeropuerto> salida = new ArrayList<>();
        for (Ruta ruta : this.rutas) {
            salida.add(ruta.getDestino());
        }
        return salida;
    }

    public Ruta getRutaAeropDestino(Aeropuerto destino){
        Ruta salida = new Ruta();
        for (Ruta ruta : this.rutas) {
            if(ruta.getDestino().getNombre().equals(destino.getNombre())){
                salida = ruta;
            }
        }
        return salida;
    }
}
