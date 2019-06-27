package testProg3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Grafo {
	private ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
	private ArrayList<Reserva> reservas = new ArrayList<>();

	public Grafo(){

	}

	/*SERVICIO 1 : Verificar si existe un vuelo directo (es decir, sin escalas) entre un aeropuerto de origen y uno de
        destino, para una aerol�nea particular. De existir, se desea conocer los kilometros que requiere el viaje
        y la cantidad de asientos que se encuentran disponibles (es decir, no estan reservados).
	 */

	private void informarResultados(ArrayList<Object> resultados){
		if(resultados.size() > 0){
			System.out.println("Se han encontrado resultados :");
		}else{
			System.out.println("No se han encontrado resultados.");
		}
	}
	public ArrayList<Object> servicio1(String aeropuertoOrigen, String aeropuertoDestino, String aerolinea){
		ArrayList<Object> salida = null;
		Aeropuerto aOrigen = new Aeropuerto();
		Aeropuerto aDestino = new Aeropuerto();
		for (Aeropuerto aeropuerto : this.aeropuertos) {
			if(aeropuerto.getNombre().equals(aeropuertoOrigen)){
				aOrigen = aeropuerto;
			}else if(aeropuerto.getNombre().equals(aeropuertoDestino)){
				aDestino = aeropuerto;
			}
		}
		salida = this.getVueloDirecto(aOrigen, aDestino, aerolinea);
		this.informarResultados(salida);
		return salida;
	}
	private ArrayList<Object> getVueloDirecto(Aeropuerto aOrigen, Aeropuerto aDestino, String aerolinea){
		ArrayList<Object> salida = new ArrayList<>();
		for (Ruta ruta : aOrigen.getRutas()) {
			if (ruta.getDestino().getNombre().equals(aDestino.getNombre()) && ruta.contieneAerolinea(aerolinea) != false) {
				salida.add(ruta.getDistancia());
				salida.add(ruta.getAsientos(aerolinea));
			}
		}
		return salida;
	}

	/*SERVICIO 2 : Para un par de aeropuertos de origen y destino, obtener todos los vuelos disponibles (directos o con
        escalas) que se pueden tomar sin utilizar una aerol�nea determinada. Para cada vuelo indicar la
        aerol�nea que se puede tomar, el n�mero de escalas a realizar y la cantidad total de kil�metros a recorrer.
	 */

	public ArrayList<Object> servicio2(String aeropuertoOrigen, String aeropuertoDestino){
		Aeropuerto aOrigen = new Aeropuerto();
		Aeropuerto aDestino = new Aeropuerto();
		ArrayList<Object> salida = new ArrayList<>();
		ArrayList<Object> vuelos = null;
		double distancia = 0;
		int escalas=0;
		for (Aeropuerto aeropuerto : this.aeropuertos) {
			if(aeropuerto.getNombre().equals(aeropuertoOrigen)){
				aOrigen = aeropuerto;
			}else if(aeropuerto.getNombre().equals(aeropuertoDestino)){
				aDestino = aeropuerto;
			}
			aeropuerto.setEstado("No visitado");
		}
		vuelos = this.getVuelos(aOrigen, aDestino, distancia, escalas, salida);
		this.informarResultados(vuelos);
		return vuelos;
	}

	private ArrayList<Object> getVuelos(Aeropuerto aeropuerto, Aeropuerto aDestino, double distancia, int escalas, ArrayList<Object> salida){
		aeropuerto.setEstado("Visitado");
		if(aeropuerto.getNombre().equals(aDestino.getNombre())){
			aeropuerto.setEstado("Finalizado");
			return salida;
		}
		for (Ruta ruta : aeropuerto.getRutas()) {
			distancia += ruta.getDistancia();
			if (ruta.getDestino().getNombre().equals(aDestino.getNombre())) {
				salida.addAll(agregarRutas(ruta.getVuelos(), distancia, escalas));
				aeropuerto.setEstado("Finalizado");
			}else{
				if(aeropuerto.getEstado().equals("No visitado")){
					escalas++;
					this.getVuelos(ruta.getDestino(), aDestino, distancia, escalas, salida);
					ruta.getDestino().setEstado("Finalizado");
				}
			}
		}
		return salida;
	}
	private ArrayList<Object> agregarRutas(HashMap<String,Integer> ruta, double km, int escalas){
		ArrayList<Object> salida = new ArrayList<>();
		Iterator it = ruta.entrySet().iterator();
		while(it.hasNext()) {
			Object aux = it.next();
			salida.add(aux);
		}
		salida.add(km);
		salida.add(escalas);
		return salida;
	}

	/*SERVICIO 3:Obtener todos los vuelos directos disponibles desde un pa�s a otro, es decir, donde no se encuentren
        reservados todos los asientos. Para cada vuelo se deber� indicar los aeropuertos de origen y de destino,
        las aerol�neas con pasajes disponibles y la distancia en kil�metros.
	 */
	public ArrayList<Object> servicio3(String paisOrigen, String paisDestino){
		Aeropuerto aOrigen = new Aeropuerto();
		Aeropuerto aDestino = new Aeropuerto();
		ArrayList<Object> salida = null;
		for (Aeropuerto aeropuerto : this.aeropuertos) {
			if(aeropuerto.getPais().equals(paisOrigen)){
				aOrigen = aeropuerto;
			}else if(aeropuerto.getPais().equals(paisDestino)){
				aDestino = aeropuerto;
			}
		}
		salida = this.getVuelosDirectosS3(aOrigen, aDestino);
		this.informarResultados(salida);
		return salida;
	}

	private ArrayList<Object> getVuelosDirectosS3(Aeropuerto aOrigen, Aeropuerto aDestino){
		ArrayList<Object> salida = new ArrayList<>();
		for (Ruta ruta : aOrigen.getRutas()) {
			if(ruta.getDestino().getPais().equals(aDestino.getPais())){
				salida.add(aOrigen.getNombre());
				salida.add(aDestino.getNombre());
				salida.addAll(ruta.getAerolineas());
				salida.add(ruta.getDistancia());
			}
		}
		return salida;
	}


	//PRIMERA OPCION : LISTAR TODOS LOS AEROPUERTOS. CHEQUEADO
	public void listarAeropuertos(){
		for (Aeropuerto aeropuerto : this.aeropuertos) {
			System.out.println("----------------");
			aeropuerto.printDatos();
			System.out.println("----------------");
		}
	}
	public void setAeropuertos(ArrayList<Aeropuerto> aeropuertos){
		this.aeropuertos.addAll(aeropuertos);
	}

	//SEGUNDA OPCION : LISTAR TODAS LAS RESERVAS REALIZADAS.
	public void listarReservas(){
		for (Reserva reserva : this.reservas) {
			System.out.println("----------------");
			reserva.printDatos();
			System.out.println("----------------");
		}
	}
	public void setReservas(ArrayList<Reserva> reservas){
		this.reservas.addAll(reservas);
	}

	//SEGUNDA PARTE
	/*
        Partiendo de un aeropuerto cualquiera debe visitar todos losaeropuertos restantes y retornar al aeropuerto de origen.
        Por una cuesti�n de costos, este recorrido debe realizarse visitando cada aeropuerto una �nica vez y viajando la menor cantidad posible de kil�metros totales.
	 */


	public ArrayList<Aeropuerto> getOrigenGreedy(String origen){
		Aeropuerto aeropOrigen = new Aeropuerto();
		for (Aeropuerto aerop : this.aeropuertos) {
			if(aerop.getNombre().equals(origen)){
				aeropOrigen = aerop;
			}
			aerop.setEstado("No visitado");
		}
		return greedy(aeropOrigen);
	}

	private ArrayList<Aeropuerto> greedy(Aeropuerto origen){
		ArrayList<Aeropuerto> solucion = new ArrayList<>();
		Aeropuerto candidato = origen;

		while(candidato != null){
			candidato.setEstado("Visitado");
			solucion.add(candidato);
			candidato = getMejorCandidato(candidato);		
		}

		if(this.estanVisitados() && llegoADestino(solucion.get(solucion.size()-1), origen)){
			System.out.println("Hay solucion.");
		}
		return solucion;
	}
	private Aeropuerto getMejorCandidato(Aeropuerto origen){
		Aeropuerto salida = null;
		Double distanciaMenor = Double.MAX_VALUE;
		for (Ruta ruta : origen.getRutas()) {
			if(ruta.getDistancia() < distanciaMenor && ruta.getDestino().getEstado().equals("No visitado")){
				salida = ruta.getDestino();
				distanciaMenor = ruta.getDistancia();
			}
		}
		return salida;
	}
	private boolean estanVisitados(){
		for (Aeropuerto aerop : this.aeropuertos) {
			if(aerop.getEstado().equals("No visitado"))
				return false;
		}
		return true;
	}
	private boolean llegoADestino(Aeropuerto aerop, Aeropuerto origen) {
		for (Ruta ruta : aerop.getRutas()) {
			if(ruta.getDestino().getNombre().equals(origen.getNombre())) {
				return true;
			}
		}
		return false;
	}


	//BACKTRACKING
	public ArrayList<Aeropuerto> backTracking(String origen){
		int grafoSize = this.aeropuertos.size();
		double distancia = 0;
		ArrayList<Double> mejordistancia = new ArrayList<>();
		Aeropuerto salida = new Aeropuerto();
		ArrayList<Aeropuerto> mejorSolucion = new ArrayList<>();
		ArrayList<Aeropuerto> solucion = new ArrayList<>();
		for (Aeropuerto aerop : this.aeropuertos) {
			if(aerop.getNombre().equals(origen)){
				salida = aerop;
			}
			aerop.setEstado("No visitado");
		}
		solucion.add(salida);
		mejordistancia.add(0, Double.MAX_VALUE);
		this.getBackTracking(salida, salida, solucion, mejorSolucion, grafoSize, distancia, mejordistancia);
		return mejorSolucion;
	}
	private void getBackTracking(Aeropuerto aeropuerto, Aeropuerto origen, ArrayList<Aeropuerto> solucion, ArrayList<Aeropuerto> mejorSolucion, int grafoSize, double distancia, ArrayList<Double> mejordistancia){
		if(esSolucion(solucion, origen, grafoSize, distancia) != 0){
			distancia = esSolucion(solucion, origen, grafoSize, distancia);
			if(distancia < mejordistancia.get(0)) {
				mejordistancia.add(0, distancia);
				mejorSolucion = (ArrayList<Aeropuerto>) solucion.clone();
				System.out.println(mejorSolucion);
				System.out.println(mejordistancia.get(0));
				
			}
		}
		else{
			for(Ruta ruta : aeropuerto.getRutas()){
				if(!solucion.contains(ruta.getDestino())) {
					solucion.add(ruta.getDestino());
					ruta.getDestino().setEstado("Visitado");
					distancia += ruta.getDistancia();
					getBackTracking(ruta.getDestino(), origen, solucion, mejorSolucion, grafoSize, distancia, mejordistancia);
					distancia -= ruta.getDistancia();
					solucion.remove(solucion.size()-1);
				}	
			}
		}
		
	}
	private double esSolucion(ArrayList<Aeropuerto> solucion, Aeropuerto origen, int grafoSize, double distancia){
		if(solucion.size() == grafoSize)
			return llegoADestinoBackTracking(solucion.get(solucion.size() - 1), origen, distancia);
		else
			return 0;

	}
	private double llegoADestinoBackTracking(Aeropuerto aerop, Aeropuerto origen, double distancia) {
		for (Ruta ruta : aerop.getRutas()) {
			if(ruta.getDestino().getNombre().equals(origen.getNombre())) {
				distancia += ruta.getDistancia();
				return distancia;
			}
		}
		return 0;
	}
}
