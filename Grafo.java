package tpe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grafo {
	private ArrayList<Aeropuerto> aeropuertos = new ArrayList<>();
	private ArrayList<Reserva> reservas = new ArrayList<>();
	private Set<Aeropuerto> settledNodes;
	private Set<Aeropuerto> unSettledNodes;
	private Map<Aeropuerto, Aeropuerto> predecessors;
	private Map<Aeropuerto, Double> distance;

	public Grafo(){

	}

	/*SERVICIO 1 : Verificar si existe un vuelo directo (es decir, sin escalas) entre un aeropuerto de origen y uno de
        destino, para una aerolínea particular. De existir, se desea conocer los kilometros que requiere el viaje
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
        escalas) que se pueden tomar sin utilizar una aerolínea determinada. Para cada vuelo indicar la
        aerolínea que se puede tomar, el número de escalas a realizar y la cantidad total de kilómetros a recorrer.
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

	/*SERVICIO 3:Obtener todos los vuelos directos disponibles desde un país a otro, es decir, donde no se encuentren
        reservados todos los asientos. Para cada vuelo se deberá indicar los aeropuertos de origen y de destino,
        las aerolíneas con pasajes disponibles y la distancia en kilómetros.
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
        Por una cuestión de costos, este recorrido debe realizarse visitando cada aeropuerto una única vez y viajando la menor cantidad posible de kilómetros totales.
	 */
	public ArrayList<Aeropuerto> algoritmoGreedy(String aeropuerto) {
		Aeropuerto origen = new Aeropuerto();
		for(Aeropuerto aerop : this.aeropuertos) {
			if(aerop.getNombre().equals(aeropuerto)) {
				origen = aerop;
			}
		}
		return this.calculateShortestPathFromSource(origen);
	}
	
	
	private ArrayList<Aeropuerto> calculateShortestPathFromSource(Aeropuerto origen) {
		ArrayList<Aeropuerto> settledNodes = new ArrayList<>();
		ArrayList<Aeropuerto> unsettledNodes = new ArrayList<>();
		ArrayList<Aeropuerto> caminoMasCorto = new ArrayList<>();

		unsettledNodes.add(origen);

		while (unsettledNodes.size() != 0) {
			Aeropuerto currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (Aeropuerto aerop : currentNode.getAdyacentes()) {
				Aeropuerto adjacentNode = aerop;
				Ruta ruta = currentNode.getRutaAeropDestino(adjacentNode);
				Double edgeWeight = ruta.getDistancia();
				if (!settledNodes.contains(adjacentNode)) {
					CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode, origen, caminoMasCorto);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return settledNodes;
	}
	private static Aeropuerto getLowestDistanceNode( ArrayList<Aeropuerto> unsettledNodes) {
		Aeropuerto lowestDistanceNode = null;
		Double lowestDistance = Double.MAX_VALUE;
		for (Aeropuerto node: unsettledNodes) {
			for(Ruta ruta : node.getRutas()){
				Double nodeDistance = ruta.getDistancia();
				if (nodeDistance < lowestDistance) {
					lowestDistance = nodeDistance;
					lowestDistanceNode = ruta.getDestino();
				}
			}
		}
		return lowestDistanceNode;
	}
	private static void CalculateMinimumDistance(Aeropuerto evaluationNode, Double edgeWeigh, Aeropuerto sourceNode, Aeropuerto aeropOrigen, ArrayList<Aeropuerto> caminoMasCorto) {
		Double distanciaAdyacente = 0.0;
		Double distanciaActual = 0.0;
		for (Ruta ruta : aeropOrigen.getRutas()) {
			if(ruta.getDestino().getNombre().equals(evaluationNode.getNombre())){
				distanciaAdyacente+= ruta.getDistancia();
			}else if (ruta.getDestino().getNombre().equals(sourceNode.getNombre())) {
				distanciaActual+= ruta.getDistancia();
			}
		}
		if(distanciaAdyacente < distanciaActual){
			caminoMasCorto.add(evaluationNode);
		}else{
			caminoMasCorto.add(sourceNode);
		}
		//     Double sourceDistance = sourceNode.getDistance();
		// if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
		//     evaluationNode.setDistance(sourceDistance + edgeWeigh);
		//     ArrayList<Aeropuerto> shortestPath = new ArrayList<Aeropuerto>(sourceNode.getShortestPath());
		//     shortestPath.add(sourceNode);
		//     evaluationNode.setShortestPath(shortestPath);
		// }
	}


	//BACKTRACKING
	public void backTracking(String aeropuerto){

	}
}
