public class Reserva {
    private String aeropuertoOrigen;
    private String aeropuertoDestino;
    private String aerolinea;
    private int asientos;

    public Reserva(String aeropuertoOrigen, String aeropuertoDestino, String aerolinea, int asientos){
        this.aeropuertoOrigen = aeropuertoOrigen;
        this.aeropuertoDestino = aeropuertoDestino;
        this.aerolinea = aerolinea;
        this.asientos = asientos;
    }

    public void printDatos(){
        System.out.println("Aeropuerto Origen : " + this.aeropuertoOrigen);
        System.out.println("Aeropuerto Destino : " + this.aeropuertoDestino);
        System.out.println("Aerolinea : " + this.aerolinea);
        System.out.println("Asientos : " + this.asientos);
    }
}
